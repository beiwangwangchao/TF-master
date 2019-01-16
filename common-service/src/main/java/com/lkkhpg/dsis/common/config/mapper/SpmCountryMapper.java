/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmCountry;

/**
 * 国家MAPPER.
 * 
 * @author frank.li
 */
public interface SpmCountryMapper {
    int deleteByPrimaryKey(String countryCode);

    int insert(SpmCountry record);

    int insertSelective(SpmCountry record);

    SpmCountry selectByPrimaryKey(String countryCode);

    int updateByPrimaryKeySelective(SpmCountry record);

    int updateByPrimaryKey(SpmCountry record);

    List<SpmCountry> queryByCountry(SpmCountry countryCode); 
    
    List<SpmCountry> queryCountry(SpmCountry countryCode); 
    
    Integer selectByCountryName(@Param("name") String name);
    
    List<SpmCountry> queryByCountryForLocation(SpmCountry countryCode); 
    
    List<SpmCountry> queryCountryForDApp(Map<String, Object> stateMap); 
}