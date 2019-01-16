/*
 *
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.controllers.configurations.EmailConfiguration;
import com.lkkhpg.dsis.platform.dto.attachment.SysFile;
import com.lkkhpg.dsis.platform.dto.system.Message;
import com.lkkhpg.dsis.platform.dto.system.MessageAddress;
import com.lkkhpg.dsis.platform.dto.system.MessageAttachment;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccountVo;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailConfig;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailWhiteList;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.MessageTransaction;
import com.lkkhpg.dsis.platform.job.SendMessageJob;
import com.lkkhpg.dsis.platform.mail.EmailStatusEnum;
import com.lkkhpg.dsis.platform.mail.EnvironmentEnum;
import com.lkkhpg.dsis.platform.mail.MailSender;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.MessageAttachmentMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailConfigMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailWhiteListMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageReceiverMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageTransactionMapper;
import com.lkkhpg.dsis.platform.service.IEmailService;
import com.lkkhpg.dsis.platform.service.attachment.ISysFileService;

/**
 * @author Clerifen Li
 */
@Transactional
@Service
public class EmailServiceImpl implements IEmailService, BeanFactoryAware {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final int dbTryTime = 3;

    private static final int TWENTY = 20;

    private static final int FIFTY = 50;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageAttachmentMapper attachmentMapper;

    @Autowired
    private MessageReceiverMapper receiverMapper;

    @Autowired
    private MessageTransactionMapper transactionMapper;

    @Autowired
    private MessageEmailAccountMapper emailAccountMapper;

    @Autowired
    private MessageEmailWhiteListMapper emailWhiteListMapper;

    @Autowired
    private MessageEmailConfigMapper emailConfigMapper;

    private BeanFactory beanFactory;

    @Autowired
    private ISysFileService sysFileService;

    // @Autowired
    // private IAESClientService aceClientService;
    @Autowired
    private EmailConfiguration configration;

