/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.platform.dto.system.ProfileValue;

public interface ProfileValueMapper {
    int deleteByPrimaryKey(ProfileValue key);

    int deleteByProfileId(ProfileValue key);

    int insert(ProfileValue record);

    int insertSelective(ProfileValue record);

    ProfileValue selectByPrimaryKey(Long profileValueId);

    List<ProfileValue> selectProfileValues(ProfileValue example);

    List<ProfileValue> selectLevelValuesForUser();

    List<ProfileValue> selectLevelValuesForRole();

    List<ProfileValue> selectLevelValuesForGlobal();

    int updateByPrimaryKeySelective(ProfileValue record);

    int updateByPrimaryKey(ProfileValue record);

    /**
     * 根据 profile id 和 user id 查询 该user id 在该配置文件下的所有值， 并按照 LEVEL_ID降序排列
     * 
     * @param profileId
     *            配置文件ID
     * @param userId
     *            用户ID
     * @return 配置文件值List
     */
    List<ProfileValue> selectByProfileIdAndUserId(@Param("profileId") Long profileId, @Param("userId") Long userId);

    /**
     * 根据request和profileName按优先级获取配置文件值.
     * 
     * @param profileName
     *            配置文件
     * @return 按优先级获取配置文件值List
     */
    List<ProfileValue> selectPriorityValues(String profileName);
}