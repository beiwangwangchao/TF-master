/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 销售订单物流.
 * 
 * @author wuyichu
 */
@Table(name = "OM_SALES_LOGISTICS")
public class SalesLogistics extends BaseDTO {

    @Id
    @Column(name = "LOGISTICS_ID", nullable = false, unique = true)
    private Long logisticsId;

    private Long headerId;

    @NotNull
    private Long shippingTierId;

    @NotNull
    private BigDecimal shippingFee;

    private Long salesOrgId;

    private String autoshipFlag;

    private String codFlag;

    private String shippingTierName;

    private BigDecimal extraAmount;

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getShippingTierId() {
        return shippingTierId;
    }

    public void setShippingTierId(Long shippingTierId) {
        this.shippingTierId = shippingTierId;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getAutoshipFlag() {
        return autoshipFlag;
    }

    public void setAutoshipFlag(String autoshipFlag) {
        this.autoshipFlag = autoshipFlag == null ? null : autoshipFlag.trim();
    }

    public String getCodFlag() {
        return codFlag;
    }

    public void setCodFlag(String codFlag) {
        this.codFlag = codFlag == null ? null : codFlag.trim();
    }

    public String getShippingTierName() {
        return shippingTierName;
    }

    public void setShippingTierName(String shippingTierName) {
        this.shippingTierName = shippingTierName;
    }

    public BigDecimal getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(BigDecimal extraAmount) {
        this.extraAmount = extraAmount;
    }
}