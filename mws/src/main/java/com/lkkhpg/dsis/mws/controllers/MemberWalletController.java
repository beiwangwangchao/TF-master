/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.mws.service.IMemWalletService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * 会员钱包控制层.
 * 
 * @author gulin
 */

@Controller
public class MemberWalletController extends BaseController {

    @Autowired
    private IMemWalletService memWalletService;

    @Autowired
    private IAESClientService aESClientService;

    /**
     * 查询登录会员银行卡.
     * 
     * @param request
     *            统一上下文.
     * @return 返回银行卡信息.
     */
    @RequestMapping(value = "/mm/bankcard/query")
    @ResponseBody
    public ResponseData queryMemCards(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Long memberId = (Long) request.getSession().getAttribute("memberId");
        List<MemCard> memCards = new ArrayList<MemCard>();
        memCards = memWalletService.queryMemCards(iRequest, memberId);
        ResponseData data = new ResponseData(memCards);
        return data;
    }

    /**
     * 查询登录会员银行卡.
     * 
     * @param request
     *            统一上下文.
     * @return 返回银行卡信息.
     */
    @RequestMapping(value = "/mm/bankcard/queryDefaultCard")
    @ResponseBody
    public ResponseData queryDefaultCard(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Long memberId = (Long) request.getSession().getAttribute("memberId");
        List<MemCard> memCards = new ArrayList<MemCard>();
        memCards = memWalletService.queryMemCards(iRequest, memberId);
        List<MemCard> mcs = new ArrayList<MemCard>();
        for (MemCard mc : memCards) {
            if (BaseConstants.YES.equals(mc.getDefaultFlag())) {
                mc.setCardNumber(aESClientService.decrypt(mc.getCardNumber()));
                mcs.add(mc);
                break;
            }
        }
        ResponseData data = new ResponseData(mcs);
        return data;
    }

    /**
     * 根据登录会员ID查询优惠券.
     * 
     * @param request
     *            统一上下文.
     * @param isUsed
     *            是否使用标记.
     * @param scope
     *            使用范围.
     * @return 返回优惠券数据及会员市场本位币
     */
    @RequestMapping(value = "/mm/voucher/query")
    @ResponseBody
    public ResponseData queryMemVouchers(HttpServletRequest request, String isUsed, String scope) {
        IRequest iRequest = createRequestContext(request);
        Long memberId = (Long) request.getSession().getAttribute("memberId");
        List<Object> vouchers = new ArrayList<Object>();
        vouchers = memWalletService.queryVouchers(iRequest, memberId, isUsed, scope);
        ResponseData data = new ResponseData(vouchers);
        return data;
    }
}
