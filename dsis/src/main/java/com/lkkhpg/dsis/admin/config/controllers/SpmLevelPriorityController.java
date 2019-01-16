/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

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

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmLevelPriorityService;
import com.lkkhpg.dsis.common.config.dto.SpmLevelPriority;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
/**
 * 组织层次优先级.
 * @author liuxuan
 *
 */
@Controller
public class SpmLevelPriorityController extends BaseController {
    @Autowired
    private ISpmLevelPriorityService iSpmLevelPriorityService;
    /**
     * 查询组织层次优先级.
     * @param request  请求上下文     
     * @param spmLevelPriority 条件           
     * @param page 页        
     * @param pagesize  分页大小         
     * @return 响应数据
     *      
     */     
    @RequestMapping(value = "/spm/level/query")
    @ResponseBody
    public ResponseData queryLevel(HttpServletRequest request, SpmLevelPriority spmLevelPriority, 
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(iSpmLevelPriorityService.selectLevel(requestContext, spmLevelPriority, page, pagesize));
    }
    /**
     * 保存组织层次优先级.
     * @param spmLevelPriority 条件
     * @param result 数据绑定结果
     * @param request 请求上下文
     * @return 响应数据
     * @throws SystemProfileException 系统配置统一异常.
     */
    @RequestMapping(value = "/spm/level/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData savelevel(@RequestBody List<SpmLevelPriority> spmLevelPriority, BindingResult result,
            HttpServletRequest request) throws SystemProfileException {
        getValidator().validate(spmLevelPriority, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(iSpmLevelPriorityService.saveLevelPrioty(requestContext, spmLevelPriority));
    }
}
