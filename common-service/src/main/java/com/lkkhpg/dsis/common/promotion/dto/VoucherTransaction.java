/*
 *
 */
package com.lkkhpg.dsis.common.promotion.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 优惠券事务dto.
 * 
 * @author houmin
 *
 */
public class VoucherTransaction extends BaseDTO {
    private Long transactionId;

    private Long marketId;

    private Long salesOrgId;

    private Long memberId;

    private Long voucherId;

    private String trxSourceType;

    private String trxSourceKey;

    private String trxSourceReference;

    private Date trxDate;

    private Long trxQty;

    private String trxType;

    private String trxReason;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getTrxSourceType() {
        return trxSourceType;
    }

    public void setTrxSourceType(String trxSourceType) {
        this.trxSourceType = trxSourceType == null ? null : trxSourceType.trim();
    }

    public String getTrxSourceKey() {
        return trxSourceKey;
    }

    public void setTrxSourceKey(String trxSourceKey) {
        this.trxSourceKey = trxSourceKey == null ? null : trxSourceKey.trim();
    }

    public String getTrxSourceReference() {
        return trxSourceReference;
    }

    public void setTrxSourceReference(String trxSourceReference) {
        this.trxSourceReference = trxSourceReference == null ? null : trxSourceReference.trim();
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public Long getTrxQty() {
        return trxQty;
    }

    public void setTrxQty(Long trxQty) {
        this.trxQty = trxQty;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public String getTrxReason() {
        return trxReason;
    }

    public void setTrxReason(String trxReason) {
        this.trxReason = trxReason == null ? null : trxReason.trim();
    }
}