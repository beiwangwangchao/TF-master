/*
 *
 */
package com.lkkhpg.dsis.mws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * MWS商品DTO.
 *
 * @author LIUXIAWANG
 */
public class Product extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID.
     */
    private Long itemId;

    /**
     * 商品编号.
     */
    private String itemNumber;

    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 商品描述.
     */
    private String description;

    /**
     * 商品类型.
     */
    @JsonInclude(Include.NON_NULL)
    private String itemType;

    /**
     * 商品数量.
     */
    @JsonInclude(Include.NON_NULL)
    private Long itemAmount;

    /**
     * 商品图片.
     */
    @JsonInclude(Include.NON_NULL)
    private List<SysFile> itemImg;
    
    /**
     * 商品发布状态.
     */
    private String publishStatus;

    /**
     * 税.
     */
    private BigDecimal tax;

    /**
     * 商品有效性.
     */
    @JsonInclude(Include.NON_NULL)
    private String functionArea;

    /**
     * 计量单位.
     */
    @JsonInclude(Include.NON_NULL)
    private String uomCode;

    /**
     * 计量单位.
     */
    private String uomName;

    /**
     * 商品单价.
     */
    private BigDecimal disAmt;

    /**
     * 商品原价.
     */
    private BigDecimal orginalAmt;

    /**
     * 商品最小购买量.
     */
    private Long minOrderQuantity;

    /**
     * 商品PV.
     */
    private BigDecimal pvAmt;

    /**
     * 商品类型id.
     */
    @JsonInclude(Include.NON_NULL)
    private Long categoryId;

    /**
     * 商品类型名称.
     */
    @JsonInclude(Include.NON_NULL)
    private String categoryName;

    /**
     * 商品类型idList.
     */
    @JsonInclude(Include.NON_NULL)
    private List<InvCategoryB> categoryIdList;

    /**
     * orgId.
     */
    @JsonInclude(Include.NON_NULL)
    private Long orgId;

    /**
     * 兑换商品所需积分.
     */
    @JsonInclude(Include.NON_NULL)
    private Long pvBuy;

    /**
     * 排列.
     */
    @JsonInclude(Include.NON_NULL)
    private String sortBy;

    /**
     * 积分兑换.
     */
    @JsonInclude(Include.NON_NULL)
    private String redeemAbility;

    /**
     * 入会套装.
     */
    @JsonInclude(Include.NON_NULL)
    private String salesAid;

    /**
     * 币种类型.
     */
    @JsonInclude(Include.NON_NULL)
    private String currencyCode;

    /**
     * 币种符号.
     */
    @JsonInclude(Include.NON_NULL)
    private String currencySymbol;

    /**
     * 商品详情.
     */
    @JsonInclude(Include.NON_NULL)
    private byte[] shfwContentB;
    private String shfwContent;


    /**
     * 规格参数.
     */
    @JsonInclude(Include.NON_NULL)
    private byte[] ggcsContentB;
    private String ggcsContent;

    /**
     * 售后服务.
     */
    @JsonInclude(Include.NON_NULL)
    private byte[] bcfsContentB;
    private String bcfsContent;

    /**
     * 使用说明.
     */
    @JsonInclude(Include.NON_NULL)
    private byte[] sysmContentB;
    private String sysmContent;

    /**
     * 注意事项.
     */
    @JsonInclude(Include.NON_NULL)
    private byte[] spjjContentB;
    private String spjjContent;

    /**
     * 保存方式.
     */
    @JsonInclude(Include.NON_NULL)
    private byte[] zysxContentB;
    private String zysxContent;
    
    private String hide;
    
    private String hideProductIntroduce;
    
    private String hideUseInstruction;
    
    private String hideStandardParam;
    
    private String hideDose;
    
    /**
     * 是否启用税标识.
     */
    private String sign;
    /**
     * 排序标识.
     */
    private Long sortNum;

    /**
     * add by furong.tang
     * 库存组织
     */
    private Long ivnOrgId;

    /**
     * add by 8327
     * 价格从
     */
    private String priceFrom;

    /**
     * add by 8327
     * 价格至
     */
    private String priceTo;
    /**
     *物流重量kg
     */
    private String attribute15;

    private  BigDecimal dis_kg;

    public BigDecimal getDis_kg() {
        return dis_kg;
    }

    public void setDis_kg(BigDecimal dis_kg) {
        this.dis_kg = dis_kg;
    }

    @Override
    public String getAttribute15() {
        return attribute15;
    }

    @Override
    public void setAttribute15(String attribute15) {
        this.attribute15 = attribute15;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }

    public Long getIvnOrgId() {
        return ivnOrgId;
    }

    public void setIvnOrgId(Long ivnOrgId) {
        this.ivnOrgId = ivnOrgId;
    }


    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getOrginalAmt() {
        return orginalAmt;
    }

    public void setOrginalAmt(BigDecimal orginalAmt) {
        this.orginalAmt = orginalAmt;
    }

    public String getFunctionArea() {
        return functionArea;
    }

    public void setFunctionArea(String functionArea) {
        this.functionArea = functionArea;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Long minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getPvBuy() {
        return pvBuy;
    }

    public void setPvBuy(Long pvBuy) {
        this.pvBuy = pvBuy;
    }

    public Long getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Long itemAmount) {
        this.itemAmount = itemAmount;
    }

    public List<SysFile> getItemImg() {
        return itemImg;
    }

    public void setItemImg(List<SysFile> itemImg) {
        this.itemImg = itemImg;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getRedeemAbility() {
        return redeemAbility;
    }

    public void setRedeemAbility(String redeemAbility) {
        this.redeemAbility = redeemAbility;
    }

    public String getSalesAid() {
        return salesAid;
    }

    public void setSalesAid(String salesAid) {
        this.salesAid = salesAid;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<InvCategoryB> getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(List<InvCategoryB> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(BigDecimal disAmt) {
        this.disAmt = disAmt;
    }

    public BigDecimal getPvAmt() {
        return pvAmt;
    }

    public void setPvAmt(BigDecimal pvAmt) {
        this.pvAmt = pvAmt;
    }

    public byte[] getShfwContentB() {
        return shfwContentB;
    }

    public void setShfwContentB(byte[] shfwContent) {
        this.shfwContentB = shfwContent;
        this.setShfwContent(new String(shfwContent));
    }

    public byte[] getGgcsContentB() {
        return ggcsContentB;
    }

    public void setGgcsContentB(byte[] ggcsContent) {
        this.ggcsContentB = ggcsContent;
        this.setGgcsContent(new String(ggcsContent));
    }

    public void setGgcsContent(String ggcsContent) {
        this.ggcsContent = ggcsContent;
        
    }

    public String getGgcsContent() {
        return ggcsContent;
    }

    public byte[] getBcfsContentB() {
        return bcfsContentB;
    }

    public void setBcfsContentB(byte[] bcfsContent) {
        this.bcfsContentB = bcfsContent;
        this.setBcfsContent(new String(bcfsContent));
    }

    public byte[] getSysmContentB() {
        return sysmContentB;
    }

    public void setSysmContentB(byte[] sysmContent) {
        this.sysmContentB = sysmContent;
        this.setSysmContent(new String(sysmContent));
        
    }

    public byte[] getSpjjContentB() {
        return spjjContentB;
    }

    public void setSpjjContentB(byte[] spjjContent) {
        this.spjjContentB = spjjContent;
        this.setSpjjContent(new String(spjjContent));
    }

    public byte[] getZysxContentB() {
        return zysxContentB;
    }

    public void setZysxContentB(byte[] zysxContent) {
        this.zysxContentB = zysxContent;
        this.setZysxContent(new String(zysxContent));
    }
    

    /**
     * @return the shfwContent
     */
    public String getShfwContent() {
        return shfwContent;
    }

    /**
     * @param shfwContent the shfwContent to set
     */
    public void setShfwContent(String shfwContent) {
        this.shfwContent = shfwContent;        
    }

    /**
     * @return the bcfsContent
     */
    public String getBcfsContent() {
        return bcfsContent;
    }

    /**
     * @param bcfsContent the bcfsContent to set
     */
    public void setBcfsContent(String bcfsContent) {
        this.bcfsContent = bcfsContent;
    }

    /**
     * @return the sysmContent
     */
    public String getSysmContent() {
        return sysmContent;
    }

    /**
     * @param sysmContent the sysmContent to set
     */
    public void setSysmContent(String sysmContent) {
        this.sysmContent = sysmContent;
    }

    /**
     * @return the spjjContent
     */
    public String getSpjjContent() {
        return spjjContent;
    }

    /**
     * @param spjjContent the spjjContent to set
     */
    public void setSpjjContent(String spjjContent) {
        this.spjjContent = spjjContent;
    }

    /**
     * @return the zysxContent
     */
    public String getZysxContent() {
        return zysxContent;
    }

    /**
     * @param zysxContent the zysxContent to set
     */
    public void setZysxContent(String zysxContent) {
        this.zysxContent = zysxContent;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public String getHideProductIntroduce() {
        return hideProductIntroduce;
    }

    public void setHideProductIntroduce(String hideProductIntroduce) {
        this.hideProductIntroduce = hideProductIntroduce;
    }

    public String getHideUseInstruction() {
        return hideUseInstruction;
    }

    public void setHideUseInstruction(String hideUseInstruction) {
        this.hideUseInstruction = hideUseInstruction;
    }

    public String getHideStandardParam() {
        return hideStandardParam;
    }

    public void setHideStandardParam(String hideStandardParam) {
        this.hideStandardParam = hideStandardParam;
    }

    public String getHideDose() {
        return hideDose;
    }

    public void setHideDose(String hideDose) {
        this.hideDose = hideDose;
    }
    
}