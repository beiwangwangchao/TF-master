/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 公司DTO.
 * 
 * @author frank.li
 */
@MultiLanguage
@Table(name = "SPM_COMPANY_B")
public class SpmCompany extends BaseDTO {
    private static final long serialVersionUID = 1L;
    
    private SpmLocation spmLocation;
    
    private String countryName;
    
    private String countryCode;
    
    private String stateName;
    
    private String stateCode;
    
    private String cityName;
    
    private String cityCode;
    
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;
    
    private String locationName;
    
    private String zipCode;
    
    @Id
    @Column(name = "company_id")
    private Long companyId;
    
    @NotEmpty
    private String code;
    
    @Column(name = "name")
    @MultiLanguageField
    @NotEmpty
    private String name;
    
    @Column(name = "short_name")
    @MultiLanguageField
    @Size(max = 30)
    private String shortName;

    @NotEmpty
    private String companyType;
    
    private String contacts;
    
    private String phone;
    
    private String fax;
    
    private String email;
    
    @Column(name = "remark")
    @MultiLanguageField
    @Size(max = 80)
    private String remark;
    
    private Long locationId;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;
    
    private String companyName;
    
    private String areaCode;
    
    private String brNo;
    private String publicSubparter;
    private String privateSubparter;
    private String parter;

    public String getPublicSubparter() {
        return publicSubparter;
    }

    public void setPublicSubparter(String publicSubparter) {
        this.publicSubparter = publicSubparter;
    }

    public String getPrivateSubparter() {
        return privateSubparter;
    }

    public void setPrivateSubparter(String privateSubparter) {
        this.privateSubparter = privateSubparter;
    }

    public String getParter() {
        return parter;
    }

    public void setParter(String parter) {
        this.parter = parter;
    }

    private String gstId;

    private String parentCompanyName;

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getBrNo() {
        return brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public SpmLocation getSpmLocation() {
        return spmLocation;
    }

    public void setSpmLocation(SpmLocation spmLocation) {
        this.spmLocation = spmLocation;
    }
    
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        this.enabledFlag = enabledFlag;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}