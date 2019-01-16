/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.inventory.cost.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.inventory.cost.service.IInvCostDetailsService;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostDetails;
import com.lkkhpg.dsis.common.inventory.cost.dto.CostRecords;
import com.lkkhpg.dsis.common.inventory.cost.mapper.CostDetailsMapper;
import com.lkkhpg.dsis.common.inventory.cost.mapper.CostRecordsMapper;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.mapper.InvTransactionMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 成本数据获取实现层.
 * 
 * @author HuangJiaJing
 *
 */
@Service
@Transactional
public class InvCostDetailsServiceImpl implements IInvCostDetailsService {

    private Logger logger = LoggerFactory.getLogger(InvCostDetailsServiceImpl.class);

    @Autowired
    private CostDetailsMapper costDetailsMapper;
    @Autowired
    private CostRecordsMapper costRecordsMapper;
    @Autowired
    private IParamService paramService;
    @Autowired
    private InvTransactionMapper invTransactionMapper;
    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean checkDetail(IRequest request, Long invOrgId, Integer year, Integer month) throws InventoryException {
        if (invOrgId == null || year == null || month == null) {
            logger.debug("The parameters of invOrgId or year or month is null");
            return false;
        }
        // 1.校验当前库存组织归集中心是否是其本身
        SpmInvOrganization spmInvOrganization = spmInvOrganizationMapper.selectByPrimaryKey(invOrgId);
        if (!spmInvOrganization.getInvOrgId().equals(spmInvOrganization.getCostOrgId())) {
            if (logger.isDebugEnabled()) {
                logger.debug("The current invOrgId[{}] is not a costOrgId[{}].", invOrgId,
                        spmInvOrganization.getCostOrgId());
            }
            throw new InventoryException(InventoryException.MSG_ERROR_INV_COST_NOT_COST_ORG_ID, null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
        // 2.校验当前库存组织、年份、月份下是否已生成成本记录
        CostRecords record = new CostRecords();
        record.setInvOrgId(invOrgId);
        record.setYear(year);
        record.setMonth(month);
        record.setStatus(InventoryConstants.COST_STATUS_P);
        StringBuilder sb = new StringBuilder();
        List<CostRecords> costRecordsList = costRecordsMapper.queryCostRecords(record);
        if (costRecordsList.size() > 0) {
            costRecordsList.forEach(costRecords -> {
                sb.append(costRecords.getInvOrgName()).append(",");
            });
            if (logger.isDebugEnabled()) {
                logger.debug("The invOrgId[{}] had generated record in {}{}", new Object[] { invOrgId, year, month });
            }
            throw new InventoryException(InventoryException.MSG_ERROR_INV_THIS_MONTH_HAS_GENERATED_RECORD,
                    new Object[] { sb.deleteCharAt(sb.length() - 1) });
        }
        // 3.校验上个自然月是否已经生成成本
        List<CostRecords> lastCostRecords = costRecordsMapper.queryTheMaxMonthOfInvOrgId(invOrgId);
        if (lastCostRecords.size() > 0) {
            Calendar calendar = Calendar.getInstance();
            lastCostRecords.forEach(costRecords -> {
                calendar.set(costRecords.getYear(), costRecords.getMonth() - 1, 1);
                calendar.add(Calendar.MONTH, 1);
                if (year.intValue() != calendar.get(Calendar.YEAR)
                        || month.intValue() != (calendar.get(Calendar.MONTH) + 1)) {
                    // 库存组织name+年份月份
                    sb.append(costRecords.getInvOrgName()).append(sdf.format(calendar.getTime())).append(",");
                }
            });
            if (sb.length() > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The last generate record on [{}].",
                            new Object[] { sb.deleteCharAt(sb.length() - 1) });
                }
                throw new InventoryException(InventoryException.MSG_ERROR_INV_COST_GET_ERROR,
                        new Object[] { sb.toString() });
            }
            return true;
        }
        // 判断是否参数库存组织、年、月份下为第一次产生事务
        List<InvTransaction> invTrxs = invTransactionMapper.queryTrxs(invOrgId, null, null);
        if (invTrxs.size() > 0) {
            InvTransaction invTrx = invTrxs.get(0);
            if (!year.equals(Integer.valueOf(new SimpleDateFormat("YYYY").format(invTrx.getTrxDate())))
                    || !month.equals(Integer.valueOf(new SimpleDateFormat("MM").format(invTrx.getTrxDate())))) {
                // 库存组织name+年月份
                sb.append(invTrx.getTransferOrgName()).append(sdf.format(invTrx.getTrxDate())).append(",");
            }
            if (sb.length() > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The [{}] cost not get.", sdf.format(invTrxs.get(0).getTrxDate()));
                }
                throw new InventoryException(InventoryException.MSG_ERROR_INV_COST_GET_ERROR,
                        new Object[] { sb.deleteCharAt(sb.length() - 1) });
            }
        }
        return true;
    }

    @Override
    public boolean checkDetailNull(IRequest request, Long invOrgId, Integer year, Integer month)
            throws InventoryException {
        int count = costDetailsMapper.queryTotal(invOrgId, year, month);
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CostDetails> genDetailDatas(IRequest request, CostDetails costDetails) throws InventoryException {
        if (costDetails == null || costDetails.getInvOrgId() == null || costDetails.getYear() == null
                || costDetails.getMonth() == null) {
            return null;
        }
        Long invOrgId = costDetails.getInvOrgId();
        Integer year = costDetails.getYear();
        Integer month = costDetails.getMonth();
        // 如果已存在成本明细则删除数据
        costDetailsMapper.deleteCostDetail(costDetails);
        // 通过costOrgId获取组织参数，计算成本方法
        List<String> parm = paramService.getParamValues(request, InventoryConstants.SPM_COST_METHOD,
                SystemProfileConstants.ORG_TYPE_INV, invOrgId);
        if (parm == null || StringUtils.isEmpty(parm.get(0))) {
            if (logger.isDebugEnabled()) {
                logger.debug("The paramValue[{}] not found on the InvOrgId[{}]!", InventoryConstants.SPM_COST_METHOD,
                        invOrgId);
            }
            return null;
        }
        List<CostDetails> costDetailsList = new ArrayList<CostDetails>();
        // 先进先出
        if (InventoryConstants.COST_CAL_FIFO.equals(parm.get(0))) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(costDetails.getYear(), costDetails.getMonth() - 1, 1);
            calendar.add(Calendar.MONTH, -1); // 上个自然月
            // 先进先出
            CostRecords costRecords = new CostRecords();
            costRecords.setInvOrgId(costDetails.getInvOrgId());
            // 库存归集中心
            // costRecords.setCostOrgId(costOrgId);
            costRecords.setYear(calendar.get(Calendar.YEAR));
            costRecords.setMonth(calendar.get(Calendar.MONTH) + 1);
            costRecords.setStatus(InventoryConstants.COST_STATUS_P);
            costRecords.setTrxDateName(String.valueOf(year).concat(String.format("%02d", month)));
            // 1.上月存在本月不存在，取records表数量
            List<CostRecords> costRecordList = costRecordsMapper.getLastRecordsAndNonCurrentTrx(costRecords);
            for (CostRecords costRecord : costRecordList) {
                CostDetails obj = new CostDetails();
                obj.setTrxType(null);
                obj.setInvOrgId(costRecord.getInvOrgId());
                // 库存归集中心Id
                obj.setCostOrgId(invOrgId);
                obj.setItemId(costRecord.getItemId());
                obj.setLotNumber(costRecord.getLotNumber());
                obj.setExpiryDate(costRecord.getExpiryDate());
                obj.setQuantity(costRecord.getQuantity());
                obj.setStatus(InventoryConstants.COST_STATUS_N);
                obj.setYear(year);
                obj.setMonth(month);
                obj.setUnitCost(costRecord.getUnitCost());
                costDetailsList.add(obj);
            }
            // 2.1获取上月records记录
            List<CostRecords> lastCostRecordList = costRecordsMapper.getCostRecordsByParams(costRecords);
            // 2.2获取汇总本月事务处理记录
            List<InvTransaction> currInvTrxList = invTransactionMapper.queryInvTrxQtySumOfFIFO(invOrgId,
                    costRecords.getTrxDateName());
            for (InvTransaction invTransaction : currInvTrxList) {
                boolean tmp = false;
                // 2本月和上月都存在，取records表和transaction表 数量之和
                for (CostRecords costRecord : lastCostRecordList) {
                    if (costRecord.getInvOrgId().equals(invTransaction.getOrganizationId())
                            && costRecord.getItemId().equals(invTransaction.getItemId())
                            && costRecord.getLotNumber().equals(invTransaction.getLotNumber())
                            && costRecord.getExpiryDate().equals(invTransaction.getExpiryDate())) {
                        CostDetails obj = new CostDetails();
                        obj.setTrxType(null);
                        obj.setInvOrgId(costRecord.getInvOrgId());
                        // 库存归集中心Id
                        obj.setCostOrgId(invOrgId);
                        obj.setStatus(InventoryConstants.COST_STATUS_N);
                        obj.setYear(year);
                        obj.setMonth(month);
                        obj.setItemId(costRecord.getItemId());
                        obj.setLotNumber(costRecord.getLotNumber());
                        obj.setExpiryDate(costRecord.getExpiryDate());
                        obj.setQuantity(costRecord.getQuantity().add(invTransaction.getSumQty()));
                        obj.setUnitCost(costRecord.getUnitCost());
                        tmp = true;
                        costDetailsList.add(obj);
                        break;
                    }
                }
                // 上月不存在且本月存在，取transaction表数量
                if (!tmp) {
                    CostDetails obj = new CostDetails();
                    obj.setTrxType(null);
                    obj.setInvOrgId(invTransaction.getOrganizationId());
                    // 库存归集中心Id
                    obj.setCostOrgId(invOrgId);
                    obj.setStatus(InventoryConstants.COST_STATUS_N);
                    obj.setYear(year);
                    obj.setMonth(month);
                    obj.setItemId(invTransaction.getItemId());
                    obj.setLotNumber(invTransaction.getLotNumber());
                    obj.setExpiryDate(invTransaction.getExpiryDate());
                    obj.setQuantity(invTransaction.getSumQty());
                    costDetailsList.add(obj);
                }
            }
        } else if (InventoryConstants.COST_CAL_AVRAG.equals(parm.get(0))) {
            // 加权平均
            List<InvTransaction> trxList = invTransactionMapper.queryCostDetailsOfAvrag(invOrgId, year, month);
            trxList.forEach(invTransaction -> {
                CostDetails obj = new CostDetails();
                obj.setTrxType(invTransaction.getTrxType());
                obj.setInvOrgId(invTransaction.getOrganizationId());
                // 库存归集中心
                obj.setCostOrgId(invOrgId);
                obj.setItemId(invTransaction.getItemId());
                obj.setLotNumber(invTransaction.getLotNumber());
                obj.setExpiryDate(invTransaction.getExpiryDate());
                obj.setQuantity(invTransaction.getTrxQty());
                obj.setStatus(InventoryConstants.COST_STATUS_N);
                obj.setYear(year);
                obj.setMonth(month);
                costDetailsList.add(obj);
            });
        }
        // insert操作
        for (CostDetails detail : costDetailsList) {
            if ("NONE".equals(detail.getLotNumber())) {
                detail.setLotNumber(null);
                detail.setExpiryDate(null);
            }
            costDetailsMapper.insert(detail);
        }
        return costDetailsList;
    }

    @Override
    public List<CostDetails> queryDetailDatas(IRequest request, CostDetails costDetails) {
        if (costDetails == null || costDetails.getInvOrgId() == null || costDetails.getYear() == null
                || costDetails.getMonth() == null) {
            return null;
        }
        // 通过invOrgId获取组织参数，计算成本方法
        List<String> parm = paramService.getParamValues(request, InventoryConstants.SPM_COST_METHOD,
                SystemProfileConstants.ORG_TYPE_INV, costDetails.getInvOrgId());
        if (parm == null || StringUtils.isEmpty(parm.get(0))) {
            if (logger.isDebugEnabled()) {
                logger.debug("The orgParam value not found!paramName:[{}]", InventoryConstants.SPM_COST_METHOD);
            }
            return null;
        }
        List<CostDetails> list = new ArrayList<>();
        if (InventoryConstants.COST_CAL_FIFO.equals(parm.get(0))) {
            list = costDetailsMapper.queryCostDetailsOfFIFO(costDetails);
        } else if (InventoryConstants.COST_CAL_AVRAG.equals(parm.get(0))) {
            costDetails.setTrxType(InventoryConstants.TRANSACTION_TYPE_STOCK_IN);
            list = costDetailsMapper.queryCostDetailsOfAvray(costDetails);
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CostDetails> updateCostDetails(IRequest request, List<CostDetails> costDetails) {
        costDetails.forEach(costDetail -> {
            switch (costDetail.get__status()) {
            case DTOStatus.ADD:
                break;
            case DTOStatus.DELETE:
                break;
            default:
                costDetailsMapper.updateCostDetailsByParams(costDetail);
            }
        });
        return costDetails;
    }

}
