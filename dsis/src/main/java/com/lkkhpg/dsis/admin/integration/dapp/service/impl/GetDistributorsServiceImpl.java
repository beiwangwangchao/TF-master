/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.DAppMember;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorResponse;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorsRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.GetDistributorsResponse;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDAppUtilService;
import com.lkkhpg.dsis.admin.integration.dapp.service.IGetDistributorsService;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmBankMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 获取业务员列表实现类.
 * 
 * @author linyuheng
 */
@Service
public class GetDistributorsServiceImpl implements IGetDistributorsService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemSiteMapper memSiteMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    @Autowired
    private MemAccountMapper memAccountMapper;

    @Autowired
    private SpmBankMapper spmBankMapper;

    @Autowired
    private IDAppUtilService dAppUtilService;

    @Override
    public GetDistributorsResponse getDistributors(GetDistributorsRequest getDistributorsRequest, int pageNo,
            int maxPerpage) throws DAppException {
        String market = getDistributorsRequest.getMarket();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("marketCode", market);
        Date startDate = null;
        Date endDate = null;
        Date updateStartDate = null;
        Date updateEndDate = null;
        if (getDistributorsRequest.getStartDate() != null) {
            startDate = dAppUtilService.dateStringToDate(getDistributorsRequest.getStartDate());
        }
        if (getDistributorsRequest.getEndDate() != null) {
            endDate = dAppUtilService.dateStringToDate(getDistributorsRequest.getEndDate());
        }
        if (getDistributorsRequest.getUpdateStartDate() != null) {
            updateStartDate = dAppUtilService.dateStringToDate(getDistributorsRequest.getUpdateStartDate());
        }
        if (getDistributorsRequest.getUpdateEndDate() != null) {
            updateEndDate = dAppUtilService.dateStringToDate(getDistributorsRequest.getUpdateEndDate());
        }
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("updateStartDate", updateStartDate);
        map.put("updateEndDate", updateEndDate);
        PageHelper.startPage(pageNo, maxPerpage);
        List<Member> selectMembers = memberMapper.selectMembersByDate(map);
        GetDistributorsResponse getDistributors = new GetDistributorsResponse();
        List<DAppMember> membersList = new ArrayList<DAppMember>();
        for (Member member : selectMembers) {
            DAppMember dAppMember = new DAppMember();
            dAppMember.setStatus(member.getStatus());
            dAppMember.setMemberRole(member.getMemberRole());
            dAppMember.setMemberType(member.getMemberType());
            dAppMember.setMarket(market);
            dAppMember.setSponsorId(member.getSponsorNo());
            dAppMember.setFirstNameEng(member.getEnglishFirstName());
            dAppMember.setLastNameEng(member.getEnglishLastName());
            dAppMember.setFirstNameChn(member.getChineseFirstName());
            dAppMember.setLastNameChn(member.getChineseLastName());
            dAppMember.setDistributorNumber(member.getMemberCode());
            dAppMember.setGender(member.getGender());
            dAppMember.setLanguage(member.getLanguage());
            dAppMember.setMobileAreaCode(member.getAreaCode());
            dAppMember.setMobileNumber(member.getPhoneNo());
            dAppMember.setEmailAddress(member.getEmail());
            dAppMember.setPromotionOptIn(member.getAdOptIn());
            dAppMember.setRegisterationChannel(member.getJointSiteType());
            membersList.add(dAppMember);
        }
        getDistributors.setMembers(membersList);
        return getDistributors;
    }

    @Override
    public GetDistributorResponse getDistributor(GetDistributorRequest getDistributorRequest) throws DAppException {
        String distributorNumber = getDistributorRequest.getDistributorNumber();
        String idNumber = getDistributorRequest.getIdNumber();
        GetDistributorResponse getDistributorResponse = new GetDistributorResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(distributorNumber) && StringUtils.isEmpty(idNumber)) {
            throw new DAppException(IntegrationException.MSG_ERROR_DISNUM_OR_IDNUM_AT_LEAST_ONE,
                    IntegrationConstant.ERROR_20003, null);
        }
        map.put("distributorNumber", distributorNumber);
        map.put("idNumber", idNumber);
        Member member = memberMapper.getDistributorByDrbNumAndIdNum(map);
        if (member == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_MEMBER_CODE_NOT_EXIST,
                    IntegrationConstant.ERROR_20001, null);
        }
        getDistributorResponse.setDistributorNumber(member.getMemberCode());
        getDistributorResponse.setStatus(member.getStatus());
        getDistributorResponse.setMarket(member.getMarketCode());
        getDistributorResponse.setSponsorId(member.getSponsorNo());
        getDistributorResponse.setFirstNameEng(member.getEnglishFirstName());
        getDistributorResponse.setLastNameEng(member.getEnglishLastName());
        getDistributorResponse.setFirstNameChn(member.getChineseFirstName());
        getDistributorResponse.setLastNameChn(member.getChineseLastName());
        getDistributorResponse.setMemberRole(member.getMemberRole());
        getDistributorResponse.setMemberType(member.getMemberType());
        getDistributorResponse.setIdType(member.getIdType());
        getDistributorResponse.setIdNumber(member.getIdNumber());
        Date dob = member.getDob();
        String birthday = null;
        if (dob != null) {
            birthday = dAppUtilService.toDateString(dob);
        }
        getDistributorResponse.setBirthday(birthday);
        getDistributorResponse.setGender(member.getGender());
        getDistributorResponse.setCompanyName(member.getRefCompany());
        getDistributorResponse.setCompanyIdNumber(member.getBrNumber());
        getDistributorResponse.setLanguage(member.getLanguage());
        getDistributorResponse.setNationality(member.getNationality());

        List<MemSite> memSites = memSiteMapper.selectHomeCtactLocByMemberId(member.getMemberId());
        for (MemSite memSite : memSites) {
            if (IntegrationConstant.SITE_HOME.equals(memSite.getSiteUseCode())) {
                SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(memSite.getLocationId());
                getDistributorResponse.setPerState(spmLocation.getStateCode());
                getDistributorResponse.setPerCity(spmLocation.getCityCode());
                getDistributorResponse.setPerCountry(spmLocation.getCountryCode());
                getDistributorResponse.setPerAddress(spmLocation.getAddressLine1());
                getDistributorResponse.setPerAddress2(spmLocation.getAddressLine2());
                getDistributorResponse.setPerAddress3(spmLocation.getAddressLine3());
                getDistributorResponse.setPerZipCode(spmLocation.getZipCode());
            } else {
                SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(memSite.getLocationId());
                getDistributorResponse.setCurState(spmLocation.getStateCode());
                getDistributorResponse.setCurCity(spmLocation.getCityCode());
                getDistributorResponse.setCurCountry(spmLocation.getCountryCode());
                getDistributorResponse.setCurAddress(spmLocation.getAddressLine1());
                getDistributorResponse.setCurAddress2(spmLocation.getAddressLine2());
                getDistributorResponse.setCurAddress3(spmLocation.getAddressLine3());
                getDistributorResponse.setCurZipCode(spmLocation.getZipCode());
            }
        }
        getDistributorResponse.setPhoneNumberAreaCode(member.getOthAreaCode());
        getDistributorResponse.setPhoneNumber(member.getOthPhoneNo());

        getDistributorResponse.setMobileAreaCode(member.getAreaCode());
        getDistributorResponse.setMobileNumber(member.getPhoneNo());

        getDistributorResponse.setEmailAddress(member.getEmail());
        getDistributorResponse.setBonusPaymentMethod(member.getBonusRcvMethod());
        getDistributorResponse.setPromotionOptIn(member.getAdOptIn());
        getDistributorResponse.setRegisterationChannel(member.getJointSiteType());

        List<MemAccount> memAccounts = memAccountMapper.selectByMemberId(member.getMemberId());
        if (memAccounts != null && memAccounts.size() > 0) {
            MemAccount memAccount = memAccounts.get(0);
            SpmBank spmBank = spmBankMapper.selectByPrimaryKey(memAccount.getBankId());
            if (spmBank != null) {
                getDistributorResponse.setBankCode(spmBank.getBankNumber());
            }
            SpmBank spmBranchBank = spmBankMapper.selectByPrimaryKey(memAccount.getBankBranchId());
            if (spmBranchBank != null) {
                getDistributorResponse.setBranchCode(spmBranchBank.getBankNumber());
            }
            getDistributorResponse.setPayeeName(memAccount.getAccountHolder());
            getDistributorResponse.setBankAccount(memAccount.getAccountNumber());
            getDistributorResponse.setBankAccountIdNumber(memAccount.getIdNumber());
            getDistributorResponse.setBankAccountRemark(memAccount.getComments());
        }
        return getDistributorResponse;
    }

}
