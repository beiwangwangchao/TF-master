/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Message;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.service.IMessageService;

/**
 * 对消息的操作.
 * 
 * @author xiawang.liu@hand-china.com
 */

@Controller
public class MessageController extends BaseController {

    @Autowired
    private IMessageService messageService;

    /**
     * 查询消息.
     * 
     * @param request
     *            HttpServletRequest
     * @param message
     *            Message
     * @param page
     *            page
     * @param pagesize
     *            pagesize
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/message/query")
    @ResponseBody
    public ResponseData getMessageBySubject(HttpServletRequest request, Message message,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(messageService.selectMessagesBySubject(requestContext, message, page, pagesize));
    }
    
    /**
     * 查询消息地址.
     * 
     * @param request
     *            HttpServletRequest
     * @param messageReceiver
     *            MessageReceiver
     * @param page
     *            page
     * @param pagesize
     *            pagesize
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/message/queryMessageAddresses")
    @ResponseBody
    public ResponseData getMessageAddressesByMessageId(HttpServletRequest request, MessageReceiver messageReceiver,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                messageService.selectMessageAddressesByMessageId(requestContext, messageReceiver, page, pagesize));
    }
}