/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * @author wuyichu
 */
public interface UserMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(User user);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);

    List<User> selectUsers(User user);

    Long selectUserIdByAccountId(Long accountId);

    Long selectAccountIdByUserId(Long userId);

    User selectUserByAccountId(Long accountId);

    List<User> selectByIdList(List<Long> userIds);

    User selectUserByLoginName(User user);

    List<CodeValue>  getUserNameByCode();

    // 根据登录名查询用户信息 add by 2017/08/04
    List<User> queryUserInfoByLoginName(List<String> loginName);
}