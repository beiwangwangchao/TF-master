/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import com.lkkhpg.dsis.platform.dto.system.FunctionResource;
import com.lkkhpg.dsis.platform.dto.system.Resource;

/**
 * 功能资源mapper.
 * 
 * @author wuyichu
 */
public interface FunctionResourceMapper {
    int deleteByPrimaryKey(Long funcSrcId);

    int insert(FunctionResource record);

    int insertSelective(FunctionResource record);

    FunctionResource selectByPrimaryKey(Long funcSrcId);

    int updateByPrimaryKeySelective(FunctionResource record);

    int updateByPrimaryKey(FunctionResource record);

    int deleteByResource(Resource resource);

    int deleteByFunctionId(Long functionId);
}