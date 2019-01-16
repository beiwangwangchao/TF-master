/*
 *
 */
package com.lkkhpg.dsis.common.promotion.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.promotion.dto.Voucher;

/**
 * 优惠券Mapper.
 * 
 * @author frank.li
 */
public interface VoucherMapper {
    int deleteByPrimaryKey(Long voucherId);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(Long voucherId);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);

    /**
     * 根据会员Id查询优惠券.
     * 
     * @param memberId
     *            会员Id
     * @param isUsed
     *            是否使用标记
     * @param scope
     *            使用范围集合
     * @return 返回优惠券集合
     */
    List<Voucher> queryVouchersByMemberId(@Param("memberId") Long memberId, @Param("isUsed") String isUsed,
            @Param("scope") String scope);

    Voucher getVoucherByCode(String voucherCode);

    List<Voucher> queryVouchers(Voucher record);

    Voucher selectByVoucherCode(String voucherCode);

    /**
     * 根据优惠券Id查询已启用且在有效期内的优惠券.
     * 
     * @param voucher
     *            优惠券对象
     * @return 对应的优惠券对象.null-表示不存在
     */
    Voucher selectEnableVoucher(Voucher voucher);

    /**
     * 根据会员查询可用的优惠券(非VIP).
     * 
     * @param memberId
     *            会员ID
     * @param salesOrgId 
     * @return 符合条件的优惠券
     */
    List<Voucher> getMemberVouchers(@Param("memberId") Long memberId,@Param("salesOrgId") Long salesOrgId);

    /**
     * Ecoupon失效.
     * 
     * @param voucher
     *            优惠券对象
     */
    void updateEcouponInvalid(Voucher voucher);

    /**
     * 根据订单ID查询.
     * 
     * @param sourceOrderId
     *            订单ID
     * @return 优惠券对象
     */
    Voucher selectBySourceOrderId(Long sourceOrderId);

    /**
     * 根据会员查询可用的优惠券(VIP).
     * 
     * @param memberId
     *            会员ID
     * @return 符合条件的优惠券
     */
    List<Voucher> getMemberVouchersForVIP(@Param("memberId") Long memberId);
    
    List<Voucher> selectVIPForDapp(Map<String, Object> map);

    /**
     * 根据优惠券截止日期置enableFlag.
     */
    void updateFlagByEndActiveDate();
}