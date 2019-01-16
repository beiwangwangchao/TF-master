/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 会员资产余额DTO.
 * 
 * @author frank.li
 */
public class MemAccountingBalance extends BaseDTO implements Comparable<MemAccountingBalance> {

    private static final long serialVersionUID = 1L;

    private Long accountingBalanceId;

    @NotNull
    private Long memberId;

    @NotNull
    private String accountingType;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private Long lastTrxId;

    @NotNull
    private Long initTrxId;

    public Long getAccountingBalanceId() {
        return accountingBalanceId;
    }

    public void setAccountingBalanceId(Long accountingBalanceId) {
        this.accountingBalanceId = accountingBalanceId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAccountingType() {
        return accountingType;
    }

    public void setAccountingType(String accountingType) {
        this.accountingType = accountingType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getLastTrxId() {
        return lastTrxId;
    }

    public void setLastTrxId(Long lastTrxId) {
        this.lastTrxId = lastTrxId;
    }

    public Long getInitTrxId() {
        return initTrxId;
    }

    public void setInitTrxId(Long initTrxId) {
        this.initTrxId = initTrxId;
    }

    /*
     * 排序显示几项资产
     */
    @Override
    public int compareTo(MemAccountingBalance o) {

        return o.getAccountingType().compareTo(accountingType);

    }

}