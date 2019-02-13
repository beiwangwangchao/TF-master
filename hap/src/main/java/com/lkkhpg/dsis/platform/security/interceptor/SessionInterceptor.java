/*
 *
 */
package com.lkkhpg.dsis.platform.security.interceptor;

import java.io.Writer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.security.DefaultConfiguration;
import com.lkkhpg.dsis.platform.util.RequestUtil;
import com.lkkhpg.dsis.platform.util.TimeZoneUtil;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年1月21日
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    public static final String SESSION_INTERCEPTOR_TOKEN = "_SESSION_INTERCEPTOR_TOKEN";

    // TODO:待优化
    private static final String DEFAULT_AJAX_RESPONSE = "{\"success\":false,\"code\":\"session_expired\"}";

    @Autowired
    private DefaultConfiguration defaultPageManager;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute(SESSION_INTERCEPTOR_TOKEN, true);
        HttpSession session = request.getSession(false);
        String contextPath = request.getContextPath();
        System.out.println("截取开始");
        System.out.println(contextPath);
        if (session == null&&contextPath.equals("/dsis")) {
            if (RequestUtil.isAjaxRequest(request)) {
                try (Writer writer = response.getWriter()) {
                    writer.write(DEFAULT_AJAX_RESPONSE);
                }
            } else {
                String path = request.getRequestURI().substring(contextPath.length());
                if ("".equals(path) || "/".equals(path)) {
                    request.getRequestDispatcher("/login").forward(request, response);
                } else if("/loginSysOthUsers".equals(path)){
                    request.getRequestDispatcher("/loginSomethingOtherUsers").forward(request, response);
                }else {
/*                    System.out.println(contextPath);
                    if(contextPath.equals("/mws"))
                    response.sendRedirect(contextPath + defaultPageManager.getPageIndex());
                    else*/
                    response.sendRedirect(contextPath + defaultPageManager.getPageLogin());
                }
                // response.sendRedirect(contextPath + "/timeout.html");
            }
            return false;
        } else {
            if(session ==null&&contextPath.equals("/mws"))
            {
                System.out.println("mws");
                 session = request.getSession();
                session.setAttribute("timeZone","GMT+0800");
                session.setAttribute("accountId",(long)999999999);
                session.setAttribute("_marketCode","0000");
                session.setAttribute("memberId",(long)999999999);
                session.setAttribute("_marketId",(long)999999999);
                session.setAttribute("_salesOrgId",(long)999999999);
                session.setAttribute("memberName","未登录");
                session.setAttribute("_salesOrgName","无");


            }
            String tz = (String) session.getAttribute(BaseConstants.TIME_ZONE);
            if (StringUtils.isNotEmpty(tz)) {
                TimeZoneUtil.setTimeZone(TimeZone.getTimeZone(tz));
            }
        }
        return true;
    }

}
