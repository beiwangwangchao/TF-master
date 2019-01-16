/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 库存属性参数dto.
 * 
 * @author wangchao
 *
 */
public class InvItemPropertyDto implements Serializable {

    @NotNull
    private Long itemId;

    @NotNull
    private Long invOrganizationId;


    private BigDecimal quantityAlert;

    private String expiryAlert;

    private String mode;
    private String itemType;
    
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getInvOrganizationId() {
        return invOrganizationId;
    }

    public void setInvOrganizationId(Long invOrganizationId) {
        this.invOrganizationId = invOrganizationId;
    }

    public BigDecimal getQuantityAlert() {
        return quantityAlert;
    }

    public void setQuantityAlert(BigDecimal quantityAlert) {
        this.quantityAlert = quantityAlert;
    }

    public String getExpiryAlert() {
        return expiryAlert;
    }

    public void setExpiryAlert(String expiryAlert) {
        this.expiryAlert = expiryAlert;
    }
}
