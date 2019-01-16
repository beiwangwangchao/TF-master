/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.controllers.configurations.SMSConfiguration;
import com.lkkhpg.dsis.platform.dto.system.Message;
import com.lkkhpg.dsis.platform.dto.system.MessageAddress;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsWhiteList;
import com.lkkhpg.dsis.platform.dto.system.MessageTransaction;
import com.lkkhpg.dsis.platform.job.SendMessageJob;
import com.lkkhpg.dsis.platform.mail.EmailStatusEnum;
import com.lkkhpg.dsis.platform.mail.EnvironmentEnum;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.MessageAttachmentMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageReceiverMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsWhiteListMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageTransactionMapper;
import com.lkkhpg.dsis.platform.service.IProfileService;
import com.lkkhpg.dsis.platform.service.ISmsService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;
import com.lkkhpg.dsis.platform.sms.SmsMessage;
import com.lkkhpg.dsis.platform.sms.SmsSender;
import com.lkkhpg.dsis.platform.sms.SmsStatusEnum;

/**
 * @author Clerifen Li
 */
@Transactional
@Service
public class SmsServiceImpl implements ISmsService, BeanFactoryAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final int dbTryTime = 3;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageAttachmentMapper attachmentMapper;

    @Autowired
    private MessageReceiverMapper receiverMapper;

    @Autowired
    private MessageTransactionMapper transactionMapper;

    @Autowired
    private MessageSmsAccountMapper smsAccountMapper;

    @Autowired
    private MessageSmsWhiteListMapper smsWhiteListMapper;

    private BeanFactory beanFactory;

    @Autowired
    private ISysFileService sysFileService;

    // @Autowired
    // private IAESClientService aceClientService;
    @Autowired
    private SMSConfiguration smsConfigration;

    @Override
    public boolean sendMessage(boolean isVipQueue, Map<String, Object> params) throws Exception {

        int success = 0;

        Map<String, SmsSender> senders = new HashMap<String, SmsSender>();
        /**
         * 发送sms部分,没有发送出去的 限制一次取100条,防止堆溢出
         */
        Integer batch = (Integer) params.get("batch");
        if (batch == null) {
            batch = 20;
        }
        if (batch == 0) {
            batch = 20;
        }
        PageHelper.startPage(1, batch);
        List<Message> smsToSend = null;
        if (isVipQueue) {
            smsToSend = messageMapper.selectVipSmsToSend();
        } else {
            smsToSend = messageMapper.selectSmsToSend();
        }
        try {
            for (Message currentMessage : smsToSend) {
                List<MessageReceiver> receivers = receiverMapper.selectByMessageId(currentMessage.getMessageId());

                // 获得smsSender
                String messageFrom = currentMessage.getMessageFrom();

                MessageAddress address = MessageAddress.toAddressObject(messageFrom);
                messageFrom = address.getAddress();
                String iMarket = address.getType();

                if (messageFrom == null || iMarket == null) {
                    error(currentMessage, "iMarket is no more exists:" + messageFrom);
                    continue;
                }

                String marketID = iMarket.substring(1, iMarket.length());

                if ("null".equals(marketID)) {
                    error(currentMessage, "marketID is no more exists:" + messageFrom);
                    continue;
                }

                SmsSender smsSender = senders.get(iMarket);
                if (smsSender == null) {
                    smsSender = (SmsSender) beanFactory.getBean("smsSender");

                    Long marketIdl = Long.valueOf(marketID);
                    MessageSmsAccount smsAccount = smsAccountMapper.selectByMarketId(marketIdl);
                    // .selectByAccountCode(messageFrom);
                    MessageTransaction obj = new MessageTransaction();
                    Date time = new Date();
                    obj.setCreatedBy(-1L);
                    obj.setLastUpdatedBy(-1L);
                    obj.setCreationDate(time);
                    obj.setLastUpdateDate(time);
                    obj.setMessageId(currentMessage.getMessageId());
                    obj.setObjectVersionNumber(0L);

                    // sms账号被删除的情况
                    if (smsAccount == null) {
                        obj.setTransactionStatus(SmsStatusEnum.ERROR.getCode());
                        obj.setTransactionMessage("sms account is no more exists:" + messageFrom);
                        this.trySaveTransaction(null, obj);
                        continue;
                    }

                    if (smsAccount.getTryTimes() != null) {
                        smsSender.setTryTimes(smsAccount.getTryTimes().intValue());
                    }
                    smsSender.setAuthId(smsAccount.getUserName());

                    String password = this.getPassword(marketIdl);
                    if (password == null) {
                        obj.setTransactionStatus(SmsStatusEnum.ERROR.getCode());
                        obj.setTransactionMessage("sms could not found  password of account : " + marketIdl);
                        this.trySaveTransaction(null, obj);
                        continue;
                    } else {
                        smsSender.setAuthToken(password);
                    }

                    smsSender.setOrganization(smsAccount.getOrganization());
                    // 白名单
                    if (smsAccount.getUseWhiteList() != null && smsAccount.getUseWhiteList().equalsIgnoreCase("Y")) {
                        List<MessageSmsWhiteList> whitelist = smsWhiteListMapper
                                .selectByAccountId(smsAccount.getAccountId());
                        List<String> stringList = new ArrayList<String>();
                        for (MessageSmsWhiteList current : whitelist) {
                            stringList.add(current.getAddress());
                        }
                        smsSender.setWhiteList(stringList);
                    }
                    senders.put(iMarket, smsSender);
                }

                // 生成sms
                // -------------------------------------------------------------------------------------
                MessageTransaction obj = new MessageTransaction();
                Date time = new Date();
                obj.setCreatedBy(-1L);
                obj.setLastUpdatedBy(-1L);
                obj.setCreationDate(time);
                obj.setLastUpdateDate(time);
                obj.setMessageId(currentMessage.getMessageId());
                obj.setObjectVersionNumber(0L);

                if (StringUtils.isAnyBlank(currentMessage.getSubject(), currentMessage.getContent())) {
                    obj.setTransactionStatus(SmsStatusEnum.ERROR.getCode());
                    obj.setTransactionMessage("title or content is null");
                    this.trySaveTransaction(null, obj);
                    continue;
                }
                boolean hasSendTO = false;
                // 创建sms消息结构
                SmsMessage smsMessage = smsSender.createSmsMessage();
                // smsMessage.setTitle(currentMessage.getSubject());
                smsMessage.setContent(this.setSmsContent(smsSender, currentMessage.getContent()));

                for (MessageReceiver receiver : receivers) {
                    if (receiver.getMessageAddress() == null || "".equals(receiver.getMessageAddress().trim())) {
                        continue;
                    }
                    // 白名单过滤
                    if (smsSender.getWhiteList() != null
                            && !smsSender.getWhiteList().contains(receiver.getMessageAddress())) {
                        continue;
                    }
                    if (ReceiverTypeEnum.NORMAL.getCode().equals(receiver.getMessageType())) {
                        // 收件人
                        smsMessage.addPhone(receiver.getMessageAddress());
                        hasSendTO = true;
                    }
                }
                // -------------------------------------------------------------------------------------
                if (hasSendTO == false) {
                    obj.setTransactionStatus(EmailStatusEnum.SUCCESS.getCode());
                    currentMessage.setSendFlag("F");// 此处改成F表示永久失败
                    this.trySaveTransaction(currentMessage, obj);
                    continue;
                }
                // 失败,重试次数
                for (int i = 0; i < smsSender.getTryTimes(); i++) {
                    try {
                        String successMessage = smsSender.send(smsMessage);

                        if (log.isDebugEnabled()) {
                            log.debug("Send sms success, {}.", i);
                        }
                        success++;
                        obj.setTransactionMessage(successMessage);
                        obj.setTransactionStatus(SmsStatusEnum.SUCCESS.getCode());
                        currentMessage.setSendFlag("Y");
                        this.trySaveTransaction(currentMessage, obj);
                        break;
                    } catch (Exception e) {
                        if (i == smsSender.getTryTimes() - 1) {
                            obj.setTransactionMessage(this.getExceptionStack(e));
                            obj.setTransactionStatus(SmsStatusEnum.ERROR.getCode());
                            currentMessage.setSendFlag("F");// 此处改成F表示永久失败
                            this.trySaveTransaction(currentMessage, obj);
                            if (log.isErrorEnabled()) {
                                log.error("Send sms failed.", e);
                            }
                            params.put("ERROR_MESSAGE", e.getMessage());
                        } else {
                            // 50毫秒间隔,供服务响应
                            Thread.sleep(50);
                        }
                    }
                }
            }
        } catch (Exception e) {
            params.put("ERROR_MESSAGE", e.getMessage());
            if (log.isErrorEnabled()) {
            	log.error(e.getMessage(), e);
            }
            throw e;
        }
        prepareSummary(smsToSend, params, success);
        return true;
    }

    private String getPassword(Long marketId) {
        if (smsConfigration.isReady()) {
            MessageSmsAccount messageSmsAccount = smsConfigration.getMessageSmsAccount(marketId);
            if (messageSmsAccount != null) {
                return messageSmsAccount.getPassword();
            }
        }
        return null;
    }

    /**
     * set summary to param map, SendMessageJob will use it.
     *
     * @param messages
     *            sms to send
     * @param param
     *            execution param
     */
    private void prepareSummary(List<Message> messages, Map<String, Object> param, int success) {
        StringBuilder sb = new StringBuilder();
        if (messages.isEmpty()) {
            sb.append("No SMS To Send.");
        } else {
            sb.append("Send ").append(messages.size()).append(" SMS.");
            sb.append("  Success : " + success);
            Object object = param.get("ERROR_MESSAGE");
            if (object != null) {
                sb.append("  Error :  " + object);
            }
        }
        param.put(SendMessageJob.SUMMARY, sb.toString());
    }

    private void trySaveTransaction(Message message, MessageTransaction obj) {
        for (int i = 0; i < dbTryTime; i++) {
            try {
                self().saveTransaction(message, obj);
                break;
            } catch (Exception e) {
                if (i == dbTryTime - 1) {
                    if (log.isErrorEnabled()) {
                        log.error("save transaction failed.", e);
                    }
                }
            }
        }
    }

    public void error(Message currentMessage, String msg) {
        MessageTransaction obj = new MessageTransaction();
        Date time = new Date();
        obj.setCreatedBy(-1L);
        obj.setLastUpdatedBy(-1L);
        obj.setCreationDate(time);
        obj.setLastUpdateDate(time);
        obj.setMessageId(currentMessage.getMessageId());
        obj.setObjectVersionNumber(0L);

        obj.setTransactionStatus(EmailStatusEnum.ERROR.getCode());
        obj.setTransactionMessage(msg);
        this.trySaveTransaction(null, obj);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    @Override
    public void saveTransaction(Message message, MessageTransaction obj) {
        if (message != null) {
            messageMapper.updateByPrimaryKeySelective(message);
        }
        transactionMapper.insert(obj);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 当发出sms带有sit,uat等标记的时候,发出sms内容上加入[sit],[uat]等标记.
     * 
     * @param smsSender
     * @param subject
     * @return
     */
    private String setSmsContent(SmsSender smsSender, String subject) {
        if ((EnvironmentEnum.SIT.getCode().equals(smsSender.getEnvironment())
                || EnvironmentEnum.UAT.getCode().equals(smsSender.getEnvironment()))) {
            return "[" + smsSender.getEnvironment() + "] " + subject;
        }
        return subject;
    }

    private String getExceptionStack(Throwable th) {
        return ExceptionUtils.getStackTrace(th);
    }
}
