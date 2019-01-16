/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ISpmCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.mws.service.IMemberShopService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.SysPreferences;
import com.lkkhpg.dsis.platform.service.ISysPreferencesService;

/**
 * 选择店铺controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class MemberShopController extends BaseController {

    private static final String ACCOUNT_SHOP = "/account/account-shop";
    // 跳转
    private static final String REDIRECT = "redirect:";
    /**
     * 首选项-mws类型固定值.
     */
    private static final Long PREFERENCES_LEVEL = 20L;
    /**
     * 组织参数-paramNmae.
     */
    private static final String SHOP_VISIBLE = "SPM.SHOP_VISIBLE";
    /**
     * 组织参数-组织类型.
     */
    private static final String ORG_TYPE = "SALES";
    /**
     * 组织参数值-org可见性.
     */
    private static final String VISIBILITY_TYPE = "Y";

    @Autowired
    private ISpmCompanyService spmCompanyService;

    @Autowired
    private IMemberShopService memberShopService;
    @Autowired
    private ISysPreferencesService sysPreferencesService;
    @Autowired
    private IParamService paramService;
    @Autowired
    private ICommMemberService iCommMemberService;

    /**
     * 查询出所有市场.
     * 
     * @param request
     *            统一上下文
     * @return 返回市场name
     */
    @RequestMapping(value = "/account/queryMarket")
    public ResponseData queryMarket(HttpServletRequest request) {
        IRequest contextRequest = createRequestContext(request);
        List<SpmMarket> list = memberShopService.queryMarket(contextRequest,
                contextRequest.getAttribute(SystemProfileConstants.MARKET_ID));
        //long companyId=list.get(0).getCompanyId();
        //SpmCompany spmCompany =new SpmCompany();
        //spmCompany.setCompanyId(companyId);
        //List<SpmCompany> list1=spmCompanyService.queyBrNo(spmCompany);
        //list.get(0).setSpmCompanies(list1);
        return new ResponseData(list);
    }

    /**
     * 根据id获取市场下面的店铺.
     * 
     * @param request
     *            统一上下文 市场id
     * @param igiCode
     *            IGICode
     * @return 对应的市场信息
     */
    @RequestMapping(value = "/account/selectByMarketName")
    @ResponseBody
    public ResponseData selectByMarketName(HttpServletRequest request,  Member memberId ,String igiCode) {
        Long marketId = Long
                .parseLong(createRequestContext(request).getAttribute(SystemProfileConstants.MARKET_ID).toString());
           List<Member>newList=new ArrayList<Member>();
  //      List<SpmSalesOrganization> newList = new ArrayList<SpmSalesOrganization>();
//        List<SpmSalesOrganization> list = memberShopService.selectByMarketName(createRequestContext(request), marketId);
        List<Member>list=iCommMemberService.selectSalesOrganization(memberId);
        for (Member sson : list) {
            List<String> shopVisible = paramService.getParamValues(createRequestContext(request), SHOP_VISIBLE,
                    ORG_TYPE, sson.getSalesOrgId());
            if (!shopVisible.isEmpty() && VISIBILITY_TYPE.equals(shopVisible.get(0))) {
                newList.add(sson);
            }
        }
        // 获取IGI市场下面的店铺
        if (!"".equals(igiCode) && igiCode != null) {
            SpmMarket sk = memberShopService.selectMarketByCode(createRequestContext(request), igiCode);
            List<SpmSalesOrganization> ls = null;
            if (sk != null) {
                ls = memberShopService.selectByMarketName(createRequestContext(request), sk.getMarketId());
                // 查询IGI店铺的可见性
                for (SpmSalesOrganization spmSalesOrganization : ls) {
                    List<String> shopVisible = paramService.getParamValues(createRequestContext(request), SHOP_VISIBLE,
                            ORG_TYPE, spmSalesOrganization.getSalesOrgId());
                    if (!shopVisible.isEmpty() && VISIBILITY_TYPE.equals(shopVisible.get(0))) {
                        for (SpmSalesOrganization sso : ls) {
                        //    newList.add(sso);
                        }
                    }
                }
            }
        }
        return new ResponseData(newList);

    }

    /**
     * 导航页面下拉切换店铺.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织id
     * @param salesOrgName
     *            店铺名
     * @return boolean
     */
    @RequestMapping(value = "/account/select/shop")
    public ResponseData selectShop(HttpServletRequest request, Long salesOrgId, String salesOrgName) {
        HttpSession session = request.getSession();
        IRequest contextRequest = createRequestContext(request);
        Long rSalesOrgId = Long.parseLong(contextRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID).toString());
        if (!salesOrgId.equals(rSalesOrgId)) {
            session.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
            contextRequest.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                    session.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            session.setAttribute(SystemProfileConstants.SHOP_NAME, salesOrgName);
            contextRequest.setAttribute(SystemProfileConstants.SHOP_NAME,
                    session.getAttribute(SystemProfileConstants.SHOP_NAME));
            return new ResponseData(true);
        }
        return new ResponseData(false);
    }

    /**
     * 登录过后市場下的店铺三种情况.
     * 
     * @param request
     *            统一上下文
     * @return 根据条件跳转
     */
    @RequestMapping(value = "/account/account-shop")
    public ModelAndView loginSelectShop(HttpServletRequest request) {
        ModelAndView result = new ModelAndView();
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        Long marketId = requestContext.getAttribute(SystemProfileConstants.MARKET_ID);
        List<SpmMarket> list = memberShopService.queryMarket(requestContext, marketId);
        List<SpmSalesOrganization> orgList = memberShopService.selectByMarketName(createRequestContext(request),
                marketId);
        // 如果该市场下有多家店铺
        if (orgList.size() > 1) {
            SysPreferences preferences = new SysPreferences();
            preferences.setAccountId(requestContext.getAccountId());
            preferences.setPreferences("defaultSalesOrganization");
            preferences.setPreferencesLevel(PREFERENCES_LEVEL);
            SysPreferences sp = sysPreferencesService.querySysPreferencesLine(requestContext, preferences);
            // 首选项不为空 && 首选项value不能为-1 && 当首选项不可见时
            if (sp != null && !"-1".equals(sp.getPreferencesValue())
                    && VISIBILITY_TYPE.equals((paramService.getParamValues(createRequestContext(request), SHOP_VISIBLE,
                            ORG_TYPE, Long.parseLong(sp.getPreferencesValue()))).get(0))) { // 当有首选项时,直接取首选项店铺
                SpmSalesOrganization sso = memberShopService.selectBySalesOrgId(createRequestContext(request),
                        Long.parseLong(sp.getPreferencesValue()));
                result.setViewName(REDIRECT + "/index.html");
                session.setAttribute(SystemProfileConstants.SALES_ORG_ID, Long.parseLong(sp.getPreferencesValue()));
                requestContext.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                        session.getAttribute(SystemProfileConstants.SALES_ORG_ID));
                session.setAttribute(SystemProfileConstants.SHOP_NAME, sso.getName());
                requestContext.setAttribute(SystemProfileConstants.SHOP_NAME,
                        session.getAttribute(SystemProfileConstants.SHOP_NAME));
            } else { // 当没有首选项时,进入店铺页面选择店铺
                result.setViewName(getViewPath() + ACCOUNT_SHOP);
                result.addObject("market", list);
            }
        } else { // 如果该市场下只有一家店铺，默认该店铺
            result.setViewName(REDIRECT + "/index.html");
            session.setAttribute(SystemProfileConstants.SALES_ORG_ID, orgList.get(0).getSalesOrgId());
            requestContext.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                    session.getAttribute(SystemProfileConstants.SALES_ORG_ID));
            session.setAttribute(SystemProfileConstants.SHOP_NAME, orgList.get(0).getName());
            requestContext.setAttribute(SystemProfileConstants.SHOP_NAME,
                    session.getAttribute(SystemProfileConstants.SHOP_NAME));
        }
        return result;
    }

    /**
     * 市场下多家店铺且没有首选项时.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织id
     * @param name
     *            店铺名
     * @param sign
     *            是否选中标记
     * @return 页面跳转
     */
    @RequestMapping(value = "/account/checkShop")
    public ModelAndView checkShop(HttpServletRequest request, Long salesOrgId, String name, String sign) {
        ModelAndView result = new ModelAndView();
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        if ("Y".equals(sign)) {
            List<SysPreferences> list = new ArrayList<SysPreferences>();
            SysPreferences preferences = new SysPreferences();
            preferences.setPreferences("defaultSalesOrganization");
            preferences.setPreferencesLevel(PREFERENCES_LEVEL);
            preferences.setPreferencesValue(salesOrgId.toString());
            preferences.setAccountId(requestContext.getAccountId());
            list.add(preferences);
            sysPreferencesService.saveSysPreferences(requestContext, list);
        }
        result.setViewName(REDIRECT + "/index.html");
        session.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
        requestContext.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                session.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        session.setAttribute(SystemProfileConstants.SHOP_NAME, name);
        requestContext.setAttribute(SystemProfileConstants.SHOP_NAME,
                session.getAttribute(SystemProfileConstants.SHOP_NAME));
        return result;
    }

    /**
     * 切换语言session中店铺name随着改变.
     * @param request 统一上下文
     * @return 结果集
     */
    @RequestMapping(value = "/account/selectLanguageAfter")
    public ResponseData selectLanguageAfter(HttpServletRequest request) {
        HttpSession session = request.getSession();
        IRequest requestContext = createRequestContext(request);
        Long salesOrgId = requestContext.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        SpmSalesOrganization org = memberShopService.showShopBySalesOrgId(createRequestContext(request),
                salesOrgId);
        /*session.setAttribute(SystemProfileConstants.SHOP_NAME, org.getName());
        requestContext.setAttribute(SystemProfileConstants.SHOP_NAME,
                session.getAttribute(SystemProfileConstants.SHOP_NAME));*/
        List<SpmSalesOrganization> list = new ArrayList<SpmSalesOrganization>();
        list.add(org);
        return new ResponseData(list);
    }

}
