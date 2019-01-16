/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.cost.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 成本属性类.
 * 
 * @author houmin
 *
 */
public class CostAttributes extends BaseDTO {
    private Long costAttrId;

    private Long costRecordId;

    private String costAttribute;

    private BigDecimal costValue;

    private String remark;

    private String beforeCostAttribute;

    private BigDecimal beforeCostValue;

    public Long getCostAttrId() {
        return costAttrId;
    }

    public void setCostAttrId(Long costAttrId) {
        this.costAttrId = costAttrId;
    }

    public Long getCostRecordId() {
        return costRecordId;
    }

    public void setCostRecordId(Long costRecordId) {
        this.costRecordId = costRecordId;
    }

    public String getCostAttribute() {
        return costAttribute;
    }

    public void setCostAttribute(String costAttribute) {
        this.costAttribute = costAttribute;
    }

    public BigDecimal getCostValue() {
        return costValue;
    }

    public void setCostValue(BigDecimal costValue) {
        this.costValue = costValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeforeCostAttribute() {
        return beforeCostAttribute;
    }

    public void setBeforeCostAttribute(String beforeCostAttribute) {
        this.beforeCostAttribute = beforeCostAttribute;
    }

    public BigDecimal getBeforeCostValue() {
        return beforeCostValue;
    }

    public void setBeforeCostValue(BigDecimal beforeCostValue) {
        this.beforeCostValue = beforeCostValue;
    }

}