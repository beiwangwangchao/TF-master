/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemAttribute;

/**
 * 会员属性Mapper.
 * 
 * @author frank.li
 */
public interface MemAttributeMapper {
    int deleteByPrimaryKey(Long attributeId);

    int insert(MemAttribute record);

    int insertSelective(MemAttribute record);

    MemAttribute selectByPrimaryKey(Long attributeId);

    List<MemAttribute> selectByMemberId(Long memberId);

    List<MemAttribute> selectMemAttributes(MemAttribute record);

    int updateByPrimaryKeySelective(MemAttribute record);

    int updateByPrimaryKey(MemAttribute record);
    
    int deleteByMemberId(Long memberId);
    
    int upgradeMemberId(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);
}