/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员账户DTO.
 * 
 * @author frank.li
 */
@AuditEnabled
@Table(name = "MM_MEM_ACCOUNT")
public class MemAccount extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "account_id")
    private Long accountId;

    // @NotNull 由保存Member对象时赋值，加上@NotNull会影响saveMember的validate
    private Long memberId;

    private Long bankId;

    private String bankCode;
    
    private String bankName;

    private String accountNumber;

    private String accountHolder;

    private String idNumber;

    private Long bankBranchId;

    private String bankBranchCode;
    
    private String bankBranchName;

    private String maskedAccountNumber;
    
    private String comments;

    private boolean isCardUpdate;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder == null ? null : accountHolder.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getMaskedAccountNumber() {
        return maskedAccountNumber;
    }

    public void setMaskedAccountNumber(String maskedAccountNumber) {
        this.maskedAccountNumber = maskedAccountNumber;
    }

    public boolean getIsCardUpdate() {
        return isCardUpdate;
    }

    public void setIsCardUpdate(boolean isCardUpdate) {
        this.isCardUpdate = isCardUpdate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(Long bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    
}