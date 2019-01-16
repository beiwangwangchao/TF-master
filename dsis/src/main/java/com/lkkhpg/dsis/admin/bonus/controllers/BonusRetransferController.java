/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusRetransferService;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * re_transfer 控制.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Controller
public class BonusRetransferController extends BaseController {

    @Autowired
    private IBonusRetransferService retransferService;

    /**
     * 查询re_transfer信息.
     * 
     * @param request
     *            请求上下文
     * @param response
     * @param retransferOrder
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
    @RequestMapping(value = "/bm/retransfer/query")
    @ResponseBody
    public ResponseData queryRetransferOrder(HttpServletRequest request, HttpServletResponse response,
            BonusRetransfer retransferOrder, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = BonusConstants.BONUS_DEFAULT_PAGE_SIZE) int pagesize)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest iRequest = createRequestContext(request);
        // if (iRequest.getAttribute(SystemProfileConstants.MARKET_ID) != null)
        // {
        // retransferOrder
        // .setMarketId(Long.parseLong(iRequest.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        // }
        List<BonusRetransfer> list = retransferService.queryRetransfer(iRequest, retransferOrder, page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }
    }

}
