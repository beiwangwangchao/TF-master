/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 角色DTO.
 *
 * @author wuyichu
 */
@MultiLanguage
@Table(name = "sys_role_b")
public class Role extends BaseDTO {

    public static final String FIELD_ROLE_ID = "roleId";

    private static final long serialVersionUID = -4852482475548005622L;

    /**
     * 区域ID.
     */
    private String areaCode;

    /**
     * 角色编码.
     */
    @NotEmpty
    @Size(max = 20)
    private String roleCode;

    /**
     * 角色描述.
     */
    @MultiLanguageField
    @Column(name = "role_description")
    @NotEmpty
    private String roleDesc;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 角色名称.
     */
    @MultiLanguageField
    @Column(name = "role_name")
    @NotEmpty
    private String roleName;

    /**
     * 用户ID.
     */
    private Long userId;

    /**
     * 角色分配是否默认标识.
     */
    private String defaultFlag;
    
    private Long surId;

    public String getAreaCode() {
        return areaCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Long getSurId() {
        return surId;
    }

    public void setSurId(Long surId) {
        this.surId = surId;
    }

}