/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.mapper.MemAccountingBalanceMapper;
import com.lkkhpg.dsis.common.order.dto.Invoice;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherAssignMapper;
import com.lkkhpg.dsis.common.service.ICommDeliveryService;
import com.lkkhpg.dsis.common.service.ICommOrderPaymentService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IInvoiceService;
import com.lkkhpg.dsis.common.service.IMemWriteOffService;
import com.lkkhpg.dsis.common.service.IMemberBalanceTrxService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 订单支付实现类.
 * 
 * @author houmin
 */
@Service
@Transactional
public class CommOrderPaymentServiceImpl implements ICommOrderPaymentService {

    private Logger logger = LoggerFactory.getLogger(CommOrderPaymentServiceImpl.class);

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private SalesLineMapper salesLineMapper;
    @Autowired
    private IInvoiceService invoiceService;
    @Autowired
    private ICommSalesOrderService commSalesOrderService;
    @Autowired
    private ICommDeliveryService commDeliveryService;
    @Autowired
    private IMemWriteOffService memWriteOffService;
    @Autowired
    private IMemberBalanceTrxService memberBalanceTrxService;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private MemAccountingBalanceMapper accountingBalanceMapper;
    @Autowired
    private VoucherAssignMapper voucherAssignMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ICodeService codeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createOrderPayment(IRequest request, List<OrderPayment> orderPayments, Long orderHeaderId)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {
        Invoice invoice = null;
        // 获取订单详细信息
        SalesOrder salesOrder = commSalesOrderService.getOrder(request, orderHeaderId, true, true);
        // 发运单头id集合
        List<Long> deliveryIds = new ArrayList<Long>();
        if (self().validateOrderPayment(request, orderPayments, salesOrder)) {
            if (!orderPayments.isEmpty()) {
                if (!updateOrderPeriod(request, salesOrder)) {
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_BONUS_MONTH_CLOSED, null);
                }
                // 1.记录支付信息
                insertPaymentInfo(orderPayments, salesOrder);
            }
        }
        // 2.更新订单状态
        SalesOrder order = new SalesOrder();
        order.setHeaderId(orderHeaderId);
        order.setOrderStatus(OrderConstants.SALES_STATUS_COMP);
        order.setPayDate(salesOrder.getPayDate());
        salesOrderMapper.updateOrderAfterPayment(order);
        salesOrder.setOrderStatus(OrderConstants.SALES_STATUS_COMP);
        // 处理虚拟商品包的订单行.
        List<SalesLine> lines = salesOrder.getLines();
        for (SalesLine line : lines) {
            if (OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_COUNT.equals(line.getItemType())
                    || OrderConstants.LINE_ITEM_TYPE_VIRTUAL_PACKG_NOT_COUNT.equals(line.getItemType())) {
                commSalesOrderService.processVirtualPackageItem(request, line);
            }
        }
        // 3.账务事务处理程序
        List<MemAccountingTrx> trxs = memWriteOffService.createTrxByOrderPayment(request, salesOrder, orderPayments);
        for (MemAccountingTrx temp : trxs) {
            memberBalanceTrxService.processAccountingTrx(request, temp);
        }
        // 4.生成发票
        invoice = invoiceService.createInvoice(request, orderHeaderId);
        // 5.更新订单的发票信息
        if (invoice != null) {
            salesOrder.setInvoiceNumber(invoice.getInvoiceNumber());
        }
        // 6.创建发运单 不需要虚拟商品包，需要用商品包拆行过后的订单行.
        List<SalesLine> salesLines = salesLineMapper.selectByHeadId(orderHeaderId, true, true);
        salesOrder.setLines(salesLines);
        deliveryIds = commDeliveryService.createDeliveriesByOrder(request, salesOrder);
        if (deliveryIds == null) {
            deliveryIds = new ArrayList<>();
        }
        return deliveryIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validateOrderPayment(IRequest request, List<OrderPayment> orderPayments, SalesOrder salesOrder)
            throws CommOrderException {
        if (!orderPayments.isEmpty()) {
            // 0.订单状态：待付款、支付中、付款失败才允许支付
            String orderStatus = salesOrder.getOrderStatus();
            if (!(OrderConstants.SALES_STATUS_WAIT_PAYMENT.equals(orderStatus)
                    || OrderConstants.SALES_STATUS_PAYIN_PAYMENT.equals(orderStatus))) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_NOT_ALLOW_PAY, null);
            }
            // 1.判断输入值是否为空值
            if (validatePaymentIsEmpty(orderPayments)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_PAYMENT_EMPTY, null);
            }
            // 2.校验Ipoint用户所下订单支付方式
            if (!validateIpointUserOrder(request, orderPayments, salesOrder)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_IPOINT_PAY_METHOD, null);
            }
            // 3.校验货到付款时只有一条支付行
            if (!validatePayOnDelivery(orderPayments)) {
                throw new CommOrderException(CommOrderException.MSG_WARNING_OM_CONFIRM_AFTER_PAY, null);
            }
            // 4.校验页面中填写支付金额与实际支付金额是否一致
            if (!validatePaymentAmount(orderPayments)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AMOUNT_ERROR, null);
            }
            // 5.判断订单类型是否是积分支付，是积分支付则判断“会员所拥有销售积分是否大于或等于实际支付积分”
            if (!validateRedeemPointIsEnough(orderPayments, salesOrder)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_SALES_POINT_INSUFFICIENT, null);
            }
            // 6. 验证Exchanging Balance.
            if (!validateExchangingBalance(orderPayments, salesOrder)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_EB_INSUFFICIENT, null);
            }
            // 7.验证 Remaining Balance.
            if (!validateRemainingBalance(orderPayments, salesOrder)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_RB_INSUFFICIENT, null);
            }
        }
        return true;
    }

    /**
     * 1、判断订单的奖金月份是否关闭，如果关闭，则将奖金月份更新为当前奖金月份。 2、如果奖金月份未关闭: 2.1
     * MWS/APP渠道，将奖金月份更新为当前奖金月份。 2.2 其他渠道，不更新订单的奖金月份。
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            订单详情
     * @return true-校验正确；false-校验失败
     */
    private boolean updateOrderPeriod(IRequest request, SalesOrder salesOrder) {
        // 校验订单上的奖金月份是否为系统当前奖金月份。若不一致，则将订单上奖金月份自动改为系统当前奖金月份。
        List<SpmPeriod> orderPeriods, nowPeriods;
        String nowPeriodName = new SimpleDateFormat("yyyyMM").format(new Date());
        // 2016-08-18 add 订单奖金月份逻辑
        // 1.判断订单上的奖金月份是否关闭
        SpmPeriod orderPeriod = new SpmPeriod();
        orderPeriod.setPeriodName(salesOrder.getPeriod());
        orderPeriod.setOrgId(request.getAttribute(SystemProfileConstants.MARKET_ID));
        orderPeriod.setOrgType(SystemProfileConstants.ORG_TYPE_MARKET);
        orderPeriods = spmPeriodMapper.selectSpmPeriod(orderPeriod);
        if (orderPeriods == null || orderPeriods.isEmpty() || orderPeriods.size() < 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("SalesOrder [{}] periodName [{}] is error!",
                        new Object[] { salesOrder.getOrderNumber(), salesOrder.getPeriod() });
            }
            return false;
        }
        // 关闭，则将奖金月份更新为当前奖金月份;或未关闭MWS渠道，将奖金月份更新为当前奖金月份。
        if (SystemProfileConstants.CLOSING_STATUS_Y.equals(orderPeriods.get(0).getClosingStatus())
                || (OrderConstants.ORDER_CHANNEL_DISWB.equals(salesOrder.getChannel()))) {
            SpmPeriod nowSpmPeriod = new SpmPeriod();
            nowSpmPeriod.setPeriodName(nowPeriodName);
            nowSpmPeriod.setOrgId(request.getAttribute(SystemProfileConstants.MARKET_ID));
            nowSpmPeriod.setOrgType(SystemProfileConstants.ORG_TYPE_MARKET);
            nowPeriods = spmPeriodMapper.selectSpmPeriod(nowSpmPeriod);
            if (nowPeriods.get(0).getPeriodId() == null) {
                return false;
            }
            salesOrder.setPeriodId(nowPeriods.get(0).getPeriodId());
            salesOrderMapper.updateByPrimaryKeySelective(salesOrder);
        }
        return true;
    }

    /**
     * 校验支付行必输字段.
     * 
     * @param orderPayments
     *            所有订单支付行信息
     * @return true-存在必输字段为空,false-不存在必输字段为空
     */
    private boolean validatePaymentIsEmpty(List<OrderPayment> orderPayments) {
        String paymentMethod = ""; // 支付方式
        String creditCardType = "";
        String transactionNumber = "";
        String tailNumber = "";
        for (OrderPayment orderPayment : orderPayments) {
            if (DTOStatus.DELETE.equals(orderPayment.get__status())) {
                continue;
            }
            paymentMethod = orderPayment.getPaymentMethod();
            creditCardType = orderPayment.getCreditCardType();
            transactionNumber = orderPayment.getTransactionNumber();
            tailNumber = orderPayment.getTailNumber();
            if (OrderConstants.PAYMENT_METHOD_CHEQU.equals(paymentMethod)) { // 支票and汇款单
                if (StringUtils.isEmpty(transactionNumber)) {
                    return true;
                }
            } else if (OrderConstants.PAYMENT_METHOD_CREDI.equals(paymentMethod)) { // 信用卡-POS
                if (StringUtils.isEmpty(creditCardType) || StringUtils.isEmpty(transactionNumber)
                        || StringUtils.isEmpty(tailNumber)) {
                    return true;
                }
            } else if (OrderConstants.PAYMENT_METHOD_DBCRD.equals(paymentMethod)) { // 借记卡—POS
                if (StringUtils.isEmpty(transactionNumber) || StringUtils.isEmpty(tailNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验Ipoint用户(销售渠道只有IPTC)所下订单支付方式.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            订单详情
     * @return true-校验正确；false-校验失败
     */
    private boolean validateIpointUserOrder(IRequest request, List<OrderPayment> orderPayments, SalesOrder salesOrder) {
        if (!OrderConstants.ORDER_CHANNEL_IPTC.equals(salesOrder.getChannel())) {
            return true;
        }
        if (salesOrder.getCreatedBy() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("[{}] SalesOrder not found the createdBy.", new Object[] { salesOrder.getOrderNumber() });
                return false;
            }
        }
        User user = userMapper.selectUserByAccountId(salesOrder.getCreatedBy());
        if (logger.isDebugEnabled()) {
            if (user == null || StringUtils.isEmpty(user.getUserType())) {
                logger.debug("SalesOrder[{}] createdBy [{}] do not found the userId.",
                        new Object[] { salesOrder.getOrderNumber(), salesOrder.getCreatedBy() });
                return false;
            }
        }
        if (UserConstants.USER_TYPE_IPONT.equals(user.getUserType())) {
            List<CodeValue> payMethodTypes = codeService.selectCodeValuesByCodeName(request,
                    OrderConstants.OM_PAY_METHOD_MODIFY);
            if (payMethodTypes.size() < 1) {
                if (logger.isDebugEnabled()) {
                    logger.debug("CodeValue not found.[{}]", new Object[] { OrderConstants.OM_PAY_METHOD_MODIFY });
                }
                return false;
            }
            for (OrderPayment orderPayment : orderPayments) {
                if (OrderConstants.PAYMENT_METHOD_ECUP.equals(orderPayment.getPaymentMethod())) {
                    continue;
                } else {
                    for (CodeValue codeValue : payMethodTypes) {
                        if (codeValue.getValue().equals(orderPayment.getPaymentMethod())) {
                            if (!OrderConstants.IPOINT_PAY_METHOD_FLAG_Y.equals(codeValue.getDescription())) {
                                return false;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 校验会员销售积分是否足够.
     * 
     * @param orderPayments
     *            所有的支付行信息
     * @param salesOrder
     *            订单信息
     * @return true-会员销售积分足够支付,false-会员销售积分不足够支付
     */
    private boolean validateRedeemPointIsEnough(List<OrderPayment> orderPayments, SalesOrder salesOrder) {
        String orderType = orderPayments.get(0).getOrderType(); // 订单类型
        BigDecimal salesPoint;
        BigDecimal redeemPointCount;
        if (OrderConstants.ORDER_TYPE_RDEM.equals(orderType)) {
            salesPoint = orderPaymentMapper.selectMemSalesPoint(salesOrder.getHeaderId()); // 会员销售积分
            redeemPointCount = orderPayments.get(0).getRedeemPointCount(); // 实际支付积分
            if (redeemPointCount.compareTo(salesPoint) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证ExchangingBalance金额是否大于支付金额.
     * 
     * @param orderPayments
     *            支付方式
     * @param salesOrder
     *            销售订单
     * @return 结果
     */
    private boolean validateExchangingBalance(List<OrderPayment> orderPayments, SalesOrder salesOrder) {
        BigDecimal ebAmount = BigDecimal.ZERO;
        for (OrderPayment orderPayment : orderPayments) {
            if (OrderConstants.PAY_METHOD_MODIFY_EBPAY.equals(orderPayment.getPaymentMethod())) {
                ebAmount = ebAmount.add(orderPayment.getPaymentAmount());
            }
        }
        if (ebAmount.compareTo(BigDecimal.ZERO) > 0) {
            Long memberId = salesOrder.getMemberId();
            MemAccountingBalance memAccountingBalance = accountingBalanceMapper.selectByMemIdAndAccType(memberId,
                    MemberConstants.ACCOUNTING_TYPE_EB);
            if (memAccountingBalance == null || memAccountingBalance.getBalance().compareTo(ebAmount) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证RemainingBalance金额是否大于支付金额.
     * 
     * @param orderPayments
     *            支付方式
     * @param salesOrder
     *            销售订单
     * @return 结果
     */
    private boolean validateRemainingBalance(List<OrderPayment> orderPayments, SalesOrder salesOrder) {
        BigDecimal rbAmount = BigDecimal.ZERO;
        for (OrderPayment orderPayment : orderPayments) {
            if (OrderConstants.PAY_METHOD_MODIFY_RBPAY.equals(orderPayment.getPaymentMethod())) {
                rbAmount = rbAmount.add(orderPayment.getPaymentAmount());
            }
        }
        if (rbAmount.compareTo(BigDecimal.ZERO) > 0) {
            Long memberId = salesOrder.getMemberId();
            MemAccountingBalance memAccountingBalance = accountingBalanceMapper.selectByMemIdAndAccType(memberId,
                    MemberConstants.ACCOUNTING_TYPE_RB);
            if (memAccountingBalance.getBalance().compareTo(rbAmount) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验支付金额与实际支付金额是否一致.
     * 
     * @param orderPayments
     *            所有的支付行信息
     * @return true-支付金额与实际支付金额一致;false-支付金额与实际支付金额不一致
     */
    private boolean validatePaymentAmount(List<OrderPayment> orderPayments) {
        // 页面中所填写支付总金额
        BigDecimal amountCount = new BigDecimal(0);
        // 实际支付金额
        BigDecimal actrualAmount = new BigDecimal(0);
        Long orderHeaderId = orderPayments.get(0).getOrderHeaderId();
        for (OrderPayment orderPayment : orderPayments) {
            if (DTOStatus.DELETE.equals(orderPayment.get__status())) {
                continue;
            }
            amountCount = amountCount.add(orderPayment.getPaymentAmount());
        }
        actrualAmount = salesOrderMapper.selectActrualPayAmtByHeaderId(orderHeaderId);
        if (!(actrualAmount.compareTo(amountCount) == 0)) { // 支付金额与实际支付金额不一致
            return false;
        }
        return true;
    }

    /**
     * 校验货到付款时只能有一条支付行.
     * 
     * @param orderPayments
     *            所有的支付行信息
     * @return true-只存在一条支付行;false-存在多条支付行
     */
    private boolean validatePayOnDelivery(List<OrderPayment> orderPayments) {
        for (OrderPayment orderPayment : orderPayments) {
            if (orderPayments.size() > 1
                    && OrderConstants.PAYMENT_METHOD_DELIV.equals(orderPayment.getPaymentMethod())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 记录订单支付信息.
     * 
     * @param orderPayments
     *            所有的支付行信息
     * @throws CommOrderException
     *             订单统一异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertPaymentInfo(List<OrderPayment> orderPayments, SalesOrder salesOrder) throws CommOrderException {
        Long memberId = salesOrder.getMemberId();
        for (OrderPayment orderPayment : orderPayments) {
            orderPayment.setStatus(OrderConstants.PAYMENT_STATUS_NEW);
            // 判断是否为ECUP支付，若为ECUP支付，则需要变更数量
            if (OrderConstants.PAYMENT_METHOD_ECUP.equals(orderPayment.getPaymentMethod())) {
                // 校验ecupon是否分配且可用
                VoucherAssign voucherAssign = new VoucherAssign();
                voucherAssign.setVoucherId(orderPayment.getVoucherId());
                voucherAssign.setMemberId(memberId);
                List<VoucherAssign> voucherAssigns = voucherAssignMapper.selectVoucherAssign(voucherAssign);
                if (voucherAssigns == null || voucherAssigns.isEmpty()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("The member not assign the voucher,voucherId : {}.",
                                new Object[] { orderPayment.getVoucherId() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_PDM_MEMBER_NOT_ASSIGN_ECUPON, null);
                } else {
                    if (voucherAssigns.get(0).getQuantity() <= 0) {
                        throw new CommOrderException(CommOrderException.MSG_ERROR_PDM_MEMBER_NOT_ASSIGN_ECUPON, null);
                    }
                }
                // 更新数量为0
                voucherAssign.setQuantity(0L);
                voucherAssignMapper.updateVoucherAssignQty(voucherAssign);
            }

            orderPaymentMapper.insertSelective(orderPayment);
        }
    }

    @Override
    public boolean valiOrderPaymentAfterPay(IRequest request, List<OrderPayment> orderPayments, SalesOrder salesOrder)
            throws CommOrderException {
        if (!orderPayments.isEmpty()) {
            // 0.订单状态：已完成
            String orderStatus = salesOrder.getOrderStatus();
            if (!OrderConstants.SALES_STATUS_COMPELETED.equals(orderStatus)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_NOT_EDIT_PAYMENT, null);
            }
            // 1.判断输入值是否为空值
            if (validatePaymentIsEmpty(orderPayments)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_PAYMENT_EMPTY, null);
            }
            // 2.校验Ipoint用户所下订单支付方式
            if (!validateIpointUserOrder(request, orderPayments, salesOrder)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_IPOINT_PAY_METHOD, null);
            }
            // 3.校验页面中填写支付金额与实际支付金额是否一致
            if (!validatePaymentAmount(orderPayments)) {
                throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AMOUNT_ERROR, null);
            }
        }
        return true;
    }

}
