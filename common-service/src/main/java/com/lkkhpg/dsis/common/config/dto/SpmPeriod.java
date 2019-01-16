/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 奖金月份dto.
 * 
 * @author gulin
 *
 */
public class SpmPeriod extends BaseDTO {
    private Long periodId;

    private String periodType;

    private String periodName;

    private Date startDate;

    private Date endDate;

    private String closingStatus;

    private Long periodYear;

    private Long periodMonth;
    private String orgType;
    private Long orgId;
    
    //市场code
    private String code;
    //市场name
    private String name;
    private Date creationDate;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType == null ? null : periodType.trim();
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName == null ? null : periodName.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getClosingStatus() {
        return closingStatus;
    }

    public void setClosingStatus(String closingStatus) {
        this.closingStatus = closingStatus == null ? null : closingStatus.trim();
    }

    public Long getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Long periodYear) {
        this.periodYear = periodYear;
    }

    public Long getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Long periodMonth) {
        this.periodMonth = periodMonth;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
    
}