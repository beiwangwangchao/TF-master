/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.config.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkkhpg.dsis.admin.config.service.ISpmClosePeriodService;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.service.ISpmPeriodService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 奖金期间差异controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class SpmPeriodController extends BaseController {

    @Autowired
    private ISpmPeriodService spmPeriodService;
    @Autowired
    private ISpmClosePeriodService spmClosePeriodService;

    /**
     * 奖金期间查询.
     * 
     * @param request
     *            request context
     * @param spmPeriod
     *            SpmPeriod
     * @param page
     *            start page
     * @param pagesize
     *            page size
     * @return ResponseData
     */
    @RequestMapping(value = "/spm/period/querySpmPeriod")
    public ResponseData querySpmPeriod(HttpServletRequest request, SpmPeriod spmPeriod,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        if (spmPeriod.getOrgId() == null && spmPeriod.getPeriodYear() == null) {
            return new ResponseData();
        }
        List<SpmPeriod> list = spmPeriodService.queryBonusPeriod(requestContext, spmPeriod, page, pagesize);
        return new ResponseData(list);
    }

    /**
     * 生成奖金期间.
     * 
     * @param request
     *            request context
     * @param spmPeriod
     *            SpmPeriod
     * @return ResponseData
     */
    @RequestMapping(value = "/spm/period/generateBonusPeriod", method = RequestMethod.POST)
    public ResponseData generateBonusPeriod(HttpServletRequest request, SpmPeriod spmPeriod) {
        IRequest requestContext = createRequestContext(request);
        spmPeriodService.generateBonusPeriod(requestContext, spmPeriod);
        return new ResponseData();
    }

    /**
     * 关闭奖金期间.
     * 
     * @param request
     *            统一上下文
     * @param spmPeriod
     *            期间信息
     * @return 返回信息
     * @throws CommSystemProfileException
     * @throws IntegrationException
     *             接口异常
     */
    @RequestMapping(value = "/spm/period/closeBonusPeriod", method = RequestMethod.POST)
    public ResponseData closeBonusPeriod(HttpServletRequest request, SpmPeriod spmPeriod)
            throws CommSystemProfileException, IntegrationException {
        IRequest requestContext = createRequestContext(request);
        spmClosePeriodService.closeBonusPeriod(requestContext, spmPeriod);
        return new ResponseData();
    }

    @RequestMapping(value = "/spm/period/queryByMarket", method = RequestMethod.POST)
    public ResponseData queryByMarket(HttpServletRequest request, SpmMarket market) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmPeriodService.queryMarket(requestContext, market));

    }

    @RequestMapping(value = "/spm/period/getSpmPeriodNameBySalesOrgId", method = RequestMethod.POST)
    public ResponseData getSpmPeriodNameBySalesOrgId(HttpServletRequest request, @Param("param") String param) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmPeriodService.getSpmPeriodNameBySalesOrgId(requestContext, param));
    }
    
    @RequestMapping(value = "/spm/period/getSpmPeriodNameBySalesOrgIdNoClose", method = RequestMethod.POST)
    public ResponseData getSpmPeriodNameBySalesOrgId(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmPeriodService.getSpmPeriodNameBySalesOrgIdNoClose(requestContext));
    }

    @RequestMapping(value = "/spm/period/queryPeriodInTw", method = RequestMethod.POST)
    public ResponseData queryByMarket(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmPeriodService.queryClosingPeriodInTw(requestContext));
    }
}
