/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 配置文件对象.
 * 
 * @author frank.li
 */
@Table(name = "sys_profile")
public class Profile extends BaseDTO {

    private static final long serialVersionUID = -3284239490993804271L;

    /**
     * 配置文件描述.
     */
    @Column(name = "description")
    @MultiLanguageField
    @NotEmpty
    private String description;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    /**
     * 配置文件.
     */
    @Column(name = "profile_name")
    @NotEmpty
    private String profileName;

    @Children
    private List<ProfileValue> profileValues;

    public String getDescription() {
        return description;
    }

    public Long getProfileId() {
        return profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public List<ProfileValue> getProfileValues() {
        return profileValues;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName == null ? null : profileName.trim();
    }

    public void setProfileValues(List<ProfileValue> profileValues) {
        this.profileValues = profileValues;
    }
}