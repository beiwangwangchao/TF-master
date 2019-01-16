/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 市场DTO.
 * 
 * @author frank.li
 */
@AuditEnabled
@MultiLanguage
@Table(name = "SPM_MARKET_B")
public class SpmMarket extends BaseDTO {

    private SpmLocation spmLocation;

    @Id
    @Column(name = "market_id")
    private Long marketId;

    private String code;

    @MultiLanguageField
    @Column(name = "name")
    private String name;

    @MultiLanguageField
    @Column(name = "description")
    private String description;

    private Long companyId;

    private Long locationId;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private String orgType;

    private String locationName;

    private String companyName;

    private Long igiMarketId;

    private String memberCode;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    //    private String publicSubparter;
//    private String privateSubparter;
//    private String parter;
//
    @Children
    private List<SpmCompany>spmCompanies;

    public List<SpmCompany> getSpmCompanies() {
        return spmCompanies;
    }

    public void setSpmCompanies(List<SpmCompany> spmCompanies) {
        this.spmCompanies = spmCompanies;
    }

//    public String getPublicSubparter() {
//        return publicSubparter;
//    }
//
//    public void setPublicSubparter(String publicSubparter) {
//        this.publicSubparter = publicSubparter;
//    }
//
//    public String getPrivateSubparter() {
//        return privateSubparter;
//    }

//    public void setPrivateSubparter(String privateSubparter) {
//        this.privateSubparter = privateSubparter;
//    }
//
//    public String getParter() {
//        return parter;
//    }
//
//    public void setParter(String parter) {
//        this.parter = parter;
//    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public SpmLocation getSpmLocation() {
        return spmLocation;
    }

    public void setSpmLocation(SpmLocation spmLocation) {
        this.spmLocation = spmLocation;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public Long getIgiMarketId() {
        return igiMarketId;
    }

    public void setIgiMarketId(Long igiMarketId) {
        this.igiMarketId = igiMarketId;
    }

}