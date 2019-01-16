/*
 *
 */
package com.lkkhpg.dsis.common.promotion.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherGift;

/**
 * 优惠券赠品Mapper.
 * @author hanrui.huang
 *
 */
public interface PdmVoucherGiftMapper {

    int deleteByPrimaryKey(Long voucherGiftId);

    int insert(PdmVoucherGift record);

    int insertSelective(PdmVoucherGift record);

    PdmVoucherGift selectByPrimaryKey(Long voucherGiftId);

    int updateByPrimaryKeySelective(PdmVoucherGift record);

    int updateByPrimaryKey(PdmVoucherGift record);

    List<PdmVoucherGift> qureyGift(PdmVoucherGift record);
}