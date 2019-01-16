/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SysMsScheduleMessage;

/**
 * @author shiliyan
 *
 */
public interface SysScheduleMessageMapper {
   List<SysMsScheduleMessage> selectScheduleMessage4User();
   List<SysMsScheduleMessage> selectScheduleMessage4Member();
}