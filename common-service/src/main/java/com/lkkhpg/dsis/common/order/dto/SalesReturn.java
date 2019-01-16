/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 退货单DTO.
 * 
 * @author houmin
 *
 */
@Table(name = "OM_SALES_RETURN")
public class SalesReturn extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RETURN_ID", nullable = false, unique = true)
    private Long returnId;

    @NotNull
    private Long salesOrgId;

    private Long orderHeaderId;
    /**
     * 退货单编号.
     */
    private String returnNumber;
    /**
     * 销售订单编号.
     */
    private String orderNumber;
    /**
     * 订单类型.
     */
    private String orderType;
    /**
     * 订单日期.
     */
    private Date orderDate;
    /**
     * 订单支付日期.
     */
    private Date payDate;
    /**
     * 退货仓库名称.
     */
    private String invOrgName;
    /**
     * 退货日期.
     */
    private Date returnDate;
    /**
     * 创建日期.
     */
    private Date creationDate;
    /**
     * 退货类型.
     */
    private String returnType;
    /**
     * 退货原因.
     */
    private String returnReason;
    /**
     * 退货仓库.
     */
    private Long invOrgId;
    /**
     * 返还优惠.
     */
    private BigDecimal returnPromotion;
    /**
     * 备注.
     */
    private String remark;
    /**
     * 退货金额.
     */
    private BigDecimal amount;
    /**
     * 退货pv.
     */
    private BigDecimal point;
    /**
     * 税额.
     */
    private BigDecimal taxAmount;
    /**
     * 退货单实退款.
     */
    private BigDecimal actualPayAmount;
    /**
     * 退货状态.
     */
    private String returnStatus;
    /**
     * 奖金月份Id.
     */
    private Long periodId;
    /**
     * 订单配送类型.
     */
    private String deliveryType;
    /**
     * 会员ID.
     */
    private Long memberId;
    /**
     * 会员编号.
     */
    private String memberCode;
    /**
     * 会员名称.
     */
    private String memberName;
    /**
     * 销售区域地址.
     */
    private String salesOrgLocation;
    /**
     * 退货仓库地址.
     */
    private String invOrgLocation;
    /**
     * 退货行.
     */
    @Children
    private List<SalesRtnLine> salesRtnLines;
    /**
     * 退款金额行.
     */
    @Children
    private List<SalesRtnRefund> salesRtnRefundLines;
    /**
     * 退货日期从.
     */
    private Date returnDateFrom;
    /**
     * 退货日期至.
     */
    private Date returnDateTo;
    /**
     * 退货单状态.
     */
    private List<String> returnStatusList;
    /**
     * 是否返还人工调整标识.
     */
    private String returnAdjustFlag;
    /**
     * 订单金额.
     */
    private BigDecimal orderAmt;
    /**
     * 订单实际支付金额.
     */
    private BigDecimal orderActrualPayAmt;
    /**
     * 调整金额总和.
     */
    private BigDecimal adjustAmt;
    /**
     * 已退款金额总和.
     */
    private BigDecimal returnAmt;
    /**
     * 优惠券订单额数总和.
     */
    private BigDecimal voucherOrderAmt;
    /**
     * 订单所使用优惠券的优惠总和.
     */
    private BigDecimal voucherAmt;
    /**
     * 退货-返还优惠值总和.
     */
    private BigDecimal returnPromotionSum;
    /**
     * 订单实退款之和.
     */
    private BigDecimal actualRtnAmtSum;
    /**
     * 是否返还运费标识.
     */
    private String shippingFeeFlag;
    /**
     * 订单运费.
     */
    private BigDecimal shippingFeeAmt;

    private String syncFlag;

    /**
     * 账款单编号.
     */
    private String creditNote;
    /**
     * 订单发票编号.
     */
    private String invoiceNumber;
    /**
     * 退货单-手工调整金额.
     */
    private BigDecimal manualAdjustAmt;
    /**
     * 退货单-人工调整-备注.
     */
    private String comments;
    /**
     * 订单历史是否已返还人工调整标识.
     */
    private String rtnAdjustFlagBefore;
    /**
     * 订单历史是否已返还运费标识.
     */
    private String shippingFeeFlagBefore;

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getOrderHeaderId() {
        return orderHeaderId;
    }

    public void setOrderHeaderId(Long orderHeaderId) {
        this.orderHeaderId = orderHeaderId;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber == null ? null : returnNumber.trim();
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    public BigDecimal getReturnPromotion() {
        return returnPromotion;
    }

    public void setReturnPromotion(BigDecimal returnPromotion) {
        this.returnPromotion = returnPromotion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<SalesRtnLine> getSalesRtnLines() {
        return salesRtnLines;
    }

    public void setSalesRtnLines(List<SalesRtnLine> salesRtnLines) {
        this.salesRtnLines = salesRtnLines;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getActualPayAmount() {
        return actualPayAmount;
    }

    public void setActualPayAmount(BigDecimal actualPayAmount) {
        this.actualPayAmount = actualPayAmount;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public List<SalesRtnRefund> getSalesRtnRefundLines() {
        return salesRtnRefundLines;
    }

    public void setSalesRtnRefundLines(List<SalesRtnRefund> salesRtnRefundLines) {
        this.salesRtnRefundLines = salesRtnRefundLines;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getInvOrgName() {
        return invOrgName;
    }

    public void setInvOrgName(String invOrgName) {
        this.invOrgName = invOrgName;
    }

    public String getSalesOrgLocation() {
        return salesOrgLocation;
    }

    public void setSalesOrgLocation(String salesOrgLocation) {
        this.salesOrgLocation = salesOrgLocation;
    }

    public String getInvOrgLocation() {
        return invOrgLocation;
    }

    public void setInvOrgLocation(String invOrgLocation) {
        this.invOrgLocation = invOrgLocation;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getReturnDateFrom() {
        return returnDateFrom;
    }

    public void setReturnDateFrom(Date returnDateFrom) {
        this.returnDateFrom = returnDateFrom;
    }

    public Date getReturnDateTo() {
        return returnDateTo;
    }

    public void setReturnDateTo(Date returnDateTo) {
        this.returnDateTo = returnDateTo;
    }

    public List<String> getReturnStatusList() {
        return returnStatusList;
    }

    public void setReturnStatusList(List<String> returnStatusList) {
        this.returnStatusList = returnStatusList;
    }

    public String getReturnAdjustFlag() {
        return returnAdjustFlag;
    }

    public void setReturnAdjustFlag(String returnAdjustFlag) {
        this.returnAdjustFlag = returnAdjustFlag;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getOrderActrualPayAmt() {
        return orderActrualPayAmt;
    }

    public void setOrderActrualPayAmt(BigDecimal orderActrualPayAmt) {
        this.orderActrualPayAmt = orderActrualPayAmt;
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    public BigDecimal getVoucherOrderAmt() {
        return voucherOrderAmt;
    }

    public void setVoucherOrderAmt(BigDecimal voucherOrderAmt) {
        this.voucherOrderAmt = voucherOrderAmt;
    }

    public BigDecimal getVoucherAmt() {
        return voucherAmt;
    }

    public void setVoucherAmt(BigDecimal voucherAmt) {
        this.voucherAmt = voucherAmt;
    }

    public BigDecimal getReturnPromotionSum() {
        return returnPromotionSum;
    }

    public void setReturnPromotionSum(BigDecimal returnPromotionSum) {
        this.returnPromotionSum = returnPromotionSum;
    }

    public BigDecimal getActualRtnAmtSum() {
        return actualRtnAmtSum;
    }

    public void setActualRtnAmtSum(BigDecimal actualRtnAmtSum) {
        this.actualRtnAmtSum = actualRtnAmtSum;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public String getShippingFeeFlag() {
        return shippingFeeFlag;
    }

    public void setShippingFeeFlag(String shippingFeeFlag) {
        this.shippingFeeFlag = shippingFeeFlag;
    }

    public BigDecimal getShippingFeeAmt() {
        return shippingFeeAmt;
    }

    public void setShippingFeeAmt(BigDecimal shippingFeeAmt) {
        this.shippingFeeAmt = shippingFeeAmt;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getManualAdjustAmt() {
        return manualAdjustAmt;
    }

    public String getRtnAdjustFlagBefore() {
        return rtnAdjustFlagBefore;
    }

    public void setRtnAdjustFlagBefore(String rtnAdjustFlagBefore) {
        this.rtnAdjustFlagBefore = rtnAdjustFlagBefore;
    }

    public String getShippingFeeFlagBefore() {
        return shippingFeeFlagBefore;
    }

    public void setShippingFeeFlagBefore(String shippingFeeFlagBefore) {
        this.shippingFeeFlagBefore = shippingFeeFlagBefore;
    }

    public void setManualAdjustAmt(BigDecimal manualAdjustAmt) {
        this.manualAdjustAmt = manualAdjustAmt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}