/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.lkkhpg.dsis.platform.dto.system.MessageEmailConfig;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.exception.EmailException;
import com.lkkhpg.dsis.platform.service.IMessageEmailConfigService;

/**
 * 邮件白名单Controller.
 * 
 * @author Clerifen Li
 */
@Controller
public class MessageEmailConfigController extends BaseController {

    @Autowired
    private IMessageEmailConfigService service;

    @RequestMapping(value = "/sys/messageEmailConfig/query")
    @ResponseBody
    public ResponseData getMessageEmailConfig(HttpServletRequest request, MessageEmailConfig example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageEmailConfigs(requestContext, example, page, pagesize));
    }

    @RequestMapping(value = "/sys/messageEmailConfig/load")
    @ResponseBody
    public ResponseData getMessageSmsAccountPassword(HttpServletRequest request, MessageEmailConfig example) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageEmailConfigs(requestContext, example, 1, 1));
    }
    
    @RequestMapping(value = "/sys/messageEmailConfig/submit", method = RequestMethod.POST)
    public ResponseData submitLov(@RequestBody MessageEmailConfig obj,
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
    
    @RequestMapping(value = "/sys/messageEmailConfig/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMessageEmailConfig(HttpServletRequest request, @RequestBody List<MessageEmailConfig> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }
    
    /**
     * 获取邮箱配置数量.
     * 
     * @param request
     *            请求上下文
     */
    @RequestMapping(value = "/sys/messageEmailConfig/queryMsgConfigQuanties")
    @ResponseBody
    public ResponseData queryMsgConfigQuanties(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        Map<String, Object> map = service.queryMsgConfigQuanties(requestContext);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result.add(map);

        return new ResponseData(result);
    }

}
