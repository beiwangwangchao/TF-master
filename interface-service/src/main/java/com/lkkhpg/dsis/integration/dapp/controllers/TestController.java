/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.integration.dapp.adaptor.FeedJson;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody;
import com.lkkhpg.dsis.integration.dapp.dto.DAppResponse;
import com.lkkhpg.dsis.integration.dapp.service.IDAppCallbackService;
import com.lkkhpg.dsis.platform.dto.system.Prompt;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping("/restful/dapp/api/v1")
public class TestController extends DAppBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private IDAppCallbackService callbackService;

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public @ResponseBody DAppResponse<?> addOrder(HttpServletRequest request, @FeedJson @RequestBody List<Prompt> body)
            throws Exception {

        logger.debug("body:\n{}", body);

        Map<String, Object> data = new HashMap<>();

        return new DAppResponse<>(data);
    }

    @RequestMapping(value = "/callbackTest", method = RequestMethod.GET)
    public @ResponseBody DAppResponse<?> callback(@RequestParam String id, @RequestParam String type,
            @RequestParam String uri, @RequestParam String state) {

        Map<String, Object> data = new HashMap<>();
        Object res = callbackService.callback(new DAppCallbackBody());

        data.put("success", res);
        return new DAppResponse<>(data);
    }

    @RequestMapping(value = "/empty", method = RequestMethod.GET)
    public @ResponseBody DAppResponse<?> empty(Object objs) {

        Map<String, Object> data = new HashMap<>();

        return new DAppResponse<>(data);
    }

}
