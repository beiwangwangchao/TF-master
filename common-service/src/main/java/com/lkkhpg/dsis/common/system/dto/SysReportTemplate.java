/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.Date;
import java.util.List;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 报表模板Dto.
 * @author hanrui.huang
 *
 */
public class SysReportTemplate extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long templateId;

    private String templateCode;

    private String templateName;

    private String remark;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private Long fileId;

    private String fileName;

    private String fileSize;
    
    private String filePath;

    private Long marketId;

    private String templateCateogry;

    private String name;
    
    private String templateDescription;
    @Children
    private List<SysTemplateAssign> sysTemplateAssigns;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public List<SysTemplateAssign> getSysTemplateAssigns() {
        return sysTemplateAssigns;
    }

    public void setSysTemplateAssigns(List<SysTemplateAssign> sysTemplateAssigns) {
        this.sysTemplateAssigns = sysTemplateAssigns;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getTemplateCateogry() {
        return templateCateogry;
    }

    public void setTemplateCateogry(String templateCateogry) {
        this.templateCateogry = templateCateogry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }
    
    
}