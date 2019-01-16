package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by miaoyifan on 2017/12/6.
 */
@Table(name = "tf_pay_refund_request")
public class PayRefundRequest extends BaseDTO {
    /**
     * 商户退货单号.
     */
    @Id
    @Column(name = "OUT_REFUND_NO", nullable = false, unique = true)
    private String outRefundNo;
    /**
     * 平台交易单号.
     */
    private String transactionId;
    /**
     * 销售组织
     */
    private String  salesOrgId;

    public String getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(String salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    /**
     * 商品订单号.
     */
    private String outTradeNo;
    /**
     * 商户号1.
     */
    private String partner;
    /**
     * 商品名称.
     */
    private String subject;
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
    /**
     * 是否退款.
     */
    private String isrefund;
    /**
     * 二级商户号.
     */
    private String subpartner;
    /**
     * 退款渠道.
     */
   private String transChannel;
    /**
     * md5加密.
     */
    private String md5Encode;
    /**
     * 退货日期从.
     */
    private Date returnDateFrom;
    /**
     * 退货日期至.
     */
    private Date returnDateTo;
    /**
     * 退货返回单号
     */
    private String refundId;

    private Date creationDate;

    private Long invOrgId;
    //使用信用额度，
    private BigDecimal discountAmt;
    //原订单金额
    private BigDecimal oldTotalFee;
    //退款失败原由
    private String  attribute15;

    @Override
    public String getAttribute15() {
        return attribute15;
    }

    @Override
    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public BigDecimal getOldTotalFee() {
        return oldTotalFee;
    }

    public void setOldTotalFee(BigDecimal oldTotalFee) {
        this.oldTotalFee = oldTotalFee;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public Long getInvOrgId() {
        return invOrgId;
    }

    public void setInvOrgId(Long invOrgId) {
        this.invOrgId = invOrgId;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
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

    public void setOutRefundNo(String outRefundNo){
        this.outRefundNo = outRefundNo;
    }

    public String getOutRefundNo(){
        return outRefundNo;
    }

    public void setTransactionId(String transactionId){
        this.transactionId = transactionId;
    }

    public String getTransactionId(){
        return transactionId;
    }

    public void setOutTradeNo(String outTradeNo){
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo(){
        return outTradeNo;
    }

    public void setPartner(String partner){
        this.partner = partner;
    }

    public String getPartner(){
        return partner;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void setRefundFee(BigDecimal refundFee){
        this.refundFee = refundFee;
    }

    public BigDecimal getRefundFee(){
        return refundFee;
    }

    public void setTotalFee(BigDecimal totalFee){
        this.totalFee = totalFee;
    }

    public BigDecimal getTotalFee(){
        return totalFee;
    }

    public void setTransportFee(BigDecimal transportFee){
        this.transportFee = transportFee;
    }

    public BigDecimal getTransportFee(){
        return transportFee;
    }

    public void setRedundReason(String redundReason){
        this.redundReason = redundReason;
    }

    public String getRedundReason(){
        return redundReason;
    }

    public void setServiceType(String serviceType){
        this.serviceType = serviceType;
    }

    public String getServiceType(){
        return serviceType;
    }

    public void setRefundExplain(String refundExplain){
        this.refundExplain = refundExplain;
    }

    public String getRefundExplain(){
        return refundExplain;
    }

    public void setIsrefund(String isrefund){
        this.isrefund = isrefund;
    }

    public String getIsrefund(){
        return isrefund;
    }

    public void setSubpartner(String subpartner){
        this.subpartner = subpartner;
    }

    public String getSubpartner(){
        return subpartner;
    }

    public String getTransChannel() {
        return transChannel;
    }

    public void setTransChannel(String transChannel) {
        this.transChannel = transChannel;
    }

    public void setMd5Encode(String md5Encode){
        this.md5Encode = md5Encode;
    }

    public String getMd5Encode(){
        return md5Encode;
    }

}
