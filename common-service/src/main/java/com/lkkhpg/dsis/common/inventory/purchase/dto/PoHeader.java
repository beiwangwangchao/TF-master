/*
 *
 */
package com.lkkhpg.dsis.common.inventory.purchase.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 采购订单DTO.
 * 
 * @author HuangJiaJing
 *
 */
@Table(name = "PO_HEADER")
public class PoHeader extends BaseDTO {
    
    @Id
    @Column(name = "PO_HEADER_ID")
    private Long poHeaderId;

    private String poNumber;

    private Long marketId;
    
    private String marketName;

    private Date orderDate;

    private String poTo;

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
    
    private Date orderDateTo;
    
    private Date orderDateDo;
    
    private String name;
    
    private String currencyName;

    private String areaCode;
    
    @Children
    private List<PoLine> poLines;

    private String trxNumber;

    private Long trxId;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public Date getOrderDateDo() {
        return orderDateDo;
    }

    public void setOrderDateDo(Date orderDateDo) {
        this.orderDateDo = orderDateDo;
    }

    public List<PoLine> getPoLines() {
        return poLines;
    }

    public void setPoLines(List<PoLine> poLines) {
        this.poLines = poLines;
    }

    public Long getPoHeaderId() {
        return poHeaderId;
    }

    public void setPoHeaderId(Long poHeaderId) {
        this.poHeaderId = poHeaderId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber == null ? null : poNumber.trim();
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

    public String getPoTo() {
        return poTo;
    }

    public void setPoTo(String poTo) {
        this.poTo = poTo == null ? null : poTo.trim();
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