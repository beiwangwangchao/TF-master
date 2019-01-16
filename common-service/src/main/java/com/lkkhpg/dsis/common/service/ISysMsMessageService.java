/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 消息service.
 * 
 * @author HuangJiaJing
 *
 */
public interface ISysMsMessageService extends ProxySelf<ISysMsMessageService> {
    /**
     * 查询消息service.
     * 
     * @param sysMsMessage
     * @return 消息集合
     */
    List<SysMsMessage> queryBySysMsMessage(IRequest requestContext, SysMsMessage sysMsMessage, int page, int pagesize);

    /**
     * 查询会员service.
     * 
     * @param messageAssign
     *            消息dto
     * @return 消息集合
     */
    List<SysMsMessageAssign> queryByMmMessage(IRequest requestContext, SysMsMessageAssign messageAssign);

    /**
     * 查询会员发送结果.
     * 
     * @param messageAssign
     *            消息dto
     * @return 消息集合
     */
    List<SysMsMessageAssign> queryPublicMessage(IRequest requestContext, SysMsMessageAssign messageAssign, int page,
            int pagesize);

    /**
     * 查询用户service.
     * 
     * @param messageAssign
     *            消息dto
     * @return 消息集合
     */
    List<SysMsMessageAssign> queryByUserMessage(IRequest requestContext, SysMsMessageAssign messageAssign);

    /**
     * 保存消息service.
     * 
     * @param sysMsMessages
     * @return 消息集合
     */
    List<SysMsMessage> saveSysMsMessage(IRequest requestContext, @StdWho List<SysMsMessage> sysMsMessages);

    /**
     * 导入会员列表.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     * @param mentionId
     * @return Long
     * @throws CommSystemProfileException
     *             系统统一异常
     */
    Long importMembers(IRequest request, Long groupId, Long mentionId) throws CommSystemProfileException;

    /**
     * 验证消息导入会员.
     * 
     * @param request
     *            请求上下文
     * @param list
     *            会员List
     * @param idType
     *            消息类型
     * @param mentionId
     *            消息ID
     * @param marketId
     *            市场ID
     * @return 响应数据
     * @throws CommMemberException
     */
    Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException;

    /**
     * 根据groupId得到要添加的会员列表.
     * 
     * @param request
     *            请求上下文
     * @param groupId
     *            临时表id
     * @return memberList集合
     */
    List<Member> queryMemberByGroupId(IRequest request, Long groupId);

}
