/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 消息DTO.
 * 
 * @author HuangJiaJing
 *
 */
public class SysMsMessage extends BaseDTO {

    public static final String DRAFT = "DRAFT";
    
    public static final String SEND = "PUBLI";

    public static final String ADVER = "ADVER";

    public static final String SYSME = "SYSME";

    private Long msMessageId;

    private Long marketId;

    private String messageName;

    private String messageType;

    private String messageStatus;

    private String publishChannel;

    private String publishDateType;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date publishDate;

    private Long sender;

    private String messageContent;

    /**
     * accountId.
     */
    private String senderCode;
    /**
     * 邮件或短信所属市场.
     */
    private Long senderMarketId;
    /**
     * 发送账号.
     */
    private String senderCodeText;

    private String marketName;

    private String userName;
    
    @Children
    private List<SysMsMessageAssign> memberAssigns;

    @Children
    private List<SysMsMessageAssign> userAssigns;


    public String getSenderCodeText() {
        return senderCodeText;
    }

    public void setSenderCodeText(String senderCodeText) {
        this.senderCodeText = senderCodeText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public List<SysMsMessageAssign> getMemberAssigns() {
        return memberAssigns;
    }

    public void setMemberAssigns(List<SysMsMessageAssign> memberAssigns) {
        this.memberAssigns = memberAssigns;
    }

    public List<SysMsMessageAssign> getUserAssigns() {
        return userAssigns;
    }

    public void setUserAssigns(List<SysMsMessageAssign> userAssigns) {
        this.userAssigns = userAssigns;
    }

    public Long getMsMessageId() {
        return msMessageId;
    }

    public void setMsMessageId(Long msMessageId) {
        this.msMessageId = msMessageId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName == null ? null : messageName.trim();
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus == null ? null : messageStatus.trim();
    }

    public String getPublishChannel() {
        return publishChannel;
    }

    public void setPublishChannel(String publishChannel) {
        this.publishChannel = publishChannel == null ? null : publishChannel.trim();
    }

    public String getPublishDateType() {
        return publishDateType;
    }

    public void setPublishDateType(String publishDateType) {
        this.publishDateType = publishDateType == null ? null : publishDateType.trim();
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getSenderMarketId() {
        return senderMarketId;
    }

    public void setSenderMarketId(Long senderMarketId) {
        this.senderMarketId = senderMarketId;
    }

}