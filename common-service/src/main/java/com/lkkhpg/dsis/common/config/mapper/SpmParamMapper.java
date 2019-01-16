/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmParam;

/**
 * 组织参数头表接口.
 * 
 * @author Zhao
 *
 */
public interface SpmParamMapper {
    /**
     * 获取参数属性.
     * 
     * @param paramName
     *            组织参数
     * @param orgType
     *            组织类型类型INV、SALES、MARKET、OU
     * @return 响应信息
     */
    List<SpmParam> getParamValues(String paramName, String orgType);
}