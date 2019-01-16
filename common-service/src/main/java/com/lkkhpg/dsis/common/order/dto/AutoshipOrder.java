/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.common.config.dto.SpmCurrency;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 自动订货单查询.
 * 
 * @author HuangJiaJing
 *
 */
public class AutoshipOrder extends BaseDTO {

    @Id
    @Column(name = "AUTOSHIP_ID", unique = true)
    private Long autoshipId;

    @NotNull
    private Long salesOrgId;

    @NotEmpty
    private String autoshipNumber;

    @NotEmpty
    private String autoshipStatus;

    @NotNull
    private Date autoshipCreateDate;

    @NotNull
    private Long memberId;

    @NotNull
    private Long createUserId;

    private String createUserName;

    private String executeType;

    private String executeDate;
    // 最近执行时间
    private Date latestExecuteDate;
    // 批次号
    private String latestBatchNumber;

    public Date getLatestExecuteDate() {
        return latestExecuteDate;
    }

    public void setLatestExecuteDate(Date latestExecuteDate) {
        this.latestExecuteDate = latestExecuteDate;
    }

    public String getLatestBatchNumber() {
        return latestBatchNumber;
    }

    public void setLatestBatchNumber(String latestBatchNumber) {
        this.latestBatchNumber = latestBatchNumber;
    }

    /**
     * 最后一次执行状态.
     */
    private String executeStatus;

    private String currency;
    private BigDecimal orderAmt;
    private BigDecimal discountAmt;
    private BigDecimal taxAmt;
    private String remark;
    private String deliveryType;
    private Long deliveryLocationId;
    private String deliveryTo;
    private Long billingLocationId;
    private String billingTo;
    private Long creditCardId;
    private Date creationDate;

    private String memberNumber;
    private BigDecimal pvSum;
    private String userName;
    private String marketName;
    private Long marketId;
    private BigDecimal salesScore;

    private Date autoshipCreateDateForm;

    private Date autoshipCreateDateTo;

    @Children
    private List<AutoshipLine> lines;

    @Children
    private SalesLogistics logistics;

    @Children
    private CreditCard creditCard;

    @Children
    private List<MemCard> memCard;

    @Children
    private List<SalesSites> salesSites;

    private SpmSalesOrganization spmSalesOrganization;

    private SpmMarket spmMarket;

    private SpmCurrency spmCurrency;

    private Member member;
    
    private String lastUpdatedName;
    
    private String memberName;

    private String lastOrderNumber;
    
    private Date lastUpdateDate;
    
    private Long lastOrderId;
    
    private List<String> autoShipStatusList;

    public List<String> getAutoShipStatusList() {
        return autoShipStatusList;
    }

    public void setAutoShipStatusList(List<String> autoShipStatusList) {
        this.autoShipStatusList = autoShipStatusList;
    }

    public Date getAutoshipCreateDateForm() {
        return autoshipCreateDateForm;
    }

    public void setAutoshipCreateDateForm(Date autoshipCreateDateForm) {
        this.autoshipCreateDateForm = autoshipCreateDateForm;
    }

    public Date getAutoshipCreateDateTo() {
        return autoshipCreateDateTo;
    }

    public void setAutoshipCreateDateTo(Date autoshipCreateDateTo) {
        this.autoshipCreateDateTo = autoshipCreateDateTo;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getPvSum() {
        return pvSum;
    }

    public void setPvSum(BigDecimal pvSum) {
        this.pvSum = pvSum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAutoshipId() {
        return autoshipId;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public void setAutoshipId(Long autoshipId) {
        this.autoshipId = autoshipId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getAutoshipNumber() {
        return autoshipNumber;
    }

    public void setAutoshipNumber(String autoshipNumber) {
        this.autoshipNumber = autoshipNumber;
    }

    public String getAutoshipStatus() {
        return autoshipStatus;
    }

    public void setAutoshipStatus(String autoshipStatus) {
        this.autoshipStatus = autoshipStatus;
    }

    public Date getAutoshipCreateDate() {
        return autoshipCreateDate;
    }

    public void setAutoshipCreateDate(Date autoshipCreateDate) {
        this.autoshipCreateDate = autoshipCreateDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
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

    public BigDecimal getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        this.taxAmt = taxAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getDeliveryLocationId() {
        return deliveryLocationId;
    }

    public void setDeliveryLocationId(Long deliveryLocationId) {
        this.deliveryLocationId = deliveryLocationId;
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public Long getBillingLocationId() {
        return billingLocationId;
    }

    public void setBillingLocationId(Long billingLocationId) {
        this.billingLocationId = billingLocationId;
    }

    public String getBillingTo() {
        return billingTo;
    }

    public void setBillingTo(String billingTo) {
        this.billingTo = billingTo;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public BigDecimal getSalesScore() {
        return salesScore;
    }

    public void setSalesScore(BigDecimal salesScore) {
        this.salesScore = salesScore;
    }

    public List<AutoshipLine> getLines() {
        return lines;
    }

    public void setLines(List<AutoshipLine> lines) {
        this.lines = lines;
    }

    public SalesLogistics getLogistics() {
        return logistics;
    }

    public void setLogistics(SalesLogistics logistics) {
        this.logistics = logistics;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public SpmSalesOrganization getSpmSalesOrganization() {
        return spmSalesOrganization;
    }

    public void setSpmSalesOrganization(SpmSalesOrganization spmSalesOrganization) {
        this.spmSalesOrganization = spmSalesOrganization;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public SpmMarket getSpmMarket() {
        return spmMarket;
    }

    public void setSpmMarket(SpmMarket spmMarket) {
        this.spmMarket = spmMarket;
    }

    public SpmCurrency getSpmCurrency() {
        return spmCurrency;
    }

    public void setSpmCurrency(SpmCurrency spmCurrency) {
        this.spmCurrency = spmCurrency;
    }

    public List<SalesSites> getSalesSites() {
        return salesSites;
    }

    public void setSalesSites(List<SalesSites> salesSites) {
        this.salesSites = salesSites;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public List<MemCard> getMemCard() {
        return memCard;
    }

    public void setMemCard(List<MemCard> memCard) {
        this.memCard = memCard;
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLastUpdatedName() {
        return lastUpdatedName;
    }

    public void setLastUpdatedName(String lastUpdatedName) {
        this.lastUpdatedName = lastUpdatedName;
    }

    public String getLastOrderNumber() {
        return lastOrderNumber;
    }

    public void setLastOrderNumber(String lastOrderNumber) {
        this.lastOrderNumber = lastOrderNumber;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastOrderId() {
        return lastOrderId;
    }

    public void setLastOrderId(Long lastOrderId) {
        this.lastOrderId = lastOrderId;
    }
    
    
    
}
