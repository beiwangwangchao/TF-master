/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.integration.dapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.integration.dapp.dto.IsgDappAuthCode;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDappCodeService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping(path = "/integration/dapp")
public class DappCodeController extends BaseController {

    @Autowired
    private IDappCodeService codeService;

    @RequestMapping(path = "/codes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectCodes(HttpServletRequest request, IsgDappAuthCode code,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(codeService.selectCodes(code, page, pagesize));
    }

    @RequestMapping(path = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitCodes(HttpServletRequest request, @RequestBody List<IsgDappAuthCode> body) {
        return new ResponseData(codeService.batchUpdate(body));
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteCodes(HttpServletRequest request, @RequestBody List<IsgDappAuthCode> body) {
        body.forEach(e -> e.set__status(DTOStatus.DELETE));
        codeService.batchUpdate(body);
        return new ResponseData();
    }

}
