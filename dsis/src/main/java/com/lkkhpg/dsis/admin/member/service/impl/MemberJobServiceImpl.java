/*
 *
 */
package com.lkkhpg.dsis.admin.member.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IAppEligibleSuspendService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.member.service.IMemStatusChangeService;
import com.lkkhpg.dsis.admin.member.service.IMemberJobService;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemDistributor;
import com.lkkhpg.dsis.common.member.dto.MemStatusChange;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemWithdrawMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IMemDistributorService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * 会员定时执行service实现类.
 * 
 * @author yuchuan.zeng
 */
@Transactional
@Service
public class MemberJobServiceImpl implements IMemberJobService {

    private Logger logger = LoggerFactory.getLogger(MemberJobServiceImpl.class);

    @Autowired
    private IMemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IMemStatusChangeService memStatusChangeService;

    @Autowired
    private MemWithdrawMapper memWithdrawMapper;

    @Autowired
    private IAppEligibleSuspendService appEligibleSuspendService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMemDistributorService mmDistributorService;

    @Override
    @Transactional
    public boolean addWithdraw(MemWithdraw memWithdraw) {
        return memWithdrawMapper.insertSelective(memWithdraw) != 0;
    }

    @Override
    @Transactional
    public void autoTerminateMemberJob(String memberCode) throws IntegrationException {
        /*
         * 满足以下2点则自动终止用户 1:过去12个月累计PV为0 2:过去12个月没有发展新下线
         */
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);

