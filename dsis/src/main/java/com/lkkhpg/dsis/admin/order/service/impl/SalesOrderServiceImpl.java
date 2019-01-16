/*
 *
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import com.lkkhpg.dsis.admin.integration.dapp.service.IAddOrderCallbackService;
import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.pojo.SalesOrderDetail;
import com.lkkhpg.dsis.admin.order.service.ISalesOrderService;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.*;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxQueryService;
import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.*;
import com.lkkhpg.dsis.common.order.mapper.*;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.service.*;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageAssignMapper;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageMapper;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.mapper.system.CodeValueMapper;
import com.lkkhpg.dsis.platform.service.IAccessService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 销售订单服务接口具体实现.
 * 
 * @author wuyichu
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements ISalesOrderService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private SalesLineMapper salesLineMapper;
    @Autowired
    private SalesAdjustmentMapper salesadjustMentMapper;
    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IAddOrderCallbackService addOrderCallbackService;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private ISpmPeriodService spmPeriodService;
    @Autowired
    private IAccessService accessService;
    @Autowired
    private SalesSitesMapper salesSitesMapper;
    @Autowired
    private CodeValueMapper codeValueMapper;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    SpmInvOrganizationMapper spmInvOrganizationMapper;

    @Autowired
    SpmCompanyMapper spmCompanyMapper;

    @Autowired
    QueryOrderMapper queryOrderMapper;

    @Autowired
    private SysMsMessageMapper msMessageMapper;

    @Autowired
    private SysMsMessageAssignMapper sysMsMessageAssignMapper;

    @Autowired
    private IManualMessageService manualMessageService;

    @Autowired
    private SysMsMessageAssignMapper msMessageAssignMapper;

    @Autowired
    private IDiscountTrxQueryService discountTrxQueryService;

    @Autowired
    private IDiscountTrxHeadService discountTrxHeadService;
    @Autowired
    private ReportSalesMapper reportSalesMapper;

    @Override
    public List<SalesOrder> queryOrders(IRequest request, SalesOrder salesOrder, int page, int pageSize) {
        return commSalesOrderService.queryOrders(request, salesOrder, page, pageSize);
    }

    @Override
    public List<ReportSales> reportSales(IRequest request, ReportSales reportSales, int page, int pageSize) {
        return reportSalesMapper.reportSales(reportSales);
    }

    @Override
    public List<SalesOrder> queryOrdersId(IRequest request, SalesOrder salesOrder) {
        return commSalesOrderService.queryOrdersId(request, salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder insertOrder(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        return commSalesOrderService.insertOrder(request, salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteOrder(IRequest request, SalesOrder salesOrder) {
        if (salesOrder == null || salesOrder.getHeaderId() == null) {
            if (log.isDebugEnabled()) {
                log.debug("order is null or orderId is null");
            }
            return;
        }
        salesLineMapper.deleteByHeadId(salesOrder.getHeaderId());
        salesadjustMentMapper.deleteByHeaderId(salesOrder.getHeaderId());
        salesSitesMapper.deleteByHeaderIdAndAutoshipFlag(OrderConstants.NO, salesOrder.getHeaderId());
        if (salesOrder.getSourceType() == null) {
            salesLogisticsMapper.deleteByHeaderId(salesOrder.getHeaderId(), OrderConstants.NO);
        }
        salesOrderMapper.deleteByPrimaryKey(salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder updateOrder(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        return commSalesOrderService.updateOrder(request, salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder updateOrderBySelective(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        return commSalesOrderService.updateOrderBySelective(request, salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder submitOrder(IRequest request, SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException {
        return commSalesOrderService.submitOrder(request, salesOrder);
    }

    /**
     * 获取订单的详细信息.
     * 
     * @param request
     *            请求基础数据
     * @param headId
     *            订单头id
     * @return 查询不到时返回null.
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SalesOrder getDetailOrder(IRequest request, Long headId) {
        return commSalesOrderService.getDetailOrder(request, headId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteLine(IRequest request, List<SalesLine> lines) {
        commSalesOrderService.deleteLine(request, lines);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SalesLine> calculateLinePrice(IRequest request, final List<SalesLine> lines,
            final SalesOrder salesOrder) throws CommOrderException {
        return commSalesOrderService.calculateLinePrice(request, lines, salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesOrder calculateOrderPrice(IRequest request, SalesOrder order) throws CommOrderException {
        return commSalesOrderService.calculateOrderPrice(request, order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SalesLine calculateLinePrice(final IRequest request, final SalesLine line, final SalesOrder salesOrder,
            final Set<Long> allowInvOrgs, final Set<String> allowSaleTypes, final InvItem invItemParams)
            throws CommOrderException {
        return commSalesOrderService.calculateLinePrice(request, line, salesOrder, allowInvOrgs, allowSaleTypes,
                invItemParams);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BigDecimal caculateAdjustMents(IRequest request, List<SalesAdjustment> adjustMents)
            throws CommOrderException {
        return commSalesOrderService.caculateAdjustMents(request, adjustMents);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String generateCode(IRequest request, SequenceType sequenceType, Object param) {
        return commSalesOrderService.generateCode(request, sequenceType, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SalesOrder> batchInsert(IRequest request, List<SalesOrder> salesOrders)
            throws CommOrderException, CommVoucherException {
        return commSalesOrderService.batchInsert(request, salesOrders);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateOrderStatus(IRequest request, Long headerId, String orderStatus) {
        return commSalesOrderService.updateOrderStatus(request, headerId, orderStatus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void invalidOrderToRemaining(IRequest request, Long headerId, String remark)
            throws CommMemberException, CommVoucherException {
        commSalesOrderService.invalidOrderToRemaining(request, headerId, remark);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void invalidOrderToRefund(IRequest request, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException, CommMemberException, CommVoucherException {
        commSalesOrderService.invalidOrderToRefund(request, headerId, paymentRefunds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateInvoiceNumberByHeaderId(IRequest request, String invoiceNumber, Long headerId)
            throws CommOrderException {
        return commSalesOrderService.updateInvoiceNumberByHeaderId(request, invoiceNumber, headerId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteOrderList(IRequest request, List<SalesOrder> salesOrders) {
        for (SalesOrder temp : salesOrders) {
            self().deleteOrder(request, temp);
        }
    }

    @Override
    public void deleteAdjustment(IRequest request, Long adjustmentId) {
        commSalesOrderService.deleteAdjustment(request, adjustmentId);
    }

    @Override
    public Map<String, Object> getBasicDataForCreate(IRequest request, SalesOrderDetail salesOrderDetail)
            throws OrderException {
        Map<String, Object> result = new HashMap<String, Object>();
        // BASIC-SALES-ORGS
        SpmSalesOrganization salesOrg = new SpmSalesOrganization();
        if (salesOrderDetail != null) {
            if (salesOrderDetail.getOrderId() != null) {
                SalesOrder order = salesOrderMapper.selectByPrimaryKey(salesOrderDetail.getOrderId());
                if (order != null) {
                    salesOrg.setSalesOrgId(order.getSalesOrgId());
                } else {
                    salesOrg.setSalesOrgId(request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
                }
            } else if (salesOrderDetail.getSalesOrgId() != null) {
                salesOrg.setSalesOrgId(salesOrderDetail.getSalesOrgId());
            } else {
                salesOrg.setSalesOrgId(request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            }
        } else {
            salesOrg.setSalesOrgId(request.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        }
        List<SpmSalesOrganization> salesOrgs = spmSalesOrganizationMapper.queryBySalesOrganization(salesOrg);
        if (salesOrgs.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("get create order basic data--salesOrg error {}", request);
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        }
        salesOrg = salesOrgs.iterator().next();
        result.put(OrderConstants.BASIC_SALES_ORG, salesOrg);
        // BASIC-MARKET
        SpmMarket market = new SpmMarket();
        market.setMarketId(salesOrg.getMarketId());
        List<SpmMarket> markets = spmMarketMapper.queryByMarket(market);
        if (markets.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("get create order basic data--market error {}", request);
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        }
        market = markets.iterator().next();
        Long igiMarketId = spmMarketMapper.selectIGIMappingMarket(market.getMarketId());
        if (igiMarketId != null) {
            market.setIgiMarketId(igiMarketId);
        }
        result.put(OrderConstants.BASIC_MARKET, market);

        /*
         * List<SpmInvOrganization> spmInvOrganizations =
         * spmInvOrganizationMapper
         * .getSupplyInvOrgsBySalesOrg(salesOrg.getSalesOrgId());
         * result.put(OrderConstants.BASIC_INV_ORGS, spmInvOrganizations);
         */

        // BASIC-CURRENCY
        List<String> currencies = paramService.getParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrg.getSalesOrgId());
        if (currencies != null && !currencies.isEmpty()) {
            result.put(OrderConstants.BASIC_CURRENCY, currencies.iterator().next());
        } else {
            if (log.isErrorEnabled()) {
                log.error("get create order basic data--currency error {}", request);
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        }

        // 不需要查询会员信息，则直接返回
        if (salesOrderDetail == null || salesOrderDetail.getMemberId() == null) {
            return result;
        }
        Member member = memberMapper.selectByPrimaryKey(salesOrderDetail.getMemberId());
        if (member == null) {
            if (log.isErrorEnabled()) {
                log.error("get create order basic data--member error {}", request);
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_ORG_MISS, new Object[] { request });
        } else {
            if (MemberConstants.MEMBER_TYPE_COMP.equals(member.getMemberType())) {
                member.setEnglishName(member.getRefCompanyEn());
                member.setChineseName(member.getRefCompany());
            }
            result.put(OrderConstants.BASIC_MEMBER, member);
        }
        return result;
    }

   
    @Override
    public Map<String, Object> getBasicDataForConfirm(IRequest request, SalesOrderDetail salesOrderDetail)
            throws OrderException {
        
        Map<String, Object> result = new HashMap<String, Object>();
        if (salesOrderDetail.getOrderId() != null) {
            SalesOrder order = salesOrderMapper.selectByPrimaryKey(salesOrderDetail.getOrderId());
            if (order != null) {
                result.put(OrderConstants.BASIC_SALES_ORG, order.getSalesOrgId());
                
                SpmMarket market = spmMarketMapper.selectBySalesOrgId(order.getSalesOrgId());
                if(market != null){
                   result.put(OrderConstants.BASIC_MARKET, market.getCode());
                }
            } 
        }
        
        return result;
    }
    
 
    @Override
    public Map<String, Object> getBasicDataForDetail(IRequest request, SalesOrderDetail salesOrderDetail)
            throws OrderException {
        Map<String, Object> result = new HashMap<String, Object>();
        if (salesOrderDetail.getOrderId() != null) {
            SalesOrder order = salesOrderMapper.selectByPrimaryKey(salesOrderDetail.getOrderId());
            if (order != null) {
                result.put(OrderConstants.BASIC_SALES_ORG, order.getSalesOrgId());
            } 
        }
        
        return result;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void dappSync(IRequest request, SalesOrder order) {
        try {
            if (null != order.getHeaderId()) {
                order = salesOrderMapper.selectByPrimaryKey(order.getHeaderId());
            }
            if (!"SAV".equals(order.getOrderStatus()) && !"WPAY".equals(order.getOrderStatus())) {
                addOrderCallbackService.updateSyncFlag(request, order);
            }
        } catch (Exception e) {
            log.error("addOrderCallback -> update syncFlag error ：{}", e.getMessage());
        }
    }

    @Override
    public Map<String, String> orderInfoUrl(IRequest request, Long headerId) {
        Map<String, String> result = new HashMap<>();
        String status = salesOrderMapper.getOrderStatusByPrimaryKey(headerId);
        result.put(OrderConstants.ORDER_TAB_STATUS, status);

        if (StringUtils.isEmpty(status)) {
            result.put(OrderConstants.ORDER_TAB_ID, OrderConstants.TAB_ORDER_CREATE);
            result.put(OrderConstants.ORDER_TAB_TITLE, OrderConstants.TAB_TITLE_CREATE);
            result.put(OrderConstants.ORDER_TAB_URL, OrderConstants.ORDER_INFO_CREATE + "?orderId=" + headerId);
            return result;
        }
        switch (status) {
        case OrderConstants.SALES_STATUS_NEW:
        case OrderConstants.SALES_STATUS_SAVED:
            result.put(OrderConstants.ORDER_TAB_ID, OrderConstants.TAB_ORDER_CREATE);
            result.put(OrderConstants.ORDER_TAB_TITLE, OrderConstants.TAB_TITLE_CREATE);
            result.put(OrderConstants.ORDER_TAB_URL, OrderConstants.ORDER_INFO_CREATE + "?orderId=" + headerId);
            break;
        case OrderConstants.SALES_STATUS_WPAY:
            result.put(OrderConstants.ORDER_TAB_ID, OrderConstants.TAB_ORDER_CREATE);
            result.put(OrderConstants.ORDER_TAB_TITLE, OrderConstants.TAB_TITLE_PAYMENT);
            result.put(OrderConstants.ORDER_TAB_URL, OrderConstants.ORDER_INFO_PAY + "?orderId=" + headerId);
            break;
        case OrderConstants.SALES_STATUS_COMP:
        case OrderConstants.SALES_STATUS_CANCELED:
        case OrderConstants.SALES_STATUS_CONF:
        case OrderConstants.SALES_STATUS_VOIDED:
        case OrderConstants.SALES_STATUS_PAYIN_PAYMENT:
        case OrderConstants.SALES_STATUS_FAIL:
            result.put(OrderConstants.ORDER_TAB_ID, OrderConstants.TAB_ORDER_DETAIL);
            result.put(OrderConstants.ORDER_TAB_TITLE, OrderConstants.TAB_TITLE_INFO);
            result.put(OrderConstants.ORDER_TAB_URL, OrderConstants.ORDER_INFO_DETAIL + "?orderId=" + headerId);
            break;
        default:
            result.put(OrderConstants.ORDER_TAB_ID, OrderConstants.TAB_ORDER_CREATE);
            result.put(OrderConstants.ORDER_TAB_TITLE, OrderConstants.TAB_TITLE_INFO);
            result.put(OrderConstants.ORDER_TAB_URL, OrderConstants.ORDER_INFO_CREATE + "?orderId=" + headerId);
            break;
        }
        return result;
    }

    @Override
    public List<SpmPeriod> checkOrderPeriod(IRequest request, Long headerId) {
        List<SpmPeriod> result = new ArrayList<SpmPeriod>(2);
        SpmPeriod orderPeriod = spmPeriodMapper.getSpmPeriodByOrderId(headerId);
        Long marketId = request.getAttribute("_marketId");
        if (orderPeriod == null) {
            SpmPeriod queryPeriod = new SpmPeriod();
            queryPeriod.setOrgId(marketId);
            SimpleDateFormat fromat = new SimpleDateFormat(SystemProfileConstants.PERIOD_NAME_FORMAT);
            String periodName = fromat.format(new Date());
            queryPeriod.setPeriodName(periodName);

            List<SpmPeriod> orderPeriods = spmPeriodMapper.selectSpmPeriod(queryPeriod);

            if (orderPeriods.size() == 1) {
                orderPeriod = orderPeriods.get(0);
            } else {
                return result;
            }
        }
        /*
         * if ( OrderConstants.YES.equals(orderPeriod.getClosingStatus())) {
         * return result; }
         */
        result.add(orderPeriod);
        SpmPeriod previousPeriod = spmPeriodService.getPreviousPeriod(request, orderPeriod);
        if (previousPeriod != null) {
            result.add(previousPeriod);
        }
        return result;
    }

    @Override
    public List<SalesOrder> selectHeaderId(String orderNumber) {
        return salesOrderMapper.selectHeaderId(orderNumber);
    }

    @Override
    public boolean updateOrderPeriod(IRequest request, SalesOrderDetail orderDetail) throws OrderException {
        SalesOrder order = salesOrderMapper.selectByPrimaryKeyOnly(orderDetail.getHeaderId());
        if (order == null) {
            return false;
        }
        /*
         * if (!OrderConstants.SALES_STATUS_COMP.equals(order.getOrderStatus()))
         * { if (log.isErrorEnabled()) { log.error(
         * "order {} currenct status is {} not COMP", order.getHeaderId(),
         * order.getOrderStatus()); } throw new
         * OrderException(OrderException.MSG_ERROR_OM_STATUS_COMPARE_ERROR, new
         * Object[] { OrderConstants.SALES_STATUS_COMP, order.getOrderStatus()
         * }); }
         */

        Date endDate = new Date();

        // TODO: bouns SPM.BONUS_MONTH_DATE

        List<String> bounsDates = paramService.getParamValues(request, SystemProfileConstants.PARAM_BONUS_MONTH_DATE,
                SystemProfileConstants.ORG_TYPE_SALES, order.getSalesOrgId());
        if (bounsDates == null || bounsDates.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("bounsDate is null");
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_BONUS_MONTH_DATE_ERROR, new Object[] {});
        }
        boolean isInclude = false;
        String toDay = endDate.getDate() + "";
        for (String bounsDate : bounsDates) {
            if (bounsDate.equals(toDay)) {
                isInclude = true;
                break;
            }
        }
        if (!isInclude) {
            if (log.isErrorEnabled()) {
                log.error("today is not include {}", bounsDates);
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_BONUS_MONTH_DATE_ERROR, new Object[] { bounsDates });
        }

        List<SpmPeriod> availablePeriods = self().checkOrderPeriod(request, order.getHeaderId());
        if (availablePeriods == null || availablePeriods.size() < 2) {
            if (log.isErrorEnabled()) {
                log.error("order availablePeriods error");
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_AVAILABLE_PERIOD_ERROR,
                    new Object[] { orderDetail.getHeaderId() });
        }
        SpmPeriod selectPeriod = null;
        for (SpmPeriod period : availablePeriods) {
            if (period.getPeriodId().equals(orderDetail.getPeriodId())) {
                selectPeriod = period;
                break;
            }
        }
        if (selectPeriod == null) {
            if (log.isErrorEnabled()) {
                log.error("order period {} not in availablePeriods", order.getPeriodId());
            }
            throw new OrderException(OrderException.MSG_ERROR_OM_AVAILABLE_PERIOD_ERROR,
                    new Object[] { orderDetail.getHeaderId() });
        }
        /*
         * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); if
         * (!dateFormat.format(order.getPayDate()).equals(dateFormat.format(
         * orderDetail.getPayDate()))) {
         * 
         * if (endDate.before(orderDetail.getPayDate())) { if
         * (log.isErrorEnabled()) { log.error(
         * "order payDate after now,change date {},now is {}",
         * orderDetail.getPayDate(), endDate); } throw new
         * OrderException(OrderException.MSG_ERROR_OM_DATE_COMPARE_ERROR, new
         * Object[] { orderDetail.getPayDate(), endDate }); } Calendar c =
         * Calendar.getInstance(); c.add(Calendar.MONTH, -1); // 上个月
         * c.set(Calendar.DAY_OF_MONTH, 1); // 第一天 c.set(Calendar.MILLISECOND,
         * 0); c.set(Calendar.SECOND, 0); c.set(Calendar.MINUTE, 0);
         * c.set(Calendar.HOUR_OF_DAY, 0); Date startDate = c.getTime(); if
         * (log.isDebugEnabled()) { log.debug("startDate is {}", startDate); }
         * if (startDate.after(orderDetail.getPayDate())) { if
         * (log.isErrorEnabled()) { log.error(
         * "order payDate before allow,payDate {},allow Date is {}",
         * orderDetail.getPayDate(), startDate); } throw new
         * OrderException(OrderException.MSG_ERROR_OM_DATE_COMPARE_ERROR, new
         * Object[] { orderDetail.getPayDate(), startDate }); }
         * 
         * // 暂时删除奖金区间与支付日期的校验 SimpleDateFormat sdf = new
         * SimpleDateFormat(OrderConstants.DEFAULT_PERIOD_FORMAT); String
         * periodName = sdf.format(orderDetail.getPayDate()); if
         * (!selectPeriod.getPeriodName().equals(periodName)) { if
         * (log.isErrorEnabled()) { log.error(
         * "order payDate {},not in the selectperiod name {}",
         * orderDetail.getPayDate(), selectPeriod.getName()); } throw new
         * OrderException(OrderException.MSG_ERROR_OM_DATE_COMPARE_ERROR,
         * 
         * new Object[] { orderDetail.getPayDate(), selectPeriod.getName() }); }
         * 
         * }
         */
        // TODO: 系统还未实现后台校验，只实现了前台的。需要框架做好实现
        /*
         * if (!accessService.access(OrderConstants.ACCESS_MODIFY_BONUS_MONTH))
         * { if (log.isErrorEnabled()) { log.error("user access error"); } throw
         * new OrderException(OrderException.MSG_ERROR_OM_ACCESS_ERROR, new
         * Object[] {}); }
         */
        return salesOrderMapper.updateOrderPeriodAndPayDate(orderDetail.getHeaderId(), orderDetail.getPayDate(),
                selectPeriod.getPeriodId()) == 1;
    }

    @Override
    public void updateSyncFlag(SalesOrder order) {
        order.setDappSyncFlag("N");
        salesOrderMapper.updateSyncFlag(order);
    }

    @Override
    public boolean checkOrderShipStatus(Long headerId) {
        return salesLineMapper.checkOrderShipStatus(headerId) == 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.lkkhpg.dsis.admin.order.service.ISalesOrderService#printInvoice(com.
     * lkkhpg.dsis.platform.core.IRequest, java.lang.Long)
     */
    @Override
    public Invoice printInvoice(IRequest request, Long orderId) throws CommOrderException, CommSystemProfileException {
        // TODO Auto-generated method stub
        return invoiceService.printInvoice(request, orderId);
    }

    @Override
    public SalesOrder orderWPayToSave(IRequest request, Long orderId) throws CommOrderException, CommVoucherException {
        // TODO Auto-generated method stub
        return commSalesOrderService.orderWPayToSave(request, orderId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lkkhpg.dsis.admin.order.service.ISalesOrderService#
     * cancelWPayORSavedOrder()
     */
    @Override
    public void cancelWPayORSavedOrder() throws CommOrderException, CommVoucherException {
        // TODO Auto-generated method stub
        List<CodeValue> values = codeValueMapper.selectCodeValuesByCode(OrderConstants.MARKET_TIME_ZONE,
                OrderConstants.LANG_CODE_CN);
        IRequest request = new DsisServiceRequest();
        request.setAccountId(Account.SYSADMIN_ACCOUNT_ID);
        request.setLocale(BaseConstants.DEFAULT_LANG);
        if (values.size() != 0) {
            Calendar calendar = Calendar.getInstance();

            // 当前的小时数
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            for (CodeValue codeValue : values) {
                int market = Integer.parseInt(codeValue.getMeaning());

                if (market - hour == 0) {
                    // 取消该市场下的所有以保存的订单.
                    String marketCode = codeValue.getValue();
                    List<SalesOrder> orders = salesOrderMapper.querySaveAndWPayOrderbyMarketCode(marketCode);
                    for (SalesOrder order : orders) {
                        order.setOrderStatus(OrderConstants.SALES_STATUS_CANCELED);
                        voucherService.validateEcoupon(request, order.getHeaderId());
                        self().updateOrderBySelective(request, order);
                    }

                }
            }
        }
    }



    @Override
    public void modifyWPayOrder() throws Exception{
        //Long i = 1L;
        Long companyId = null;
        Long marketId = null;
        SpmCompany spmCompany = new SpmCompany();
        SalesOrder salesOrder = new SalesOrder();
        //Map<Long,List<Long>> maps = new HashMap<Long,List<Long>>(16);

        //所有公司信息
        List<SpmCompany> spmCompanies = spmCompanyMapper.queryCompany(spmCompany);

        //待付款状态的所有订单
        List<QueryOrder> orders = queryOrderMapper.queryOrders();

        for(QueryOrder order : orders){
            if(order.getMemberId() != null){
                salesOrder.setHeaderId(order.getHeaderId());

                Long memberId = Long.parseLong(order.getMemberId());
                Member member = memberMapper.queryMarketIdAndCompanyIdByMemberId(memberId);
                if(member !=null ){
                    companyId = member.getCompanyId();
                    marketId = member.getMarketId();
                }
                if(companyId != null && marketId != null ){
                    for(SpmCompany company : spmCompanies){
                        if(company.getCompanyId().equals(companyId)){
                            //公司维护的付款预留时间
                            if(company.getAttribute2() != null && !company.getAttribute2().equals("-1")){
                                Date orderDate = order.getWaitPayDate();
                                //Date orderDate = order.getOrderDate();
                                if (orderDate != null){
                                    Double intervalTime = Double.parseDouble(company.getAttribute2()) *60*1000;
                                    Long orderMillisCond =  orderDate.getTime();
                                    Long currentMillisCond = System.currentTimeMillis();
                                    Long su = currentMillisCond - orderMillisCond;
                                    if((currentMillisCond - orderMillisCond) > intervalTime){
                                        //修改销售订单状态为已取消
                                        salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_CANCELED);
                                        salesOrderMapper.updateOrderStatus(salesOrder);

                                        //信用额度还原
                                        //查询订单详细信息
                                        IRequest request = new DsisServiceRequest();
                                        request.setAccountId(Account.SYSADMIN_ACCOUNT_ID);
                                        request.setLocale(BaseConstants.DEFAULT_LANG);
                                        SalesOrder salesOrder1=commSalesOrderService.getOrder(request,salesOrder.getHeaderId(),false,false);
                                        BigDecimal dis=salesOrder1.getDiscountAmt();
                                        if(dis.compareTo(BigDecimal.ZERO) > 0) {
                                            //先查一下是否有包含该订单的信用额度调整
                                            DiscountTrxQuery discountTrxQuery=new DiscountTrxQuery();
                                            discountTrxQuery.setAttribute1(salesOrder1.getOrderNumber());
                                            //如果有则将该信用额度调整的状态设为取消
                                            List<DiscountTrxQuery> list =discountTrxQueryService.query(discountTrxQuery);
                                            if(CollectionUtils.isNotEmpty(list)){
                                                discountTrxHeadService.canclTrx(salesOrder1.getOrderNumber());
                                            }
                                        }

                                        //发送站内通知
                                        this.publishSysMessage(order.getOrderNumber(),marketId,memberId.toString());
                                        /**List<Long> list = new ArrayList<>();
                                         *list.add(memberId);
                                         *list.add(su);
                                         *maps.put(i,list);
                                         *i++;
                                          */
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("====================================="+maps.toString());
    }


    public void publishSysMessage(String orderNumber,Long marketId,String memberId) throws Exception{
        String content = "<p>"+"订单号：<b>"+orderNumber+"</b> 待付款状态超时,订单已取消！"+"</p>";
        SysMsMessage msMessage = new SysMsMessage();
        msMessage.setMarketId(marketId);
        msMessage.setMessageName("订单取消通知");
        msMessage.setMessageType("SYSME");
        msMessage.setMessageStatus("DRAFT");
        msMessage.setPublishChannel("DSIS");
        msMessage.setPublishDateType("NOW");
        msMessage.setSender(1L);
        msMessage.setMessageContent(content);

        msMessageMapper.insert(msMessage);

        Date currentDate = new Date(System.currentTimeMillis());

        SysMsMessageAssign msMessageAssign = new SysMsMessageAssign();
        msMessageAssign.setAssignType("MEM");
        msMessageAssign.setMsMessageId(msMessage.getMsMessageId());
        msMessageAssign.setRequestId(-1L);
        msMessageAssign.setProgramId(-1L);
        msMessageAssign.setObjectVersionNumber(1L);
        msMessageAssign.setCreatedBy(1L);
        msMessageAssign.setCreationDate(currentDate);
        msMessageAssign.setLastUpdatedBy(1L);
        msMessageAssign.setLastUpdateDate(currentDate);
        msMessageAssign.setAssignValue(memberId);

        msMessageAssignMapper.insert(msMessageAssign);

        msMessage.set__status("update");

        SysMsMessageAssign assign = new SysMsMessageAssign();
        assign.setMsMessageId(msMessage.getMsMessageId());
        List<SysMsMessageAssign> arrayList = sysMsMessageAssignMapper.queryByMemAssign(assign);

        manualMessageService.publishMessage(msMessage, arrayList);
    }

}
