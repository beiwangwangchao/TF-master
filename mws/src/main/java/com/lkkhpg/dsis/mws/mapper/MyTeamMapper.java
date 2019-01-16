/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.mws.dto.MyTeam;

/**
 * mws我的团队mapper.
 * @author Zhaoqi
 *
 */
public interface MyTeamMapper {

    List<MyTeam> selectTeamByMemberId(Long memberId);
    
    List<MyTeam> queryDatePeriod(Map<String, Object> map);
    
    List<MyTeam> queryDatePeriodOffline(Map<String, Object> map);
    
    List<MyTeam> selectByMemberCode(String memberCode);
    
    List<MyTeam> selectNotMemberIdData(String memberCode);
    
    List<MyTeam> selectByDate(Map<String, Object> map);
}
