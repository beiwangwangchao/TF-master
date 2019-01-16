/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.dto.system.Role;

/**
 * 角色分配界面的组织模板DTO.
 *
 * @author liang.rao
 */
@MultiLanguage
@Table(name = "sys_role_b")
public class RoleTemplate extends Role {

    private String templateCode;
    
    private Long templateId;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
    
}