/*
 *
 */
package com.lkkhpg.dsis.common.order.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.AuditEnabled;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 销售订单行.
 * 
 * @author wuyichu
 */
@AuditEnabled
@Table(name = "OM_SALES_LINE")
public class SalesLine extends BaseDTO {

    @Id
    @Column(name = "LINE_ID", nullable = false, unique = true)
    private Long lineId;

    private Long headerId;

    @NotNull
    private Long itemId;

    private Long parentItemId;

    private String itemName;

    private String itemNumber;

    private BigDecimal unitOrigPrice;

    private BigDecimal unitDiscountPrice;

    private BigDecimal unitSellingPrice;

    private String itemSalesType;

    private BigDecimal pv;

    @NotNull
    @Max(value = 99999)
    @Min(value = 1)
    private BigDecimal quantity;

    private BigDecimal amount;
    
    private BigDecimal amount1;

    private BigDecimal unitRedeemPoint;

    private BigDecimal redeemPoint;

    private String sourceType;

    private String sourceKey;

    private Long voucherId;
    // 折扣类型
    private String discountType;
    // 折扣值
    private BigDecimal discountValue;

    @NotEmpty
    private String status;

    @NotNull
    private Long defaultInvOrgId;

    // 商品类型
    private String itemType;

    // 父级行id
    private Long parentLineId;

    private Long salesOrgId;

    private String uomCode;

    private BigDecimal unpickQuantity;

    private String syncFlag;

    private Long lineNum;

    // 实际商品ID
    private Long countItemId;

    // 实际商品编号
    private String countItemNumber;

    // 实际商品名称
    private String countItemName;

    // 商品单位名称
    private String uomName;

    // 已发运商品数量
    private Long outstandingQty;

    // 已退货商品数量
    private Long returnQty;

    // 支付调整-优惠金额
    private BigDecimal adjustmentAmount;

    // 商品是否启用批次控制
    private String lotCtrlFlag;

    // 商品计算库存
    private String countStockFlag;

    // 商品包库存计算方式
    private String countType;

    // 商品描述
    private String itemDescription;

    // 行号
    private Long rownum;

    // 行小计pv
    private BigDecimal totalPv;

    private String starterAid;

    private String backOrder;

    private BigDecimal sgt;

    /**
     * 单个商品的税.
     */
    private BigDecimal tax;
    /**
     * 退货单头ID.
     */
    private BigDecimal returnId;
    /**
     * 退货单状态.
     */
    private String returnStatus;
    /**
     * 币种符号.
     */
    private String currency;

    //20180425
    private Long fileId;

    //20180425
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    //20180425
    public Long getFileId() {
        return fileId;
    }

    public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

    /**
     * 订单行商品是否计税.
     */
    private String disableTaxFlag;

    public String getDisableTaxFlag() {
        return disableTaxFlag;
    }

    public void setDisableTaxFlag(String disableTaxFlag) {
        this.disableTaxFlag = disableTaxFlag;
    }

	public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(Long parentItemId) {
        this.parentItemId = parentItemId;
    }

    public BigDecimal getUnitOrigPrice() {
        return unitOrigPrice;
    }

    public void setUnitOrigPrice(BigDecimal unitOrigPrice) {
        this.unitOrigPrice = unitOrigPrice;
    }

    public BigDecimal getUnitDiscountPrice() {
        return unitDiscountPrice;
    }

    public void setUnitDiscountPrice(BigDecimal unitDiscountPrice) {
        this.unitDiscountPrice = unitDiscountPrice;
    }

    public BigDecimal getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public String getItemSalesType() {
        return itemSalesType;
    }

    public void setItemSalesType(String itemSalesType) {
        this.itemSalesType = itemSalesType;
    }

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getDefaultInvOrgId() {
        return defaultInvOrgId;
    }

    public void setDefaultInvOrgId(Long defaultInvOrgId) {
        this.defaultInvOrgId = defaultInvOrgId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public BigDecimal getUnpickQuantity() {
        return unpickQuantity;
    }

    public void setUnpickQuantity(BigDecimal unpickQuantity) {
        this.unpickQuantity = unpickQuantity;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag == null ? null : syncFlag.trim();
    }

    public Long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(Long countItemId) {
        this.countItemId = countItemId;
    }

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
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

    public Long getOutstandingQty() {
        return outstandingQty;
    }

    public void setOutstandingQty(Long outstandingQty) {
        this.outstandingQty = outstandingQty;
    }

    public Long getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Long returnQty) {
        this.returnQty = returnQty;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public String getLotCtrlFlag() {
        return lotCtrlFlag;
    }

    public void setLotCtrlFlag(String lotCtrlFlag) {
        this.lotCtrlFlag = lotCtrlFlag;
    }

    public String getCountStockFlag() {
        return countStockFlag;
    }

    public void setCountStockFlag(String countStockFlag) {
        this.countStockFlag = countStockFlag;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(Long parentLineId) {
        this.parentLineId = parentLineId;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Long getRownum() {
        return rownum;
    }

    public void setRownum(Long rownum) {
        this.rownum = rownum;
    }

    public BigDecimal getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(BigDecimal totalPv) {
        this.totalPv = totalPv;
    }

    public String getStarterAid() {
        return starterAid;
    }

    public void setStarterAid(String starterAid) {
        this.starterAid = starterAid;
    }

    public String getBackOrder() {
        return backOrder;
    }

    public void setBackOrder(String backOrder) {
        this.backOrder = backOrder;
    }

    public BigDecimal getSgt() {
        return sgt;
    }

    public void setSgt(BigDecimal sgt) {
        this.sgt = sgt;
    }

    public BigDecimal getReturnId() {
        return returnId;
    }

    public void setReturnId(BigDecimal returnId) {
        this.returnId = returnId;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

}