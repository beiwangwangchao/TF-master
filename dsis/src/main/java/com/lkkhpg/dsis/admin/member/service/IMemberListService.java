/*
 *
 */
package com.lkkhpg.dsis.admin.member.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 导入会员列表接口类.
 * 
 * @author mclin
 */
public interface IMemberListService extends ProxySelf<IMemberListService> {
    /**
     * 导入会员列表信息.
     * 
     * @param request
     *            请求上下文
     * @param memberList
     *            会员列表Dto
     * @return 会员列表Dto
     * @throws CommMemberException
     *             会员统一异常
     */
    MemberList insert(IRequest request, @StdWho MemberList memberList) throws CommMemberException;

    /**
     * 批量导入会员列表信息.
     * 
     * @param request
     *            请求上下文
     * @param list
     *            会员列表List
     * @param page
     *            页
     * @param pageSize
     *            分页大小
     * @return List 会员List
     * @throws CommMemberException
     *             会员统一异常
     */
    List<Member> batchInsert(IRequest request, @StdWho List<MemberList> list, int page, int pageSize)
            throws CommMemberException;

    /**
     * 验证优惠券导入的会员列表.
     * 
     * @param request
     *            请求上下文
     * @param list
     *            会员列表List
     * @param idType
     *            mentioneId的类型，优惠券或消息
     * @param mentionId
     *            优惠券ID或消息ID
     * @return List 会员列表List
     */
    List<MemberList> validate(IRequest request, List<MemberList> list, String idType, Long mentionId);
}
