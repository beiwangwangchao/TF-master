/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.member.service.IMemApplyRoleService;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.MemApplyRole;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 会员申请角色变更controller.
 * 
 * @author linyuheng
 */
@Controller
public class MemApplyRoleController extends BaseController {
    @Autowired
    private IMemApplyRoleService memApplyRoleService;
    
    @RequestMapping(value = "/mm/member/applyRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryMarketChange(HttpServletRequest request, @ModelAttribute MemApplyRole memApplyRole,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        memApplyRole.setMarketId(memApplyRole.getMarketId()==null?iRequest.getAttribute(SystemProfileConstants.MARKET_ID):memApplyRole.getMarketId());
        return new ResponseData(memApplyRoleService.queryRecords(iRequest,memApplyRole));
    }
}
