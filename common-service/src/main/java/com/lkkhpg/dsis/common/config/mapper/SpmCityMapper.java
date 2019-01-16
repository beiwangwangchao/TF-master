/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmCity;

/**
 * 城市MAPPER.
 * 
 * @author frank.li
 */
public interface SpmCityMapper {
    int deleteByPrimaryKey(String cityCode);

    int insert(SpmCity record);

    int insertSelective(SpmCity record);

    SpmCity selectByPrimaryKey(String cityCode);

    int updateByPrimaryKeySelective(SpmCity record);

    int updateByPrimaryKey(SpmCity record);

    int updateByCity(SpmCity record);

    List<SpmCity> queryByCity(SpmCity cityCode);

    List<SpmCity> queryNullByCity(SpmCity cityCode);

    List<SpmCity> selectByName(SpmCity cityCode);

    int updateSpmCity(SpmCity record);

    List<SpmCity> queryCityForLocation(SpmCity cityCode);

    String queryNameByCityCode(@Param("cityCode") String cityCode, @Param("lang") String lang);

    String queryEnNameByCityCode(String cityCode);

    List<SpmCity> queryCityByStateCode(Map<String, Object> map);

    SpmCity queryCityByStateAndCity(Map<String, Object> map);
}