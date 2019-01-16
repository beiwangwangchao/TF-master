/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 角色功能DTO.
 * 
 * @author wuyichu
 */
public class RoleFunction extends BaseDTO {

    private static final long serialVersionUID = 688371423171208115L;

    private Long functionId;

    private Long roleId;

    private Long srfId;

    public Long getFunctionId() {
        return functionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getSrfId() {
        return srfId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setSrfId(Long srfId) {
        this.srfId = srfId;
    }

}