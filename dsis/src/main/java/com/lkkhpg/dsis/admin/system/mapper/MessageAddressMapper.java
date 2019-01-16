/*
 *
 */
package com.lkkhpg.dsis.admin.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * @author shiliyan
 *
 */
public interface MessageAddressMapper {
    Member selectByMemberId(Long memberId);

    List<Member> selectAllMember();

    User selectUserByPrimaryKey(Long userId);

    List<User> selectAllUser();

    List<User> selectAllUserOfRole(Long roleId);
}
