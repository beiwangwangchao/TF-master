/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccountVo;
import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * 邮件账号服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageEmailAccountService extends ProxySelf<IMessageEmailAccountService> {

    MessageEmailAccount createMessageEmailAccount(IRequest request, @StdWho MessageEmailAccount obj) throws BaseException;

    MessageEmailAccount updateMessageEmailAccount(IRequest request, @StdWho MessageEmailAccount obj);

    MessageEmailAccount updateMessageEmailAccountPasswordOnly(IRequest request, MessageEmailAccount obj);
    
    MessageEmailAccount selectMessageEmailAccountById(IRequest request, Long objId);

    List<MessageEmailAccountVo> selectMessageEmailAccounts(IRequest request, MessageEmailAccount obj, int page, int pageSize);

    int deleteMessageEmailAccount(IRequest request, MessageEmailAccount obj);

    int batchDelete(IRequest request, List<MessageEmailAccount> objs) throws BaseException;
    
    List<MessageEmailAccount> selectMessageEmailAccountWithPassword(IRequest request, MessageEmailAccount obj, int page, int pageSize);
    
}
