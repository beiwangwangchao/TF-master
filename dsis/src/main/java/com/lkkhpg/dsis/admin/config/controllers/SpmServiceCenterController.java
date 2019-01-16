/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmServiceCenterService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenter;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenterAssign;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 服务中心Controller.
 * 
 * @author hanrui.huang
 *
 */
@Controller
public class SpmServiceCenterController extends BaseController {

    @Autowired
    private ISpmServiceCenterService spmServiceCenterService;

    /**
     * 服务中心查询.
     * 
     * @param spmServiceCenter
     *            服务中心DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/spm/serviceCenter/query")
    @ResponseBody
    public ResponseData queryServiceCenter(SpmServiceCenter spmServiceCenter,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmServiceCenterService.queryServiceCenter(requestContext, spmServiceCenter, page, pagesize));
    }

    /**
     * 服务中心查询和负责会员.
     * 
     * @param spmServiceCenter
     *            服务中心DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     */
    @RequestMapping(value = "/spm/serviceCenterAndMember/query")
    @ResponseBody
    public ResponseData queryServiceCenterWithMember(SpmServiceCenter spmServiceCenter,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmServiceCenterService.queryServiceCenterWithMember(requestContext, spmServiceCenter, page, pagesize));
    }

    /**
     * 服务中心更新及保存.
     * 
     * @param request
     *            请求上下文
     * @param spmServiceCenters
     *            服务中心DTO
     * @param result
     *            数据绑定结果
     * @return 保存结果
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/serviceCenter/save")
    @ResponseBody
    public ResponseData saveSpmServiceCenter(HttpServletRequest request,
            @RequestBody List<SpmServiceCenter> spmServiceCenters, BindingResult result) throws SystemProfileException {
        getValidator().validate(spmServiceCenters, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmServiceCenterService.saveSpmServiceCenter(requestContext, spmServiceCenters));
    }

    /**
     * 服务中心详情页查询.
     * 
     * @param request
     *            请求上下文
     * @param serviceCenter
     * @return 服务中心DTO
     */
    @RequestMapping(value = "/spm/serviceCenter/get")
    @ResponseBody
    public ResponseData getSpmServiceCenter(HttpServletRequest request, SpmServiceCenter serviceCenter) {
        IRequest requestContext = createRequestContext(request);
        List<SpmServiceCenter> list = new ArrayList<SpmServiceCenter>();
        SpmServiceCenter spmServiceCenter = spmServiceCenterService.getSpmServiceCenter(requestContext,
                serviceCenter);
        list.add(spmServiceCenter);
        return new ResponseData(list);
    }

    /**
     * 服务中心会员查询.
     * 
     * @param request
     *            请求上下文
     * @param serviceCenterId
     *            服务中心id
     * @param salesOrgId 组织id
     * @return 会员列表
     */
    @RequestMapping(value = "/spm/serviceCenter/getMembers")
    @ResponseBody
    public ResponseData getSpmServiceCenterMember(HttpServletRequest request, Long serviceCenterId, Long salesOrgId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmServiceCenterService.getSpmServiceCenterMembers(requestContext, serviceCenterId));
    }

    /**
     * 删除服务中心会员.
     * 
     * @param request
     *            请求上下文
     * @param spmServiceCenterAssigns
     *            服务中心会员
     * @return 服务中心会员列表
     */
    @RequestMapping(value = "/spm/serviceCenter/removeMember")
    @ResponseBody
    public ResponseData deleteSpmServiceCenterMember(HttpServletRequest request,
            @RequestBody List<SpmServiceCenterAssign> spmServiceCenterAssigns) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                spmServiceCenterService.deleteSpmServiceCenterMembers(requestContext, spmServiceCenterAssigns));
    }

    /**
     * 提交审核.
     * 
     * @param request
     *            请求上下文
     * @param serviceCenters
     *            服务中心DTO列表
     * @param result
     *            数据绑定结果
     * @return 服务中心
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/serviceCenter/submit")
    @ResponseBody
    public ResponseData submitServiceCenter(HttpServletRequest request,
            @RequestBody List<SpmServiceCenter> serviceCenters, BindingResult result) throws SystemProfileException {
        getValidator().validate(serviceCenters, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmServiceCenterService.submitServiceCenter(requestContext, serviceCenters));
    }

    /**
     * 审核.
     * 
     * @param request
     *            请求上下文
     * @param serviceCenterId
     *            服务中心id
     * @param salesOrgId 组织id
     * @return 服务中心
     * @throws Exception
     *             调用短信发送接口异常
     */
    @RequestMapping(value = "/spm/serviceCenter/approve")
    @ResponseBody
    public ResponseData approveServiceCenter(HttpServletRequest request, Long serviceCenterId, Long salesOrgId) throws Exception {
        IRequest requestContext = createRequestContext(request);
        List<Long> list = new ArrayList<Long>();
        Long result = spmServiceCenterService.approveServiceCenter(requestContext, serviceCenterId, salesOrgId);
        list.add(result);
        return new ResponseData(list);
    }

    /**
     * 拒绝.
     * 
     * @param request
     *            请求上下文
     * @param serviceCenterId
     *            服务中心id
     * @return 服务中心
     */
    @RequestMapping(value = "/spm/serviceCenter/reject")
    @ResponseBody
    public ResponseData rejectServiceCenter(HttpServletRequest request, Long serviceCenterId) {
        IRequest requestContext = createRequestContext(request);
        List<Long> list = new ArrayList<Long>();
        Long result = spmServiceCenterService.rejectServiceCenter(requestContext, serviceCenterId);
        list.add(result);
        return new ResponseData(list);
    }

    /**
     * 删除服务中心.
     * 
     * @param request
     *            请求上下文
     * @param serviceCenters
     *            服务中心DTO
     * @param result
     *            数据绑定结果
     * @return 服务中心
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/serviceCenter/close")
    @ResponseBody
    public ResponseData closeServiceCenter(HttpServletRequest request,
            @RequestBody List<SpmServiceCenter> serviceCenters, BindingResult result) throws SystemProfileException {
        getValidator().validate(serviceCenters, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmServiceCenterService.closeServiceCenter(requestContext, serviceCenters));
    }
    
    /**
     * 动态切换市场.
     * @param request 统一上下文
     * @param salesOrgId 销售组织id
     * @return 返回市场
     */
    @RequestMapping(value = "/spm/serviceCenter/selectMarketByOrg")
    public ResponseData selectMarketByOrg(HttpServletRequest request, Long salesOrgId){
        List<SpmMarket> list = new ArrayList<>();
        SpmMarket market = spmServiceCenterService.selectBySalesOrgId(createRequestContext(request), salesOrgId);
        list.add(market);
        return new ResponseData(list);
    }
}
