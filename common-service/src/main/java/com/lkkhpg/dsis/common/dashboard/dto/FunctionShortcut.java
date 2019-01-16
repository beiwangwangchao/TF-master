/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.dashboard.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 快捷方式的DTO.
 *
 * @author runbai.chen
 */
@Table(name = "sys_function_shortcut")
public class FunctionShortcut extends BaseDTO {

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column(name = "shortcut_Id", nullable = false)
    private Long shortcutId;
    /**
     * 用户.
     */
    @Column(name = "user_Id")
    private Long userId;
    /**
     * 角色.
     */
    @Column(name = "role_Id")
    private Long roleId;
    /**
     * 功能.
     */
    @Column(name = "function_Id")
    @NotNull
    private Long functionId;
    /**
     * 序号.
     */
    @Column(name = "sort_number")
    private Long sortNumber;

    public Long getShortcutId() {
        return shortcutId;
    }

    public void setShortcutId(Long shortcutId) {
        this.shortcutId = shortcutId;
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

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public Long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
    }

}