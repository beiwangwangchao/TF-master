/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmBankService;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmBankCharges;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 银行Controller.
 * @author liuxuan
 *
 */
@Controller
public class SpmBankController extends BaseController {
    
    @Autowired
    private ISpmBankService spmBankService;
    
    /**
     * 查询银行.
     * @param request 请求上下文
     * @param spmBank 查询条件
     * @param page 页数
     * @param pagesize 每页记录数
     * @return 响应数据
     */
    @RequestMapping(value = "/spm/bank/query")
    @ResponseBody
    public ResponseData queryBySpmBank(HttpServletRequest request, SpmBank spmBank,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmBankService.querySpmBank(requestContext, spmBank, page, pagesize));
    }
    
    /**
     * 查询银行详情.
     * @param request 请求上下文
     * @param spmBankCharges 查询条件
     * @param page 页数
     * @param pagesize 每页记录数
     * @return 响应数据
     */
    @RequestMapping(value = "/spm/edit/query")
    @ResponseBody
    public ResponseData queryBySpmBankEdit(HttpServletRequest request, SpmBankCharges spmBankCharges,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmBankService.querSpmBankCharces(requestContext, spmBankCharges, page, pagesize));
    }
    
    /**
     * 保存.
     * @param request 请求上下文
     * @param SpmBanks 银行
     * @return 响应数据
     * @throws SystemProfileException 系统配置异常
     */
    @RequestMapping(value = "/spm/bank/save")
    @ResponseBody
    public ResponseData saveBySpmBank(HttpServletRequest request, @RequestBody List<SpmBank> 
    SpmBanks) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        SpmBanks = spmBankService.saveSpmBank(requestContext, SpmBanks);
        return new ResponseData(SpmBanks);
    }
    
}