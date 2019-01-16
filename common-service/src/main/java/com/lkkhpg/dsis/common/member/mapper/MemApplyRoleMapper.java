/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemApplyRole;

/**
 * 会员申请角色变更Mapper.
 * 
 * @author linyuheng
 */
public interface MemApplyRoleMapper {

    Long insert(MemApplyRole memApplyRole);

    List<MemApplyRole> selectAllPendingRecords();

    int updateByPrimaryKeySelective(MemApplyRole memApplyRole);

    List<MemApplyRole> selectAllRecords(MemApplyRole memApplyRole);

    List<MemApplyRole> selectByMemberId(@Param("memberId") Long memberId);
}
