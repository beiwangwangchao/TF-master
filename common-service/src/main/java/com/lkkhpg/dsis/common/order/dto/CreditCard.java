/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 信用卡.
 * 
 * @author wuyichu
 */
@Table(name = "OM_CREDIT_CARD")
public class CreditCard extends BaseDTO {

    @Id
    @Column(name = "CREDIT_CARD_ID", nullable = false, unique = true)
    private Long creditCardId;

    @NotEmpty
    private String creditCardNum;

    @NotEmpty
    private String maskedCreditCardNum;

    @NotEmpty
    private String cardHolder;

    @NotEmpty
    private String validYear;

    @NotEmpty
    private String validMonth;

    @NotEmpty
    private String bankCode;

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum == null ? null : creditCardNum.trim();
    }

    public String getMaskedCreditCardNum() {
        return maskedCreditCardNum;
    }

    public void setMaskedCreditCardNum(String maskedCreditCardNum) {
        this.maskedCreditCardNum = maskedCreditCardNum == null ? null : maskedCreditCardNum.trim();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder == null ? null : cardHolder.trim();
    }

    public String getValidYear() {
        return validYear;
    }

    public void setValidYear(String validYear) {
        this.validYear = validYear == null ? null : validYear.trim();
    }

    public String getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(String validMonth) {
        this.validMonth = validMonth == null ? null : validMonth.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }
}