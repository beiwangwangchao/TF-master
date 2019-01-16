/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.config.dto.SpmCity;
import com.lkkhpg.dsis.common.config.dto.SpmCountry;
import com.lkkhpg.dsis.common.config.dto.SpmState;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.service.ISpmCityService;
import com.lkkhpg.dsis.common.service.ISpmCountryService;
import com.lkkhpg.dsis.common.service.ISpmStateService;
import com.lkkhpg.dsis.mws.dto.MemberInfo;
import com.lkkhpg.dsis.mws.service.IMemberInfoService;
import com.lkkhpg.dsis.mws.service.IMemberSiteService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.exception.TokenException;

/**
 * 我的账户controller.
 * 
 * @author guanghui.liu
 *
 */
@Controller
public class MemberInfoController extends BaseController {

    @Autowired
    private IMemberInfoService memberInfoService;

    @Autowired
    private IMemberSiteService memberSiteService;

    @Autowired
    private ISpmCountryService spmCountryService;

    @Autowired
    private ISpmStateService spmStateService;

    @Autowired
    private ISpmCityService spmCityService;

    /**
     * 更新会员联系方式.
     * 
     * @param request
     *            请求上下文
     * @param memberInfo
     *            包含memberId 需要保存的信息
     * @return 返回响应信息
     * @throws CommMemberException
     *             抛出业务异常
     * @throws TokenException token验证异常
     */
    @RequestMapping(value = "/account/updateMemCtact", method = RequestMethod.POST)
    public ResponseData updateMemCtact(HttpServletRequest request, @RequestBody MemberInfo memberInfo)
            throws CommMemberException, TokenException {
        IRequest requestContext = createRequestContext(request);
        checkToken(request, memberInfo);
        memberInfoService.updateMemberInfo(requestContext, memberInfo);
        return new ResponseData();
    }

    /**
     * 插入联系信息.
     * 
     * @param request
     *            请求上下文
     * @return 返回响应信息
     */
    @RequestMapping(value = "/account/queryMemInfo", method = RequestMethod.POST)
    public ResponseData queryMemInfo(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(MemberConstants.MWS_MEMBER_ID);
        MemberInfo memberInfo = memberInfoService.queryMemberInfo(requestContext, memberId);
        List<MemSite> siteList = new ArrayList<MemSite>();
        // 查询出所有地址的集合
        MemSite allMem = new MemSite();
        allMem.setMemberId(memberId);
        List<MemSite> allList = memberSiteService.queryMemSites(requestContext, allMem);
        for (MemSite ms : allList) {
            if (MemberConstants.MEM_SITE_TYPE_CTACT.equals(ms.getSiteUseCode())) { // 联系地址
                siteList.add(ms);
            } else if (MemberConstants.MEM_SITE_TYPE_SHIP.equals(ms.getSiteUseCode())) { // 默认收货地址
                if (UserConstants.YES.equals(ms.getDefaultFlag())) {
                    siteList.add(ms);
                }
            } else if (MemberConstants.MEM_SITE_TYPE_BILL.equals(ms.getSiteUseCode())) { // 默认账单地址
                if (UserConstants.YES.equals(ms.getDefaultFlag())) {
                    siteList.add(ms);
                }
            }
        }
        memberInfo.setMemSites(siteList);
        List<MemberInfo> list = new ArrayList<MemberInfo>();
        list.add(memberInfo);
        return new ResponseData(list);
    }

    /**
     * 查询国家.
     * 
     * @param country
     *            国家DTO
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/account/queryCountry")
    @ResponseBody
    public ResponseData queryCountry(HttpServletRequest request, SpmCountry country) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCountryService.queryCountryForLocation(requestContext, country));
    }

    /**
     * 查询省/州.
     * 
     * @param state
     *            省/州DTO
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/account/queryState")
    @ResponseBody
    public ResponseData queryState(HttpServletRequest request, SpmState state) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmStateService.queryStateForLocation(requestContext, state));
    }

    /**
     * 查询城市.
     * 
     * @param city
     *            城市DTO
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/account/queryCity")
    @ResponseBody
    public ResponseData queryCity(HttpServletRequest request, SpmCity city) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(spmCityService.queryCityForLocation(requestContext, city));
    }

}
