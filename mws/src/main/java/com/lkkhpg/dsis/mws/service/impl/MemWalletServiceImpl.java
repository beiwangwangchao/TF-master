/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.mapper.MemCardMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.mws.service.IMemWalletService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员钱包service实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional
public class MemWalletServiceImpl implements IMemWalletService {
    @Autowired
    private MemCardMapper memCardMapper;

    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public List<MemCard> queryMemCards(IRequest request, Long memberId) {
        return memCardMapper.selectByMemberIdAndStatus(memberId, MemberConstants.BANKCARD_STATUS_ACTIVE);
    }

    @Override
    public List<Object> queryVouchers(IRequest request, Long memberId, String isUsed, String scope) {
        List<Object> vouchers = new ArrayList<Object>();
        vouchers.add(voucherMapper.queryVouchersByMemberId(memberId, isUsed, scope));
        return vouchers;
    }

}
