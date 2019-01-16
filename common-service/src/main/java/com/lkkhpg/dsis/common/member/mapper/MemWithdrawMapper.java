/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;

/**
 * 会员冲销节余Mapper.
 * 
 * @author frank.li
 */
public interface MemWithdrawMapper {
    int deleteByPrimaryKey(Long withdrawId);

    int insert(MemWithdraw record);

    int insertSelective(MemWithdraw record);

    MemWithdraw selectByPrimaryKey(Long withdrawId);

    List<MemWithdraw> selectByMemberId(Long memberId);

    int updateByPrimaryKeySelective(MemWithdraw record);

    int updateByPrimaryKey(MemWithdraw record);

    List<MemWithdraw> queryWithdraws(Map<String, Object> map);

    int addWithdraw(MemWithdraw memWithdraw);

    long isBalance(Map<String, Object> map);

    long isActive(Map<String, Object> map);

    List<String> getBalanceByMemberId(Map<String, Object> map);
    
    Long getPeriodByNow(Map<String, Object> map);
    
    int updateBalance(Map<String, Object> map);
    
    int updateWithdrawStatus(@Param("memWithdraws")List<MemWithdraw> memWithdraws, @Param("status")String status, @Param("combine")BonusReleaseCombine combine);
    
    int rollbackWithdraw(@Param("bonusFinal")List<BonusFinal> bonusFinal, @Param("status")String status);
    
}