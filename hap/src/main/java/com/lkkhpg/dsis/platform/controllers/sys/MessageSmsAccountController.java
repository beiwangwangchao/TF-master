/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.service.IMessageSmsAccountService;

/**
 * 消息账号Controller.
 * 
 * @author Clerifen Li
 */
@Controller
public class MessageSmsAccountController extends BaseController {

    @Autowired
    private IMessageSmsAccountService service;
    
    @RequestMapping(value = "/sys/messageSmsAccount/query")
    @ResponseBody
    public ResponseData getMessageSmsAccount(HttpServletRequest request, MessageSmsAccount example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageSmsAccounts(requestContext, example, page, pagesize));
    }

    @RequestMapping(value = "/sys/messageSmsAccount/queryAccount")
    @ResponseBody
    public ResponseData getMessageSmsAccountPassword(HttpServletRequest request, MessageSmsAccount example) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageSmsAccountPassword(requestContext, example, 1, 1));
    }
    
    @RequestMapping(value = "/sys/messageSmsAccount/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLov(@RequestBody MessageSmsAccount obj,
            BindingResult result, HttpServletRequest request) throws EmailException {
        //没意义的值
        obj.setObjectVersionNumber(0L);
        
        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.batchUpdate(requestContext, obj);
        return new ResponseData();
    }
    
    @ResponseBody
    @RequestMapping(value="/sys/messageSmsAccount/updatePasswordOnly")
    public ResponseData updateMessageSmsAccountPasswordOnly(HttpServletRequest request, @RequestBody MessageSmsAccount obj, BindingResult result) throws BaseException {
        //没意义的值
        obj.setObjectVersionNumber(0L);
        
        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.updateMessageSmsAccountPasswordOnly(requestContext, obj);
        return new ResponseData();
    }
    
    @RequestMapping(value = "/sys/messageSmsAccount/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMessageSmsAccount(HttpServletRequest request, @RequestBody List<MessageSmsAccount> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
