/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;

/**
 * 商品单位转换率Mapper接口.
 * 
 * @author mclin
 */
public interface InvUnitConvertMapper {
    int deleteByPrimaryKey(Long convertId);

    int insert(InvUnitConvert record);

    int insertSelective(InvUnitConvert record);

    InvUnitConvert selectByPrimaryKey(Long convertId);

    int updateByPrimaryKeySelective(InvUnitConvert record);

    int updateByPrimaryKey(InvUnitConvert record);

    InvUnitConvert getInvUnitConvert(InvUnitConvert record);

    /**
     * 查询商品转化率.
     * 
     * @param invUnitConvert
     *            商品转化率
     * @return 商品转化率list
     */
    List<InvUnitConvert> selectInvUnitConverts(InvUnitConvert invUnitConvert);
    
    List<InvUnitConvert> queryIucSelection(@Param("itemId") Long itemId, @Param("uomCode") String uomCode);
    
    List<InvUnitConvert> queryInvUnitConverts(@Param("itemId") Long itemId);
    
    int updateUomConvertByItemId(InvUnitConvert invUnitConvert);
    
}