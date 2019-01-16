/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.lading.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
/**
 * 提货单头dto.
 * @author liuxuan
 *
 */
@Table(name = "DO_HEADER")
public class DoHeader extends BaseDTO {
    @Id
    @Column(name = "DO_HEADER_ID")
    private Long  doHeaderId;

    private String doNumber;

    private Long  marketId;
    
    private String marketName;

    private Date orderDate;

    private String doTo;

    private String vendorName;

    private String deliveryTo;

    private String contacts;

    private String fax;

    private String phone;

    private Date expectedDate;

    private String incoterm;

    private String currency;

    private String deliveryAddress;

    private String remark;
    
    private Date startDate;
    
    private Date overDate;
    
    private String name;
    
    @Children
    private List<DoLine> doLines;
    
    private String description;
    
    private Long packageQuantity;
    
    private Long minQuantity;
    
    private String itemName;
    
    private String uomCode;
    
    private Long quantity;
    
    private String currencyName;
    
    private String areaCode;
    
    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Long getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Long packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DoLine> getDoLines() {
        return doLines;
    }

    public void setDoLines(List<DoLine> doLines) {
        this.doLines = doLines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public Long getDoHeaderId() {
        return doHeaderId;
    }

    public void setDoHeaderId(Long doHeaderId) {
        this.doHeaderId = doHeaderId;
    }

    public String getDoNumber() {
        return doNumber;
    }

    public void setDoNumber(String doNumber) {
        this.doNumber = doNumber == null ? null : doNumber.trim();
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDoTo() {
        return doTo;
    }

    public void setDoTo(String doTo) {
        this.doTo = doTo == null ? null : doTo.trim();
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo == null ? null : deliveryTo.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm == null ? null : incoterm.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}

