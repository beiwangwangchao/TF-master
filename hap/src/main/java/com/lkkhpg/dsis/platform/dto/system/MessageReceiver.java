/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * @author njq.niu@hand-china.com
 * <p>
 * 2016年3月2日
 */
public class MessageReceiver extends BaseDTO {

    private static final long serialVersionUID = 6189604448869596205L;

    private String messageAddress;

    private Long messageId;

    //使用此枚举类型ReceiverTypeEnum
    private String messageType;

    private Long receiverId;

    public String getMessageAddress() {
        return messageAddress;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setMessageAddress(String messageAddress) {
        this.messageAddress = messageAddress == null ? null : messageAddress.trim();
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

}