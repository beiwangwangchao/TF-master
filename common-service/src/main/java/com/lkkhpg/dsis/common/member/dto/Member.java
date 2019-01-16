/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员DTO.
 *
 * @author frank.li
 */
@AuditEnabled
@Table(name = "MM_MEMBER")
public class Member extends BaseDTO {

    public static final String FIELD_MEMBER_ID = "memberId";

    public static final String FIELD_MEMBER_CODE = "memberCode";

    public static final String FIELD_MEMBER_NAME = "memberName";

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @NotNull
    private String memberType;

    @NotNull
    private Long marketId;

    @NotNull
    private Long companyId;
    //库存组织
    private String salesOrganization;

    private Long salesOrgId;

    private BigDecimal salesPiont;

    private BigDecimal exchangeBalance;

    private BigDecimal remainingBalance;

    private BigDecimal currentPv;

    // 会员编码（会员ID）
    @NotNull
    private String memberCode;

    @NotNull
    private String memberRole;

    @NotNull
    private String status;

    private String remark;

    private String refCompany;

    private String brNumber;

    private String englishFirstName;

    private String englishLastName;

    private String chineseFirstName;

    private String chineseLastName;

    private String englishName;

    private String chineseName;

    /**
     * 中英拼接姓名.
     */
    private String memberName;

    @NotNull
    private String gender;


    private Date dob;


    private String idType;


    private String idNumber;

    private String areaCode;

    private String areaPhone;

    private String phoneNo;

    private String othAreaCode;

    private String othPhoneNo;

    private String email;

    private String nationality;

    @MultiLanguageField
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String country;

    private String language;

    private String race;

    private String education;

    private String citizenType;

    private String nhiTaxExcluded;

    private String gstIdNumber;

    @NotNull
    private String adOptIn;

    @NotNull
    private String sysMsgIn;

    private String warningMsg;

    @NotNull
    private String jointSiteType;

    @NotNull
    private String jointSite;

    private String jointSiteCode;

    private String jointSiteName;

    @NotNull
    private Date jointDate;

    private Date approvalDate;

    private Long sponsorId;

    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @NotNull
    private String acceptBonus;

    private String travelPlanMonth;

    private String syncFlag;

    private String signature;

    private String statusDesc;

    private Long homeLocationId;

    private Long contactLocationId;

    private String registerCode;

    private String registNumber;

    private String homeLocation;

    private Long discount;

    private SpmLocation homeSpmLocation;

    private String contactLocation;

    private SpmLocation contactSpmLocation;

    private BigDecimal discountAmt;

    private BigDecimal discountAmtFrom;

    private BigDecimal discountAmtTo;

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getDiscountAmtFrom() {
        return discountAmtFrom;
    }

    public void setDiscountAmtFrom(BigDecimal discountAmtFrom) {
        this.discountAmtFrom = discountAmtFrom;
    }

    public BigDecimal getDiscountAmtTo() {
        return discountAmtTo;
    }

    public void setDiscountAmtTo(BigDecimal discountAmtTo) {
        this.discountAmtTo = discountAmtTo;
    }

    @Children
    private List<MemRelationship> memRelationships;

    @Children
    private List<MemAttribute> memAttributes;

    @Children
    private List<MemAccount> memAccounts;

    @Children
    private List<MemSite> memSites;

    @Children
    private List<MemCard> memCards;

    @Children
    private List<MemAccountingBalance> memAccountingBalances;

    @Children
    private List<MemAccountingTrx> memAccountingTrxs;

    @Children
    private List<MemRank> memRanks;

    @Children
    private List<MemWithdraw> memWithdraws;

    @Children
    private List<Member> memDownLines;

    private String marketCode;

    private String marketName;

    private String companyCode;

    private String companyName;

    private String sponsorMemberCode;

    /**
     * 中英拼接推荐人姓名.
     */
    private String sponsorName;

    private String sponsorChineseName;

    private String sponsorEnglishName;

    private String sponsorMarketCode;

    private Integer rank;

    private String nationalityName;

    private String countryName;

    private String isActive;

    private Integer travelCount;

    private Long sourceMemberId;

    private Long signatureFileId;

    // 终止期限
    private String deadline;

    // TP
    private BigDecimal tp;

    private String firstLoginFlag;

    private boolean isSponsorVerify;

    private String sponsorNo;

    private String loginName;

    private Long accountId;

    private Integer level;

    private String accountType;

    private String dappSyncFlag;

    private String dappAppNo;

    private String rankDesc;

    private String sponsorRankDesc;
    /**
     * 存在变更所有权预存会员.
     */
    private String hasChangeOwnershipTemp;