        Member memberQry = new Member();
        if (memberCode != null && !memberCode.equals("")) {
            memberQry.setMemberCode(memberCode);
        }
        List<Member> members = memberMapper.selectYearAgoValidMembers(memberQry);
        for (Member member : members) {
            request.setAttribute(SystemProfileConstants.MARKET_ID, member.getMarketId());
            // 判断过去12个月有没有发展新下线
            Map<String, Object> resultMap = memberService.validTerminate(member);
            BigDecimal pvSum = (BigDecimal) resultMap.get("pvSum");
            Long downLineSum = (Long) resultMap.get("downLineSum");
            if (pvSum.doubleValue() > 0.0 || downLineSum > 0) {
                if (downLineSum > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "一年内发展了新下线");
                    }
                }
                if (pvSum.doubleValue() > 0.0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "一年内PV不为0");
                    }
                }
            } else {
                MemStatusChange memStatusChange = new MemStatusChange();
                memStatusChange.setApplyDate(new Date());
                memStatusChange.setMarketId(member.getMarketId());
                memStatusChange.setMemberId(member.getMemberId());
                memStatusChange.setApprovalDate(new Date());
                memStatusChange.setApprovalStatus(MemberConstants.SYS_REVIEW_STATUS_YES);
                memStatusChange.setRemark("TerminateMemberJob:memberId为" + member.getMemberId() + "会员自动终止成功");
                memStatusChange.setOperationType(MemberConstants.MEM_STATUS_CHANGE_AUTO_TERMINATE);
                String appNo = gdsUtilService.getAppNo(request);
                memStatusChange.setAppNo(appNo);
                if (memStatusChangeService.saveMemStatusChangeForJob(request, memStatusChange)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("TerminateMemberJob:memberId为{}{}", member.getMemberId(),
                                "的MemStatusChange记录创建成功");
                    }

//                    if (memberService.getRBBymemberId(member.getMemberId()) != 0) {
//                        MemWithdraw memWithdraw = new MemWithdraw();
//                        memWithdraw.setMemberId(member.getMemberId());
//                        memWithdraw.setAmount(BigDecimal.valueOf(memberService.getRBBymemberId(member.getMemberId())));
//                        memWithdraw.setWriteoffType(MemberConstants.MM_MEMWITHDRAW_WRITEOFF_TYPE);
//                        memWithdraw.setMarketId(member.getMarketId());
//                        memWithdraw.setCompanyId(member.getCompanyId());
//                        Map<String, Object> map = new HashMap<String, Object>();
//                        map.put("marketId", member.getMarketId());
//                        memWithdraw.setPeriod(Integer.parseInt(memWithdrawMapper.getPeriodByNow(map) + ""));
//                        memWithdraw.setAdjustmentType(MemberConstants.MM_MEMWITHDRAW_ADJUSTMENT_TYPE);
//                        memWithdraw.setRemark("自动终止创建的冲销结余");
//                        memWithdraw.setStatus(MemberConstants.MM_MEMWITHDRAW_STATUS);
//                        memWithdraw.setCreationDate(new Date());
//                        memWithdraw.setLastUpdateDate(new Date());
//                        if (self().addWithdraw(memWithdraw)) {
//                            if (logger.isDebugEnabled()) {
//                                logger.debug("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "创建冲销结余成功");
//                            }
//                            if (memberService.clearRBalance(member.getMemberId())) {
//                                if (logger.isDebugEnabled()) {
//                                    logger.debug("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "的RB清空成功");
//                                }
//                            } else {
//                                if (logger.isErrorEnabled()) {
//                                    logger.error("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "的RB清空失败");
//                                }
//                            }
//                        } else {
//                            if (logger.isErrorEnabled()) {
//                                logger.error("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "创建冲销结余失败");
//                            }
//                        }
//                    } else {
//                        if (logger.isDebugEnabled()) {
//                            logger.debug("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "的RB=0");
//                        }
//                    }

                    // 调用GDS会员停权接口
                    String appDocNo = appNo;
                    String appPeriod = gdsUtilService.toPeriodString(memStatusChange.getApplyDate());
                    String appMemo = "";
                    String orgCode = member.getMarketCode();
                    appEligibleSuspendService.appEligibleSuspend(request, member.getMemberCode(), appNo, appDocNo,
                            appPeriod, appMemo, orgCode);
                } else {
                    if (logger.isErrorEnabled()) {
                        logger.error("TerminateMemberJob:memberId为{}{}", member.getMemberId(), "会员自动终止失败");
                    }
                }
            }
        }
    }

    @Override
    // @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
    // Exception.class)
    public void vipToDistributor() throws CommMemberException {
        boolean memberEmail = false;
        boolean sponsorEmail = false;
        IRequest request = RequestHelper.newEmptyRequest();
        request.setAccountId(-1L);
        request.setLocale(BaseConstants.DEFAULT_LANG);
        Member memberQry = new Member();
        memberQry.setMemberRole(MemberConstants.MM_ROLE_VIP);
        List<Member> members = memberMapper.selectValidMembers(memberQry);
        for (Member member : members) {
            memberEmail = false;
            sponsorEmail = false;
            Map<String, Object> result = commMemberService.validateForVIPToDIS(request, member.getMemberId());
            boolean isChange = (boolean) result.get("qualifiedFlag");
            if (isChange) {
                // 初始化会员转经销商记录
                MemDistributor mmDistributor = new MemDistributor();
                mmDistributor.setMemberId(member.getMemberId());
                mmDistributor.setMessageDate(new Date());

                String sendSmsMsg = profileService.getProfileValue(request, MemberConstants.PROFILE_MM_SEND_SMS_MSG);
                if (BaseConstants.YES.equals(sendSmsMsg)) {
                    // 调用接口发送短信/邮件通知用户
                    List<MessageReceiver> receiverlistForSms = new ArrayList<MessageReceiver>();
                    List<MessageReceiver> receiverlistForEmail = new ArrayList<MessageReceiver>();
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("loginName", member.getMemberCode());
                    // 设置会员本人的短信内容
                    MessageReceiver receiverForMember = new MessageReceiver();
                    receiverForMember.setMessageAddress(member.getAreaCode() + member.getPhoneNo());
                    receiverForMember.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                    receiverForMember.setReceiverId(member.getMemberId());
                    receiverlistForSms.add(receiverForMember);
                    // 设置会员本人的邮件内容
                    if (member.getEmail() != null) {
                        receiverForMember.setMessageAddress(member.getEmail());
                        receiverlistForEmail.add(receiverForMember);
                        memberEmail = true;
                    }
                    // 获取会员上线
                    String sponsorNo = member.getSponsorNo();
                    if (sponsorNo != null) {
                        Member memberForSponsor = new Member();
                        memberForSponsor.setMemberCode(sponsorNo);
                        List<Member> memberForSponsors = memberMapper.selectValidMembers(memberForSponsor);
                        if (memberForSponsors != null && !memberForSponsors.isEmpty()) {
                            memberForSponsor = memberForSponsors.get(0);
                        }

                        if (memberForSponsor.getMemberId() != null) {
                            // 设置会员上线的短信内容
                            MessageReceiver receiverForSponsor = new MessageReceiver();
                            receiverForSponsor
                                    .setMessageAddress(memberForSponsor.getAreaCode() + memberForSponsor.getPhoneNo());
                            receiverForSponsor.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                            receiverForSponsor.setReceiverId(memberForSponsor.getMemberId());
                            receiverlistForSms.add(receiverForSponsor);
                            // 设置会员上线的邮件内容
                            if (memberForSponsor.getEmail() != null) {
                                receiverForSponsor.setMessageAddress(memberForSponsor.getEmail());
                                receiverlistForEmail.add(receiverForSponsor);
                                sponsorEmail = true;
                            }
                        } else {
                            if (logger.isErrorEnabled()) {
                                logger.error("VIP会员{}找不到上线.", member.getMemberCode());
                            }
                        }
                    }
                    try {
                        // 添加会员转经销商记录
                        mmDistributorService.saveMemDistributor(request, mmDistributor);

                        messageService.sendSmsMessage(-1L, member.getMarketId(),
                                MemberConstants.VIP_CHANGE_TO_DISTRIBUTOR, null, data, receiverlistForSms);
                        if (memberEmail || sponsorEmail) {
                            messageService.sendEmailMessage(-1L, null, MemberConstants.VIP_CHANGE_TO_DISTRIBUTOR,
                                    "DO_NOT_REPLY", data, receiverlistForEmail, null);
                        }
                        if (logger.isInfoEnabled()) {
                            logger.info("VIP会员{}可以转为经销商.", member.getMemberCode());
                        }
                    } catch (Exception e) {
//                        e.printStackTrace();
                        if (logger.isErrorEnabled()) {
                            logger.error("发送短信或邮件失败!");
                        }
                    }

                }
            }
        }
    }

}
