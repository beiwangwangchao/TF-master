/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

/**
 * 库存组织下拉框DTO.
 * 
 * @author mclin
 */
public class OrgSelection {
    private Long invOrgId;
    private String code;
    private String name;
    private Long operationUnitId;
    private String operationUnitCode;
    private String operationUnitName;

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
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

    public Long getOperationUnitId() {
        return operationUnitId;
    }

    public void setOperationUnitId(Long operationUnitId) {
        this.operationUnitId = operationUnitId;
    }

    public String getOperationUnitCode() {
        return operationUnitCode;
    }

    public void setOperationUnitCode(String operationUnitCode) {
        this.operationUnitCode = operationUnitCode == null ? null : operationUnitCode.trim();
    }

    public String getOperationUnitName() {
        return operationUnitName;
    }

    public void setOperationUnitName(String operationUnitName) {
        this.operationUnitName = operationUnitName == null ? null : operationUnitName.trim();
    }
}
