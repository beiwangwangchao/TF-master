/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品分配dto.
 * 
 * @author wangchao
 *
 */
@Table(name = "inv_item_hierarchy")
public class InvItemHierarchy extends BaseDTO {

    @Id
    @Column(name = "hierarchy_id")
    private Long hierarchyId;

    @NotNull
    private Long itemId;

    private Long parentItemId;

    @NotEmpty
    private String uomCode;

    @NotNull
    private BigDecimal quantity;

    private String itemNumber;
    private String itemName; // 商品名称

    // 计算库存标记
    private String countStockFlag;

    // 计算库存商品Id
    private Long invCountItemId;

    // 计算库存商品编号
    private String invCountItemNumber;

    // 计算库存商品名称
    private String invCountItemName;

    // 启用批次控制标记
    private String lotControlFlag;

    // 对应创建重新分包的商品包ID
    private Long packageItemId;

    // 对应创建重新分包的库存组织
    private Long organizationId;

    // 对应创建重新分包的库存组织
    private String trxNumber;

    // 单位名称
    private String uomName;

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(Long hierarchyId) {
        this.hierarchyId = hierarchyId;
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

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
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

    public Long getInvCountItemId() {
        return invCountItemId;
    }

    public void setInvCountItemId(Long invCountItemId) {
        this.invCountItemId = invCountItemId;
    }

    public String getInvCountItemNumber() {
        return invCountItemNumber;
    }

    public void setInvCountItemNumber(String invCountItemNumber) {
        this.invCountItemNumber = invCountItemNumber;
    }

    public String getInvCountItemName() {
        return invCountItemName;
    }

    public void setInvCountItemName(String invCountItemName) {
        this.invCountItemName = invCountItemName;
    }

    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber;
    }

    public String getCountStockFlag() {
        return countStockFlag;
    }

    public void setCountStockFlag(String countStockFlag) {
        this.countStockFlag = countStockFlag;
    }

    public String getLotControlFlag() {
        return lotControlFlag;
    }

    public void setLotControlFlag(String lotControlFlag) {
        this.lotControlFlag = lotControlFlag;
    }
}