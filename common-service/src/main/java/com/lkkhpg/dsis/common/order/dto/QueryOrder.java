/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 包含订单查询条件字段及订单显示字段的 DTO类.
 * 
 * @author gulin
 */
public class QueryOrder extends BaseDTO {
    /*
     * 订单查询条件字段
     */
    private String memberId;
    
    private String memberCode;

    private String orderStatus;

    private String channel;

    private String orderNumber;

    private String orderType;

    private String itemSalesType;

    private Date queryTimeStart;

    private Date queryTimeEnd;

    private String sourceKey;

    private String batchNumber;

    private String deliveryType;

    private String chineseName;
    
    private String englishName;
    /*
     * 订单显示字段（补充）
     */
    private Long headerId;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date orderDate;

    private Long createUserId;

    private BigDecimal pvNumber;

    private Date dispatchTime;

    private BigDecimal paymentAmount;
    
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDate;

    private Long salesOrgId;
    
    private Long marketId;
    
    private String createUserName;
    
    private String returnFlag;
    
    private String transactionNumber;
    
    private String memberName;
    
    private String invoiceNumber;
    
    private String memberRole;

    private Date waitPayDate;

    private BigDecimal orderAmt;

    //核销状态
    private String attribute15;

    @Override
    public String getAttribute15() {
        return attribute15;
    }

    @Override
    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public Date getWaitPayDate() {
        return waitPayDate;
    }

    public void setWaitPayDate(Date waitPayDate) {
        this.waitPayDate = waitPayDate;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getItemSalesType() {
        return itemSalesType;
    }

    public void setItemSalesType(String itemSalesType) {
        this.itemSalesType = itemSalesType;
    }

    public Date getQueryTimeStart() {
        return queryTimeStart;
    }

    public void setQueryTimeStart(Date queryTimeStart) {
        this.queryTimeStart = queryTimeStart;
    }

    public Date getQueryTimeEnd() {
        return queryTimeEnd;
    }

    public void setQueryTimeEnd(Date queryTimeEnd) {
        this.queryTimeEnd = queryTimeEnd;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public BigDecimal getPvNumber() {
        return pvNumber;
    }

    public void setPvNumber(BigDecimal pvNumber) {
        this.pvNumber = pvNumber;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnlishName(String englishName) {
        this.englishName = englishName;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    
}
