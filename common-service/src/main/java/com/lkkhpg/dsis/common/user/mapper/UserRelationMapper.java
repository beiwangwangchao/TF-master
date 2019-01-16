/*
 *
 */
package com.lkkhpg.dsis.common.user.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.user.dto.UserRelation;

/**
 * 用户关系接口.
 * 
 * @author runbai.chen
 */
public interface UserRelationMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(UserRelation record);

    int update(UserRelation record);

    List<UserRelation> selectUserRelation(UserRelation record);
}
