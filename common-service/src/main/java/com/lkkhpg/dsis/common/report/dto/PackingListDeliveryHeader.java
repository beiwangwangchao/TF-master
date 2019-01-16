/*
 *
 */
package com.lkkhpg.dsis.common.report.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * PACKING-LIST 打印清单 DELIVERY-HEADER.
 * 
 * @author zhenyang.he
 *
 */
public class PackingListDeliveryHeader extends BaseDTO {

    private String inventory;
    private String distributorName;
    private String distributorNo;
    private String ReceiptNo;
    private String shipmentNo;
    private String deliveryDate;
    private Long deliveryId;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
