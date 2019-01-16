/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年3月2日
 */
@Table(name = "sys_profile_value")
public class ProfileValue extends BaseDTO {

    private static final long serialVersionUID = -6967222521922622857L;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column(name = "profile_value_id", nullable = false)
    private Long profileValueId;

    /**
     * 配置文件ID.
     */
    @Id
    @Column(name = "profile_id")
    @NotEmpty
    private Long profileId;

    /**
     * 层次ID值.
     */
    @Column(name = "level_id")
    @NotEmpty
    private Long levelId;

    /**
     * 层次值.
     */
    @Column(name = "level_value")
    @NotEmpty
    private String levelValue;

    @Column(name = "level_Name")
    private String levelName;

    /**
     * 配置文件值.
     */
    @Column(name = "profile_value")
    private String profileValue;

    public Long getProfileValueId() {
        return profileValueId;
    }

    public void setProfileValueId(Long profileValueId) {
        this.profileValueId = profileValueId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue == null ? null : levelValue.trim();
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    public String getProfileValue() {
        return profileValue;
    }

    public void setProfileValue(String profileValue) {
        this.profileValue = profileValue == null ? null : profileValue.trim();
    }

}