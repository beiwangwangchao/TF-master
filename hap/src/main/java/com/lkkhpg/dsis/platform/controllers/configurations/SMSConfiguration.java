/*
 *
 */
package com.lkkhpg.dsis.platform.controllers.configurations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccountVo;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsAccountMapper;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;
import com.lkkhpg.dsis.platform.message.TopicMonitor;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * @author shiliyan
 *
 */
@TopicMonitor(channel = "sms_config")
public class SMSConfiguration implements InitializingBean, IMessageConsumer<MessageSmsAccount> {

    public static final String SMS_CONFIG = "sms_config";

    private boolean isReady = false;

    @Autowired
    private IAESClientService aesService;

    @Autowired
    private BeanFactory beanFactory;
    
    @Autowired
    private IMessagePublisher msgPublisher;


    private Map<Long, MessageSmsAccount> smsAccounts;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configChanged();
    }

    public MessageSmsAccount getMessageSmsAccount(Long marketId) {
        if (smsAccounts != null) {
            return smsAccounts.get(marketId);
        }
        return null;
    }

    private void configChanged() {
        this.setReady(false);
        smsAccounts = new HashMap<Long, MessageSmsAccount>();
        MessageSmsAccountMapper smsAccountMapper = beanFactory.getBean(MessageSmsAccountMapper.class);
        List<MessageSmsAccountVo> selectMessageSmsAccountPassword = smsAccountMapper
                .selectMessageSmsAccountPassword(null);
        for (MessageSmsAccountVo messageSmsAccountVo : selectMessageSmsAccountPassword) {
            MessageSmsAccount account = new MessageSmsAccount();
            Long marketId = messageSmsAccountVo.getMarketId();
            account.setMarketId(marketId);
            account.setPassword(aesService.decrypt(messageSmsAccountVo.getPassword()));
            smsAccounts.put(marketId, account);
        }
        this.setReady(true);
    }

    @Override
    public void onMessage(MessageSmsAccount message, String pattern) {
        if (SMS_CONFIG.equals(pattern)) {
            this.configChanged();
        }
    }

    public boolean isReady() {
        return isReady;
    }

    private void setReady(boolean isReady) {
        this.isReady = isReady;
    }
    
    public void resetConfigrations(){
        msgPublisher.publish(SMS_CONFIG, new MessageSmsAccount());
    }
}
