/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmServiceCenter;

/**
 * 服务中心mapper.
 * 
 * @author wangc
 *
 */
public interface SpmServiceCenterMapper {
    int deleteByPrimaryKey(Long serviceCentereId);

    int insert(SpmServiceCenter record);

    int insertSelective(SpmServiceCenter record);

    SpmServiceCenter selectById(Long serviceCenterId);

    SpmServiceCenter selectByPrimaryKey(SpmServiceCenter spmServiceCenter);

    int updateByPrimaryKeySelective(SpmServiceCenter record);

    int updateByPrimaryKey(SpmServiceCenter record);

    List<SpmServiceCenter> queryServiceCenter(@Param(value = "serviceCenter") SpmServiceCenter serviceCenter,
            @Param(value = "statusList") List<String> statusList);
    
    List<SpmServiceCenter>  queryServiceCenterForOrder(SpmServiceCenter serviceCenter);

    int approveServiceCenter(Long serviceCenterId);

    int rejectServiceCenter(Long serviceCenterId);

    Integer queryServiceCenterByCode(SpmServiceCenter record);

    List<SpmServiceCenter> queryServiceCenterByMember(SpmServiceCenter record);
    
    int closeServiceCenter(SpmServiceCenter record);

}