    @Override
    public boolean sendMessage(boolean isVipQueue, Map<String, Object> params) throws Exception {

        Map<String, MailSender> senders = new HashMap<String, MailSender>();
        /**
         * 发送邮件部分,没有发送出去的 限制一次取100条,防止堆溢出
         */
        Integer batch = (Integer) params.get("batch");
        int success = 0;
        if (batch == null) {
            batch = TWENTY;
        }
        if (batch == 0) {
            batch = TWENTY;
        }
        PageHelper.startPage(1, batch);
        List<Message> userEmailToSend = null;
        if (isVipQueue) {
            userEmailToSend = messageMapper.selectVipEmailToSend();
        } else {
            userEmailToSend = messageMapper.selectEmailToSend();
        }
        try {

            for (Message currentMessage : userEmailToSend) {
                List<MessageReceiver> receivers = receiverMapper.selectByMessageId(currentMessage.getMessageId());
                List<MessageAttachment> attachments = attachmentMapper.selectByMessageId(currentMessage.getMessageId());

                String messageFrom = currentMessage.getMessageFrom();
                MessageAddress address = MessageAddress.toAddressObject(messageFrom);
                messageFrom = address.getAddress();
                String iMarket = address.getType();

                if (messageFrom == null || iMarket == null) {
                    error(currentMessage, "iMarket is no more exists:" + messageFrom);
                    continue;
                }

                // 获得mailSender,currentMessage.getMessageFrom()是noReplyAccount的发送邮箱编号

                MailSender mailSender = senders.get(messageFrom);
                if (mailSender == null) {
                    mailSender = (MailSender) beanFactory.getBean("mailSender");

                    MessageEmailAccount record = new MessageEmailAccount();
                    record.setAccountCode(messageFrom);
                    List<MessageEmailAccountVo> selectMessageEmailAccounts = emailAccountMapper
                            .selectMessageEmailAccounts(record);

                    if (selectMessageEmailAccounts == null || selectMessageEmailAccounts.size() == 0) {
                        error(currentMessage, "email account is no more exists:" + messageFrom);
                        continue;
                    }
                    MessageEmailAccountVo account = null;
                    for (MessageEmailAccountVo messageEmailAccount : selectMessageEmailAccounts) {
                        if (iMarket.equals("I" + messageEmailAccount.getMarketId())) {
                            account = messageEmailAccount;
                            break;
                        }
                    }
                    MessageEmailAccountVo mailAccount = account;

                    // email账号被删除的情况
                    if (mailAccount == null) {
                        error(currentMessage, "email account is no more exists:" + messageFrom);
                        continue;
                    }

                    MessageEmailConfig config = emailConfigMapper.selectByPrimaryKey(mailAccount.getConfigId());
                    mailSender.setHost(config.getHost());
                    mailSender.setPort(Integer.parseInt(config.getPort()));
                    if (config.getTryTimes() != null) {
                        mailSender.setTryTimes(config.getTryTimes().intValue());
                    }
                    mailSender.setMessageAccount(mailAccount.getUserName());
                    mailSender.setUsername(config.getUserName());
                    // 是否需要密码
                    if (config.getPassword() != null) {

                        String password = this.getPassword(config.getConfigId());
                        if (password == null) {
                            error(currentMessage, "email could not found  password of account : " + config);
                            continue;
                        } else {
                            mailSender.setPassword(password);
                        }
                    }

                    // 白名单
                    if (config.getUseWhiteList() != null && config.getUseWhiteList().equalsIgnoreCase("Y")) {
                        List<MessageEmailWhiteList> whitelist = emailWhiteListMapper
                                .selectByConfigId(config.getConfigId());
                        List<String> stringList = new ArrayList<String>();
                        for (MessageEmailWhiteList current : whitelist) {
                            stringList.add(current.getAddress());
                        }
                        mailSender.setWhiteList(stringList);
                    }
                    senders.put(messageFrom, mailSender);
                }

                // 生成邮件
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
                    obj.setTransactionStatus(EmailStatusEnum.ERROR.getCode());
                    obj.setTransactionMessage("title or content is null");
                    this.trySaveTransaction(null, obj);
                    continue;
                }

                // 创建多元化邮件
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                // 创建 mimeMessage 帮助类，用于封装信息至 mimeMessage
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                // 发送者地址，必须填写正确的邮件格式，否者会发送失败
                helper.setFrom(mailSender.getMessageAccount());
                // 邮件主题
                helper.setSubject(this.setEmailSubject(mailSender, currentMessage.getSubject()));
                // 邮件内容
                helper.setText(currentMessage.getContent(), true);

                // 附件
                if (attachments != null && attachments.size() > 0) {
                    List<String> attachmentIds = new ArrayList<String>();
                    for (MessageAttachment attachment : attachments) {
                        attachmentIds.add(attachment.getFileId().toString());
                    }
                    List<SysFile> fileNames = sysFileService.selectByIds(null, attachmentIds);
                    for (SysFile sysFile : fileNames) {
                        File file = new File(sysFile.getFilePath());
                        if (file.isFile()) {
                            helper.addAttachment(FilenameUtils.getName(sysFile.getFileName()), file);
                        }
                    }
                }

                boolean hasSendTO = false;
                try {

                    for (MessageReceiver receiver : receivers) {
                        if (receiver.getMessageAddress() == null || "".equals(receiver.getMessageAddress().trim())) {
                            continue;
                        }
                        // 白名单过滤
                        // mailSender.getWhiteList() != null
                        // &&
                        // !mailSender.getWhiteList().contains(receiver.getMessageAddress())
                        if (mailSender.getWhiteList() != null
                                && !mailSender.checkWhiteList(receiver.getMessageAddress())) {
                            continue;
                        }
                        if (ReceiverTypeEnum.NORMAL.getCode().equals(receiver.getMessageType())) {
                            hasSendTO = true;
                            // 收件人
                            helper.addTo(receiver.getMessageAddress());
                        } else if (ReceiverTypeEnum.CC.getCode().equals(receiver.getMessageType())) {
                            hasSendTO = true;
                            // 抄送
                            helper.addCc(receiver.getMessageAddress());
                        } else if (ReceiverTypeEnum.BCC.getCode().equals(receiver.getMessageType())) {
                            hasSendTO = true;
                            // 密送
                            helper.addBcc(receiver.getMessageAddress());
                        }
                    }
                } catch (Exception e) {
                    // wrong address such as '@.'
                    hasSendTO = false;
                }
                // -------------------------------------------------------------------------------------
                if (hasSendTO == false) {
                    obj.setTransactionStatus(EmailStatusEnum.SUCCESS.getCode());
                    currentMessage.setSendFlag("F");// 此处改成F表示永久失败
                    this.trySaveTransaction(currentMessage, obj);
                    continue;
                }
                // 失败,重试次数
                for (int i = 0; i < mailSender.getTryTimes(); i++) {
                    try {
                        mailSender.send(mimeMessage);
                        success++;
                        if (log.isDebugEnabled()) {
                            log.debug("Send mail success, {}.", i);
                        }
                        obj.setTransactionStatus(EmailStatusEnum.SUCCESS.getCode());
                        currentMessage.setSendFlag("Y");
                        this.trySaveTransaction(currentMessage, obj);
                        break;
                    } catch (Exception e) {
                        if (i == mailSender.getTryTimes() - 1) {
                            obj.setTransactionMessage(getExceptionStack(e));
                            obj.setTransactionStatus(EmailStatusEnum.ERROR.getCode());
                            currentMessage.setSendFlag("F");// 此处改成F表示永久失败
                            this.trySaveTransaction(currentMessage, obj);
                            if (log.isErrorEnabled()) {
                                log.error("Send mail failed.", e);
                            }
                            params.put("ERROR_MESSAGE", e.getMessage());
                        } else {
                            Thread.sleep(FIFTY);
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
        prepareSummary(userEmailToSend, params, success);
        return true;
    }

    private String getPassword(Long configId) {
        if (configration.isReady()) {
            MessageEmailConfig messageEmailConfig = configration.getMessageEmailConfig(configId);
            if (messageEmailConfig != null) {
                return messageEmailConfig.getPassword();
            }
        }
        return null;
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

    /**
     * set summary to param map, SendMessageJob will use it.
     * 
     * @param messages
     *            emails to send
     * @param param
     *            execution param
     */
    private void prepareSummary(List<Message> messages, Map<String, Object> param, int success) {
        StringBuilder sb = new StringBuilder();
        if (messages.isEmpty()) {
            sb.append("No Email To Send.");
        } else {
            sb.append("Send ").append(messages.size()).append(" Emails. ");
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
                return;
            } catch (Exception e) {
                if (i == dbTryTime - 1) {
                    if (log.isErrorEnabled()) {
                        log.error("save transaction failed.", e);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
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
     * 当发出邮箱带有sit,uat等标记的时候,发出邮件主题上加入[sit],[uat]等标记.
     * 
     * @param mailSender
     * @param subject
     * @return
     */
    private String setEmailSubject(MailSender mailSender, String subject) {
        if ((EnvironmentEnum.SIT.getCode().equals(mailSender.getEnvironment())
                || EnvironmentEnum.UAT.getCode().equals(mailSender.getEnvironment()))) {
            return "[" + mailSender.getEnvironment() + "] " + subject;
        }
        return subject;
    }

    private String getExceptionStack(Throwable th) {
        return ExceptionUtils.getStackTrace(th);
    }
}
