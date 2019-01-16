/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.system.dto.*;
import com.lkkhpg.dsis.common.system.mapper.SysMessageReadMapper;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageResultMapper;
import com.lkkhpg.dsis.common.system.mapper.SysScheduleMessageMapper;
import com.lkkhpg.dsis.platform.dto.BaseDTO;
import com.lkkhpg.dsis.platform.dto.system.*;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.mail.PriorityLevelEnum;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.MessageEmailAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageSmsAccountMapper;
import com.lkkhpg.dsis.platform.mapper.system.MessageTemplateMapper;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.impl.MessageServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.*;

/**
 * @author shiliyan
 *
 */
@Service
public class ManualMessageServiceImpl implements IManualMessageService {

    private static final String Y = "Y";
    private static final String SITE_MESSAGE = "SITE_MESSAGE";
    private static final String N = "N";
    private static final int I_3999 = 3999;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private SysMessageReadMapper sysMessageReadMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SysScheduleMessageMapper sysScheduleMessageMapper;
    @Autowired
    private MessageTemplateMapper templateMapper;
    @Autowired
    private com.lkkhpg.dsis.common.system.mapper.SysMsMessageMapper sysMsMessageMapper;
    @Autowired
    private MessageSmsAccountMapper messageSmsAccountMapper;

    @Autowired
    private MessageEmailAccountMapper messageEmailAccountMapper;
    @Autowired
    private SysMsMessageResultMapper sysMsMessageResultMapper;

    private Logger logger = LoggerFactory.getLogger(ManualMessageServiceImpl.class);

    @Override
    public List<SiteMessageRead> queryMessage(SiteMessageRead record, int page, int pagesize) {
        // new Date(record.getPublishDate());
        PageHelper.startPage(page, pagesize);
        return sysMessageReadMapper.selectMessageByAccount(record);
    }

    private void initStd(BaseDTO dto, Long userId, Date now) {
        dto.setObjectVersionNumber(1L);
        dto.setCreatedBy(userId == null ? -1L : userId);
        dto.setCreationDate(now);
        dto.setLastUpdatedBy(userId == null ? -1L : userId);
        dto.setLastUpdateDate(now);
    }

