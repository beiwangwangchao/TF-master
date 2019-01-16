/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemRelationship;

/**
 * 会员相关人Mapper.
 * 
 * @author frank.li
 */
public interface MemRelationshipMapper {
    int deleteByPrimaryKey(MemRelationship record);

    int insert(MemRelationship record);

    int insertSelective(MemRelationship record);

    List<MemRelationship> selectByMemberId(Long memberId);

    MemRelationship selectByPrimaryKey(Long memRelId);

    int updateByPrimaryKeySelective(MemRelationship record);

    int updateByPrimaryKey(MemRelationship record);

    String isIdNumberExist(Map<String, Object> map);
    
    int deleteByMemberId(MemRelationship record);
    
    int upgradeMemberId(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);
}