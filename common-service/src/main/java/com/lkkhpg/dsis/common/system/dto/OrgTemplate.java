/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织模板.
 * 
 * @author runbai.chen
 */
public class OrgTemplate extends BaseDTO {
    private Long orgTemplateId;

    private String templateCode;

    private String remark;

    @Children
    private List<OrgTemplateDtl> orgTemplateDtls;

    public List<OrgTemplateDtl> getOrgTemplateDtls() {
        return orgTemplateDtls;
    }

    public void setOrgTemplateDtls(List<OrgTemplateDtl> orgTemplateDtls) {
        this.orgTemplateDtls = orgTemplateDtls;
    }

    public Long getOrgTemplateId() {
        return orgTemplateId;
    }

    public void setOrgTemplateId(Long orgTemplateId) {
        this.orgTemplateId = orgTemplateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}