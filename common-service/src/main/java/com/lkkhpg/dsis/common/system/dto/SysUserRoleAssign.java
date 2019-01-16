/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 用户角色分配组织DTO.
 * 
 * @author chenjingxiong
 */
public class SysUserRoleAssign extends BaseDTO {

    public static final String SALES_ORG_ASSIGN_TYPE = "SALES";

    public static final String INV_ORG_ASSIGN_TYPE = "INV";

    private Long userRoleAssignId;

    private Long userId;

    private Long roleId;

    private String assignType;

    private Long assignValue;

    private String defaultFlag;

    private Long objectVersionNumber;

    private Long requestId;

    private Long programId;

    private Date creationDate;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date lastUpdateDate;

    private Long lastUpdateLogin;

    private String attributeCategory;


    private String assignValueName;

    public Long getUserRoleAssignId() {
        return userRoleAssignId;
    }

    public void setUserRoleAssignId(Long userRoleAssignId) {
        this.userRoleAssignId = userRoleAssignId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType == null ? null : assignType.trim();
    }

    public Long getAssignValue() {
        return assignValue;
    }

    public void setAssignValue(Long assignValue) {
        this.assignValue = assignValue;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Long lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public String getAttributeCategory() {
        return attributeCategory;
    }

    public void setAttributeCategory(String attributeCategory) {
        this.attributeCategory = attributeCategory == null ? null : attributeCategory.trim();
    }

    public String getAssignValueName() {
        return assignValueName;
    }

    public void setAssignValueName(String assignValueName) {
        this.assignValueName = assignValueName;
    }

}