/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.GlobalProfile;
import com.lkkhpg.dsis.platform.dto.system.Profile;
import com.lkkhpg.dsis.platform.dto.system.ProfileValue;
import com.lkkhpg.dsis.platform.mapper.system.ProfileMapper;
import com.lkkhpg.dsis.platform.mapper.system.ProfileValueMapper;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * @author frank.li
 */

@Service
@Transactional
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileValueMapper profileValueMapper;

    private Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private IMessagePublisher messagePublisher;
    /*
     * @Override
     * 
     * @Transactional(propagation = Propagation.SUPPORTS) public Profile
     * getProfileById(Long profileId) { return
     * profileMapper.selectByPrimaryKey(profileId); }
     */

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Profile> selectProfiles(Profile profile, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return profileMapper.selectProfiles(profile);
    }

    @Override
    public Profile createProfile(IRequest request, @StdWho Profile profile) {
        // 插入头行
        profileMapper.insert(profile);
        // 判断如果行不为空，则迭代循环插入

        if (profile.getProfileValues() != null) {
            processProfileValues(profile);
        }

        return profile;
    }

    @Override
    public List<ProfileValue> selectProfileValues(ProfileValue value) {
        return profileValueMapper.selectProfileValues(value);
    }

    @Override
    public boolean batchDelete(IRequest request, List<Profile> profiles) {
        // 删除头
        for (Profile profile : profiles) {

            ProfileValue profileValue = new ProfileValue();
            profileValue.setProfileId(profile.getProfileId());
            // delete profile value;
            profileValueMapper.deleteByProfileId(profileValue);
            // delete profile;
            profileMapper.deleteByPrimaryKey(profile);

        }
        return true;
    }

    @Override
    public boolean batchDeleteValues(IRequest requestContext, List<ProfileValue> values) {
        for (ProfileValue value : values) {
            profileValueMapper.deleteByPrimaryKey(value);
        }
        return true;
    }

    /**
     * 批量操作快码行数据.
     *
     * @param profile
     *            头行数据
     */
    private void processProfileValues(Profile profile) {
        for (ProfileValue profileValue : profile.getProfileValues()) {
            if (profileValue.getProfileValueId() == null) {
                profileValue.setProfileId(profile.getProfileId()); // 设置头ID跟行ID一致
                profileValueMapper.insert((profileValue));
            } else if (profileValue.getProfileValueId() != null) {
                profileValueMapper.updateByPrimaryKeySelective(profileValue);
            }
            if (IProfileService.LEVEL_GLOBAL == profileValue.getLevelId()) {
                // 当更改 global 级别的配置文件时, 发出一个消息, 通知其他监听者更新
                messagePublisher.publish("profile",
                        new GlobalProfile(profile.getProfileName(), profileValue.getProfileValue()));
            }
        }
    }

    @Override
    public Profile updateProfile(IRequest request, @StdWho Profile profile) {
        profileMapper.updateByPrimaryKeySelective(profile);
        // 判断如果行不为空，则迭代循环插入
        if (profile.getProfileValues() != null) {
            processProfileValues(profile);
        }
        return profile;
    }

    @Override
    public List<Profile> batchUpdate(IRequest request, @StdWho List<Profile> profiles) {
        for (Profile profile : profiles) {
            if (profile.getProfileId() == null) {
                self().createProfile(request, profile);
            } else if (profile.getProfileId() != null) {
                self().updateProfile(request, profile);
            }
        }
        return profiles;
    }

    /**
     * 根据配置文件的名字/用户，查找用户在该配置文件下的值. 优先顺序 用户>角色>全局 若当前用户 在 用户、角色、全局三层 均没有值，返回
     * null.
     * 
     * @param profileName
     *            配置文件名字
     * @return 配置文件值
     */
    @Override
    public String getValueByUserIdAndName(Long userId, String profileName) {
        // TODO Auto-generated method stub

        Profile profile = profileMapper.selectByName(profileName);
        if (profile == null) {
            return null;
        }
        Long profileId = profile.getProfileId();

        List<ProfileValue> profileValues = profileValueMapper.selectByProfileIdAndUserId(profileId, userId);
        if (profileValues != null && profileValues.size() > 0) {
            return profileValues.get(0).getProfileValue();
        }
        return null;
    }

    @Override
    public List<ProfileValue> selectLevelValues(Long levelId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("levelId:{}", levelId);
        }
        // Map map;
        if (levelId == null) {
            return null;
        } else if (levelId == LEVEL_USER) {
            return profileValueMapper.selectLevelValuesForUser();
        } else if (levelId == LEVEL_ROLE) {
            return profileValueMapper.selectLevelValuesForRole();
        } else if (levelId == LEVEL_GLOBAL) {
            return profileValueMapper.selectLevelValuesForGlobal();
        }
        return null;

    }

    @Override
    public String getProfileValue(IRequest request, String profileName) {
        List<ProfileValue> profileValues = profileValueMapper.selectPriorityValues(profileName);
        // 如果不为空，返回优先级最高的第一条记录值
        if (profileValues != null && profileValues.size() > 0) {
            return profileValues.get(0).getProfileValue();
        }
        return null;
    }

    @Override
    public String getProfileValue(String profileName) {
        List<ProfileValue> profileValues = profileValueMapper.selectPriorityValues(profileName);
        // 如果不为空，返回优先级最高的第一条记录值
        if (profileValues != null && profileValues.size() > 0) {
            return profileValues.get(0).getProfileValue();
        }
        return null;
    }

}