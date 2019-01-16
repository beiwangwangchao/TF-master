/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Message;
import com.lkkhpg.dsis.platform.dto.system.MessageTransaction;

/**
 * sms服务,被job调用.
 * 
 * @author Clerifen Li
 */
public interface ISmsService extends ProxySelf<ISmsService> {

    /*
     * 定时器定时发送邮件 已经在内部实现事务,有新的实现修改内部代码
     * 
     * @throws Exception
     */
    boolean sendMessage(boolean isVipQueue, Map<String, Object> params) throws Exception;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void saveTransaction(Message message, MessageTransaction obj);
}
