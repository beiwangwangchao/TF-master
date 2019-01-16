/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccountVo;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.exception.EmailException;

/**
 * sms账号服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageSmsAccountService extends ProxySelf<IMessageSmsAccountService> {

    MessageSmsAccount createMessageSmsAccount(IRequest request, @StdWho MessageSmsAccount obj) throws BaseException;

    MessageSmsAccount updateMessageSmsAccount(IRequest request, @StdWho MessageSmsAccount obj);

    MessageSmsAccount updateMessageSmsAccountPasswordOnly(IRequest request, MessageSmsAccount obj);
    
    MessageSmsAccount selectMessageSmsAccountById(IRequest request, Long objId);

    List<MessageSmsAccountVo> selectMessageSmsAccounts(IRequest request, MessageSmsAccount obj, int page, int pageSize);

    int deleteMessageSmsAccount(IRequest request, MessageSmsAccount obj);

    int batchDelete(IRequest request, List<MessageSmsAccount> objs) throws BaseException;
    
    List<MessageSmsAccountVo> selectMessageSmsAccountPassword(IRequest request, MessageSmsAccount obj, int page, int pageSize);

    void batchUpdate(IRequest requestContext, @StdWho MessageSmsAccount obj) throws EmailException;
    
}
