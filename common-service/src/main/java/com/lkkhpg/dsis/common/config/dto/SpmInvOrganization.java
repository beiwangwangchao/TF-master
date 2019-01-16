/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 库存组织DTO.
 * 
 * @author frank.li
 */
@MultiLanguage
@Table(name = "SPM_INV_ORGANIZATION_B")
public class SpmInvOrganization extends BaseDTO {

    @Id
    @Column(name = "INV_ORG_ID")
    private Long invOrgId;

    @MultiLanguageField
    @Column(name = "name")
    private String name;

    private String code;

    @MultiLanguageField
    @Column(name = "DESCRIPTION")
    private String description;

    private Long locationId;

    private String defaultFlag;

    private Long operationUnitId;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private String operationUnitName;
    /**
     * 
     * 库存归集中心Id.
     */
    private Long costOrgId;
    /**
     * 库存归集中心name.
     */
    private String costOrgName;

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    public Long getOperationUnitId() {
        return operationUnitId;
    }

    public void setOperationUnitId(Long operationUnitId) {
        this.operationUnitId = operationUnitId;
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

    public String getOperationUnitName() {
        return operationUnitName;
    }

    public void setOperationUnitName(String operationUnitName) {
        this.operationUnitName = operationUnitName;
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