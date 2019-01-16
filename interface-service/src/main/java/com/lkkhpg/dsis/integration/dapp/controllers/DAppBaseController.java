/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.controllers;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.integration.dapp.IDAConstants;
import com.lkkhpg.dsis.integration.dapp.adaptor.FeedJsonRequestBodyAdvice;
import com.lkkhpg.dsis.integration.dapp.dto.DAppResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.integration.dapp.service.DAppSecurityManager;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;
import com.lkkhpg.dsis.integration.recorder.service.IInterfaceRecorderService;
import com.lkkhpg.dsis.integration.utils.IpUtils;

/**
 * @author shengyang.zhou@hand-china.com
 */
@RestController
@RequestMapping("/restful/dapp/api/v1")
public class DAppBaseController {

    public static final String DAPP_FLAG = "/dapp";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IInterfaceRecorderService recorderService;

    @Autowired
    private Validator validator;

    @Autowired(required = false)
    private DAppSecurityManager securityManager;

    @Autowired
    private ObjectMapper objectMapper;

    public Object preLog(HttpServletRequest request) {
        return new Object();
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder, HttpServletRequest request) throws Exception {
        ListenerRecord record = createRecordRequest(request);
        recorderService.listenerRecord(record);

        if (logger.isDebugEnabled()) {
            logger.debug("dapp request from: [ip:{}, host:{}, port:{}, origin:{}, UserAgent:{}]", record.getIp(),
                    record.getHost(), record.getPort(), record.getOrigin(), record.getUserAgent());
            logger.debug("uri:" + record.getMethodName());
        }

        if (dataBinder.getTarget() == null) {
            return;
        }

        String apiUri = getApiUri(request);
        if (apiUri.startsWith("/api") && securityManager.isSecurityEnabled()) {
            securityManager.checkAccess(request);
        } else {
            FeedJsonRequestBodyAdvice.getAndRemoveBody();
        }
        if (!HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            validate(dataBinder.getTarget(), dataBinder.getObjectName());
        }
    }

    private String getApiUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.substring(uri.indexOf(DAPP_FLAG) + DAPP_FLAG.length());
    }

    /**
     * validate bean.
     * 
     * @param target
     *            bean to be validate
     * @throws DAppException
     *             if validation failed
     */
    protected void validate(Object target) throws DAppException {
        validate(target, "");
    }

    protected Logger getLogger() {
        return logger;
    }

    private ListenerRecord createRecordRequest(HttpServletRequest request) {
        ListenerRecord record = new ListenerRecord();
        record.setHost(request.getRemoteHost());
        record.setIp(IpUtils.getIpAddr(request));
        record.setPort(request.getRemotePort());
        record.setServiceName("dapp");
        record.setMethodName(getApiUri(request));
        record.setOrigin(request.getHeader("Origin"));
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setRequestDate(new Date());
        String qs = request.getQueryString();
        if (qs != null && qs.length() > 1000) {
            qs = qs.substring(0, 1000);
        }
        record.setQueryString(qs);
        record.setBody(FeedJsonRequestBodyAdvice.getBody());

        return record;
    }

    private void validate(Object object, String name) throws DAppException {
        if (object == null) {
            return;
        }
        if (object instanceof String || object instanceof Number || object instanceof Map) {
            return;
        }

        BindingResult br = new BeanPropertyBindingResult(object, name);
        validator.validate(object, br);
        FieldError fe = br.getFieldError();
        if (fe != null) {
            throw new DAppException(IDAConstants.MSG_10002, IDAConstants.ERROR_10002, fe.getField());
        }
    }

    @ExceptionHandler(value = { Exception.class })
    public @ResponseBody Object exceptionHandler(Exception exception) {
        Throwable thr = getRootCause(exception);
        if (logger.isErrorEnabled()) {
            logger.error("error while dapp call,related SYS_INTERFACE_LISTENER id:" + MDC.get("listenerRequestId"),
                    thr);
        }
        DAppResponse<Map> response = new DAppResponse<>(null);
        DAppResponse.Status status = response.getStatus();
        if (thr instanceof DAppException) {
            DAppException dae = (DAppException) thr;
            status.setCode(dae.getCode());
            status.setValue(dae.getValue());
            status.setMessage(dae.getMessage());
        } else {
            status.setCode(IDAConstants.ERROR_10000);
            status.setMessage(exception.toString() + ":" + thr.getMessage());
        }

        return response;
    }

    protected Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    /**
     * Object转换为Json
     * 
     * @param object
     * @return json
     */
    private String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
