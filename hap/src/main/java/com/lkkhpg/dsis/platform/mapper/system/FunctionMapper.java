/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.platform.dto.system.Function;
import com.lkkhpg.dsis.platform.dto.system.FunctionDisplay;
import com.lkkhpg.dsis.platform.dto.system.Resource;

/**
 * @author wuyichu
 */
public interface FunctionMapper {

    int deleteByPrimaryKey(Function record);

    int insert(Function record);

    int insertSelective(Function record);

    Function selectByPrimaryKey(Long functionId);

    int updateByPrimaryKeySelective(Function record);

    int updateByPrimaryKey(Function record);

    List<FunctionDisplay> selectFunctions(Function record);

    List<Resource> selectExitResourcesByFunction(Map<String, Object> params);

    List<Resource> selectNotExitResourcesByFunction(Map<String, Object> params);
}