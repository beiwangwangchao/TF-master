/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.common.constant.TFInterfaceConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.service.IProfileService;
import com.lkkhpg.dsis.platform.service.impl.ProfileServiceImpl;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售订单.
 * 
 * @author wuyichu
 */
@AuditEnabled
@Table(name = "OM_SALES_ORDER")
public class SalesOrder extends BaseDTO {

    @Id
    @Column(name = "HEADER_ID", nullable = false, unique = true)
    private Long headerId;

    @NotNull
    private Long salesOrgId;

    private String orderNumber;

    private String invoiceNumber;

    @NotEmpty
    private String orderStatus;

    private String orderStatusDesc;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @NotNull
    private Date orderDate;

    private String brNo;

    private String gstId;
    
    private String shippingStatus;
    
    private BigDecimal shippingFee;

    public Date getDateB() {
        return dateB;
    }

    public void setDateB(Date dateB) {
        this.dateB = dateB;
    }

    public Date getDateE() {
        return dateE;
    }

    public void setDateE(Date dateE) {
        this.dateE = dateE;
    }

    private Date dateB;
    private Date dateE;

    @NotEmpty
    private String orderType;

    @NotNull
    private Long memberId;

    @NotEmpty
    private String channel;

    private Long serviceCenterId;

    private String serviceCenterName;

    @NotNull
    private Long createUserId;

    private String createUserName;

    private Long periodId;

    private String remark;

    private String deliveryType;

    private Long deliveryLocationId;

    private String deliveryTo;

    private Long billingLocationId;

    private String billingTo;

    private String sourceType;

    private String sourceKey;

    private String currency;

    private BigDecimal orderAmt;

    private BigDecimal discountAmt;

    private BigDecimal taxAmt;

    private BigDecimal actrualPayAmt;

    private String codFlag;

    private String batchNumber;

    private String syncFlag;

    private String firstFlag;

    private BigDecimal salesPoints;

    private String period;

    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date payDate;

    private BigDecimal exchangeAmt;

    private Member member;
    /**
     * 订单PV总计.
     */
    private BigDecimal pvSum;
    /**
     * 实付款总计.
     */
    private BigDecimal actrualPayAmtSum;
    /**
     * 市场ID.
     */
    private Long marketId;

    /**
     * 赠品标记.
     */
    private String autoshipGiftFlag;

    /**
     * 赠品规则标记.
     */
    private String autoshipGiftRuleFlag;

    /**
     * 用户IP.
     */
    private String ip;

    @Children
    private List<SalesLine> lines;

    @Children
    private List<SalesAdjustment> adjustMents;

    @Children
    private List<Voucher> vouchers;

    @Children
    private List<SalesVoucher> salesVouchers;
    
    @Children
    private List<OmMwsOrderPayment> mwsOrderPayments;

    @Children
    private SalesLogistics logistics;

    private SpmTax tax;

    @Children
    private List<SalesSites> salesSites;

    private SpmCurrency spmCurrency;

    private SpmSalesOrganization salesOrganization;

    private String dappSyncFlag;

    private String dappAppNo;

    private String memberName;

    private String memberCode;

    private String gstIdNumber;

    private String phoneNo;

    private String address;

    private String salesOrgName;

    private String freeShipping;
    
    private String internalRemark;
    
    private String staffName;
    
    private String staffNum;

    private List statusList;


    /**
     * 待付款时间
     */
    private Date waitPayDate;
    //核销状态

    @Override
    public String getAttribute15() {
        return attribute15;
    }

    @Override
    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    private String attribute15;

    public Date getWaitPayDate() {
        return waitPayDate;
    }

