/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import com.lkkhpg.dsis.admin.config.dto.SpmOrgParamValue;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmOrgParamService;
import com.lkkhpg.dsis.admin.config.service.ISpmOrganizationService;
import com.lkkhpg.dsis.common.config.dto.SpmOrgDefination;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织定义 Controller.
 * 
 * @author wangc
 */
@Controller
public class SpmOrganizationController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(SpmOrganizationController.class);

    @Autowired
    private ISpmOrganizationService spmOrganizationService;

    @Autowired
    private ISpmOrgParamService spmOrgParamService;

    /**
     * 
     * @param request
     *            请求上下文
     * @param page
     *            页码
     * @param pagesize
     *            每页记录数
     * @param spmOrgDefination
     *            组织定义dto
     * @return 组织定义列表
     * @throws SystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/organization/queryAll")
    @ResponseBody
    public ResponseData queryOrganization(HttpServletRequest request,
                                          @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, SpmOrgDefination spmOrgDefination)
            throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        if(userId == 1) {
            return new ResponseData(
                    spmOrganizationService.queryByOrganization(requestContext, spmOrgDefination, page, pagesize));
        }else {
            return new ResponseData(
                    spmOrganizationService.queryByOrganization2(requestContext, spmOrgDefination, page, pagesize));
        }
    }

    /**
     * 
     * @param request
     *            请求上下文
     * @param orgs
     *            组织定义列表
     * @param result
     *            组织定义验证
     * @return 组织定义
     * @throws SystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/organization/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveOrgDefination(HttpServletRequest request, @RequestBody List<SpmOrgDefination> orgs,
                                          BindingResult result) throws SystemProfileException {
        getValidator().validate(orgs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        List<SpmOrgDefination> saveOrgDefinations = spmOrganizationService.saveOrgDefinations(requestContext, orgs);
        if(saveOrgDefinations==null){
            ResponseData rd= new ResponseData();
            rd.setSuccess(false);
            rd.setMessage("该销售组织已经关联，请解除关联刷新后再进行更改操作");
            return rd;
        }else{
            SpmOrgParamValue value= new SpmOrgParamValue();
            SpmOrgParamValue value1= new SpmOrgParamValue();
            SpmOrgParamValue value2= new SpmOrgParamValue();
            SpmOrgParamValue value3=new SpmOrgParamValue();
            for(SpmOrgDefination defination:saveOrgDefinations){
                if(defination.getSalesOrgId()!=null){
                    List<SpmOrgParamValue> spmOrgParamValues = new ArrayList<SpmOrgParamValue>();
                    value.setOrgId(defination.getSalesOrgId());
                    value.setOrgType("SALES");
                    value.setParamName("SPM.SHOP_VISIBLE");
                    value.setParamValue("Y");
                    value1.setOrgId(defination.getSalesOrgId());
                    value1.setOrgType("SALES");
                    value1.setParamName("SPM.CURRENCY");
                    value1.setParamValue("CNY");
                    value2.setOrgId(defination.getSalesOrgId());
                    value2.setOrgType("SALES");
                    value2.setParamName("SPM.MEMBER_TYPE");
                    value2.setParamValue("Y");
                    value3.setOrgId(defination.getInvOrgId());
                    spmOrgParamValues.add(value);
                    spmOrgParamValues.add(value1);
                    spmOrgParamValues.add(value2);
                    spmOrgParamService.saveOrgParamValues(requestContext, spmOrgParamValues);
                }
                if(defination.getInvOrgId()!=null){
                    List<SpmOrgParamValue> spmOrgValues = new ArrayList<SpmOrgParamValue>();
                    value3.setOrgId(defination.getInvOrgId());
                    value3.setOrgType("INV");
                    value3.setParamName("SPM.CURRENCY");
                    value3.setParamValue("CNY");
                    spmOrgValues.add(value3);
                    spmOrgParamService.saveOrgParamValues(requestContext, spmOrgValues);
                }
            }
            return new ResponseData(saveOrgDefinations);
        }

    }
    
    

}
