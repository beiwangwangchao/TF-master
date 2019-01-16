/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 报表程序.
 * 
 * @author wangchao
 *
 */
@Table(name = "sys_report_program")
public class SysReportProgram extends BaseDTO {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "report_program_id")
    private Long reportProgramId;

    @NotEmpty
    private String programName;

    @NotEmpty
    private String programCode;

    private String remark;

    private String dataServiceName;

    @NotEmpty
    private String templateCode;
    
    private String entityCode;
    
    private String entityKey;
    
    private String entityKeyDesc;
    
    @Children
    private List<SysReportParams> reportParams;
    
    public List<SysReportParams> getReportParams() {
        return reportParams;
    }

    public void setReportParams(List<SysReportParams> reportParams) {
        this.reportParams = reportParams;
    }

    public Long getReportProgramId() {
        return reportProgramId;
    }

    public void setReportProgramId(Long reportProgramId) {
        this.reportProgramId = reportProgramId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataServiceName() {
        return dataServiceName;
    }

    public void setDataServiceName(String dataServiceName) {
        this.dataServiceName = dataServiceName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getEntityKeyDesc() {
        return entityKeyDesc;
    }

    public void setEntityKeyDesc(String entityKeyDesc) {
        this.entityKeyDesc = entityKeyDesc;
    }
    
}
