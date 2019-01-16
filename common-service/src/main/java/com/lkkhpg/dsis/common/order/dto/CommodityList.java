/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;

/**
 * 商品清单dto.
 *
 * @author houmin
 */
public class CommodityList extends SalesOrder {

    private static final long serialVersionUID = 1L;

    // 库存组织
    private String defaultInvOrgId;

    // 产品销售类型
    private String itemSalesType;

    // 商品Id
    private Long itemId;

    // 货号
    private String itemNumber;

    // 商品名称
    private String itemName;

    // 商品类型
    private String itemType;

    // 商品数量
    private BigDecimal quantity;

    // PV值
    private BigDecimal pv;

    // 原价
    private BigDecimal unitOrigPrice;

    // 折扣后价格
    private BigDecimal unitSellingPrice;

    // 优惠名称
    private String voucherName;

    // 现有积分
    private BigDecimal salesPoint;

    // 兑换积分
    private BigDecimal unitRedeemPoint;

    // 兑换总积分
    private BigDecimal redeemPoint;

    // 运费
    private BigDecimal shippingFee;

    // 支付调整总额
    private BigDecimal adjustmentAmountCount;

    // 订单优惠总额
    private BigDecimal discountValueSum;

    // 来源
    private String sourceKey;

    // 来源类型
    private String sourceType;

    // 金额=数量*销售价格
    private BigDecimal amount;

    // 创建订单用户类型
    private String userType;

    // 订单会员编号
    private String memberCode;

    // 会员姓名
    private String memberName;

    // 当前PV(GDS)
    private BigDecimal currentPv;
    /**
     * 销售组织编号.
     */
    private String salesOrgCode;

    // 账户余额
    private List<MemAccountingBalance> memAccountingBalances;

    public String getDefaultInvOrgId() {
        return defaultInvOrgId;
    }

    public void setDefaultInvOrgId(String defaultInvOrgId) {
        this.defaultInvOrgId = defaultInvOrgId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemSalesType() {
        return itemSalesType;
    }

    public void setItemSalesType(String itemSalesType) {
        this.itemSalesType = itemSalesType;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public BigDecimal getUnitOrigPrice() {
        return unitOrigPrice;
    }

    public void setUnitOrigPrice(BigDecimal unitOrigPrice) {
        this.unitOrigPrice = unitOrigPrice;
    }

    public BigDecimal getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSalesPoint() {
        return salesPoint;
    }

    public void setSalesPoint(BigDecimal salesPoint) {
        this.salesPoint = salesPoint;
    }

    public BigDecimal getUnitRedeemPoint() {
        return unitRedeemPoint;
    }

    public void setUnitRedeemPoint(BigDecimal unitRedeemPoint) {
        this.unitRedeemPoint = unitRedeemPoint;
    }

    public BigDecimal getRedeemPoint() {
        return redeemPoint;
    }

    public void setRedeemPoint(BigDecimal redeemPoint) {
        this.redeemPoint = redeemPoint;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getAdjustmentAmountCount() {
        return adjustmentAmountCount;
    }

    public void setAdjustmentAmountCount(BigDecimal adjustmentAmountCount) {
        this.adjustmentAmountCount = adjustmentAmountCount;
    }

    public BigDecimal getDiscountValueSum() {
        return discountValueSum;
    }

    public void setDiscountValueSum(BigDecimal discountValueSum) {
        this.discountValueSum = discountValueSum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<MemAccountingBalance> getMemAccountingBalances() {
        return memAccountingBalances;
    }

    public void setMemAccountingBalances(List<MemAccountingBalance> memAccountingBalances) {
        this.memAccountingBalances = memAccountingBalances;
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

    public BigDecimal getCurrentPv() {
        return currentPv;
    }

    public void setCurrentPv(BigDecimal currentPv) {
        this.currentPv = currentPv;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

}
