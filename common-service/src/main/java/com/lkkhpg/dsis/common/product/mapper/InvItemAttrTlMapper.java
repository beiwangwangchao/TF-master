/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvItemAttrTl;

/**
 * 商品参数多语言mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemAttrTlMapper {
    int deleteByPrimaryKey(InvItemAttrTl record);

    int insert(InvItemAttrTl record);

    int insertSelective(InvItemAttrTl record);

    InvItemAttrTl selectByPrimaryKey(InvItemAttrTl record);

    int updateByPrimaryKeySelective(InvItemAttrTl record);

    int updateByPrimaryKeyWithBLOBs(InvItemAttrTl record);

    int updateByPrimaryKey(InvItemAttrTl record);

    List<InvItemAttrTl> getItemAttrTlsByItemId(Long itemId);

    List<InvItemAttrTl> getItemAttrsByItemId(Long itemId);

    int deleteByLang(InvItemAttrTl record);
    
    int updateAttrContent(InvItemAttrTl record);
    
    List<InvItemAttrTl> selectHideOptions(InvItemAttrTl record);
}