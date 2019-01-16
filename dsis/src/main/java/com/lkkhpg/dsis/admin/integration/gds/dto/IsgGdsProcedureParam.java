/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

import java.util.Date;

/**
 * GDS接口存储过程返回信息DTO.
 * 
 * @author frank.li
 */
public class IsgGdsProcedureParam {
    
    /**
     * 存储过程返回状态
     */
    private String returnStatus;

    /**
     * 存储过程返回信息
     */
    private String returnMessage;

    /**
     * 业务接口代码
     */
    private String intCode;

    /**
     * 增量O或全量F
     */
    private String fullOrOffset;

    /**
     * 期间 
     */
    private String periodCode;

    /**
     * 时间从
     */
    private Date dateFrom;

    /**
     * 时间至
     */
    private Date dateTo;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 语言代码
     */
    private String langCode;

    /**
     * 重新提交  Y =>是，N=>否
     */
    private String retryFlag;

    /**
     * 是否清除接口表标准，默认为'N'
     */
    private String clearFlag;
    
    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getIntCode() {
        return intCode;
    }

    public void setIntCode(String intCode) {
        this.intCode = intCode;
    }

    public String getFullOrOffset() {
        return fullOrOffset;
    }

    public void setFullOrOffset(String fullOrOffset) {
        this.fullOrOffset = fullOrOffset;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getRetryFlag() {
        return retryFlag;
    }

    public void setRetryFlag(String retryFlag) {
        this.retryFlag = retryFlag;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

}