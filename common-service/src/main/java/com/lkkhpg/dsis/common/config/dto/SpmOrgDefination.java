/*
 *
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织定义DTO.
 * 
 * @author wangc
 */
@MultiLanguage
@Table(name = "spm_org_defination_b")
public class SpmOrgDefination extends BaseDTO {

    private SpmLocation spmLocation;

    private String locationName;

    @Id
    @Column(name = "defination_id")
    private Long definationId;

    @NotEmpty
    private String code;

    @NotEmpty
    @MultiLanguageField
    @Column(name = "name")
    private String name;

    private String description;

    @NotEmpty
    private String orgType;

    private Long locationId;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private Long salesOrgId;

    private Long invOrgId;

    @NotEmpty
    private String salesOrgFlag;

    @NotEmpty
    private String invOrgFlag;
    /**
     * 库存归集中心Id.
     */
    private Long costOrgId;
    /**
     * 库存归集中心name.
     */
    private String costOrgName;
    /**
     * 公司ID.
     */
    private Long companyId;
    /**
     * 公司名称.
     */
    private String companyName;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public SpmLocation getSpmLocation() {
        return spmLocation;
    }

    public void setSpmLocation(SpmLocation spmLocation) {
        this.spmLocation = spmLocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getDefinationId() {
        return definationId;
    }

    public void setDefinationId(Long definationId) {
        this.definationId = definationId;
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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
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

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public String getSalesOrgFlag() {
        return salesOrgFlag;
    }

    public void setSalesOrgFlag(String salesOrgFlag) {
        this.salesOrgFlag = salesOrgFlag == null ? null : salesOrgFlag.trim();
    }

    public String getInvOrgFlag() {
        return invOrgFlag;
    }

    public void setInvOrgFlag(String invOrgFlag) {
        this.invOrgFlag = invOrgFlag == null ? null : invOrgFlag.trim();
    }

    public Long getCostOrgId() {
        return costOrgId;
    }

    public void setCostOrgId(Long costOrgId) {
        this.costOrgId = costOrgId;
    }

    public String getCostOrgName() {
        return costOrgName;
    }

    public void setCostOrgName(String costOrgName) {
        this.costOrgName = costOrgName;
    }

}