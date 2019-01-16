/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * MessageAttachment.
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年3月2日
 */
public class MessageAttachment extends BaseDTO {
   
    private static final long serialVersionUID = -8831715672578562022L;

    private Long attachmentId;

    private Long fileId;

    private Long messageId;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public Long getFileId() {
        return fileId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

}