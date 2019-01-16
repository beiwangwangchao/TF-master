/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 批次DTO.
 * 
 * @author mclin
 */
public class Lot extends BaseDTO {
    private Long organizationId;

    private Long itemId;

    private String lotNumber;

    private Date expiryDate;

    private String enabledFlag;

    private Long invOrgId;

    private Long hasOnHandQty;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public Long getHasOnHandQty() {
        return hasOnHandQty;
    }

    public void setHasOnHandQty(Long hasOnHandQty) {
        this.hasOnHandQty = hasOnHandQty;
    }

}