/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.order.service.IOrderPaymentService;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.mapper.MemAttributeMapper;
import com.lkkhpg.dsis.common.order.dto.CommodityList;
import com.lkkhpg.dsis.common.order.dto.OrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.CommodityListMapper;
import com.lkkhpg.dsis.common.order.mapper.OrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ICommOrderPaymentService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * 订单支付实现类.
 * 
 * @author houmin
 */
@Service
@Transactional
public class OrderPaymentServiceImpl implements IOrderPaymentService {

    @Autowired
    private CommodityListMapper commodityListMapper;

    @Autowired
    private ICommOrderPaymentService commOrderPaymentService;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private MemAttributeMapper memAttributeMapper;

    @Autowired
    private IAESClientService aescClientService;

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    // @Autowired
    // private OmMwsOrderPaymentMapper omMwsOrderPaymentMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CommodityList> selectByOrderHeaderId(IRequest request, Long orderHeaderId) {
        List<CommodityList> list = commodityListMapper.selectItemsByOrderHeaderId(orderHeaderId);

        if (list.size() > 0) {
            Long memberId = list.get(0).getMemberId();
            if (memberId != null) {
                List<MemAccountingBalance> memAccountingBalances = commMemberService.queryMemAccountingBalances(request,
                        memberId);
                /*
                 * OmMwsOrderPayment rbSum =
                 * omMwsOrderPaymentMapper.queryRemainingBalSum(memberId); if
                 * (null != rbSum) { for (MemAccountingBalance temp :
                 * memAccountingBalances) { if
                 * (MemberConstants.ACCOUNTING_TYPE_RB.equals(temp.
                 * getAccountingType())) {
                 * temp.setBalance(temp.getBalance().subtract(rbSum.
                 * getPaymentAmount())); break; } } }
                 */
                list.get(0).setMemAccountingBalances(memAccountingBalances);
            }
        }

        return list;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Long> createOrderPayment(IRequest request, List<OrderPayment> orderPayments, Long orderHeaderId)
            throws CommOrderException, CommDeliveryException, CommMemberException, CommSystemProfileException {
        return commOrderPaymentService.createOrderPayment(request, orderPayments, orderHeaderId);
    }

    @Override
    public List<Voucher> queryEcupon(IRequest request, Long orderHeaderId) {
        SalesOrder order = salesOrderMapper.selectByPrimaryKey(orderHeaderId);
        Long memberId = order.getMemberId();
        return voucherService.getMemberVouchersForVIP(request, memberId);
    }

    @Override
    public List<String> checkPassword(IRequest request, MemAttribute attribute) {
        List<String> result = new ArrayList<String>();
        MemAttribute queryAttribute = new MemAttribute();
        queryAttribute.setAttribute(OrderConstants.MEMBER_ATTRIBUTE_CARD_PASS);
        queryAttribute.setMemberId(attribute.getMemberId());
        List<MemAttribute> attributes = memAttributeMapper.selectMemAttributes(queryAttribute);
        if (attributes.isEmpty()) {
            result.add("empty");
        } else {
            if (attribute.getValue().equals(attributes.get(0).getValue())) {
                result.add("true");
            } else {
                result.add("error");
            }
        }
        return result;
    }

    @Override
    public List<MemCard> queryBankInfo(IRequest request, Long memberId) {
        List<MemCard> cards = commMemberService.autoQueryCard(request, memberId);
        for (MemCard temp : cards) {
            String cardNumber = aescClientService.decrypt(temp.getCardNumber());
            temp.setCardNumber(cardNumber);
        }
        return cards;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updatePaymentAfterPay(IRequest request, Long headerId, List<OrderPayment> orderPayments)
            throws CommOrderException {
        SalesOrder order = salesOrderMapper.selectByPrimaryKey(headerId);
        // 校验支付信息
        if (commOrderPaymentService.valiOrderPaymentAfterPay(request, orderPayments, order)) {
            List<OrderPayment> orginPayments = orderPaymentMapper.selectByHeaderId(headerId);
            if (orginPayments == null || orginPayments.isEmpty() || orginPayments.size() < 1) {
                // TODO 该订单不存在支付行信息，不允许修改支付信息
                throw new CommOrderException(CommOrderException.MSG_ERROR_NOT_ALLOW_PAY, null);
            }
            for (OrderPayment orderPayment : orderPayments) {
                switch (orderPayment.get__status()) {
                case DTOStatus.ADD:
                    orderPayment.setStatus(OrderConstants.PAYMENT_STATUS_NEW);
                    orderPaymentMapper.insertSelective(orderPayment);
                    break;
                case DTOStatus.UPDATE:
                    orderPaymentMapper.updateByPrimaryKeySelective(orderPayment);
                    break;
                case DTOStatus.DELETE:
                    orderPaymentMapper.deleteByPrimaryKey(orderPayment.getPaymentId());
                    break;
                default:
                    break;
                }
            }

        }
        return true;
    }

}
