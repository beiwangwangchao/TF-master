/*
 *
 */
package com.lkkhpg.dsis.common.promotion.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;

/**
 * 优惠券分配Mapper.
 * 
 * @author frank.li
 */
public interface VoucherAssignMapper {
    int deleteByPrimaryKey(BigDecimal assignId);

    int insert(VoucherAssign record);

    int insertSelective(VoucherAssign record);

    VoucherAssign selectByPrimaryKey(Long assignId);

    List<VoucherAssign> selectByMemberId(Long memberId);

    int updateByPrimaryKeySelective(VoucherAssign record);

    int updateByPrimaryKey(VoucherAssign record);

    int insertOrUpdateVoucherAssign(@Param("mml") MemberList mml, @Param("voucherId") Long voucherId);

    List<VoucherAssign> queryQuantity(Long voucherId);

    /**
     * 根据条件查询是否存在分配记录.
     * 
     * @param voucheAssign
     *            优惠券分配Dto
     * @return 满足条件的对象
     */
    List<VoucherAssign> selectVoucherAssign(VoucherAssign voucherAssign);

    /**
     * 更新优惠券分配数量.
     * 
     * @param voucherAssign
     *            优惠券分配对象
     * @return 更新的记录数
     */
    int updateVoucherAssignQty(VoucherAssign voucherAssign);

    /**
     * 更新优惠券分配状态.
     * 
     * @param voucherId
     */
    void updateStatus(Long voucherId);

    /**
     * 根据会员信息查找优惠券分配信息.
     * 
     * @param member
     *            会员DTO
     * @return 优惠券分配集合
     */
    List<VoucherAssign> selectVoucherAssignByMember(Member member);
}