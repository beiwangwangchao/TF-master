/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.UpdateDistributorRequest;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IOnlineRegistrationService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IUpdateDistributorService;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmBankMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
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
 * 更新会员实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class UpdateDistributorServiceImpl implements IUpdateDistributorService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private SpmBankMapper spmBankMapper;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IOnlineRegistrationService onlineRegistrationService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Map<String, Object> updateDistributor(UpdateDistributorRequest updateDistributorRequest)
            throws DAppException, CommMemberException {
        IRequest request = RequestHelper.newEmptyRequest();
        RequestHelper.setCurrentRequest(request);

        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        try {
            SpmMarket market = spmMarketMapper.selectMarketByCode(updateDistributorRequest.getMarket());
            Long marketId = market.getMarketId();
            request.setAttribute(SystemProfileConstants.MARKET_ID, marketId);
            request.setAccountId(dAppUtilService.getDappAccountId());
            request.setLocale(BaseConstants.DEFAULT_LANG);
            String distributorNumber = updateDistributorRequest.getDistributorNumber();
            Member selectMember = new Member();
            selectMember.setMemberCode(distributorNumber);
            selectMember.setMarketId(marketId);
            List<Member> members = memberMapper.selectValidMembers(selectMember);
            if (members == null || members.size() <= 0) {
                throw new DAppException(IntegrationException.MSG_ERROR_MEMBER_CODE_NOT_EXIST,
                        IntegrationConstant.ERROR_20001, null);
            }
            Member member = members.get(0);
            member = commMemberService.getMember(request, member.getMemberId());
            self().setMemberInfo(updateDistributorRequest, member, market);
            self().updateMemberSpouseInfo(request, updateDistributorRequest, member);
            self().updateMemberBeneficiaryInfo(request, updateDistributorRequest, member);
            self().updateMemberAccountInfo(request, updateDistributorRequest, member);
            self().updateMemberHomeSiteInfo(request, updateDistributorRequest, member);
            self().updateMemberCtactSiteInfo(request, updateDistributorRequest, member);
            commMemberService.saveMember(request, member);
            resultMap.put("distributorNumber", updateDistributorRequest.getDistributorNumber());
            resultMap.put("result", 1L);
            resultMap.put("message", null);
            return resultMap;
        } catch (Exception e) {
            if (e instanceof CommMemberException) {
                throw e;
            }
            resultMap.put("distributorNumber", updateDistributorRequest.getDistributorNumber());
            resultMap.put("result", -1L);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }
    }

    public void setMemberInfo(UpdateDistributorRequest updateDistributorRequest, Member member, SpmMarket market)
            throws DAppException {
        if (updateDistributorRequest.getFirstNameChn() != null) {
            String firstNameChn = updateDistributorRequest.getFirstNameChn();
            member.setChineseFirstName(firstNameChn);
        }
        if (updateDistributorRequest.getLastNameChn() != null) {
            String lastNameChn = updateDistributorRequest.getLastNameChn();
            member.setChineseLastName(lastNameChn);
        }
        if (updateDistributorRequest.getFirstNameEng() != null) {
            String firstNameEng = updateDistributorRequest.getFirstNameEng();
            member.setEnglishFirstName(firstNameEng);
        }
        if (updateDistributorRequest.getLastNameEng() != null) {
            String lastNameEng = updateDistributorRequest.getLastNameEng();
            member.setEnglishLastName(lastNameEng);
        }
        if (updateDistributorRequest.getMemberRole() != null) {
            member.setMemberRole(updateDistributorRequest.getMemberRole());
        }
        if (updateDistributorRequest.getMemberType() != null) {
            member.setMemberType(updateDistributorRequest.getMemberType());
        }
        if (updateDistributorRequest.getIdType() != null) {
            member.setIdType(updateDistributorRequest.getIdType());
        }
        if (updateDistributorRequest.getIdNumber() != null) {
            member.setIdNumber(updateDistributorRequest.getIdNumber());
        }
        if (updateDistributorRequest.getBirthday() != null) {
            Date birthDay = dAppUtilService.dateStringToDate(updateDistributorRequest.getBirthday());
            birthDay = addHourForSummerTime(birthDay);
            member.setDob(birthDay);
        }
        if (updateDistributorRequest.getGender() != null) {
            member.setGender(updateDistributorRequest.getGender());
        }
        if (updateDistributorRequest.getCompanyName() != null) {
            member.setRefCompany(updateDistributorRequest.getCompanyName());
        }
        if (updateDistributorRequest.getCompanyIdNumber() != null) {
            member.setBrNumber(updateDistributorRequest.getCompanyIdNumber());
        }
        if (updateDistributorRequest.getLanguage() != null) {
            member.setLanguage(updateDistributorRequest.getLanguage());
        }
        if (updateDistributorRequest.getEducation() != null) {
            member.setEducation(updateDistributorRequest.getEducation());
        }
        if (updateDistributorRequest.getRace() != null) {
            member.setRace(updateDistributorRequest.getRace());
        }
        if (updateDistributorRequest.getNationality() != null) {
            member.setNationality(updateDistributorRequest.getNationality());
        }
        if (updateDistributorRequest.getPerCountry() != null) {
            member.setCountry(updateDistributorRequest.getCurCountry());
        }
        if (updateDistributorRequest.getMobileNumber() != null) {
            member.setPhoneNo(updateDistributorRequest.getMobileNumber());
        }
        if (updateDistributorRequest.getMobileAreaCode() != null) {
            member.setAreaCode(updateDistributorRequest.getMobileAreaCode());
        }
        if (updateDistributorRequest.getPhoneNumber() != null) {
            member.setOthPhoneNo(updateDistributorRequest.getPhoneNumber());
        }
        if (updateDistributorRequest.getPhoneNumberAreaCode() != null) {
            member.setOthAreaCode(updateDistributorRequest.getPhoneNumberAreaCode());
        }
        if (updateDistributorRequest.getEmailAddress() != null) {
            member.setEmail(updateDistributorRequest.getEmailAddress());
        }
        if (updateDistributorRequest.getPromotionOptIn() != null) {
            member.setAdOptIn(updateDistributorRequest.getPromotionOptIn());
        }
        if (updateDistributorRequest.getRegisterationChannel() != null) {
            member.setJointSiteType(updateDistributorRequest.getRegisterationChannel());
        }
        if (updateDistributorRequest.getTravelPlanMonth() != null) {
            member.setTravelPlanMonth(updateDistributorRequest.getTravelPlanMonth());
        }
        if (updateDistributorRequest.getBonusPaymentMethod() != null) {
            member.setBonusRcvMethod(updateDistributorRequest.getBonusPaymentMethod());
        }
        member.setDappSyncFlag(IntegrationConstant.YES);
    }

    @Override
    public void updateMemberSpouseInfo(IRequest request, UpdateDistributorRequest udr, Member member)
            throws DAppException {
        boolean addFlag = true;
        String spouseFirstNameChn = udr.getSpouseFirstNameChn();
        String spouseLastNameChn = udr.getSpouseLastNameChn();
        String spouseFirstNameEng = udr.getSpouseFirstNameEng();
        String spouseLastNameEng = udr.getSpouseLastNameEng();
        String spouseBirthday = udr.getSpouseBirthday();
        String spouseID = udr.getSpouseID();
        String spouseIDType = udr.getSpouseIDType();
        if (spouseFirstNameChn != null || spouseLastNameChn != null || spouseFirstNameEng != null
                || spouseLastNameEng != null || spouseBirthday != null || spouseID != null || spouseIDType != null) {
            List<MemRelationship> relationships = commMemberService.queryRelationships(request, member.getMemberId(),
                    Integer.valueOf(IntegrationConstant.PAGE_NO), Integer.valueOf(IntegrationConstant.MAX_PERPAGE));
            for (int i = 0; i < relationships.size(); i++) {
                MemRelationship memRelationship = relationships.get(i);
                if (IntegrationConstant.REL_TYPE_SPOUS.equals(memRelationship.getRelType())) {
                    if (StringUtils.isNotEmpty(spouseFirstNameChn) || StringUtils.isNotEmpty(spouseLastNameChn)) {
                        if (spouseFirstNameChn == null) {
                            spouseFirstNameChn = "";
                        }
                        if (spouseLastNameChn == null) {
                            spouseLastNameChn = "";
                        }
                        memRelationship.setChineseName(spouseFirstNameChn + spouseLastNameChn);
                    }
                    if (StringUtils.isNotEmpty(spouseFirstNameEng) || StringUtils.isNotEmpty(spouseLastNameEng)) {
                        if (spouseFirstNameEng == null) {
                            spouseFirstNameEng = "";
                        }
                        if (spouseLastNameEng == null) {
                            spouseLastNameEng = "";
                        }
                        memRelationship.setEnglishName(spouseFirstNameEng + " " + spouseLastNameEng);
                    }
                    if (spouseIDType != null) {
                        memRelationship.setIdType(spouseIDType);
                    }
                    if (spouseID != null) {
                        memRelationship.setIdNumber(spouseID);
                    }
                    if (spouseBirthday != null) {
                        Date spouseBirthDay = dAppUtilService.dateStringToDate(spouseBirthday);
                        spouseBirthDay = addHourForSummerTime(spouseBirthDay);
                        memRelationship.setDob(spouseBirthDay);
                    }
                    memRelationship.set__status(DTOStatus.UPDATE);
                    relationships.remove(i);
                    relationships.add(i, memRelationship);
                    member.setMemRelationships(relationships);
                    // 已找到配偶信息，不需要新增
                    addFlag = false;
                }
            }
            if (addFlag) {
                // 新增配偶信息
                // if (relationships == null) {
                // }
                MemRelationship spouseInfo = self().addMemberSpouseInfo(request, udr, member);
                relationships.add(spouseInfo);
                member.setMemRelationships(relationships);
            }
        }

    }

    @Override
    public void updateMemberBeneficiaryInfo(IRequest request, UpdateDistributorRequest udr, Member member)
            throws DAppException {
        boolean addFlag = true;
        String beneficiaryFirstNameChn = udr.getBeneficiaryFirstNameChn();
        String beneficiaryLastNameChn = udr.getBeneficiaryLastNameChn();
        String beneficiaryFirstNameEng = udr.getBeneficiaryFirstNameEng();
        String beneficiaryLastNameEng = udr.getBeneficiaryLastNameEng();
        String beneficiaryBirthday = udr.getBeneficiaryBirthday();
        String beneficiaryID = udr.getBeneficiaryID();
        String beneficiaryIDType = udr.getBeneficiaryIDType();
        String beneficiaryRelationship = udr.getBeneficiaryRelationship();
        if (beneficiaryFirstNameChn != null || beneficiaryLastNameChn != null || beneficiaryFirstNameEng != null
                || beneficiaryLastNameEng != null || beneficiaryBirthday != null || beneficiaryID != null
                || beneficiaryIDType != null || beneficiaryRelationship != null) {
            List<MemRelationship> relationships = commMemberService.queryRelationships(request, member.getMemberId(),
                    Integer.valueOf(IntegrationConstant.PAGE_NO), Integer.valueOf(IntegrationConstant.MAX_PERPAGE));
            for (int i = 0; i < relationships.size(); i++) {
                MemRelationship memRelationship = relationships.get(i);
                if (IntegrationConstant.REL_TYPE_BENF.equals(memRelationship.getRelType())) {
                    if (StringUtils.isNotEmpty(beneficiaryFirstNameChn)
                            || StringUtils.isNotEmpty(beneficiaryLastNameChn)) {
                        if (beneficiaryFirstNameChn == null) {
                            beneficiaryFirstNameChn = "";
                        }
                        if (beneficiaryLastNameChn == null) {
                            beneficiaryLastNameChn = "";
                        }
                        memRelationship.setChineseName(beneficiaryFirstNameChn + beneficiaryLastNameChn);
                    }
                    if (StringUtils.isNotEmpty(beneficiaryFirstNameEng)
                            || StringUtils.isNotEmpty(beneficiaryLastNameEng)) {
                        if (beneficiaryFirstNameEng == null) {
                            beneficiaryFirstNameEng = "";
                        }
                        if (beneficiaryLastNameEng == null) {
                            beneficiaryLastNameEng = "";
                        }
                        memRelationship.setEnglishName(beneficiaryFirstNameEng + " " + beneficiaryLastNameEng);
                    }
                    if (beneficiaryIDType != null) {
                        memRelationship.setIdType(beneficiaryIDType);
                    }
                    if (beneficiaryID != null) {
                        memRelationship.setIdNumber(beneficiaryID);
                    }
                    if (beneficiaryBirthday != null) {
                        Date beneficiaryBirthDay = dAppUtilService.dateStringToDate(beneficiaryBirthday);
                        beneficiaryBirthDay = addHourForSummerTime(beneficiaryBirthDay);
                        memRelationship.setDob(beneficiaryBirthDay);
                    }
                    if (beneficiaryRelationship != null) {
                        memRelationship.setRelDesc(beneficiaryRelationship);
                    }
                    memRelationship.set__status(DTOStatus.UPDATE);
                    relationships.remove(i);
                    relationships.add(i, memRelationship);
                    member.setMemRelationships(relationships);
                    // 已找到受益人信息，不需要新增
                    addFlag = false;
                }
            }
            if (addFlag) {
                // 新增受益人信息
                MemRelationship beneficiaryInfo = self().addMemberBeneficiaryInfo(request, udr, member);
                relationships.add(beneficiaryInfo);
                member.setMemRelationships(relationships);
            }
        }

    }

    @Override
    public MemRelationship addMemberSpouseInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException {
        if (updateDistributorRequest.getSpouseFirstNameEng() != null
                || updateDistributorRequest.getSpouseLastNameEng() != null
                || updateDistributorRequest.getSpouseFirstNameChn() != null
                || updateDistributorRequest.getSpouseLastNameChn() != null) {
            // 新增配偶信息
            MemRelationship memRelationship_spous = new MemRelationship();
            // memRelationship_spous.setMemberId(member.getMemberId());
            memRelationship_spous.set__status(DTOStatus.ADD);
            memRelationship_spous.setRelType(IntegrationConstant.REL_TYPE_SPOUS);

            String spouseFirstNameChn = updateDistributorRequest.getSpouseFirstNameChn();
            String spouseLastNameChn = updateDistributorRequest.getSpouseLastNameChn();
            if (spouseFirstNameChn == null) {
                spouseFirstNameChn = "";
            }
            if (spouseLastNameChn == null) {
                spouseLastNameChn = "";
            }
            String spouseNameChn = spouseFirstNameChn + spouseLastNameChn;

            String spouseFirstNameEng = updateDistributorRequest.getSpouseFirstNameEng();
            String spouseLastNameEng = updateDistributorRequest.getSpouseLastNameEng();
            if (spouseFirstNameEng == null) {
                spouseFirstNameEng = "";
            }
            if (spouseLastNameEng == null) {
                spouseLastNameEng = "";
            }
            String spouseNameEng = spouseFirstNameEng + " " + spouseLastNameEng;

            if (StringUtils.isBlank(spouseNameChn) && StringUtils.isBlank(spouseNameEng)) {
                throw new DAppException(IntegrationException.MSG_ERROR_SPOUSE_CHN_ENG_LEAST_ONE,
                        IntegrationConstant.ERROR_20002, null);
            }
            memRelationship_spous.setChineseName(spouseNameChn);
            memRelationship_spous.setEnglishName(spouseNameEng);
            memRelationship_spous.setIdType(updateDistributorRequest.getSpouseIDType());
            memRelationship_spous.setIdNumber(updateDistributorRequest.getSpouseID());
            if (member.getGender() == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_GENDER_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20012, null);
            }
            memRelationship_spous.setRelDesc(codeService.getCodeMeaningByValue(request,
                    IntegrationConstant.MEMBER_SPOUSE_REL_CODE, member.getGender()));
            if (updateDistributorRequest.getSpouseBirthday() != null) {
                Date spouseBirthDay = dAppUtilService.dateStringToDate(updateDistributorRequest.getSpouseBirthday());
                spouseBirthDay = addHourForSummerTime(spouseBirthDay);
                memRelationship_spous.setDob(spouseBirthDay);
            }
            return memRelationship_spous;
        } else {
            throw new DAppException(IntegrationException.MSG_ERROR_SPOUSE_CHN_ENG_LEAST_ONE,
                    IntegrationConstant.ERROR_20002, null);
        }
    }

    @Override
    public MemRelationship addMemberBeneficiaryInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException {
        if (updateDistributorRequest.getBeneficiaryBirthday() != null
                && updateDistributorRequest.getBeneficiaryIDType() != null
                && updateDistributorRequest.getBeneficiaryID() != null
                && updateDistributorRequest.getBeneficiaryRelationship() != null) {
            // 新增受益人信息
            MemRelationship memRelationship_benf = new MemRelationship();
            // memRelationship_benf.setMemberId(member.getMemberId());
            memRelationship_benf.set__status(DTOStatus.ADD);
            memRelationship_benf.setRelType(IntegrationConstant.REL_TYPE_BENF);

            String beneficiaryFirstNameChn = updateDistributorRequest.getBeneficiaryFirstNameChn();
            String beneficiaryLastNameChn = updateDistributorRequest.getBeneficiaryLastNameChn();
            if (beneficiaryFirstNameChn == null) {
                beneficiaryFirstNameChn = "";
            }
            if (beneficiaryLastNameChn == null) {
                beneficiaryLastNameChn = "";
            }
            String beneficiaryNameChn = beneficiaryFirstNameChn + beneficiaryLastNameChn;

            String beneficiaryFirstNameEng = updateDistributorRequest.getBeneficiaryFirstNameEng();
            String beneficiaryLastNameEng = updateDistributorRequest.getBeneficiaryLastNameEng();
            if (beneficiaryFirstNameEng == null) {
                beneficiaryFirstNameEng = "";
            }
            if (beneficiaryLastNameEng == null) {
                beneficiaryLastNameEng = "";
            }
            String beneficiaryNameEng = beneficiaryFirstNameEng + " " + beneficiaryLastNameEng;

            if (StringUtils.isBlank(beneficiaryNameChn) && StringUtils.isBlank(beneficiaryNameEng)) {
                throw new DAppException(IntegrationException.MSG_ERROR_BENEF_CHN_ENG_LEAST_ONE,
                        IntegrationConstant.ERROR_20002, null);
            }
            memRelationship_benf.setChineseName(beneficiaryNameChn);
            memRelationship_benf.setEnglishName(beneficiaryNameEng);
            memRelationship_benf.setIdType(updateDistributorRequest.getBeneficiaryIDType());
            memRelationship_benf.setIdNumber(updateDistributorRequest.getBeneficiaryID());
            memRelationship_benf.setRelDesc(updateDistributorRequest.getBeneficiaryRelationship());
            if (updateDistributorRequest.getBeneficiaryBirthday() != null) {
                Date beneficiaryBirthDay = dAppUtilService
                        .dateStringToDate(updateDistributorRequest.getBeneficiaryBirthday());
                beneficiaryBirthDay = addHourForSummerTime(beneficiaryBirthDay);
                memRelationship_benf.setDob(beneficiaryBirthDay);
            }
            return memRelationship_benf;
        } else {
            if (updateDistributorRequest.getBeneficiaryBirthday() == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_BIRTHDAY_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20008, null);
            }
            if (updateDistributorRequest.getBeneficiaryIDType() == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_ID_TYPE_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20009, null);
            }
            if (updateDistributorRequest.getBeneficiaryID() == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_ID_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20010, null);
            }
            if (updateDistributorRequest.getBeneficiaryRelationship() != null) {
                throw new DAppException(IntegrationException.MSG_ERROR_RELATIONSHIP_DESC_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20011, null);
            }
        }
        return null;
    }

    @Override
    public void updateMemberAccountInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException {
        String bankAccount = updateDistributorRequest.getBankAccount();
        String bankAccountIdNumber = updateDistributorRequest.getBankAccountIdNumber();
        String bankCode = updateDistributorRequest.getBankCode();
        String payeeName = updateDistributorRequest.getPayeeName();
        String branchCode = updateDistributorRequest.getBranchCode();
        String bankAccountRemark = updateDistributorRequest.getBankAccountRemark();
        if (bankAccount != null || bankAccountIdNumber != null || bankCode != null || payeeName != null
                || branchCode != null || bankAccountRemark != null) {
            List<MemAccount> accounts = member.getMemAccounts();
            if (accounts != null && accounts.size() > 0) {
                MemAccount memAccount = accounts.get(0);
                if (bankCode != null) {
                    SpmBank spmBank = new SpmBank();
                    spmBank.setBankNumber(bankCode);
                    spmBank.setMarketId(member.getMarketId());
                    List<SpmBank> spmBanks = spmBankMapper.queryBySpmBank(spmBank);
                    if (spmBanks == null || spmBanks.size() < 1) {
                        throw new DAppException(IntegrationException.MSG_ERROR_BANK_IS_NOT_EXIST,
                                IntegrationConstant.ERROR_20005, null);
                    }
                    memAccount.setBankId(spmBanks.get(0).getBankId());
                }
                if (branchCode != null) {
                    SpmBank spmBranchBank = new SpmBank();
                    spmBranchBank.setBankNumber(branchCode);
                    spmBranchBank.setMarketId(member.getMarketId());
                    List<SpmBank> spmBranchBanks = spmBankMapper.queryBySpmBank(spmBranchBank);
                    if (spmBranchBanks == null || spmBranchBanks.size() < 1) {
                        throw new DAppException(IntegrationException.MSG_ERROR_BRANCH_BANK_IS_NOT_EXIST,
                                IntegrationConstant.ERROR_20023, null);
                    }
                    memAccount.setBankBranchId(spmBranchBanks.get(0).getBankId());
                }
                if (bankAccountIdNumber != null) {
                    memAccount.setIdNumber(bankAccountIdNumber);
                }
                if (bankAccount != null) {
                    memAccount.setAccountNumber(bankAccount);
                }
                if (payeeName != null) {
                    memAccount.setAccountHolder(payeeName);
                }
                if(bankAccountRemark != null){
                    memAccount.setComments(bankAccountRemark);
                }
                memAccount.set__status(DTOStatus.UPDATE);
                accounts.remove(0);
                accounts.add(0, memAccount);
                member.setMemAccounts(accounts);
            } else {
                MemAccount memAccount = self().addMemberAccountInfo(request, updateDistributorRequest, member);
                accounts.add(memAccount);
                member.setMemAccounts(accounts);
            }
        }
    }

    @Override
    public MemAccount addMemberAccountInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException {
        String bankAccount = updateDistributorRequest.getBankAccount();
        String bankAccountIdNumber = updateDistributorRequest.getBankAccountIdNumber();
        String bankCode = updateDistributorRequest.getBankCode();
        String payeeName = updateDistributorRequest.getPayeeName();
        if (bankAccount != null && bankAccountIdNumber != null && bankCode != null && payeeName != null) {
            MemAccount memAccount = new MemAccount();
            SpmBank spmBank = new SpmBank();
            spmBank.setBankNumber(bankCode);
            spmBank.setMarketId(member.getMarketId());
            List<SpmBank> spmBanks = spmBankMapper.queryBySpmBank(spmBank);
            if (spmBanks == null || spmBanks.size() < 1) {
                throw new DAppException(IntegrationException.MSG_ERROR_BANK_IS_NOT_EXIST,
                        IntegrationConstant.ERROR_20005, null);
            }
            memAccount.setBankId(spmBanks.get(0).getBankId());
            if (updateDistributorRequest.getBranchCode() != null) {
                spmBank.setBankNumber(updateDistributorRequest.getBranchCode());
                List<SpmBank> spmBranchBanks = spmBankMapper.queryBySpmBank(spmBank);
                if (spmBranchBanks == null || spmBranchBanks.size() < 1) {
                    throw new DAppException(IntegrationException.MSG_ERROR_BRANCH_BANK_IS_NOT_EXIST,
                            IntegrationConstant.ERROR_20023, null);
                }
                memAccount.setBankBranchId(spmBranchBanks.get(0).getBankId());
            }
            memAccount.setIdNumber(bankAccountIdNumber);
            memAccount.setAccountHolder(payeeName);
            memAccount.setAccountNumber(bankAccount);
            memAccount.set__status(DTOStatus.ADD);
            return memAccount;
        } else {
            if (bankAccount == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_BANK_ACCOUNT_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20013, null);
            }
            if (bankAccountIdNumber == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_BANK_ACCOUNT_ID_NUMBER_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20014, null);
            }
            if (bankCode == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_BANK_CODE_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20015, null);
            }
            if (payeeName == null) {
                throw new DAppException(IntegrationException.MSG_ERROR_PAYEE_NAME_CAN_NOT_NULL,
                        IntegrationConstant.ERROR_20016, null);
            }
        }
        return null;
    }

    @Override
    public void updateMemberHomeSiteInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException {
        String perCountry = updateDistributorRequest.getPerCountry();
        String perCity = updateDistributorRequest.getPerCity();
        String perState = updateDistributorRequest.getPerState();
        String perAddress = updateDistributorRequest.getPerAddress();
        String perAddress2 = updateDistributorRequest.getPerAddress2();
        String perAddress3 = updateDistributorRequest.getPerAddress3();
        String perZipCode = updateDistributorRequest.getPerZipCode();
        if (perCountry != null || perCity != null || perState != null || perAddress != null || perAddress2 != null
                || perAddress3 != null || perZipCode != null) {
            SpmLocation homeSpmLocation = member.getHomeSpmLocation();
            if (homeSpmLocation != null) {
                if (perCountry != null) {
                    onlineRegistrationService.validateLocation(perCountry, homeSpmLocation.getStateCode(),
                            homeSpmLocation.getCityCode(), IntegrationConstant.SITE_HOME);
                    homeSpmLocation.setCountryCode(perCountry);
                }
                if (perState != null) {
                    onlineRegistrationService.validateLocation(homeSpmLocation.getCountryCode(), perState,
                            homeSpmLocation.getCityCode(), IntegrationConstant.SITE_HOME);
                    homeSpmLocation.setStateCode(perState);
                }
                if (perCity != null) {
                    onlineRegistrationService.validateLocation(homeSpmLocation.getCountryCode(),
                            homeSpmLocation.getStateCode(), perCity, IntegrationConstant.SITE_HOME);
                    homeSpmLocation.setCityCode(perCity);
                }
                if (perAddress != null) {
                    homeSpmLocation.setAddressLine1(perAddress);
                }
                if (perAddress2 != null) {
                    homeSpmLocation.setAddressLine2(perAddress2);
                }
                if (perAddress3 != null) {
                    homeSpmLocation.setAddressLine3(perAddress3);
                }
                if (perZipCode != null) {
                    homeSpmLocation.setZipCode(perZipCode);
                }
                member.setHomeSpmLocation(homeSpmLocation);
            }
        }
    }

    @Override
    public void updateMemberCtactSiteInfo(IRequest request, UpdateDistributorRequest updateDistributorRequest,
            Member member) throws DAppException {
        String curCountry = updateDistributorRequest.getCurCountry();
        String curCity = updateDistributorRequest.getCurCity();
        String curState = updateDistributorRequest.getCurState();
        String curAddress = updateDistributorRequest.getCurAddress();
        String curAddress2 = updateDistributorRequest.getCurAddress2();
        String curAddress3 = updateDistributorRequest.getCurAddress3();
        String curZipCode = updateDistributorRequest.getCurZipCode();
        if (curCountry != null || curCity != null || curState != null || curAddress != null || curAddress2 != null
                || curAddress3 != null || curZipCode != null) {
            SpmLocation contactSpmLocation = member.getContactSpmLocation();
            if (contactSpmLocation != null) {
                if (curCountry != null) {
                    onlineRegistrationService.validateLocation(curCountry, contactSpmLocation.getStateCode(),
                            contactSpmLocation.getCityCode(), IntegrationConstant.SITE_CTACT);
                    contactSpmLocation.setCountryCode(curCountry);
                }
                if (curState != null) {
                    onlineRegistrationService.validateLocation(contactSpmLocation.getCountryCode(), curState,
                            contactSpmLocation.getCityCode(), IntegrationConstant.SITE_CTACT);
                    contactSpmLocation.setStateCode(curState);
                }
                if (curCity != null) {
                    onlineRegistrationService.validateLocation(contactSpmLocation.getCountryCode(),
                            contactSpmLocation.getStateCode(), curCity, IntegrationConstant.SITE_CTACT);
                    contactSpmLocation.setCityCode(curCity);
                }
                if (curAddress != null) {
                    contactSpmLocation.setAddressLine1(curAddress);
                }
                if (curAddress2 != null) {
                    contactSpmLocation.setAddressLine2(curAddress2);
                }
                if (curAddress3 != null) {
                    contactSpmLocation.setAddressLine3(curAddress3);
                }
                if (curZipCode != null) {
                    contactSpmLocation.setZipCode(curZipCode);
                }
                member.setContactSpmLocation(contactSpmLocation);
            }

        }
    }

    // TODO:由于夏令时问题在日期创建时多增加一小时
    public Date addHourForSummerTime(Date beneficiaryBirthDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beneficiaryBirthDay);
        calendar.add(Calendar.HOUR, 1);
        beneficiaryBirthDay = calendar.getTime();
        return beneficiaryBirthDay;
    }
}
