package com.lkkhpg.dsis.integration.payment.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by li.liu06@hand-china.com on 2017/11/27.
 */
public class TianFuPaymentTransaction extends BaseDTO{

    public static final String FAILED = "F";
    public static final String SUCCESS = "S";


    @NotNull
    private String sourceType;//来源类型
    private String status;//商品处于销售成功还是退货
    @NotNull
    private Object message;
    @NotNull
    private String payType;
    @NotNull
    private String outTradeNo;
    @NotNull
    private String partner;
    @NotNull
    private String  subject;
    @NotNull
    private String productFee;
    @NotNull
    private String totalFee;
    @NotNull
    private String transportFee;
    private String payMessage;
    private String imagePath;
    private String imageType;
    private Long  discount;
    private String  remark;
    private String buyerId;
    @NotNull
    private String   subPartner;
    @NotNull
    private String  bankCode;
    @NotNull
    private String  transChannel;
    private String  tradeMode;

    private BigDecimal totalSum;

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public String getPayMessage() {
        return payMessage;
    }

    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProductFee() {
        return productFee;
    }

    public void setProductFee(String productFee) {
        this.productFee = productFee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(String transportFee) {
        this.transportFee = transportFee;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSubPartner() {
        return subPartner;
    }

    public void setSubPartner(String subPartner) {
        this.subPartner = subPartner;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTransChannel() {
        return transChannel;
    }

    public void setTransChannel(String transChannel) {
        this.transChannel = transChannel;
    }

    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }
}
