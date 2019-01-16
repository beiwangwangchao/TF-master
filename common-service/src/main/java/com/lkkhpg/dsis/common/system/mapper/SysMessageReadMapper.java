/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.SiteMessageRead;
import com.lkkhpg.dsis.common.system.dto.SysMessageRead;

/**
 * 消息记录表.
 * 
 * @author HuangJiaJing
 *
 */
public interface SysMessageReadMapper {
    int deleteByPrimaryKey(Long readId);

    int insert(SysMessageRead record);

    int insertSelective(SysMessageRead record);

    SysMessageRead selectByPrimaryKey(Long readId);

    int updateByPrimaryKeySelective(SysMessageRead record);

    int updateByPrimaryKey(SysMessageRead record);

    // List<SiteMessageRead> selectMessageByAccount(Long accountId);

    List<SiteMessageRead> selectMessageByReadID(Long readId);

    List<SiteMessageRead> selectMessageByAccount(SiteMessageRead record);
    
    SiteMessageRead queryNext(SiteMessageRead record);
    
    List<SiteMessageRead> selectMessageByMwsAccount(SiteMessageRead record);
    
    int getMessageUnreadCount(Long memberId);

}