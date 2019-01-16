/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.cost.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 成本记录.
 * 
 * @author hanrui.huang
 *
 */
public class CostRecords extends BaseDTO {
    private Long costRecordId;

    private String costRecordNumber;

    private Long invOrgId;

    private Long itemId;

    private String lotNumber;

    private Date expiryDate;

    private BigDecimal quantity;

    private BigDecimal unitCost;

    private BigDecimal balance;

    private String status;

    private Integer year;

    private Integer month;

    private String itemNumber;

    private String itemName;

    private Date beforeexpiryDate;

    private Date afterexpiryDate;

    /**
     * 上月结余_数量.
     */
    private BigDecimal lastMonthQty;
    /**
     * 上月结余_单价.
     */
    private BigDecimal lastMonthUnitCost;
    /**
     * 上月结余_总额.
     */
    private BigDecimal lastMonthBalance;
    /**
     * 本期进货_数量.
     */
    private BigDecimal currentStkinQty;
    /**
     * 本期进货_单价.
     */
    private BigDecimal currentUnitCost;
    /**
     * 本月换入_数量.
     */
    private BigDecimal currentComcpQty;
    /**
     * 本月成本_数量.
     */
    private BigDecimal currentCostQty;
    /**
     * 商品事务处理数量.
     */
    private Long trxQty;
    /**
     * 子商品_成本单价.
     */
    private BigDecimal unitCostSub;
    /**
     * 事务日期名称.
     */
    private String trxDateName;
    /**
     * 库存归集中心.
     */
    private Long costOrgId;
    /**
     * 库存组织名称.
     */
    private String invOrgName;

    public Long getCostRecordId() {
        return costRecordId;
    }

    public void setCostRecordId(Long costRecordId) {
        this.costRecordId = costRecordId;
    }

    public String getCostRecordNumber() {
        return costRecordNumber;
    }

    public void setCostRecordNumber(String costRecordNumber) {
        this.costRecordNumber = costRecordNumber == null ? null : costRecordNumber.trim();
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public Date getBeforeexpiryDate() {
        return beforeexpiryDate;
    }

    public void setBeforeexpiryDate(Date beforeexpiryDate) {
        this.beforeexpiryDate = beforeexpiryDate;
    }

    public Date getAfterexpiryDate() {
        return afterexpiryDate;
    }

    public void setAfterexpiryDate(Date afterexpiryDate) {
        this.afterexpiryDate = afterexpiryDate;
    }

    public BigDecimal getLastMonthQty() {
        return lastMonthQty;
    }

    public void setLastMonthQty(BigDecimal lastMonthQty) {
        this.lastMonthQty = lastMonthQty;
    }

    public BigDecimal getLastMonthUnitCost() {
        return lastMonthUnitCost;
    }

    public void setLastMonthUnitCost(BigDecimal lastMonthUnitCost) {
        this.lastMonthUnitCost = lastMonthUnitCost;
    }

    public BigDecimal getLastMonthBalance() {
        return lastMonthBalance;
    }

    public void setLastMonthBalance(BigDecimal lastMonthBalance) {
        this.lastMonthBalance = lastMonthBalance;
    }

    public BigDecimal getCurrentStkinQty() {
        return currentStkinQty;
    }

    public void setCurrentStkinQty(BigDecimal currentStkinQty) {
        this.currentStkinQty = currentStkinQty;
    }

    public BigDecimal getCurrentUnitCost() {
        return currentUnitCost;
    }

    public void setCurrentUnitCost(BigDecimal currentUnitCost) {
        this.currentUnitCost = currentUnitCost;
    }

    public BigDecimal getCurrentComcpQty() {
        return currentComcpQty;
    }

    public void setCurrentComcpQty(BigDecimal currentComcpQty) {
        this.currentComcpQty = currentComcpQty;
    }

    public BigDecimal getCurrentCostQty() {
        return currentCostQty;
    }

    public void setCurrentCostQty(BigDecimal currentCostQty) {
        this.currentCostQty = currentCostQty;
    }

    public Long getTrxQty() {
        return trxQty;
    }

    public void setTrxQty(Long trxQty) {
        this.trxQty = trxQty;
    }

    public BigDecimal getUnitCostSub() {
        return unitCostSub;
    }

    public void setUnitCostSub(BigDecimal unitCostSub) {
        this.unitCostSub = unitCostSub;
    }

    public String getTrxDateName() {
        return trxDateName;
    }

    public void setTrxDateName(String trxDateName) {
        this.trxDateName = trxDateName;
    }

    public Long getCostOrgId() {
        return costOrgId;
    }

    public void setCostOrgId(Long costOrgId) {
        this.costOrgId = costOrgId;
    }

    public String getInvOrgName() {
        return invOrgName;
    }

    public void setInvOrgName(String invOrgName) {
        this.invOrgName = invOrgName;
    }

}