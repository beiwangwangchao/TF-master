/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 用户角色.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class UserRole extends BaseDTO {

    private static final long serialVersionUID = 2098581833914123800L;

    private Long roleId;

    /**
     * 表ID，主键，供其他表做外键.
     * 
     */
    private Long surId;

    private Long userId;

    private String defaultFlag;

    public Long getRoleId() {
        return roleId;
    }

    public Long getSurId() {
        return surId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setSurId(Long surId) {
        this.surId = surId;
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

}