/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.MessageTemplate;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.mapper.system.MessageTemplateMapper;
import com.lkkhpg.dsis.platform.service.IMessageTemplateService;

/**
 * 消息模板impl
 * 
 * @author Clerifen Li
 */
@Transactional
@Service
public class MessageTemplateServiceImpl implements IMessageTemplateService {

    @Autowired
    private MessageTemplateMapper templateMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageTemplate createMessageTemplate(IRequest request, MessageTemplate obj) throws EmailException {
        if (obj == null) {
            return null;
        }
        /* Mclin修改，验证编号和市场是否唯一 */
        if (validMsgTem(request, obj) == false) {
            throw new EmailException(EmailException.MSG_ERROR_SAME_CODE_AND_MARKET_ID_IS_EXISTS);
        }
        templateMapper.insert(obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MessageTemplate updateMessageTemplate(IRequest request, MessageTemplate obj) throws EmailException {
        if (obj == null) {
            return null;
        }
        /* Mclin修改，验证编号和市场是否唯一 */
        if (validMsgTem(request, obj) == false) {
            throw new EmailException(EmailException.MSG_ERROR_SAME_CODE_AND_MARKET_ID_IS_EXISTS);
        }
        templateMapper.updateByPrimaryKey(obj);
        return obj;
    }

    @Override
    public MessageTemplate selectMessageTemplateById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return templateMapper.selectByPrimaryKey(new BigDecimal(objId));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteMessageTemplate(IRequest request, MessageTemplate obj) {
        if (obj.getTemplateId() == null) {
            return 0;
        }
        return templateMapper.deleteByPrimaryKey(obj.getTemplateId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchDelete(IRequest request, List<MessageTemplate> objs) {
        int result = 0;
        if (objs == null || objs.isEmpty()) {
            return result;
        }

        for (MessageTemplate obj : objs) {
            self().deleteMessageTemplate(request, obj);
            result++;
        }
        return result;
    }

    @Override
    public List<MessageTemplate> selectMessageTemplates(IRequest request, MessageTemplate example, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<MessageTemplate> list = templateMapper.selectMessageTemplates(example);
        return list;
    }
    
    private boolean validMsgTem(IRequest request, MessageTemplate obj){
        MessageTemplate mt = templateMapper.getMsgTemByCodeAndMarketId(obj.getTemplateId(), obj.getTemplateCode(), obj.getMarketId());
        if (mt != null) {
            return false;
        }
        return true;
    }

}
