/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsWhiteList;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsWhiteListMapper;
import com.lkkhpg.dsis.platform.service.IMessageSmsWhiteListService;

/**
 * 短信白名单impl.
 * 
 * @author Clerifen Li
 */
@Transactional
@Service
public class MessageSmsWhiteListServiceImpl implements IMessageSmsWhiteListService, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    private MessageSmsWhiteListMapper mapper;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageSmsWhiteList createMessageSmsWhiteList(IRequest request, MessageSmsWhiteList obj) {
        if (obj == null) {
            return null;
        }
        mapper.insert(obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageSmsWhiteList updateMessageSmsWhiteList(IRequest request, MessageSmsWhiteList obj) {
        if (obj == null) {
            return null;
        }
        mapper.updateByPrimaryKeySelective(obj);
        return obj;
    }

    @Override
    public MessageSmsWhiteList selectMessageSmsWhiteListById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteMessageSmsWhiteList(IRequest request, MessageSmsWhiteList obj) {
        if (obj.getId() == null) {
            return 0;
        }
        return mapper.deleteByPrimaryKey(obj.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchDelete(IRequest request, List<MessageSmsWhiteList> objs) {
        int result = 0;
        if (objs == null || objs.isEmpty()) {
            return result;
        }

        for (MessageSmsWhiteList obj : objs) {
            self().deleteMessageSmsWhiteList(request, obj);
            result++;
        }
        return result;
    }

    @Override
    public List<MessageSmsWhiteList> selectMessageSmsWhiteLists(IRequest request, MessageSmsWhiteList example, int page,
            int pageSize) {
//        PageHelper.startPage(page, pageSize);
        List<MessageSmsWhiteList> list = mapper.selectMessageSmsWhiteLists(example);
        return list;
    }

}
