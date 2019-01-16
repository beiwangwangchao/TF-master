/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Profile;

/**
 * @author Frank.li
 */
public interface ProfileMapper {
    int deleteByPrimaryKey(Profile record);

    int insert(Profile record);

    int insertSelective(Profile record);

    Profile selectByPrimaryKey(Long profileId);

    List<Profile> selectProfiles(Profile example);

    int updateByPrimaryKeySelective(Profile record);

    int updateByPrimaryKey(Profile record);

    Profile selectByName(String profileName);
}