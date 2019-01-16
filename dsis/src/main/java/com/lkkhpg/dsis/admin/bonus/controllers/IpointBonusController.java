/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IIpointBonusService;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.common.bonus.dto.IpointBonus;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * ipoint奖金记录 Controller.
 * 
 * @author wangc
 *
 */
@Controller
public class IpointBonusController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(IpointBonusController.class);

    @Autowired
    private IIpointBonusService ipointBonusService;

    /**
     * ipoint奖金记录查询.
     * 
     * @param request
     *            统一上下文.
     * @param response
     * @param ipointBonus
     *            查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            页数.
     * @return 返回结果
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     */
    @RequestMapping(value = "/bm/ipointBonus/query")
    @ResponseBody
    public ResponseData queryIpointBonus(HttpServletRequest request, HttpServletResponse response,
            IpointBonus ipointBonus, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = BonusConstants.BONUS_DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        List<IpointBonus> list = ipointBonusService.queryIpointBonuses(createRequestContext(request), ipointBonus, page,
                pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

    /**
     * ipoint奖金记录提交.
     * 
     * @param request
     *            请求上下文
     * @param result
     *            校验结果
     * @param ipointBonus
     *            奖金记录
     * @return 奖金记录列表
     * @throws CommBonusException
     *             奖金基础异常
     */
    @RequestMapping(value = "/bm/ipointBonus/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitIpointBonus(HttpServletRequest request, @RequestBody List<IpointBonus> ipointBonus,
            BindingResult result) throws CommBonusException {
        getValidator().validate(ipointBonus, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        return new ResponseData(ipointBonusService.submitIpointBonuses(createRequestContext(request), ipointBonus));
    }

    /**
     * ipoint奖金记录审核.
     * 
     * @param request
     *            请求上下文
     * @param result
     *            校验结果
     * @param ipointBonus
     *            奖金记录
     * @return 奖金记录列表
     * @throws CommBonusException
     *             奖金基础异常
     */
    @RequestMapping(value = "/bm/ipointBonus/approve", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData approveIpointBonus(HttpServletRequest request, @RequestBody List<IpointBonus> ipointBonus,
            BindingResult result) throws CommBonusException {
        getValidator().validate(ipointBonus, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        return new ResponseData(ipointBonusService.approveIpointBonuses(createRequestContext(request), ipointBonus));
    }

    /**
     * ipoint奖金记录拒绝.
     * 
     * @param request
     *            请求上下文
     * @param result
     *            校验结果
     * @param ipointBonus
     *            奖金记录
     * @return 奖金记录列表
     * @throws CommBonusException
     *             奖金基础异常
     */
    @RequestMapping(value = "/bm/ipointBonus/reject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData rejectIpointBonus(HttpServletRequest request, @RequestBody List<IpointBonus> ipointBonus,
            BindingResult result) throws CommBonusException {
        getValidator().validate(ipointBonus, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        return new ResponseData(ipointBonusService.rejectIpointBonuses(createRequestContext(request), ipointBonus));
    }

}
