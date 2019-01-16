/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lkkhpg.dsis.mws.dto.BmBonusDetail;
import com.lkkhpg.dsis.mws.dto.BmBonusPayDetail;
import com.lkkhpg.dsis.mws.service.IBonusDetailService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 奖金明细controller.
 * 
 * @author guanghui.liu
 *
 */
@Controller
public class BonusDetailController extends BaseController {

    @Autowired
    private IBonusDetailService bonusDetailService;

    /**
     * 查询奖金明细.
     * 
     * @param request
     *            统一上下文
     * @param bmBonusDetail
     *            包含奖金月份
     * @return 响应信息
     */
    @RequestMapping(value = "/account/queryBonusDetail", method = RequestMethod.POST)
    public ResponseData queryBonusDetail(HttpServletRequest request, BmBonusDetail bmBonusDetail) {
        IRequest requestContext = createRequestContext(request);
        List<BmBonusDetail> result = bonusDetailService.queryBonusDetails(requestContext, bmBonusDetail);
        return new ResponseData(result);
    }

    /**
     * 查询奖金支付明细.
     * 
     * @param request
     *            统一上下文
     * @param bmBonusPayDetail
     *            包含奖金月份
     * @return 响应信息
     */
    @RequestMapping(value = "/account/queryBonusPayDetail", method = RequestMethod.POST)
    public ResponseData queryBonusPayDetail(HttpServletRequest request, BmBonusPayDetail bmBonusPayDetail) {
        IRequest requestContext = createRequestContext(request);
        List<BmBonusPayDetail> result = bonusDetailService.queryBonusPayDetail(requestContext, bmBonusPayDetail);
        return new ResponseData(result);
    }
}
