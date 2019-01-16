/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.platform.dto.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmOperatingUnitService;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmOperatingUnit;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 业务实体Controller.
 * @author hanrui.huang
 *
 */
@Controller
public class SpmOperatingUnitController extends BaseController {

    @Autowired
    private ISpmOperatingUnitService spmOperatingUnitService;

    /**
     * 查询业务实体.
     * @param spmOperatingUnit
     *            业务实体DTO.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @param request
     *            请求上下文 .
     * @return responseData 响应数据.
     */
    @RequestMapping(value = "/spm/operatingUnit/query")
    @ResponseBody
    public ResponseData querySpmOperatingUnit(SpmOperatingUnit spmOperatingUnit,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        if(userId == 1) {
            return new ResponseData(
                    spmOperatingUnitService.querySpmOperatingUnit(requestContext, spmOperatingUnit, page, pagesize));
        }else{
            return new ResponseData(
                    spmOperatingUnitService.querySpmOperatingUnit2(requestContext, spmOperatingUnit, page, pagesize));
        }
    }

    /**
     * 保存业务实体.
     * @param spmOperatingUnits
     *            业务实体List.
     * @param result
     *            数据绑定结果 .
     * @param request
     *            请求上下文.
     * @return responseData 响应数据.
     * @throws SystemProfileException
     *             系统配置统一异常.
     */
    @RequestMapping(value = "/spm/operatingUnit/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveSpmOperatingUnit(@RequestBody List<SpmOperatingUnit> spmOperatingUnits,
            BindingResult result, HttpServletRequest request) throws SystemProfileException {
        getValidator().validate(spmOperatingUnits, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmOperatingUnitService.saveSpmOperatingUnit(requestContext, spmOperatingUnits));
    }

    /**
     * 根据业务实体查询库存组织.
     * @param spmInvOrganization
     *            库存组织DTO.
     * @param page
     *            页.
     * @param pagesize
     *            分页大小.
     * @param request
     *            请求上下文 .
     * @return responseData 响应数据.
     */
    @RequestMapping(value = "/spm/spmInvOrganization/query")
    @ResponseBody
    public ResponseData querySpmInvOrganization(SpmInvOrganization spmInvOrganization,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmOperatingUnitService.querySpmInvOrganization(requestContext, spmInvOrganization, page, pagesize));
    }

    /**
     * 库存组织关联业务实体.
     * @param spmInvOrganizations
     *            业务实体List.
     * @param result
     *            数据绑定结果 .
     * @param request
     *            请求上下文.
     * @return responseData 响应数据.
     * @throws SystemProfileException
     *             系统配置统一异常.
     */
    @RequestMapping(value = "/spm/spmInvOrganization/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveSpmInvOrganization(@RequestBody List<SpmInvOrganization> spmInvOrganizations,
            BindingResult result, HttpServletRequest request) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmOperatingUnitService.saveSpmInvOrganization(requestContext, spmInvOrganizations));
    }
}
