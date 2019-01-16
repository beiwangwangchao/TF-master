/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvItemCategory;

/**
 * 商品类别关系Mapper.
 * 
 * @author chenjingxiong
 */
public interface InvItemCategoryMapper {

    int deleteByPrimaryKey(InvItemCategory category);

    int insert(InvItemCategory record);

    int insertSelective(InvItemCategory record);

    InvItemCategory selectByPrimaryKey(InvItemCategory category);

    int updateByPrimaryKeySelective(InvItemCategory record);

    int updateByPrimaryKey(InvItemCategory record);

    List<InvItemCategory> getCategoryIdByItemId(Long itemId);
    
    int deleteByItemId(Long itemId);

    int deleteByCategoryId(Long categoryId);

}