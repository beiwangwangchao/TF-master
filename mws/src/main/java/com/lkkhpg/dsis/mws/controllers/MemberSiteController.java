/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.service.IMemberSiteService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.TokenException;

/**
 * 地址controller.
 * 
 * @author guanghui.liu
 *
 */
@Controller
public class MemberSiteController extends BaseController {

    @Autowired
    private IMemberSiteService memberSiteService;
    

    /**
     * 查询会员地址.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            包含地址类型信息
     * @return 返回地址列表
     */
    @RequestMapping("/account/queryAddress")
    public ResponseData queryAddress(HttpServletRequest request, MemSite memSite) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(MemberConstants.MWS_MEMBER_ID);
        memSite.setMemberId(memberId);
        List<MemSite> memSites = memberSiteService.queryMemSites(requestContext, memSite);
        return new ResponseData(memSites);
    }

    /**
     * 修改地址默认标记.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            包含siteid和标记
     * @param _token token
     * @return 返回响应信息
     * @throws TokenException token验证异常
     */
    @RequestMapping("/account/changeDefaultFlag")
    public ResponseData changeDefaultFlag(HttpServletRequest request, MemSite memSite, @RequestParam String _token) throws TokenException {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession(false);
        memSite.set_token(_token);
        checkToken(request, memSite);
        Long memberId = (Long) session.getAttribute(MemberConstants.MWS_MEMBER_ID);
        memSite.setMemberId(memberId);
        memberSiteService.updateDefaultFlag(requestContext, memSite);
        return new ResponseData();
    }

    /**
     * 保存地址.
     * 
     * @param request
     *            请求上下文
     * @param memSite
     *            需要保存的地址信息
     * @return 返回响应信息
     * @throws TokenException token验证错误
     */
    @RequestMapping(value = "/account/saveAddress")
    @ResponseBody
    public ResponseData saveMemSite(HttpServletRequest request, @RequestBody MemSite memSite) throws TokenException {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession(false);
        if(memSite.getSiteId() != null){
            checkToken(request, memSite);
        }
        Long memberId = (Long) session.getAttribute(MemberConstants.MWS_MEMBER_ID);
        memSite.setMemberId(memberId);
        List<MemSite> list = new ArrayList<MemSite>();
        MemSite afterChange = memberSiteService.saveMemSite(requestContext, memSite);
        list.add(afterChange);
        return new ResponseData(list);
    }

    /**
     * 删除会员地址.
     * 
     * @param request
     *            请求上下午
     * @param memSite
     *            包含siteId
     * @param _token token
     * @return 返回最新的默认地址siteId
     * @throws MemberException
     *             同会员同类型只有单条地址不允许删除
     * @throws TokenException 
     */
    @RequestMapping(value = "/account/deleteAddress")
    @ResponseBody
    public ResponseData deleteAddress(MemSite memSite, HttpServletRequest request, @RequestParam String _token)
            throws MemberException, TokenException {
        IRequest requestContext = createRequestContext(request);
        memSite.set_token(_token);
        checkToken(request, memSite);
        Long defaultSiteId = memberSiteService.deleteMemSite(requestContext, memSite.getSiteId());
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("defaultSiteId", defaultSiteId);
        list.add(map);
        return new ResponseData(list);
    }

}
