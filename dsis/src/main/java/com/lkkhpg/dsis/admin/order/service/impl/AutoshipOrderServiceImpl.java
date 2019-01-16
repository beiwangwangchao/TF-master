/*
 *
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.service.IAutoshipGiftRuleService;
import com.lkkhpg.dsis.admin.order.service.IAutoshipOrderService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSupplyMapper;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.mapper.MemCardMapper;
import com.lkkhpg.dsis.common.order.dto.AutoshipGiftRule;
import com.lkkhpg.dsis.common.order.dto.AutoshipGifts;
import com.lkkhpg.dsis.common.order.dto.AutoshipLine;
import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.dto.SalesSites;
import com.lkkhpg.dsis.common.order.mapper.AutoshipGiftsMapper;
import com.lkkhpg.dsis.common.order.mapper.AutoshipLineMapper;
import com.lkkhpg.dsis.common.order.mapper.AutoshipOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesSitesMapper;
import com.lkkhpg.dsis.common.service.ICommAutoshipOrderService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 自动订货单查询实现类.
 * 
 * @author HuangJiaJing
 * @author wuyichu
 * 
 */
@Service
@Transactional
public class AutoshipOrderServiceImpl implements IAutoshipOrderService {

    private final Logger logger = LoggerFactory.getLogger(AutoshipOrderServiceImpl.class);

    @Autowired
    private ICommAutoshipOrderService commAutoshipOrderService;
    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private AutoshipLineMapper autoshipLineMapper;
    @Autowired
    private AutoshipOrderMapper autoshipOrderMapper;
    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;
    @Autowired
    private IParamService paramService;
    @Autowired
    private SalesSitesMapper salesSitesMapper;
    @Autowired
    private IAutoshipGiftRuleService autoshipGiftRuleService;
    @Autowired
    private AutoshipGiftsMapper autoshipGiftsMapper;
    @Autowired
    private SpmSupplyMapper spmSupplyMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private MemCardMapper cardMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    
    @Autowired
    private  SpmMarketMapper spmMarketMapper;
    
    @Override
    public List<AutoshipOrder> selectAutoshipOrderParas(IRequest request, AutoshipOrder autoshipOrder, int page,
            int pageSize) {

        if (autoshipOrder.getAutoshipStatus() != null) {
            List<String> statusList = Arrays.asList(autoshipOrder.getAutoshipStatus().split(";"));
            autoshipOrder.setAutoShipStatusList(statusList);
        }
        return commAutoshipOrderService.selectAutoshipOrderParas(request, autoshipOrder, page, pageSize);

    }

