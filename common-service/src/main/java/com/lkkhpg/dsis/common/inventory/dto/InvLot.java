/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 批次对象.
 * 
 * @author jiajing.huang
 *
 */
@Table(name = "inv_lot")
public class InvLot extends BaseDTO {
    private static final long serialVersionUID = 1L;
    /**
     * 库存组织ID.
     */
    @Column(name = "organization_id")
    // @NotNull
    private Long organizationId;

    // 库存组织名称
    private String organizationName;

    /**
     * 商品ID.
     */
    @Column(name = "item_id")
    @NotNull
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_number")
    private String itemNumber;

    /**
     * 批次编号.
     */
    @Column(name = "lot_number")
    @NotEmpty
    private String lotNumber;

    /**
     * 库存量.
     */
    @Column(name = "quantity")
    private BigDecimal quantity;
    
    /**
     * 失效日期.
     */
    @Column(name = "expiry_date")
    private Date expiryDate;

    /**
     * 有效标识.
     */
    @Column(name = "enable_flag")
    private String enabledFlag;

    /**
     * 创建日期.
     */
    private Date creationDate;

    private Date expiryDateFrom;
    private Date expiryDateTo;
    private Date creationDateFrom;
    private Date creationDateTo;
    
    private Date creationDateToE;

    public Date getCreationDateToE() {
        return creationDateTo == null ? null : addDate(creationDateTo);
    }

    public Date addDate(Date creationDateToE) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creationDateTo);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Date time = calendar.getTime();
        return time;
    }

    public Date getExpiryDateFrom() {
        return expiryDateFrom;
    }

    public void setExpiryDateFrom(Date expiryDateFrom) {
        this.expiryDateFrom = expiryDateFrom;
    }

    public Date getExpiryDateTo() {
        return expiryDateTo;
    }

    public void setExpiryDateTo(Date expiryDateTo) {
        this.expiryDateTo = expiryDateTo;
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

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
        this.lotNumber = lotNumber;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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
        this.enabledFlag = enabledFlag;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

}
