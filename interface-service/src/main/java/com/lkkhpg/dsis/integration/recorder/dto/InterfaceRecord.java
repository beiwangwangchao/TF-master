/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.recorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 接口记录.
 * @author shengyang.zhou@hand-china.com
 */
public class InterfaceRecord {
    private Long recordId;

    private String url;

    private String queryString;

    private String body;

    private String result;

    private String success;

    private String exception;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date requestDate;

    private Long elapse;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString == null ? null : queryString.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success == null ? null : success.trim();
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getElapse() {
        return elapse;
    }

    public void setElapse(Long elapse) {
        this.elapse = elapse;
    }
}