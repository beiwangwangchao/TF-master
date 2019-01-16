/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.system.dto.SiteMessageRead;
import com.lkkhpg.dsis.common.system.dto.SysMessageRead;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.dto.SysMsScheduleMessage;

/**
 * 消息服务,站内信.
 * 
 * @author shiliyan
 *
 */
public interface IManualMessageService {

    // List<SiteMessageRead> queryMessage(Long accountID);

    void updateMessage(SysMessageRead record);

    List<SiteMessageRead> readMessage(SysMessageRead record);

    void delMessage(SysMessageRead record);

    void publishMessage(SysMsMessage sysMSMessage, List<SysMsMessageAssign> sysMSMessageAssigns) throws Exception;

    Map<SysMsScheduleMessage, List<SysMsMessageAssign>> publishSchedulUserMessage();

    Map<SysMsScheduleMessage, List<SysMsMessageAssign>> publishSchedulMemberMessage();

    List<SiteMessageRead> queryMessage(SiteMessageRead record, int page, int pagesize);

    void publishMessage(SysMsMessage sysMSMessage, List<SysMsMessageAssign> sysMSMessageAssigns, String templateCode,
            Map<String, Object> data, Long marketId) throws Exception;

    SiteMessageRead queryNext(SiteMessageRead record);

    List<SiteMessageRead> queryMessageMws(SiteMessageRead record, int page, int pagesize);
    
    int getMessageUnreadCount(Long accountId);
}
