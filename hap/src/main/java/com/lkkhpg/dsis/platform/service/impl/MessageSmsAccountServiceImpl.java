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
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccountVo;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsWhiteList;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsWhiteListMapper;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;
import com.lkkhpg.dsis.platform.service.IMessageSmsAccountService;

/**
 * sms消息账号impl.
 * 
 * @author Clerifen Li
 */
@Transactional
@Service
public class MessageSmsAccountServiceImpl implements IMessageSmsAccountService, BeanFactoryAware {

    // 选择白名单没有设置名单
    private static final String MSG_MESSAGE_NO_WHITE_LIST = "msg.warning.system.email_message_no_whitelist";
    
    private BeanFactory beanFactory;

    @Autowired
    private MessageSmsAccountMapper mapper;

    @Autowired
    private MessageSmsWhiteListMapper addressMapper;
    
    @Autowired
    private IAESClientService aceClientService;
    
    @Autowired
    private com.lkkhpg.dsis.platform.controllers.configurations.SMSConfiguration configration;
    
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageSmsAccount createMessageSmsAccount(IRequest request, MessageSmsAccount obj) {
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
    public MessageSmsAccount updateMessageSmsAccount(IRequest request, MessageSmsAccount obj) {
        if (obj == null) {
            return null;
        }
        MessageSmsAccount account = mapper.selectByPrimaryKey(obj.getAccountId());
        if(account.getPassword() != null && account.getPassword().equals(obj.getPassword())){
            // 没有修改密码
            obj.setPassword(null);
        }else{
            // aes加密
//            AESEncryptors encryptor = (AESEncryptors) beanFactory.getBean("aesEncryptor");
            obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        }
        mapper.updateByPrimaryKeySelective(obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageSmsAccount updateMessageSmsAccountPasswordOnly(IRequest request, MessageSmsAccount obj) {
        if (obj == null) {
            return null;
        }
        // aes加密
//        AESEncryptors encryptor = (AESEncryptors) beanFactory.getBean("aesEncryptor");
        obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        mapper.updateByPrimaryKeySelective(obj);
        
        configration.resetConfigrations();
        return obj;
    }

    @Override
    public MessageSmsAccount selectMessageSmsAccountById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteMessageSmsAccount(IRequest request, MessageSmsAccount obj) {
        if (obj.getAccountId() == null) {
            return 0;
        }
        int count = 0;
        count += addressMapper.deleteByAccountId(obj.getAccountId());
        count += mapper.deleteByPrimaryKey(obj.getAccountId());
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchDelete(IRequest request, List<MessageSmsAccount> objs) {
        int result = 0;
        if (objs == null || objs.isEmpty()) {
            return result;
        }

        for (MessageSmsAccount obj : objs) {
            self().deleteMessageSmsAccount(request, obj);
            result++;
        }
        return result;
    }

    @Override
    public List<MessageSmsAccountVo> selectMessageSmsAccounts(IRequest request, MessageSmsAccount example, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectMessageSmsAccounts(example);
    }

    @Override
    public List<MessageSmsAccountVo> selectMessageSmsAccountPassword(IRequest request, MessageSmsAccount example, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<MessageSmsAccountVo> list = mapper.selectMessageSmsAccountPassword(example);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private MessageSmsWhiteList createAddress(IRequest request, MessageSmsWhiteList obj) {
        if (obj == null) {
            return null;
        }
        addressMapper.insert(obj);
        return obj;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    private MessageSmsWhiteList updateAddress(IRequest request, MessageSmsWhiteList obj) {
        if (obj == null) {
            return null;
        }
        addressMapper.updateByPrimaryKeySelective(obj);
        return obj;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchUpdate(IRequest requestContext, MessageSmsAccount obj) throws EmailException {
        if(obj != null){
            /* Mclin修改，编辑更新和新建都要进行白名单检查 */
            if(obj.getUseWhiteList() != null && obj.getUseWhiteList().equalsIgnoreCase("Y") 
                    && (obj.getWhiteLists() == null || obj.getWhiteLists().size() == 0) ){
                // 选择白名单没有设置名单
                throw new EmailException(MSG_MESSAGE_NO_WHITE_LIST);
            }
            /* Mclin修改验证编号或市场是否唯一 */
            List<MessageSmsAccount> tmp = mapper.getSmsByCodeOrMarketId(obj.getAccountId(), 
                    obj.getAccountCode(), obj.getMarketId());
            if (tmp.isEmpty() == false) {
                MessageSmsAccount acc = tmp.get(0);
                if (acc.getAccountCode().compareTo(obj.getAccountCode()) == 0) {
                    throw new EmailException(EmailException.MSG_ERROR_SAME_CODE_IS_EXISTS);
                } else {
                    throw new EmailException(EmailException.MSG_ERROR_SAME_MARKET_ID_IS_EXISTS);
                }
            }
            if(obj.getAccountId() == null){
               /* if(obj.getUseWhiteList() != null && obj.getUseWhiteList().equalsIgnoreCase("Y") && (obj.getWhiteLists() == null || obj.getWhiteLists().size() == 0) ){
                    // 选择白名单没有设置名单
                    throw new EmailException(MSG_MESSAGE_NO_WHITE_LIST);
                }*/
                createMessageSmsAccount(requestContext, obj);
            }else{
                updateMessageSmsAccount(requestContext, obj);
            }
            
            if(obj.getWhiteLists() != null){
                for (MessageSmsWhiteList current : obj.getWhiteLists()) {
                    if (current.getId() == null) {
                        //没意义的值
                        current.setObjectVersionNumber(0L);
                        current.setAccountId(obj.getAccountId());
                        createAddress(requestContext, current);
                    } else {
                        updateAddress(requestContext, current);
                    }
                }
            }
            configration.resetConfigrations();
        }
    }
}
