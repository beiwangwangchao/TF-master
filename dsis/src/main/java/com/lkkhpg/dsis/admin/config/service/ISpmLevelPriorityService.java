/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.config.dto.SpmLevelPriority;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 组织层次优先级.
 * @author liuxuan
 *
 */
public interface ISpmLevelPriorityService {
    /**
     * 查询组织层次优先级.
     * @param request 请求的上下文
     * @param spmLevelPriority 条件
     * @param page 页
     * @param pagesize 页数
     * @return 响应数据
     */
    List<SpmLevelPriority> selectLevel(IRequest request, SpmLevelPriority spmLevelPriority, 
            int page, int pagesize);
    /**
     * 保存组织层次优先级.
     * @param request 请求上下文
     * @param spmLevelPriority 条件
     * @return 响应数据
     * @throws SystemProfileException 系统配置统一异常
     */
    List<SpmLevelPriority> saveLevelPrioty(IRequest request, List<SpmLevelPriority> spmLevelPriority)
            throws SystemProfileException;
}
