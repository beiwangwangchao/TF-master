package com.lkkhpg.dsis.common.product.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by miaoyifan on 2018/1/3.
 */
@Table(name = "om_price_change_history")
public class PriceChangeHistory extends BaseDTO {

    @NotNull
    private Long priceChangeId;

    @NotNull
    private Long itemId;

    @NotNull
    private Long salesOrgId;

    @NotNull
    private BigDecimal originalPrice;

    @NotNull
    private BigDecimal changePrice;

    private String currency;

    private String priceType;

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public void setPriceChangeId(Long priceChangeId){
        this.priceChangeId = priceChangeId;
    }

    public Long getPriceChangeId(){
        return priceChangeId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public Long getItemId(){
        return itemId;
    }

    public void setSalesOrgId(Long salesOrgId){
        this.salesOrgId = salesOrgId;
    }

    public Long getSalesOrgId(){
        return salesOrgId;
    }

    public void setOriginalPrice(BigDecimal originalPrice){
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice(){
        return originalPrice;
    }

    public void setChangePrice(BigDecimal changePrice){
        this.changePrice = changePrice;
    }

    public BigDecimal getChangePrice(){
        return changePrice;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public String getCurrency(){
        return currency;
    }
}
