/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderPaymentUpdate;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderPaymentUpdateResponse;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOrderPaymentUpdateService;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.service.ICommDeliveryService;
import com.lkkhpg.dsis.common.service.ICommOrderPaymentService;
import com.lkkhpg.dsis.common.service.IMwsOrderPaymentService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.service.IAccountService;

/**
 * 订单支付接口实现类.
 *
 * @author zhenyang.he
 */
@Service
@Transactional
public class OrderPaymentUpdateServiceImpl implements IOrderPaymentUpdateService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;

    @Autowired
    private ICommDeliveryService commDeliveryService;

    @Autowired
    private IMwsOrderPaymentService mwsOrderPaymentService;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private ICommOrderPaymentService commOrderPaymentService;
    
    @Autowired
    private IAccountService accountService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 更新订单状态.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderPaymentUpdateResponse updatePaymentStatus(OrderPaymentUpdate orderPaymentUpdate) throws DAppException {
        String orderNumber = orderPaymentUpdate.getOrderNumber();
        String orderStatus = orderPaymentUpdate.getOrderStatus();
        IRequest iRequest = RequestHelper.newEmptyRequest();
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        RequestHelper.setCurrentRequest(iRequest);
        Account dappAccount = accountService.selectByLoginName(IntegrationConstant.DAPP_ACCOUNT_NAME);
        if(null == dappAccount || StringUtil.isEmpty(dappAccount.getLoginName())){
            throw new DAppException(IntegrationConstant.ISG_ERROR_DAPP_USER_MISSING,
                    IntegrationConstant.STATUS_CODE_ORDER, IntegrationConstant.ISG_ERROR_DAPP_USER_MISSING);
        }
        iRequest.setAccountId(dappAccount.getAccountId());
        OrderPaymentUpdateResponse orderPaymentUpdateResponse = new OrderPaymentUpdateResponse();
        orderPaymentUpdateResponse.setOrderNumber(orderNumber);

        if (StringUtils.isEmpty(orderStatus) || StringUtils.isEmpty(orderNumber)) {
            throw new DAppException(IntegrationConstant.ISG_ERROR_REQUIRED_MISSING,
                    IntegrationConstant.STATUS_CODE_ORDER, IntegrationConstant.ISG_ERROR_REQUIRED_MISSING);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("orderNumber", orderPaymentUpdate.getOrderNumber());
        List<SalesOrder> salesOrder = salesOrderMapper.getOrderPaymentByOrderId(map);
        if (salesOrder != null && salesOrder.size() > 0) {
            if (salesOrder.get(0).getSourceType().equals(IntegrationConstant.ORDER_SOURCE_TYPE_DAPP)) {
                if (!IntegrationConstant.ORDER_STATUS_COMP_ED.equals(salesOrder.get(0).getOrderStatus())
                        && !IntegrationConstant.ORDER_STATUS_PAYIN.equals(salesOrder.get(0).getOrderStatus())
                        && !IntegrationConstant.ORDER_STATUS_FAIL.equals(salesOrder.get(0).getOrderStatus())) {
                    orderPaymentUpdateResponse.setResult(-1L);
                    orderPaymentUpdateResponse.setDescription(String
                            .valueOf(IntegrationConstant.ERROR_30006 + ":" + IntegrationConstant.ORDER_STATUS_ERROR));
                    return orderPaymentUpdateResponse;
                }

                // 从完成变成失败或支付中，拒绝掉
                if (IntegrationConstant.ORDER_STATUS_COMP_ED.equals(salesOrder.get(0).getOrderStatus())
                        && (IntegrationConstant.ORDER_STATUS_PAYIN.equals(orderStatus)
                                || IntegrationConstant.ORDER_STATUS_FAIL.equals(orderStatus))) {
                    orderPaymentUpdateResponse.setResult(-1L);
                    orderPaymentUpdateResponse.setDescription(String
                            .valueOf(IntegrationConstant.ERROR_30015 + ":" + IntegrationConstant.ORDER_STATUS_COMP));
                    return orderPaymentUpdateResponse;
                }

                // 只有要更新的状态为COMP,且原状态为PAYIN或者FAIL，才新增支付行
                if (IntegrationConstant.ORDER_STATUS_COMP_ED.equals(orderStatus)
                        && !IntegrationConstant.ORDER_STATUS_COMP_ED.equals(salesOrder.get(0).getOrderStatus())) {

                    iRequest.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrder.get(0).getSalesOrgId());
                    addPayment(iRequest, salesOrder.get(0), orderPaymentUpdate);
                }

                map.put("orderStatus", orderStatus);
                map.put("headerId", salesOrder.get(0).getHeaderId());
                map.put("lastUpdatedBy", dappAccount.getAccountId());
                // 更新状态为COMP时更新payDate,period
                if (IntegrationConstant.ORDER_STATUS_COMP_ED.equals(orderStatus)) {
                    map.put("payDate", dAppUtilService.dateTimeStringToDate(orderPaymentUpdate.getAuthDate()));
                    /*map.put("periodId",
                            parsePeriod(dAppUtilService.dateTimeStringToDate(orderPaymentUpdate.getAuthDate())));*/
                }
                salesOrderMapper.updateOrderStatusByHeaderId(map);

                /*
                 * // 处理虚拟商品包的订单行.订单完成,则进行拆行. if ("COMP".equals(orderStatus) &&
                 * !"COMP".equals(salesOrder.get(0).getOrderStatus())) {
                 * Map<String, Object> getLineMap = new HashMap<String,
                 * Object>(); getLineMap.put("headerId",
                 * salesOrder.get(0).getHeaderId());
                 * getLineMap.put("salesOrgId",
                 * salesOrder.get(0).getSalesOrgId()); List<SalesLine> lines =
                 * salesLineMapper.selectByHeaderId(getLineMap); for (SalesLine
                 * line : lines) { if
                 * (OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(
                 * line.getItemType()) ||
                 * OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(
                 * line.getItemType())) {
                 * commSalesOrderService.processVirtualPackageItem(iRequest,
                 * line); } } }
                 */

                orderPaymentUpdateResponse.setOrderNumber(orderNumber);
                orderPaymentUpdateResponse.setResult(1L);
                orderPaymentUpdateResponse.setDescription(null);

                // 如果订单状态为已完成，生成发运单
                /*
                 * if("COMP".equals(orderStatus)){ SalesOrder _salesOrder=
                 * salesOrderMapper.selectSalesOrderByHeadId(salesOrder.get(0).
                 * getHeaderId()); orderPaymentUpdateResponse =
                 * self().createDeliveriesByOrder(iRequest,
                 * _salesOrder,orderPaymentUpdateResponse); }
                 */

                return orderPaymentUpdateResponse;

            } else {
                orderPaymentUpdateResponse.setResult(-1L);
                orderPaymentUpdateResponse.setDescription(String
                        .valueOf(IntegrationConstant.ERROR_30005 + ":" + IntegrationConstant.ORDER_SOURCE_TYPE_ERROR));
                return orderPaymentUpdateResponse;
            }
        } else {
            orderPaymentUpdateResponse.setResult(-1L);
            orderPaymentUpdateResponse.setDescription(String
                    .valueOf(IntegrationConstant.ERROR_30003 + ":" + IntegrationConstant.ORDER_NUMBER_NOT_EXISTED));
            return orderPaymentUpdateResponse;
        }

    }

    /*@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderPaymentUpdateResponse createDeliveriesByOrder(IRequest iRequest, SalesOrder salesOrder,
            OrderPaymentUpdateResponse orderPaymentUpdateResponse) {
        List<SalesLine> salesLines = salesLineMapper.selectByHeadId(salesOrder.getHeaderId(), true, true);
        salesOrder.setLines(salesLines);
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        iRequest.setAccountId(IntegrationConstant.DAPP_ACCOUNT_ID);
        iRequest.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrder.getSalesOrgId());
        try {
            List<Long> deliveryIds = commDeliveryService.createDeliveriesByOrder(iRequest, salesOrder);
            if (deliveryIds == null || deliveryIds.size() == 0) {
                orderPaymentUpdateResponse.setDescription("createDeliveriesByOrder error");
            }
            return orderPaymentUpdateResponse;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("订单" + salesOrder.getOrderNumber() + "生成发运单失败：" + IntegrationException.getErrorStackTrace(e));
            }
            orderPaymentUpdateResponse.setDescription("createDeliveriesByOrder error");
            return orderPaymentUpdateResponse;
        }
    }*/

    @Transactional(rollbackFor = Exception.class)
    private void addPayment(IRequest iRequest, SalesOrder salesOrder, OrderPaymentUpdate orderPaymentUpdate)
            throws DAppException {
        OmMwsOrderPayment couponsPayment = new OmMwsOrderPayment();
        List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
        couponsPayment.setOrderHeaderId(salesOrder.getHeaderId());
        List<OmMwsOrderPayment> omMwsOrderPayments = mwsOrderPaymentService.getMwsOrderPayments(iRequest,
                couponsPayment);
        for (OmMwsOrderPayment omMwsOrderPayment : omMwsOrderPayments) {
            if (IntegrationConstant.ORDER_PAYMETHOD_ONLPA.equals(omMwsOrderPayment.getPaymentMethod())
                    || IntegrationConstant.ORDER_PAYMETHOD_ECUP.equals(omMwsOrderPayment.getPaymentMethod())) {
                OrderPayment orderPayment = new OrderPayment();
                orderPayment.setOrderHeaderId(omMwsOrderPayment.getOrderHeaderId());
                orderPayment.setSalesOrgId(omMwsOrderPayment.getSalesOrgId());
                orderPayment.setAttribute2(orderPaymentUpdate.getPaymentMethod());
                orderPayment.setPaymentMethod(omMwsOrderPayment.getPaymentMethod());
                orderPayment.setPaymentAmount(omMwsOrderPayment.getPaymentAmount());
                orderPayment.setTailNumber(orderPaymentUpdate.getLastFourDigitPAN());
                orderPayment.setAttribute1(orderPaymentUpdate.getAuthCode());
                orderPayment.setTransactionNumber(orderPaymentUpdate.getTransactionRefNo());
                orderPayment.setVoucherId(omMwsOrderPayment.getVoucherId());
                orderPayment.setStatus("NEW");
                orderPayments.add(orderPayment);

            }
            // 扣减用掉的coupons
            if (IntegrationConstant.ORDER_PAYMETHOD_ECUP.equals(omMwsOrderPayment.getPaymentMethod())) {
                Voucher voucher = voucherMapper.selectByPrimaryKey(omMwsOrderPayment.getVoucherId());
                voucher.setQuantity(voucher.getQuantity() - 1);
                voucherMapper.updateByPrimaryKeySelective(voucher);
            }
        }

        try {
            // 调用comm service
            commOrderPaymentService.createOrderPayment(iRequest, orderPayments, salesOrder.getHeaderId());

        } catch (Exception e) {
            // TODO: handle exception
            if (logger.isErrorEnabled()) {
                logger.error("订单" + salesOrder.getOrderNumber() + "入库数据失败：" + IntegrationException.getErrorStackTrace(e));
            }
            throw new DAppException(IntegrationConstant.ORDER_PAYMENT_SAVE_ERROR+ "：" + IntegrationException.getErrorStackTrace(e), IntegrationConstant.ERROR_30004,
                    IntegrationConstant.ORDER_PAYMENT_SAVE_ERROR + ":" + IntegrationConstant.ORDER_PAYMENT_SAVE_ERROR
                            + " " + IntegrationException.getErrorStackTrace(e));
        }
    }

    private Long parsePeriod(Date date) {
        Calendar cal = Calendar.getInstance();
        Map<String, Object> map = new HashMap<String, Object>();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR); // 获取年份
        int month = cal.get(Calendar.MONTH) + 1; // 获取月份
        String periodName = String.valueOf(year) + String.format("%02d", month);
        map.put("periodYear", String.valueOf(year));

        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setPeriodName(periodName);
        List<SpmPeriod> spmPeriods = spmPeriodMapper.selectSpmPeriod(spmPeriod);
        if (spmPeriods == null || null == spmPeriods.get(0)) {
            return null;
        }
        return spmPeriods.get(0).getPeriodId();
    }

    @Override
    public OrderPaymentUpdateResponse createDeliveriesByOrder(IRequest iRequest, SalesOrder salesOrder,
            OrderPaymentUpdateResponse orderPaymentUpdateResponse) {
        // TODO Auto-generated method stub
        return null;
    }

}
