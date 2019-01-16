/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.lkkhpg.dsis.common.config.dto.OrgSelection;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.ISpmInvOrganizationService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 库存组织 Controller.
 * 
 * @author shenqb
 */
@Controller
public class SpmInvOrganizationController extends BaseController {

    @Autowired
    private ISpmInvOrganizationService spmInvOrganizationService;

    /**
     * 保存库存组织.
     * 
     * @param invOrganizations
     *            库存组织List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/invOrganization/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveInvOrganization(@RequestBody List<SpmInvOrganization> invOrganizations,
            BindingResult result, HttpServletRequest request) throws SystemProfileException {
        getValidator().validate(invOrganizations, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmInvOrganizationService.saveInvOrganization(requestContext, invOrganizations));
    }

    /**
     * 删除库存组织.
     * 
     * @param invOrganizations
     *            库存组织List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/invOrganization/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteInvOrganization(@RequestBody List<SpmInvOrganization> invOrganizations,
            BindingResult result, HttpServletRequest request) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmInvOrganizationService.deleteInvOrganization(requestContext, invOrganizations);
        return new ResponseData();
    }

    /**
     * 查询库存组织.
     * 
     * @param invOrganization
     *            库存组织DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/invOrganization/query")
    @ResponseBody
    public ResponseData queryInvOrganization(SpmInvOrganization invOrganization,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmInvOrganizationService.queryInvOrganizations(requestContext, invOrganization, page, pagesize));
    }

    /**
     * 查询库存组织.
     * 
     * @param spmInvOrganization
     *            库存组织dto.
     * @param request
     *            请求
     * @return SpmInvOrganization 结果集
     */
    @RequestMapping(value = "/spm/organization/query")
    @ResponseBody
    public List<SpmInvOrganization> querySpmInvOrganizations(SpmInvOrganization spmInvOrganization,
            HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return spmInvOrganizationService.querySpmInvOrganizations(requestContext, spmInvOrganization);
    }

    /**
     * 获取有效期结束时间 大于当前时间 或 为null的库存组织.
     * 
     * @param request
     *            请求上下文
     * @return 库存组织list
     */
    @RequestMapping(value = "/spm/validOrg/get")
    @ResponseBody
    public List<SpmInvOrganization> getValidOrg(HttpServletRequest request) {

        return spmInvOrganizationService.getValidOrg(createRequestContext(request));
    }

    /**
     * 获取有效期结束时间 大于当前时间 或 为null的库存组织.
     * 数据屏蔽  只显示当前市场所在公司的组织架构中的所有库存组织（公司组织架构下总分公司的所有库存组织）
     *
     * @param request
     *            请求上下文
     * @return 库存组织list
     */
    @RequestMapping(value = "/spm/validOrg/get2")
    @ResponseBody
    public List<SpmInvOrganization> getValidOrg2(HttpServletRequest request) {

        IRequest requestContext = createRequestContext(request);
        Long userId = (Long) requestContext.getAttribute(User.FILED_USER_ID);
         if(userId==1)
           return spmInvOrganizationService.getValidOrg(requestContext);
          else  return spmInvOrganizationService.getValidOrg2(requestContext);

    }
    /**
     * 获取库存组织信息(包括对应的业务实体).
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/org/get")
    @ResponseBody
    public ResponseData getOrg(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        // 获取当前用户所选库存组织ID
        Long defaultOrgId = (Long) request.getSession(false).getAttribute(SystemProfileConstants.INV_ORG_ID);
        List<OrgSelection> list = new ArrayList<>();
        list.add(spmInvOrganizationService.getOrgSelection(requestContext, defaultOrgId));
        return new ResponseData(list);
    }

    /**
     * 获取分配的库存组织.
     * 
     * @param request
     *            请求上下文.
     * @return 已分配的库存组织.
     */
    @RequestMapping(value = "/spm/assignOrg/get")
    public List<SpmInvOrganization> getAssignInvOrganization(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return spmInvOrganizationService.getAssignInvOrganization(requestContext);
    }

    /**
     * 获取当前供货的库存组织.
     * 
     * @param request
     *            请求上下文.
     * @return 当前供货的库存组织
     */
    @RequestMapping(value = "/spm/invOrganization/supply/get")
    @ResponseBody
    public List<SpmInvOrganization> getCurrentSupplyInvOrgs(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return spmInvOrganizationService.getCurrentSupplyInvOrgs(requestContext);
    }

    /**
     * 获取当前供货的库存组织.
     * 
     * @param request
     *            请求上下文.
     * @return 当前供货的库存组织
     */
    @RequestMapping(value = "/spm/invOrganization/bysalesorg")
    @ResponseBody
    public List<SpmInvOrganization> getSupplyInvOrgsBySalesOrg(HttpServletRequest request, Long salesOrgId) {
        IRequest requestContext = createRequestContext(request);
        return spmInvOrganizationService.getSupplyInvOrgsBySalesOrg(requestContext, salesOrgId);
    }

    /**
     * 根据订单获取库存组织.
     * 
     * @param request
     *            请求上下文.
     * @param orderId
     *            订单ID
     * @return 当前供货的库存组织
     */

    @RequestMapping(value = "/spm/invOrganization/byorderid")
    @ResponseBody
    public List<SpmInvOrganization> getSupplyInvOrgsByOrderId(HttpServletRequest request, Long orderId) {
        IRequest requestContext = createRequestContext(request);
        return spmInvOrganizationService.getSupplyInvOrgsByOrderId(requestContext, orderId);
    }

    /**
     * 获取销售组织的供货的库存组织和货币明细.
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织id
     * @return 销售组织的供货的库存组织和货币明细.
     */

    @RequestMapping(value = "/spm/invOrganization/invorgandcurrency")
    @ResponseBody
    public Map<String, Object> getSupplyInvOrgsAndCurrencyBySalesOrg(HttpServletRequest request, Long salesOrgId) {
        IRequest requestContext = createRequestContext(request);

        return spmInvOrganizationService.getSupplyInvOrgsAndCurrencyBySalesOrg(requestContext, salesOrgId);
    }

    /**
     * 获取用户可访问的库存组织.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/invOrganization/queryInvOrgsByRole")
    @ResponseBody
    public ResponseData queryInvOrgsByRole(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmInvOrganizationService.queryInvOrgsByRole(requestContext));
    }
    
    /**
     * 获取用户可访问的库存组织.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/invOrganization/queryCostOrgsByRole")
    @ResponseBody
    public ResponseData queryCostOrgsByRole(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmInvOrganizationService.queryCostOrgsByRole(requestContext));
    }
}
