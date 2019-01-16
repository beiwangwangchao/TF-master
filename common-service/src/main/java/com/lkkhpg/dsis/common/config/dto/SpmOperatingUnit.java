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
import com.sun.istack.NotNull;
/**
 * 业务实体DTO.
 * 
 * @author hanrui.huang
 */
@MultiLanguage
@Table(name = "SPM_OPERATING_UNIT_B")
public class SpmOperatingUnit extends BaseDTO {
    
    private SpmLocation spmLocation;
    
    @Id
    @Column(name = "OPERATING_UNIT_ID")
    private Long operatingUnitId;

    @NotEmpty
    private String code;

    @MultiLanguageField
    @Column(name = "name")
    @NotEmpty
    private String name;

    @MultiLanguageField
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    private Long companyId;

    private Long locationId;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;
    
    @NotEmpty
    private String orgType;
    
    private String companyName;
    
    private String locationName;

    public Long getOperatingUnitId() {
        return operatingUnitId;
    }

    public void setOperatingUnitId(Long operatingUnitId) {
        this.operatingUnitId = operatingUnitId;
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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
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

}