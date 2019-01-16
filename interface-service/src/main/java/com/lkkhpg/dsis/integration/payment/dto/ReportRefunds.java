package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hand on 2018/6/6.
 */
public class ReportRefunds {
    @Id
    @Column(name = "OUT_REFUND_NO", nullable = false, unique = true)
    private String outRefundNo;
    private Date creationDate;
    /**
     * 退款金额.
     */
    private BigDecimal refundFee;
    /**
     * 原订单金额.
     */
    private BigDecimal totalFee;
    /**
     * 物流费.
     */
    private BigDecimal transportFee;
    /**
     * 退款原因.
     */
    private String redundReason;
    /**
     * 服务类型.
     */
    private String serviceType;
    /**
     * 退款说明.
     */
    private String refundExplain;
    private  BigDecimal weight;
    private BigDecimal unitSellingPrice;
    @NotNull
    @Max(value = 99999)
    @Min(value = 1)
    private BigDecimal quantity;

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
    private Date creationDateFrom;
    private Date creationDateTo;
    private Long salesOrgId;
    @NotNull
    private Long memberId;

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(BigDecimal transportFee) {
        this.transportFee = transportFee;
    }

    public String getRedundReason() {
        return redundReason;
    }

    public void setRedundReason(String redundReason) {
        this.redundReason = redundReason;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRefundExplain() {
        return refundExplain;
    }

    public void setRefundExplain(String refundExplain) {
        this.refundExplain = refundExplain;
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

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
