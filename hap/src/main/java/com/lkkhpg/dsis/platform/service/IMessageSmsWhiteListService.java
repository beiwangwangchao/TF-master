/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsWhiteList;
import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 邮箱白名单服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageSmsWhiteListService extends ProxySelf<IMessageSmsWhiteListService> {

    MessageSmsWhiteList createMessageSmsWhiteList(IRequest request, @StdWho MessageSmsWhiteList obj) throws BaseException;

    MessageSmsWhiteList updateMessageSmsWhiteList(IRequest request, @StdWho MessageSmsWhiteList obj);

    MessageSmsWhiteList selectMessageSmsWhiteListById(IRequest request, Long objId);

    List<MessageSmsWhiteList> selectMessageSmsWhiteLists(IRequest request, MessageSmsWhiteList obj, int page, int pageSize);

    int deleteMessageSmsWhiteList(IRequest request, MessageSmsWhiteList obj);

    int batchDelete(IRequest request, List<MessageSmsWhiteList> objs) throws BaseException;
    
}
