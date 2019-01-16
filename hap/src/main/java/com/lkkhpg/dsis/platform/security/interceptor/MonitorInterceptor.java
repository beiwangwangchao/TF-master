/*
 *
 */
package com.lkkhpg.dsis.platform.security.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.interceptor.SecurityTokenInterceptor;
import com.lkkhpg.dsis.platform.security.TokenUtils;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年1月21日
 */
public class MonitorInterceptor extends HandlerInterceptorAdapter {

    private static ThreadLocal<Long> holder = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Boolean sessionInterceptorToken = (Boolean) request.getAttribute(SessionInterceptor.SESSION_INTERCEPTOR_TOKEN);
        if (sessionInterceptorToken == null) {
            return true;
        }
        fillMDC(request);
        holder.set(System.currentTimeMillis());
        HttpSession session = request.getSession(false);
        SecurityTokenInterceptor.LOCAL_SECURITY_KEY.set(TokenUtils.getSecurityKey(session));
        return true;
    }

    private void fillMDC(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long accountId = (Long) session.getAttribute(Account.FIELD_ACCOUNT_ID);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            if (accountId != null) {
                MDC.put("accountId", accountId.toString());
            }
            MDC.put("requestId", uuid);
            MDC.put("sessionId", session.getId());
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        Boolean sessionInterceptorToken = (Boolean) request.getAttribute(SessionInterceptor.SESSION_INTERCEPTOR_TOKEN);
        if (sessionInterceptorToken == null) {
            return;
        }
        long end = System.currentTimeMillis();
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Logger logger = LoggerFactory.getLogger(method.getBeanType());
            if (logger.isTraceEnabled()) {
                logger.trace(method.toString() + " - " + (end - holder.get()) + " ms");
            }
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }
}
