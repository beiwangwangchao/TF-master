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

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccountVo;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailAccountMapper;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;
import com.lkkhpg.dsis.platform.service.IMessageEmailAccountService;

/**
 * 邮箱账号impl.
 * 
 * @author Clerifen Li
 */
@Transactional
@Service
public class MessageEmailAccountServiceImpl implements IMessageEmailAccountService, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    private MessageEmailAccountMapper mapper;

    @Autowired
    private IAESClientService aceClientService;
    
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageEmailAccount createMessageEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        // aes加密
//        AESEncryptors encryptor = (AESEncryptors) beanFactory.getBean("aesEncryptor");
        obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        mapper.insert(obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageEmailAccount updateMessageEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        // 不处理password
        obj.setPassword(null);
        mapper.updateByPrimaryKeySelective(obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageEmailAccount updateMessageEmailAccountPasswordOnly(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        // aes加密
//        AESEncryptors encryptor = (AESEncryptors) beanFactory.getBean("aesEncryptor");
        obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        mapper.updateByPrimaryKeySelective(obj);
        return obj;
    }

    @Override
    public MessageEmailAccount selectMessageEmailAccountById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteMessageEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj.getAccountId() == null) {
            return 0;
        }
        return mapper.deleteByPrimaryKey(obj.getAccountId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchDelete(IRequest request, List<MessageEmailAccount> objs) {
        int result = 0;
        if (objs == null || objs.isEmpty()) {
            return result;
        }

        for (MessageEmailAccount obj : objs) {
            self().deleteMessageEmailAccount(request, obj);
            result++;
        }
        return result;
    }

    @Override
    public List<MessageEmailAccountVo> selectMessageEmailAccounts(IRequest request, MessageEmailAccount example, int page,
            int pageSize) {
//        PageHelper.startPage(page, pageSize);
        List<MessageEmailAccountVo> list = mapper.selectMessageEmailAccounts(example);
        return list;
    }

    @Override
    public List<MessageEmailAccount> selectMessageEmailAccountWithPassword(IRequest request, MessageEmailAccount example, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<MessageEmailAccount> list = mapper.selectMessageEmailAccountPassword(example);
        return list;
    }

}
