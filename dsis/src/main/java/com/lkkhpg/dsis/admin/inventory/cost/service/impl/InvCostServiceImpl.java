/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.cost.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.cost.service.IInvCostDetailsService;
import com.lkkhpg.dsis.admin.inventory.cost.service.IInvCostService;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostAttributes;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostDetails;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostRecords;
import com.lkkhpg.dsis.common.inventory.cost.mapper.CostAttributesMapper;
import com.lkkhpg.dsis.common.inventory.cost.mapper.CostDetailsMapper;
import com.lkkhpg.dsis.common.inventory.cost.mapper.CostRecordsMapper;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.mapper.InvTransactionMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ISpmCurrencyService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 成本记录ServiceImpl.
 * 
 * @author hanrui.huang
 *
 */
@Service
@Transactional
public class InvCostServiceImpl implements IInvCostService {

    private Logger logger = LoggerFactory.getLogger(InvCostServiceImpl.class);

    @Autowired
    private InvTransactionMapper invTransactionMapper;
    @Autowired
    private CostRecordsMapper costRecordsMapper;
    @Autowired
    private CostDetailsMapper costDetailsMapper;
    @Autowired
    private CostAttributesMapper costAttributesMapper;
    @Autowired
    private IParamService paramService;
    @Autowired
    private ISpmCurrencyService spmCurrencyService;
    @Autowired
    private IDocSequenceService docSequenceService;
    @Autowired
    private IInvCostDetailsService invCostDetailsService;

