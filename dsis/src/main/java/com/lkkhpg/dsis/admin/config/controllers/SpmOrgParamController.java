/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.config.dto.OrgParams;
import com.lkkhpg.dsis.admin.config.dto.SpmOrgParamValue;
import com.lkkhpg.dsis.admin.config.service.ISpmOrgParamService;
import com.lkkhpg.dsis.common.config.dto.OrgParamDef;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 组织参数Controller.
 * 
 * @author chenjingxiong
 */
@Controller
public class SpmOrgParamController extends BaseController {

    @Autowired
    private ISpmOrgParamService spmOrgParamService;
    
    @Autowired
    private IParamService paramService;

    @RequestMapping("/spm/paramDef/get")
    @ResponseBody
    public ResponseData getOrgParamDefByOrgType(HttpServletRequest request, String orgType) {
        List<OrgParamDef> result = spmOrgParamService.getOrgParamDefByOrgType(createRequestContext(request), orgType);

       if(orgType.equals("INV")){
            result.removeIf(s->s.getParamName().equals("SPM.NOTED_WAREHOUSEMAN"));
        }
        return new ResponseData(result);
    }

    /**
     * 组织参数定义查询.
     * @param orgParamDef 组织参数定义
     * @param page 页
     * @param pagesize 页大小
     * @param request 请求上下文
     * @return 组织参数定义List
     */
    @RequestMapping(value = "/spm/orgParamDef/query")
    @ResponseBody
    public ResponseData queryOrgParamDef(OrgParamDef orgParamDef, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmOrgParamService.queryOrgParamDef(requestContext, orgParamDef, page, pagesize));
    }
    
    /**
     * 组织参数定义保存.
     * @param orgParamDefs
     *        组织参数定义List
     * @param result 数据绑定结果
     * @param request 请求上下文
     * @return 组织参数定义List
     */
    @RequestMapping(value = "/spm/orgParamDef/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveOrgParamDef(@RequestBody List<OrgParamDef> orgParamDefs, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(orgParamDefs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmOrgParamService.saveOrgParamDef(requestContext, orgParamDefs));
    }

    /**
     * 保存组织参数.
     * @param spmOrgParamValues 要保存的组织参数.
     * @param result 结果绑定.
     * @param request 请求上下文.
     * @return 响应数据.
     */
    @RequestMapping(value = "/spm/orgParam/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveOrgParams(@RequestBody List<SpmOrgParamValue> spmOrgParamValues, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(spmOrgParamValues, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmOrgParamService.saveOrgParamValues(requestContext, spmOrgParamValues));
    }
    
    /**
     * 查询组织参数的值.
     * @param request 请求上下文.
     * @param orgType 组织类型.
     * @param orgId 组织ID.
     * @return 已保存的组织参数.
     */
    @RequestMapping(value = "/spm/orgParam/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryOrgParams(HttpServletRequest request, String orgType, Long orgId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmOrgParamService.getOrgParamValues(requestContext, orgType, orgId));
    }
    
    /**
     * 通用获取组织参数.
     * @param orgParams 需要获取的组织参数DTO.
     * @param request 请求上下文.
     * @return 包含组织参数值的DTO.
     */
    @RequestMapping(value = "/spm/orgParam/get", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getOrgParamValues(@RequestBody List<OrgParams> orgParams, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        for (OrgParams orgParam : orgParams) {
            List<String> paramNames = orgParam.getParamNames();
            for (String paramName : paramNames) {
                List<String> values = new ArrayList<>();
                String orgType = orgParam.getOrgType();
                if (SystemProfileConstants.ORG_TYPE_MARKET.equals(orgType)) {
                    values = paramService.getMarketParamValues(requestContext, paramName, orgParam.getOrgId());
                } else if (SystemProfileConstants.ORG_TYPE_OU.equals(orgType)) {
                    values = paramService.getOuParamValues(requestContext, paramName, orgParam.getOrgId());
                } else {
                    values = paramService.getParamValues(requestContext, paramName, orgParam.getOrgType(),
                            orgParam.getOrgId());
                }
                Map<String, List<String>> valuesMap = orgParam.getParamValues();
                if (valuesMap == null) {
                    valuesMap = new HashMap<String, List<String>>();
                }
                valuesMap.put(paramName, values);
                orgParam.setParamValues(valuesMap);
            }
        }
        return new ResponseData(orgParams);
    }
    
}
