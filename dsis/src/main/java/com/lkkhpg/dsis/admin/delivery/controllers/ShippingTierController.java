/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.delivery.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.delivery.exception.DeliveryException;
import com.lkkhpg.dsis.admin.delivery.service.IShippingTierService;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.constant.DeliveryConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTier;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierDtl;
import com.lkkhpg.dsis.common.delivery.dto.ShippingTierSeg;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.service.ISpmSalesOrganizationService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 承运商控制层.
 * 
 * @author huangjiajing
 */
@Controller
public class ShippingTierController extends BaseController {

    @Autowired
    private IShippingTierService shippingTierService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private ISpmSalesOrganizationService spmSalesOrganizationService;

    /**
     * 根据地点币种，查询物流金额.
     * 
     * @param request
     *            请求上下文 @param location 地点 @param currency 币种 @return
     *            ResponseData结果集
     * @param location
     *            地点
     * @param salesOrgId
     *            销售组织id
     * @param apptype
     *            应用类型
     * @return 运费列表
     */
    @RequestMapping(value = "/dm/shippingTierDtl/queryByLocation")
    @ResponseBody
    public ResponseData queryShippingTierDtl(final HttpServletRequest request, final SpmLocation location,
            final Long salesOrgId, String apptype) {
        IRequest requestContext = createRequestContext(request);
        List<ShippingTier> shippingTiers = shippingTierService.queryShippingTiersByLocation(requestContext, location,
                salesOrgId,apptype);
        return new ResponseData(shippingTiers);
    }

    /**
     * 查询物流资料信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTier
     *            承运商对象
     * @param page
     *            页数 默认为1
     * @param pagesize
     *            每页显示的行数 默认为10
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/dm/shippingTier/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getShippingTiers(HttpServletRequest request, ShippingTier shippingTier,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(shippingTierService.selectShippingTiers(iRequest, shippingTier, page, pagesize));
    }

    /**
     * 查询物流运费信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtl
     *            运费对象
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/dm/shippingTierDtl/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getShippingTierDtls(HttpServletRequest request, ShippingTierDtl shippingTierDtl) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(shippingTierService.selectShippingTierDtls(iRequest, shippingTierDtl));
    }

    /**
     * 查询物流地点信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtlSeg
     *            地点信息
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/dm/shippingTierSeg/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getShippingTierDtlSegs(HttpServletRequest request, ShippingTierSeg shippingTierDtlSeg) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(shippingTierService.selectShippingTierDtlSegs(iRequest, shippingTierDtlSeg));
    }

    /**
     * 获取本位币.
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织
     * @return 币种
     * @throws DeliveryException
     *             发运异常
     */
    @RequestMapping(value = "/dm/shippingtier/getcurrency", method = RequestMethod.POST)
    public ResponseData getCurrency(HttpServletRequest request, Long salesOrgId) throws DeliveryException {
        IRequest iRequest = createRequestContext(request);
        List<String> currencies = paramService.getParamValues(iRequest, DeliveryConstants.SHIPPING_TIER_ORG_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES,
                (Long) iRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        if (currencies == null || currencies.isEmpty()) {
            throw new DeliveryException(DeliveryException.SHIPPING_TIER_ORG_CURRENCY_NOTEXIST, null);
        }
        return new ResponseData(currencies);
    }

    /**
     * 获取销售组织.
     * 
     * @param request
     *            请求上下文
     * @return ResponseData集合
     * @throws DeliveryException
     *             发运统一异常
     */
    @RequestMapping(value = "/dm/shippingtier/getsalesorg", method = RequestMethod.POST)
    public ResponseData getSalesOrg(HttpServletRequest request) throws DeliveryException {
        IRequest iRequest = createRequestContext(request);
        Long salesOrgId = (Long) iRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        SpmSalesOrganization salesOrganization = new SpmSalesOrganization();
        salesOrganization.setSalesOrgId(salesOrgId);
        List<SpmSalesOrganization> salesOrganizations = spmSalesOrganizationService.querySalesOrganization(iRequest,
                salesOrganization, Integer.parseInt(DEFAULT_PAGE), Integer.parseInt(DEFAULT_PAGE_SIZE));
        String salesOrgName = salesOrganizations.get(0).getName();
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        HashMap<String, String> resultHashMap = new HashMap<>();
        resultHashMap.put("salesOrgName", salesOrgName);
        resultHashMap.put("salesOrgId", salesOrgId.toString());

        List<String> currencies = paramService.getParamValues(iRequest, DeliveryConstants.SHIPPING_TIER_ORG_CURRENCY,
                SystemProfileConstants.ORG_TYPE_SALES,
                (Long) iRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        if (currencies == null || currencies.isEmpty()) {
            throw new DeliveryException(DeliveryException.SHIPPING_TIER_ORG_CURRENCY_NOTEXIST, null);
        }
        resultHashMap.put("currencyCode", currencies.get(0));
        result.add(resultHashMap);
        return new ResponseData(result);
    }

    /**
     * 提交判断是增加或修改数据.
     * 
     * @param request
     *            请求上下文
     * @param shippingTiers
     *            承运商对象
     * @param result
     *            验证
     * @return ResponseData结果集
     * @throws DeliveryException
     *             发运统一异常
     */
    @RequestMapping(value = "/dm/shippingtier/submit", method = RequestMethod.POST)
    public ResponseData submitShippingTier(HttpServletRequest request, @RequestBody List<ShippingTier> shippingTiers,
            BindingResult result) throws DeliveryException {
        IRequest requestContext = createRequestContext(request);
        /*for (ShippingTier shippingTier : shippingTiers) {
            shippingTier.setSalesOrgId((Long) requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        }*/

        getValidator().validate(shippingTiers, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        shippingTiers = shippingTierService.batchUpdate(requestContext, shippingTiers);
        return new ResponseData(shippingTiers);
    }

    /**
     * 删除物流运费信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtls
     *            运费集合对象
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/dm/shippingTierDtl/remove", method = RequestMethod.POST)
    public ResponseData removeShippingTierDtls(HttpServletRequest request,
            @RequestBody List<ShippingTierDtl> shippingTierDtls) {
        IRequest requestContext = createRequestContext(request);
        shippingTierService.batchDeleteShippingTierDtls(requestContext, shippingTierDtls);
        return new ResponseData();
    }

    /**
     * 删除物流地点信息.
     * 
     * @param request
     *            请求上下文
     * @param shippingTierDtlSegs
     *            地点集合对象
     * @return ResponseData结果集
     */
    @RequestMapping(value = "/dm/shippingTierSeg/remove", method = RequestMethod.POST)
    public ResponseData removeShippingTierDtlSegs(HttpServletRequest request,
            @RequestBody List<ShippingTierSeg> shippingTierDtlSegs) {
        IRequest requestContext = createRequestContext(request);
        shippingTierService.batchDeleteShippingTierDtlSegs(requestContext, shippingTierDtlSegs);
        return new ResponseData();
    }

}