    /**
     * 是否变更所有权操作.
     */
    private boolean isChangeOwnership;

    /**
     * 奖金领取方式.
     */
    @NotNull
    private String bonusRcvMethod;

    /**
     * 截止日期.
     */
    private Date terminationDate;

    private String tempPassword;

    private Long igiMarketId;

    private Long gstLocationId;

    private SpmLocation gstSpmLocation;

    private String gstLocation;

    private String isSameSpouseName;

    private String refCompanyEn;

    private String orderType;

    private String isLeaf;

    private Date jointDateFrom;

    private Date jointDateTo;

    private String lastOrderDate;


    //updated by 11816 at 2018/01/11 11:25
    private String freezeDiscount;

    private String availableDiscount;

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFreezeDiscount() {
        return freezeDiscount;
    }

    public void setFreezeDiscount(String freezeDiscount) {
        this.freezeDiscount = freezeDiscount;
    }

    public String getAvailableDiscount() {
        return availableDiscount;
    }

    public void setAvailableDiscount(String availableDiscount) {
        this.availableDiscount = availableDiscount;
    }

    /**
     * 组合国家代码和电话号码.
     *
     * @return 区号+号码
     */
    public String getAreaPhone() {
        if (areaCode == null) {
            return phoneNo;
        } else {
            return areaCode + phoneNo;
        }
    }

