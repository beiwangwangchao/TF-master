/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLogistics;
import com.lkkhpg.dsis.common.order.dto.SalesVoucher;
import com.lkkhpg.dsis.common.order.mapper.OmMwsOrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesLogisticsMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesVoucherMapper;
import com.lkkhpg.dsis.common.service.IMwsOrderPaymentService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 支付信息Service接口实现.
 *
 * @author shenqb
 */
@Service
@Transactional
public class MwsOrderPaymentServiceImpl implements IMwsOrderPaymentService {

    private final Logger logger = LoggerFactory.getLogger(MwsOrderPaymentServiceImpl.class);

    @Autowired
    private OmMwsOrderPaymentMapper omMwsOrderPaymentMapper;

    @Autowired
    private SalesVoucherMapper salesVoucherMapper;

    @Autowired
    private SalesLogisticsMapper salesLogisticsMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OmMwsOrderPayment> saveOmMwsOrderPayment(IRequest request, @StdWho List<OmMwsOrderPayment> omMwsOrderPayments) throws CommSystemProfileException {
        for (OmMwsOrderPayment omMwsOrderPayment : omMwsOrderPayments) {
            if (null == omMwsOrderPayment.getPaymentId()) {
                omMwsOrderPaymentMapper.insert(omMwsOrderPayment);
            } else {
                omMwsOrderPaymentMapper.updateByPrimaryKey(omMwsOrderPayment);
            }
        }
        return omMwsOrderPayments;
    }

    @Override
    public boolean deleteOmMwsOrderPayment(IRequest request, List<OmMwsOrderPayment> omMwsOrderPayments) {
        for (OmMwsOrderPayment omMwsOrderPayment : omMwsOrderPayments) {
            omMwsOrderPaymentMapper.deleteByPrimaryKey(omMwsOrderPayment.getPaymentId());
        }

        return true;
    }

    ;


    @Override
    public OmMwsOrderPayment getOmMwsOrderPayment(IRequest request, Long paymentId) {
        return omMwsOrderPaymentMapper.selectByPrimaryKey(paymentId);
    }

    @Override
    public List<OmMwsOrderPayment> getMwsOrderPayments(IRequest request, OmMwsOrderPayment mwsOrderPayment) {
        return omMwsOrderPaymentMapper.selectByAttributes(mwsOrderPayment);
    }

    @Override
    public List<SalesVoucher> getVouchersByOrderId(Long headerId) {
        return salesVoucherMapper.getVouchersByOrderId(headerId);
    }

    @Override
    public SalesLogistics queryFreightByHeaderId(Long headerId) {
        return salesLogisticsMapper.queryFreightByHeaderId(headerId);
    }

    @Override
    public OmMwsOrderPayment queryRemainingBalSum(IRequest request) {
        Long memberId = request.getAttribute(Member.FIELD_MEMBER_ID);
        return omMwsOrderPaymentMapper.queryRemainingBalSum(memberId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateRemainingBalValue(IRequest request, OmMwsOrderPayment omMwsOrderPayment) {
        omMwsOrderPaymentMapper.updateRemainingBalStatus(omMwsOrderPayment);
        return true;
    }

}
