/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysEvent;

/**
 * 事件管理mapper.
 * 
 * @author wangc
 *
 */
public interface SysEventMapper {
    int deleteByPrimaryKey(Long eventId);

    int insert(SysEvent record);

    int insertSelective(SysEvent record);

    SysEvent selectByPrimaryKey(Long eventId);

    int updateByPrimaryKeySelective(SysEvent record);

    int updateByPrimaryKey(SysEvent record);

    List<SysEvent> queryEvents(SysEvent record);
    
    Long getEventId();

    SysEvent selectByPrimaryKeyForLock(Long eventId);

    int closeEvent(Long eventId);

    List<SysEvent> queryEventsForWms(SysEvent record);
    
}