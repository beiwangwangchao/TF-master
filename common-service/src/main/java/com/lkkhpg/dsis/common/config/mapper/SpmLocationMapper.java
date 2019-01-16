/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;

/**
 * 地址MAPPER.
 * 
 * @author frank.li
 */
public interface SpmLocationMapper {
    int deleteByPrimaryKey(SpmLocation record);

    int insert(SpmLocation record);

    int insertSelective(SpmLocation record);

    SpmLocation selectByPrimaryKey(@Param("locationId") Long locationId);

    int updateByPrimaryKeySelective(SpmLocation record);

    int updateByPrimaryKey(SpmLocation record);

    List<SpmLocation> queryByLocation(SpmLocation locationId);

    /**
     * 触发审计.
     * 
     * @author xiawang.liu
     * 
     */
    int updateAddressLastUpdateDate(SpmLocation spmLocation);

    /**
     * 根据locationId对应国家Code获取地址信息.
     * 
     * @param locationId
     *            地址Id
     * @return 地址对象
     */
    SpmLocation selectLocationById(@Param("locationId") Long locationId);
}