/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.platform.sms.SmsSender;
import com.lkkhpg.dsis.platform.utils.SmsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.system.Message;
import com.lkkhpg.dsis.platform.dto.system.MessageAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageAttachment;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccountVo;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageTemplate;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.mail.PriorityLevelEnum;
import com.lkkhpg.dsis.platform.mapper.system.MessageAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageAttachmentMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageReceiverMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageTemplateMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageTransactionMapper;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Clerifen Li
 * @author xiawang.liu@hand-china.com
 */
@Transactional
@Service
public class MessageServiceImpl implements IMessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    // 没有收件人不能编辑修改
    private static final String MSG_NO_MESSAGE_RECEIVER = "msg.warning.system.no_message_receiver";

    // 没有该邮件模板
    public static final String MSG_NO_MESSAGE_TEMPLATE = "msg.warning.system.no_message_template";

    // 邮件已经发出不能修改
    private static final String MSG_MESSAGE_HAS_SEND = "msg.warning.system.message_has_send";

    // 信息不全
    private static final String MSG_MESSAGE_TYPE_EMPTY = "msg.warning.system.email_message_type_empty";

    // 没有设置优先级
    private static final String MSG_MESSAGE_PRIORITY_EMPTY = "msg.warning.system.email_message_priority_empty";

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
    private MessageTemplateMapper templateMapper;

    @Autowired
    private MessageAccountMapper accountMapper;

    @Autowired
    private MessageSmsAccountMapper smsAccountMapper;

    @Autowired
    private MessageEmailAccountMapper emailAccountMapper;

    @Override
    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    public Message addMessage(Long userId, String templateCode, Map<String, Object> data, List<Long> attachmentIds,
                              List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        MessageTemplate template = templateMapper.selectByCode(templateCode);
        if (template == null) {
            // 没有该模板
            throw new EmailException(MSG_NO_MESSAGE_TEMPLATE);
        }
        if (template.getTemplateType() == null) {
            // 没有消息类型
            throw new EmailException(MSG_MESSAGE_TYPE_EMPTY);
        }
        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageAccount account = accountMapper.selectByPrimaryKey(template.getAccountId());
        Message message = new Message();
        message.setMessageType(template.getTemplateType());
        message.setPriorityLevel(template.getPriorityLevel());
        message.setSubject(translateData(template.getSubject(), data));
        message.setContent(translateData(template.getContent(), data));
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 附件
        if (attachmentIds != null && attachmentIds.size() > 0) {
            for (Long current : attachmentIds) {
                MessageAttachment attachment = new MessageAttachment();
                attachment.setAttachmentId(current);
                attachment.setMessageId(message.getMessageId());
                initStd(attachment, userId, now);

                attachmentMapper.insert(attachment);
            }
        }
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    /**
     * 转换模板数据[freemarker].
     *
     * @param content
     * @param data
     * @return
     * @throws Exception
     */
    private String translateData(String content, Map data) throws Exception {
        if (content == null) {
            return "";
        }
        try (StringWriter out = new StringWriter()) {
            Configuration config = new Configuration();
            config.setDefaultEncoding("utf-8");
            // classic compatible,是${abc}允许出现空值的
            config.setClassicCompatible(true);
            Template template = new Template(null, content, config);
//            System.out.println("//////////////////////////////");
//            System.out.println(data.toString());
            template.process(data, out);
            return out.toString();
        }
    }

    public static void main(String[] args) {
        Map<Object, Object> mm = new HashMap<Object, Object>();
        Map<Object, Object> m1 = new HashMap<Object, Object>();
        Map<Object, Object> m2 = new HashMap<Object, Object>();
        Map<Object, Object> m3 = new HashMap<Object, Object>();

        m1.put("m1", "m1");
        m2.put("m2", "m2");
        m3.put("m3", "m3");

        mm.put("m1", m1);

        mm.put("m2", m2);

        mm.put("m3", m3);

        String content = "" + "${m1.m1}" + "*****" + "${m2.m2}" + "*****" + "${m3.m3}" + "*****" + "";

        try {
            String translateData = new MessageServiceImpl().translateData(content, mm);

            //System.out.println(translateData);
        } catch (Exception e) {
            //e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    @Override
    public List<Message> selectMessages(Message message, int page, int pagesize) throws Exception {
        PageHelper.startPage(page, pagesize);
        return messageMapper.selectMessages(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Message message) throws Exception {
        transactionMapper.deleteByMessageId(message.getMessageId());
        attachmentMapper.deleteByMessageId(message.getMessageId());
        receiverMapper.deleteByMessageId(message.getMessageId());
        return messageMapper.deleteByPrimaryKey(message.getMessageId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Message> messages) throws Exception {
        for (Message message : messages) {
            transactionMapper.deleteByMessageId(message.getMessageId());
            attachmentMapper.deleteByMessageId(message.getMessageId());
            receiverMapper.deleteByMessageId(message.getMessageId());
            messageMapper.deleteByPrimaryKey(message.getMessageId());
        }
    }

    @Override
    public List<Message> selectMessagesBySubject(IRequest requestContext, Message message, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return messageMapper.selectMessagesBySubject(message);
    }

    @Override
    public List<MessageReceiver> selectMessageAddressesByMessageId(IRequest requestContext,
                                                                   MessageReceiver messageReceiver, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return receiverMapper.selectMessageAddressesByMessageId(messageReceiver);
    }

    private void initStd(BaseDTO dto, Long userId, Date now) {
        dto.setObjectVersionNumber(1L);
        dto.setCreatedBy(userId == null ? -1L : userId);
        dto.setCreationDate(now);
        dto.setLastUpdatedBy(userId == null ? -1L : userId);
        dto.setLastUpdateDate(now);
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addEmailMessage(Long userId, String accountCode, String templateCode, Map<String, Object> data,
                                   List<Long> attachmentIds, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        MessageTemplate template = templateMapper.selectByCode(templateCode);
        if (template == null) {
            // 没有该模板
            throw new EmailException(MSG_NO_MESSAGE_TEMPLATE);
        }
        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageEmailAccount account = emailAccountMapper.selectByAccountCode(accountCode);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.EMAIL.getCode());
        message.setPriorityLevel(template.getPriorityLevel());
        message.setSubject(translateData(template.getSubject(), data));
        message.setContent(translateData(template.getContent(), data));
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 附件
        if (attachmentIds != null && attachmentIds.size() > 0) {
            for (Long current : attachmentIds) {
                MessageAttachment attachment = new MessageAttachment();
                attachment.setAttachmentId(current);
                attachment.setMessageId(message.getMessageId());
                initStd(attachment, userId, now);

                attachmentMapper.insert(attachment);
            }
        }
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addEmailMessage(Long userId, String accountCode, String subject, String content,
                                   PriorityLevelEnum priority, List<Long> attachmentIds, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        if (priority == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageEmailAccount account = emailAccountMapper.selectByAccountCode(accountCode);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.EMAIL.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 附件
        if (attachmentIds != null && attachmentIds.size() > 0) {
            for (Long current : attachmentIds) {
                MessageAttachment attachment = new MessageAttachment();
                attachment.setAttachmentId(current);
                attachment.setMessageId(message.getMessageId());
                initStd(attachment, userId, now);

                attachmentMapper.insert(attachment);
            }
        }
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addEmailMessage(Long userId, Long marketId, String templateCode, Map<String, Object> data,
                                   List<Long> attachmentIds, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        MessageTemplate template = templateMapper.selectByCode(templateCode);
        if (template == null) {
            // 没有该模板
            throw new EmailException(MSG_NO_MESSAGE_TEMPLATE);
        }
        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageEmailAccount account = emailAccountMapper.selectByMarketId(marketId);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.EMAIL.getCode());
        message.setPriorityLevel(template.getPriorityLevel());
        message.setSubject(translateData(template.getSubject(), data));
        message.setContent(translateData(template.getContent(), data));
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 附件
        if (attachmentIds != null && attachmentIds.size() > 0) {
            for (Long current : attachmentIds) {
                MessageAttachment attachment = new MessageAttachment();
                attachment.setAttachmentId(current);
                attachment.setMessageId(message.getMessageId());
                initStd(attachment, userId, now);

                attachmentMapper.insert(attachment);
            }
        }
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addEmailMessage(Long userId, Long marketId, String subject, String content,
                                   PriorityLevelEnum priority, List<Long> attachmentIds, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        if (priority == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageEmailAccount account = emailAccountMapper.selectByMarketId(marketId);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.EMAIL.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 附件
        if (attachmentIds != null && attachmentIds.size() > 0) {
            for (Long current : attachmentIds) {
                MessageAttachment attachment = new MessageAttachment();
                attachment.setAttachmentId(current);
                attachment.setMessageId(message.getMessageId());
                initStd(attachment, userId, now);

                attachmentMapper.insert(attachment);
            }
        }
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addSmsMessage(Long userId, String accountCode, String templateCode, Map<String, Object> data,
                                 List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        MessageTemplate template = templateMapper.selectByCode(templateCode);
        if (template == null) {
            // 没有该模板
            throw new EmailException(MSG_NO_MESSAGE_TEMPLATE);
        }
        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageSmsAccount account = smsAccountMapper.selectByAccountCode(accountCode);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.SMS.getCode());
        message.setPriorityLevel(template.getPriorityLevel());
        message.setSubject(translateData(template.getSubject(), data));
        message.setContent(translateData(template.getContent(), data));
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);
            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addSmsMessage(Long userId, String accountCode, String subject, String content,
                                 PriorityLevelEnum priority, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        if (priority == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageSmsAccount account = smsAccountMapper.selectByAccountCode(accountCode);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.SMS.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addSmsMessage(Long userId, Long marketId, String templateCode, Map<String, Object> data,
                                 List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        MessageTemplate template = templateMapper.selectByCode(templateCode);
        if (template == null) {
            // 没有该模板
            throw new EmailException(MSG_NO_MESSAGE_TEMPLATE);
        }
        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageSmsAccount account = smsAccountMapper.selectByMarketId(marketId);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.SMS.getCode());
        message.setPriorityLevel(template.getPriorityLevel());
        message.setSubject(translateData(template.getSubject(), data));
        message.setContent(translateData(template.getContent(), data));
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addSmsMessage(Long userId, Long marketId, String subject, String content, PriorityLevelEnum priority,
                                 List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        if (priority == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        MessageSmsAccount account = smsAccountMapper.selectByMarketId(marketId);
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.SMS.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");
        message.setMessageFrom(account.getAccountCode());
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addSiteMessage(Long userId, String templateCode, Map<String, Object> data,
                                  List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        MessageTemplate template = templateMapper.selectByCode(templateCode);
        if (template == null) {
            // 没有该模板
            throw new EmailException(MSG_NO_MESSAGE_TEMPLATE);
        }
        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.DSIS.getCode());
        message.setPriorityLevel(template.getPriorityLevel());
        message.setSubject(translateData(template.getSubject(), data));
        message.setContent(translateData(template.getContent(), data));
        message.setSendFlag("N");
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message addSiteMessage(Long userId, String subject, String content, PriorityLevelEnum priority,
                                  List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        if (priority == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        Date now = new Date();
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.SITE.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");
        initStd(message, userId, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, userId, now);

            receiverMapper.insert(current);
        }
        return message;
    }

    private MessageTemplate check(Long sender, Long marketId, String templateCode, String smsAccountCode,
                                  Map<String, Object> data, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        // MessageTemplate template = templateMapper.selectByCode(templateCode);

        MessageTemplate record = new MessageTemplate();
        record.setTemplateCode(templateCode);

//        System.out.println("++++++++++++++++++++++");
        List<MessageTemplate> selectMessageTemplates = templateMapper.selectMessageTemplates(record);

        if (selectMessageTemplates == null || selectMessageTemplates.size() == 0) {
            // 没有该模板
            throw new EmailException(MessageServiceImpl.MSG_NO_MESSAGE_TEMPLATE);
        }
        MessageTemplate template = null;
        for (MessageTemplate messageTemplate : selectMessageTemplates) {

//            System.out.println(marketId);
//            System.out.println(messageTemplate.getMarketId());

            if (marketId == null && messageTemplate.getMarketId() == null) {
                template = messageTemplate;
                break;
            }
            if (marketId != null && marketId.equals(messageTemplate.getMarketId())) {
                template = messageTemplate;
                break;
            }
        }

        if (template == null) {
            throw new EmailException(MessageServiceImpl.MSG_NO_MESSAGE_TEMPLATE);
        }

        if (template.getPriorityLevel() == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }
//        System.out.println("+++++++++++++++++++++++++++++++++++++");
//        System.out.println("template:" +template);
        return template;
    }

    private void check(Long sender, Long marketId, String smsAccountCode, String subject, String content,
                       PriorityLevelEnum priority, List<MessageReceiver> receivers) throws Exception {
        if (receivers == null || receivers.size() == 0) {
            // 没有设置收件人,报错
            throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
        }
        if (priority == null) {
            // 没有设置优先级
            throw new EmailException(MSG_MESSAGE_PRIORITY_EMPTY);
        }

        for (MessageReceiver messageReceiver : receivers) {
            String messageAddress = messageReceiver.getMessageAddress();
            if (messageAddress == null || "".equals(messageAddress.trim())) {
                throw new EmailException(MSG_NO_MESSAGE_RECEIVER);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message sendSmsMessage(Long sender, Long marketId, String templateCode, String smsAccountCode,
                                  Map<String, Object> data, List<MessageReceiver> receivers) throws Exception {

        Map<String, Object> data_for_content = new HashMap<String, Object>();
        data_for_content.put("verifyCode", data.get("verifyCode"));
        data_for_content.put("limit", data.get("limit"));
        data_for_content.put("loginName", data.get("loginName"));

        MessageTemplate template = this.check(sender, marketId, templateCode, smsAccountCode, data_for_content, receivers);

        String sms_interface_address = (String) data.get("sms_interface_address");
        String sms_userid = (String) data.get("sms_userid");
        String sms_username = (String) data.get("sms_username");
        String sms_password = (String) data.get("sms_password");

        String subject = translateData(template.getSubject(), data_for_content);
        String content = translateData(template.getContent(), data_for_content);

        String priorityS = template.getPriorityLevel();
        PriorityLevelEnum priority = PriorityLevelEnum.valueOf(priorityS);
        Message sendSmsMessage = this.sendSmsMessage(sender, marketId, smsAccountCode, subject, content, priority,
                receivers, sms_interface_address, sms_userid, sms_username, sms_password);
        return sendSmsMessage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message sendSmsMessage(Long sender, Long marketId, String smsAccountCode, String subject, String content,
                                  PriorityLevelEnum priority, List<MessageReceiver> receivers, String sms_interface_address,
                                  String sms_userid, String sms_username, String sms_password) throws Exception {
        this.check(sender, marketId, smsAccountCode, subject, content, priority, receivers);

        Date now = new Date();
        if (marketId == null) {
            // 没有设置marketId
            throw new EmailException("msg.warning.system.sms_message.marketId.empty");
        }
        MessageSmsAccount account = smsAccountMapper.selectByMarketId(marketId);

        if (account == null) {
            // 没有设置SMS Account
            throw new EmailException("msg.warning.system.sms_message.sms_account.empty");
        }

        Message message = new Message();
        message.setMessageType(MessageTypeEnum.SMS.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");

        message.setMessageFrom(from(marketId, account.getAccountCode()));
        initStd(message, sender, now);

        messageMapper.insert(message);
        // 收件人(抄送/接收人)

        SmsSend smsSend = new SmsSend();

        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, sender, now);

            receiverMapper.insert(current);
//            System.out.println("---------------------------");
//            System.out.println("current: " + current);
//            System.out.println("getContent: " + message.getContent());
            Boolean flag =  false;
            flag = smsSend.anOtherWaySendSms(current.getMessageAddress(), message.getContent(), sms_interface_address, sms_userid,
                    sms_username, sms_password);
//            System.out.println("flag: " + flag);
        }
        return message;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message sendEmailMessage(Long sender, Long marketId, String templateCode, String emailAccountCode,
                                    Map<String, Object> data, List<MessageReceiver> receivers, List<Long> attachmentIds) throws Exception {
        MessageTemplate template = this.check(sender, marketId, templateCode, emailAccountCode, data, receivers);
        String subject = translateData(template.getSubject(), data);
        String content = translateData(template.getContent(), data);
        String priorityS = template.getPriorityLevel();
        PriorityLevelEnum priority = PriorityLevelEnum.valueOf(priorityS);
        Message sendSmsMessage = this.sendEmailMessage(sender, marketId, emailAccountCode, subject, content, priority,
                receivers, attachmentIds);
        return sendSmsMessage;
    }


    public String sendMsg(String phones, String content) {
//短信接口URL提交地址
        String url = "短信接口URL提交地址";

        Map<String, String> params = new HashMap<String, String>();

        params.put("zh", "用户账号");
        params.put("mm", "用户密码");
        params.put("dxlbid", "短信类别编号");
        params.put("extno", "扩展编号");

        //手机号码，多个号码使用英文逗号进行分割
        params.put("hm", phones);
        //将短信内容进行URLEncoder编码
        params.put("nr", URLEncoder.encode(content));

//        return HttpRequestUtil.getRequest(url, params);
        return null;
    }


    public static String from(Long marketId, String accountCode) {

        StringBuilder sb = new StringBuilder();
        sb.append("I");
        sb.append(marketId);
        sb.append("://");
        sb.append(accountCode);
        return sb.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message sendEmailMessage(Long sender, Long marketId, String emailAccountCode, String subject, String content,
                                    PriorityLevelEnum priority, List<MessageReceiver> receivers, List<Long> attachmentIds) throws Exception {
        this.check(sender, marketId, emailAccountCode, subject, content, priority, receivers);

        Date now = new Date();
        MessageEmailAccount record = new MessageEmailAccount();
        record.setAccountCode(emailAccountCode);
        List<MessageEmailAccountVo> selectMessageEmailAccounts = emailAccountMapper.selectMessageEmailAccounts(record);

        if (selectMessageEmailAccounts == null || selectMessageEmailAccounts.size() == 0) {
            // 没有设置email Account
            throw new EmailException("msg.warning.system.sms_message.email_account.empty");
        }
        MessageEmailAccountVo account = null;
        for (MessageEmailAccountVo messageEmailAccount : selectMessageEmailAccounts) {
            if (marketId == null && messageEmailAccount.getMarketId() == null) {
                account = messageEmailAccount;
                break;
            }
            if (marketId != null && marketId.equals(messageEmailAccount.getMarketId())) {
                account = messageEmailAccount;
                break;
            }
        }
        if (account == null) {
            // 没有设置email Account
            throw new EmailException("msg.warning.system.sms_message.email_account.empty");
        }
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.EMAIL.getCode());
        message.setPriorityLevel(priority.getCode());
        message.setSubject(subject);
        message.setContent(content);
        message.setSendFlag("N");
        // message.setMessageFrom(account.getAccountCode());
        message.setMessageFrom(from(marketId, account.getAccountCode()));
        initStd(message, sender, now);

        // int insert = messageMapper.insert(message);
        messageMapper.insert(message);
        // 附件
        if (attachmentIds != null && attachmentIds.size() > 0) {
            for (Long current : attachmentIds) {
                MessageAttachment attachment = new MessageAttachment();
                // attachment.setAttachmentId(current);
                attachment.setMessageId(message.getMessageId());
                attachment.setFileId(current);
                initStd(attachment, sender, now);

                attachmentMapper.insert(attachment);
            }
        }
        // 收件人(抄送/接收人)
        for (MessageReceiver current : receivers) {
            current.setMessageId(message.getMessageId());
            initStd(current, sender, now);

            receiverMapper.insert(current);
        }
        // message.setMessageId(Long.valueOf(insert));
        return message;
    }

    @Override
    public Message sendSmsMessage(Long sender, Long marketId, String smsAccountCode, String subject, String content, PriorityLevelEnum priority, List<MessageReceiver> receivers) throws Exception {
        return null;
    }

    @Override
    public void sendSmsMessage(Long sender,String smsAccountCode,
                           Map<String, Object> data, List<MessageReceiver> receivers) throws Exception{



        String sms_interface_address = (String) data.get("sms_interface_address");
        String sms_userid = (String) data.get("sms_userid");
        String sms_username = (String) data.get("sms_username");
        String sms_password = (String) data.get("sms_password");


        SmsSend smsSend = new SmsSend();
        String content ="【天府电商】尊敬的用户：我们已收到您的注册请求，验证码为： "+data.get("verifyCode")+", 有效期为:10分钟。";

        for (MessageReceiver current : receivers) {
            smsSend.anOtherWaySendSms(current.getMessageAddress(), content, sms_interface_address, sms_userid,
                    sms_username, sms_password);
        }
        //System.out.println(content);
    }

}