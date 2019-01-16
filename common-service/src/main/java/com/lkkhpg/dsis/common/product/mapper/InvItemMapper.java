/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.InvCategoryB;
import com.lkkhpg.dsis.common.product.dto.InvItem;

/**
 * 商品mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(InvItem record);

    int insertSelective(InvItem record);

    InvItem selectByPrimaryKey(Long key);

    int updateByPrimaryKeySelective(InvItem record);

    int updateByPrimaryKey(InvItem record);

    List<InvItem> queryItemAndPrice(InvItem item);

    InvItem getItemById(@Param("itemId") Long itemId);

    InvItem getItemByIdByOrg(@Param("itemId") Long itemId);

    InvItem getItemByIdForOrder(@Param("itemId") Long itemId);

    int publishItem(Long itemId);

    int unPublishItem(Long itemId);

    List<InvItem> queryProductByCategory(InvCategoryB category);

    List<InvItem> queryItemFilterByCategory(InvItem item);

    List<InvItem> querySelective(InvItem item);

    List<InvItem> queryItemsByParams(Map<String, Object> map);

    List<InvItem> queryItemsByOrganizationId(InvItem item);

    List<InvItem> queryItem(InvItem item);

    List<InvItem> queryItemByOrg(InvItem item);

    List<InvItem> queryItemForOrder(InvItem item);

    Long getNextItemKey();

    List<InvItem> queryItemWithOrg(InvItem item);

    InvItem getInvCountItem(Long organizationId, Long itemId);

    List<InvItem> queryItemBs(@Param("itm") InvItem record, @Param("orgId") Long invOrgId, @Param("trxType") String trxType);

    InvItem getItemUomCode(Long itemId);

    List<InvItem> queryItemBs(InvItem record);

    List<InvItem> queryTransferItems(@Param(value = "invItem") InvItem invItem, @Param(value = "outOrg") Long outOrg,
            @Param(value = "inOrg") Long inOrg);

    List<InvItem> queryItemsByOrgId(InvItem item);

    List<InvItem> queryItemsByCountStock(InvItem item);

    Integer queryItemByItemNumber(@Param("itemNumber") String itemNumber);

    List<InvItem> queryItemsOfUnitConvert(InvItem invItem);
    
    List<InvItem> queryByPoHeader(InvItem invItem);
    
    int queryItemByCodeAndId(@Param(value = "itemId") Long itemId, @Param(value = "itemNumber") String itemNumber);
    
    List<InvItem> queryItemLov(@Param("itemNumber") String itemNumber, @Param("itemName") String itemName, 
            @Param("redeemFlag") String redeemFlag, @Param("starterAid") String starterAid, @Param("organizationId") Long organizationId,
            @Param("categoryId") Long categoryId, @Param("trxType") String trxType, @Param("transferOrgId") Long transferOrgId);

    Integer getItemCountBySalesOrgId(Map<String, Object> map);

    Long queryItemInvOrgEnabled(@Param(value = "itemId") Long itemId, @Param(value = "invOrgId") Long invOrgId);

    Long queryPackgInvOrgEnabled(@Param(value = "itemId") Long itemId, @Param(value = "invOrgId") Long invOrgId);
    
    List<InvItem> getItemByItemNumber(InvItem invItem);
}