/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.dashboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.dashboard.dto.FunctionShortcut;
import com.lkkhpg.dsis.platform.dto.system.Function;

/**
 * 快捷方式 Mapper.
 *
 * @author runbai.chen
 */
public interface FunctionShortcutMapper {
    int deleteBatch(@Param("userId") Long userId, @Param("roleId") Long roleId,
            @Param("functionIdList") List<Long> functionIdList);

    int deleteByPrimaryKey(FunctionShortcut record);

    int insert(FunctionShortcut record);

    int insertSelective(FunctionShortcut record);

    FunctionShortcut selectByPrimaryKey(Long shortcutId);

    List<FunctionShortcut> selectFunctionShortcuts(FunctionShortcut record);

    int updateByPrimaryKeySelective(FunctionShortcut record);

    int updateByPrimaryKey(FunctionShortcut record);

    List<Map<String, Object>> selectFunctionShortcutsMenu(@Param("roleId") Long roleId, @Param("userId") Long userId,
            @Param("local") String local);

    Function selectFunctionNameByCode(Function record);
}