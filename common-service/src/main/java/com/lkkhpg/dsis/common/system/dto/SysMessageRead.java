/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import javax.persistence.Id;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 消息记录表dto.
 * 
 * @author HuangJiaJing
 *
 */
public class SysMessageRead extends BaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long readId;

    private Long messageId;

    private Long accountId;
    //userid
    private Long sender;

    private String status;

    public static final String DEL = "D";
    public static final String READ = "Y";
    public static final String NEW = "N";

    public Long getReadId() {
        return readId;
    }

    public void setReadId(Long readId) {
        this.readId = readId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

}