/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailWhiteList;
import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 邮箱白名单服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageEmailWhiteListService extends ProxySelf<IMessageEmailWhiteListService> {

    MessageEmailWhiteList createMessageEmailWhiteList(IRequest request, @StdWho MessageEmailWhiteList obj) throws BaseException;

    MessageEmailWhiteList updateMessageEmailWhiteList(IRequest request, @StdWho MessageEmailWhiteList obj);

    MessageEmailWhiteList selectMessageEmailWhiteListById(IRequest request, Long objId);

    List<MessageEmailWhiteList> selectMessageEmailWhiteLists(IRequest request, MessageEmailWhiteList obj, int page, int pageSize);

    int deleteMessageEmailWhiteList(IRequest request, MessageEmailWhiteList obj);

    int batchDelete(IRequest request, List<MessageEmailWhiteList> objs) throws BaseException;
    
}
