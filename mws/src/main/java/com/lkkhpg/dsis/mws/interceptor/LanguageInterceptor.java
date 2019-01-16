/*
 *
 */
package com.lkkhpg.dsis.mws.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.dto.system.Language;
import com.lkkhpg.dsis.platform.security.interceptor.SessionInterceptor;

/**
 * 语言切换拦截器-添加languages.
 * 
 * @author guanghui.liu
 * 
 */
public class LanguageInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ILanguageProvider languageProvider;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Boolean sessionInterceptorToken = (Boolean) request.getAttribute(SessionInterceptor.SESSION_INTERCEPTOR_TOKEN);
        if (sessionInterceptorToken == null) {
            return true;
        }
        List<Language> languages = languageProvider.getSupportedLanguages();
        request.setAttribute("languages", languages);
        return true;
    }

}
