/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 商品dto.
 * 
 * @author wangchao
 *
 */
@MultiLanguage
@Table(name = "inv_item_b")
public class InvItem extends BaseDTO {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @NotEmpty
    private String itemNumber;

    private String barCode;

    @NotEmpty
    @MultiLanguageField
    @Column(name = "item_name")
    private String itemName;

    //@NotEmpty
    @MultiLanguageField
    @Column(name = "description")
    private String description;

    private String starterAid;

    // 页面使用，防止重名
    @NotEmpty
    private String starterAidField;

    @NotEmpty
    private String countStockFlag;

    private String quantityCountType;

    private Long countItemId;

    private String redeemFlag;

    // 页面使用，防止重名
    @NotEmpty
    private String redeemFlagField;

    private BigDecimal quantityAlert;

    private String expiryAlert;

    @NotNull
    private BigDecimal minOrderQuantity;

    @NotNull
    private Date validateFrom;

    // @NotNull
    private Date validateTo;

    private String orderFlag;

    private String inventoryFlag;

    @NotEmpty
    private String itemType;

    @NotEmpty
    private String uomCode;

    @NotEmpty
    private String publishStatus;

    private String publishStatusDesc;

    @NotEmpty
    private String lotCtrlFlag;

    private Long categoryId;
    // 商品类别
    // 一个商品包可对应多个商品类别
    private List<InvCategoryB> invCategory;
    private String categoryDesc;
    private String categoryIdList;

    // 商品有效性
    private String inStore = ProductConstants.NO;
    private String fax = ProductConstants.NO;
    private String agencyWeb = ProductConstants.NO;
    private String agencyApp = ProductConstants.NO;
    private String autoDeliver = ProductConstants.NO;

    private String channel;

    private String isActive;

    private String salesOrgId;

    private String currency;

    private String priceType;

    private String firstPrice;

    public String getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        this.firstPrice = firstPrice;
    }

    // 库存组织ID
    private Long organizationId;

    private List<PriceListDetail> priceListDetails;

    // 新建or更新
    private String mode;

    /** 单位名称. */
    private String uomName;

    /** 库存量. */
    private BigDecimal quantity;

    /**
     * 重新分包——库存组织ID.
     */
    private Long organization;

    /**
     * 计算商品名称
     */
    private String countItemName;
    /**
     * 计算商品code
     */
    private String countItemNumber;

    private Long packageQuantity;

    private String savePropertyFlag;

    private Long marketId;

    private String marketName;

    private String backOrder;

    private Long sortNum;

    private String functionArea;

    private List<String> functionAreas;

    //w0117
    private Long roleId;

    private Long userId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getFunctionAreas() {
        return functionAreas;
    }

    public void setFunctionAreas(List<String> functionAreas) {
        this.functionAreas = functionAreas;
    }

    public String getFunctionArea() {
        return functionArea;
    }

    public void setFunctionArea(String functionArea) {
        this.functionArea = functionArea;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getBackOrder() {
        return backOrder;
    }

    public void setBackOrder(String backOrder) {
        this.backOrder = backOrder;
    }

    public String getSavePropertyFlag() {
        return savePropertyFlag;
    }

    public void setSavePropertyFlag(String savePropertyFlag) {
        this.savePropertyFlag = savePropertyFlag;
    }

    public Long getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Long packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getLotCtrlFlag() {
        return lotCtrlFlag;
    }

    public void setLotCtrlFlag(String lotCtrlFlag) {
        this.lotCtrlFlag = lotCtrlFlag;
    }

    public String getCountItemNumber() {
        return countItemNumber;
    }

    public void setCountItemNumber(String countItemNumber) {
        this.countItemNumber = countItemNumber;
    }

    public String getStarterAidField() {
        return starterAidField;
    }

    public void setStarterAidField(String starterAidField) {
        this.starterAidField = starterAidField;
    }

    public String getRedeemFlagField() {
        return redeemFlagField;
    }

    public void setRedeemFlagField(String redeemFlagField) {
        this.redeemFlagField = redeemFlagField;
    }

    public String getCountItemName() {
        return countItemName;
    }

    public void setCountItemName(String countItemName) {
        this.countItemName = countItemName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAgencyApp() {
        return agencyApp;
    }

    public void setAgencyApp(String agencyApp) {
        this.agencyApp = agencyApp;
    }

    public String getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(String categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public String getInStore() {
        return inStore;
    }

    public void setInStore(String inStore) {
        this.inStore = inStore;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAgencyWeb() {
        return agencyWeb;
    }

    public void setAgencyWeb(String agencyWeb) {
        this.agencyWeb = agencyWeb;
    }

    public String getAutoDeliver() {
        return autoDeliver;
    }

    public void setAutoDeliver(String autoDeliver) {
        this.autoDeliver = autoDeliver;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStarterAid() {
        return starterAid;
    }

    public void setStarterAid(String starterAid) {
        this.starterAid = starterAid == null ? null : starterAid.trim();
    }

    public String getCountStockFlag() {
        return countStockFlag;
    }

    public void setCountStockFlag(String countStockFlag) {
        this.countStockFlag = countStockFlag == null ? null : countStockFlag.trim();
    }

    public String getQuantityCountType() {
        return quantityCountType;
    }

    public void setQuantityCountType(String quantityCountType) {
        this.quantityCountType = quantityCountType == null ? null : quantityCountType.trim();
    }

    public Long getCountItemId() {
        return countItemId;
    }

    public void setCountItemId(Long countItemId) {
        this.countItemId = countItemId;
    }

    public String getRedeemFlag() {
        return redeemFlag;
    }

    public void setRedeemFlag(String redeemFlag) {
        this.redeemFlag = redeemFlag == null ? null : redeemFlag.trim();
    }

    public BigDecimal getQuantityAlert() {
        return quantityAlert;
    }

    public void setQuantityAlert(BigDecimal quantityAlert) {
        this.quantityAlert = quantityAlert;
    }

    public String getExpiryAlert() {
        return expiryAlert;
    }

    public void setExpiryAlert(String expiryAlert) {
        this.expiryAlert = expiryAlert;
    }

    public BigDecimal getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(BigDecimal minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public Date getValidateFrom() {
        return validateFrom;
    }

    public void setValidateFrom(Date validateFrom) {
        this.validateFrom = validateFrom;
    }

    public Date getValidateTo() {
        return validateTo;
    }

    public void setValidateTo(Date validateTo) {
        this.validateTo = validateTo;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getInventoryFlag() {
        return inventoryFlag;
    }

    public void setInventoryFlag(String inventoryFlag) {
        this.inventoryFlag = inventoryFlag == null ? null : inventoryFlag.trim();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode == null ? null : uomCode.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus == null ? null : publishStatus.trim();
    }

    public String getPublishStatusDesc() {
        return publishStatusDesc;
    }

    public void setPublishStatusDesc(String publishStatusDesc) {
        this.publishStatusDesc = publishStatusDesc;
    }

    public List<InvCategoryB> getInvCategory() {
        return invCategory;
    }

    public void setInvCategory(List<InvCategoryB> invCategory) {
        this.invCategory = invCategory;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public List<PriceListDetail> getPriceListDetails() {
        return priceListDetails;

    }

    public void setPriceListDetails(List<PriceListDetail> priceListDetails) {
        this.priceListDetails = priceListDetails;

    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName == null ? null : uomName.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(String salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Long getOrganization() {
        return organization;
    }

    public void setOrganization(Long organization) {
        this.organization = organization;
    }
}