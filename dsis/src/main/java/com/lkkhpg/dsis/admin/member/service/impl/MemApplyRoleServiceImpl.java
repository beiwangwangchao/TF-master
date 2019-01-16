/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.member.service.IMemApplyRoleService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemApplyRole;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemApplyRoleMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * 会员申请角色变更实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class MemApplyRoleServiceImpl implements IMemApplyRoleService {

    private final Logger logger = LoggerFactory.getLogger(MemApplyRoleServiceImpl.class);

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemApplyRoleMapper memApplyRoleMapper;

    @Autowired
    private AccountMapper sysAccountMapper;

    @Override
    public String getApplyNumber(IRequest request) {
        // 流水号段数设置
        DocSequence docSequence;
        docSequence = new DocSequence(MemberConstants.SEQ_APPLY_ROLE, null, null, null, null, null);
        String applyNumber = docSequenceService.getSequence(request, docSequence, "", MemberConstants.APPLY_ROLE_LENGTH,
                MemberConstants.APPLY_ROLE_START_NUMBER);
        return applyNumber;
    }

    @Override
    public void insertRecord(IRequest request, Member member) {
        MemApplyRole memApplyRole = new MemApplyRole();
        memApplyRole.setApplyNumber(self().getApplyNumber(request));
        memApplyRole.setApplyDate(new Date());
        memApplyRole.setMemberId(member.getMemberId());
        memApplyRole.setMarketId(member.getMarketId());
        memApplyRole.setStatus(MemberConstants.CHANGE_ROLE_APPROVE_STATUS_APING);
        memApplyRole.setOldRole(MemberConstants.MM_ROLE_VIP);
        memApplyRole.setNewRole(MemberConstants.MM_ROLE_DIS);
        memApplyRole.setCreatedBy(request.getAccountId());
        memApplyRole.setLastUpdatedBy(request.getAccountId());
        memApplyRoleMapper.insert(memApplyRole);
    }

    @Override
    public void updatePendingRecord(IRequest request) {
        List<MemApplyRole> memApplyRoles = memApplyRoleMapper.selectAllPendingRecords();
        try {
            for (MemApplyRole memApplyRole : memApplyRoles) {
                // 更新申请表的申请状态为审核通过
                memApplyRole.setStatus(MemberConstants.CHANGE_ROLE_APPROVE_STATUS_APED);
                memApplyRole.setApproveDate(new Date());
                memApplyRoleMapper.updateByPrimaryKeySelective(memApplyRole);
                if (logger.isDebugEnabled()) {
                    logger.debug("更新申请号为{}的记录", memApplyRole.getApplyNumber());
                }
                // 更新会员的角色为经销商,接受奖金字段更新为Y,两个同步标记改为N
                Member member = memberMapper.selectByPrimaryKey(memApplyRole.getMemberId());
                member.setMemberRole(MemberConstants.MM_ROLE_DIS);
                member.setAcceptBonus(BaseConstants.YES);
                member.setSyncFlag(BaseConstants.NO);
                member.setDappSyncFlag(BaseConstants.NO);
                memberMapper.updateByPrimaryKeySelective(member);
                if (logger.isDebugEnabled()) {
                    logger.debug("更新会员{}", member.getMemberCode());
                }
                // 更新会员密码
                Account account = sysAccountMapper.selectByMemberId(member.getMemberId());
                // 账号不存在则创建账号
                if (account == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("account for memberId {} is not exist, create new member account",
                                member.getMemberId());
                    }
                    // account = createMemAccount(request, member);
                    if (logger.isDebugEnabled()) {
                        logger.debug("create member success, account number {}", member.getMemberCode());
                    }
                }
                // 账号临时密码失效时间(获取配置文件中的数据)
                String expiryHourStr = profileService.getProfileValue(request, MemberConstants.TEMP_PWD_EXPIRY_DATE);
                int expiryHour;
                if (expiryHourStr == null) {
                    expiryHour = 1;
                } else {
                    expiryHour = Integer.parseInt(expiryHourStr);
                }
                Date expiryDate = DateUtils.addHours(new Date(), expiryHour);
                String tempPassword = accountService.generateRandomPassword();
                accountService.updatePassword(account.getAccountId(), tempPassword, expiryDate, BaseConstants.YES);
                if (logger.isDebugEnabled()) {
                    logger.debug("call accountService.updatePassword success");
                }
                if (StringUtils.isNotEmpty(member.getAreaCode()) && StringUtils.isNotEmpty(member.getPhoneNo())) {
                    // 发送邮件
                    String sendSmsMsg = profileService.getProfileValue(request, MemberConstants.PROFILE_MM_SEND_SMS_MSG);
                    if (BaseConstants.YES.equals(sendSmsMsg)) {
                        // 调用接口发送短信/邮件通知用户
                        List<MessageReceiver> receiverlistForSms = new ArrayList<MessageReceiver>();
                        Map<String, Object> data = new HashMap<String, Object>();
                        data.put("loginName", account.getLoginName());
                        data.put("tmpPassword", tempPassword);
                        data.put("limit", expiryHour);
                        MessageReceiver receiverForMember = new MessageReceiver();
                        receiverForMember.setMessageAddress(member.getAreaCode() + member.getPhoneNo());
                        receiverForMember.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                        receiverForMember.setReceiverId(member.getMemberId());
                        receiverlistForSms.add(receiverForMember);
                        try {
                            messageService.sendSmsMessage(-1L, member.getMarketId(),
                                    MemberConstants.VIP_CHANGE_TO_DISTRIBUTOR_WELCOME, null, data, receiverlistForSms);
                            if (logger.isInfoEnabled()) {
                                logger.info("VIP会员{}可以转为经销商.", member.getMemberCode());
                            }
                        } catch (Exception e) {
                            if (logger.isErrorEnabled()) {
                                logger.error("发送短信失败!");
                            }
                        }
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("会员{}的区号或手机号为空,不发送短信",member.getMemberCode());
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("更新失败" + e.getMessage());
            }
        }

    }

    @Override
    public List<MemApplyRole> queryRecords(IRequest request, MemApplyRole memApplyRole) {
        return memApplyRoleMapper.selectAllRecords(memApplyRole);
    }

}
