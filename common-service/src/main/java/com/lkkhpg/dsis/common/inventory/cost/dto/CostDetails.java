/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.cost.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 成本维护数据.
 * 
 * @author huangjiajing
 *
 */
public class CostDetails extends BaseDTO {
    private Long costDetailId;

    private String trxType;

    private Long invOrgId;

    private Long itemId;

    private String lotNumber;

    private Date expiryDate;

    private BigDecimal quantity;

    private BigDecimal unitCost;

    private String remark;

    private String status;

    private Integer year;

    private Integer month;

    private String itemNumber;

    private String itemName;
    /**
     * 库存归集中心.
     */
    private Long costOrgId;

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCostDetailId() {
        return costDetailId;
    }

    public void setCostDetailId(Long costDetailId) {
        this.costDetailId = costDetailId;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getCostOrgId() {
        return costOrgId;
    }

    public void setCostOrgId(Long costOrgId) {
        this.costOrgId = costOrgId;
    }

}