    private Message doPublishSiteMessage(SysMsMessage sysMSMessage, List<SysMsMessageAssign> sysMSMessageAssigns,
            List<String> memberBlackList) {
        String messageType = sysMSMessage.getPublishChannel();
        MessageTypeEnum type = MessageTypeEnum.valueOf(messageType);
        String messageName = sysMSMessage.getMessageName();
        String messageContent = sysMSMessage.getMessageContent();
        Message message = new Message();
        message.setMessageType(type.getCode());
        message.setPriorityLevel(PriorityLevelEnum.NORMAL.getCode());
        message.setSubject(messageName);
        message.setContent(messageContent);
        message.setMessageFrom(SITE_MESSAGE);
        message.setSendFlag(Y);
        initStd(message, sysMSMessage.getSender(), new Date());
        messageMapper.insert(message);
        for (SysMsMessageAssign sysMsMessageAssign : sysMSMessageAssigns) {
            if (memberBlackList.contains(sysMsMessageAssign.getAssignValue())) {
                continue;
            }
            SysMessageRead read = new SysMessageRead();
            /*modified by furong.tang*/
            if(sysMsMessageAssign.getAccountId() == null){
                read.setAccountId(Long.valueOf(1));
            }else{
                read.setAccountId(Long.valueOf(sysMsMessageAssign.getAccountId()));
            }
            read.setMessageId(message.getMessageId());
            read.setStatus(SysMessageRead.NEW);
            read.setSender(sysMSMessage.getSender());
            initStd(read, sysMSMessage.getSender(), new Date());
            sysMessageReadMapper.insert(read);
        }
        return message;
    }

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
            template.process(data, out);
            return out.toString();
        }
    }

    @Override
    public void publishMessage(SysMsMessage sysMSMessage, List<SysMsMessageAssign> sysMSMessageAssigns,
            String templateCode, Map<String, Object> data, Long marketId) throws Exception {

        MessageTemplate record = new MessageTemplate();
        record.setTemplateCode(templateCode);
        List<MessageTemplate> selectMessageTemplates = templateMapper.selectMessageTemplates(record);

        if (selectMessageTemplates == null || selectMessageTemplates.size() == 0) {
            // 没有该模板
            throw new EmailException(MessageServiceImpl.MSG_NO_MESSAGE_TEMPLATE);
        }
        MessageTemplate template = null;
        for (MessageTemplate messageTemplate : selectMessageTemplates) {

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

        sysMSMessage.setMessageType(template.getTemplateType());
        sysMSMessage.setMessageName(translateData(template.getSubject(), data));
        sysMSMessage.setMessageContent(translateData(template.getContent(), data));
        this.publishMessage(sysMSMessage, sysMSMessageAssigns);
    }

    @Override
    @Transactional
    public void publishMessage(SysMsMessage sysMSMessage, List<SysMsMessageAssign> sysMSMessageAssigns)
            throws Exception {
        String messageType = sysMSMessage.getPublishChannel();
        MessageTypeEnum type = MessageTypeEnum.valueOf(messageType);
        String from = MessageServiceImpl.from(sysMSMessage.getSenderMarketId(), sysMSMessage.getSenderCodeText());
        // String senderCode = sysMSMessage.getSenderCode();
        Long sender = sysMSMessage.getSender();
        String messageName = sysMSMessage.getMessageName();
        String messageContent = sysMSMessage.getMessageContent();
        // filter
        List<String> memberBlackList = getMemberBlackList(sysMSMessage, sysMSMessageAssigns);
        Message message = null;
        if (MessageTypeEnum.DSIS.equals(type)) {
            message = doPublishSiteMessage(sysMSMessage, sysMSMessageAssigns, memberBlackList);
        } else {
            List<MessageReceiver> receivers = new ArrayList<MessageReceiver>();
            for (SysMsMessageAssign sysMsMessageAssign : sysMSMessageAssigns) {
                if (memberBlackList.contains(sysMsMessageAssign.getAssignValue())) {
                    continue;
                }
                MessageReceiver receiver = new MessageReceiver();
                receiver.setMessageAddress(sysMsMessageAssign.getMessageAddress(type));
                receiver.setMessageType(getMessageType(type));
                receivers.add(receiver);
            }
            if (receivers.size() != 0) {
                switch (type) {
                case SMS:
                    message = messageService.sendSmsMessage(sender, sysMSMessage.getSenderMarketId(),
                            sysMSMessage.getSenderCodeText(), messageName, messageContent, PriorityLevelEnum.NORMAL,
                            receivers);
                    break;
                case EMAIL:
                    message = messageService.sendEmailMessage(sender, sysMSMessage.getSenderMarketId(),
                            sysMSMessage.getSenderCodeText(), messageName, messageContent, PriorityLevelEnum.NORMAL,
                            receivers, null);
                    break;
                default:
                    break;
                }
            }
        }
        Long msMessageId = sysMSMessage.getMsMessageId();
        if (msMessageId != null) {
            sysMSMessage.setMessageStatus(SysMsMessage.SEND);
            sysMSMessage.setPublishDate(new Date());
            sysMsMessageMapper.updateByPrimaryKeySelective(sysMSMessage);

            List<SysMsMessagePublishResult> results = new ArrayList<SysMsMessagePublishResult>();

            for (SysMsMessageAssign assign : sysMSMessageAssigns) {
                SysMsMessagePublishResult result = new SysMsMessagePublishResult();
                result.setMsMessageId(msMessageId);
                result.setSysMessageId(message == null ? -1 : message.getMessageId());
                String assignType = assign.getAssignType();
                result.setAssignType(assignType);
                result.setAssignValue(assign.getAssignValue());
                result.setPublishStatus(memberBlackList.contains(assign.getAssignValue()) ? "F" : "Y");
                results.add(result);
            }

            for (SysMsMessagePublishResult sysMsMessagePublishResult : results) {
                sysMsMessageResultMapper.insertSelective(sysMsMessagePublishResult);
            }
        }
    }

    public String getMessageType(MessageTypeEnum type) {

        switch (type) {
        case SMS:
            return ReceiverTypeEnum.NORMAL.getCode();

        case EMAIL:
            return ReceiverTypeEnum.BCC.getCode();
        default:
            return ReceiverTypeEnum.NORMAL.getCode();
        }
    }

    private List<String> getMemberBlackList(SysMsMessage sysMSMessage, List<SysMsMessageAssign> sysMSMessageAssigns) {
        String mmType = sysMSMessage.getMessageType();
        StringBuilder sb = new StringBuilder();
        List<Member> mmBMembers = new ArrayList<Member>();
        
        String AD_OPT_IN = null;
        String SYS_MSG_IN = null;
        if (SysMsMessage.ADVER.equals(mmType)) {
            AD_OPT_IN = N;
        }
        if (SysMsMessage.SYSME.equals(mmType)) {
            SYS_MSG_IN = N;
        }
        // 限定字符长度最大4000
        for (SysMsMessageAssign sysMsMessageAssign : sysMSMessageAssigns) {
            if (SysMsMessageAssign.MEM.equals(sysMsMessageAssign.getAssignType())) {
                String assignValue = sysMsMessageAssign.getAssignValue();
                sb.append("{");
                sb.append(assignValue);
                sb.append("}");
                sb.append(",");

                if (sb.length() > I_3999) {
                    List<Member> selectMemberByMsgMemberIds = memberMapper.selectMemberByMsgMemberIds(sb.toString(),
                            AD_OPT_IN, SYS_MSG_IN);
                    mmBMembers.addAll(selectMemberByMsgMemberIds);
                    sb = new StringBuilder();
                }
            }
        }
        if (sb.length() > 1) {
            List<Member> selectMemberByMsgMemberIds = memberMapper.selectMemberByMsgMemberIds(sb.toString(), AD_OPT_IN,
                    SYS_MSG_IN);
            mmBMembers.addAll(selectMemberByMsgMemberIds);
        }
        List<String> result = new ArrayList<String>();
        for (Member member : mmBMembers) {
            result.add("" + member.getMemberId());
        }
        return result;
    }

    @Override
    @Transactional
    public List<SiteMessageRead> readMessage(SysMessageRead record) {
        record.setStatus(SysMessageRead.READ);
        updateMessage(record);
        return sysMessageReadMapper.selectMessageByReadID(record.getReadId());
    }

    @Override
    public void delMessage(SysMessageRead record) {
        record.setStatus(SysMessageRead.DEL);
        updateMessage(record);
    }

    @Override
    public Map<SysMsScheduleMessage, List<SysMsMessageAssign>> publishSchedulMemberMessage() {
        List<SysMsScheduleMessage> selectScheduleMessage4Member = sysScheduleMessageMapper
                .selectScheduleMessage4Member();

        fillSMSEmailAccount(selectScheduleMessage4Member);

        Map<SysMsScheduleMessage, List<SysMsMessageAssign>> doPublish = doPublish(selectScheduleMessage4Member);
        return doPublish;
    }

    public void fillSMSEmailAccount(List<SysMsScheduleMessage> selectScheduleMessage4Member) {
        List<MessageEmailAccountVo> selectMessageEmailAccounts = messageEmailAccountMapper
                .selectMessageEmailAccounts(new MessageEmailAccount());
        List<MessageSmsAccountVo> selectMessageSmsAccounts = messageSmsAccountMapper
                .selectMessageSmsAccounts(new MessageSmsAccount());

        for (SysMsScheduleMessage sysMsScheduleMessage : selectScheduleMessage4Member) {
            String publishChannel = sysMsScheduleMessage.getPublishChannel();
            if (MessageTypeEnum.SMS.getCode().equals(publishChannel)) {
                MessageSmsAccountVo sms = querySMS(selectMessageSmsAccounts, sysMsScheduleMessage.getSenderCode());
                if (sms == null) {
                    continue;
                }
                sysMsScheduleMessage.setSenderCodeText(sms.getAccountCode());
                sysMsScheduleMessage.setSenderMarketId(sms.getMarketId());
            }
            if (MessageTypeEnum.EMAIL.getCode().equals(publishChannel)) {
                MessageEmailAccountVo em = queryEM(selectMessageEmailAccounts, sysMsScheduleMessage.getSenderCode());
                if (em == null) {
                    continue;
                }
                sysMsScheduleMessage.setSenderCodeText(em.getAccountCode());
                sysMsScheduleMessage.setSenderMarketId(em.getMarketId());
            }
        }
    }

    private MessageEmailAccountVo queryEM(List<MessageEmailAccountVo> selectMessageEmailAccounts, String senderCode) {

        for (MessageEmailAccountVo messageEmailAccountVo : selectMessageEmailAccounts) {
            if (messageEmailAccountVo.getAccountId().toString().equals(senderCode)) {
                return messageEmailAccountVo;
            }
        }
        return null;
    }

    private MessageSmsAccountVo querySMS(List<MessageSmsAccountVo> selectMessageSmsAccounts, String senderCode) {
        for (MessageSmsAccountVo a : selectMessageSmsAccounts) {
            if (a.getAccountId().toString().equals(senderCode)) {
                return a;
            }
        }
        return null;
    }

    private Map<SysMsScheduleMessage, List<SysMsMessageAssign>> doPublish(
            List<SysMsScheduleMessage> selectScheduleMessage4Member) {
        Map<SysMsScheduleMessage, List<SysMsMessageAssign>> messages = new HashMap<SysMsScheduleMessage, List<SysMsMessageAssign>>();
        for (SysMsScheduleMessage sysMsScheduleMessage : selectScheduleMessage4Member) {
            SysMsMessageAssign sysMsMessageAssign = sysMsScheduleMessage.getSysMsMessageAssign();
            String messageType = sysMsScheduleMessage.getPublishChannel();
            MessageTypeEnum type = MessageTypeEnum.valueOf(messageType);
            String messageAddress = sysMsMessageAssign.getMessageAddress(type);
            if (messageAddress == null || "".equals(messageAddress.trim())) {
                continue;
            }
            List<SysMsMessageAssign> list = messages.get(sysMsScheduleMessage);
            if (list == null) {
                list = new ArrayList<SysMsMessageAssign>();
                messages.put(sysMsScheduleMessage, list);
            }
            list.add(sysMsMessageAssign);
        }
        Set<SysMsScheduleMessage> keySet = messages.keySet();
        for (SysMsScheduleMessage sysMsScheduleMessage : keySet) {
            List<SysMsMessageAssign> list = messages.get(sysMsScheduleMessage);
            if (list == null || list.isEmpty()) {
                continue;
            }
            try {
                this.publishMessage(sysMsScheduleMessage, list);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage());
                }
            }
        }
        return messages;
    }

    @Override
    public Map<SysMsScheduleMessage, List<SysMsMessageAssign>> publishSchedulUserMessage() {
        List<SysMsScheduleMessage> selectScheduleMessage4User = sysScheduleMessageMapper.selectScheduleMessage4User();

        fillSMSEmailAccount(selectScheduleMessage4User);

        Map<SysMsScheduleMessage, List<SysMsMessageAssign>> doPublish = doPublish(selectScheduleMessage4User);

        return doPublish;
    }

    @Override
    public void updateMessage(SysMessageRead record) {
        sysMessageReadMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SiteMessageRead queryNext(SiteMessageRead record) {
        return sysMessageReadMapper.queryNext(record);
    }

    @Override
    public List<SiteMessageRead> queryMessageMws(SiteMessageRead record, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return sysMessageReadMapper.selectMessageByMwsAccount(record);
    }

    @Override
    public int getMessageUnreadCount(Long accountId) {
        int ig = sysMessageReadMapper.getMessageUnreadCount(accountId);
        return ig;
    }

}
