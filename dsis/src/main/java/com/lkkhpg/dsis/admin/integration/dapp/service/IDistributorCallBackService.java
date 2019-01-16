/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * POS distributor callback接口类.
 * 
 * @author linyuheng
 */
public interface IDistributorCallBackService {

    /**
     * 实时回调.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            member DTO
     * @param action
     *            请求动作
     */
    void callbackDistributor(IRequest request, Member member, String action);

    /**
     * 同步标记为N的会员call back.
     */
    void callbackDistributorNoSync();
}
