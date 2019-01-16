package com.lkkhpg.dsis.integration.payment.controllers;




        import java.io.UnsupportedEncodingException;
        import java.net.URLDecoder;
        import java.net.URLEncoder;
        import java.util.*;
        import java.util.Map.Entry;

        import javax.servlet.http.HttpServletRequest;

        import com.lkkhpg.dsis.integration.payment.service.IUnionQueryPaymentStatusService;
        import com.lkkhpg.dsis.integration.payment.service.impl.unionQueryPaymentStatusServiceImpl;
        import org.apache.commons.codec.digest.DigestUtils;

/**
 * 类MD5Sign.java的实现描述：MD5签名和验签
 *
 */
public class MD5Sign {

    /**
     * 方法描述:将字符串MD5加码 生成32位md5码
     *
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        String value=inStr.trim();
        try {
            return DigestUtils.md5Hex(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }

    /**
     * 方法描述:签名字符串
     *
     *
     * @param params 需要签名的参数
     * @param appSecret 签名密钥
     * @return
     */
    public static String sign(HashMap<String, String> params, String appSecret) {
        StringBuilder valueSb = new StringBuilder();
       // params.put("appSecret", appSecret);
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortParams.entrySet();
        // 遍历排序的字典,并拼接value1+value2......格式
        int count=0;
        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
            count++;
            if(count!=entrys.size())valueSb.append("&");
        }
        valueSb.append(appSecret);
       // params.remove("appSecret");
        return md5(valueSb.toString());
    }

    /**
     * 方法描述:验证签名
     *
     * @param appSecret 加密秘钥
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean verify(String appSecret, HttpServletRequest request) throws Exception {

        String sign = request.getParameter("sign");
        if (sign == null) {
            throw new Exception(URLEncoder.encode("请求中没有带签名", "UTF-8"));
        }
        if (request.getParameter("time_end") == null) {
            throw new Exception(URLEncoder.encode("请求中没有带时间戳", "UTF-8"));
        }

        HashMap<String, String> params = new HashMap<String, String>();

        // 获取url参数
        @SuppressWarnings("unchecked")
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign") && !paramName.equals("sign_type")) {
                // 拼接参数值字符串并进行utf-8解码，防止中文乱码产生
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortParams.entrySet();

        int count=0;
        StringBuilder valueSb = new StringBuilder();
        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getKey()+"="+entry.getValue());
            count++;
            if(count!=entrys.size())valueSb.append("&");
        }
        valueSb.append(appSecret);
        String mySign = md5(valueSb.toString());
        if (mySign.equals(sign)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 方法描述:验证签名
     *
     * @param appSecret 加密秘钥
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean returnVerify(String appSecret, HttpServletRequest request) throws Exception {

        String sign = request.getParameter("sign");
        if (sign == null) {
            throw new Exception(URLEncoder.encode("请求中没有带签名", "UTF-8"));
        }
        HashMap<String, String> params = new HashMap<String, String>();

        @SuppressWarnings("unchecked")
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign") && !paramName.equals("sign_type")) {
                // 拼接参数值字符串并进行utf-8解码，防止中文乱码产生
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortParams.entrySet();

        // 遍历排序的字典,并拼接value1+value2......格式
        int count=0;
        StringBuilder valueSb = new StringBuilder();

        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getKey()+"="+entry.getValue());
            count++;
            if(count!=entrys.size())valueSb.append("&");
        }

        valueSb.append(appSecret);
        String mySign = md5(valueSb.toString());
        if (mySign.equals(sign)) {
            return true;
        } else {
            return false;
        }

    }

}

