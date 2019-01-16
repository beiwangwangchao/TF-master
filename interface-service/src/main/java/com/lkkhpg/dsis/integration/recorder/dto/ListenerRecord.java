/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.recorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lkkhpg.dsis.platform.constant.BaseConstants;

/**
 * 监听器记录.
 * @author shengyang.zhou@hand-china.com
 */
public class ListenerRecord {

    private Long requestId;

    private String ip;

    private String host;

    private Integer port;

    private String serviceName;

    private String methodName;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date requestDate;

    private String queryString;

    private String origin;

    private String userAgent;

    private String body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date requestDateFrom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date requestDateTo;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString == null ? null : queryString.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getRequestDateFrom() {
        return requestDateFrom;
    }

    public void setRequestDateFrom(Date requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    public Date getRequestDateTo() {
        return requestDateTo;
    }

    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }
}