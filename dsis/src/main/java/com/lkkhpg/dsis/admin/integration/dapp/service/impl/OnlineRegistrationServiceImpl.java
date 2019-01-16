/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OnlineRegistrationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOnlineRegistrationService;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.config.dto.SpmCountry;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.config.mapper.SpmBankMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmCityMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmCountryMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmStateMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemAttribute;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 线上入会实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class OnlineRegistrationServiceImpl implements IOnlineRegistrationService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private SpmBankMapper spmBankMapper;

    @Autowired
    private SpmCountryMapper spmCountryMapper;

    @Autowired
    private SpmStateMapper spmStateMapper;

    @Autowired
    private SpmCityMapper spmCityMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Autowired
    private ICodeService codeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addDistributor(OnlineRegistrationRequest onlineRegistrationRequest)
            throws CommMemberException, DAppException {
        List<Member> membersByAppNo = memberMapper.selectByDappNo(onlineRegistrationRequest.getAppNo());
        if (membersByAppNo != null && membersByAppNo.size() > 0) {
            throw new DAppException(IntegrationException.MSG_ERROR_DAPP_NO_EXIST, IntegrationConstant.ERROR_20017,
                    null);
        }
        IRequest request = RequestHelper.newEmptyRequest();
        RequestHelper.setCurrentRequest(request);
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = new Member();
        member.setDappAppNo(onlineRegistrationRequest.getAppNo());
        List<MemSite> memSites = new ArrayList<MemSite>();
        List<MemRelationship> memRelationships = null;
        List<MemAttribute> memAttributes = new ArrayList<MemAttribute>();
        SpmMarket market = spmMarketMapper.selectMarketByCode(onlineRegistrationRequest.getMarket());
        if (market == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_MARKET_NOT_EXIST, IntegrationConstant.ERROR_20022,
                    null);
        }
        Long marketId = market.getMarketId();
        Long companyId = market.getCompanyId();
        member.setMarketId(marketId);
        request.setAttribute(SystemProfileConstants.MARKET_ID, marketId);
        request.setAccountId(dAppUtilService.getDappAccountId());
        request.setLocale(IntegrationConstant.DEFAULT_LANG);
        member.setSponsorNo(onlineRegistrationRequest.getSponsorId());
        Member sponsor = memberMapper.selectMembersByMemberCode(onlineRegistrationRequest.getSponsorId());
        if (sponsor != null) {
            member.setSponsorId(sponsor.getMemberId());
        }

        String firstNameChn = onlineRegistrationRequest.getFirstNameChn();
        String lastNameChn = onlineRegistrationRequest.getLastNameChn();
        member.setChineseFirstName(firstNameChn);
        member.setChineseLastName(lastNameChn);
        String firstNameEng = onlineRegistrationRequest.getFirstNameEng();
        String lastNameEng = onlineRegistrationRequest.getLastNameEng();
        // 校验名至少输入一种语言
        if (StringUtils.isEmpty(firstNameChn + lastNameChn) && StringUtils.isEmpty(firstNameEng + lastNameEng)) {
            throw new DAppException(IntegrationException.MSG_ERROR_MEMBER_CHN_ENG_LEAST_ONE,
                    IntegrationConstant.ERROR_20002, null);
        }
        member.setEnglishFirstName(firstNameEng);
        member.setEnglishLastName(lastNameEng);
        member.setMemberRole(onlineRegistrationRequest.getMemberRole());
        member.setMemberType(onlineRegistrationRequest.getMemberType());
        member.setIdType(onlineRegistrationRequest.getIdType());
        member.setIdNumber(onlineRegistrationRequest.getIdNumber());
        Date birthDay = dAppUtilService.dateStringToDate(onlineRegistrationRequest.getBirthday());
        birthDay = addHourForSummerTime(birthDay);
        member.setDob(birthDay);
        member.setGender(onlineRegistrationRequest.getGender());
        member.setRefCompany(onlineRegistrationRequest.getCompanyName());
        member.setCompanyId(companyId);
        member.setBrNumber(onlineRegistrationRequest.getCompanyIdNumber());
        member.setLanguage(onlineRegistrationRequest.getLanguage());
        member.setEducation(onlineRegistrationRequest.getEducation());
        member.setRace(onlineRegistrationRequest.getRace());
        if (StringUtils.isEmpty(onlineRegistrationRequest.getRace())
                && market.getCode().equals(IntegrationConstant.MARKET_CODE_MY)) {
            member.setRace("MALA");
        }
        member.setNationality(onlineRegistrationRequest.getNationality());
        String country = codeService.getCodeMeaningByValue(request, IntegrationConstant.MM_DEFAULT_COUNTRY,
                onlineRegistrationRequest.getMarket());
        member.setCountry(country);
        member.setPhoneNo(onlineRegistrationRequest.getMobileNumber());
        member.setAreaCode(onlineRegistrationRequest.getMobileAreaCode());
        member.setOthPhoneNo(onlineRegistrationRequest.getPhoneNumber());
        member.setOthAreaCode(onlineRegistrationRequest.getPhoneNumberAreaCode());
        member.setEmail(onlineRegistrationRequest.getEmailAddress());

        memSites.add(self().setHomeSite(onlineRegistrationRequest, member));
        memSites.add(self().setCtactSite(onlineRegistrationRequest, member));
        memSites.add(self().setBillSite(onlineRegistrationRequest, member));
        memSites.add(self().setShipSite(onlineRegistrationRequest, member));

        member.setMemSites(memSites);

        MemAccount memAccount = self().setAccount(onlineRegistrationRequest, member, request);
        if (memAccount != null) {
            List<MemAccount> memAccounts = new ArrayList<MemAccount>();
            memAccounts.add(memAccount);
            member.setMemAccounts(memAccounts);
        }

        MemRelationship memRelationship_spous = self().setSpouseInfo(onlineRegistrationRequest);
        MemRelationship memRelationship_benf = self().setBenfInfo(onlineRegistrationRequest);
        if (memRelationship_spous != null || memRelationship_benf != null) {
            memRelationships = new ArrayList<MemRelationship>();
        }
        if (memRelationship_spous != null) {
            memRelationships.add(memRelationship_spous);
        }
        if (memRelationship_benf != null) {
            memRelationships.add(memRelationship_benf);
        }
        member.setMemRelationships(memRelationships);

        self().setAttributes(memAttributes);
        member.setMemAttributes(memAttributes);
        String promotionOptIn = onlineRegistrationRequest.getPromotionOptIn();
        if (StringUtils.isEmpty(promotionOptIn)) {
            member.setAdOptIn(BaseConstants.NO);
        } else {
            member.setAdOptIn(promotionOptIn);
        }
        member.setSysMsgIn(IntegrationConstant.YES);
        member.setAcceptBonus(IntegrationConstant.YES);
        member.setNhiTaxExcluded(BaseConstants.NO);
        member.setJointSite(IntegrationConstant.DEFAULT_JOIN_SITE);
        String jointSite = codeService.getCodeMeaningByValue(request, IntegrationConstant.DAPP_DEFAULT_JOINT_SITE,
                onlineRegistrationRequest.getMarket());
        if (jointSite == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_SALES_ORG_NO_EXIST, IntegrationConstant.ERROR_20021,
                    null);
        }
        SpmSalesOrganization record = new SpmSalesOrganization();
        record.setMarketId(marketId);
        record.setCode(jointSite);
        List<SpmSalesOrganization> spmSalesOrganizations = spmSalesOrganizationMapper
                .getSalesOrgByCodeAndMarket(record);
        if (spmSalesOrganizations != null && spmSalesOrganizations.size() > 0) {
            member.setJointSite(String.valueOf(spmSalesOrganizations.get(0).getSalesOrgId()));
        }
        if (StringUtils.isEmpty(onlineRegistrationRequest.getRegisterationChannel())) {
            member.setJointSiteType(IntegrationConstant.DEFAULT_JOINT_SITE_TYPE);
        } else {
            member.setJointSiteType(onlineRegistrationRequest.getRegisterationChannel());
        }
        if (StringUtils.isEmpty(onlineRegistrationRequest.getTravelPlanMonth())) {
            String meaning = codeService.getCodeMeaningByValue(request, IntegrationConstant.MM_TRAVEL_PLAN_MONTH,
                    onlineRegistrationRequest.getMarket());
            if (meaning != null) {
                member.setTravelPlanMonth(meaning);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                String travelPlanMonth = sdf.format(new Date());
                member.setTravelPlanMonth(travelPlanMonth);
            }
        } else {
            member.setTravelPlanMonth(onlineRegistrationRequest.getTravelPlanMonth());
        }
        if (StringUtils.isEmpty(onlineRegistrationRequest.getBonusPaymentMethod())) {
            member.setBonusRcvMethod(IntegrationConstant.DEFAULT_BONUS_RCV_METHOD);
        } else {
            member.setBonusRcvMethod(onlineRegistrationRequest.getBonusPaymentMethod());
        }
        member.setDappSyncFlag(IntegrationConstant.YES);
        try {
            Member saveMember = commMemberService.saveMember(request, member);
            map.put("appNo", onlineRegistrationRequest.getAppNo());
            map.put("distributorNumber", saveMember.getMemberCode());
            map.put("result", 1);
            map.put("description", null);
        } catch (Exception e) {
            map.put("appNo", onlineRegistrationRequest.getAppNo());
            map.put("distributorNumber", null);
            map.put("result", -1);
            map.put("description", null);
            throw e;
        }
        return map;
    }

    @Override
    public MemAccount setAccount(OnlineRegistrationRequest onlineRegistrationRequest, Member member, IRequest request)
            throws DAppException {
        if (StringUtils.isNoneEmpty(onlineRegistrationRequest.getBankAccount())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBankAccountIdNumber())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBankCode())) {
            MemAccount memAccount = new MemAccount();
            SpmBank spmBank = new SpmBank();
            spmBank.setBankNumber(onlineRegistrationRequest.getBankCode());
            spmBank.setMarketId(member.getMarketId());
            List<SpmBank> spmBanks = spmBankMapper.queryBySpmBank(spmBank);
            if (spmBanks == null || spmBanks.size() < 1) {
                throw new DAppException(IntegrationException.MSG_ERROR_BANK_IS_NOT_EXIST,
                        IntegrationConstant.ERROR_20005, null);
            }
            memAccount.setBankId(spmBanks.get(0).getBankId());
            if (onlineRegistrationRequest.getBranchCode() != null) {
                spmBank.setBankNumber(onlineRegistrationRequest.getBranchCode());
                List<SpmBank> spmBranchBanks = spmBankMapper.queryBySpmBank(spmBank);
                if (spmBranchBanks == null || spmBranchBanks.size() < 1) {
                    throw new DAppException(IntegrationException.MSG_ERROR_BRANCH_BANK_IS_NOT_EXIST,
                            IntegrationConstant.ERROR_20023, null);
                }
                memAccount.setBankBranchId(spmBranchBanks.get(0).getBankId());
            }
            memAccount.setIdNumber(onlineRegistrationRequest.getBankAccountIdNumber());
            memAccount.setAccountHolder(onlineRegistrationRequest.getPayeeName());
            memAccount.setAccountNumber(onlineRegistrationRequest.getBankAccount());
            memAccount.setComments(onlineRegistrationRequest.getBankAccountRemark());
            memAccount.set__status(DTOStatus.ADD);
            return memAccount;
        }
        return null;
    }

    @Override
    public void setAttributes(List<MemAttribute> memAttributes) {
        // 申请表
        MemAttribute attribute_appform = new MemAttribute();
        attribute_appform.setAttribute(IntegrationConstant.APPLICATION_FORM);
        attribute_appform.setValue(IntegrationConstant.NO);
        memAttributes.add(attribute_appform);
        // 银行复印件
        MemAttribute attribute_bankcopy = new MemAttribute();
        attribute_bankcopy.setAttribute(IntegrationConstant.BANCK_ACCOUNT_COPY);
        attribute_bankcopy.setValue(IntegrationConstant.NO);
        memAttributes.add(attribute_bankcopy);
        // 保密协议
        MemAttribute attribute_confagree = new MemAttribute();
        attribute_confagree.setAttribute(IntegrationConstant.CONFIDENTIALITY_AGREEMENT);
        attribute_confagree.setValue(IntegrationConstant.NO);
        memAttributes.add(attribute_confagree);
        // ID复印件
        MemAttribute attribute_idcopy = new MemAttribute();
        attribute_idcopy.setAttribute(IntegrationConstant.ID_COPY);
        attribute_idcopy.setValue(IntegrationConstant.NO);
        memAttributes.add(attribute_idcopy);
    }

    @Override
    public MemRelationship setBenfInfo(OnlineRegistrationRequest onlineRegistrationRequest) throws DAppException {
        if (StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryFirstNameChn())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryLastNameChn())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryFirstNameEng())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryLastNameEng())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryBirthday())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryIDType())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryID())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getBeneficiaryRelationship())) {
            // 新增受益人信息
            MemRelationship memRelationship_benf = new MemRelationship();
            // memRelationship_benf.setMemberId(member.getMemberId());
            memRelationship_benf.set__status(DTOStatus.ADD);
            memRelationship_benf.setRelType(IntegrationConstant.REL_TYPE_BENF);

            String beneficiaryFirstNameChn = onlineRegistrationRequest.getBeneficiaryFirstNameChn();
            String beneficiaryLastNameChn = onlineRegistrationRequest.getBeneficiaryLastNameChn();
            String beneficiaryNameChn = beneficiaryFirstNameChn + beneficiaryLastNameChn;

            String beneficiaryFirstNameEng = onlineRegistrationRequest.getBeneficiaryFirstNameEng();
            String beneficiaryLastNameEng = onlineRegistrationRequest.getBeneficiaryLastNameEng();
            String beneficiaryNameEng = beneficiaryFirstNameEng + " " + beneficiaryLastNameEng;

            if (StringUtils.isBlank(beneficiaryNameChn) && StringUtils.isBlank(beneficiaryNameEng)) {
                throw new DAppException(IntegrationException.MSG_ERROR_BENEF_CHN_ENG_LEAST_ONE,
                        IntegrationConstant.ERROR_20002, null);
            } else {
                if (StringUtils.isNoneEmpty(beneficiaryNameChn)) {
                    // 校验受益人中文姓名长度
                    if (beneficiaryFirstNameChn.getBytes().length
                            + beneficiaryLastNameChn.getBytes().length > IntegrationConstant.CHN_NAME_LENGTH) {
                        throw new DAppException(IntegrationException.MSG_ERROR_FIRST_PLUS_LAST_TOO_LONG,
                                IntegrationConstant.ERROR_20004, null);
                    }
                }
                memRelationship_benf.setChineseName(beneficiaryNameChn);
                if (StringUtils.isNoneEmpty(beneficiaryNameEng)) {
                    // 校验受益人英文姓名长度
                    if (beneficiaryFirstNameEng.length()
                            + beneficiaryLastNameEng.length() > IntegrationConstant.ENG_NAME_LENGTH) {
                        throw new DAppException(IntegrationException.MSG_ERROR_FIRST_PLUS_LAST_TOO_LONG,
                                IntegrationConstant.ERROR_20004, null);
                    }
                }
                memRelationship_benf.setEnglishName(beneficiaryNameEng);
            }
            if (StringUtils.isEmpty(onlineRegistrationRequest.getBeneficiaryIDType())) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_ID_TYPE_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20009, null);
            }
            memRelationship_benf.setIdType(onlineRegistrationRequest.getBeneficiaryIDType());
            if (StringUtils.isEmpty(onlineRegistrationRequest.getBeneficiaryID())) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_ID_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20010, null);
            }
            memRelationship_benf.setIdNumber(onlineRegistrationRequest.getBeneficiaryID());
            if (StringUtils.isEmpty(onlineRegistrationRequest.getBeneficiaryRelationship())) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_DESC_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20011, null);
            }
            memRelationship_benf.setRelDesc(onlineRegistrationRequest.getBeneficiaryRelationship());
            if (onlineRegistrationRequest.getBeneficiaryBirthday() != null) {
                Date beneficiaryBirthDay = dAppUtilService
                        .dateStringToDate(onlineRegistrationRequest.getBeneficiaryBirthday());
                beneficiaryBirthDay = addHourForSummerTime(beneficiaryBirthDay);
                memRelationship_benf.setDob(beneficiaryBirthDay);
            }
            return memRelationship_benf;
        }
        return null;
    }

    @Override
    public MemRelationship setSpouseInfo(OnlineRegistrationRequest onlineRegistrationRequest) throws DAppException {
        if (StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseFirstNameEng())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseLastNameEng())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseFirstNameChn())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseLastNameChn())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseBirthday())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseIDType())
                || StringUtils.isNoneEmpty(onlineRegistrationRequest.getSpouseID())) {
            // 新增配偶信息
            MemRelationship memRelationship_spous = new MemRelationship();
            // memRelationship_spous.setMemberId(member.getMemberId());
            memRelationship_spous.set__status(DTOStatus.ADD);
            memRelationship_spous.setRelType(IntegrationConstant.REL_TYPE_SPOUS);

            String spouseFirstNameChn = onlineRegistrationRequest.getSpouseFirstNameChn();
            String spouseLastNameChn = onlineRegistrationRequest.getSpouseLastNameChn();
            String spouseNameChn = spouseFirstNameChn + spouseLastNameChn;

            String spouseFirstNameEng = onlineRegistrationRequest.getSpouseFirstNameEng();
            String spouseLastNameEng = onlineRegistrationRequest.getSpouseLastNameEng();
            String spouseNameEng = spouseFirstNameEng + " " + spouseLastNameEng;

            if (StringUtils.isBlank(spouseNameChn) && StringUtils.isBlank(spouseNameEng)) {
                throw new DAppException(IntegrationException.MSG_ERROR_SPOUSE_CHN_ENG_LEAST_ONE,
                        IntegrationConstant.ERROR_20002, null);
            } else {
                if (StringUtils.isNoneEmpty(spouseNameChn) && StringUtils.isNoneEmpty(spouseFirstNameChn)
                        && StringUtils.isNoneEmpty(spouseLastNameChn)) {
                    // 校验配偶中文姓名长度
                    if (spouseFirstNameChn.getBytes().length
                            + spouseLastNameChn.getBytes().length > IntegrationConstant.CHN_NAME_LENGTH) {
                        throw new DAppException(IntegrationException.MSG_ERROR_FIRST_PLUS_LAST_TOO_LONG,
                                IntegrationConstant.ERROR_20004, null);
                    }
                }
                memRelationship_spous.setChineseName(spouseNameChn);
                if (StringUtils.isNoneEmpty(spouseNameEng) && StringUtils.isNoneEmpty(spouseFirstNameEng)
                        && StringUtils.isNoneEmpty(spouseLastNameEng)) {
                    // 校验配偶英文姓名长度
                    if (spouseFirstNameEng.length()
                            + spouseLastNameEng.length() > IntegrationConstant.ENG_NAME_LENGTH) {
                        throw new DAppException(IntegrationException.MSG_ERROR_FIRST_PLUS_LAST_TOO_LONG,
                                IntegrationConstant.ERROR_20004, null);
                    }
                }
                memRelationship_spous.setEnglishName(spouseNameEng);
            }
            memRelationship_spous.setIdType(onlineRegistrationRequest.getSpouseIDType());
            memRelationship_spous.setIdNumber(onlineRegistrationRequest.getSpouseID());
            IRequest request = RequestHelper.newEmptyRequest();
            request.setLocale(BaseConstants.DEFAULT_LANG);
            if (StringUtils.isEmpty(onlineRegistrationRequest.getGender())) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_GENDER_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20012, null);
            }
            memRelationship_spous.setRelDesc(codeService.getCodeMeaningByValue(request,
                    IntegrationConstant.MEMBER_SPOUSE_REL_CODE, onlineRegistrationRequest.getGender()));
            if (onlineRegistrationRequest.getSpouseBirthday() != null) {
                Date spouseBirthDay = dAppUtilService.dateStringToDate(onlineRegistrationRequest.getSpouseBirthday());
                spouseBirthDay = addHourForSummerTime(spouseBirthDay);
                memRelationship_spous.setDob(spouseBirthDay);
            }
            return memRelationship_spous;
        }
        return null;
    }

    @Override
    public MemSite setCtactSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member)
            throws DAppException {
        String curCountry = onlineRegistrationRequest.getCurCountry();
        String curState = onlineRegistrationRequest.getCurState();
        String curCity = onlineRegistrationRequest.getCurCity();
        self().validateLocation(curCountry, curState, curCity, IntegrationConstant.SITE_CTACT);
        // 新建联系地址
        SpmLocation spmLocation_ctact = new SpmLocation();
        // TODO:可能需要做映射
        spmLocation_ctact.setCountryCode(curCountry);
        spmLocation_ctact.setStateCode(curState);
        spmLocation_ctact.setCityCode(curCity);
        spmLocation_ctact.setAddressLine1(onlineRegistrationRequest.getCurAddress());
        spmLocation_ctact.setAddressLine2(onlineRegistrationRequest.getCurAddress2());
        spmLocation_ctact.setAddressLine3(onlineRegistrationRequest.getCurAddress3());
        spmLocation_ctact.setZipCode(onlineRegistrationRequest.getCurZipCode());
        member.setContactSpmLocation(spmLocation_ctact);

        MemSite memSite_ctact = new MemSite();
        // memSite_ctact.setMemberId(member.getMemberId());
        // TODO:可能需要做映射
        memSite_ctact.setSiteUseCode(IntegrationConstant.SITE_CTACT);
        memSite_ctact.setDefaultFlag(IntegrationConstant.YES);
        memSite_ctact.set__status(DTOStatus.ADD);
        memSite_ctact.setSpmLocation(spmLocation_ctact);
        return memSite_ctact;
    }

    @Override
    public MemSite setHomeSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member)
            throws DAppException {
        String perCountry = onlineRegistrationRequest.getPerCountry();
        String perState = onlineRegistrationRequest.getPerState();
        String perCity = onlineRegistrationRequest.getPerCity();
        self().validateLocation(perCountry, perState, perCity, IntegrationConstant.SITE_HOME);
        // 新建家庭地址
        SpmLocation spmLocation_per = new SpmLocation();
        spmLocation_per.setCountryCode(perCountry);
        spmLocation_per.setStateCode(perState);
        spmLocation_per.setCityCode(perCity);
        spmLocation_per.setAddressLine1(onlineRegistrationRequest.getPerAddress());
        spmLocation_per.setAddressLine2(onlineRegistrationRequest.getPerAddress2());
        spmLocation_per.setAddressLine3(onlineRegistrationRequest.getPerAddress3());
        spmLocation_per.setZipCode(onlineRegistrationRequest.getPerZipCode());
        member.setHomeSpmLocation(spmLocation_per);

        MemSite memSite_per = new MemSite();
        // memSite_per.setMemberId(member.getMemberId());
        memSite_per.setSiteUseCode(IntegrationConstant.SITE_HOME);
        memSite_per.setDefaultFlag(IntegrationConstant.YES);
        memSite_per.set__status(DTOStatus.ADD);
        memSite_per.setSpmLocation(spmLocation_per);
        return memSite_per;
    }

    @Override
    public MemSite setBillSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member) {
        // 新建账单地址
        SpmLocation spmLocation_bill = new SpmLocation();
        spmLocation_bill.setCountryCode(onlineRegistrationRequest.getCurCountry());
        spmLocation_bill.setStateCode(onlineRegistrationRequest.getCurState());
        spmLocation_bill.setCityCode(onlineRegistrationRequest.getCurCity());
        spmLocation_bill.setAddressLine1(onlineRegistrationRequest.getCurAddress());
        spmLocation_bill.setAddressLine2(onlineRegistrationRequest.getCurAddress2());
        spmLocation_bill.setAddressLine3(onlineRegistrationRequest.getCurAddress3());
        spmLocation_bill.setZipCode(onlineRegistrationRequest.getCurZipCode());

        MemSite memSite_bill = new MemSite();
        memSite_bill.setSpmLocation(spmLocation_bill);
        memSite_bill.setAreaCode(onlineRegistrationRequest.getMobileAreaCode());
        memSite_bill.setPhone(onlineRegistrationRequest.getMobileNumber());
        String chineseFirstName = member.getChineseFirstName();
        String chineseLastName = member.getChineseLastName();
        if (StringUtils.isNoneEmpty(chineseFirstName) || StringUtils.isNoneEmpty(chineseLastName)) {
            memSite_bill.setName(chineseFirstName + chineseLastName);
        } else {
            memSite_bill.setName(member.getEnglishFirstName() + member.getEnglishLastName());
        }
        memSite_bill.setSiteUseCode(IntegrationConstant.SITE_BILL);
        memSite_bill.setDefaultFlag(IntegrationConstant.YES);
        memSite_bill.set__status(DTOStatus.ADD);
        return memSite_bill;
    }

    @Override
    public MemSite setShipSite(OnlineRegistrationRequest onlineRegistrationRequest, Member member) {
        // 新建收货地址
        SpmLocation spmLocation_ship = new SpmLocation();
        spmLocation_ship.setCountryCode(onlineRegistrationRequest.getCurCountry());
        spmLocation_ship.setStateCode(onlineRegistrationRequest.getCurState());
        spmLocation_ship.setCityCode(onlineRegistrationRequest.getCurCity());
        spmLocation_ship.setAddressLine1(onlineRegistrationRequest.getCurAddress());
        spmLocation_ship.setAddressLine2(onlineRegistrationRequest.getCurAddress2());
        spmLocation_ship.setAddressLine3(onlineRegistrationRequest.getCurAddress3());
        spmLocation_ship.setZipCode(onlineRegistrationRequest.getCurZipCode());

        MemSite memSite_ship = new MemSite();
        memSite_ship.setSpmLocation(spmLocation_ship);
        memSite_ship.setAreaCode(onlineRegistrationRequest.getMobileAreaCode());
        memSite_ship.setPhone(onlineRegistrationRequest.getMobileNumber());
        String chineseFirstName = member.getChineseFirstName();
        String chineseLastName = member.getChineseLastName();
        if (StringUtils.isNoneEmpty(chineseFirstName) || StringUtils.isNoneEmpty(chineseLastName)) {
            memSite_ship.setName(chineseFirstName + chineseLastName);
        } else {
            memSite_ship.setName(member.getEnglishFirstName() + member.getEnglishLastName());
        }
        memSite_ship.setSiteUseCode(IntegrationConstant.SITE_SHIP);
        memSite_ship.setDefaultFlag(IntegrationConstant.YES);
        memSite_ship.set__status(DTOStatus.ADD);
        return memSite_ship;
    }

    public void validateLocation(String countryCode, String stateCode, String cityCode, String siteUseCode)
            throws DAppException {
        SpmCountry country = spmCountryMapper.selectByPrimaryKey(countryCode);
        if (country == null) {
            throw new DAppException(
                    IntegrationException.MSG_ERROR_COUNTRY_CODE_IS_NOT_EXIST + siteUseCode.toLowerCase(),
                    IntegrationConstant.ERROR_20018, null);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("countryCode", countryCode);
        map.put("stateCode", stateCode);
        SpmState state = spmStateMapper.queryStateByCountryAndState(map);
        if (state == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_STATE_CODE_IS_NOT_EXIST + siteUseCode.toLowerCase(),
                    IntegrationConstant.ERROR_20019, null);
        }
        map.put("cityCode", cityCode);
        SpmCity city = spmCityMapper.queryCityByStateAndCity(map);
        if (city == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_CITY_CODE_IS_NOT_EXIST + siteUseCode.toLowerCase(),
                    IntegrationConstant.ERROR_20020, null);
        }
    }

    public Date addHourForSummerTime(Date birthDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDay);
        calendar.add(Calendar.HOUR, 1);
        birthDay = calendar.getTime();
        return birthDay;
    }
}
