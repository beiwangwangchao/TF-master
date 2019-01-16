/*
 *
 */

package com.lkkhpg.dsis.platform.message.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.GlobalProfile;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.TopicMonitor;
import com.lkkhpg.dsis.platform.service.IProfileService;

/**
 * @author shengyang.zhou@hand-china.com
 */
@TopicMonitor(channel = "profile")
public class GlobalProfileSubscriber implements IMessageConsumer<GlobalProfile>, ListenerInitHandler, InitializingBean {

    private static Map<GlobalProfileListener, List<String>> listenerMap = new HashMap<>();

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private IProfileService profileService;

    private Logger logger = LoggerFactory.getLogger(GlobalProfileSubscriber.class);

    public void addListener(GlobalProfileListener listener) {
        listenerMap.put(listener, listener.getAcceptedProfiles());
        initLoad(listener);
    }

    @Override
    public void initLoad(GlobalProfileListener listener) {
        if (logger.isDebugEnabled()) {
            logger.debug("initial load profile values for:" + listener);
        }
        IRequest iRequest = RequestHelper.newEmptyRequest();
        iRequest.setAccountId(-1L);
        iRequest.setRoleId(-1L);
        for (String profileName : listener.getAcceptedProfiles()) {
            String profileValue = profileService.getProfileValue(iRequest, profileName);
            if (profileValue != null) {
                listener.updateProfile(profileName, profileValue);
            } else {
            	if (logger.isDebugEnabled()) {
            		logger.debug("skip init profile:{}, there no such value in database.", profileName);
            	}
            }
        }
    }

    @Override
    public void onMessage(GlobalProfile message, String pattern) {
        listenerMap.forEach((k, v) -> {
            // notify profile listener
            if (v.contains(message.getProfileName())) {
                k.updateProfile(message.getProfileName(), message.getProfileValue());
            }
        });
    }

    /**
     * find all GlobalProfileListener .
     * 
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, GlobalProfileListener> listeners = appCtx.getBeansOfType(GlobalProfileListener.class);
        listeners.forEach((k, v) -> addListener(v));
    }

}
