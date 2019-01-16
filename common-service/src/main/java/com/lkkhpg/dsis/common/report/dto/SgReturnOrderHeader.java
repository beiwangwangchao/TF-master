/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 新加坡退货单报表的头数据DTO.
 * 
 * @author zhenyang.he
 *
 */
public class SgReturnOrderHeader {

    private String memberName;

    private String memberCode;

    private String returnNumber;

    private String gstId;

    private String creditNote;

    private String shipTo;

    private String deliveryType;

    private Date returnDate;

    private String period;

    private String deliveryAddress;

    private String telNo;

    private String userName;

    private String remark;

    private BigDecimal taxAmt;

    private BigDecimal totalPV;

    private BigDecimal totalAmt;

    private BigDecimal totalGST;

    private BigDecimal shippingFeeBeforeTax;

    private BigDecimal total;

    private BigDecimal totalAfterTax;

    private BigDecimal adjustment;

    private BigDecimal voucher;

    private BigDecimal totalactualPayAmount;

    private BigDecimal tax;

    private BigDecimal shippingFeeTax;

    private BigDecimal totalTax;

    public BigDecimal getShippingFeeTax() {
        return shippingFeeTax;
    }

    public void setShippingFeeTax(BigDecimal shippingFeeTax) {
        this.shippingFeeTax = shippingFeeTax;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalactualPayAmount() {
        return totalactualPayAmount;
    }

    public void setTotalactualPayAmount(BigDecimal totalactualPayAmount) {
        this.totalactualPayAmount = totalactualPayAmount;
    }

    public BigDecimal getVoucher() {
        return voucher;
    }

    public void setVoucher(BigDecimal voucher) {
        this.voucher = voucher;
    }

    public BigDecimal getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(BigDecimal adjustment) {
        this.adjustment = adjustment;
    }

    public BigDecimal getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(BigDecimal totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getShippingFeeBeforeTax() {
        return shippingFeeBeforeTax;
    }

    public void setShippingFeeBeforeTax(BigDecimal shippingFeeBeforeTax) {
        this.shippingFeeBeforeTax = shippingFeeBeforeTax;
    }

    public BigDecimal getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        this.taxAmt = taxAmt;
    }

    public BigDecimal getTotalPV() {
        return totalPV;
    }

    public void setTotalPV(BigDecimal totalPV) {
        this.totalPV = totalPV;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getTotalGST() {
        return totalGST;
    }

    public void setTotalGST(BigDecimal totalGST) {
        this.totalGST = totalGST;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

}
