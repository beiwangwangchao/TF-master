/*
 *
 */
package com.lkkhpg.dsis.platform.report.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 报表Controller基类.
 * @author chenjingxiong
 */
public class ReportBaseController extends BaseController {

    private static final String SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE = "springMacroRequestContext";
    
    private static final String BASE_PATH = "basePath";
    
    /**
     * 创建IRquest实例.
     * @param request 请求上下文.
     * @param response Http相应.
     * @return IRequest实例.
     */
    protected IRequest createReportRequest(HttpServletRequest request, HttpServletResponse response) {
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> model = new HashMap<>();
        RequestContext springMacroRequestContext = new RequestContext(request, response, request.getServletContext(),
                model);
        requestContext.setAttribute(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE, springMacroRequestContext);
        String basePath = request.getSession().getServletContext().getRealPath("/");
        requestContext.setAttribute(BASE_PATH, basePath);
        
        return requestContext;
    }

    @Override
    @ExceptionHandler(value = { Exception.class })
    public Object exceptionHandler(Exception exception, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        exception.getCause().printStackTrace(pw);
        request.setAttribute("exception",sw.toString());
        modelAndView.setViewName("500");
        return modelAndView;
    }
}
