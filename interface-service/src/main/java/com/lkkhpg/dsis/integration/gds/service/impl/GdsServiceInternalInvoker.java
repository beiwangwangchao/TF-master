/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.integration.ComparableNameValuePair;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.recorder.service.IInterfaceRecorderService;

/**
 * @author shengyang.zhou@hand-china.com
 */
class GdsServiceInternalInvoker {

    @Autowired
    private ObjectMapper objectMapper;

    private String appKey;
    private String secret;

    private Logger logger = LoggerFactory.getLogger(GdsServiceInternalInvoker.class);

    private int connectTimeout = 30 * 1000;
    private int readTimeout = 60 * 1000;

    @Autowired
    private IInterfaceRecorderService serviceRecorder;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * invoke an auto generated client with given parameter.
     * <p>
     * the header parameter will be auto create(hard coded).
     * 
     * @param delegate
     *            generated client
     * @param method
     *            METHOD.get,post,put,delete...
     * @param qp
     *            QueryParam. if the value is String,it will be constructed to
     *            real QueryParam
     * @param pb
     *            Body. if the value is String,it will be constructed to real
     *            Body
     * @param <T>
     *            return type(auto convert)
     * @return response(deserialize)
     */
    @SuppressWarnings("unchecked")
    protected <T> T invoke(Object delegate, String method, Object qp, Object pb) {
        String url = extractUrl(delegate);
        Method mtd = extractMethod(delegate, method);
        Client client = extractClient(delegate);
        GdsRequestReaderInterceptor gdsReaderInterceptor = new GdsRequestReaderInterceptor();
        String methodName = getMethodName();
        if (StringUtils.isNotBlank(methodName) && logger.isDebugEnabled()) {
            // prepare request
            String fakeRequestData = serviceRecorder.getFakeRequestData(methodName);
            if (StringUtils.isNotBlank(fakeRequestData)) {
                if (pb instanceof List) {
                    Class<?> eleClass = HashMap.class;
                    if (((List) pb).size() > 0) {
                        eleClass = ((List) pb).get(0).getClass();
                    } else {
                        logger.error("list is empty, can not get element type.");
                    }
                    JavaType jType = objectMapper.getTypeFactory().constructParametrizedType(List.class, List.class,
                            eleClass);
                    try {
                        pb = objectMapper.readValue(fakeRequestData, jType);
                        if (logger.isDebugEnabled()) {
                            logger.debug("request data replace:" + fakeRequestData);
                        }
                    } catch (IOException e) {
                        logger.error("request data replace failed", e);
                    }

                } else if (pb != null && !(pb instanceof String)) {
                    try {
                        pb = objectMapper.readValue(fakeRequestData, pb.getClass());
                        if (logger.isDebugEnabled()) {
                            logger.debug("request data replace:" + fakeRequestData);
                        }
                    } catch (IOException e) {
                        logger.error("request data replace failed", e);
                    }
                } else {
                    logger.debug("request replace skipped. pb=" + pb);
                }
            }

            // prepare response
            String fakeData = serviceRecorder.getFakeData(methodName);
            gdsReaderInterceptor.setFakeData(fakeData);
        }
        client.register(gdsReaderInterceptor);
        // client.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
        // client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        if (logger.isDebugEnabled()) {
            logger.debug("request url:" + url);
            logger.debug("method:" + method.toUpperCase());
        }
        Class<?>[] types = mtd.getParameterTypes();
        Date beginDate = new Date();
        String response = null;
        Throwable throwable = null;
        try {
            List<Object> params = new ArrayList<>();
            for (Class<?> c : types) {
                if (c.getName().endsWith("Param")) {
                    qp = castParam(c, qp);
                    params.add(qp);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Param:" + toAttr(qp));
                    }
                } else if (c.getName().endsWith("Body") || c.isAssignableFrom(List.class)) {
                    pb = castParam(c, pb);
                    params.add(pb);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Body:" + toJson(pb));
                    }
                } else if (c.getName().endsWith("Header")) {
                    Object header = createHeaderObject(c, url, qp, pb, beginDate.getTime());
                    params.add(header);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Header:" + toAttr(header));
                    }
                } else {
                    throw new RuntimeException("Unrecognized parameter type:" + c);
                }
            }
            Object[] pArr = params.toArray();
            Object ret = mtd.invoke(delegate, pArr);
            response = gdsReaderInterceptor.getResponse();
            return (T) ret;
        } catch (Exception e) {
            throwable = e;
            while (throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
            if (throwable instanceof WebApplicationException) {
                WebApplicationException wae = (WebApplicationException) throwable;
                Response res = wae.getResponse();
                if (res != null) {
                    response = res.readEntity(String.class);
                    if (logger.isDebugEnabled()) {
                        logger.debug("status code:" + res.getStatus());
                    }
                    String errorMessage = tryExtractErrorMessage(response);
                    throw new ESBException(res.getStatus(), errorMessage, response, throwable);
                }
                throw wae;
            }
            throw new RuntimeException(throwable);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("response:" + response);
            }
            long elapse = System.currentTimeMillis() - beginDate.getTime();
            try {
                if (throwable != null) {
                    serviceRecorder.recordFail(url, paramsToUrl(qp), toJson(pb), response, throwable, beginDate,
                            elapse);
                } else {
                    serviceRecorder.recordSuccess(url, paramsToUrl(qp), toJson(pb), response, beginDate, elapse);
                }
            } catch (Throwable e) {
                logger.error("error occurred while record request.", e);
            }
        }
    }

    protected String getMethodName() {
        try {
            throw new Exception();
        } catch (Exception e) {
            for (StackTraceElement ste : e.getStackTrace()) {
                if (GdsServiceImpl.class.getName().equals(ste.getClassName())) {
                    return ste.getMethodName();
                }
            }
        }
        return "";
    }

    protected String extractUrl(Object delegate) {
        try {
            Field fld = delegate.getClass().getDeclaredField("_baseUrl");
            fld.setAccessible(true);
            return (String) fld.get(delegate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Method extractMethod(Object delegate, String methodName) {
        for (Method m : delegate.getClass().getDeclaredMethods()) {
            if (m.getName().equalsIgnoreCase(methodName)) {
                return m;
            }
        }
        throw new RuntimeException("Can not do '" + methodName + "' within " + delegate);
    }

    protected Client extractClient(Object delegate) {
        try {
            Field field = delegate.getClass().getDeclaredField("client");
            field.setAccessible(true);
            return (Client) field.get(delegate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected Object createHeaderObject(Class<?> type, String url, Object qp, Object pb, long ts) {
        try {
            String timestamp = "" + ts;
            Object[] params = new Object[] { appKey, timestamp, getSign(url, qp, pb, timestamp, secret) };
            return type.getConstructor(String.class, String.class, String.class).newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * extract error message from {"message":"xxx"}.
     * 
     * @param errorResponse
     *            json
     * @return message
     */
    private String tryExtractErrorMessage(String errorResponse) {
        try {
            HashMap map = objectMapper.readValue(errorResponse, HashMap.class);
            return (String) map.get("message");
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    /**
     * 使用参数最少的构造方法,加上全null的参数值,来构造一个对象.
     * <p>
     * 如果参数中有基本类型的参数,则会报错.
     * 
     * @param clazz
     *            对象class
     * @return 对象实例
     * @throws Exception
     *             Class.newInstance
     */
    protected Object defaultConstruct(Class<?> clazz) throws Exception {
        Constructor<?>[] cons = clazz.getConstructors();
        if (cons.length == 0) {
            return clazz.newInstance();
        }
        Arrays.sort(cons, (l, r) -> l.getParameterCount() - r.getParameterCount());

        Constructor<?> constructor = cons[0];
        Object[] args = new Object[constructor.getParameterCount()];
        return constructor.newInstance(args);
    }

    protected Object castParam(Class<?> targetType, Object realPara) throws Exception {
        if (realPara instanceof String[]) {
            // auto create Object with String array
            Class[] ss = new Class[((String[]) realPara).length];
            Arrays.fill(ss, String.class);
            realPara = targetType.getConstructor(ss).newInstance((String[]) realPara);
        } else if (realPara instanceof String) {
            // auto create Object with single String
            realPara = targetType.getConstructor(String.class).newInstance(realPara);
        } else if (realPara == null) {
            // auto create Object using minimum constructor ,with null values
            realPara = defaultConstruct(targetType);
        }
        return realPara;
    }

    /**
     * 
     * 计算签名.
     */
    private String getSign(String url, Object queryParameter, Object postParameter, String timestamp, String secret) {
        StringBuilder content = new StringBuilder();
        content.append(secret);
        try {
            url = new URL(url).getPath();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        content.append(url);
        List<ComparableNameValuePair> queryParaList = toParaList(queryParameter);
        Collections.sort(queryParaList);
        for (ComparableNameValuePair item : queryParaList) {
            content.append(item.getName()).append(item.getValue());
        }

        String body = null;
        try {
            if (postParameter != null) {
                body = objectMapper.writeValueAsString(postParameter);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (body != null) {
            content.append(body);
        }
        content.append(timestamp);
        content.append(secret);
        return DigestUtils.md5Hex(content.toString()).toUpperCase();
    }

    private List<ComparableNameValuePair> toParaList(Object object) {
        if (object == null) {
            return Collections.emptyList();
        }
        try {
            Map<String, String> params = BeanUtils.describe(object);
            params.remove("class");
            return ComparableNameValuePair.toList(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String paramsToUrl(Object object) {
        if (object == null) {
            return null;
        }
        try {
            Map<String, String> params = BeanUtils.describe(object);
            params.remove("class");
            StringBuilder sb = new StringBuilder();
            params.forEach((k, v) -> {
                try {
                    sb.append(k).append("=");
                    if (v != null) {
                        sb.append(URLEncoder.encode(v, "UTF-8"));
                    }
                    sb.append("&");
                } catch (UnsupportedEncodingException e) {
                    // ignore, never happen
                }
            });
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    private String toAttr(Object object) {
        if (object == null) {
            return null;
        }
        try {
            Map<String, String> params = BeanUtils.describe(object);
            params.remove("class");
            StringBuilder sb = new StringBuilder();
            params.forEach((k, v) -> sb.append(k).append("=").append(v).append(", "));
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 2);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
