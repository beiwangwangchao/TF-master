/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.report.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;
import com.lkkhpg.dsis.common.report.service.IBonusDetailInquiryService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 报表 - BonusDetailInquiry控制器.
 * 
 * @author guanghui.liu
 */
@Controller
public class BonusDetailInquiryController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BonusDetailInquiryController.class);

    @Autowired
    private IBonusDetailInquiryService bonusDetailInquiryService;

    @RequestMapping(value = "/report/queryBasicSalesBonusRoot", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryBasicSalesBonusRoot(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryBasicSalesBonusRoot(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryBasicSalesBonusLeaf", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryBasicSalesBonusLeaf(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryBasicSalesBonusLeaf(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryExtraSalesBonusRoot", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryExtraSalesBonusRoot(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryExtraSalesBonusRoot(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryExtraSalesBonusLeaf", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryExtraSalesBonusLeaf(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryExtraSalesBonusLeaf(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryPerformanceBonusRoot", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryPerformanceBonusRoot(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryPerformanceBonusRoot(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryPerformanceBonusLeaf", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryPerformanceBonusLeaf(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryPerformanceBonusLeaf(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryLeadershipBonusRoot", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryLeadershipBonusRoot(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryLeadershipBonusRoot(requestContext, gdsSalaryMaster);
    }

    @RequestMapping(value = "/report/queryLeadershipBonusLeaf", method = RequestMethod.POST)
    @ResponseBody
    public List<GdsSalaryMaster> queryLeadershipBonusLeaf(HttpServletRequest request, GdsSalaryMaster gdsSalaryMaster) {
        IRequest requestContext = createRequestContext(request);
        return bonusDetailInquiryService.queryLeadershipBonusLeaf(requestContext, gdsSalaryMaster);
    }

}
