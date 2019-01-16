/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.platform.annotation.StdWho;

/**
 * 导入会员临时Mapper.
 * 
 * @author mclin
 */
public interface MemberListMapper {
    int deleteByPrimaryKey(Long uniqueId);

    int insert(@StdWho MemberList record);

    int insertSelective(MemberList record);

    MemberList selectByPrimaryKey(Long uniqueId);

    int updateByPrimaryKeySelective(MemberList record);

    int updateByPrimaryKey(MemberList record);

    int insertOrUpdate(MemberList record);

    List<MemberList> getMemberListByGroupId(Long groupId);

    int deleteByGroupId(Long groupId);
    
    Long getNextGroupId();

    Integer getByMemberCode(String memberCode);
    
    List<MemberList> getAllMemberListByGroupId(Long groupId);
    
    List<Member> getAllMember(Long groupId);
}