/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusAdjustService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusService;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.common.bonus.dto.BonusAdjust;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 奖金调整.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Controller
public class BonusAdjustController extends BaseController {

    @Autowired
    private IBonusService bonusService;

    @Autowired
    private IBonusAdjustService bonusAdjustService;

    /**
     * 奖金期间查询.
     * 
     * @param request
     *            请求上下文
     * @param marketId
     *            页面市场id
     * @return 奖金期间列表
     */
    @RequestMapping(value = "/bm/getBonusPeriod")
    @ResponseBody
    public List<SpmPeriod> getBonusPeriod(HttpServletRequest request, @RequestParam("marketId") Long marketId) {
        return bonusService.getPeriod(createRequestContext(request), marketId);
    }

    /**
     * 能用期间.
     * 
     * @param request
     *            上下文请求
     * @return 奖金期间列表
     */
    @RequestMapping(value = "/bm/getUsableBonusPeriod")
    @ResponseBody
    public List<SpmPeriod> getUsableBonusPeriod(HttpServletRequest request, @RequestParam("marketId") Long marketId) {
        return bonusService.getUsablePeriod(createRequestContext(request), marketId);
    }

    /**
     * 保存奖金调整信息.
     * 
     * @param request
     *            请求上下文
     * @param bonusAdjust
     *            奖金调整信息
     * @return 数据返回对象
     */
    @RequestMapping(value = "/bm/saveBonusAdjust")
    @ResponseBody
    public ResponseData saveBonusAdjust(HttpServletRequest request, @RequestBody BonusAdjust bonusAdjust) {
        BonusAdjust result = bonusAdjustService.saveBonusAdjust(createRequestContext(request), bonusAdjust);
        List<BonusAdjust> bonusAdjustsList = new ArrayList<>();
        bonusAdjustsList.add(result);
        return new ResponseData(bonusAdjustsList);
    }

    /**
     * 查询奖金调整信息.
     * 
     * @param request
     *            请求上下文
     * @param response
     * @param bonusAdjust
     *            奖金调整信息
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 数据返回对象
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "/bm/queryBonusAdjust")
    @ResponseBody
    public ResponseData queryBonusAdjust(HttpServletRequest request, HttpServletResponse response,
            BonusAdjust bonusAdjust, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = BonusConstants.BONUS_DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        List<BonusAdjust> list = bonusAdjustService.queryBonusAdjust(createRequestContext(request), bonusAdjust, page,
                pagesize);
        ResponseData result = new ResponseData(list);
        // result.setRows();
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return result;
        }
    }

    /**
     * 删除奖金调整信息.
     * 
     * @param request
     *            请求上下文
     * @param bonusAdjusts
     *            删除奖金列表
     * @return 数据返回对象
     */
    @RequestMapping(value = "/bm/deleteBonusAdjust")
    @ResponseBody
    public ResponseData deleteBonusAdjust(HttpServletRequest request, @RequestBody List<BonusAdjust> bonusAdjusts) {
        ResponseData result = new ResponseData();
        if (bonusAdjustService.delBatchBonusAdjust(createRequestContext(request), bonusAdjusts) <= 0) {
            result.setSuccess(false);
        }
        return result;
    }
}
