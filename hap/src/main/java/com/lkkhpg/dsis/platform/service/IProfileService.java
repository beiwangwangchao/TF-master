/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Profile;
import com.lkkhpg.dsis.platform.dto.system.ProfileValue;

/**
 * @author frank.li
 */
public interface IProfileService extends ProxySelf<IProfileService> {

    int LEVEL_USER = 30;
    int LEVEL_ROLE = 20;
    int LEVEL_GLOBAL = 10;

    List<ProfileValue> selectLevelValues(Long levelId, int page, int pagesize);

    List<Profile> selectProfiles(Profile profile, int page, int pagesize);

    List<ProfileValue> selectProfileValues(ProfileValue value);

    Profile createProfile(IRequest request, @StdWho Profile profile);

    boolean batchDelete(IRequest request, List<Profile> Profiles);

    boolean batchDeleteValues(IRequest requestContext, List<ProfileValue> values);

    Profile updateProfile(IRequest requestContext, @StdWho Profile profile);

    List<Profile> batchUpdate(IRequest request, @StdWho List<Profile> Profiles);

    /**
     * 根据配置文件的名字/用户，查找用户在该配置文件下的值. 优先顺序 用户>角色>全局 若当前用户 在 用户、角色、全局三层 均没有值，返回 null
     * @param userId 用戶Id
     * 
     * @param profileName
     *            配置文件名字
     * @return 配置文件值
     */
    String getValueByUserIdAndName(Long userId, String profileName);

    /**
     * 根据request和profileName按优先级获取配置文件值.
     * 
     * @param request
     *            请求上下文
     * @param profileName
     *            配置文件
     * @return 配置文件值
     */
    String getProfileValue(IRequest request, String profileName);

    String getProfileValue(String profileName);
}
