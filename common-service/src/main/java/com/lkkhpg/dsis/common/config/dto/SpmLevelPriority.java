/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.dto;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 组织优先级dto.
 * 
 * @author Zhao
 *
 */
public class SpmLevelPriority extends BaseDTO {
    private Long lvlPriorityId;

    private String priorityType;

    private Long levelId;

    private Long priority;

    private String remark;

    public Long getLvlPriorityId() {
        return lvlPriorityId;
    }

    public void setLvlPriorityId(Long lvlPriorityId) {
        this.lvlPriorityId = lvlPriorityId;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType == null ? null : priorityType.trim();
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}