/*
 *
 */
package com.lkkhpg.dsis.common.promotion.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherItem;

/**
 * 优惠券商品Mapper.
 * @author hanrui.huang
 *
 */
public interface PdmVoucherItemMapper {
    
    int deleteByPrimaryKey(Long voucherItemId);

    int insert(PdmVoucherItem record);

    int insertSelective(PdmVoucherItem record);

    PdmVoucherItem selectByPrimaryKey(Long voucherItemId);

    int updateByPrimaryKeySelective(PdmVoucherItem record);

    int updateByPrimaryKey(PdmVoucherItem record);

    List<PdmVoucherItem> qureyItem(PdmVoucherItem record);
}