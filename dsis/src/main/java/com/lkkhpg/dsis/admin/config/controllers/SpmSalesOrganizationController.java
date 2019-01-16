/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.service.ISpmSalesOrganizationService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 销售区域 Controller.
 * 
 * @author shenqb
 */
@Controller
public class SpmSalesOrganizationController extends BaseController {

    @Autowired
    private ISpmSalesOrganizationService spmSalesOrganizationService;

    /**
     * 保存销售区域.
     * 
     * @param salesOrganizations
     *            销售区域List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/salesOrganization/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveSalesOrganization(@RequestBody List<SpmSalesOrganization> salesOrganizations,
                                              BindingResult result, HttpServletRequest request) throws SystemProfileException {
        getValidator().validate(salesOrganizations, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmSalesOrganizationService.saveSalesOrganization(requestContext, salesOrganizations));
    }

    /**
     * 新增树.
     * 
     * @param salesOrganization
     *            SpmSalesOrganization
     * @param result
     *            validation result
     * @param request
     *            HttpServletRequest
     * @return ResponseData集合
     * @throws SystemProfileException
     *             SystemProfileException
     */
    @RequestMapping(value = "/spm/salesOrganization/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSalesOrganization(@RequestBody SpmSalesOrganization salesOrganization,
                                             BindingResult result, HttpServletRequest request) throws SystemProfileException {
        getValidator().validate(salesOrganization, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);
        SpmSalesOrganization ssoList = spmSalesOrganizationService.submitSalesOrganization(requestContext,
                salesOrganization);
        List<SpmSalesOrganization> list = new ArrayList<>();
        list.add(ssoList);
        return new ResponseData(list);
    }

    /**
     * 删除销售区域.
     * 
     * @param salesOrganizations
     *            销售区域List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/salesOrganization/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSalesOrganization(@RequestBody List<SpmSalesOrganization> salesOrganizations,
                                                BindingResult result, HttpServletRequest request) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmSalesOrganizationService.deleteSalesOrganization(requestContext, salesOrganizations);
        return new ResponseData();
    }

    /**
     * 查询销售区域.
     * 
     * @param salesOrganization
     *            销售区域DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/salesOrganization/query")
    @ResponseBody
    public ResponseData querySalesOrganization(SpmSalesOrganization salesOrganization,
                                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmSalesOrganizationService.querySalesOrganization(requestContext, salesOrganization, page, pagesize));
    }
    
    /**
     * 查询所有销售区域.
     * 
     * @param salesOrganization
     *            销售区域DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/allSalesOrganization/query")
    @ResponseBody
    public ResponseData queryAllSalesOrganization(SpmSalesOrganization salesOrganization,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmSalesOrganizationService.queryAllSalesOrganization(requestContext, salesOrganization, page, pagesize));
    }

    /**
     * 查询为marketId为空的数据.
     * 
     * @param salesOrganization
     *            销售组织
     * @param page
     *            页数
     * @param pagesize
     *            每页显示行数
     * @param request
     *            请求上下文
     * @return ResponseData集合
     */
    @RequestMapping(value = "/spm/salesOrganizationNull/query")
    @ResponseBody
    public ResponseData queryNull(SpmSalesOrganization salesOrganization,
                                  @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        if(userId==1){
                    return new ResponseData(
                spmSalesOrganizationService.querySalesOrgByTime(requestContext, salesOrganization, page, pagesize));
        }else {
            return new ResponseData(
                    spmSalesOrganizationService.queryNull(requestContext, salesOrganization, page, pagesize));
        }

    }

    /**
     * 查询父节点数据.
     * 
     * @param request
     *            请求上下文
     * @param salesOrganization
     *            销售组织
     * @return list集合
     */
    @RequestMapping(value = "/spm/salesOrganization/queryFu")
    @ResponseBody
    public List<SpmSalesOrganization> querySalesFu(HttpServletRequest request, SpmSalesOrganization salesOrganization) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        // 查询顶层节点
        List<SpmSalesOrganization> sso = spmSalesOrganizationService.queryBySalesFu(requestContext, salesOrganization);
        return sso;
    }

    /**
     * 查询子节点数据.
     * 
     * @param request
     *            请求上下文
     * @param spmSalesOrganization
     *            销售组织
     * @return list 集合
     */
    @RequestMapping(value = "/spm/salesOrganization/queryZi")
    @ResponseBody
    public List<SpmSalesOrganization> querySalesZi(HttpServletRequest request,
                                                   SpmSalesOrganization spmSalesOrganization) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        // 查询顶层节点
        List<SpmSalesOrganization> sso = spmSalesOrganizationService.queryBySalesZi(requestContext,
                spmSalesOrganization);
        return sso;
    }

    /**
     * 获取当前市场下的销售区域.
     * @param request
     * @return
     */
    @RequestMapping(value = "/spm/salesOrganization/market")
    @ResponseBody
    public List<SpmSalesOrganization> querySalesOrgInCurrentMarket(HttpServletRequest request) {
        // 构建上下文参数
        IRequest requestContext = createRequestContext(request);
        SpmSalesOrganization query = new SpmSalesOrganization();
        query.setMarketId(((DsisServiceRequest) requestContext).getMarketId());
        List<SpmSalesOrganization> sso = spmSalesOrganizationService.querySalesOrganization(requestContext, query, 1, Integer.MAX_VALUE);
        return sso;
    }

    @RequestMapping(value = "/spm/salesOrg/querySalesOrgByRole")
    @ResponseBody
    public ResponseData querySalesOrganizationByRole (HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmSalesOrganizationService.querySalesOrganizationByRole(requestContext));
    }


}
