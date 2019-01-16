/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 导入会员列表接口类.
 * 
 * @author mclin
 */
public interface ICommMemberListService extends ProxySelf<ICommMemberListService> {
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
     * @throws CommMemberException
     *             会员统一异常
     */
    Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException;

    /**
     * 导入会员.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            导入批id
     * @param groupType
     *            单据类型
     * @return 导入批id
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    Long importMembers(IRequest request, Long groupId, String groupType, Long mentionId, BigDecimal maxMember)
            throws CommSystemProfileException;

    /**
     * 删除会员.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            导入批id
     * @param groupType
     *            单据类型
     * @param mentionId
     *            单据id
     * @return 导入批id
     */
    Long deleteImport(IRequest request, Long groupId, Long mentionId);

    /**
     * 查询导入的会员.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            导入批id
     * @return 导入会员
     */
    List<MemberList> queryImport(IRequest request, Long groupId);

    /**
     * 会员查询页-发送通知，保存通知人.
     * 
     * @param request
     *            统一上下文
     * @param memberLists
     *            选中的会员信息
     */
    void saveReceiver(IRequest request, @StdWho MemberList memberLists);

}
