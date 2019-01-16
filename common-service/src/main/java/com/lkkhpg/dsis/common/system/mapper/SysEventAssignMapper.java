/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.system.dto.SysEventAssign;

/**
 * 事件参与会员mapper.
 * 
 * @author wangc
 *
 */
public interface SysEventAssignMapper {
    int deleteByPrimaryKey(Long eventAssignId);

    int insert(SysEventAssign record);

    int insertSelective(SysEventAssign record);

    SysEventAssign selectByPrimaryKey(Long eventAssignId);

    int updateByPrimaryKeySelective(SysEventAssign record);

    int updateByPrimaryKey(SysEventAssign record);
    
    Integer queryRepeatAssign(SysEventAssign record);
    
    List<SysEventAssign> queryByEventId(Long eventId);

    SysEventAssign getByMember(@Param(value = "eventId") Long eventId,
            @Param(value = "assignValue") String assignValue);

    Long selectAttendanceByEventIdAndMemberId(@Param("eventId") Long eventId, @Param("memberId") String memberId);
    
    Long getTravelCountByMember(Long memberId);
}