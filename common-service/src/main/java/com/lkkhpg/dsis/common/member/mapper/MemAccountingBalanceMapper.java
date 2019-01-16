/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;

/**
 * 会员资产余额Mapper.
 * 
 * @author frank.li
 */
public interface MemAccountingBalanceMapper {
    int deleteByPrimaryKey(Long accountingBalanceId);

    int insert(MemAccountingBalance record);

    int insertSelective(MemAccountingBalance record);

    MemAccountingBalance selectByPrimaryKey(Long accountingBalanceId);

    List<MemAccountingBalance> selectByMemberId(Long memberId);

    int updateByPrimaryKeySelective(MemAccountingBalance record);

    int updateByPrimaryKey(MemAccountingBalance record);

    /**
     * 查询会员账余额信息.
     * 
     * @param memberId
     *            会员ID
     * @param accountingType
     *            账户类型
     * @return 会员账务余额信息
     */
    MemAccountingBalance selectByMemIdAndAccType(@Param("memberId") Long memberId,
            @Param("accountingType") String accountingType);

    /**
     * 更新会员帐户余额表.
     * 
     * @param memAccountingBalance
     *            会员账务余额信息
     * @return 更新的条数
     */
    int updateMemAccountBalance(MemAccountingBalance memAccountingBalance);

    /**
     * @param map
     *            包含memberID: 会员编号 , accountingType:RB
     * 
     * @return RB总额
     */
    Long getRBBymemberId(Map map);

    /**
     * @param map
     * @return 1代表成功，2代表失败
     */
    int clearRBalance(Map map);

}