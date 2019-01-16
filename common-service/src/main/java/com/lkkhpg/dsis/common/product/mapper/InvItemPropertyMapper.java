/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.InvItemProperty;
import com.lkkhpg.dsis.common.product.dto.InvItemPropertyV;

/**
 * 库存属性参数mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemPropertyMapper {
    int deleteByPrimaryKey(Long itemPropertyId);

    int insert(InvItemProperty record);

    int insertSelective(InvItemProperty record);

    InvItemProperty selectByPrimaryKey(Long itemPropertyId);

    int updateByPrimaryKeySelective(InvItemProperty record);

    int updateByPrimaryKey(InvItemProperty record);

    // int updateByPropertyType(InvItemProperty record);

    List<InvItemProperty> queryInvProperty(InvItemProperty record);

    List<InvItemProperty> queryInvItemProperties(InvItemProperty record);

    int deleteByItemIdAndOrgId(InvItemProperty record);

    /**
     * 通过商品ID和库存组织ID查询商品属性.
     * 
     * @param itemId
     *            商品id
     * @param orgId
     *            库存组织id
     * @return 商品属性集合
     */
    InvItemPropertyV queryItemPropertyVByItemIdAndOrgId(@Param(value = "itemId") Long itemId,
            @Param(value = "orgId") Long orgId);

    /**
     * 查询商品在改库存是否分配.
     * 
     * @param itemId
     *            商品ID
     * @param orgId
     *            组织ID
     * @return 结果
     */
    String queryInvEnabledFlag(@Param(value = "itemId") Long itemId, @Param(value = "orgId") Long orgId);

}