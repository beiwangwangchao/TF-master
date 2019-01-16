/*
 *
 */

package com.lkkhpg.dsis.admin.member.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemApplyRoleService;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.MemApplyRole;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemRank;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemAccountingBalanceMapper;
import com.lkkhpg.dsis.common.member.mapper.MemApplyRoleMapper;
import com.lkkhpg.dsis.common.member.mapper.MemAttributeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemCardMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRankMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRelationshipMapper;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.member.mapper.MemWithdrawMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberListMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IMemberBalanceTrxService;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.GdealerPersonalInfo;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.AccountRelation;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.attachment.AttachmentMapper;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.AccountRelationMapper;
import com.lkkhpg.dsis.platform.security.PasswordManager;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * 会员Service接口实现.
 *
 * @author frank.li
 */
@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

    private Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemRankMapper memRankMapper;

    @Autowired
    private MemRelationshipMapper relationshipMapper;

    @Autowired
    private MemAttributeMapper attributeMapper;

    @Autowired
    private MemSiteMapper siteMapper;

    @Autowired
    private MemAccountMapper accountMapper;

    @Autowired
    private MemCardMapper cardMapper;

    @Autowired
    private MemRankMapper rankMapper;

    @Autowired
    private MemAccountingBalanceMapper accountingBalanceMapper;

    @Autowired
    private MemWithdrawMapper memWithdrawMapper;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private AccountRelationMapper accountRelationMapper;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private MemberListMapper memberListMapper;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private IMemberBalanceTrxService memberBalanceTrxService;

    @Autowired
    private IAESClientService aescClientService;

    @Autowired
    private AccountMapper sysAccountMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private IMemApplyRoleService memApplyRoleService;

    @Autowired
    private MemApplyRoleMapper memApplyRoleMapper;

    @Override
    public List<MemRelationship> queryRelationships(IRequest request, Long memberId, int page, int pagesize) {
        // PageHelper.startPage(page, pagesize); //单个会员的信息不需要分页
        return relationshipMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemAttribute> queryAttributes(IRequest request, Long memberId, int page, int pagesize) {
        // PageHelper.startPage(page, pagesize); //单个会员的信息不需要分页
        return attributeMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemSite> querySites(IRequest request, Long memberId, int page, int pagesize) {
        // PageHelper.startPage(page, pagesize); //单个会员的信息不需要分页
        return siteMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemAccount> queryAccounts(IRequest request, Long memberId, int page, int pagesize) {
        // PageHelper.startPage(page, pagesize); //单个会员的信息不需要分页
        return accountMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemCard> queryCards(IRequest request, Long memberId, int page, int pagesize) {
        // PageHelper.startPage(page, pagesize); //单个会员的信息不需要分页
        return cardMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemCard> autoQueryCard(IRequest request, Long memberId) {
        return commMemberService.autoQueryCard(request, memberId);
    }

    @Override
    public List<MemAccountingBalance> queryMemAccountingBalances(IRequest request, Long memberId) {
        return commMemberService.queryMemAccountingBalances(request, memberId);
    }

    @Override
    public List<MemAccountingTrx> queryMemAccountingTrxs(IRequest request, Long memberId, String accountingType,
                                                         Date trxDateFrom, Date trxDateTo, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return commMemberService.queryMemAccountingTrxs(request, memberId, accountingType, trxDateFrom, trxDateTo, page,
                pagesize);
    }

    @Override
    public List<MemRank> queryRanks(IRequest request, String memberCode, Date monthFrom, Date monthTo, int page,
                                    int pagesize) throws CommMemberException {
        PageHelper.startPage(page, pagesize);
        List<MemRank> queryRanks = commMemberService.queryRanks(request, memberCode, monthFrom, monthTo, page,
                pagesize);
        return queryRanks;
    }

    @Override
    public List<MemWithdraw> queryWithdraws(IRequest request, Long memberId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return commMemberService.queryWithdraws(request, memberId, page, pagesize);
    }

    @Override
    public List<VoucherAssign> queryVoucherAssigns(IRequest request, Long memberId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return commMemberService.queryVoucherAssigns(request, memberId, page, pagesize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> queryDownLines(IRequest request, Long memberId) {
        return commMemberService.queryDownLines(request, memberId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> queryMembers(IRequest request, Member member, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return commMemberService.queryMembers(request, member, page, pagesize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Member getMember(IRequest request, Long memberId) {
        return commMemberService.getMember(request, memberId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member saveMember(IRequest request, @StdWho Member member) throws CommMemberException {
        // 调用API创建会员
        Member result = commMemberService.saveMember(request, member);
        if (logger.isDebugEnabled()) {
            logger.debug("call commMemberService.saveMember success, memberId: {}", result.getMemberId());
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createMemAccount(IRequest request, Member member) throws CommMemberException {
        // 校验会员账户是否已存在
        if (accountService.selectByLoginName(member.getMemberCode()) != null) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_MEM_ACCOUNT_EXIST,
                    new Object[]{member.getMemberCode()});
        }

        try {
            // 插入账户表
            Account account = new Account();
            account.setLoginName(member.getMemberCode());
            account.setPassword(passwordManager.getDefaultPassword()); // 初始密码有随机生成
            account.setStatus(Account.STATUS_ACTIVE);
            account.setFirstLoginFlag(BaseConstants.YES);
            account.setPwdExpiryDate(null);
            account.setCreatedBy(member.getCreatedBy());
            account.setCreationDate(member.getCreationDate());
            account.setLastUpdatedBy(member.getLastUpdatedBy());
            account.setLastUpdateDate(member.getLastUpdateDate());
            Account dbAccount = accountService.insert(account);
            if (logger.isDebugEnabled()) {
                logger.debug("call accountService.insert success");
            }

            // 插入账户关联表
            AccountRelation accountRel = new AccountRelation();
            accountRel.setAccountId(dbAccount.getAccountId());
            accountRel.setAccountType(Account.ACCOUNT_TYPE_MEM);
            accountRel.setRelPartyId(member.getMemberId());
            accountRel.setCreatedBy(member.getCreatedBy());
            accountRel.setCreationDate(member.getCreationDate());
            accountRel.setLastUpdatedBy(member.getLastUpdatedBy());
            accountRel.setLastUpdateDate(member.getLastUpdateDate());
            accountRelationMapper.insert(accountRel);
            if (logger.isDebugEnabled()) {
                logger.debug("call accountRelationMapper.insert success");
            }

            // 由配置文件控制是否发送短信
            String sendSmsMsg = profileService.getProfileValue(request, MemberConstants.PROFILE_MM_SEND_SMS_MSG);
            if (BaseConstants.YES.equals(sendSmsMsg)) {
                // 调用接口发送短信通知用户
                List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
                MessageReceiver receiver = new MessageReceiver();
                Map<String, Object> data = new HashMap<String, Object>();
                receiver.setMessageAddress(member.getAreaCode() + member.getPhoneNo());
                receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                receiver.setReceiverId(member.getMemberId());
                receiverlist.add(receiver);
                // 设置邮件模板里的数据
                data.put("tmpPassword", passwordManager.getDefaultPassword());
                data.put("limit", UserConstants.DEFAULT_PAGE); // 临时口令有效期为1个小时
                data.put("loginName", account.getLoginName());
                // messageService.addMessage(null, "phone_user_temp_pwd", data,
                // null, receiverlist);
                messageService.addSmsMessage(null, "forget_password", "phone_user_temp_pwd", data, receiverlist);
                if (logger.isDebugEnabled()) {
                    logger.debug("call messageService.addMessage success");
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error in MemberServiceImpl.createMemAccount.", e);
            }

            throw new CommMemberException(CommMemberException.MSG_ERROR_CREATE_MEM_ACCOUNT,
                    new Object[]{e.getMessage()});
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member submitMember(IRequest request, Member member) throws MemberException {
        if (member.getMemberId() == null) {
            throw new MemberException(MemberException.MSG_ERROR_NO_MEMBER, null);
        } else {
            member.setStatus(MemberConstants.MEMBER_STATUS_PENDING);
            memberMapper.updateStatusForNew(member);
        }

        return member;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long changeOwnership(IRequest request, Long sourceMemberId, Long tempMemberId) throws MemberException {
        if (logger.isDebugEnabled()) {
            logger.debug("sourceMemberId: {}", new Object[]{sourceMemberId});
            logger.debug("tempMemberId: {}", new Object[]{tempMemberId});
        }

        // 获取原会员信息
        Member sourceMember = memberMapper.selectByPrimaryKey(sourceMemberId);

        if (logger.isDebugEnabled()) {
            logger.debug("get source sourceMember, memberCode: {}", new Object[]{sourceMember.getMemberCode()});
        }

        // 从原会员创建快照会员
        Long snapshootMemberId = createSnapshootMember(request, sourceMember);

        if (logger.isDebugEnabled()) {
            logger.debug("create snapshoot member, snapshootMemberId: {}", new Object[]{snapshootMemberId});
        }

        // 应用预存会员信息, 预存会员信息覆盖至原会员
        applyTempMember(request, sourceMember, tempMemberId);

        if (logger.isDebugEnabled()) {
            logger.debug("apply temp member success");
        }

        return snapshootMemberId;
    }

    /**
     * 创建快照会员，即更新原会员相关表的memberId为新的序列memberId.
     *
     * @param request      请求上下文
     * @param sourceMember 原会员DTO 原会员卡号
     * @return 快照会员Id
     * @throws MemberException 会员统一异常
     */
    public Long createSnapshootMember(IRequest request, Member sourceMember) throws MemberException {

        Long sourceMemberId = sourceMember.getMemberId();
        String sourceMemberCode = sourceMember.getMemberCode();
        String snapshootMemberCode;

        // 获取历史快照memberCode
        Long maxSnapshootSeq = memberMapper.selectMaxSeqByMemberCode(sourceMemberCode);
        if (logger.isDebugEnabled()) {
            logger.debug("maxSnapshootSeq: {}", new Object[]{maxSnapshootSeq});
        }

        // 获取快照memberCode，快照会员的会员卡号="会员卡号" + "最大快照序号+1"
        if (maxSnapshootSeq == null) {
            snapshootMemberCode = sourceMemberCode + "_1";
        } else {
            snapshootMemberCode = sourceMemberCode + "_" + (maxSnapshootSeq + 1);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("snapshootMemberCode: {}", new Object[]{snapshootMemberCode});
        }

        // 获取快照memberId
        Long snapshootMemberId = memberMapper.selectKey();

        if (logger.isDebugEnabled()) {
            logger.debug("snapshootMemberId: {}", new Object[]{snapshootMemberId});
        }

        Date nowDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

        // 来源会员的id为oldMemberId，快照会员的id为newMemberId，快照会员为终止状态，清空快照的推荐人，截止日期为当前日期
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("oldMemberId", sourceMemberId);
        param.put("newMemberId", snapshootMemberId);
        param.put("newMemberCode", snapshootMemberCode);
        param.put("newSatus", MemberConstants.MEMBER_STATUS_TERMINATED);
        param.put("sponsorId", null);
        param.put("sponsorNo", null);
        param.put("terminationDate", nowDate);

        memberMapper.upgradeMemberId(param);

        attributeMapper.upgradeMemberId(sourceMemberId, snapshootMemberId);

        siteMapper.upgradeMemberId(sourceMemberId, snapshootMemberId);

        relationshipMapper.upgradeMemberId(sourceMemberId, snapshootMemberId);

        accountMapper.upgradeMemberId(sourceMemberId, snapshootMemberId);

        cardMapper.upgradeMemberId(sourceMemberId, snapshootMemberId);

        // 更新附件的来源主键为快照会员memberId
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sourceType", "MEMBER_SIGNATURE");
        map.put("oldSourceKey", sourceMemberId);
        map.put("newSourceKey", snapshootMemberId);
        attachmentMapper.upgradeSourceKey(map);

        // 创建快照会员的RB冲销节余
        /*
         * MemWithdraw memWithdraw = new MemWithdraw();
         * memWithdraw.setMemberId(snapshootMemberId);
         * memWithdraw.setAmount(BigDecimal.valueOf(self().getRBBymemberId(
         * sourceMember.getMemberId())));
         * memWithdraw.setWriteoffType(MemberConstants.
         * MM_MEMWITHDRAW_WRITEOFF_TYPE);
         * memWithdraw.setMarketId(sourceMember.getMarketId());
         * memWithdraw.setCompanyId(sourceMember.getCompanyId()); Map<String,
         * Object> periodPram = new HashMap<String, Object>();
         * periodPram.put("marketId", sourceMember.getMarketId());
         * memWithdraw.setPeriod(Integer.parseInt(memWithdrawMapper.
         * getPeriodByNow(periodPram) + ""));
         * memWithdraw.setAdjustmentType(MemberConstants.
         * MM_MEMWITHDRAW_ADJUSTMENT_TYPE); memWithdraw.setRemark(
         * "change ownership");
         * memWithdraw.setStatus(MemberConstants.MM_MEMWITHDRAW_STATUS);
         * memWithdraw.setCreationDate(new Date());
         * memWithdraw.setLastUpdateDate(new Date());
         * memWithdraw.setCreatedBy(-1L); memWithdraw.setLastUpdatedBy(-1L);
         * memWithdrawMapper.insert(memWithdraw);
         */

        return snapshootMemberId;
    }

    /**
     * 应用预存会员信息，覆盖预存会员信息至原会员，即更新会员预存信息的memberId为原会员memberId.
     *
     * @param request      请求上下文
     * @param sourceMember 原会员DTO
     * @param tempMemberId 预存会员Id
     * @throws MemberException 会员统一异常
     */
    public void applyTempMember(IRequest request, Member sourceMember, Long tempMemberId) throws MemberException {
        Long sourceMemberId = sourceMember.getMemberId();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date nextDate = calendar.getTime();
        nextDate = DateUtils.truncate(nextDate, Calendar.DAY_OF_MONTH);

        // 更新会员预存信息的memberId为原会员memberId，状态为原会员状态，推荐人为原会员推荐人，加入和审核日期为当前日期+1
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("oldMemberId", tempMemberId);
        param.put("newMemberId", sourceMemberId);
        param.put("newMemberCode", sourceMember.getMemberCode());
        param.put("newSatus", sourceMember.getStatus());
        param.put("sponsorId", sourceMember.getSponsorId());
        param.put("sponsorNo", sourceMember.getSponsorNo());
        param.put("jointDate", nextDate);
        param.put("approvalDate", nextDate);

        memberMapper.upgradeMemberId(param);

        // 更新会员属性预存信息的memberId为原会员memberId

        attributeMapper.upgradeMemberId(tempMemberId, sourceMemberId);

        // 更新会员地点预存信息的memberId为原会员memberId
        siteMapper.upgradeMemberId(tempMemberId, sourceMemberId);

        // 更新会员相关人预存信息的memberId为原会员memberId
        relationshipMapper.upgradeMemberId(tempMemberId, sourceMemberId);

        // 更新会员账户预存信息的memberId为原会员memberId
        accountMapper.upgradeMemberId(tempMemberId, sourceMemberId);

        // 更新会员银行卡预存信息的memberId为原会员memberId
        cardMapper.upgradeMemberId(tempMemberId, sourceMemberId);
    }

    @Override
    public Member querySponsorId(IRequest iRequest, Long memberId) {
        Member member = memberMapper.selectSponsorId(memberId);
        return member;
    }

    @Override
    public Member queryMarketId(IRequest request, Long memberId) {
        Member member = memberMapper.selectMarketId(memberId);
        return member;
    }

    @Override
    public boolean checkNewUpstreamStatus(IRequest request, String memberCode) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("memberCode", memberCode);
        Member member = memberMapper.selectValidMemberByMemberCode(queryMap);
        if (member != null) {
            return MemberConstants.MEMBER_STATUS_ACTIVE.equals(member.getStatus());
        } else {
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<Member> queryMember(IRequest request, Member member, int page, int pagesize) throws MemberException {
        PageHelper.startPage(page, pagesize);
        if (member.getMemberCode() == null && member.getChineseName() == null && member.getRegisterCode() == null
                && member.getPhoneNo() == null) {
            throw new MemberException(MemberException.MSG_NO_SEARCH_CRITERIA, null);
        }
        /* Mclin 修改 */
        member.setMemberCode(member.getMemberCode());

        member.setEnglishName(member.getChineseName());
        member.setBrNumber(member.getRegisterCode());
        member.setIdNumber(member.getRegisterCode());
        member.setPhoneNo(member.getPhoneNo());
        member.setOthPhoneNo(member.getPhoneNo());
        List<Member> members = memberMapper.changeMemberSelect(member);
        for (Member memberTmp : members) {
            setMemberName(memberTmp);
        }
        return members;
    }

    private void setMemberName(Member member_tmp) {
        String chineseName = member_tmp.getChineseName();
        String englishName = member_tmp.getEnglishName();
        if (englishName != null && chineseName != null) {
            member_tmp.setMemberName(englishName + "/" + chineseName);
        } else if (englishName != null) {
            member_tmp.setMemberName(englishName);
        } else {
            member_tmp.setMemberName(chineseName);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> queryExamineMembers(IRequest request, Member member, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return memberMapper.selectExamineMembers(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activeMember(IRequest request, @StdWho Member member) throws MemberException {
        if (member == null || member.getMemberId() == null) {
            return;
        }
        memberMapper.examineMember(member, MemberConstants.MEMBER_STATUS_ACTIVE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchActive(IRequest request, @StdWho List<Member> members) throws MemberException {
        if (members == null || members.isEmpty()) {
            return;
        }
        for (Member member : members) {
            self().activeMember(request, member);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectMember(IRequest request, @StdWho Member member) throws MemberException {
        if (member == null || member.getMemberId() == null) {
            return;
        }
        memberMapper.examineMember(member, MemberConstants.MEMBER_STATUS_REJECTED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchReject(IRequest request, @StdWho List<Member> members) throws MemberException {
        if (members == null || members.isEmpty()) {
            return;
        }
        for (Member member : members) {
            self().rejectMember(request, member);
        }
    }

    /**
     * 查询会员.
     *
     * @param request  请求上下文
     * @param member   会员DTO
     * @param yearFrom 年份从
     * @param yearTo   年份至
     * @param month    月份
     * @param page     页
     * @param pagesize 分页大小
     * @return members 会员列表
     */
    @Override
    public List<Member> queryMembers(IRequest request, Member member, String month, String yearFrom, String yearTo,
                                     String userType, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Member> members = new ArrayList<Member>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberCode", member.getMemberCode());
        map.put("memberName", member.getMemberName());
        map.put("phoneNo", member.getPhoneNo());
        map.put("registerCode", member.getRegisterCode());
        map.put("month", month);
        map.put("yearFrom", yearFrom);
        map.put("yearTo", yearTo);
        map.put("marketId", member.getMarketId());
        map.put("isActive", member.getIsActive());
        map.put("relationShip", MemberConstants.MEMBER_REL_TYPE_SPOUS);
        map.put("englishName", member.getEnglishName());
        map.put("chineseName", member.getChineseName());
        map.put("memberId", member.getMemberId());
        map.put("status", member.getStatus());
        map.put("jointDate", member.getJointDate());
        map.put("jointDateFrom", member.getJointDateFrom());
        map.put("jointDateTo", member.getJointDateTo());
        map.put("sponsorNo", member.getSponsorNo());
        map.put("sortname", member.getSortname());
        map.put("sortorder", member.getSortorder());
        map.put("userType", userType);
        map.put("sponsorName", member.getSponsorName());
        map.put("sponsorChineseName", member.getSponsorChineseName());
        map.put("sponsorEnglishName", member.getSponsorEnglishName());
        map.put("idNumber", member.getIdNumber());
        map.put("rank", member.getRank());
        map.put("jointSiteName", member.getJointSiteName());
        map.put("jointSite", member.getJointSite());
        map.put("salesOrgId", member.getSalesOrgId());
        String role = member.getMemberRole();
        List<String> roleList = new ArrayList<String>();
        if (role != null) {
            Collections.addAll(roleList, role.split(";"));
            map.put("roleList", roleList);
        }

        // 会员查询界面的排序限制条件
        /*
         * if (null != member.getSortname() && null != member.getSortorder()) {
         * if (member.getMemberCode() == null && member.getMemberName() == null
         * && member.getPhoneNo() == null && member.getRegisterCode() == null &&
         * null == month && null == yearFrom && null == yearTo && null ==
         * member.getStatus() && null == member.getMemberId()) { return members;
         * } }
         */
        members = memberMapper.queryMembers(map);

        return members;
    }

    /**
     * 根据条件查询所有会员冲销节余.
     *
     * @param request    请求上下文
     * @param memberCode 会员Id
     * @param periodFrom 年份从
     * @param periodTo   年份至
     * @param page       页
     * @param pagesize   分页大小
     * @return MemWithdraws 会员冲销列表
     */
    @Override
    public List<MemWithdraw> queryWithdrawsByParams(IRequest request, String memberCode, String periodFrom,
                                                    String periodTo, Long memberId, String memberName, int page, int pagesize, Long marketId) {
        PageHelper.startPage(page, pagesize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("periodTo", periodTo);
        map.put("periodFrom", periodFrom);
        map.put("memberCode", memberCode);
        map.put("memberId", memberId);
        map.put("memberName", memberName);
        map.put("marketId", marketId);
        List<MemWithdraw> memWithdraws = memWithdrawMapper.queryWithdraws(map);
        return memWithdraws;
    }

    /**
     * 会员申请冲销节余.
     *
     * @param request          请求上下文
     * @param memWithdraw      冲销节余DTO
     * @param remainingBalance 会员剩余奖金
     * @param pagesize         分页大小
     * @param isConfirm        是否确认执行冲销
     * @return responseData 响应数据
     * @throws CommMemberException 会员异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> addWithdraw(IRequest request, @StdWho MemWithdraw memWithdraw, String remainingBalance, int page,
                                    int pagesize, boolean isConfirm) throws CommMemberException {
        // Boolean isSuccess = false;
        List<String> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (null == memWithdraw.getMemberId()) {
            Member member = memberMapper.selectMembersByMemberCode(memWithdraw.getMemberCode());
            if (null != member) {
                memWithdraw.setMemberId(member.getMemberId());
            }
        }
        map.put("memberId", memWithdraw.getMemberId());
        map.put("memberStatus", MemberConstants.MEMBER_STATUS_ACTIVE);
        long isActive = memWithdrawMapper.isActive(map);
        if (isActive == 0) {
            list.add(MemberConstants.MM_MEMWITHDRAW_NO_ACTIVE);
            return list;
        }

        if (memWithdraw.getAmount() == null) {
            memWithdraw.setAmount(BigDecimal.valueOf(Long.valueOf(remainingBalance)));
        }
        map.put("amount", memWithdraw.getAmount());
        map.put("accountingType", MemberConstants.ACCOUNTING_TYPE_RB);
        long isBalance = memWithdrawMapper.isBalance(map);
        if (isBalance == 0) {
            list.add(MemberConstants.MM_MEMWITHDRAW_NO_BALANCE);
            return list;
        }
        if (isConfirm) {
            map.put("remark", memWithdraw.getRemark());
            map.put("remainingBalance", remainingBalance);
            Member member = memberMapper.selectByPrimaryKey(memWithdraw.getMemberId());
            map.put("marketId", member.getMarketId());
            map.put("companyId", member.getCompanyId());
            Long periodByNow = memWithdrawMapper.getPeriodByNow(map);
            /*
             * map.put("period", periodByNow); map.put("writeoffType",
             * MemberConstants.MM_MEMWITHDRAW_WRITEOFF_TYPE);
             * map.put("adjustmentType",
             * MemberConstants.MM_MEMWITHDRAW_ADJUSTMENT_TYPE);
             * map.put("status", MemberConstants.MM_MEMWITHDRAW_STATUS);
             */
            memWithdraw.setMarketId(member.getMarketId());
            memWithdraw.setCompanyId(member.getCompanyId());
            memWithdraw.setPeriod(periodByNow.intValue());
            memWithdraw.setWriteoffType(MemberConstants.MM_MEMWITHDRAW_WRITEOFF_TYPE);
            memWithdraw.setAdjustmentType(MemberConstants.MM_MEMWITHDRAW_ADJUSTMENT_TYPE);
            memWithdraw.setStatus(MemberConstants.MM_MEMWITHDRAW_STATUS);
            memWithdrawMapper.addWithdraw(memWithdraw);

            // 调用账务事务处理程序，更新账户余额
            /* int isUpdate = memWithdrawMapper.updateBalance(map); */
            // 生成账务事务处理dto
            MemAccountingTrx trx = new MemAccountingTrx();
            trx.setMemberId(member.getMemberId());
            trx.setCompanyId(member.getCompanyId());
            trx.setSalesOrgId(Long.parseLong(member.getJointSite()));
            trx.setTrxDate(new Date(System.currentTimeMillis()));
            trx.setTrxType(MemberConstants.TRX_TYPE_WITHDRAW);
            trx.setTrxSourceType(MemberConstants.MM_MEM_WITHDRAW);
            trx.setTrxSourceId(memWithdraw.getWithdrawId()); // 设置事务来源ID
            trx.setTrxSourceLineId(memWithdraw.getWithdrawId());
            trx.setAccountingType(MemberConstants.ACCOUNTING_TYPE_RB);
            trx.setTrxValue(memWithdraw.getAmount().multiply(new BigDecimal(-1)));
            memberBalanceTrxService.processAccountingTrx(request, trx);

            if (null != memWithdraw.getWithdrawId()) {
                list.add(MemberConstants.MM_MEMWITHDRAW_SUCCESS);
                return list;
            }
        }

        list.add("false");
        return list;
    }

    /**
     * 根据会员ID获取剩余奖金.
     *
     * @param request  请求上下文
     * @param memberId 会员id
     * @param pagesize 分页大小
     * @return responseData 响应数据
     */
    @Override
    public List<String> getBalanceByMemberId(IRequest request, String memberId, int page, int pagesize) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("accountingType", MemberConstants.ACCOUNTING_TYPE_RB);
        List<String> balances = memWithdrawMapper.getBalanceByMemberId(map);
        return balances;
    }

    @Override
    public Map<String, Object> validTerminate(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long downLineSum = 0L;
        BigDecimal pvSum = BigDecimal.ZERO;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -11);
        Date beforeTime = cal.getTime();
        String beforeTimeStr = sdf.format(beforeTime);
        map.put("memberCode", member.getMemberCode());
        map.put("monthFrom", beforeTimeStr);
        String endTimeStr = sdf.format(date);
        map.put("monthTo", endTimeStr);
        if (logger.isDebugEnabled()) {
            logger.debug("memberCode:{}", member.getMemberCode());
            logger.debug("monthFrom:{}", beforeTimeStr);
            logger.debug("monthTo:{}", endTimeStr);
        }
        List<MemRank> ranks = memRankMapper.selectCurrentAndHistoryRank(map);
        for (MemRank memRank : ranks) {
            pvSum = pvSum.add(memRank.getPv());
            downLineSum += memRank.getDownLine();
        }
        map.put("pvSum", pvSum);
        map.put("downLineSum", downLineSum);
        return map;
    }

    @Override
    public List<Member> queryActiveMembers() {
        Map<Object, List<String>> map = new HashMap<>();
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add(MemberConstants.MEMBER_STATUS_TERMINATED);
        statusList.add(MemberConstants.MEMBER_STATUS_AUTO_TERMINATED);
        map.put("statusList", statusList);
        return memberMapper.selectMembersByStatusNotIn(map);
    }

    @Override
    public boolean updateByPrimaryKeySelective(Member record) {
        return memberMapper.updateByPrimaryKeySelective(record) != 0;
    }

    @Override
    public boolean checkMemPVInYear(Long memberId) {
        BigDecimal sum = rankMapper.selectSumPVByMemberId(memberId);
        return !BigDecimal.ZERO.equals(sum);
    }

    @Override
    public List<Member> queryMemNameAuto(String key) {
        List<Member> members = memberMapper.queryMemNameAuto(key);
        return members;
    }

    @Override
    public Long getRBBymemberId(Long memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("accountingType", MemberConstants.ACCOUNTING_TYPE_RB);
        Long br = accountingBalanceMapper.getRBBymemberId(map);
        return br;
    }

    @Override
    @Transactional
    public boolean clearRBalance(Long memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("accountingType", MemberConstants.ACCOUNTING_TYPE_RB);
        return !(accountingBalanceMapper.clearRBalance(map) == 0);
    }

    @Override
    public List<Member> getMemberIdByCode(IRequest request, String memberCode, Long marketId) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", MemberConstants.MEMBER_STATUS_ACTIVE);
        map.put("memberCode", memberCode);
        map.put("marketId", marketId);
        List<Member> memberId = memberMapper.getMemberIdByCode(map);
        return memberId;
    }

    @Override
    public Map isMemberBirthdayMonth(IRequest request, Long memberId) {
        return commMemberService.isMemberBirthdayMonth(request, memberId);
    }

    @Override
    public List<Member> queryMessageMember(IRequest request, Member member, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Member> list = memberMapper.queryByMemberAccountId(member);
        return list;
    }

    @Override
    public List<Member> queryMembersForOrder(IRequest request, Member member, int page, int pagesize)
            throws IntegrationException {
        String orderType = member.getOrderType();
        String memberCode = member.getMemberCode();

        List<Member> memberList = commMemberService.queryMembersForOrder(request, member, page, pagesize);
        // 大陆会员
        if (OrderConstants.ORDER_TYPE_NMDP.equals(orderType) && !StringUtils.isEmpty(memberCode)
                && memberCode.length() == 9 && memberList.size() == 0) {
            Member m = memberMapper.selectMembersByMemberCode(memberCode);
            if (m == null) {
                memberList.add(self().insertGdsMemberToDsis(request, memberCode));
            }

        }

        return memberList;
    }

    @Override
    public List<Member> queryMembersForIpointOrder(IRequest request, Member member, int page, int pagesize)
            throws IntegrationException {
        List<Member> memberList = new ArrayList<Member>();
        if (StringUtils.isEmpty(member.getMemberCode()) && StringUtils.isEmpty(member.getRegisterCode())
                && StringUtils.isEmpty(member.getEnglishName()) && StringUtils.isEmpty(member.getChineseName())) {
            return memberList;
        }
        memberList = commMemberService.queryMembersForIpointOrder(request, member, page, pagesize);
        return memberList;
    }

    @Override
    public List<Long> saveReceiver(IRequest request, List<Long> memberIds) {
        Long groupId = memberListMapper.getNextGroupId();
        List<Long> groupIds = new ArrayList<Long>();
        Member member = new Member();
        for (Long memberId : memberIds) {
            member = memberMapper.selectByPrimaryKey(memberId);
            MemberList memberList = new MemberList();
            memberList.setMemberId(member.getMemberId());
            memberList.setMemberCode(member.getMemberCode());
            memberList.setGroupId(groupId);
            memberList.setGroupType("MSG");
            memberList.setValidateFlag("N");
            memberListMapper.insert(memberList);
        }
        groupIds.add(groupId);
        return groupIds;
    }

    @Override
    public void vipChangeToDistributor(IRequest request, Long memberId) throws CommMemberException {
        Map<String, Object> result = commMemberService.validateForVIPToDIS(request, memberId);
        List<MemApplyRole> records = memApplyRoleMapper.selectByMemberId(memberId);
        if (records != null && records.size() > 0) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_APPLY_ROLE_RECORD_EXIST, null);
        }
        boolean isChange = (boolean) result.get("qualifiedFlag");
        if (isChange) {
            Member member = memberMapper.selectByPrimaryKey(memberId);
            memApplyRoleService.insertRecord(request, member);
        } else {
            throw new CommMemberException(CommMemberException.MSG_ERROR_CANNOT_CHANGE_TO_DISTRIBUTOR, null);
        }
    }

    @Override
    public List<MemCard> queryBankInfo(IRequest iRequest, Long memberId) {
        List<MemCard> cards = commMemberService.queryAllCardInfo(iRequest, memberId);
        for (MemCard temp : cards) {
            String cardNumber = aescClientService.decrypt(temp.getCardNumber());
            temp.setCardNumber(cardNumber);
        }
        return cards;
    }

    @Override
    @Transactional
    public Member insertGdsMemberToDsis(IRequest request, String memberCode) throws IntegrationException {
        String orgCode = gdsUtilService.getCurrentOrgCode(request);
        String gdsOrgCode = gdsUtilService.getGdsOrgCode(request, orgCode);
        SponsorVerifyPOSTResponse sponsorVerifyPOSTResponse;
        try {
            sponsorVerifyPOSTResponse = gdsService.sponsorVerify(memberCode, gdsOrgCode);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_SPONSOR_VERIFY, e);
            }
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[]{
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse()});
            }
            throw new IntegrationException(e.getMessage(), null);
        }
        // GDS返回会员卡号不匹配
        if (!memberCode.equals(sponsorVerifyPOSTResponse.getDealerNo())) {
            throw new IntegrationException(IntegrationException.GDS_MEMBER_CODE_NOT_MATCH, null);
        }
        // 推荐人卡号为空
        if (sponsorVerifyPOSTResponse.getDealerNo() == null || sponsorVerifyPOSTResponse.getDealerNo().equals("")) {
            throw new IntegrationException(IntegrationException.MSG_ERROR_INVALID_SPONSOR, null);
        }
        Member gdsmember = new Member();
        gdsmember.setStatus(MemberConstants.MEMBER_STATUS_ACTIVE);
        gdsmember.setRemark(sponsorVerifyPOSTResponse.getComments());
        gdsmember.setChineseLastName(sponsorVerifyPOSTResponse.getDealerName());
        gdsmember.setChineseName(sponsorVerifyPOSTResponse.getDealerName());
        gdsmember.setMemberCode(sponsorVerifyPOSTResponse.getDealerNo());

        /*if (!StringUtils.isEmpty(sponsorVerifyPOSTResponse.getDealerType())) {
            String memberRole = codeService.getCodeValueByDesc(request, IntegrationConstant.MM_MEMBER_ROLE,
                    sponsorVerifyPOSTResponse.getDealerType());

        }*/
        gdsmember.setMemberRole(MemberConstants.MM_ROLE_DIS);
        gdsmember.setEnglishLastName(sponsorVerifyPOSTResponse.getEnglishName());
        gdsmember.setEnglishName(sponsorVerifyPOSTResponse.getEnglishName());
        if (!StringUtils.isEmpty(sponsorVerifyPOSTResponse.getSaleOrgCode())) {
            String orgCodem = gdsUtilService.getOrgCode(sponsorVerifyPOSTResponse.getSaleOrgCode());
            SpmMarket spmMarket = spmMarketMapper.selectMarketByCode(orgCodem);
            gdsmember.setMarketId(spmMarket.getMarketId());
            gdsmember.setCompanyId(spmMarket.getCompanyId());
        }

        gdsmember.setGender(sponsorVerifyPOSTResponse.getSex());
        gdsmember.setSponsorNo(sponsorVerifyPOSTResponse.getSponsorNo());
        GdealerPersonalInfo gdealerPersonalInfo = sponsorVerifyPOSTResponse.getGdealerPersonalInfo();
        if (gdealerPersonalInfo != null) {
            if (!StringUtils.isEmpty(gdealerPersonalInfo.getBirthday())) {
                Date birthday = gdsUtilService.dateStringToDate(gdealerPersonalInfo.getBirthday());
                gdsmember.setDob(birthday);
            }
            if (StringUtils.isEmpty(gdealerPersonalInfo.getCertificateNationCode())) {
                gdsmember.setCountry(IntegrationConstant.COUNTRY_CN);
            } else {
                gdsmember.setCountry(gdealerPersonalInfo.getCertificateNationCode());
            }
            gdsmember.setIdNumber(gdealerPersonalInfo.getCertificateNo());
            gdsmember.setIdType(MemberConstants.ID_TYPE_IC);
            if (!StringUtils.isEmpty(gdealerPersonalInfo.getCertificateType())) {
                String memberType = codeService.getCodeValueByDesc(request, IntegrationConstant.MM_MEMBER_TYPE,
                        gdealerPersonalInfo.getCertificateType());
                if (StringUtils.isEmpty(memberType)) {
                    gdsmember.setMemberType(IntegrationConstant.INDIVIDUAL);
                } else {
                    gdsmember.setMemberType(memberType);
                }
            } else {
                gdsmember.setMemberType(IntegrationConstant.INDIVIDUAL);
            }
            gdsmember.setEmail(gdealerPersonalInfo.getContactEmail());
            gdsmember.setPhoneNo(gdealerPersonalInfo.getContactTele());
            gdsmember.setSyncFlag(BaseConstants.YES);
            gdsmember.setBonusRcvMethod(MemberConstants.BONUS_RCV_METHOD_BANK);
        }

        Member mm = memberMapper.selectMembersByMemberCode(memberCode);
        if (mm != null) {
            return mm;
        }
        commMemberService.insert(request, gdsmember);
        return gdsmember;
    }

    @Override
    public List<MemSite> queryMemSites(String memberCode, IRequest request) {
        return memberMapper.queryMemSites(memberCode);
    }

    @Override
    public Boolean isRoleAssignBonusFinal(Long roleId) {
        String result = memberMapper.isAccessToFunctionByRole(roleId, MemberConstants.FUNCTION_CODE_BONUS_FINAL);
        if (BaseConstants.YES.equals(result)) {
            return true;
        }
        return false;
    }

    @Override
    public String isSameAccount(IRequest request, Member member) {
        List<MemAccount> accounts = member.getMemAccounts();
        if (accounts != null) {
            for (MemAccount account : accounts) {
                // 验证马来市场下是否重复的账户号码
                if (MemberConstants.MARKET_MY.equals(member.getMarketCode()) || MemberConstants.MARKET_SG.equals(member.getMarketCode())) {
                    String memberCode = accountMapper.isExistAccount(account.getBankId(), account.getAccountNumber(),
                            member.getMemberId(), member.getMarketCode());
                    if (memberCode != null) {// 若存在重复账号则把重复的会员卡号返回
                        return memberCode;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param request
     *          请求上下文
     * @param member
     *          会员
     * @return
     */
    @Override
    public List<Member> queryDiscount(IRequest request, Member member, int page, int pageSize) {

        PageHelper.startPage(page, pageSize);
        return memberMapper.queryDiscount(member);

    }

    @Override
    public List<Member> queryMemberByMarket(IRequest request){
        return memberMapper.queryMemberForDiscountLov();
    }


    @Override
    public int updateMemberDiscount(IRequest request, Long memberId, Long discountAmt){
        return memberMapper.updateMemberDiscount(memberId, discountAmt);
    };
}