/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.InvItemHierarchy;

/**
 * 商品分配mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemHierarchyMapper {
    int insert(InvItemHierarchy record);

    int insertSelective(InvItemHierarchy record);

    List<InvItemHierarchy> getHierarchyByItemId(@Param(value = "itemId") Long itemId);

    int updateByPrimaryKeySelective(InvItemHierarchy record);

    int deleteByPrimaryKey(InvItemHierarchy key);

    List<InvItemHierarchy> getHierarchyByItemNumber(String itemNumber);

    List<InvItemHierarchy> queryItemsByOrganizationId(InvItemHierarchy record);
    
    List<InvItemHierarchy> selectPkgItemsByPkgItemId(@Param("parentItemId") Long parentItemId);
}