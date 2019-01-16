/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.member.mapper.MemberListMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.ISysMsMessageService;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageAssignMapper;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 消息实现层.
 * 
 * @author HuangJiaJing
 *
 */
@Service
@Transactional
public class SysMsMessageServiceImpl implements ISysMsMessageService {

    public static final String MEM = "MEM";
    public static final String USER = "USER";
    public static final String SMS = "SMS";
    public static final int FIVE = 500;

    @Autowired
    private SysMsMessageMapper msMessageMapper;

    @Autowired
    private SysMsMessageAssignMapper msMessageAssignMapper;

    @Autowired
    private MemberListMapper memberListMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<SysMsMessage> queryBySysMsMessage(IRequest requestContext, SysMsMessage sysMsMessage, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SysMsMessage> list = msMessageMapper.queryBySysMsMessage(sysMsMessage);
        return list;
    }

    @Override
    public List<SysMsMessage> saveSysMsMessage(IRequest requestContext, List<SysMsMessage> sysMsMessages) {
        for (SysMsMessage sysMsMessage : sysMsMessages) {
            if (DTOStatus.ADD.equals(sysMsMessage.get__status())) {
                msMessageMapper.insert(sysMsMessage);
                if (!sysMsMessage.getMemberAssigns().isEmpty()) {
                    processMeMessageAssign(sysMsMessage.getMemberAssigns(), sysMsMessage);
                }
                if (!sysMsMessage.getUserAssigns().isEmpty()) {
                    processUserMessageAssign(sysMsMessage.getUserAssigns(), sysMsMessage);
                }
            } else if (DTOStatus.UPDATE.equals(sysMsMessage.get__status())) {
                msMessageMapper.updateByPrimaryKeySelective(sysMsMessage);
                if (!sysMsMessage.getMemberAssigns().isEmpty()) {
                    processMeMessageAssign(sysMsMessage.getMemberAssigns(), sysMsMessage);
                }
                if (!sysMsMessage.getUserAssigns().isEmpty()) {
                    processUserMessageAssign(sysMsMessage.getUserAssigns(), sysMsMessage);
                }
            }
        }
        return sysMsMessages;
    }

    /**
     * 判断会员增加或删除.
     * 
     * @param assigns
     *            消息assigns集合
     * @param sysMsMessage
     *            消息dto
     */
    public void processMeMessageAssign(List<SysMsMessageAssign> assigns, SysMsMessage sysMsMessage) {
        for (SysMsMessageAssign sysMsMessageAssign : assigns) {
            if (DTOStatus.ADD.equals(sysMsMessageAssign.get__status())) {
                sysMsMessageAssign.setMsMessageId(sysMsMessage.getMsMessageId());
                sysMsMessageAssign.setAssignType(MEM);
                sysMsMessageAssign.setAssignValue(Long.toString(sysMsMessageAssign.getMemberId()));
                msMessageAssignMapper.insert(sysMsMessageAssign);
            } else if (DTOStatus.DELETE.equals(sysMsMessageAssign.get__status())) {
                msMessageAssignMapper.deleteByPrimaryKey(sysMsMessageAssign.getAssignId());
            }
        }
    }

    /**
     * 判断用户增加或删除.
     * 
     * @param assigns
     *            消息assigns集合
     * @param sysMsMessage
     *            消息dto
     */
    public void processUserMessageAssign(List<SysMsMessageAssign> assigns, SysMsMessage sysMsMessage) {
        for (SysMsMessageAssign sysMsMessageAssign : assigns) {
            if (DTOStatus.ADD.equals(sysMsMessageAssign.get__status())) {
                sysMsMessageAssign.setMsMessageId(sysMsMessage.getMsMessageId());
                sysMsMessageAssign.setAssignType(USER);
                sysMsMessageAssign.setAssignValue(Long.toString(sysMsMessageAssign.getUserId()));
                msMessageAssignMapper.insert(sysMsMessageAssign);
            } else if (DTOStatus.DELETE.equals(sysMsMessageAssign.get__status())) {
                msMessageAssignMapper.deleteByPrimaryKey(sysMsMessageAssign.getAssignId());
            }
        }
    }

    @Override
    public List<SysMsMessageAssign> queryByMmMessage(IRequest requestContext, SysMsMessageAssign messageAssign) {
        List<SysMsMessageAssign> list = msMessageAssignMapper.queryByMemAssign(messageAssign);
        return list;
    }

