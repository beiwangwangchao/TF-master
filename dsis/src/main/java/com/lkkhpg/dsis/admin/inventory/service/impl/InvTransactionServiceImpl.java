/*
 *
 */

package com.lkkhpg.dsis.admin.inventory.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.platform.dto.system.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.admin.inventory.service.IInvDeductionService;
import com.lkkhpg.dsis.admin.inventory.service.IInvTransactionService;
import com.lkkhpg.dsis.admin.product.product.service.IInvItemPropertyService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmInvOrganizationMapper;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.inventory.dto.InvLot;
import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.InvTransaction;
import com.lkkhpg.dsis.common.inventory.dto.InvTransactionQuery;
import com.lkkhpg.dsis.common.inventory.mapper.InvLotMapper;
import com.lkkhpg.dsis.common.inventory.mapper.InvTransactionMapper;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.mapper.InvCategoryBMapper;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.service.IInvOnhandQuantityService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 库存事务处理程序.
 * 
 * @author shenqb
 *
 */
@Service
@Transactional
public class InvTransactionServiceImpl implements IInvTransactionService {

    private Logger logger = LoggerFactory.getLogger(InvTransactionServiceImpl.class);

    @Autowired
    private InvTransactionMapper invTransactionMapper;

    @Autowired
    private InvItemMapper invItemMapper;

    @Autowired
    private InvLotMapper invLotMapper;

    @Autowired
    private SpmInvOrganizationMapper spmInvOrganizationMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IInvOnhandQuantityService invOnhandQuantityService;

    @Autowired
    private IInvDeductionService invDeductionService;

    @Autowired
    private IInvItemPropertyService invItemPropertyService;
    
    @Autowired
    private InvCategoryBMapper invCategoryBMapper;

    @Autowired
    private SpmInvOrganizationMapper invOrganizationMapper;

    @Override
    public List<Map<String, Object>> queryOrganization(IRequest request) {
        Long userId = (Long) request.getAttribute(User.FILED_USER_ID);
        List<Map<String, Object>> organizations;
                //invTransactionMapper.queryOrgsByRole();
        if(userId==1)
            organizations=invTransactionMapper.getOrganization1();
        else  organizations = invTransactionMapper.getOrganization();

        return organizations;
    }

    @Override
    public List<InvTransaction> queryInvTransactions(IRequest request, int page, int pagesize,
            InvTransactionQuery invTransactionQuery, Long invOrgId) {
        PageHelper.startPage(page, pagesize);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("organizationId", invTransactionQuery.getOrganizationId());
        map.put("itemNumber", invTransactionQuery.getItemNumber());
        map.put("itemName", invTransactionQuery.getItemName());
        map.put("expiryDateFrom", invTransactionQuery.getExpiryDateFrom());
        map.put("expiryDateTo", invTransactionQuery.getExpiryDateTo());
        map.put("trxDateFrom", invTransactionQuery.getTrxDateFrom());
        map.put("trxDateTo", invTransactionQuery.getTrxDateTo());
        map.put("lotNumber", invTransactionQuery.getLotNumber());
        map.put("trxType", invTransactionQuery.getTrxType());
        map.put("transferOrgId", invTransactionQuery.getTransferOrgId());
        map.put("trxNumber", invTransactionQuery.getTrxSourceReference());
        List<InvTransaction> invTransactions = invTransactionMapper.getTransactions(map);
        return invTransactions;
    }

    @Override
    public List<InvItem> queryItemNumbers(IRequest request, int page, int pagesize, String codeCondition,
            String conditionType) {
        PageHelper.startPage(page, pagesize);
        Map<String, Object> map = new HashMap<>();

        map.put("codeCondition", codeCondition);
        map.put("conditionType", conditionType);
        List<InvItem> invItems = invItemMapper.queryItemsByParams(map);
        return invItems;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<InvTransaction> processTransaction(IRequest request, @StdWho List<InvTransaction> invTransactions)
            throws InventoryException {
        for (InvTransaction invTransaction : invTransactions) {

            // 组织校验
            if (spmInvOrganizationMapper.selectByPrimaryKey(invTransaction.getOrganizationId()) == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("OrganizationId not exist with: {}",
                            new Object[] { invTransaction.getOrganizationId() });
                }

                throw new InventoryException(InventoryException.MSG_ERROR_ORGANIZATION_INVALID, null);
            }

            // 当前不启用子库货位，默认为1
            invTransaction.setSubinventoryId(1L);
            invTransaction.setLocatorId(1L);

            InvItem invItem = invItemMapper.selectByPrimaryKey(invTransaction.getItemId());

            // 商品校验
            if (invItem == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("ItemId not exist with: {}", new Object[] { invTransaction.getItemId() });
                }

                throw new InventoryException(InventoryException.MSG_ERROR_ITEM_INVALID, null);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Validate item ok, itemNumber: {}", new Object[] { invItem.getItemNumber() });
            }

            // 设置商品主单位
            invTransaction.setUomCode(invItem.getUomCode());
            if (logger.isDebugEnabled()) {
                logger.debug("Validate uomCode ok, uomCode: {}", new Object[] { invItem.getUomCode() });
            }

            // 数量校验
            if (invTransaction.getTrxQty() == null || BigDecimal.ZERO.equals(invTransaction.getTrxQty())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("TrxQty invalid: {}", new Object[] { invTransaction.getTrxQty() });
                }

                throw new InventoryException(InventoryException.MSG_ERROR_QTY_INVALID, null);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Validate trxQty ok, TrxQty: {}", new Object[] { invTransaction.getTrxQty() });
            }

