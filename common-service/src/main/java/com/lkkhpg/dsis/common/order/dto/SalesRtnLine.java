/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 退货行DTO.
 * 
 * @author houmin
 *
 */
public class SalesRtnLine extends BaseDTO {
    private Long returnLineId;

    private Long returnId;

    private Long salesOrgId;

    private Long orderLineId;

    private Long itemId;
    /**
     * 父级行ID.
     */
    private Long parentLineId;
    /**
     * 商品编号.
     */
    private String itemNumber;
    /**
     * 商品名称.
     */
    private String itemName;
    /**
     * 商品类型(商品/商品包).
     */
    private String itemType;

    private Long countItemId;

    private String countItemNumber;

    private String countItemName;

    private String uomName;

    private BigDecimal orderQuantity;

    private BigDecimal quantity;
    /**
     * 可退货数量.
     */
    private BigDecimal enabledRtnQuantity;
    /**
     * 销售价格.
     */
    private BigDecimal unitSellingPrice;
    /**
     * 销售积分,
     */
    private BigDecimal unitRedeemPoint;
    /**
     * 订单行上折扣.
     */
    private BigDecimal unitDiscountPrice;
    /**
     * 商品PV.
     */
    private BigDecimal pv;
    /**
     * 返还优惠.
     */
    private BigDecimal returnPromotion;

    private String returnInvFlag;

    /**
     * 批次控制标识.
     */
    private String lotCtrlFlag;

    /**
     * 已发运数量.
     */
    private BigDecimal outstandingQty;

    /**
     * 订单行上折扣类型.
     */
    private String discountType;

    /**
     * 订单行上折扣值.
     */
    private BigDecimal discountValue;

    /**
     * 人工调整金额.
     */
    private BigDecimal adjustmentAmount;

    /**
     * 商品计算库存方式.
     */
    private String countStockFlag;

    /**
     * 商品包库存计算方式.
     */
    private String countType;

    /**
     * 商品是否计税标识.
     */
    private String disableTaxFlag;
    /**
     * 退货-行上折扣率.
     */
    private BigDecimal conversionRate;
    /**
     * 退货批次.
     */
    private List<SalesRtnLot> salesRtnLots;

    /**
     * 商品包明细.
     */
    private List<SalesRtnLine> itemPkgDetail;

    public Long getReturnLineId() {
        return returnLineId;
    }

    public void setReturnLineId(Long returnLineId) {
        this.returnLineId = returnLineId;
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

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(Long parentLineId) {
        this.parentLineId = parentLineId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(Long countItemId) {
        this.countItemId = countItemId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public BigDecimal getUnitRedeemPoint() {
        return unitRedeemPoint;
    }

    public void setUnitRedeemPoint(BigDecimal unitRedeemPoint) {
        this.unitRedeemPoint = unitRedeemPoint;
    }

    public BigDecimal getReturnPromotion() {
        return returnPromotion;
    }

    public void setReturnPromotion(BigDecimal returnPromotion) {
        this.returnPromotion = returnPromotion;
    }

    public String getReturnInvFlag() {
        return returnInvFlag;
    }

    public void setReturnInvFlag(String returnInvFlag) {
        this.returnInvFlag = returnInvFlag == null ? null : returnInvFlag.trim();
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public List<SalesRtnLot> getSalesRtnLots() {
        return salesRtnLots;
    }

    public void setSalesRtnLots(List<SalesRtnLot> salesRtnLots) {
        this.salesRtnLots = salesRtnLots;
    }

    public BigDecimal getEnabledRtnQuantity() {
        return enabledRtnQuantity;
    }

    public void setEnabledRtnQuantity(BigDecimal enabledRtnQuantity) {
        this.enabledRtnQuantity = enabledRtnQuantity;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getCountItemNumber() {
        return countItemNumber;
    }

    public void setCountItemNumber(String countItemNumber) {
        this.countItemNumber = countItemNumber;
    }

    public String getCountItemName() {
        return countItemName;
    }

    public void setCountItemName(String countItemName) {
        this.countItemName = countItemName;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getLotCtrlFlag() {
        return lotCtrlFlag;
    }

    public void setLotCtrlFlag(String lotCtrlFlag) {
        this.lotCtrlFlag = lotCtrlFlag;
    }

    public BigDecimal getOutstandingQty() {
        return outstandingQty;
    }

    public void setOutstandingQty(BigDecimal outstandingQty) {
        this.outstandingQty = outstandingQty;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public String getCountStockFlag() {
        return countStockFlag;
    }

    public void setCountStockFlag(String countStockFlag) {
        this.countStockFlag = countStockFlag;
    }

    public BigDecimal getUnitDiscountPrice() {
        return unitDiscountPrice;
    }

    public void setUnitDiscountPrice(BigDecimal unitDiscountPrice) {
        this.unitDiscountPrice = unitDiscountPrice;
    }

    public List<SalesRtnLine> getItemPkgDetail() {
        return itemPkgDetail;
    }

    public void setItemPkgDetail(List<SalesRtnLine> itemPkgDetail) {
        this.itemPkgDetail = itemPkgDetail;
    }

    public String getDisableTaxFlag() {
        return disableTaxFlag;
    }

    public void setDisableTaxFlag(String disableTaxFlag) {
        this.disableTaxFlag = disableTaxFlag;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

}