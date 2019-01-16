/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemRank;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.Sponsor;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemAccountingBalanceMapper;
import com.lkkhpg.dsis.common.member.mapper.MemAccountingTrxMapper;
import com.lkkhpg.dsis.common.member.mapper.MemAttributeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemCardMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRankMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRelationshipMapper;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.member.mapper.MemWithdrawMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.member.mapper.SponsorMapper;
import com.lkkhpg.dsis.common.order.dto.OmMwsOrderPayment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.OmMwsOrderPaymentMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherAssignMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherTransactionMapper;
import com.lkkhpg.dsis.common.service.ICommMemSiteService;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ICommSalesOrderService;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.common.service.IMemberBalanceTrxService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.AccountRelation;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.AccountRelationMapper;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * 会员Service接口实现.
 *
 * @author frank.li
 */
@Service
@Transactional
public class CommMemberServiceImpl implements ICommMemberService {

    private final Logger logger = LoggerFactory.getLogger(CommMemberServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

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
    private MemWithdrawMapper withdrawMapper;

    @Autowired
    private VoucherAssignMapper voucherAssignMapper;

    @Autowired
    private MemAccountingBalanceMapper accountingBalanceMapper;

    @Autowired
    private MemAccountingTrxMapper accountingTrxMapper;

    @Autowired
    private MemWithdrawMapper memWithdrawMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private ICommMemSiteService commMemSiteService;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SponsorMapper sponserMapper;

    @Autowired
    private IAESClientService aESClientService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AccountMapper sysAccountMapper;

    @Autowired
    private AccountRelationMapper accountRelationMapper;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IMemberBalanceTrxService memberBalanceTrxService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private OmMwsOrderPaymentMapper omMwsOrderPaymentMapper;

    @Autowired
    private VoucherTransactionMapper voucherTransactionMapper;

    @Autowired
    private ICommSalesOrderService commSalesOrderService;

    @Override
    public List<MemRelationship> queryRelationships(IRequest request, Long memberId, int page, int pagesize) {
        return relationshipMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemAttribute> queryAttributes(IRequest request, Long memberId, int page, int pagesize) {
        return attributeMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemSite> querySites(IRequest request, Long memberId, int page, int pagesize) {
        return siteMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemAccount> queryAccounts(IRequest request, Long memberId, int page, int pagesize) {
        return accountMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemCard> queryCards(IRequest request, Long memberId, int page, int pagesize) {
        List<MemCard> memCards = cardMapper.selectByMemberId(memberId);
        for (MemCard memCard : memCards) {
            memCard.setCardNumber("");
        }
        return memCards;
    }

    @Override
    public List<MemAccountingBalance> queryMemAccountingBalances(IRequest request, Long memberId) {
        /*
         * List<MemAccountingBalance> memAccountingBalances =
         * accountingBalanceMapper.selectByMemberId(memberId); OmMwsOrderPayment
         * rbSum = omMwsOrderPaymentMapper.queryRemainingBalSum(memberId); if
         * (null != rbSum) { for (MemAccountingBalance temp :
         * memAccountingBalances) { if
         * (MemberConstants.ACCOUNTING_TYPE_RB.equals(temp.getAccountingType()))
         * {
         * temp.setBalance(temp.getBalance().subtract(rbSum.getPaymentAmount()))
         * ; break; } } }
         */
        return accountingBalanceMapper.selectByMemberId(memberId);
    }

    @Override
    public List<MemAccountingTrx> queryMemAccountingTrxs(IRequest request, Long memberId, String accountingType,
            Date trxDateFrom, Date trxDateTo, int page, int pagesize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", memberId);
        map.put("accountingType", accountingType);
        map.put("trxDateFrom", trxDateFrom);
        map.put("trxDateTo", trxDateTo);

        return accountingTrxMapper.selectByMemberId(map);
    }

    @Override
    public List<MemRank> queryRanks(IRequest request, String memberCode, Date monthFrom, Date monthTo, int page,
            int pagesize) throws CommMemberException {
        Member member = memberMapper.selectMembersByMemberCode(memberCode);
        if (logger.isDebugEnabled()) {
            logger.debug("memberCode  : {}", memberCode);
            logger.debug("monthFrom : {}", monthFrom);
            logger.debug("monthTo   : {}", monthTo);
        }
        Map<String, Object> map = new HashMap<String, Object>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        String currentTime = sdf.format(date);
        String monthFromStr = null;
        String monthToStr = null;
        if (monthFrom == null) {
            // if (member != null && member.getJointDate() != null) {
            // monthFrom = member.getJointDate();
            // monthFromStr = sdf.format(monthFrom);
            // } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.MONTH, 0);
            monthFromStr = sdf.format(cal.getTime());
            // }
        } else {
            monthFromStr = sdf.format(monthFrom);
        }
        if (monthFromStr != null && monthFromStr.compareTo(currentTime) > 0) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_QUERY_DATE_MORE_THAN_CURRENT_TIME, null);
        }
        if (monthTo == null) {
            monthTo = new Date();
        }
        monthToStr = sdf.format(monthTo);
        if (monthToStr != null && monthToStr.compareTo(currentTime) > 0) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_QUERY_DATE_MORE_THAN_CURRENT_TIME, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("monthFromStr : {}", monthFromStr);
            logger.debug("monthToStr : {}", monthToStr);
        }

        map.put("memberCode", memberCode);
        map.put("monthFrom", monthFromStr);
        map.put("monthTo", monthToStr);
        List<MemRank> ranks = new ArrayList<MemRank>();
        if (member != null) { // pos会员
            ranks = rankMapper.selectCurrentAndHistoryRank(map);
        } else {// 中国会员
            ranks = rankMapper.selectRankForChn(map);
        }
        for (int i = 0; i < ranks.size(); i++) {
            MemRank memRank = ranks.get(i);
            if (memRank.getRank() != null) {
                String rankDesc = codeService.getCodeMeaningByValue(request, MemberConstants.MM_MEMBER_RANK,
                        memRank.getRank().toString());
                memRank.setRankDesc(rankDesc);
                ranks.remove(i);
                ranks.add(i, memRank);
            }
        }
        return ranks;
    }

    @Override
    public List<MemWithdraw> queryWithdraws(IRequest request, Long memberId, int page, int pagesize) {
        return withdrawMapper.selectByMemberId(memberId);
    }

    @Override
    public List<VoucherAssign> queryVoucherAssigns(IRequest request, Long memberId, int page, int pagesize) {
        return voucherAssignMapper.selectByMemberId(memberId);
    }

    /**
     * 深度遍历会员下线树.
     *
     * @param request
     *            请求上下文
     * @param sponsorId
     *            推荐人Id
     * @param downLines
     *            下线List
     */
    public void depthTraversalMember(IRequest request, Long sponsorId, List<Member> downLines) {
        List<Member> members = new ArrayList<Member>();
        members = memberMapper.selectBySponsorId(sponsorId);

        for (Member member : members) {
            downLines.add(member);
            depthTraversalMember(request, member.getMemberId(), downLines);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> queryDownLines(IRequest request, Long memberId) {
        List<Member> downLines = new ArrayList<Member>();
        depthTraversalMember(request, memberId, downLines);

        return downLines;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> getOmkDownLines(IRequest request, String memberCode, Integer level, String excludeSelf) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (memberCode != null) {
            map.put("memberCode", memberCode);
        }

        if (level != null) {
            map.put("level", level);
        }
        if (excludeSelf != null) {
            map.put("excludeSelf", excludeSelf);
        }
        List<Member> members = memberMapper.selectOmkDownLineByMemberCode(map);
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            // 从OMK表里获取会员等级
            Map<String, Object> resultMap = self().getDeadLine(request, member.getMemberCode());
            Object deadLine = resultMap.get("deadLine");
            member.setDeadline(deadLine.toString());
            members.remove(i);
            members.add(i, member);
        }
        return members;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> getDownLines(IRequest request, Long memberId, Integer level, String excludeSelf) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (memberId != null) {
            map.put("memberId", memberId);
        }

        if (level != null) {
            map.put("level", level);
        }
        if (excludeSelf != null) {
            map.put("excludeSelf", excludeSelf);
        }

        return memberMapper.selectDownLineByMemberId(map);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> getUpLines(IRequest request, Long memberId, Integer level, String excludeSelf) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", memberId);
        map.put("level", level);
        map.put("excludeSelf", excludeSelf);

        return memberMapper.selectDownLineByMemberId(map);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> queryMembers(IRequest request, Member member, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return memberMapper.selectMembers(member);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Member getMember(IRequest request, Long memberId) {
        Member member = new Member();
        member = memberMapper.selectByPrimaryKey(memberId);

        if (member != null) {
            // 获取其他属性
            member.setMemAttributes(attributeMapper.selectByMemberId(memberId));
            String isHideState = BaseConstants.NO;
            // 获取家庭地址、联系地址
            List<MemSite> homeCtactSites = siteMapper.selectHomeCtactLocByMemberId(memberId);
            for (MemSite memSite : homeCtactSites) {
                if (MemberConstants.SITE_USE_CODE_HOME.equals(memSite.getSiteUseCode())) {
                    SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(memSite.getLocationId());
                    isHideState = siteMapper.isHideState(spmLocation.getCountryCode());
                    String address1 = spmLocation.getAddressLine1();
                    String address2 = spmLocation.getAddressLine2() == null ? "" : spmLocation.getAddressLine2();
                    String address3 = spmLocation.getAddressLine3() == null ? "" : spmLocation.getAddressLine3();
                    String city = spmLocation.getCityName();
                    String state = spmLocation.getStateName();
                    String country = spmLocation.getCountryName();
                    member.setHomeSpmLocation(spmLocation);
                    member.setHomeLocationId(memSite.getLocationId());
                    if (BaseConstants.YES.equals(isHideState)) {
                        if (BaseConstants.DEFAULT_LANG.equals(request.getLocale())) {
                            member.setHomeLocation(address1 + "," + (address2.equals("") ? "" : address2 + ",")
                                    + (address3.equals("") ? "" : address3 + ",") + city + "," + country);
                        } else {
                            member.setHomeLocation(country + city + address1 + address2 + address3);
                        }
                    } else {
                        if (BaseConstants.DEFAULT_LANG.equals(request.getLocale())) {
                            member.setHomeLocation(address1 + "," + (address2.equals("") ? "" : address2 + ",")
                                    + (address3.equals("") ? "" : address3 + ",") + city + "," + state + "," + country);
                        } else {
                            member.setHomeLocation(country + state + city + address1 + address2 + address3);
                        }
                    }
                } else if (MemberConstants.SITE_USE_CODE_CTACT.equals(memSite.getSiteUseCode())) {
                    SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(memSite.getLocationId());
                    isHideState = siteMapper.isHideState(spmLocation.getCountryCode());
                    String address1 = spmLocation.getAddressLine1();
                    String address2 = spmLocation.getAddressLine2() == null ? "" : spmLocation.getAddressLine2();
                    String address3 = spmLocation.getAddressLine3() == null ? "" : spmLocation.getAddressLine3();
                    String city = spmLocation.getCityName();
                    String state = spmLocation.getStateName();
                    String country = spmLocation.getCountryName();
                    member.setContactSpmLocation(spmLocation);
                    member.setContactLocationId(memSite.getLocationId());
                    if (BaseConstants.YES.equals(isHideState)) {
                        if (BaseConstants.DEFAULT_LANG.equals(request.getLocale())) {
                            member.setContactLocation(address1 + "," + (address2.equals("") ? "" : address2 + ",")
                                    + (address3.equals("") ? "" : address3 + ",") + city + "," + country);
                        } else {
                            member.setContactLocation(country + city + address1 + address2 + address3);
                        }
                    } else {
                        if (BaseConstants.DEFAULT_LANG.equals(request.getLocale())) {
                            member.setContactLocation(address1 + "," + (address2.equals("") ? "" : address2 + ",")
                                    + (address3.equals("") ? "" : address3 + ",") + city + "," + state + "," + country);
                        } else {
                            member.setContactLocation(country + state + city + address1 + address2 + address3);
                        }
                    }
                } else if (MemberConstants.SITE_USE_GST.equals(memSite.getSiteUseCode())) {
                    SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(memSite.getLocationId());
                    isHideState = siteMapper.isHideState(spmLocation.getCountryCode());
                    String city = spmLocation.getCityName();
                    String state = spmLocation.getStateName();
                    String country = spmLocation.getCountryName();
                    member.setGstSpmLocation(spmLocation);
                    member.setGstLocationId(memSite.getLocationId());
                    if (BaseConstants.YES.equals(isHideState)) {
                        if (BaseConstants.DEFAULT_LANG.equals(request.getLocale())) {
                            member.setGstLocation(city + "," + country);
                        } else {
                            member.setGstLocation(country + city);
                        }
                    } else {
                        if (BaseConstants.DEFAULT_LANG.equals(request.getLocale())) {
                            member.setGstLocation(city + "," + state + "," + country);
                        } else {
                            member.setGstLocation(country + state + city);
                        }
                    }
                    // if (null != spmLocation.getAddress()) {
                    // member.setGstLocation(spmLocation.getAddress().replaceAll("null",
                    // ""));
                    // }
                }
            }

            // 获取银行账户信息
            List<MemAccount> memAccounts = accountMapper.selectByMemberId(memberId);
            for (MemAccount memAccount : memAccounts) {
                String decryptAccountNumber = aESClientService.decrypt(memAccount.getAccountNumber());
                memAccount.setAccountNumber(decryptAccountNumber);
            }
            member.setMemAccounts(memAccounts);

            // 获取账务余额信息
            member.setMemAccountingBalances(accountingBalanceMapper.selectByMemberId(memberId));

            // 从OMK表里获取会员等级
            Map<String, Object> resultMap = self().getDeadLine(request, member.getMemberCode());
            Object rank = resultMap.get("rank");
            Object rankDesc = resultMap.get("rankDesc");
            Object sponsorRankDesc = resultMap.get("sponsorRankDesc");
            Object deadLine = resultMap.get("deadLine");
            Object lastOrderDate = resultMap.get("lastOrderDate");
            if (rank != null && rankDesc != null) {
                member.setRank((Integer) rank);
                member.setRankDesc(rankDesc.toString());
            }
            if (sponsorRankDesc != null) {
                member.setSponsorRankDesc(sponsorRankDesc.toString());
            }
            member.setDeadline(deadLine.toString());
            BigDecimal tp = memberMapper.getTPFromGdsSalaryLctTT(member.getMemberCode());
            if (tp == null) {
                member.setTp(BigDecimal.ZERO);
            } else {
                member.setTp(tp);
            }
            member.setLastOrderDate(lastOrderDate.toString());
        }

        return member;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Member> selectSalesOrganization(Member memberId) {

        return memberMapper.selectSalesOrganization(memberId);
    }

    @Override
    public List<Member> selectMember(String orderNumber) {
        return memberMapper.selectMember(orderNumber);
    }

    private void saveRelationships(IRequest request, Member member) throws CommMemberException {

        List<MemRelationship> relationships = member.getMemRelationships();
        if (relationships != null) {
            for (MemRelationship relationship : relationships) {
                String status = relationship.get__status();

                if (logger.isDebugEnabled()) {
                    logger.debug("relationship.getMemRelId(): {}", relationship.getMemRelId());
                    logger.debug("relationship.get__status(): {}", relationship.get__status());
                }

                // 校验配偶证件编码(和其他配偶的证件编码)是否重复
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("memberId", member.getMemberId());
                map.put("idType", relationship.getIdType());
                map.put("idNumber", relationship.getIdNumber());
                String result = memberMapper.isMemberIdNumberExist(map);
                if (DTOStatus.ADD.equals(relationship.get__status()) && (BaseConstants.YES.equals(result)
                        || member.getIdNumber().equals(relationship.getIdNumber()))) {
                    throw new CommMemberException(CommMemberException.MSG_ERROR_SPOUSE_ID_NUMBER_EXIST, null);
                }
                map.put("spouseChineseName", relationship.getChineseName());
                map.put("spouseEnglishName", relationship.getEnglishName());
                if (MemberConstants.MEMBER_REL_TYPE_SPOUS.equals(relationship.getRelType())) {
                    // 若类型为配偶则校验是否有重复的配偶姓名
                    member.setIsSameSpouseName(memberMapper.isSameSpouseName(map));
                }
                if (StringUtils.isEmpty(relationship.getChineseName())
                        && StringUtils.isBlank(relationship.getEnglishName())) {
                    throw new CommMemberException(CommMemberException.MSG_ERROR_RELATIONSHIP_NAME_REQUIRED, null);
                }

                if (relationship.getRelDesc() == null
                        && relationship.getRelType() == MemberConstants.MEMBER_REL_TYPE_BENF) {
                    throw new CommMemberException(CommMemberException.MSG_ERROR_RELATIONSHIP_REL_DESC_REQUIRED, null);
                }

                if (status.equals(DTOStatus.ADD)) {
                    relationship.setMemberId(member.getMemberId());
                    relationshipMapper.insert((relationship));
                } else if (status.equals(DTOStatus.UPDATE)) {
                    relationshipMapper.updateByPrimaryKeySelective(relationship);
                } else if (status.equals(DTOStatus.DELETE)) {
                    relationshipMapper.deleteByPrimaryKey(relationship);
                }
            }
        }
    }

    private void saveAttributes(IRequest request, Member member) {
        List<MemAttribute> attributes = member.getMemAttributes();
        if (attributes != null) {
            for (MemAttribute attribute : attributes) {

                if (logger.isDebugEnabled()) {
                    logger.debug("attribute.getAttributeId(): {}", attribute.getAttributeId());
                    logger.debug("attribute.getMemberId(): {}", attribute.getMemberId());
                }

                if (attribute.getMemberId() == null) {
                    attribute.setMemberId(member.getMemberId());
                    attributeMapper.insert((attribute));
                } else {
                    attributeMapper.updateByPrimaryKeySelective(attribute);
                }
            }
        }
    }

    private void saveSites(IRequest request, Member member, String operType) throws CommMemberException {
        boolean hasBillTo = false;
        boolean hasShipTo = false;

        // 调用通用MemSiteService保存会员地点
        List<MemSite> sites = member.getMemSites();
        if (sites != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("call CommMemSiteService process memberSite");
            }
            for (MemSite site : sites) {
                // 账单地址、配送地址则更新MemSite和SpmLocation
                if (MemberConstants.MEM_SITE_TYPE_BILL.equals(site.getSiteUseCode())
                        || MemberConstants.MEM_SITE_TYPE_SHIP.equals(site.getSiteUseCode())) {

                    if (MemberConstants.MEM_SITE_TYPE_BILL.equals(site.getSiteUseCode())) {
                        hasBillTo = true;
                    }
                    if (MemberConstants.MEM_SITE_TYPE_SHIP.equals(site.getSiteUseCode())) {
                        hasShipTo = true;
                    }

                    switch (site.get__status()) {
                    case DTOStatus.ADD:
                        site.setMemberId(member.getMemberId());
                        commMemSiteService.saveMemSite(request, site);
                        break;
                    case DTOStatus.UPDATE:
                        commMemSiteService.saveMemSite(request, site);
                        break;
                    case DTOStatus.DELETE:
                        commMemSiteService.deleteMemSite(request, site);
                        break;
                    default:
                        break;
                    }
                }
            }
        }

        // 会员家庭地点对应地址Id不为空则创建会员地点和地址，否则只根据地址Id更新地址
        if (logger.isDebugEnabled()) {
            logger.debug("call CommMemSiteService process homeMemSite");
        }
        MemSite homeMemSite = new MemSite();
        SpmLocation homeSpmLocation = member.getHomeSpmLocation();
        if (homeSpmLocation != null && homeSpmLocation.getLocationId() == null) {
            homeMemSite.setMemberId(member.getMemberId());
            homeMemSite.setSiteUseCode(MemberConstants.SITE_USE_HOME);
            homeMemSite.setDefaultFlag(BaseConstants.YES);
            homeMemSite.setSpmLocation(homeSpmLocation);
            commMemSiteService.saveMemSite(request, homeMemSite);
        } else if (homeSpmLocation != null) {
            commMemSiteService.updateSpmLocation(homeSpmLocation);
        }

        // 会员联系地点对应地址Id不为空则创建会员地点和地址，否则只根据地址Id更新地址
        if (logger.isDebugEnabled()) {
            logger.debug("call CommMemSiteService process contactMemSite");
        }
        MemSite contactMemSite = new MemSite();
        SpmLocation contactSpmLocation = member.getContactSpmLocation();
        if (contactSpmLocation != null && contactSpmLocation.getLocationId() == null) {
            contactMemSite.setMemberId(member.getMemberId());
            contactMemSite.setSiteUseCode(MemberConstants.SITE_USE_CTACT);
            contactMemSite.setDefaultFlag(BaseConstants.YES);
            contactMemSite.setSpmLocation(contactSpmLocation);
            commMemSiteService.saveMemSite(request, contactMemSite);
        } else if (contactSpmLocation != null) {
            commMemSiteService.updateSpmLocation(contactSpmLocation);
        }

        // gst地点对应地址Id不为空则创建地点和地址，否则只根据地址Id更新地址
        if (logger.isDebugEnabled()) {
            logger.debug("call CommMemSiteService process gstLocation");
        }
        MemSite gstMemSite = new MemSite();
        SpmLocation gstSpmLocation = member.getGstSpmLocation();
        if (gstSpmLocation != null && gstSpmLocation.getLocationId() == null
                && !StringUtil.isEmpty(gstSpmLocation.getCountryCode())) {
            gstMemSite.setMemberId(member.getMemberId());
            gstMemSite.setSiteUseCode(MemberConstants.SITE_USE_GST);
            gstMemSite.setDefaultFlag(BaseConstants.YES);
            gstMemSite.setSpmLocation(gstSpmLocation);
            commMemSiteService.saveMemSite(request, gstMemSite);
        } else if (gstSpmLocation != null && !StringUtil.isEmpty(gstSpmLocation.getCountryCode())) {
            commMemSiteService.updateSpmLocation(gstSpmLocation);
        } else if (gstSpmLocation != null && gstSpmLocation.getLocationId() != null
                && StringUtil.isEmpty(gstSpmLocation.getCountryCode())) {
            List<MemSite> homeCtactSites = siteMapper.selectHomeCtactLocByMemberId(member.getMemberId());
            for (MemSite memSite : homeCtactSites) {
                if (MemberConstants.SITE_USE_GST.equals(memSite.getSiteUseCode())) {
                    commMemSiteService.deleteMemSite(request, memSite);
                }
            }

        }

        // 账单地点必输
        if ((MemberConstants.OPER_TYPE_I).equals(operType) && !hasBillTo) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_BILL_SITE_REQUIRED, null);
        }
        // 配送地点必输
        if ((MemberConstants.OPER_TYPE_I).equals(operType) && !hasShipTo) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_SHIP_SITE_REQUIRED, null);
        }
    }

    private void saveAccounts(IRequest request, Member member) throws CommMemberException {
        List<MemAccount> accounts = member.getMemAccounts();
        if (accounts != null) {
            for (MemAccount account : accounts) {
                if (logger.isDebugEnabled()) {
                    logger.debug("account.getAccountId(): {}", account.getAccountId());
                    logger.debug("account.get__status(): {}", account.get__status());
                }

                String accountNumber = account.getAccountNumber();
                // 账户号码（明码）不为空时进行加密
                if(!"N".equals(member.getFlag())){
                    if (StringUtils.isNoneEmpty(accountNumber)) {
                        // 获取显示后四位账户号码
                        int length = accountNumber.length();
                        if (length < 4) {
                            throw new CommMemberException(CommMemberException.MSG_ERROR_ACCOUNT_NUMBER_LENGTH_VALIDATE,
                                    null);
                        }
                        String lastFourNumber = accountNumber.substring(length - 4);
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < length - 4; i++) {
                            stringBuffer = stringBuffer.append("*");
                        }
                        String maskedAccountNumber = stringBuffer + lastFourNumber;

                        // 获取加密后账户号码
                        // String decryptAccountNumber = null;
                        // decryptAccountNumber =
                        // aESClientService.encrypt(accountNumber);

                        // account.setAccountNumber(decryptAccountNumber);
                        account.setMaskedAccountNumber(maskedAccountNumber);
                    }
                }
                if (account.getAccountId() == null) {
                    account.setMemberId(member.getMemberId());
                    accountMapper.insert((account));
                } else {
                    accountMapper.updateByAccountId(account);
                }
            }
        }
    }

