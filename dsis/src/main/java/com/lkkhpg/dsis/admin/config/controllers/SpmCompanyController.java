/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.dto.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmCompanyService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 公司 Controller.
 * 
 * @author liang.rao
 */
@Controller
public class SpmCompanyController extends BaseController {

    @Autowired
    private ISpmCompanyService spmCompanyService;

    /**
     * 保存公司信息.
     * 
     * @param company
     *            公司信息.
     * @param request
     *            请求上下文.
     * @return 响应信息.
     * @throws CommSystemProfileException
     *             系统配置统一异常.
     */
    @RequestMapping(value = "/spm/company/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveCompany(@RequestBody SpmCompany company, HttpServletRequest request, BindingResult result)
            throws CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        List<SpmCompany> spmCompanys = new ArrayList<SpmCompany>();
        if (spmCompanys != null) {
            getValidator().validate(spmCompanys, result);
        }
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        company = spmCompanyService.saveCompany(requestContext, company);
        if (company != null) {
            spmCompanys.add(company);
            return new ResponseData(spmCompanys);
        } else {
            return new ResponseData(false);
        }
    }

    /**
     * 查询公司.
     * 
     * @param company
     *            公司DTO.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @param request
     *            请求上下文.
     * @return responseData 响应数据.
     */
    @RequestMapping(value = "/spm/company/query")
    @ResponseBody
    public ResponseData queryCompany(HttpServletRequest request, @ModelAttribute SpmCompany company,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        IRequest requestContext = createRequestContext(request);
        if(userId == 1) {
            return new ResponseData(spmCompanyService.queryCompany(requestContext, company, page, pagesize));
        }else {
            return new ResponseData(spmCompanyService.queryCompany2(requestContext, company, page, pagesize));
        }
    }

    /**
     * 根据公司id查询公司信息.
     * 
     * @param request
     *            请求上下文.
     * @param companyId
     *            公司代码.
     * @return responseData 响应数据.
     */
    @RequestMapping(value = "/spm/company/queryById")
    @ResponseBody
    public ResponseData queryById(HttpServletRequest request, Long companyId) {
        IRequest requestContext = createRequestContext(request);
        SpmCompany company = new SpmCompany();
        company.setCompanyId(companyId);
        return new ResponseData(spmCompanyService.queryCompanyById(requestContext, company));
    }
}
