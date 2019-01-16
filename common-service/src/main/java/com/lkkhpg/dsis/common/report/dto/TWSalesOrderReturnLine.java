/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.dto;

import java.math.BigDecimal;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 台湾营业人销货退回单行数据dto.
 * 
 * @author zhenyang.he
 *
 */
public class TWSalesOrderReturnLine {
    // 年（中华民国）
    private String payYear;
    // 月
    private String payMom;
    // 日
    private String payDay;
    // 字轨
    private String zg;
    // 号码
    private String invoiceNum;
    // 品名
    private String itemName;
    // 数量
    private BigDecimal quantity;
    // 销售价
    private BigDecimal salesPrice;
    // 税率
    private BigDecimal tax;
    // 单价
    private BigDecimal price;
    // 金额
    private BigDecimal amount;
    // 营业税额
    private BigDecimal taxAmount;
    // 销售订单头ID
    private Long orderHeaderId;
    // 人工调整标记
    private String returnAdjustFlag;
    // 汇总金额
    private BigDecimal totalAmount;
    //汇总营业税额
    private BigDecimal totalTaxAmount;
    //应税
    private String taxable;
    //运费标记
    private String shippingFeeFlag;
    

    public String getShippingFeeFlag() {
        return shippingFeeFlag;
    }

    public void setShippingFeeFlag(String shippingFeeFlag) {
        this.shippingFeeFlag = shippingFeeFlag;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReturnAdjustFlag() {
        return returnAdjustFlag;
    }

    public void setReturnAdjustFlag(String returnAdjustFlag) {
        this.returnAdjustFlag = returnAdjustFlag;
    }

    public Long getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(Long orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getPayYear() {
        return payYear;
    }

    public void setPayYear(String payYear) {
        this.payYear = payYear;
    }

    public String getPayMom() {
        return payMom;
    }

    public void setPayMom(String payMom) {
        this.payMom = payMom;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }

    public String getZg() {
        return zg;
    }

    public void setZg(String zg) {
        this.zg = zg;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

}
