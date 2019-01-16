/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;



import com.lkkhpg.dsis.common.product.dto.InvCategoryTl;

/**
 * 商品类别多语言Mapper.
 * 
 * @author wangchao
 *
 */
public interface InvCategoryTlMapper {
    int deleteByPrimaryKey(InvCategoryTl record);

    int insert(InvCategoryTl record);

    int insertSelective(InvCategoryTl record);

    InvCategoryTl selectByPrimaryKey(InvCategoryTl record);

    int updateByPrimaryKeySelective(InvCategoryTl record);

    int updateByPrimaryKey(InvCategoryTl record);

}