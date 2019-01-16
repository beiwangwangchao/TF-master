/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员钱包service接口.
 *
 * @author gulin
 */
public interface IMemWalletService extends ProxySelf<IMemWalletService> {
    /**
     * 根据会员ID查询会员银行卡信息.
     * 
     * @param request
     *            统一上下文.
     * @param memberId
     *            会员ID.
     * @return 返回会员银行卡信息集合.
     */
    List<MemCard> queryMemCards(IRequest request, Long memberId);
    
    /**
     * 根据会员ID查询会员优惠券信息.
     * 
     * @param request
     *            统一上下文.
     * @param memberId
     *            会员ID.
     * @param isUsed
     *            是否使用标记.
     * @param scope
     *            使用范围.
     * @return 返回优惠券集合及本位币.
     */
    List<Object> queryVouchers(IRequest request, Long memberId, String isUsed, String scope);
}