    public void setWaitPayDate(Date waitPayDate) {
        this.waitPayDate = waitPayDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List getStatusList() {
        return statusList;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType == null ? null : deliveryType.trim();
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo == null ? null : deliveryTo.trim();
    }

    public String getBillingTo() {
        return billingTo;
    }

    public void setBillingTo(String billingTo) {
        this.billingTo = billingTo == null ? null : billingTo.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey == null ? null : sourceKey.trim();
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getDeliveryLocationId() {
        return deliveryLocationId;
    }

    public void setDeliveryLocationId(Long deliveryLocationId) {
        this.deliveryLocationId = deliveryLocationId;
    }

    public Long getBillingLocationId() {
        return billingLocationId;
    }

    public void setBillingLocationId(Long billingLocationId) {
        this.billingLocationId = billingLocationId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public List<SalesLine> getLines() {
        return lines;
    }

    public void setLines(List<SalesLine> lines) {
        this.lines = lines;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public BigDecimal getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        this.taxAmt = taxAmt;
    }

    public String getCodFlag() {
        return codFlag;
    }

    public void setCodFlag(String codFlag) {
        this.codFlag = codFlag;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public List<SalesAdjustment> getAdjustMents() {
        return adjustMents;
    }

    public void setAdjustMents(List<SalesAdjustment> adjustMents) {
        this.adjustMents = adjustMents;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public SalesLogistics getLogistics() {
        return logistics;
    }

    public void setLogistics(SalesLogistics logistics) {
        this.logistics = logistics;
    }

    public BigDecimal getActrualPayAmt() {
        return actrualPayAmt;
    }

    public void setActrualPayAmt(BigDecimal actrualPayAmt) {
        this.actrualPayAmt = actrualPayAmt;
    }

    public SpmCurrency getSpmCurrency() {
        return spmCurrency;
    }

    public void setSpmCurrency(SpmCurrency spmCurrency) {
        this.spmCurrency = spmCurrency;
    }

    public SpmSalesOrganization getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(SpmSalesOrganization salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public Long getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Long serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag == null ? null : syncFlag.trim();
    }

    public String getFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(String firstFlag) {
        this.firstFlag = firstFlag;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getSalesPoints() {
        return salesPoints;
    }

    public void setSalesPoints(BigDecimal salesPoints) {
        this.salesPoints = salesPoints;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public BigDecimal getExchangeAmt() {
        return exchangeAmt;
    }

    public void setExchangeAmt(BigDecimal exchangeAmt) {
        this.exchangeAmt = exchangeAmt;
    }

    public String getDappSyncFlag() {
        return dappSyncFlag;
    }

    public void setDappSyncFlag(String dappSyncFlag) {
        this.dappSyncFlag = dappSyncFlag;
    }

    public String getDappAppNo() {
        return dappAppNo;
    }

    public void setDappAppNo(String dappAppNo) {
        this.dappAppNo = dappAppNo;
    }

    public BigDecimal getPvSum() {
        return pvSum;
    }

    public void setPvSum(BigDecimal pvSum) {
        this.pvSum = pvSum;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public BigDecimal getActrualPayAmtSum() {
        return actrualPayAmtSum;
    }

    public void setActrualPayAmtSum(BigDecimal actrualPayAmtSum) {
        this.actrualPayAmtSum = actrualPayAmtSum;
    }

    public List<SalesSites> getSalesSites() {
        return salesSites;
    }

    public void setSalesSites(List<SalesSites> salesSites) {
        this.salesSites = salesSites;
    }

    public List<SalesVoucher> getSalesVouchers() {
        return salesVouchers;
    }

    public void setSalesVouchers(List<SalesVoucher> salesVouchers) {
        this.salesVouchers = salesVouchers;
    }
    
    public List<OmMwsOrderPayment> getMwsOrderPayments() {
        return mwsOrderPayments;
    }

    public void setMwsOrderPayments(List<OmMwsOrderPayment> mwsOrderPayments) {
        this.mwsOrderPayments = mwsOrderPayments;
    }

    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
    }

    public SpmTax getTax() {
        return tax;
    }

    public void setTax(SpmTax tax) {
        this.tax = tax;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAutoshipGiftFlag() {
        return autoshipGiftFlag;
    }

    public void setAutoshipGiftFlag(String autoshipGiftFlag) {
        this.autoshipGiftFlag = autoshipGiftFlag;
    }

    public String getAutoshipGiftRuleFlag() {
        return autoshipGiftRuleFlag;
    }

    public void setAutoshipGiftRuleFlag(String autoshipGiftRuleFlag) {
        this.autoshipGiftRuleFlag = autoshipGiftRuleFlag;
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

    public String getGstIdNumber() {
        return gstIdNumber;
    }

    public void setGstIdNumber(String gstIdNumber) {
        this.gstIdNumber = gstIdNumber;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalesOrgName() {
        return salesOrgName;
    }

    public void setSalesOrgName(String salesOrgName) {
        this.salesOrgName = salesOrgName;
    }

    public String getBrNo() {
        return brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(String freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getInternalRemark() {
        return internalRemark;
    }

    public void setInternalRemark(String internalRemark) {
        this.internalRemark = internalRemark;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }
    
    

}