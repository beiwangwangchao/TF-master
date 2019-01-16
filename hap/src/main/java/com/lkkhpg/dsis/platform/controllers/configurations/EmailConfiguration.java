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

import com.lkkhpg.dsis.platform.dto.system.MessageEmailConfig;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailConfigMapper;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;
import com.lkkhpg.dsis.platform.message.TopicMonitor;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * @author shiliyan
 *
 */
@TopicMonitor(channel = "email_config")
public class EmailConfiguration implements InitializingBean, IMessageConsumer<MessageEmailConfig> {

    public static final String EMAIL_CONFIG = "email_config";

    private boolean isReady = false;

    @Autowired
    private IAESClientService aesService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private IMessagePublisher msgPublisher;

    private Map<Long, MessageEmailConfig> emailAccounts;

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

    public MessageEmailConfig getMessageEmailConfig(Long configId) {
        if (emailAccounts != null) {
            return emailAccounts.get(configId);
        }
        return null;
    }

    private void configChanged() {
        this.setReady(false);
        emailAccounts = new HashMap<Long, MessageEmailConfig>();
        MessageEmailConfigMapper accountMapper = beanFactory.getBean(MessageEmailConfigMapper.class);
        List<MessageEmailConfig> selectMessageEmailConfigs = accountMapper.selectMessageEmailConfigs(null);
        for (MessageEmailConfig config : selectMessageEmailConfigs) {

            MessageEmailConfig r = new MessageEmailConfig();
            r.setConfigId(config.getConfigId());
            r.setConfigCode(config.getConfigCode());
            r.setPassword(aesService.decrypt(config.getPassword()));
            emailAccounts.put(config.getConfigId(), r);
        }
        this.setReady(true);
    }

    @Override
    public void onMessage(MessageEmailConfig message, String pattern) {
        if (EMAIL_CONFIG.equals(pattern)) {
            this.configChanged();
        }
    }

    public boolean isReady() {
        return isReady;
    }

    private void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public void resetConfigrations() {
        msgPublisher.publish(EMAIL_CONFIG, new MessageEmailConfig());
    }
}
