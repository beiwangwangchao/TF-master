/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员银行卡Dto.
 * 
 * @author frank.li
 */
@AuditEnabled
@Table(name = "MM_MEM_CARD")
public class MemCard extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "card_id")
    private Long cardId;

    // @NotNull 由保存Member对象时赋值，加上@NotNull会影响saveMember的validate
    private Long memberId;

    @NotNull
    private String cardNumber;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM")
    private Date expiryDate;

    @NotNull
    private String defaultFlag;

    @NotNull
    private String status;

    @NotNull
    private String cardType;

    @NotNull
    private String cardSubType;

    private String bankCode;

    private String maskedCardNumber;

    private boolean isCardUpdate;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {

        this.cardType = cardType;
    }

    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {

        this.bankCode = bankCode;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public boolean getIsCardUpdate() {
        return isCardUpdate;
    }

    public void setIsCardUpdate(boolean isCardUpdate) {
        this.isCardUpdate = isCardUpdate;
    }

}