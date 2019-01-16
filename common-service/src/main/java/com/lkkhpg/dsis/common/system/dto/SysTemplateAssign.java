/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 报表模板分配市场DTO.
 * 
 * @author hanrui.huang
 */
public class SysTemplateAssign extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long assignId;

    private Long templateId;

    private Long marketId;

    private String code;

    private String name;

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}