            // 事务处理类型校验
            CodeValue trxType = codeService.getCodeValue(request, InventoryConstants.TRX_TYPE,
                    invTransaction.getTrxType());
            if (trxType == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("TrxType invalid: {}", new Object[] { invTransaction.getTrxType() });
                }

                throw new InventoryException(InventoryException.MSG_ERROR_TRX_TYPE_INVALID, null);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Validate trxType ok, TrxType: {}", new Object[] { invTransaction.getTrxType() });
                logger.debug("Validate trxType ok, TrxTypeDesc: {}", new Object[] { trxType.getMeaning() });
            }

            // 事务处理来源类型校验
            CodeValue trxSourceType = codeService.getCodeValue(request, InventoryConstants.TRX_SOURCE_TYPE,
                    invTransaction.getTrxSourceType());
            if (trxSourceType == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("TrxSourceType invalid: {}", new Object[] { invTransaction.getTrxSourceType() });
                }

                throw new InventoryException(InventoryException.MSG_ERROR_TRX_SOURCE_TYPE_INVALID, null);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Validate trxSourceType ok, TrxSourceType: {}",
                        new Object[] { invTransaction.getTrxSourceType() });
                logger.debug("Validate trxSourceType ok, TrxSourceTypeDesc: {}",
                        new Object[] { trxSourceType.getMeaning() });
            }

            // 事务处理来源键值校验
            if (invTransaction.getTrxSourceKey() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("TrxSourceType invalid: {}", new Object[] { invTransaction.getTrxSourceKey() });
                }

                throw new InventoryException(InventoryException.MSG_ERROR_TRX_SOURCE_KEY_INVALID, null);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Validate trxSourceKey ok, TrxSourceKey: {}",
                        new Object[] { invTransaction.getTrxSourceKey() });
            }

            // 启用批次控制
            if (invItemPropertyService.isLotControl(request, invTransaction.getOrganizationId(),
                    invTransaction.getItemId())) {
                if (invTransaction.getLotNumber() == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Lot number required");
                    }
                    throw new InventoryException(InventoryException.MSG_ERROR_LOT_REQUIRED, null);
                }

                InvLot lotQry = new InvLot();
                lotQry.setOrganizationId(invTransaction.getOrganizationId());
                lotQry.setItemId(invTransaction.getItemId());
                lotQry.setLotNumber(invTransaction.getLotNumber());
                List<InvLot> lots = invLotMapper.selectInvLot(lotQry);

                if (lots.size() == 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Lot number not exist");
                    }
                    throw new InventoryException(InventoryException.MSG_ERROR_LOT_INVALID, null);

                    // 批次Number唯一
                } /*else if (BaseConstants.NO.equals(lots.get(0).getEnabledFlag())) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Lot number was disabled, lotNumber {}",
                                new Object[] { lots.get(0).getLotNumber() });
                    }
                    throw new InventoryException(InventoryException.MSG_ERROR_LOT_DISABLED, null);
                }*/

                // 不启用批次控制时批次属性要为空
            } else {
                if (invTransaction.getLotNumber() != null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Lot control flag is N");
                    }
                    throw new InventoryException(InventoryException.MSG_ERROR_LOT_MUST_NULL, null);
                }
            }

            // 创建库存事物处理
            invTransactionMapper.insert(invTransaction);
            
            // 判断商品是否计算库存
            if (invItemPropertyService.isCountStack(request, invTransaction.getOrganizationId(),
                    invTransaction.getItemId())) {
                if (invTransaction.getTrxQty().compareTo(BigDecimal.ZERO) >= 0) {
                    // 创建库存量
                    InvOnhandQuantity invOnhandQuantity = new InvOnhandQuantity();
                    invOnhandQuantity.setOrganizationId(invTransaction.getOrganizationId());
                    invOnhandQuantity.setSubinventoryId(invTransaction.getSubinventoryId());
                    invOnhandQuantity.setLocationId(invTransaction.getLocatorId());
                    invOnhandQuantity.setItemId(invTransaction.getItemId());
                    invOnhandQuantity.setLotNumber(invTransaction.getLotNumber());
                    invOnhandQuantity.setUomCode(invTransaction.getUomCode());
                    invOnhandQuantity.setQuantity(invTransaction.getTrxQty());
                    invOnhandQuantity.setInitTransactionId(invTransaction.getTrxId());
                    invOnhandQuantity.setLastTransactionId(invTransaction.getTrxId());
                    invOnhandQuantity.setQuantity(invTransaction.getTrxQty());
                    invOnhandQuantityService.createInvOnhandQuantity(request, invOnhandQuantity);

                } else {
                    // 调用现有量扣减程序
                    invDeductionService.process(request, invTransaction);
                }
            }
            

        }

        return invTransactions;
    }
    
    @Override
    public List<SpmInvOrganization> getCurrentOrganization(IRequest request) {
 
        Long invOrgId = (Long) request.getAttribute(SystemProfileConstants.INV_ORG_ID);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("invOrgId", invOrgId);
        List<SpmInvOrganization> currentOrg = spmInvOrganizationMapper.getCurrentOrganization(map);
        return currentOrg;
      
    }

    @Override
    public List<InvItem> queryItemsByOrgId(IRequest request, InvItem item, int page, int pagesize) {
        // 进行分页
        PageHelper.startPage(page, pagesize);
        return invItemMapper.queryItemsByOrgId(item);
    }
    
    @Override
    public List<InvCategoryB> queryCategorysByInvOrgId(IRequest request, Long invOrgId) {
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("invOrgId", invOrgId);
          List<InvCategoryB> invCategorys = invCategoryBMapper.queryCategorysByInvOrgId(map);
          return invCategorys;
    }
}