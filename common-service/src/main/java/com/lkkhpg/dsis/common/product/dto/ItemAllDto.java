/*
 *
 */
package com.lkkhpg.dsis.common.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 商品包保存数据dto.
 * 
 * @author wangchao
 *
 */
public class ItemAllDto implements Serializable {

    @NotNull
    private Long itemId;
    private InvItem itemInfo;
    // 商品包参数语言
    private List<ItemAttrDto> itemAttrsTlsAll;
    private List<ItemAttrDto> itemAttrsTls;
    // 商品包分配组织
    private List<InvItemAssign> itemAssignsAll;
    private List<InvItemAssign> itemAssigns;
    // 包含商品
    private List<InvItemHierarchy> itemIncludesAll;
    private List<InvItemHierarchy> itemIncludes;
    // 价格属性
    private List<PriceListDetail> itemPriceDetail;
    private List<PriceListDetail> itemPriceDetailAll;
    private List<PriceListDetail> itemPriceDetailAllType;
    //初始价格
    private BigDecimal firstPrice;

    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(BigDecimal firstPrice) {
        this.firstPrice = firstPrice;
    }

    public List<PriceListDetail> getItemPriceDetailAllType() {
        return itemPriceDetailAllType;
    }

    public void setItemPriceDetailAllType(List<PriceListDetail> itemPriceDetailAllType) {
        this.itemPriceDetailAllType = itemPriceDetailAllType;
    }

    public List<ItemAttrDto> getItemAttrsTlsAll() {
        return itemAttrsTlsAll;
    }

    public void setItemAttrsTlsAll(List<ItemAttrDto> itemAttrsTlsAll) {
        this.itemAttrsTlsAll = itemAttrsTlsAll;
    }

    public List<ItemAttrDto> getItemAttrsTls() {
        return itemAttrsTls;
    }

    public void setItemAttrsTls(List<ItemAttrDto> itemAttrsTls) {
        this.itemAttrsTls = itemAttrsTls;
    }

    public List<InvItemAssign> getItemAssignsAll() {
        return itemAssignsAll;
    }

    public void setItemAssignsAll(List<InvItemAssign> itemAssignsAll) {
        this.itemAssignsAll = itemAssignsAll;
    }

    public List<InvItemHierarchy> getItemIncludesAll() {
        return itemIncludesAll;
    }

    public void setItemIncludesAll(List<InvItemHierarchy> itemIncludesAll) {
        this.itemIncludesAll = itemIncludesAll;
    }

    public List<PriceListDetail> getItemPriceDetailAll() {
        return itemPriceDetailAll;
    }

    public void setItemPriceDetailAll(List<PriceListDetail> itemPriceDetailAll) {
        this.itemPriceDetailAll = itemPriceDetailAll;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public InvItem getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(InvItem itemInfo) {
        this.itemInfo = itemInfo;
    }

    public List<InvItemAssign> getItemAssigns() {
        return itemAssigns;
    }

    public void setItemAssigns(List<InvItemAssign> itemAssigns) {
        this.itemAssigns = itemAssigns;
    }

    public List<InvItemHierarchy> getItemIncludes() {
        return itemIncludes;
    }

    public void setItemIncludes(List<InvItemHierarchy> itemIncludes) {
        this.itemIncludes = itemIncludes;
    }

    public List<PriceListDetail> getItemPriceDetail() {
        return itemPriceDetail;
    }

    public void setItemPriceDetail(List<PriceListDetail> itemPriceDetail) {
        this.itemPriceDetail = itemPriceDetail;
    }

}
