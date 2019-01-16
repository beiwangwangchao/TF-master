/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.order.service.IAutoshipGiftRuleService;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.AutoshipGiftRule;
import com.lkkhpg.dsis.common.order.dto.AutoshipGifts;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 自动订货赠品规则Controller.
 * 
 * @author hanrui.huang
 *
 */
@Controller
public class AutoshipGiftRuleController extends BaseController {

    @Autowired
    private IAutoshipGiftRuleService autoshipGiftRuleService;

    /**
     * 查询自动订货单赠品规则.
     * 
     * @param request
     *            请求上下文
     * @param autoshipGiftRule
     *            自动订货单赠品规则DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 自动订货单赠品规则List
     */
    @RequestMapping(value = "/om/autoshipgiftrule/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryAutoshipGiftRules(HttpServletRequest request, AutoshipGiftRule autoshipGiftRule,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(
                autoshipGiftRuleService.queryAutoshipGiftRules(iRequest, autoshipGiftRule, page, pagesize));
    }

    /**
     * 查询自动订货单赠品.
     * 
     * @param request
     *            请求上下文
     * @param autoshipGifts
     *            自动订货单赠品DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 自动订货单赠品List
     */
    @RequestMapping(value = "/om/autoshipGifts/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryAutoshipGifts(HttpServletRequest request, AutoshipGifts autoshipGifts,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(autoshipGiftRuleService.queryAutoshipGifts(iRequest, autoshipGifts, page, pagesize));
    }

    /**
     * 保存自动订货单赠品规则.
     * @param request 请求上下文
     * @param autoshipGiftRules 自动订货单赠品规则List
     * @return 响应数据
     * @throws CommOrderException 系统配置统一异常
     */
    @RequestMapping(value = "/om/autoshipGiftRule/save")
    @ResponseBody
    public ResponseData saveAutoshipGiftRules(HttpServletRequest request,
            @RequestBody List<AutoshipGiftRule> autoshipGiftRules) throws CommOrderException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(autoshipGiftRuleService.saveAutoshipGiftRules(requestContext, autoshipGiftRules));
    }
}
