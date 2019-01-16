/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * DELIVERY-HEADER.
 * 
 * @author lipeng.lin
 *
 */
public class TWPrintDeliveryRecordHeader extends BaseDTO {

    private Long deliveryId;
    private String deliveryNumber;
    private String orderNumber;
    private String invoiceNumber;
    private String inventory;
    private String saleOrderType;
    private String deliveryType;
    private String deliveryDate;
    private String distributorName;
    private String contactName;
    private String phone;
    private String address;
    private String remark;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSaleOrderType() {
        return saleOrderType;
    }

    public void setSaleOrderType(String saleOrderType) {
        this.saleOrderType = saleOrderType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

}
