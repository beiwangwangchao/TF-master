/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsWhiteList;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.service.IMessageSmsWhiteListService;

/**
 * 短信白名单Controller.
 * 
 * @author Clerifen Li
 */
@Controller
public class MessageSmsWhiteListController extends BaseController {

    @Autowired
    private IMessageSmsWhiteListService service;

    @RequestMapping(value = "/sys/messageSmsWhiteList/query")
    @ResponseBody
    public ResponseData getMessageSmsWhiteList(HttpServletRequest request, MessageSmsWhiteList example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageSmsWhiteLists(requestContext, example, page, pagesize));
    }

    @RequestMapping(value = "/sys/messageSmsWhiteList/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMessageSmsWhiteList(HttpServletRequest request, @RequestBody List<MessageSmsWhiteList> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
