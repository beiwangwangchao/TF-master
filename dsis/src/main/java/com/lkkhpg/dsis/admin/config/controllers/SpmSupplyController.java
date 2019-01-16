/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import com.lkkhpg.dsis.admin.config.service.ISpmSupplyService;
import com.lkkhpg.dsis.admin.system.service.ISysUserRoleAssignService;
import com.lkkhpg.dsis.common.config.dto.SpmSupplies;
import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 供货组织Controller.
 * 
 * @author wangc
 */
@Controller
public class SpmSupplyController extends BaseController {

    @Autowired
    private ISpmSupplyService spmSupplyService;

    @Autowired
    private ISysUserRoleAssignService sysUserRoleAssignService;
    /**
     * 查询供货组织.
     * 
     * @param supply
     *            供货组织DTO
     * @param page
     *            页码
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/supply/query")
    @ResponseBody
    public ResponseData querySupply(HttpServletRequest request, SpmSupply supply,
                                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
        //为最高权限可以不受控制，系统管理员的id为1
        if(userId==1){
            return new ResponseData(spmSupplyService.querySupply(requestContext, supply, page, pagesize));
        }

//        Map<String, Object> map = new HashMap<>();
//        map =sysUserRoleAssignService.getOrgs(RequestHelper.createServiceRequest(request), userId, roleId);
//        List<SpmSalesOrganization> salesOrgIds=(List<SpmSalesOrganization>)map.get("salesOrg");
        return new ResponseData(spmSupplyService.querySupplyByUserAndRole(requestContext, supply, page, pagesize));

    }

    /**
     * 根据id查询供货组织.
     * 
     * @param request
     *            请求上下文
     * @param supply
     *            供货组织
     * @param page
     *            页码
     * @param pagesize
     *            分页大小
     * @return 供货组织列表
     */
    @RequestMapping(value = "/spm/supply/querySupply")
    @ResponseBody
    public ResponseData querySupplyById(HttpServletRequest request, SpmSupply supply,
                                        @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        List<SpmSupply> items = spmSupplyService.query(createRequestContext(request), supply, page,
                pagesize);
        return new ResponseData(items);
    }

    /**
     * 供货组织保存.
     * 
     * @param request
     *            请求上下文
     * @param spmSupplies
     *            供货组织
     * @return 供货组织
     * @throws CommSystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/supply/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveSupply(HttpServletRequest request, @RequestBody SpmSupplies spmSupplies)
            throws CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        List<SpmSupplies> list = new ArrayList<SpmSupplies>();
        list.add(spmSupplyService.saveSupplies(requestContext, spmSupplies));
        return new ResponseData(list);
    }
    
    /**
     * 删除库存.
     * 
     * @param request
     *            请求上下文
     * @param spmSupplies
     *            供货组织
     * @return 供货组织
     * @throws CommSystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/supply/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSupply(HttpServletRequest request, @RequestBody List<SpmSupply> spmSupplies)
            throws CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmSupplyService.deleteSpmSupply(requestContext, spmSupplies));
    }
    
    /**
     * 删除供货销售区域.
     * 
     * @param request
     *            请求上下文
     * @param spmSupplies
     *            供货组织
     * @return 供货组织
     * @throws CommSystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/supply/salesOrgRemove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSupplySales(HttpServletRequest request, @RequestBody List<SpmSupply> spmSupplies)
            throws CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmSupplyService.deleteSupplySales(requestContext, spmSupplies);
        return new ResponseData();
    }
    
    /**
     * 删除供货地点.
     * 
     * @param request
     *            请求上下文
     * @param spmSupplies
     *            供货组织
     * @return 供货组织
     * @throws CommSystemProfileException
     *             基础设置异常
     */
    @RequestMapping(value = "/spm/supply/addressRemove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteSupplyAddress(HttpServletRequest request, @RequestBody List<SpmSupply> spmSupplies)
            throws CommSystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmSupplyService.deleteSupplyAddress(requestContext, spmSupplies);
        return new ResponseData();
    }
    
    /**
     * 查询销售组织.
     * 
     * @param request 请求上下文
     * @param spmSupply 供货销售区域dto
     * @param page 页码
     * @param pagesize 每页记录数
     * @return 销售组织列表
     */
    @RequestMapping(value = "/spm/supply/querySalesOrg")
    @ResponseBody
    public ResponseData querySalesOrg(HttpServletRequest request, SpmSupply spmSupply,
                                      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmSupplyService.querySalesOrganization(requestContext, spmSupply, page, pagesize));
    }
    
    /**
     * 查询供货地址.
     * 
     * @param request 请求上下文
     * @param spmSupply 供货地址dto
     * @param page 页码
     * @param pagesize 每页记录数
     * @return 供货地址列表
     */
    @RequestMapping(value = "/spm/supply/queryAddress")
    @ResponseBody
    public ResponseData queryAddress(HttpServletRequest request, SpmSupply spmSupply,
                                     @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmSupplyService.queryAddress(requestContext, spmSupply, page, pagesize));
    }
}
