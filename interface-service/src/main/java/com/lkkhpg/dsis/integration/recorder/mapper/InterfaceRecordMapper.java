/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.integration.recorder.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.integration.recorder.dto.InterfaceRecord;

/**
 * 接口记录Mapper.
 * @author shengyang.zhou@hand-china.com
 */
public interface InterfaceRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(InterfaceRecord record);

    InterfaceRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(InterfaceRecord record);

    int updateByPrimaryKey(InterfaceRecord record);

    String getFakeData(String methodName);

    String getFakeRequestData(String methodName);

    List<InterfaceRecord> selectByRequestDate(Map<String, Object> params);

}