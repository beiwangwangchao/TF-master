/*
 *
 */

package com.lkkhpg.dsis.mws.service;

import com.lkkhpg.dsis.mws.dto.MemberInfo;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * mws会员信息Service接口.
 * 
 * @author guanghui.liu
 */
public interface IMemberInfoService extends ProxySelf<IMemberInfoService> {

    /**
     * 获取会员信息.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return memberInfo mws会员信息Dto
     */
    MemberInfo queryMemberInfo(IRequest request, Long memberId);

    /**
     * 获取会员RemainingBalance.
     * 
     * @param request
     *            请求上下文
     * @param memberId
     *            会员Id
     * @return memberInfo mws会员信息Dto
     */
    MemberInfo queryRemBal(IRequest request, Long memberId);

    /**
     * 更新会员信息.
     * 
     * @param request
     * @param memberInfo
     * @return 返回更新的条数
     */
    @AuditEntry("MEMBER")
    int updateMemberInfo(IRequest request, @StdWho MemberInfo memberInfo);
    
    int updateMemberLastUpdateDate(MemberInfo memberInfo);

	int updateMemberHomeAddressArchive(Long memberId);

	int updateMemberCtactAddressArchive(Long memberId);

	int updateMemberAccountArchive(Long memberId);

}
