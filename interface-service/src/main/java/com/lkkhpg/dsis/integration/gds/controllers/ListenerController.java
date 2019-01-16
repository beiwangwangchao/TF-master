/*
 *
 */

package com.lkkhpg.dsis.integration.gds.controllers;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.integration.gds.service.impl.GdsSwitch;
import com.lkkhpg.dsis.integration.recorder.dto.ListenerRecord;
import com.lkkhpg.dsis.integration.recorder.service.IInterfaceRecorderService;
import com.lkkhpg.dsis.integration.utils.IpUtils;

/**
 * GDS监听器.
 * @author shengyang.zhou@hand-china.com
 */
@Controller
public class ListenerController implements BeanFactoryAware {
    // @Autowired
    // private IGdsService gdsService;
    private Logger logger = LoggerFactory.getLogger(ListenerController.class);
    private BeanFactory beanFactory;

    @Autowired
    private IInterfaceRecorderService recorderService;

    @Autowired
    private GdsSwitch gdsSwitch;

    /**
     * 接口 listener.
     * 
     * @param request
     *            HttpServletRequest
     * @param extData
     *            post额外附加数据
     * @param serviceName
     *            接口服务名
     * @param methodName
     *            接口方法名
     * @param invokeType
     *            调用类型,SYNC
     * @param adviseNo
     *            批次号
     * @param orgCode
     *            市场代码
     * @param ftpFileName
     *            ftp 文件名
     * @return 调用结果
     */
    @RequestMapping(value = "/restful/gds-listener", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Object listenerUrl(HttpServletRequest request, @RequestBody(required = false) String extData,
            @RequestParam String serviceName, @RequestParam String methodName, @RequestParam String invokeType,
            @RequestParam String adviseNo, @RequestParam String orgCode,
            @RequestParam(required = false) String ftpFileName) {
        Map<String, Object> ret = new HashMap<>();
        if (!gdsSwitch.isSwitchFlag()) {
            ret.put("success", false);
            ret.put("message", "GDS is close");
            return ret;
        }
        String ip = IpUtils.getIpAddr(request);
        String host = request.getRemoteHost();
        int port = request.getRemotePort();
        String queryString = request.getQueryString();
        String origin = request.getHeader("Origin");
        String userAgent = request.getHeader("User-Agent");
        ListenerRecord listenerRecord = new ListenerRecord();
        listenerRecord.setIp(ip);
        listenerRecord.setHost(host);
        listenerRecord.setPort(port);
        listenerRecord.setServiceName(serviceName);
        listenerRecord.setMethodName(methodName);
        listenerRecord.setRequestDate(new Date());
        listenerRecord.setQueryString(queryString);
        listenerRecord.setOrigin(origin);
        listenerRecord.setUserAgent(userAgent);
        try {
            recorderService.listenerRecord(listenerRecord);
        } catch (Exception e) {
            logger.error("error occurred while insert listener record.", e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("request info: [ip:{}, host:{}, port:{}, origin:{}, UserAgent:{}]", ip, host, port, origin,
                    userAgent);
            logger.debug("serviceName:{}, methodName:{}, invokeType:{}, adviseNo:{}, orgCode:{}", serviceName,
                    methodName, invokeType, adviseNo, orgCode);
        }
        if ("SYNC".equalsIgnoreCase(invokeType)) {
            try {
                if ("IGdsService".equalsIgnoreCase(serviceName) || "GdsService".equalsIgnoreCase(serviceName)) {
                    serviceName = "gdsService";
                }
                Object gdsService = beanFactory.getBean(serviceName);

                Method method = gdsService.getClass().getMethod(methodName, String.class, String.class);

                method.invoke(gdsService, adviseNo, orgCode);
                ret.put("success", true);
                if (logger.isDebugEnabled()) {
                    logger.debug("invoke success.");
                }
            } catch (Exception e) {
                Throwable thr = getRootCause(e);
                ret.put("success", false);
                ret.put("message", thr.getMessage());
                if (logger.isErrorEnabled()) {
                    logger.error("invoke failed: " + thr.getMessage(), thr);
                }
            }
        } else {
            ret.put("success", false);
            ret.put("message", "unknown invokeType");
            if (logger.isWarnEnabled()) {
                logger.warn("invokeType:{} not support", invokeType);
            }
        }
        if (Boolean.FALSE.equals(ret.get("success"))) {
            return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
        }
        return ret;
    }

    private Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