    @Override
    public List<CostRecords> queryCostRecords(IRequest request, CostRecords costRecords, int page, int pagesize) {
        if (costRecords == null || costRecords.getInvOrgId() == null) {
            return null;
        }
        List<String> parm = paramService.getParamValues(request, InventoryConstants.SPM_COST_METHOD,
                SystemProfileConstants.ORG_TYPE_INV, costRecords.getInvOrgId());
        if (parm == null || StringUtils.isEmpty(parm.get(0))) {
            if (logger.isDebugEnabled()) {
                logger.debug("The paramValue[{}] not found on the InvOrgId[{}]!", InventoryConstants.SPM_COST_METHOD,
                        costRecords.getInvOrgId());
            }
            return null;
        }
        PageHelper.startPage(page, pagesize);
        switch (parm.get(0)) {
        case InventoryConstants.COST_CAL_FIFO:
            return costRecordsMapper.queryCostRecordsByFIFO(costRecords);
        case InventoryConstants.COST_CAL_AVRAG:
            return costRecordsMapper.queryCostRecordsByAvrag(costRecords);
        default:
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean removeCostRecords(IRequest requestContext, CostRecords costRecords) throws InventoryException {
        if (costRecords == null || costRecords.getYear() == null || costRecords.getMonth() == null
                || costRecords.getInvOrgId() == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        List<CostRecords> costRecordsList = costRecordsMapper.getNewCostRecords(costRecords.getInvOrgId());
        if (costRecordsList.size() > 0) {
            costRecordsList.forEach(record -> {
                if (!costRecords.getYear().equals(record.getYear())
                        || !costRecords.getMonth().equals(record.getMonth())) {
                    sb.append(record.getInvOrgName()).append(record.getYear())
                            .append(String.format("%02d", record.getMonth())).append(",");
                }
            });
        }
        if (sb.length() > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("msg.error.inv_only_cancle_latest_cost_records.[{}]", sb.deleteCharAt(sb.length() - 1));
            }
            throw new InventoryException(InventoryException.MSG_ERROR_INV_ONLY_CANCLE_LATEST_COST_RECORDS,
                    new Object[] { sb });
        }
        costRecords.setStatus(InventoryConstants.COST_STATUS_C);
        costRecordsMapper.updateCostRecords(costRecords);
        costDetailsMapper.updateCostDetail(costRecords);
        // TODO 删除对应属性表信息
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void calculateCost(IRequest request, List<CostDetails> costDetailsList) throws InventoryException {
        CostDetails costDetailsForm = costDetailsList.remove(0);
        Long costOrgId = costDetailsForm.getInvOrgId();// 库存归集中心Id
        Integer year = costDetailsForm.getYear();
        Integer month = costDetailsForm.getMonth();
        // 1.校验是否满足提交成本条件
        invCostDetailsService.checkDetail(request, costOrgId, year, month);
        // 2.校验是否已获取成本记录
        List<CostDetails> detailList = costDetailsMapper.queryIsGetUnitCostInfo(costDetailsForm);
        if (detailList == null || detailList.size() < 1 || detailList.isEmpty()) {
            // 判断参数年月是否产生事务
            List<InvTransaction> invTrxs = invTransactionMapper.queryTrxs(costOrgId, year, month);
            if (invTrxs != null && invTrxs.size() > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Do not have get cost of [{}-{}] from inv_transaction.", year, month);
                }
                throw new InventoryException(InventoryException.MSG_ERROR_INV_NO_GENERATED_COST_DETAIL, null);
            }
        }
        // 更新details表中成本值
        costDetailsList.forEach(costDetails -> {
            costDetails.setYear(year);
            costDetails.setMonth(month);
        });
        invCostDetailsService.updateCostDetails(request, costDetailsList);
        // 3.校验是否存在成本值为空的记录
        List<String> paramValues = paramService.getParamValues(request, InventoryConstants.SPM_COST_METHOD,
                SystemProfileConstants.ORG_TYPE_INV, costOrgId);
        String value = paramValues.get(0);
        if (InventoryConstants.COST_CAL_AVRAG.equals(value)) {
            // 加权平均
            costDetailsForm.setTrxType(InventoryConstants.TRANSACTION_TYPE_STOCK_IN);
        }
        List<CostDetails> list2 = costDetailsMapper.queryCostUnitIsNull(costDetailsForm);
        if (list2 != null && list2.size() > 0 && !list2.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The month[{}] had cost is null.", month);
            }
            throw new InventoryException(InventoryException.MSG_ERROR_THIS_MONTH_HAS_COST_NULL, null);
        }
        CostRecords costRecord = new CostRecords();
        costRecord.setInvOrgId(costOrgId);
        costRecord.setYear(year);
        costRecord.setMonth(month);
        List<CostRecords> costRecords = null;
        switch (value) {
        case InventoryConstants.COST_CAL_FIFO:
            costRecords = costRecordsMapper.getCostRecordsByFIFO(costRecord);
            break;
        case InventoryConstants.COST_CAL_AVRAG:
            costRecords = self().calculateAverageCostForTw(costRecord);
            break;
        default:
            break;
        }
        // 保存成本明细记录
        self().createCostRecords(request, costOrgId, costRecords);
    }

    @Override
    public List<CostRecords> calculateAverageCostForTw(CostRecords costRecords) throws InventoryException {
        BigDecimal quantity = BigDecimal.ZERO;
        BigDecimal unitCost = BigDecimal.ZERO;
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal currentComcpSum = BigDecimal.ZERO; // 本月COMCP总数
        // 获取上个月成本信息
        CostRecords record = new CostRecords();
        record.setInvOrgId(costRecords.getInvOrgId());
        record.setStatus(InventoryConstants.COST_STATUS_P);
        if (InventoryConstants.COST_MONTH_JAN.equals(costRecords.getMonth())) {
            record.setYear(costRecords.getYear() - 1);
            record.setMonth(InventoryConstants.COST_MONTH_NOV);
        } else {
            record.setYear(costRecords.getYear());
            record.setMonth(costRecords.getMonth() - 1);
        }
        // 获取上月成本记录信息.
        List<CostRecords> lastCostRecords = costRecordsMapper.getCostRecordsByParams(record);
        // 获取当前月份成本记录信息
        List<CostRecords> costRecordList = costRecordsMapper.getCostRecordsByAvragForTW(costRecords);
        for (CostRecords costRecord : costRecordList) {
            // 1.期末数量:上月期末数量+本月STKIN的数量+本月COMCP的数量+本月成本数量
            quantity = costRecord.getLastMonthQty().add(costRecord.getCurrentStkinQty())
                    .add(costRecord.getCurrentComcpQty()).add(costRecord.getCurrentCostQty());
            costRecord.setQuantity(quantity);
            // 2.单位成本：
            currentComcpSum = costRecord.getUnitCostSub().multiply(costRecord.getCurrentComcpQty());
            BigDecimal unitCostSum = costRecord.getLastMonthBalance().add(costRecord.getCurrentUnitCost())
                    .add(currentComcpSum);
            BigDecimal qtySum = quantity.subtract(costRecord.getCurrentCostQty());
            if (!qtySum.equals(BigDecimal.ZERO)) {
                unitCost = unitCostSum.divide(qtySum, 2, BigDecimal.ROUND_HALF_EVEN);
            } else {
                unitCost = BigDecimal.ZERO;
            }
            costRecord.setUnitCost(unitCost);
            // 3.期末余额：期末数量*单位成本
            balance = unitCost.multiply(quantity);
            costRecord.setBalance(balance);
        }
        // 生成本月份没有生成事务但上月产生事务的商品的成本记录
        List<CostRecords> flagList = new ArrayList<CostRecords>();
        for (CostRecords lastCostRecord : lastCostRecords) {
            boolean flag = false;
            for (CostRecords costRecord : costRecordList) {
                if (lastCostRecord.getItemId().equals(costRecord.getItemId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (InventoryConstants.COST_MONTH_NOV.equals(lastCostRecord.getMonth())) {
                    lastCostRecord.setMonth(1);
                } else {
                    lastCostRecord.setMonth(lastCostRecord.getMonth() + 1);
                }
                if ("NONE".equals(lastCostRecord.getLotNumber())) {
                    lastCostRecord.setLotNumber(null);
                    lastCostRecord.setExpiryDate(null);
                }
                flagList.add(lastCostRecord);
            }
        }
        costRecordList.addAll(flagList);
        return costRecordList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CostRecords> createCostRecords(IRequest request, Long costOrgId, List<CostRecords> costRecords) {
        // 获取本位币
        List<String> paramValues = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_INV, costOrgId);
        if (paramValues == null || paramValues.size() < 1 || paramValues.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("From invOrgId[{}] not found the OrgParam[{}].", costOrgId,
                        SystemProfileConstants.SPM_CURRENCY);
            }
            return null;
        }
        for (CostRecords costRecord : costRecords) {
            DocSequence docSequence = new DocSequence();
            docSequence.setDocType(InventoryConstants.COST_DOC_TYPE);
            docSequence.setPk1Value(costRecord.getInvOrgId().toString());
            String.valueOf(costRecord.getYear()).concat(String.format("%02d", costRecord.getMonth()));
            String key2 = String.valueOf(costRecord.getYear()).concat(String.format("%02d", costRecord.getMonth()));
            docSequence.setPk2Value(key2);
            String number = docSequenceService.getSequence(request, docSequence, InventoryConstants.COST_CST + key2,
                    InventoryConstants.COST_NUMBER_LENGTH, InventoryConstants.COST_NUMBER_STEP);
            costRecord.setCostRecordNumber(number);
            costRecord.setStatus(InventoryConstants.COST_STATUS_P);
            // 计算精度
            costRecord.setBalance(spmCurrencyService.toPrecisionValue(paramValues.get(0), costRecord.getBalance()));
            // 库存归集中心Id
            costRecord.setCostOrgId(costOrgId);
            costRecordsMapper.insert(costRecord);
            // 更新成本明细处理状态为P
            costDetailsMapper.updateCostDetail(costRecord);
        }
        return costRecords;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CostAttributes> saveOrUpdateCostAttributes(IRequest request, List<CostAttributes> costAttributes) {
        if (costAttributes == null || costAttributes.size() < 1 || costAttributes.get(0).getCostRecordId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("CostAttributes is null.");
            }
            return null;
        }
        for (CostAttributes costAttribute : costAttributes) {
            switch (costAttribute.get__status()) {
            case DTOStatus.ADD:
                costAttributesMapper.insertSelective(costAttribute);
                break;
            case DTOStatus.DELETE:
                costAttributesMapper.deleteByPrimaryKey(costAttribute.getCostAttrId());
                break;
            case DTOStatus.UPDATE:
                costAttributesMapper.updateByPrimaryKeySelective(costAttribute);
                break;
            default:
                break;
            }
        }
        return costAttributes;
    }

    @Override
    public List<CostAttributes> queryCostAttributes(IRequest request, Long costRecordId) {
        if (costRecordId == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("costRecordId is null.");
            }
            return null;
        }
        return costAttributesMapper.queryCostAttrByCostRecordId(costRecordId);
    }

}