    @Override
    public List<SysMsMessageAssign> queryByUserMessage(IRequest requestContext, SysMsMessageAssign messageAssign) {
        List<SysMsMessageAssign> list = msMessageAssignMapper.queryByUserAssign(messageAssign);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importMembers(IRequest request, Long groupId, Long mentionId) throws CommSystemProfileException {
        // 根据groupid查处会员，写进活动会员关系表
        List<MemberList> memberLists = memberListMapper.getMemberListByGroupId(groupId);
        for (MemberList member : memberLists) {
            Long memberId = member.getMemberId();
            Member mem = new Member();
            mem.setMemberId(memberId);
            // List<Member> members = memberMapper.queryMembersForOrder(mem);
            SysMsMessageAssign messageAssign = new SysMsMessageAssign();
            messageAssign.setMsMessageId(mentionId);
            messageAssign.setAssignType(MEM);
            messageAssign.setAssignValue(memberId.toString());

            if (msMessageAssignMapper.queryByMemAssign(messageAssign).isEmpty()) {
                msMessageAssignMapper.insert(messageAssign);
            }
        }
        return mentionId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException {
        Long groupId = memberListMapper.getNextGroupId();
        // 验证导入的会员
        for (MemberList memberList : list) {
            MemberList memList = new MemberList();
            String code = memberList.getMemberCode();
            Member member = memberMapper.selectMembersByMemberCode(code);
            memList.setGroupId(groupId);
            memList.setGroupType(MemberConstants.MEMIMPORT_TYPE_MESSAGE);
            memList.setMemberCode(code);
            // 会员是否存在
            if (member == null) {
                memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_ID_FAIL);
            } else {
                Long memMarketId = member.getMarketId();
                String status = member.getStatus();
                // 根据市场
                SysMsMessageAssign assign = new SysMsMessageAssign();
                assign.setAssignValue(member.getMemberId().toString());
                assign.setMsMessageId(mentionId);

                List<SysMsMessageAssign> assignList = msMessageAssignMapper.queryByMemAssign(assign);
                memList.setMemberId(member.getMemberId());

                if (memMarketId == null || !memMarketId.equals(marketId)) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_MARKET_FAIL);
                } else if (MemberConstants.MEMBER_STATUS_TERMINATED.equals(status)
                        || MemberConstants.MEMBER_STATUS_AUTO_TERMINATED.equals(status)
                        || MemberConstants.MEM_STATUS_CHANGE_AUTO_TERMINATE.equals(status)
                        || MemberConstants.MEMBER_STATUS_WCHG.equals(status)
                        || MemberConstants.MEMBER_STATUS_SUSPENDED.equals(status)
                        || MemberConstants.MEMBER_STATUS_PENDING.equals(status)
                        || MemberConstants.MEMBER_STATUS_NEW.equals(status)) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_STATUS_FAIL);
                } else if (!assignList.isEmpty()) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_IMPORT_MESSAGE_EXIST);
                } else {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_ENABLE);
                }
            }
            memberListMapper.insert(memList);
        }
        return groupId;
    }

    @Override
    public List<Member> queryMemberByGroupId(IRequest request, Long groupId) {
        List<Member> memList = memberListMapper.getAllMember(groupId);
        return memList;
    }

    /***
     * 
     * @param content
     *            内容String
     * @param p
     *            >0 .位数
     * @return @tale:
     * @purpose：得到相应位数已过滤html、script、style 标签的内容 内容结尾 为...
     * @author：Simon - 赵振明
     * @CreationTime：Aug 25, 201011:07:06 AM
     */
    public static String getNoHTMLString(String content, int p) {

        if (null == content) {
            return "";
        }
        if (0 == p) {
            return "";
        }

        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(content);
            content = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(content);
            content = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(content);

            content = m_html.replaceAll(""); // 过滤html标签
        } catch (Exception e) {
            return "";
        }

        if (content.length() > p) {
            content = content.substring(0, p);
        }

        return content;
    }

    @Override
    public List<SysMsMessageAssign> queryPublicMessage(IRequest requestContext, SysMsMessageAssign msMessageAssign,
            int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SysMsMessageAssign> list = msMessageAssignMapper.queryPublicAssign(msMessageAssign);
        return list;
    }

}
