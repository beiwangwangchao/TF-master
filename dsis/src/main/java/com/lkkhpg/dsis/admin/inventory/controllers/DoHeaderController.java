/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.inventory.service.IDoHeaderService;
import com.lkkhpg.dsis.common.inventory.lading.dto.DoHeader;
import com.lkkhpg.dsis.common.inventory.lading.dto.DoLine;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 提货单controller.
 * @author liuxuan
 *
 */
@Controller
public class DoHeaderController extends BaseController {
    @Autowired
    private IDoHeaderService doHeaderService;

    /**
     * 
     * @param request 请求上下文
     * @param doHeader 提货单头dto
     * @param page 页数
     * @param pagesize 每页显示数
     * @return 集合
     */
    @RequestMapping(value = "/im/header/query")
    @ResponseBody
    public ResponseData queryByDoHeader(HttpServletRequest request, DoHeader doHeader,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(doHeaderService.queryDoHeader(requestContext, doHeader, page, pagesize));
    }

    /**
     * 
     * @param request 请求上下文
     * @param doLine 提货单行dto
     * @param page 页数
     * @param pagesize 每页显示条数
     * @return 集合
     */
    @RequestMapping(value = "/im/doline/query")
    @ResponseBody
    public ResponseData queryByDoLine(HttpServletRequest request, DoLine doLine) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(doHeaderService.queryDoLine(requestContext, doLine));
    }
    
   /**
    * 
    * @param request 请求上下文
    * @param doHeaders 提货单集合
    * @return ResponseData集合
    */
    @RequestMapping(value = "/im/header/save")
    @ResponseBody
    public ResponseData saveByDoHeader(HttpServletRequest request, @RequestBody List<DoHeader> doHeaders) {
        IRequest requestContext = createRequestContext(request);
        doHeaders = doHeaderService.saveDoHeader(requestContext, doHeaders);
        return new ResponseData(doHeaders);
    }
}