    private void saveCards(IRequest request, @StdWho Member member) throws CommMemberException {
        List<MemCard> cards = member.getMemCards();

        if (cards != null) {
            for (MemCard card : cards) {
                String status = card.get__status();

                if (logger.isDebugEnabled()) {
                    logger.debug("card.getCardId(): {}", card.getCardId());
                    logger.debug("card.get__status(): {}", card.get__status());
                }

                String cardNumber = card.getCardNumber();
                // 只有新增或者更新卡号情况下
                if (DTOStatus.ADD.equals(status) || card.getIsCardUpdate()) {
                    // 获取显示后四位卡号
                    int length = cardNumber.length();
                    String lastFourNumber = cardNumber.substring(length - 4);
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < length - 4; i++) {
                        stringBuffer = stringBuffer.append("*");
                    }
                    String maskedCardNumber = stringBuffer + lastFourNumber;

                    // 获取加密后卡号
                    String decryptCardNumber = null;
                    decryptCardNumber = aESClientService.encrypt(cardNumber);

                    card.setCardNumber(decryptCardNumber);
                    card.setMaskedCardNumber(maskedCardNumber);
                }

                if (DTOStatus.ADD.equals(status)) {
                    card.setMemberId(member.getMemberId());
                    cardMapper.insert((card));
                } else if (DTOStatus.UPDATE.equals(status)) {
                    cardMapper.updateByPrimaryKeySelective(card);
                } else if (DTOStatus.DELETE.equals(status)) {
                    cardMapper.deleteByPrimaryKey(card);
                }
            }
        }
    }

    private void validateMember(IRequest request, Member member) throws CommMemberException {
        // 中文名或英文名至少输入一个
        if (StringUtils.isEmpty(member.getChineseFirstName()) && StringUtils.isEmpty(member.getEnglishFirstName())) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_FIRST_NAME_REQUIRE_ONE, null);
        }

        // 中文姓和名汉字长度校验
        int chineseFirstNameLength = 0;
        int chineseLastNameLength = 0;
        chineseFirstNameLength = member.getChineseFirstName() != null ? member.getChineseFirstName().getBytes().length
                : 0;
        chineseLastNameLength = member.getChineseLastName() != null ? member.getChineseLastName().getBytes().length : 0;
        if (chineseFirstNameLength > 48) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_CHINESE_FIRST_NAME_LENGTH_ERROR, null);
        }

        if (chineseLastNameLength > 45) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_CHINESE_LAST_NAME_LENGTH_ERROR, null);
        }

        // 英文姓和名汉字长度校验
        int englishFirstNameLength = 0;
        int englishLastNameLength = 0;
        englishFirstNameLength = member.getEnglishFirstName() != null ? member.getEnglishFirstName().length() : 0;
        englishLastNameLength = member.getEnglishLastName() != null ? member.getEnglishLastName().length() : 0;
        if (englishFirstNameLength > 48) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_ENGLISH_FIRST_NAME_LENGTH_ERROR, null);
        }

        if (englishLastNameLength > 45) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_ENGLISH_LAST_NAME_LENGTH_ERROR, null);
        }

