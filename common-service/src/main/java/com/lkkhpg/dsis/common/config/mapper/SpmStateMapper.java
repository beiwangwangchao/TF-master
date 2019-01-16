/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmState;

/**
 * 省/州MAPPER.
 * 
 * @author frank.li
 */
public interface SpmStateMapper {
    int deleteByPrimaryKey(SpmState stateCode);

    int insert(SpmState record);

    int insertSelective(SpmState record);

    SpmState selectByPrimaryKey(String stateCode);

    int updateByPrimaryKeySelective(SpmState record);

    int updateByPrimaryKey(SpmState record);
    
    int updateByCountry(SpmState record);

    List<SpmState> queryByState(SpmState stateCode);
    
    List<SpmState> queryByStateNoCountry(SpmState stateCode);
    
    Integer selectByStateName(@Param("name") String name);
    
    List<SpmState> queryByStateForLocation(SpmState stateCode);
    
    String queryNameByStateCode(@Param("stateCode") String stateCode, @Param("lang") String lang);
    
    String queryCountryByStateForDapp(String stateCode);
    
    List<SpmState> queryStateByStateCode(Map<String, Object> map);

    SpmState queryStateByCountryAndState(Map<String, Object> map);
}