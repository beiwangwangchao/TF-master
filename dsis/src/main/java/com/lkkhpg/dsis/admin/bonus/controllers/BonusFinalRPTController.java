/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.bonus.service.IBonusFinalRPTService;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 生成TXT文件报表.
 * 
 * @author hanrui.huang
 *
 */
@Controller
public class BonusFinalRPTController extends BaseController {

    @Autowired
    private IBonusFinalRPTService bonusFinalRPTService;

    /**
     * 生成TXT文件报表RPT-00172.
     * 
     * @param request
     *            请求上下文
     * @param dateFrom
     *            所得给付起始年月
     * @param dateTo
     *            所得给付结束年月
     * @param memberCodeForm
     *            会员编号从
     * @param memberCodeTo
     *            会员编号至
     * @param response
     *            HttpServletResponse请求
     * @throws IOException
     *             异常
     * @throws CommBonusException 统一异常处理
     */
    @RequestMapping(value = "/bm/demo/down", method = RequestMethod.POST)
    @ResponseBody
    public void downLoadTXT(HttpServletRequest request, String dateFrom, String dateTo, String memberCodeForm,
            String memberCodeTo, HttpServletResponse response) throws CommBonusException, IOException {
        IRequest iRequest = createRequestContext(request);
        String fileName = bonusFinalRPTService.downloadFile(iRequest);
        response.setContentType("application/csv;charset=BIG5");
        response.setCharacterEncoding("BIG5");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        bonusFinalRPTService.createTXTFile(iRequest, response, dateFrom, dateTo, memberCodeForm,
                memberCodeTo);
    }
}