//        // 校验年龄
//        int age = getAge(member.getDob());
//        if (age < MemberConstants.POS_MEMBER_AGE_LIMIT) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("age less than limit {}", MemberConstants.POS_MEMBER_AGE_LIMIT);
//            }
//            throw new CommMemberException(CommMemberException.MSG_ERROR_AGE_LESS_THAN_LIMIT,
//                    new Object[] { MemberConstants.POS_MEMBER_AGE_LIMIT });
//        }
//        // 年龄大于18，小于市场提醒值，只输出日志，不报错
//        String paramAge = marketParamService.getParamValue(request, SystemProfileConstants.PARAM_MEMBER_AGE_ALERT,
//                SystemProfileConstants.MARKET, member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
//        if (logger.isDebugEnabled()) {
//            logger.debug("paramAge: {}", paramAge);
//        }
//        if (paramAge != null) {
//            if (age < Integer.valueOf(paramAge)) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("age less than market parameter {}", paramAge);
//                }
//            }
//        }

        // 校验家庭住址
        String paramHomeAddress = marketParamService.getParamValue(request,
                SystemProfileConstants.PARAM_MEMBER_HOME_ADDRESS, SystemProfileConstants.MARKET,
                member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
        if (logger.isDebugEnabled()) {
            logger.debug("paramHomeAddress: {}", paramHomeAddress);
        }
        if (BaseConstants.YES.equals(paramHomeAddress)) {
            if (member.getHomeSpmLocation() == null) {
                throw new CommMemberException(CommMemberException.MSG_ERROR_HOME_ADDRESS_REQUIRED, null);
            }
        } else {
            member.setHomeLocation(null);
            member.setHomeLocationId(null);
            member.setHomeSpmLocation(null);
        }

        // 校验种族
        String paramRrace = marketParamService.getParamValue(request, SystemProfileConstants.PARAM_MEMBER_RACE,
                SystemProfileConstants.MARKET, member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
        if (logger.isDebugEnabled()) {
            logger.debug("paramRrace: {}", paramRrace);
        }
        if (BaseConstants.YES.equals(paramRrace)) {
            if (member.getRace() == null) {
                throw new CommMemberException(CommMemberException.MSG_ERROR_RACE_REQUIRED, null);
            }
        } else {
            member.setRace(null);
        }

        // 校验健税保外
        String paramNhiTaxExcluded = marketParamService.getParamValue(request,
                SystemProfileConstants.PARAM_MEMBER_NHI_TAX_EXCLUDED, SystemProfileConstants.MARKET,
                member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
        if (logger.isDebugEnabled()) {
            logger.debug("paramNhiTaxExcluded: {}", paramNhiTaxExcluded);
        }
        if (BaseConstants.YES.equals(paramNhiTaxExcluded)) {
            if (member.getNhiTaxExcluded() == null) {
                throw new CommMemberException(CommMemberException.MSG_ERROR_NHI_TAX_EXCLUDED_REQUIRED, null);
            }
        } else {
            member.setNhiTaxExcluded(null);
        }

        // 校验GST ID号码
        String paramGstId = marketParamService.getParamValue(request, SystemProfileConstants.PARAM_MEMBER_GST_ID,
                SystemProfileConstants.MARKET, member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
        if (logger.isDebugEnabled()) {
            logger.debug("paramGstId: {}", paramGstId);
        }
        if (BaseConstants.YES.equals(paramGstId)) {
        } else {
            member.setGstIdNumber(null);
        }

        // 校验会员证件编码(和其他会员的证件编码)是否重复
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", member.getMemberId());
        map.put("idNumber", member.getIdNumber());
        String result = null;
        if (MemberConstants.MARKET_MY.equals(member.getMarketCode())) {//马来市场会员只校验证件编码,无需校验证件类型
            result = memberMapper.isMemberIdNumberExistForMY(map);
            if (!BaseConstants.YES.equals(result)) {//如果校验通过,则继续与其余市场的会员证件编号进行校验,此时需要校验证件类型
                map.put("idType", member.getIdType());
                result = memberMapper.isMemberIdNumberExist(map);
            }
        } else {
            map.put("idType", member.getIdType());
            map.put("phoneNo",member.getPhoneNo());
            map.put("marketId",member.getMarketId());
            //9633会员创建时保证依电话号码在所在市场唯一
//        result = memberMapper.isMemberIdNumberExist(map);
         result=memberMapper.isMemberPhoneNoExit(map);
        }
        if (BaseConstants.YES.equals(result)) {
          // throw new CommMemberException(CommMemberException.MSG_ERROR_MEMBER_ID_NUMBER_EXIST, null);
            throw new CommMemberException(CommMemberException.MSG_ERROR_MEMBER_PHONE_NO_EXIST, null);
        }

        // 校验公司商业注册码是否重复
        if (MemberConstants.MEMBER_TYPE_COMP.equals(member.getMemberType())) {
            Map<String, Object> brMap = new HashMap<String, Object>();
            brMap.put("memberId", member.getMemberId());
            brMap.put("brNumber", member.getBrNumber());
            result = memberMapper.isBrNumberExist(brMap);
            if (BaseConstants.YES.equals(result)) {
                throw new CommMemberException(CommMemberException.MSG_ERROR_BR_NUMBER_EXIST, null);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member saveMember(IRequest request, Member member) throws CommMemberException {
        if (logger.isDebugEnabled()) {
            logger.debug("member.getMemberId: {}", member.getMemberId());
            logger.debug("member.get__status: {}", member.get__status());
            logger.debug("member.getCreatedBy: {}", member.getCreatedBy());
            logger.debug("member.getCreationDate: {}", member.getCreationDate());
            logger.debug("member.getLastUpdateDate: {}", member.getLastUpdateDate());
        }

        // 判断操作类型： I/U
        String operType;
        if (member.getMemberId() == null) {
            operType = MemberConstants.OPER_TYPE_I;
        } else {
            operType = MemberConstants.OPER_TYPE_U;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("operType: {}", operType);
        }

        //updated by 13525 at 2018.03.07
        //validateMember(request, member);
        if(!"N".equals(member.getFlag())){
            validateMember(request, member);
        }

        // 新增
        if (MemberConstants.OPER_TYPE_I.equals(operType)) {
            // 判断会员ID是否已存在
            Member temp = memberMapper.selectMembersByMemberCode(member.getMemberCode());
            if (temp != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("memberCode is exists, memberCode: {}", member.getMemberCode());
                }
                throw new CommMemberException(CommMemberException.MSG_ERROR_MEMBER_CODE_EXIST, null);
            }

            // 新增变更所有权预存会员
            if (member.getIsChangeOwnership()) {
                member.setStatus(MemberConstants.MEMBER_STATUS_WCHG);

                // 新增会员
            } else {
                // 获取推荐人对应memberId（针对会员类型推荐人）
                Sponsor sponsorQry = new Sponsor();
                sponsorQry.setSponsorNo(member.getSponsorNo());
                Sponsor sponsor = sponserMapper.getValidSponsorBySponsorNo(sponsorQry);
                if (sponsor != null) {
                    member.setSponsorId(sponsor.getMemberId());
                    if (logger.isDebugEnabled()) {
                        logger.debug("member.setSponsorId : {}", sponsor.getMemberId());
                    }
                }

                // 根据组织参数获取会员ID前缀
                List<String> prefixs = marketParamService.getParamValues(request,
                        SystemProfileConstants.PARAM_MEMBER_CODE_PREFIX, SystemProfileConstants.MARKET,
                        member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
                String prefix = "";
                if (prefixs.size() > 0) {
                    prefix = prefixs.get(0);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("prefix : {}", prefix);
                }

                // 生成会员编码
                DocSequence docSequence = new DocSequence(MemberConstants.DOC_SEQ_MEMBER_DODE, prefix, null, null, null,
                        null);
                String seq;
                while (true) {
                    seq = docSequenceService.getSequence(request, docSequence, prefix,
                            MemberConstants.DOC_SEQ_MEMBER_DODE_MAX_LENGTH,
                            MemberConstants.DOC_SEQ_MEMBER_DODE_INI_VALUE);
                    // 排除特殊后缀的会员编码
                    if (!MemberConstants.DOC_SEQ_MEMBER_DODE_EXCELUDE
                            .equals(seq.substring(seq.length() - 1, seq.length()))) {
                        break;
                    }
                }

                //updated by 13525 at 2018.03.08
                //member.setMemberCode(seq);
                if("N".equals(member.getFlag())){
                    member.setMemberCode(member.getChineseFirstName());
                }else{
                    member.setMemberCode(seq);
                }


                if (logger.isDebugEnabled()) {
                    logger.debug("member.setMemberCode : {}", seq);
                }

                // 获取组织参数 - 新建会员自动激活
                List<String> autoActives = marketParamService.getParamValues(request,
                        SystemProfileConstants.PARAM_MEMBER_AUTO_APPROVED, SystemProfileConstants.MARKET,
                        member.getMarketId().toString(), SystemProfileConstants.ORG_TYPE_MARKET);
                String autoActive = BaseConstants.NO;
                if (autoActives.size() > 0) {
                    autoActive = autoActives.get(0);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("autoActive : {}", autoActive);
                }

                if (BaseConstants.YES.equals(autoActive)) {
                    // 会员自动激活，审核日期为当前时间
                    member.setStatus(MemberConstants.MEMBER_STATUS_ACTIVE);
                    member.setApprovalDate(new Date());
                } else {
                    member.setStatus(MemberConstants.MEMBER_STATUS_NEW);
                }
            }

            // 加入日期为当前时间
            member.setJointDate(new Date());
            if(member.getDob()==null){
                member.setDob(new Date());
            }

            // 外围系统同步标记为N
            member.setSyncFlag(MemberConstants.SYNC_FLAG_N);
            member.setDappSyncFlag(MemberConstants.SYNC_FLAG_Y); // 已在MemberControll同步DAPP
            memberMapper.insert(member);

            // 新增会员，且非新增变更所有权预存会员，则需要创建账号
            if (!member.getIsChangeOwnership()) {
                // 新增的会员调用API创建会员账号
                createMemAccount(request, member);
                if (logger.isDebugEnabled()) {
                    logger.debug("call memberService.createMemAccount success");
                }
            }

            // 更新
        } else {
            // 外围系统同步标记为N
            member.setSyncFlag(MemberConstants.SYNC_FLAG_N);
            member.setDappSyncFlag(MemberConstants.SYNC_FLAG_Y); // 已在MemberControll同步DAPP
            memberMapper.updateByPrimaryKeySelective(member);

            // 重置临时密码
            if (StringUtils.isNotEmpty(member.getTempPassword())) {
                resetPassword(request, member);
            }
        }

        saveRelationships(request, member);
        saveAttributes(request, member);
        // updated by 13525 at 2018.03.08
        if(!"N".equals(member.getFlag())){
            saveSites(request, member, operType);
        }else {
            List<MemAccount> accounts = new ArrayList<MemAccount>();
            MemAccount memAccount = new MemAccount();
            memAccount.setAccountId(member.getAccountId());
            memAccount.setMemberId(member.getMemberId());
            memAccount.setCreatedBy(member.getCreatedBy());
            memAccount.setLastUpdatedBy(member.getLastUpdatedBy());
            accounts.add(memAccount);
            member.setMemAccounts(accounts);
        }
        //saveSites(request, member, operType);

        saveAccounts(request, member);
        saveCards(request, member);

        return member;
    }

    /**
     * 重置密码.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @throws CommMemberException
     *             会员通用异常
     */
    public void resetPassword(IRequest request, Member member) throws CommMemberException {
        // 当前角色/用户是否启用重置密码
        String tempPwdReset = profileService.getProfileValue(request, MemberConstants.TEMP_PWD_RESET);
        if (!BaseConstants.YES.equals(tempPwdReset)) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_RESET_PWD_NO_PERMISSION, null);
        }

        Account account = sysAccountMapper.selectByMemberId(member.getMemberId());
        // 账号不存在则创建账号
        if (account == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("account for memberId {} is not exist, create new member account", member.getMemberId());
            }
            account = createMemAccount(request, member);
            if (logger.isDebugEnabled()) {
                logger.debug("create member success, account number {}", member.getMemberCode());
            }
        }

        // 账号临时密码失效时间
        String expiryHourStr = profileService.getProfileValue(request, MemberConstants.TEMP_PWD_EXPIRY_DATE);
        int expiryHour;
        if (expiryHourStr == null) {
            expiryHour = 1;
        } else {
            expiryHour = Integer.parseInt(expiryHourStr);
        }
        Date expiryDate = DateUtils.addHours(new Date(), expiryHour);

        accountService.updatePassword(account.getAccountId(), member.getTempPassword(), expiryDate, BaseConstants.YES);
        if (logger.isDebugEnabled()) {
            logger.debug("call accountService.updatePassword success");
        }

        /*
         * // 由配置文件控制是否发送短信 String sendSmsMsg =
         * profileService.getProfileValue(request,
         * MemberConstants.PROFILE_MM_SEND_SMS_MSG); if
         * (BaseConstants.YES.equals(sendSmsMsg)) { // 调用接口发送短信通知用户
         * List<MessageReceiver> receiverlist = new
         * ArrayList<MessageReceiver>(); MessageReceiver receiver = new
         * MessageReceiver(); Map<String, Object> data = new HashMap<String,
         * Object>(); receiver.setMessageAddress(member.getAreaCode() +
         * member.getPhoneNo());
         * receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
         * receiver.setReceiverId(member.getMemberId());
         * receiverlist.add(receiver); // 设置邮件模板里的数据 data.put("loginName",
         * account.getLoginName()); data.put("tmpPassword",
         * member.getTempPassword()); data.put("limit", expiryHour); try {
         * messageService.sendSmsMessage(-1L, member.getMarketId(),
         * MemberConstants.PHONE_NEW_MEMBER_PASSWORD, null, data, receiverlist);
         * 
         * if (logger.isDebugEnabled()) { logger.debug(
         * "call messageService.addMessage success"); } } catch (Exception e) {
         * if (logger.isErrorEnabled()) { logger.error(
         * "Error in messageService.sendSmsMessage.", e); } throw new
         * CommMemberException(CommMemberException.
         * MSG_ERROR_SEND_SMS_MESSAGE_ERROR, new Object[] {e.getMessage()}); } }
         */
    }

    /**
     * 创建会员账号.
     *
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @return 账户DTO
     * @throws CommMemberException
     *             会员异常
     */
    public Account createMemAccount(IRequest request, Member member) throws CommMemberException {
        // 校验会员账户是否已存在
        if (accountService.selectByLoginName(member.getMemberCode()) != null) {
            throw new CommMemberException(CommMemberException.MSG_ERROR_MEM_ACCOUNT_EXIST,
                    new Object[] { member.getMemberCode() });
        }

        try {
            // 插入账户表
            Account account = new Account();
            account.setLoginName(member.getMemberCode());

            // 在配置文件设置临时密码，否则默认和账号一致
            // String tempPassword = profileService.getProfileValue(request,
            // MemberConstants.TEMP_PWD);

            //updated by 13525 at 2018.03.08
           /* String tempPassword = accountService.generateRandomPassword();
            if (StringUtils.isEmpty(tempPassword)) {
                tempPassword = member.getMemberCode();
            }
            account.setPassword(tempPassword);*/


           // account.setStatus(Account.STATUS_ACTIVE);

            //account.setFirstLoginFlag(BaseConstants.YES);
            // 账号临时密码失效时间
            /*String expiryHourStr = profileService.getProfileValue(request, MemberConstants.TEMP_PWD_EXPIRY_DATE);
            int expiryHour;
            if (expiryHourStr == null) {
                expiryHour = 1;
            } else {
                expiryHour = Integer.parseInt(expiryHourStr);
            }
            Date expiryDate = DateUtils.addHours(new Date(), expiryHour);

            account.setPwdExpiryDate(expiryDate);*/

            //updated by 13525 at 2810.03.08
            String tempPassword = null;
            String expiryHourStr = null;
            int expiryHour = 0 ;
            if ("N".equals(member.getFlag())) {
                account.setPassword(member.getTempPassword());

                account.setStatus(Account.STATUS_ACTIVE);

                account.setFirstLoginFlag(BaseConstants.NO);
                account.setPwdExpiryDate(
                        new SimpleDateFormat(BaseConstants.SYSTEM_DATE_FORMAT).parse(BaseConstants.SYSTEM_MAX_DATE));
            }else{
                tempPassword = accountService.generateRandomPassword();
                if (StringUtils.isEmpty(tempPassword)) {
                    tempPassword = member.getMemberCode();
                }
                account.setPassword(tempPassword);

                account.setStatus(Account.STATUS_ACTIVE);

                account.setFirstLoginFlag(BaseConstants.YES);
                // 账号临时密码失效时间
                expiryHourStr = profileService.getProfileValue(request, MemberConstants.TEMP_PWD_EXPIRY_DATE);

                if (expiryHourStr == null) {
                    expiryHour = 1;
                } else {
                    expiryHour = Integer.parseInt(expiryHourStr);
                }
                Date expiryDate = DateUtils.addHours(new Date(), expiryHour);
                account.setPwdExpiryDate(expiryDate);
            }

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

            // 如果每天联系号码则不发送短信
            if (!StringUtils.isEmpty(member.getPhoneNo()) && !StringUtils.isEmpty(member.getAreaCode())) {
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
                    data.put("loginName", account.getLoginName());
                    // 若会员角色为VIP则采用VIP模板PHONE_NEW_MEMBER_WELCOME_VIP
                    if (MemberConstants.MM_ROLE_VIP.equals(member.getMemberRole())) {
                        messageService.sendSmsMessage(-1L, member.getMarketId(),
                                MemberConstants.PHONE_NEW_MEMBER_WELCOME_VIP, null, data, receiverlist);
                    } else {
                        // 设置邮件模板里的数据
                        data.put("tmpPassword", tempPassword);
                        data.put("limit", expiryHour);
                        messageService.sendSmsMessage(-1L, member.getMarketId(),
                                MemberConstants.PHONE_NEW_MEMBER_WELCOME, null, data, receiverlist);
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("call messageService.addMessage success");
                    }
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("联系号码为空，不发送短信");
                }
            }

            return account;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error in MemberServiceImpl.createMemAccount.", e);
            }

            throw new CommMemberException(CommMemberException.MSG_ERROR_CREATE_MEM_ACCOUNT,
                    new Object[] { e.getMessage() });
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member suspendMember(IRequest request, @StdWho Member member) throws CommMemberException {
        if (member == null || member.getMemberId() == null) {
            return member;
        }
        member.setStatus(MemberConstants.MEMBER_STATUS_SUSPENDED);
        memberMapper.updateStatusByMemberId(member);

        return member;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member terminateMember(IRequest request, @StdWho Member member) throws CommMemberException {
        if (member == null || member.getMemberId() == null) {
            return member;
        }

        member.setStatus(MemberConstants.MEMBER_STATUS_TERMINATED);
        memberMapper.updateStatusByMemberId(member);

        return member;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Member activeMember(IRequest request, @StdWho Member member) throws CommMemberException {
        if (member == null || member.getMemberId() == null) {
            return member;
        }

        member.setStatus(MemberConstants.MEMBER_STATUS_ACTIVE);
        memberMapper.updateStatusByMemberId(member);

        return member;
    }

    @Override
    public List<MemWithdraw> queryWithdrawsByParams(IRequest request, String memberCode, String periodFrom,
            String periodTo, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("periodFrom", periodFrom);
        map.put("periodTo", periodTo);
        map.put("memberCode", memberCode);
        List<MemWithdraw> memWithdraws = memWithdrawMapper.queryWithdraws(map);
        return memWithdraws;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<String> addWithdraw(IRequest request, MemWithdraw memWithdraw, String remainingBalance, int page,
            int pagesize, boolean isConfirm) throws CommMemberException {
        // Boolean isSuccess = false;
        List<String> list = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();
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

    @Override
    public List<String> getBalanceByMemberId(IRequest request, String memberId, int page, int pagesize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", memberId);
        List<String> balances = memWithdrawMapper.getBalanceByMemberId(map);
        return balances;
    }

    /**
     * 校验年龄.
     *
     * @param birthDate
     *            出生日期
     * @return int 年龄
     */
    public int getAge(Date birthDate) {
        int age = 0;

        Date now = new Date();

        SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new SimpleDateFormat("MM");

        String birth_year = format_y.format(birthDate);
        String current_year = format_y.format(now);

        String birth_month = format_M.format(birthDate);
        String current_month = format_M.format(now);

        // 年份估算
        age = Integer.parseInt(current_year) - Integer.parseInt(birth_year);

        // 如果未到出生月份，则age - 1
        if (current_month.compareTo(birth_month) < 0) {
            age -= 1;
        }
        if (age < 0) {
            age = 0;
        }
        return age;
    }

    @Override
    public Map isMemberBirthdayMonth(IRequest request, Long memberId) {

        Map<String, Boolean> map = new HashMap<String, Boolean>();

        Boolean brithday = Boolean.FALSE;

        Boolean brithdayV = Boolean.FALSE;
        // 生日订单
        Boolean brithdayOrder = Boolean.FALSE;
        // 生日订单 不含优惠券
        Boolean brithdayOrderV = Boolean.FALSE;

        Member member = memberMapper.selectByPrimaryKey(memberId);
        if (member.getDob() == null || member.getDob() == null) {
            map.put("brithday", brithday);
            map.put("brithdayOrder", brithdayOrder);
            map.put("brithdayOrderV", brithdayOrderV);
            map.put("brithdayV", brithdayV);
            return map;
        }
        // 出生日期
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(member.getDob());
        // 当前时间
        Calendar currDate = Calendar.getInstance();

        // 当前月份不是会员的生日月份时，返回false
        if (birthday.get(Calendar.MONTH) != currDate.get(Calendar.MONTH)) {
            brithday = Boolean.FALSE;
            brithdayV = Boolean.FALSE;
        } else {
            brithday = Boolean.TRUE;
            brithdayV = Boolean.TRUE;
        }
        // 距离该月1号 11个月之前的时间
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, MemberConstants.ELEVEN);
        lastDate.set(lastDate.get(Calendar.YEAR), lastDate.get(Calendar.MONTH), MemberConstants.ONE,
                MemberConstants.ZERO, MemberConstants.ZERO);
        // 订单
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setMemberId(member.getMemberId());
        salesOrder.setOrderDate(lastDate.getTime());
        List<String> statusList =new ArrayList<String>();
        statusList.add(OrderConstants.SALES_STATUS_COMP);
        statusList.add(OrderConstants.SALES_STATUS_WAIT_PAYMENT);
        salesOrder.setStatusList(statusList);
        salesOrder.setOrderType(OrderConstants.ORDER_TYPE_BIGF);
        List<SalesOrder> orders = salesOrderMapper.queryOrders(salesOrder);
        if (orders.size() > 0) {
            brithday = Boolean.FALSE;
            brithdayOrder = Boolean.TRUE;
            brithdayOrderV = Boolean.TRUE;

            Boolean returnall = Boolean.TRUE;
            // 如果订单全部退货，则不提示下过生日订单
            for (SalesOrder brithOrder : orders) {
                Boolean result = commSalesOrderService.isOrderReturnAll(request, brithOrder);
                if (Boolean.TRUE.equals(result)) {
                    // 该单退货
                } else {
                    returnall = Boolean.FALSE;
                }
            }
            if (returnall.equals(Boolean.TRUE)) {
                brithdayOrder = Boolean.FALSE;
                brithdayOrderV = Boolean.FALSE;
            }
        }
        // 优惠劵
        VoucherTransaction vocherTransaction = new VoucherTransaction();
        vocherTransaction.setMemberId(memberId);
        vocherTransaction.setTrxDate(lastDate.getTime());
        List<VoucherTransaction> voucherTransactions = voucherTransactionMapper
                .queryBirthVoucherTransaction(vocherTransaction);
        if (voucherTransactions.size() > 0) {
            brithday = Boolean.FALSE;
            brithdayOrder = Boolean.TRUE;
        }
        map.put("brithday", brithday);
        map.put("brithdayOrder", brithdayOrder);
        map.put("brithdayOrderV", brithdayOrderV);
        map.put("brithdayV", brithdayV);
        return map;
    }

    @Override
    public List<Member> queryMembersForOrder(IRequest request, Member member, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Member> members = memberMapper.queryMembersForOrder(member);
        for (Member temp : members) {
            OmMwsOrderPayment rbSum = omMwsOrderPaymentMapper.queryRemainingBalSum(temp.getMemberId());
            if (null != rbSum) {
                temp.setRemainingBalance(temp.getRemainingBalance().subtract(rbSum.getPaymentAmount()));
            }
        }
        return members;
    }

    @Override
    public List<Member> queryMembersForIpointOrder(IRequest request, Member member, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Member> members = memberMapper.queryMembersForIpointOrder(member);
        for (Member temp : members) {
            OmMwsOrderPayment rbSum = omMwsOrderPaymentMapper.queryRemainingBalSum(temp.getMemberId());
            if (null != rbSum) {
                temp.setRemainingBalance(temp.getRemainingBalance().subtract(rbSum.getPaymentAmount()));
            }
        }
        return members;
    }

    @Override
    public List<MemCard> autoQueryCard(IRequest request, Long memberId) {
        // TODO Auto-generated method stub
        List<MemCard> cards = cardMapper.selectByMemberId(memberId);
        for (MemCard card : cards) {
            StringBuffer sb = new StringBuffer();
            sb.append(card.getBankCode() == null ? "" : card.getBankCode());
            sb.append(MemberConstants.MM_LEFT_BRACKET);
            sb.append(card.getMaskedCardNumber().substring(card.getMaskedCardNumber().length() - 4,
                    card.getMaskedCardNumber().length()));
            sb.append(MemberConstants.MM_RIGHT_BRACKET);
            card.setBankCode(sb.toString());
        }
        return cards;
    }

    @Override
    public List<MemCard> queryAllCardInfo(IRequest request, Long memberId) {
        // TODO Auto-generated method stub
        List<MemCard> cards = cardMapper.selectAllBankByMemberId(memberId);
        return cards;
    }

    @Override
    public Map<String, Object> validateForVIPToDIS(IRequest request, Long memberId) throws CommMemberException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        Date date = new Date();
        Member member = memberMapper.selectByPrimaryKey(memberId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 根据快码取得VIP转经销商日期分水岭
        String shed = codeService.getCodeMeaningByValue(request, MemberConstants.VIP_CHANGE_DISTRIBUTOR_SHED,
                member.getMarketCode());
        // 若当前日期处在本月1号至本月分水岭日时,取该会员前两个月的订单，否则取前一个月
        if (shed != null && cal.get(Calendar.DAY_OF_MONTH) >= 1
                && cal.get(Calendar.DAY_OF_MONTH) <= Long.valueOf(shed)) {
            cal.add(Calendar.MONTH, -MemberConstants.TWO_MONTH_AGO);
        } else {
            cal.add(Calendar.MONTH, -MemberConstants.ONE_MONTH_AGO);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date beginDate = cal.getTime();
        map.put("memberId", memberId);
        map.put("beginDate", beginDate);
        map.put("endDate", date);
        List<SalesOrder> orders = salesOrderMapper.selectOrderForVIP(map);
        BigDecimal unitSellPriceSum = BigDecimal.ZERO;
        for (SalesOrder salesOrder : orders) {
            List<SalesLine> lines = salesOrder.getLines();
            for (SalesLine salesLine : lines) {
                // if (!("V".equals(salesLine.getStarterAid()))) {
                unitSellPriceSum = unitSellPriceSum
                        .add(salesLine.getUnitSellingPrice().multiply(salesLine.getQuantity()));
                // }
            }
        }
        // 根据快码取VIP转经销商销售额标准
        String criteria = codeService.getCodeMeaningByValue(request, MemberConstants.VIP_SALES_AMOUNT_CRITERIA,
                member.getMarketCode());
        if (criteria == null) {// 若没有定义该快码则表示该市场未启用VIP业务
            throw new CommMemberException(CommMemberException.MSG_ERROR_CURRENT_MARKET_DISABLE_VIP, null);
        }
        if (unitSellPriceSum.doubleValue() >= Double.valueOf(criteria)) {
            result.put("qualifiedFlag", true);
        } else {
            result.put("qualifiedFlag", false);
        }
        result.put("purchaseAmt", unitSellPriceSum);
        result.put("qualifiedAmt", Double.valueOf(criteria));
        result.put("dateFrom", beginDate);
        result.put("dateTo", date);
        return result;
    }

    @Override
    public Map<String, Object> getDeadLine(IRequest request, String memberCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = memberMapper.selectMembersByMemberCode(memberCode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        // 取当前日期
        Date date = new Date();
        String currentTime = sdf.format(date);
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(date);
        tempCal.add(Calendar.MONTH, -11);
        String beforeTimeStr = sdf.format(tempCal.getTime());
        map.put("memberCode", memberCode);
        map.put("monthFrom", beforeTimeStr);
        map.put("monthTo", currentTime);
        if (logger.isDebugEnabled()) {
            logger.debug("memberCode:{}", memberCode);
            logger.debug("monthFrom:{}", beforeTimeStr);
            logger.debug("monthTo:{}", currentTime);
        }
        List<MemRank> ranks = null;
        // 查出会员等级
        if (member != null) {
            ranks = rankMapper.selectCurrentAndHistoryRank(map);
        } else {
            ranks = rankMapper.selectRankForChn(map);
        }
        if (ranks != null && ranks.size() > 0) {
            Long rank = ranks.get(0).getRank();
            if (rank != null) {
                String rankDesc = codeService.getCodeMeaningByValue(request, MemberConstants.MM_MEMBER_RANK,
                        rank.toString());
                map.put("rank", rank.intValue());
                map.put("rankDesc", rankDesc);
            }
            // if (member != null &&
            // MemberConstants.MEMBER_STATUS_TERMINATED.equals(member.getStatus()))
            // {
            // // 若会员状态为终止则终止期限为0
            // map.put("deadLine", 0);
            // } else {
            int zeroPv = 0;
            int zeroDownLine = 0;
            // 计算连续PV和下线数为0的次数
            for (MemRank memRank : ranks) {
                if (memRank.getPv() == null || memRank.getPv().doubleValue() == 0.0) {
                    zeroPv++;
                }
                if (memRank.getDownLine() == null || memRank.getDownLine().doubleValue() == 0.0) {
                    zeroDownLine++;
                }
                if ((memRank.getPv() != null && memRank.getPv().doubleValue() != 0.0)
                        || (memRank.getDownLine() != null && memRank.getDownLine().doubleValue() != 0.0)) {
                    break;
                }
            }
            if (zeroPv > 12) { // 累计值最大为12
                zeroPv = 12;
            }
            if (zeroDownLine > 12) {// 同上
                zeroDownLine = 12;
            }
            // 取连续PV值为0的月份数、下线数为0的月份数中两者的最小值，用12去减，得到终止日期
            int deadLine = 12 - (zeroPv < zeroDownLine ? zeroPv : zeroDownLine);
            if (logger.isDebugEnabled()) {
                logger.debug("The Deadline is : {}", String.valueOf(deadLine));
            }
            tempCal.setTime(date);
            tempCal.add(Calendar.MONTH, deadLine);
            map.put("deadLine", sdf.format(tempCal.getTime()));
            // }

        } else { // 若该会员在日结表和月结表中查不到近12个月的记录
            if (member != null && MemberConstants.MEMBER_STATUS_TERMINATED.equals(member.getStatus())) {
                // 若会员状态为终止则终止期限为0
                map.put("deadLine",
                        member.getTerminationDate() == null ? currentTime : sdf.format(member.getTerminationDate()));
            } else {
                tempCal.setTime(date);
                tempCal.add(Calendar.MONTH, 12);
                map.put("deadLine", sdf.format(tempCal.getTime()));
            }
        }
        // 查出推荐人等级
        String sponsorNo = null;
        if (member != null) {
            sponsorNo = member.getSponsorNo();
        }
        if (sponsorNo != null) {
            map.put("memberCode", sponsorNo);
            Member sponsor = memberMapper.selectMembersByMemberCode(sponsorNo);
            if (sponsor != null) {
                ranks = rankMapper.selectCurrentAndHistoryRank(map);
            } else {
                ranks = rankMapper.selectRankForChn(map);
            }
            if (ranks != null && ranks.size() > 0) {
                Long rank = ranks.get(0).getRank();
                if (rank != null) {
                    String rankDesc = codeService.getCodeMeaningByValue(request, MemberConstants.MM_MEMBER_RANK,
                            rank.toString());
                    map.put("sponsorRankDesc", rankDesc);
                }
            }
        }
        // 取最后一次订购日期
        Date lastOrderDate = null;
        if (member != null) {
            lastOrderDate = salesOrderMapper.selectMemberLastOrderDate(member.getMemberId());
        }
        map.put("lastOrderDate", lastOrderDate == null ? "" : sdf.format(lastOrderDate));
        return map;
    }

    @Override
    public Long insert(IRequest request, Member record) {
        return memberMapper.insertSelective(record);
    }

}