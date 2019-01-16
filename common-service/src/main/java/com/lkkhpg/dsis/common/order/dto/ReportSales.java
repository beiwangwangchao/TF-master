package com.lkkhpg.dsis.common.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hand on 2018/6/5.
 */
public class ReportSales extends BaseDTO {
    @Id
    @Column(name = "HEADER_ID", nullable = false, unique = true)
    private Long headerId;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @NotNull
    private Date orderDate;

    private String orderNumber;
    @NotNull
    private Long memberId;
    private BigDecimal taxAmt;
    private BigDecimal orderAmt;

    private BigDecimal discountAmt;
    private BigDecimal actrualPayAmt;
    private  BigDecimal weight;
    private BigDecimal unitSellingPrice;
    @NotNull
    @Max(value = 99999)
    @Min(value = 1)
    private BigDecimal quantity;
    @NotNull
    private BigDecimal shippingFee;
    @NotEmpty
    private String itemNumber;
    @NotEmpty
    @MultiLanguageField
    @Column(name = "item_name")
    private String itemName;
    private String categoryName;
    private String  name;
    @NotNull
    private String memberCode;
    private Date orderDateFrom;
    private Date orderDateTo;
    private Long salesOrgId;

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        this.taxAmt = taxAmt;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getActrualPayAmt() {
        return actrualPayAmt;
    }

    public void setActrualPayAmt(BigDecimal actrualPayAmt) {
        this.actrualPayAmt = actrualPayAmt;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Date getOrderDateFrom() {
        return orderDateFrom;
    }

    public void setOrderDateFrom(Date orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }

    public Date getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }
}
