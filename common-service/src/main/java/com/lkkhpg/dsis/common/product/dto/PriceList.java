/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品价格头.
 * 
 * @author wuyichu
 */
@Table(name = "OM_PRICE_LIST")
public class PriceList extends BaseDTO {

    @Id
    @Column(name = "PRICE_LIST_ID", nullable = false, unique = true)
    private Long priceListId;

    @NotEmpty
    private Long salesOrgId;

    private String name;

    private String description;

    private String globalFlag;

    public Long getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(Long priceListId) {
        this.priceListId = priceListId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGlobalFlag() {
        return globalFlag;
    }

    public void setGlobalFlag(String globalFlag) {
        this.globalFlag = globalFlag;
    }

}