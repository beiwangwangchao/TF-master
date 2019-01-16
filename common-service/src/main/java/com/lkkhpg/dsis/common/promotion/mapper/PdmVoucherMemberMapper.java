/*
 *
 */
package com.lkkhpg.dsis.common.promotion.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.promotion.dto.PdmVoucherMember;
/**
 * 优惠券会员Mapper.
 * @author hanrui.huang
 *
 */
public interface PdmVoucherMemberMapper {

    int deleteByPrimaryKey(Long voucherMemberId);

    int insert(PdmVoucherMember record);

    int insertSelective(PdmVoucherMember record);

    PdmVoucherMember selectByPrimaryKey(Long voucherMemberId);

    int updateByPrimaryKeySelective(PdmVoucherMember record);

    int updateByPrimaryKey(PdmVoucherMember record);

    List<PdmVoucherMember> qureyMember(PdmVoucherMember record);
    
    PdmVoucherMember qureyByMemberId(PdmVoucherMember record);
}