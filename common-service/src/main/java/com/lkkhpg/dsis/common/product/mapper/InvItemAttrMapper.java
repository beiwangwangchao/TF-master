/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvItemAttr;

/**
 * 商品参数mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemAttrMapper {
    int deleteByPrimaryKey(Short itemAttrId);

    int insert(InvItemAttr record);

    int insertSelective(InvItemAttr record);

    InvItemAttr selectByPrimaryKey(Short itemAttrId);

    int updateByPrimaryKeySelective(InvItemAttr record);

    int updateByPrimaryKeyWithBLOBs(InvItemAttr record);

    int updateByPrimaryKey(InvItemAttr record);

    List<InvItemAttr> getItemAttrsByItemId(Long itemId);
}