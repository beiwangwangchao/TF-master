/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.bonus.service.IBonusReleaseService;
import com.lkkhpg.dsis.common.bonus.dto.BonusRelease;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 税管理controller.
 * 
 * @author gulin
 *
 */
@Controller
public class TaxManagementController extends BaseController {

    @Autowired
    private IBonusReleaseService bonusReleaseService;

    /**
     * 税管理年度查询.
     * 
     * @param request
     *            统一上下文.
     * @param bonuRelease
     *            查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            页面size.
     * @return 返回查询结果.
     */
    @RequestMapping(value = "/bm/tax/query/year")
    @ResponseBody
    public ResponseData queryYearRelease(HttpServletRequest request, BonusRelease bonuRelease,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        ResponseData result = new ResponseData(
                bonusReleaseService.queryYearRelease(createRequestContext(request), bonuRelease, page, pagesize));
        return result;
    }
    
    /**
     * 税管理月度查询.
     * 
     * @param request
     *            统一上下文.
     * @param bonuRelease
     *            查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            页面size.
     * @return 返回查询结果.
     */
    @RequestMapping(value = "/bm/tax/query/month")
    @ResponseBody
    public ResponseData queryMonthRelease(HttpServletRequest request, BonusRelease bonuRelease,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        ResponseData result = new ResponseData(
                bonusReleaseService.queryMonthRelease(createRequestContext(request), bonuRelease, page, pagesize));
        return result;
    }
}
