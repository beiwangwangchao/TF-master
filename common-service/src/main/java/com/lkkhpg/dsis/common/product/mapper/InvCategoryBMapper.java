/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.product.dto.InvCategoryB;

/**
 * 商品类别Mapper.
 * 
 * @author chenjingxiong
 */
public interface InvCategoryBMapper {

    int deleteByPrimaryKey(InvCategoryB invCategoryB);

    Long beforeInsert(InvCategoryB invCategoryB);

    int insert(InvCategoryB record);

    int insertSelective(InvCategoryB record);

    InvCategoryB selectByPrimaryKey(Long categoryId);

    int updateByPrimaryKeySelective(InvCategoryB record);

    int updateByPrimaryKey(InvCategoryB record);

    List<InvCategoryB> queryCategory(InvCategoryB invCategoryB);

    List<InvCategoryB> queryTopCategory();

    List<InvCategoryB> queryChildrenCategory(InvCategoryB invCategoryB);

    List<InvCategoryB> queryBottomCategory(String itemType);

    List<InvCategoryB> queryAllCategoryItem();

    List<InvCategoryB> queryCategorySelection();
    
    List<InvCategoryB> queryCategorysByInvOrgId(Map<String, Object> map);
    
    List<InvCategoryB> queryAllBottomCategory();
    
    List<InvCategoryB> queryCategoriesByParent(InvCategoryB invCategoryB);
    
    List<InvCategoryB> queryParentCates();
}