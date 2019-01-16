/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.service.ISysMsMessageService;
import com.lkkhpg.dsis.common.system.dto.SiteMessageRead;
import com.lkkhpg.dsis.common.system.dto.SysMessageRead;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.system.mapper.SysMsMessageAssignMapper;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;

/**
 * 消息controller.
 * 
 * @author HuangJiaJing
 *
 */
@Controller
public class SysMsMessageController extends BaseController {

    private static final String DSIS = "DSIS";

    private static final String SMS = "SMS";

    private static final String EMAIL = "EMAIL";

    @Autowired
    private ISysMsMessageService sysMsMessageService;

    @Autowired
    private IManualMessageService manualMessageService;

    @Autowired
    private SysMsMessageAssignMapper sysMsMessageAssignMapper;

    /**
     * 查询消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessage
     *            消息dto
     * @param page
     *            页数
     * @param pagesize
     *            每页显示的行数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/msmessage/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryBySysMsMessage(HttpServletRequest request, SysMsMessage msMessage,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysMsMessageService.queryBySysMsMessage(requestContext, msMessage, page, pagesize));
    }

    /**
     * 查询我的消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessage
     *            消息dto
     * @param page
     *            页数
     * @param pagesize
     *            每页显示的行数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/mymessage/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryByMySysMsMessage(HttpServletRequest request, SiteMessageRead msMessage,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        Long accountId = createRequestContext(request).getAccountId();
        msMessage.setAccountId(accountId);
        return new ResponseData(manualMessageService.queryMessage(msMessage, page, pagesize));
    }

    /**
     * 删除我的消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessage
     *            消息dto
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/mymessage/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMySysMsMessage(HttpServletRequest request, @RequestBody List<SysMessageRead> msMessage) {
        for (SysMessageRead sysMessageRead : msMessage) {
            manualMessageService.delMessage(sysMessageRead);
        }
        return new ResponseData();
    }

    /**
     * 查询我的消息内容.
     * 
     * @param request
     *            应用上下文.
     * @param msMessage
     *            消息dto
     * @param page
     *            页数
     * @param pagesize
     *            每页显示的行数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/mycontent/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryBymessageContent(HttpServletRequest request, SysMessageRead msMessage,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(manualMessageService.readMessage(msMessage));
    }

    protected IRequest createRequestContext(HttpServletRequest request) {
        return RequestHelper.createServiceRequest(request);
    }

    /**
     * 保存消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessages
     *            消息dto
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/msmessage/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveSysMsMessage(HttpServletRequest request, @RequestBody List<SysMsMessage> msMessages) {
        IRequest requestContext = createRequestContext(request);
        List<SysMsMessage> saveSysMsMessage = sysMsMessageService.saveSysMsMessage(requestContext, msMessages);
        return new ResponseData(saveSysMsMessage);
    }

    /**
     * 发布消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessages
     *            消息list集合
     * @return ResponseData集合
     * @throws Exception
     *             异常
     */
    @RequestMapping(value = "/sys/msmessage/public", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData publicSysMsMessage(HttpServletRequest request, @RequestBody List<SysMsMessage> msMessages)
            throws Exception {

        for (SysMsMessage sysMsMessage : msMessages) {
            SysMsMessageAssign assign = new SysMsMessageAssign();
            assign.setMsMessageId(sysMsMessage.getMsMessageId());
            List<SysMsMessageAssign> arrayList = sysMsMessageAssignMapper.queryByMemAssign(assign);
            List<SysMsMessageAssign> arrayList2 = sysMsMessageAssignMapper.queryByUserAssign(assign);
            List<SysMsMessageAssign> list = new ArrayList<>();
            list.addAll(arrayList);
            list.addAll(arrayList2);
            String messageType = sysMsMessage.getPublishChannel();
            MessageTypeEnum type = MessageTypeEnum.valueOf(messageType);
            if (!DSIS.equals(sysMsMessage.getPublishChannel())) {
                for (SysMsMessageAssign sysMsMessageAssign : list) {
                    if (StringUtils.isEmpty(sysMsMessageAssign.getMessageAddress(type))) {
                        if (SMS.equals(sysMsMessage.getPublishChannel())) {
                            throw new SystemProfileException(SystemProfileException.MSG_ERROR_MESSAGE_PUBLIC_PHONE,
                                    null);
                        } else if (EMAIL.equals(sysMsMessage.getPublishChannel())) {
                            throw new SystemProfileException(SystemProfileException.MSG_ERROR_MESSAGE_PUBLIC, null);
                        }
                    }
                }
            }
            manualMessageService.publishMessage(sysMsMessage, list);
        }
        return new ResponseData(msMessages);
    }

    /**
     * 查询会员表消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessageAssign
     *            消息assign表
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/mmmessage/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData querySysMsMessage(HttpServletRequest request, SysMsMessageAssign msMessageAssign) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysMsMessageService.queryByMmMessage(requestContext, msMessageAssign));
    }

    /**
     * 查询消息发送状态.
     * 
     * @param request
     *            应用上下文.
     * @param msMessageAssign
     *            dto
     * @param page
     *            页数
     * @param pagesize
     *            每页行数
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/publicmessage/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryPublicMessage(HttpServletRequest request, SysMsMessageAssign msMessageAssign,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                sysMsMessageService.queryPublicMessage(requestContext, msMessageAssign, page, pagesize));
    }

    /**
     * 添加会员表消息.
     * 
     * @param request
     *            应用上下文.
     * @param groupId
     *            groupId
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/addmmmessage/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryAddSysMsMessage(HttpServletRequest request, Long groupId) {
        IRequest requestContext = createRequestContext(request);
        List<Member> list = sysMsMessageService.queryMemberByGroupId(requestContext, groupId);
        return new ResponseData(list);
    }

    /**
     * 查询用户表消息.
     * 
     * @param request
     *            应用上下文.
     * @param msMessageAssign
     *            消息assign表
     * @return ResponseData集合
     */
    @RequestMapping(value = "/sys/usermessage/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData querySysUserMessage(HttpServletRequest request, SysMsMessageAssign msMessageAssign) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(sysMsMessageService.queryByUserMessage(requestContext, msMessageAssign));
    }
}
