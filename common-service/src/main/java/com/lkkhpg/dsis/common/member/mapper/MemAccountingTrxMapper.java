/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;

/**
 * 会员资产交易Mapper.
 * 
 * @author frank.li
 */
public interface MemAccountingTrxMapper {
    int deleteByPrimaryKey(Long trxId);

    int insert(MemAccountingTrx record);

    int insertSelective(MemAccountingTrx record);

    MemAccountingTrx selectByPrimaryKey(Long trxId);

    List<MemAccountingTrx> selectByMemberId(Map<String, Object> paras);

    int updateByPrimaryKeySelective(MemAccountingTrx record);

    int updateByPrimaryKey(MemAccountingTrx record);

    /**
     * 记录会员资产交易信息.
     * 
     * @param memAccountingTrx
     *            会员资产交易信息
     * @return 插入记录条数
     */
    int insertMemAccTrx(MemAccountingTrx memAccountingTrx);

    /**
     * 更新余额值.
     * 
     * @param trxId
     *            会员资产交易表ID
     * @param balance
     *            余额
     */
    void updateBalanceByTrxId(@Param("trxId") long trxId, @Param("balance") BigDecimal balance);

}