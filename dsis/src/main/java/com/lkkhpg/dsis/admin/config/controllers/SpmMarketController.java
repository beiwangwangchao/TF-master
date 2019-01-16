/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.config.controllers;

import com.lkkhpg.dsis.admin.config.dto.SpmOrgParamValue;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmOrgParamService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
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
import java.util.Map;

/**
 * 市场 Controller.
 * 
 * @author huangjiajing
 */
@Controller
public class SpmMarketController extends BaseController {

    @Autowired
    private ISpmMarketService spmMarketService;

    @Autowired
    private ISpmOrgParamService spmOrgParamService;
    /**
     * 保存市场.
     * 
     * @param markets
     *            市场List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/market/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveMarket(@RequestBody List<SpmMarket> markets, BindingResult result,
                                   HttpServletRequest request) throws CommSystemProfileException {
        getValidator().validate(markets, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        List<SpmMarket> lists= spmMarketService.saveMarket(requestContext, markets);
        if(lists==null){
           ResponseData rd= new ResponseData();
           rd.setSuccess(false);
           rd.setMessage("请删除已有的组织关联，刷新后再进行更改操作");
           return rd;
        }else{
            SpmOrgParamValue value= new SpmOrgParamValue();
            SpmOrgParamValue value1= new SpmOrgParamValue();
            SpmOrgParamValue value2= new SpmOrgParamValue();
            SpmOrgParamValue value3=new SpmOrgParamValue();
            for(SpmMarket spmMarket:markets){
                if(spmMarket.getMarketId()!=null) {
                    List<SpmOrgParamValue> spmOrgParamValues = new ArrayList<SpmOrgParamValue>();
                    value.setOrgId(spmMarket.getMarketId());
                    value.setOrgType("MKT");
                    value.setParamName("SPM.SHOP_VISIBLE");
                    value.setParamValue("Y");
                    value1.setOrgId(spmMarket.getMarketId());
                    value1.setOrgType("MKT");
                    value1.setParamName("SPM.CURRENCY");
                    value1.setParamValue("CNY");
                    value2.setOrgId(spmMarket.getMarketId());
                    value2.setOrgType("MKT");
                    value2.setParamName("SPM.MEMBER_TYPE");
                    value2.setParamValue("Y");
                    value3.setOrgType("MKT");
                    value3.setParamName("SPM.MEMBER_AUTO_APPROVED");
                    value3.setParamValue("Y");
                    spmOrgParamValues.add(value);
                    spmOrgParamValues.add(value1);
                    spmOrgParamValues.add(value2);
                    spmOrgParamValues.add(value3);
                    spmOrgParamService.saveOrgParamValues(requestContext, spmOrgParamValues);
                }else{
                    List<SpmMarket> values =spmMarketService.queryMarket(requestContext, spmMarket);
                    for(SpmMarket market:values ){
                        List<SpmOrgParamValue> spmOrgParams = new ArrayList<SpmOrgParamValue>();
                        value.setOrgId(market.getMarketId());
                        value.setOrgType("MKT");
                        value.setParamName("SPM.SHOP_VISIBLE");
                        value.setParamValue("Y");
                        value1.setOrgId(market.getMarketId());
                        value1.setOrgType("MKT");
                        value1.setParamName("SPM.CURRENCY");
                        value1.setParamValue("CNY");
                        value2.setOrgId(market.getMarketId());
                        value2.setOrgType("MKT");
                        value2.setParamName("SPM.MEMBER_TYPE");
                        value2.setParamValue("Y");
                        value3.setOrgType("MKT");
                        value3.setParamName("SPM.MEMBER_AUTO_APPROVED");
                        value3.setParamValue("Y");
                        spmOrgParams.add(value);
                        spmOrgParams.add(value1);
                        spmOrgParams.add(value2);
                        spmOrgParams.add(value3);
                        spmOrgParamService.saveOrgParamValues(requestContext, spmOrgParams);
                    }
                }
            }
            return new ResponseData(lists);
        }
    }

    /**
     * 删除市场.
     * 
     * @param markets
     *            市场List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    @RequestMapping(value = "/spm/market/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMarket(@RequestBody List<SpmMarket> markets, BindingResult result,
                                     HttpServletRequest request) throws SystemProfileException {
        IRequest requestContext = createRequestContext(request);
        spmMarketService.deleteMarket(requestContext, markets);
        return new ResponseData();
    }

    /**
     * 查询市场.
     * 
     * @param market
     *            市场DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/market/query")
    @ResponseBody
    public ResponseData queryMarket(SpmMarket market, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
        if(userId == 1) {
            return new ResponseData(spmMarketService.queryMarket(requestContext, market, page, pagesize));
        }else {
            return new ResponseData(spmMarketService.queryMarket2(requestContext, market, page, pagesize));
        }
    }

    /**
     * 根据销售组织Id获取公司和市场.
     * 
     * @param salesOrgId
     *            销售组织Id
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/market/getCompAndMarket")
    @ResponseBody
    public ResponseData getCompAndMarket(Long salesOrgId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        Map<String, Object> map = spmMarketService.getCompAndMarket(requestContext, salesOrgId);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result.add(map);

        return new ResponseData(result);
    }

    /**
     * 获取市场数量和消息数量.
     * 
     * @param request
     *            请求上下文
     * @return json
     */
    @RequestMapping(value = "/spm/market/queryMarketsAndSmsQuanties")
    @ResponseBody
    public ResponseData queryMarketsAndSmsQuanties(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        Map<String, Object> map = spmMarketService.queryMarketsAndSmsQuanties(requestContext);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result.add(map);

        return new ResponseData(result);
    }

    /**
     * 获取用户可访问的市场.
     * 
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/spm/market/queryMarketsByRole")
    @ResponseBody
    public ResponseData queryMarketsByRole(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmMarketService.queryMarketsByRole(requestContext));
    }



}
