/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.MessageTemplate;
import com.lkkhpg.dsis.platform.exception.EmailException;

/**
 * 消息模板服务接口.
 * 
 * @author Clerifen Li
 */
public interface IMessageTemplateService extends ProxySelf<IMessageTemplateService> {

    MessageTemplate createMessageTemplate(IRequest request, @StdWho MessageTemplate obj) throws EmailException;

    /**
     *
     * @param request 统一上下文
     * @param obj     模板对象
     * @return
     * @throws EmailException
     */
    MessageTemplate updateMessageTemplate(IRequest request, @StdWho MessageTemplate obj) throws EmailException;

    MessageTemplate selectMessageTemplateById(IRequest request, Long objId);

    /**
     * 消息模板记录查询
     * @param request  统一上下文
     * @param obj      模板对象
     * @param page     页码
     * @param pageSize 每页长度
     * @return
     */
    List<MessageTemplate> selectMessageTemplates(IRequest request, MessageTemplate obj, int page, int pageSize);

    int deleteMessageTemplate(IRequest request, MessageTemplate obj);

    int batchDelete(IRequest request, List<MessageTemplate> objs);

}
