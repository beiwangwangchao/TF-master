/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.MessageAccount;
import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 消息模板服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageAccountService extends ProxySelf<IMessageAccountService> {

    MessageAccount createMessageAccount(IRequest request, @StdWho MessageAccount obj) throws BaseException;

    MessageAccount updateMessageAccount(IRequest request, @StdWho MessageAccount obj);

    MessageAccount updateMessageAccountPasswordOnly(IRequest request, MessageAccount obj);
    
    MessageAccount selectMessageAccountById(IRequest request, Long objId);

    List<MessageAccount> selectMessageAccounts(IRequest request, MessageAccount obj, int page, int pageSize);

    int deleteMessageAccount(IRequest request, MessageAccount obj);

    int batchDelete(IRequest request, List<MessageAccount> objs) throws BaseException;
    
    List<MessageAccount> selectMessageAccountPassword(IRequest request, MessageAccount obj, int page, int pageSize);
    
}
