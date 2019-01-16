/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 银行手续费DTO.
 * @author liuxuan
 *
 */
@Table(name = "SPM_BANK_CHARGES")
public class SpmBankCharges extends BaseDTO {
    @Id
    @Column(name = "BANK_CHARGES_ID")
    private Long bankChargesId;

    private Long bankId;

    private Long marketId;

    private Long bonusFrom;

    private Long bonusTo;

    private BigDecimal charges;
    
    private String name;

    private String chargeType;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBankChargesId() {
        return bankChargesId;
    }

    public void setBankChargesId(Long bankChargesId) {
        this.bankChargesId = bankChargesId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getBonusFrom() {
        return bonusFrom;
    }

    public void setBonusFrom(Long bonusFrom) {
        this.bonusFrom = bonusFrom;
    }

    public Long getBonusTo() {
        return bonusTo;
    }

    public void setBonusTo(Long bonusTo) {
        this.bonusTo = bonusTo;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }
    
}