/*
 *
 */
package com.lkkhpg.dsis.platform.controllers.sys;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.DemoUser;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * @author shiliyan
 *
 */
public class SiteMessageController extends BaseController {

    @RequestMapping(value = "/site/message/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectUsers(@ModelAttribute DemoUser user,
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pagesize) {
        PageHelper.startPage(page, pagesize);
        return new ResponseData();
    }

    @RequestMapping(value = "/site/message/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData update(@ModelAttribute DemoUser user) {
        return new ResponseData();
    }

//    @RequestMapping(value = "/site/message/create", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseData create(@ModelAttribute SiteMessage msg) {
//        return new ResponseData();
//    }

}
