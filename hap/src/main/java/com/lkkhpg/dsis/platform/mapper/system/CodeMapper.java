/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Code;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface CodeMapper {
    int deleteByPrimaryKey(Code record);

    int insert(Code record);

    Code selectByPrimaryKey(Code example);

    List<Code> selectCodes(Code record);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);
}