    @Override
    @Transactional
    public String executeAutoshipOrders(IRequest request, List<AutoshipOrder> autoshipOrders) {
        String sequence = self().convertAutoshipOrders(request, autoshipOrders);
        if (logger.isDebugEnabled()) {
            logger.debug(sequence);
        }
        // 订单信息生成用于付款的“授权文件”txt，文件与订单信息
        return sequence;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SalesOrder convertAutoshipOrder(final IRequest request, AutoshipOrder autoshipOrder, String batchNum) {
        if (autoshipOrder == null || StringUtils.isEmpty(batchNum)) {
            return null;
        }
        Date now = new Date();
        SalesOrder salesOrder = null;
        autoshipOrder = autoshipOrderMapper.selectByPrimaryKey(autoshipOrder.getAutoshipId());
        MemCard memCard = cardMapper.selectByPrimaryKey(autoshipOrder.getCreditCardId());
        if (memCard == null) {
            return null;
        } else {
            Date expiryDate = memCard.getExpiryDate();
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(expiryDate);

            int nowtime = c1.get(Calendar.YEAR) * 100 + c1.get(Calendar.MONTH);
            int expirytime = c2.get(Calendar.YEAR) * 100 + c2.get(Calendar.MONTH);

            if (nowtime > expirytime) {
                autoshipOrder.setExecuteStatus(OrderConstants.AUTO_AUROSHIP_EXECUTE_STATUS_NCARD);
                self().updateAutoshipOrderStatus(request, autoshipOrder);
                return null;
            }

        }

        try {
            List<AutoshipLine> autoshipLines = autoshipOrder.getLines();
            if (autoshipLines == null || autoshipLines.isEmpty()) {
                autoshipLines = autoshipLineMapper.selectByHeaderId(autoshipOrder.getAutoshipId());
            }
            List<SalesSites> sites = salesSitesMapper.selectSitesByHeaderIdAndAutoshipFlag(OrderConstants.YES,
                    autoshipOrder.getAutoshipId());

            /*-----------------------订单行转换--------------------------------*/
            if (autoshipLines == null || autoshipLines.isEmpty()) {
                return null;
            }
            final SalesOrder result = new SalesOrder();
            final List<SalesLine> saleLines = new ArrayList<SalesLine>();
            // 获取当前销售组织下供货关系默认库存组织
            Long salesOrgId = Long.parseLong(request.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
            List<SpmSupply> spmSupplies = null;
            Long defalutInvOrgId = null;
            SpmSupply criteria = new SpmSupply();
            criteria.setSupplyType(SystemProfileConstants.SUPPLY_TYPE_ORG);
            criteria.setSalesOrgId(salesOrgId);
            spmSupplies = spmSupplyMapper.selectBySpmSupply(criteria);
            for (SpmSupply spmSupply : spmSupplies) {
                if (DeliveryConstants.YES.equals(spmSupply.getDefaultFlag())) {
                    defalutInvOrgId = spmSupply.getInvOrgId();
                }
            }
            BigDecimal totalSumPv = BigDecimal.ZERO;
            for (AutoshipLine autoshipLine : autoshipLines) {
                final SalesLine salesLine = convertOrderLine(request, autoshipLine, defalutInvOrgId);
                if (salesLine != null) {
                    saleLines.add(salesLine);
                    totalSumPv = totalSumPv.add(salesLine.getPv().multiply(salesLine.getQuantity()));

                }
            }
            autoshipOrder.setPvSum(totalSumPv);
            result.setLines(saleLines);
            if (!sites.isEmpty()) {
                for (SalesSites site : sites) {
                    site.setSalesSiteId(null);
                    site.setAutoshipFlag(OrderConstants.NO);
                    site.setHeaderId(null);
                }
            }
            result.setSalesSites(sites);
            /*-----------------------订单头转换--------------------------------*/
            result.setSourceType(OrderConstants.AUTOSHIP);
            result.setSourceKey(autoshipOrder.getAutoshipNumber());
            result.setOrderStatus(OrderConstants.WAIT_PAT);
            result.setSalesOrgId(autoshipOrder.getSalesOrgId());
            result.setOrderDate(now);
            result.setOrderType(OrderConstants.DEFAULT_AUTOSHIP_ORDER_TYPE);
            result.setMemberId(autoshipOrder.getMemberId());
            result.setChannel(OrderConstants.ORDER_CHANNEL_AUTOS);
            result.setCreateUserId(request.getAccountId());
            result.setPvSum(autoshipOrder.getPvSum());
            SimpleDateFormat sdf = new SimpleDateFormat(OrderConstants.DEFAULT_PERIOD_FORMAT);
            result.setPeriod(sdf.format(now));
            if (autoshipOrder.getSalesScore() == null) {
                result.setSalesPoints(BigDecimal.ZERO);
            } else {
                result.setSalesPoints(autoshipOrder.getSalesScore());
            }
            SalesLogistics logistics = salesLogisticsMapper.selectByHeaderId(autoshipOrder.getAutoshipId(),
                    OrderConstants.YES);
            if (null != logistics) {
                logistics.setLogisticsId(null);
                logistics.setHeaderId(null);
                logistics.setAutoshipFlag(OrderConstants.NO);
            }
            result.setLogistics(logistics);
            result.setCurrency(autoshipOrder.getCurrency());
            result.setOrderAmt(autoshipOrder.getOrderAmt());
            result.setDiscountAmt(autoshipOrder.getDiscountAmt());
            result.setTaxAmt(autoshipOrder.getTaxAmt());
            result.setRemark(autoshipOrder.getRemark());
            result.setDeliveryType(autoshipOrder.getDeliveryType());
            result.setCodFlag(OrderConstants.NO);
            // result.setDeliveryLocationId(autoshipOrder.getDeliveryLocationId());
            // result.setDeliveryTo(autoshipOrder.getDeliveryTo());
            // result.setBillingLocationId(autoshipOrder.getBillingLocationId());
            // result.setBillingTo(autoshipOrder.getBillingTo());
            result.setBatchNumber(batchNum);

            // 应用赠品
            self().addAutoShipGift(request, autoshipOrder, result);
            salesOrder = commSalesOrderService.insertOrder(request, result);
        } catch (Exception e) {
            // TODO: handle exception
            if (logger.isDebugEnabled()) {
                logger.debug("autoshiporder id : {} ,convert autoship order to salesorder error,error msg ,{} ",
                        autoshipOrder.getAutoshipId(), e.toString());
            }
            autoshipOrder.setExecuteStatus(OrderConstants.AUTO_AUROSHIP_EXECUTE_STATUS_N);
            self().updateAutoshipOrderStatus(request, autoshipOrder);
            return null;
        }
        autoshipOrder.setExecuteStatus(OrderConstants.AUTO_AUROSHIP_EXECUTE_STATUS_Y);
        self().updateAutoshipOrderStatus(request, autoshipOrder);
        return salesOrder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int updateAutoshipOrderStatus(final IRequest request, AutoshipOrder autoshipOrder) {
        return autoshipOrderMapper.updateByPrimaryKeySelective(autoshipOrder);
    }

    /**
     * autoship订单行转换为销售订单行.
     * 
     * @param request
     *            基础数据请求
     * @param autoshipLine
     *            autoship订单行
     * @return 销售订单行
     */
    @Transactional
    private SalesLine convertOrderLine(final IRequest request, final AutoshipLine autoshipLine, Long defaultInvOrgId) {
        if (autoshipLine == null) {
            return null;
        }
        final SalesLine result = new SalesLine();
        result.setItemId(autoshipLine.getItemId());
        result.setUnitOrigPrice(autoshipLine.getUnitOrigPrice());
        result.setUnitDiscountPrice(autoshipLine.getUnitDiscountPrice());
        result.setUnitSellingPrice(autoshipLine.getUnitSellingPrice());
        result.setItemSalesType(autoshipLine.getItemSalesType());
        result.setPv(autoshipLine.getPv());
        result.setQuantity(autoshipLine.getQuantity());
        result.setAmount(autoshipLine.getAmount());
        result.setSourceType(OrderConstants.AUTOSHIP);
        result.setSourceKey(String.valueOf(autoshipLine.getLineId()));
        result.setStatus(OrderConstants.AUTOSHIP_STATUS_ACV);
        result.setUnitRedeemPoint(autoshipLine.getUnitRedeemPoint());
        result.setRedeemPoint(autoshipLine.getRedeemPoint());
        result.setVoucherId(autoshipLine.getVoucherId());
        result.setSalesOrgId(autoshipLine.getSalesOrgId());
        result.setUomCode(autoshipLine.getUomCode());
        // TODO 默认库存组织设置
        result.setDefaultInvOrgId(defaultInvOrgId);
        return result;
    }

    @Override
    @Transactional
    public String convertAutoshipOrders(final IRequest request, List<AutoshipOrder> autoshipOrders) {
        if (autoshipOrders == null || autoshipOrders.isEmpty()) {
            return null;
        }
        String result = commSalesOrderService.generateCode(request, SequenceType.AUTOSHIPBATCH, null);
        List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
        for (AutoshipOrder autoshipOrder : autoshipOrders) {

            final SalesOrder salesOrder = self().convertAutoshipOrder(request, autoshipOrder, result);
            if (salesOrder != null) {
                salesOrders.add(salesOrder);
            }
        }
        int totalNum = autoshipOrders.size();
        int successNum = salesOrders.size();
        result = result + ":" + successNum + ":" + (totalNum - successNum);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AutoshipOrder submitAutoshipOrder(final IRequest request, final AutoshipOrder autoshipOrder)
            throws CommOrderException {
        validateAutoshipOrder(request, autoshipOrder);
        return commAutoshipOrderService.submitAutoshipOrder(request, autoshipOrder);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateOrderStatus(IRequest request, Long autoShipId, String status) throws CommOrderException {

        return commAutoshipOrderService.updateOrderStatus(request, autoShipId, status);
    }

    @Override
    public AutoshipOrder getDetail(IRequest request, Long autoshipId) {
        return commAutoshipOrderService.getDetail(request, autoshipId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteLine(IRequest request, List<AutoshipLine> lines) {
        commAutoshipOrderService.deleteLine(request, lines);

    }

    @Override
    public List<AutoshipOrder> checkMemberAutoship(IRequest request, Long memberId) {
        Long autoshipId = commAutoshipOrderService.checkMemberAutoship(request, memberId);
        List<AutoshipOrder> result = new ArrayList<AutoshipOrder>();
        if (autoshipId != null) {
            AutoshipOrder autoshipOrder = new AutoshipOrder();
            autoshipOrder.setAutoshipId(autoshipId);
            result.add(autoshipOrder);
        }
        return result;
    }

    @Override
    public boolean validateAutoshipOrder(IRequest request, AutoshipOrder autoshipOrder) throws CommOrderException {
        // 会员ID 不能为空
        if (autoshipOrder.getMemberId() == null) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR, null);
        }

        // 配货类型为物流配送，收货地址不能为空,物流信息不能为空
        if (OrderConstants.ORDER_DELIVERY_SHIPP.equals(autoshipOrder.getDeliveryType())) {
            // 收货地址为空
            // if (autoshipOrder.getSalesSites() == null) {
            // throw new
            // CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR,
            // null);
            // }

            // 物流信息不能为空
            if (autoshipOrder.getLogistics() == null) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR, null);
            }
        }

        // 信用卡信息不能为空
        if (autoshipOrder.getCreditCardId() == null) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR, null);
        }

        // 销售区域是不是当前销售区域
      /*  if (!request.getAttribute(SystemProfileConstants.SALES_ORG_ID).equals(autoshipOrder.getSalesOrgId())
                || autoshipOrder.getSalesOrgId() == null) {

            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
                    new Object[] { request, autoshipOrder });
        }*/
        // 本位币校验
        List<String> currencyParams = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, autoshipOrder.getSalesOrgId());
        if (currencyParams == null || currencyParams.isEmpty()
                || !currencyParams.iterator().next().equals(autoshipOrder.getCurrency())
                || autoshipOrder.getCurrency() == null) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BASIC_CHECK_ERROR,
                    new Object[] { request, autoshipOrder });
        }
        // 下单金额不能小于库存组织维护的最小下单金额
        List<String> autoshipMinSumParams = paramService.getParamValues(request,
                SystemProfileConstants.PARAM_AUTOSHIP_MIN_SUM, SystemProfileConstants.ORG_TYPE_SALES,
                autoshipOrder.getSalesOrgId());
        if (autoshipMinSumParams != null && !autoshipMinSumParams.isEmpty()) {
            String autoshipMinSum = autoshipMinSumParams.iterator().next();
            BigDecimal minFee = new BigDecimal(autoshipMinSum);
            // 订单金额
            BigDecimal orderamt = autoshipOrder.getOrderAmt();
            // 运费
            BigDecimal shippingFee = autoshipOrder.getLogistics().getShippingFee();
            // 总金额
            BigDecimal totalFee = orderamt.add(shippingFee);

            if (totalFee.compareTo(minFee) < 0) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AUTOSHIP_MIN_SUM,
                        new Object[] { request, autoshipOrder });
            }
        }
        // 最小pv 值

        List<String> autoshipMinPvParams = paramService.getParamValues(request, SystemProfileConstants.AUTOSHIP_MIN_PV,
                SystemProfileConstants.ORG_TYPE_SALES, autoshipOrder.getSalesOrgId());
        if (autoshipMinPvParams != null && !autoshipMinPvParams.isEmpty()) {
            BigDecimal autoshipMinPv = new BigDecimal(autoshipMinPvParams.iterator().next());

            /*
             * if (autoshipOrder.getPvSum().compareTo(autoshipMinPv) < 0) {
             * throw new CommOrderException(CommOrderException.
             * MSG_ERROR_OM_AUTOSHIP_MIN_PV, new Object[] { request,
             * autoshipOrder }); }
             */
        }

        // 订单商品行不能为空
        List<AutoshipLine> autoshipLines = autoshipOrder.getLines();
        if (autoshipLines == null || autoshipLines.size() < 1) {
            throw new CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR, null);
        }

        // 验证订单行
        for (AutoshipLine autoshipLine : autoshipLines) {
            // 订单行商品不能为空
            if (autoshipLine.getItemId() == null) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_AUTOSHIPORDER_VALIDATE_ERROR, null);
            }
            // TODO 优惠劵验证
        }
        return true;
    }

    @Override
    public SalesOrder addAutoShipGift(IRequest request, AutoshipOrder autoshipOrder, SalesOrder salesOrder) {
        // TODO Auto-generated method stub
        Long salesOrgId = autoshipOrder.getSalesOrgId();
        if (salesOrgId == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("autoshiporder (id : {} )market id is null ,", autoshipOrder.getAutoshipId());
            }
            return salesOrder;
        }
        AutoshipGiftRule giftRule = autoshipGiftRuleService.queryAutoshipGiftRuleBySalesOrgId(request, salesOrgId);
        if (giftRule == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("market (id : {}) cant find giftRule ", autoshipOrder.getAutoshipId());
            }
            return salesOrder;
        }
        // 计算类型
        if (OrderConstants.CALCULATION_TYPE_PV.equals(giftRule.getCalculationType())) {
            if (autoshipOrder.getPvSum().compareTo(new BigDecimal(giftRule.getCalculationValue())) >= 0) {
                // 设置Y
                salesOrder.setAutoshipGiftRuleFlag(OrderConstants.AUTOSHIP_GIFT_FLAG_Y);
            } else {
                salesOrder.setAutoshipGiftRuleFlag(OrderConstants.AUTOSHIP_GIFT_FLAG_N);
            }
        } else if (OrderConstants.CALCULATION_TYPE_AMOUN.equals(giftRule.getCalculationType())) {
            if (autoshipOrder.getOrderAmt().compareTo(new BigDecimal(giftRule.getCalculationValue())) > 0) {
                salesOrder.setAutoshipGiftRuleFlag(OrderConstants.AUTOSHIP_GIFT_FLAG_Y);
            } else {
                salesOrder.setAutoshipGiftRuleFlag(OrderConstants.AUTOSHIP_GIFT_FLAG_N);
            }
        }
        Long memberId = autoshipOrder.getMemberId();

        // 当前月份- 启用月份 >连续月份
        Calendar now = Calendar.getInstance();
        Long month = giftRule.getMonth();
        Integer activeMonth = Integer.valueOf(giftRule.getActiveMonth());
        int currentMonth = now.get(Calendar.YEAR) * 100 + now.get(Calendar.MONTH) + 1;
        if ((currentMonth - activeMonth.intValue()) < month.intValue()) {
            if (logger.isDebugEnabled()) {
                logger.debug(
                        "The current month:({}) minus the activation month : ({}) must be greater than that of the month :({})",
                        currentMonth, activeMonth, month);
            }
            return salesOrder;
        }

        // 结束时间 当月的第一天零时零分零秒
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        Date endTime = now.getTime();

        now.add(Calendar.MONTH, -month.intValue());

        Date startTime = now.getTime();

        Long count = commSalesOrderService.queryNumberwithGiftRule(request, memberId, startTime, endTime);
        if (count != month || OrderConstants.AUTOSHIP_GIFT_FLAG_N.equals(salesOrder.getAutoshipGiftRuleFlag())) {
            return salesOrder;
        } else {
            // 符合赠品规则
            // 查询出规则下维护的商品
            salesOrder.setAutoshipGiftFlag(OrderConstants.AUTOSHIP_GIFT_FLAG_I);

            List<AutoshipGifts> gifts = autoshipGiftsMapper.selectByRuleId(giftRule.getRuleId());
            for (AutoshipGifts gift : gifts) {
                Date nowDate = new Date();
                if (nowDate.before(gift.getStartActiveDate()) || nowDate.after(gift.getEndActiveDate())) {
                    continue;
                }

                final SalesLine result = new SalesLine();
                result.setItemId(gift.getItemId());
                result.setUnitOrigPrice(BigDecimal.ZERO);
                result.setUnitDiscountPrice(BigDecimal.ZERO);
                result.setUnitSellingPrice(BigDecimal.ZERO);
                result.setItemSalesType(OrderConstants.LINE_SALETYPE_FREE);
                result.setPv(BigDecimal.ZERO);
                result.setQuantity(new BigDecimal(gift.getQuantity()));
                result.setAmount(BigDecimal.ZERO);
                result.setSourceType(OrderConstants.AUTOSHIP);
                // result.setSourceKey(String.valueOf(autoshipLine.getLineId()));
                result.setStatus(OrderConstants.AUTOSHIP_STATUS_ACV);
                result.setUnitRedeemPoint(BigDecimal.ZERO);
                result.setRedeemPoint(BigDecimal.ZERO);
                // result.setVoucherId(BigDecimal.ZERO);
                result.setSalesOrgId(autoshipOrder.getSalesOrgId());
                result.setUomCode(gift.getUomCode());
                salesOrder.getLines().add(result);
            }
            // 更新订单赠品规则状态
            commSalesOrderService.updateSalesOrderGiftRuleFlag(request, memberId, startTime, endTime);
        }

        return salesOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelAutoshipOrder() {
        // TODO Auto-generated method stub
        List<SalesOrder> salesOrders = commSalesOrderService.queryAutoshipWpayOrder();
        for (SalesOrder salesOrder : salesOrders) {
            String sourceKey = salesOrder.getSourceKey();
            if (sourceKey != null) {
                AutoshipOrder autoshipOrder = autoshipOrderMapper.selectByAutoshipNumber(sourceKey);
                if (autoshipOrder != null) {
                    autoshipOrder.setAutoshipStatus(OrderConstants.AUTOSHIP_STATUS_TER);
                    autoshipOrderMapper.updateByPrimaryKey(autoshipOrder);
                }
            }
            salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_CANCELED);
            salesOrderMapper.updateByPrimaryKey(salesOrder);
        }

    }

    /* (non-Javadoc)
     * @see com.lkkhpg.dsis.admin.order.service.IAutoshipOrderService#getBasicDataForCreate(com.lkkhpg.dsis.platform.core.IRequest, com.lkkhpg.dsis.common.order.dto.AutoshipOrder)
     */
    @Override
    public Map<String, Object> getBasicDataForCreate(IRequest request, AutoshipOrder autoshipOrder) throws OrderException {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        // BASIC-SALES-ORGS
        SpmSalesOrganization salesOrg = new SpmSalesOrganization();
        if (autoshipOrder != null) {
            if (autoshipOrder.getAutoshipId() != null) {
                AutoshipOrder order = autoshipOrderMapper.selectByPrimaryKey(autoshipOrder.getAutoshipId());
                if (order != null) {
                    salesOrg.setSalesOrgId(order.getSalesOrgId());
                } else {
                    salesOrg.setSalesOrgId(request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
                }
            } else if (autoshipOrder.getSalesOrgId() != null) {
                salesOrg.setSalesOrgId(autoshipOrder.getSalesOrgId());
            } else {
                salesOrg.setSalesOrgId(request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            }
        } else {
            salesOrg.setSalesOrgId(request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        }
        List<SpmSalesOrganization> salesOrgs = spmSalesOrganizationMapper.queryBySalesOrganization(salesOrg);
        if (salesOrgs.isEmpty()) {
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        }
        salesOrg = salesOrgs.iterator().next();
        result.put(OrderConstants.BASIC_SALES_ORG, salesOrg);
        // BASIC-MARKET
        SpmMarket market = new SpmMarket();
        market.setMarketId(salesOrg.getMarketId());
        List<SpmMarket> markets = spmMarketMapper.queryByMarket(market);
        if (markets.isEmpty()) {
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        }
        market = markets.iterator().next();
        Long igiMarketId = spmMarketMapper.selectIGIMappingMarket(market.getMarketId());
        if (igiMarketId != null) {
            market.setIgiMarketId(igiMarketId);
        }
        result.put(OrderConstants.BASIC_MARKET, market);
        
        List<String> currencies = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrg.getSalesOrgId());
        if (currencies != null && !currencies.isEmpty()) {
            result.put(OrderConstants.BASIC_CURRENCY, currencies.iterator().next());
        } else {
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        }
        return result;
    }
}
