/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

import java.math.BigDecimal;

/**
 * MWS购物车明细dto.
 * @author gulin
 *
 */
public class ShopCartItem {
    private Long productId;
    
    private BigDecimal quantity;
    
    private String flag;
    
    private String uuid;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
}