    public String getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(String salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public void setAreaPhone(String areaPhone) {
        this.areaPhone = areaPhone;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<MemRelationship> getMemRelationships() {
        return memRelationships;
    }

    public void setMemRelationships(List<MemRelationship> memRelationships) {
        this.memRelationships = memRelationships;
    }

    public List<MemAttribute> getMemAttributes() {
        return memAttributes;
    }

    public void setMemAttributes(List<MemAttribute> memAttributes) {
        this.memAttributes = memAttributes;
    }

    public List<MemAccount> getMemAccounts() {
        return memAccounts;
    }

    public void setMemAccounts(List<MemAccount> memAccounts) {
        this.memAccounts = memAccounts;
    }

    public List<MemSite> getMemSites() {
        return memSites;
    }

    public void setMemSites(List<MemSite> memSites) {
        this.memSites = memSites;
    }

    public List<MemCard> getMemCards() {
        return memCards;
    }

    public void setMemCards(List<MemCard> memCards) {
        this.memCards = memCards;
    }

    public List<MemRank> getMemRanks() {
        return memRanks;
    }

    public void setMemRanks(List<MemRank> memRanks) {
        this.memRanks = memRanks;
    }

    public List<MemWithdraw> getMemWithdraws() {
        return memWithdraws;
    }

    public void setMemWithdraws(List<MemWithdraw> memWithdraws) {
        this.memWithdraws = memWithdraws;
    }

    public List<Member> getMemDownLines() {
        return memDownLines;
    }

    public void setMemDownLines(List<Member> memDownLines) {
        this.memDownLines = memDownLines;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole == null ? null : memberRole.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remarks) {
        this.remark = remarks == null ? null : remarks.trim();
    }

    public String getBrNumber() {
        return brNumber;
    }

    public String getRefCompany() {
        return refCompany;
    }

    public void setRefCompany(String refCompany) {
        this.refCompany = refCompany;
    }

    public void setBrNumber(String brNumber) {
        this.brNumber = brNumber == null ? null : brNumber.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getOthAreaCode() {
        return othAreaCode;
    }

    public void setOthAreaCode(String othAreaCode) {
        this.othAreaCode = othAreaCode == null ? null : othAreaCode.trim();
    }

    public String getOthPhoneNo() {
        return othPhoneNo;
    }

    public void setOthPhoneNo(String othPhoneNo) {
        this.othPhoneNo = othPhoneNo == null ? null : othPhoneNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Long getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(Long homeLocationId) {
        this.homeLocationId = homeLocationId;
    }

    public Long getContactLocationId() {
        return contactLocationId;
    }

    public void setContactLocationId(Long contactLocationId) {
        this.contactLocationId = contactLocationId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race == null ? null : race.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getCitizenType() {
        return citizenType;
    }

    public void setCitizenType(String citizenType) {
        this.citizenType = citizenType == null ? null : citizenType.trim();
    }

    public String getNhiTaxExcluded() {
        return nhiTaxExcluded;
    }

    public void setNhiTaxExcluded(String nhiTaxExcluded) {
        this.nhiTaxExcluded = nhiTaxExcluded == null ? null : nhiTaxExcluded.trim();
    }

    public String getGstIdNumber() {
        return gstIdNumber;
    }

    public void setGstIdNumber(String gstIdNumber) {
        this.gstIdNumber = gstIdNumber == null ? null : gstIdNumber.trim();
    }

    public String getAdOptIn() {
        return adOptIn;
    }

    public void setAdOptIn(String adOptIn) {
        this.adOptIn = adOptIn == null ? null : adOptIn.trim();
    }

    public String getSysMsgIn() {
        return sysMsgIn;
    }

    public void setSysMsgIn(String sysMsgIn) {
        this.sysMsgIn = sysMsgIn == null ? null : sysMsgIn.trim();
    }

    public String getJointSite() {
        return jointSite;
    }

    public void setJointSite(String jointSite) {
        this.jointSite = jointSite == null ? null : jointSite.trim();
    }

    public Date getJointDate() {
        return jointDate;
    }

    public void setJointDate(Date jointDate) {
        this.jointDate = jointDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public List<MemAccountingBalance> getMemAccountingBalances() {
        return memAccountingBalances;
    }

    public void setMemAccountingBalances(List<MemAccountingBalance> memAccountingBalances) {
        this.memAccountingBalances = memAccountingBalances;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getAcceptBonus() {
        return acceptBonus;
    }

    public void setAcceptBonus(String acceptBonus) {
        this.acceptBonus = acceptBonus;
    }

    public String getTravelPlanMonth() {
        return travelPlanMonth;
    }

    public void setTravelPlanMonth(String travelPlanMonth) {
        this.travelPlanMonth = travelPlanMonth;
    }

    public Integer getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(Integer travelCount) {
        this.travelCount = travelCount;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getContactLocation() {
        return contactLocation;
    }

    public void setContactLocation(String contactLocation) {
        this.contactLocation = contactLocation;
    }

    public String getSponsorChineseName() {
        return sponsorChineseName;
    }

    public void setSponsorChineseName(String sponsorChineseName) {
        this.sponsorChineseName = sponsorChineseName;
    }

    public String getSponsorEnglishName() {
        return sponsorEnglishName;
    }

    public void setSponsorEnglishName(String sponsorEnglishName) {
        this.sponsorEnglishName = sponsorEnglishName;
    }

    public String getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber == null ? null : registNumber.trim();
    }

    public List<MemAccountingTrx> getMemAccountingTrxs() {
        return memAccountingTrxs;
    }

    public void setMemAccountingTrxs(List<MemAccountingTrx> memAccountingTrxs) {
        this.memAccountingTrxs = memAccountingTrxs;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public BigDecimal getSalesPiont() {
        return salesPiont;
    }

    public void setSalesPiont(BigDecimal salesPiont) {
        this.salesPiont = salesPiont;
    }

    public BigDecimal getExchangeBalance() {
        return exchangeBalance;
    }

    public void setExchangeBalance(BigDecimal exchangeBalance) {
        this.exchangeBalance = exchangeBalance;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public BigDecimal getCurrentPv() {
        return currentPv;
    }

    public void setCurrentPv(BigDecimal currentPv) {
        this.currentPv = currentPv;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public Long getSourceMemberId() {
        return sourceMemberId;
    }

    public void setSourceMemberId(Long sourceMemberId) {
        this.sourceMemberId = sourceMemberId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public BigDecimal getTp() {
        return tp;
    }

    public void setTp(BigDecimal tp) {
        this.tp = tp;
    }

    public SpmLocation getHomeSpmLocation() {
        return homeSpmLocation;
    }

    public void setHomeSpmLocation(SpmLocation homeSpmLocation) {
        this.homeSpmLocation = homeSpmLocation;
    }

    public SpmLocation getContactSpmLocation() {
        return contactSpmLocation;
    }

    public void setContactSpmLocation(SpmLocation contactSpmLocation) {
        this.contactSpmLocation = contactSpmLocation;
    }

    public String getSponsorMemberCode() {
        return sponsorMemberCode;
    }

    public void setSponsorMemberCode(String sponsorMemberCode) {
        this.sponsorMemberCode = sponsorMemberCode;
    }

    public Long getSignatureFileId() {
        return signatureFileId;
    }

    public void setSignatureFileId(Long signatureFileId) {
        this.signatureFileId = signatureFileId;
    }

    public String getFirstLoginFlag() {
        return firstLoginFlag;
    }

    public void setFirstLoginFlag(String firstLoginFlag) {
        this.firstLoginFlag = firstLoginFlag;
    }

    public boolean getIsSponsorVerify() {
        return isSponsorVerify;
    }

    public void setIsSponsorVerify(boolean isSponsorVerify) {
        this.isSponsorVerify = isSponsorVerify;
    }

    public String getSponsorNo() {
        return sponsorNo;
    }

    public void setSponsorNo(String sponsorNo) {
        this.sponsorNo = sponsorNo;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getEnglishFirstName() {
        return englishFirstName;
    }

    public void setEnglishFirstName(String englishFirstName) {
        this.englishFirstName = englishFirstName;
    }

    public String getEnglishLastName() {
        return englishLastName;
    }

    public void setEnglishLastName(String englishLastName) {
        this.englishLastName = englishLastName;
    }

    public String getChineseFirstName() {
        return chineseFirstName;
    }

    public void setChineseFirstName(String chineseFirstName) {
        this.chineseFirstName = chineseFirstName;
    }

    public String getChineseLastName() {
        return chineseLastName;
    }

    public void setChineseLastName(String chineseLastName) {
        this.chineseLastName = chineseLastName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getJointSiteCode() {
        return jointSiteCode;
    }

    public void setJointSiteCode(String jointSiteCode) {
        this.jointSiteCode = jointSiteCode;
    }

    public String getJointSiteType() {
        return jointSiteType;
    }

    public void setJointSiteType(String jointSiteType) {
        this.jointSiteType = jointSiteType;
    }

    public String getDappSyncFlag() {
        return dappSyncFlag;
    }

    public void setDappSyncFlag(String dappSyncFlag) {
        this.dappSyncFlag = dappSyncFlag;
    }

    public String getDappAppNo() {
        return dappAppNo;
    }

    public void setDappAppNo(String dappAppNo) {
        this.dappAppNo = dappAppNo;
    }

    public String getBonusRcvMethod() {
        return bonusRcvMethod;
    }

    public void setBonusRcvMethod(String bonusRcvMethod) {
        this.bonusRcvMethod = bonusRcvMethod;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public boolean getIsChangeOwnership() {
        return isChangeOwnership;
    }

    public void setIsChangeOwnership(boolean isChangeOwnership) {
        this.isChangeOwnership = isChangeOwnership;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public String getHasChangeOwnershipTemp() {
        return hasChangeOwnershipTemp;
    }

    public void setHasChangeOwnershipTemp(String hasChangeOwnershipTemp) {
        this.hasChangeOwnershipTemp = hasChangeOwnershipTemp;
    }

    public Long getIgiMarketId() {
        return igiMarketId;
    }

    public void setIgiMarketId(Long igiMarketId) {
        this.igiMarketId = igiMarketId;
    }

    public void setSponsorVerify(boolean isSponsorVerify) {
        this.isSponsorVerify = isSponsorVerify;
    }

    public void setChangeOwnership(boolean isChangeOwnership) {
        this.isChangeOwnership = isChangeOwnership;
    }

    public String getJointSiteName() {
        return jointSiteName;
    }

    public void setJointSiteName(String jointSiteName) {
        this.jointSiteName = jointSiteName;
    }

    public Long getGstLocationId() {
        return gstLocationId;
    }

    public void setGstLocationId(Long gstLocationId) {
        this.gstLocationId = gstLocationId;
    }

    public SpmLocation getGstSpmLocation() {
        return gstSpmLocation;
    }

    public void setGstSpmLocation(SpmLocation gstSpmLocation) {
        this.gstSpmLocation = gstSpmLocation;
    }

    public String getGstLocation() {
        return gstLocation;
    }

    public void setGstLocation(String gstLocation) {
        this.gstLocation = gstLocation;
    }

    public String getIsSameSpouseName() {
        return isSameSpouseName;
    }

    public void setIsSameSpouseName(String isSameSpouseName) {
        this.isSameSpouseName = isSameSpouseName;
    }

    public String getRefCompanyEn() {
        return refCompanyEn;
    }

    public void setRefCompanyEn(String refCompanyEn) {
        this.refCompanyEn = refCompanyEn == null ? null : refCompanyEn.trim();
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Date getJointDateFrom() {
        return jointDateFrom;
    }

    public void setJointDateFrom(Date jointDateFrom) {
        this.jointDateFrom = jointDateFrom;
    }

    public Date getJointDateTo() {
        return jointDateTo;
    }

    public void setJointDateTo(Date jointDateTo) {
        this.jointDateTo = jointDateTo;
    }

    public String getSponsorMarketCode() {
        return sponsorMarketCode;
    }

    public void setSponsorMarketCode(String sponsorMarketCode) {
        this.sponsorMarketCode = sponsorMarketCode;
    }

    public String getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(String lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public String getSponsorRankDesc() {
        return sponsorRankDesc;
    }

    public void setSponsorRankDesc(String sponsorRankDesc) {
        this.sponsorRankDesc = sponsorRankDesc;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }
}

