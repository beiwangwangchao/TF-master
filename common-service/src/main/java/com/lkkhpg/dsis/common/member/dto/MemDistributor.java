/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

public class MemDistributor extends BaseDTO {
    private Long recordId;

    private Long memberId;

    private Date messageDate;

    private Date recordDate;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}