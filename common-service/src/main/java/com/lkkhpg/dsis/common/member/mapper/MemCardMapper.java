/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemCard;

/**
 * 会员银行卡Mapper.
 * 
 * @author frank.li
 */
public interface MemCardMapper {
    int deleteByPrimaryKey(MemCard record);

    int insert(MemCard record);

    int insertSelective(MemCard record);

    MemCard selectByPrimaryKey(Long cardId);

    List<MemCard> selectByMemberId(Long memberId);
    
    List<MemCard> selectActiveByMemberId(Long memberId);

    List<MemCard> selectMemCards(MemCard record);

    int updateByPrimaryKeySelective(MemCard record);

    int updateByPrimaryKey(MemCard record);
    
    List<MemCard> selectByMemberIdAndStatus(@Param("memberId")Long memberId, @Param("status")String status);
    
    int deleteByMemberId(MemCard record);
    
    int upgradeMemberId(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);
    
    List<MemCard> selectAllBankByMemberId(Long memberId);
}