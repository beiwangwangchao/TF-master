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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmInvNumberingService;
import com.lkkhpg.dsis.common.config.dto.SpmInvNumbering;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 发票规则Controller.
 * @author chenjingxiong
 */
@Controller
public class SpmInvNumberingController extends BaseController {

    @Autowired
    private ISpmInvNumberingService spmInvNumberingService;

    /**
     * 查询发票规则.
     * @param request 请求上下文.
     * @param spmInvNumbering 查询条件.
     * @param page 页数.
     * @param pagesize 每页记录数.
     * @return 相应数据.
     */
    @RequestMapping("/spm/numbering/query")
    @ResponseBody
    public ResponseData querySpmInvNumberings(HttpServletRequest request, SpmInvNumbering spmInvNumbering, int page,
            int pagesize) {
        return new ResponseData(spmInvNumberingService.querySpmInvNumberings(createRequestContext(request),
                spmInvNumbering, page, pagesize));
    }
    
    /**
     * 保存发票规则.
     * @param request 请求上下文.
     * @param result 数据绑定结果.
     * @param spmInvNumberings 保存的发票规则.
     * @return 相应数据.
     * @throws SystemProfileException 系统配置统一异常
     */
    @RequestMapping("/spm/numbering/save")
    @ResponseBody
    public ResponseData saveSpmInvNumberings(HttpServletRequest request,
            @RequestBody List<SpmInvNumbering> spmInvNumberings, BindingResult result) throws SystemProfileException {
        getValidator().validate(spmInvNumberings, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        return new ResponseData(
                spmInvNumberingService.saveSpmInvNumberings(createRequestContext(request), spmInvNumberings));
    }
    
    /**
     * 删除选中.
     * @param request 统一上下文
     * @param spmInvNumberings 发票规则.
     * @return 受影响行
     */
    @RequestMapping("/spm/numbering/delete")
    @ResponseBody
    public ResponseData deleteByRuleId(HttpServletRequest request, @RequestBody List<SpmInvNumbering> spmInvNumberings){
        spmInvNumberingService.deleteByRuleId(createRequestContext(request), spmInvNumberings);
        return new ResponseData();
    }
}
