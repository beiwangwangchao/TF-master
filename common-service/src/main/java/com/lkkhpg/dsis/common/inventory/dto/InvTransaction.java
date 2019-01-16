/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.inventory.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 库存事务DTO.
 * 
 * @author shenqb
 *
 */
public class InvTransaction extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long trxId;

    /**
     * 库存组织ID.
     */
    @NotNull
    private Long organizationId;

    /**
     * 子库ID,启用子库则需要传值.
     */
    @NotNull
    private Long subinventoryId;

    /**
     * 货位ID,启用货位则需要传值.
     */
    @NotNull
    private Long locatorId;

    /**
     * 商品ID.
     */
    @NotNull
    private Long itemId;

    /**
     * 批次,启用批次控制必须传批次值.
     */
    private String lotNumber;

    /**
     * 事务处理日期.
     */
    @NotNull
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date trxDate;

    /**
     * 事务处理数量,产生库存量则传正数,扣减库存量则传负值数.
     */
    @NotNull
    private BigDecimal trxQty;

    /**
     * 事务处理类型,来源于快码inv.transaction_type.
     */
    @NotEmpty
    private String trxType;

    /**
     * 事务处理原因,来源于出入库或者发运退回的事务处理原因代码.
     */
    private String trxReason;

    /**
     * 事务处理来源类型,记录来源表名.
     */
    @NotNull
    private String trxSourceType;

    /**
     * 事务处理来源键值,记录来源表主键值.
     */
    @NotEmpty
    private String trxSourceKey;

    private BigDecimal unitCost;

    /**
     * 到期日,根据批次到期日传值.
     */
    private Date expiryDate;

    /**
     * 转移库存组织ID.
     */
    private Long transferOrgId;

    /**
     * 转移子库存ID.
     */
    private Long transferSubinvId;

    /**
     * 转移货位ID.
     */
    private Long transferLocatorId;

    /**
     * 转移批次.
     */
    private String transferLot;

    /**
     * 转移失效日期.
     */
    private Date transferLotExpiry;

    private String transferFrom;

    private String transferTo;

    /**
     * 商品编号.
     */
    private String itemNumber;

    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 商品单价.
     */
    private String unitPrice;

    /**
     * 商品总价.
     */
    private String totalPrice;

    private String assistCount;

    private String assistCode;

    /**
     * 事务处理来源参考.
     */
    private String trxSourceReference;

    /**
     * 单位.
     */
    private String uomCode;

    /**
     * 备注.
     */
    private String remark;

    /**
     * 转移组织名称.
     */
    private String transferOrgName;
    
    /**
     * 商品包ID.
     */
    private Long packageItemId;
    
    /**
     * 商品包编码.
     */
    private String packageItemNumber;
    
    private BigDecimal sumQty;

    //退货单号
    private String outRefundNo;

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }


    public BigDecimal getSumQty() {
        return sumQty;
    }

    public void setSumQty(BigDecimal sumQty) {
        this.sumQty = sumQty;
    }

    public String getPackageItemNumber() {
        return packageItemNumber;
    }

    public void setPackageItemNumber(String packageItemNumber) {
        this.packageItemNumber = packageItemNumber;
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public String getTransferOrgName() {
        return transferOrgName;
    }

    public void setTransferOrgName(String transferOrgName) {
        this.transferOrgName = transferOrgName;
    }

    public void setTransferLotExpiry(Date transferLotExpiry) {
        this.transferLotExpiry = transferLotExpiry;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getTrxSourceReference() {
        return trxSourceReference;
    }

    public void setTrxSourceReference(String trxSourceReference) {
        this.trxSourceReference = trxSourceReference;
    }

    public String getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAssistCount() {
        return assistCount;
    }

    public void setAssistCount(String assistCount) {
        this.assistCount = assistCount;
    }

    public String getAssistCode() {
        return assistCode;
    }

    public void setAssistCode(String assistCode) {
        this.assistCode = assistCode;
    }

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getSubinventoryId() {
        return subinventoryId;
    }

    public void setSubinventoryId(Long subinventoryId) {
        this.subinventoryId = subinventoryId;
    }

    public Long getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(Long locatorId) {
        this.locatorId = locatorId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber == null ? null : lotNumber.trim();
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public BigDecimal getTrxQty() {
        return trxQty;
    }

    public void setTrxQty(BigDecimal trxQty) {
        this.trxQty = trxQty;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public String getTrxReason() {
        return trxReason;
    }

    public void setTrxReason(String trxReason) {
        this.trxReason = trxReason == null ? null : trxReason.trim();
    }

    public String getTrxSourceType() {
        return trxSourceType;
    }

    public void setTrxSourceType(String trxSourceType) {
        this.trxSourceType = trxSourceType == null ? null : trxSourceType.trim();
    }

    public String getTrxSourceKey() {
        return trxSourceKey;
    }

    public void setTrxSourceKey(String trxSourceKey) {
        this.trxSourceKey = trxSourceKey == null ? null : trxSourceKey.trim();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getTransferSubinvId() {
        return transferSubinvId;
    }

    public void setTransferSubinvId(Long transferSubinvId) {
        this.transferSubinvId = transferSubinvId;
    }

    public Long getTransferLocatorId() {
        return transferLocatorId;
    }

    public void setTransferLocatorId(Long transferLocatorId) {
        this.transferLocatorId = transferLocatorId;
    }

    public String getTransferLot() {
        return transferLot;
    }

    public void setTransferLot(String transferLot) {
        this.transferLot = transferLot == null ? null : transferLot.trim();
    }

    public Date getTransferLotExpiry() {
        return transferLotExpiry;
    }

    public void seTransferLotExpiry(Date transferLotExpiry) {
        this.transferLotExpiry = transferLotExpiry;
    }

    public Long getTransferOrgId() {
        return transferOrgId;
    }

    public void setTransferOrgId(Long transferOrgId) {
        this.transferOrgId = transferOrgId;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }
    
}
