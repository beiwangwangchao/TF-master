/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * Message.
 * 
 * @author njq.niu@hand-china.com
 * @author xiawang.liu@hand-china.com 2016年3月2日
 */
public class Message extends BaseDTO {

    private static final long serialVersionUID = -5838987819601451602L;

    private Long messageId;

    private String messageType;

    private String messageHost;

    private String messageFrom;

    private String subject;

    private String content;

    private String priorityLevel;

    private String sendFlag = "N";

    private String messageSource;

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessageHost() {
        return messageHost;
    }

    public void setMessageHost(String messageHost) {
        this.messageHost = messageHost == null ? null : messageHost.trim();
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom == null ? null : messageFrom.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

}