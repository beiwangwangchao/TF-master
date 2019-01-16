/*
 *
 */
package com.lkkhpg.dsis.platform.security.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lkkhpg.dsis.platform.cache.Cache;
import com.lkkhpg.dsis.platform.cache.CacheManager;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.system.Resource;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.security.DefaultConfiguration;
import com.lkkhpg.dsis.platform.util.RequestUtil;

/**
 * 基于spring mvc的url权限控制拦截器.
 * 
 * @author wuyichu
 * 
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private boolean filter = false;

    private final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Autowired
    private DefaultConfiguration defaultPageManager;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 在业务处理器处理请求之前被调用.
     * <p>
     * 如果返回false从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true,执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller ,然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     * 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Boolean sessionInterceptorToken = (Boolean) request.getAttribute(SessionInterceptor.SESSION_INTERCEPTOR_TOKEN);
        if (sessionInterceptorToken == null) {
            return true;
        }

        HttpSession session = request.getSession(false);
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        if ("".equals(url) || "/".equals(url)) {
            url = session == null ? "login" : "index";
        }
        if (log.isDebugEnabled()) {
            log.debug("requestUri:{}", requestUri);
            log.debug("contextPath:{}", contextPath);
            log.debug("url:{}", url);
        }
        boolean passFlag = false;
        if (!filter) {
            passFlag = true;
        }
        if (!passFlag) {
            Cache<Resource> resourceCache = cacheManager.getCache(BaseConstants.CACHE_RESOURCE_URL);
            Resource resource = resourceCache.getValue(url);
            if (resource == null) {
                if (log.isDebugEnabled()) {
                    log.debug("resource " + url + " not found!");
                }
                passFlag = false;
            } else {
                // 如果不需要登录，直接返回true
                if (!BaseConstants.YES.equalsIgnoreCase(resource.getLoginRequire())) {
                    return true;
                }
                if (session != null) {
                    Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
                    Cache<Set<Long>> roleResourceCache = cacheManager.getCache(BaseConstants.ROLE_RESOURCE_CACHE);
                    Set<Long> resources = roleResourceCache.getValue(String.valueOf(roleId));
                    if (!BaseConstants.YES.equalsIgnoreCase(resource.getAccessChecked()) || (resources != null
                            && !resources.isEmpty() && resources.contains(resource.getResourceId()))) {
                        passFlag = true;
                    }
                }
            }
        }
        if (!passFlag) {
            if (RequestUtil.isAjaxRequest(request)) {
                // TODO:
                return true;
            } else {
                response.sendRedirect(contextPath + defaultPageManager.getPage403());
            }
            return false;
        }
        return passFlag;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等.
     * 
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

}
