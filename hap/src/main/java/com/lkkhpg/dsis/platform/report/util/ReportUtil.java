/*
 *
 */
package com.lkkhpg.dsis.platform.report.util;

import com.lkkhpg.dsis.platform.report.Report;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

/**
 * 报表工具类.
 * @author chenjingxiong
 */
public final class ReportUtil {
    
    private static Logger logger = LoggerFactory.getLogger(ReportUtil.class);
    
    private ReportUtil() {
    }

    public static void viewReport(HttpServletResponse response, Report report) throws IOException {
        response.setHeader("cache-control", "must-revalidate");
        response.setHeader("pragma", "public");
        response.setContentType(report.getContentType());
        response.setHeader("Content-disposition", "inline;filename=\"" + report.getFileName() + "\"");
        ServletOutputStream out = response.getOutputStream();
        try {
            byte[] data = report.getReportData();
            out.write(data);
            out.flush();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("error when write to response!", e);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void httpGet(String url, List<NameValuePair> params, OutputStream outputStream) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        InputStream inputStream = null;
        try {
            // 设置参数
            String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + paramStr));

            if (logger.isDebugEnabled()) {
                logger.debug("Birt Url is : {}", httpGet.getURI().toString());
            }

            httpClient.execute(httpGet);
            // 发送请求
            HttpResponse httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Request fail!");
            }

            // 获取返回数据
            HttpEntity entity = httpResponse.getEntity();

            inputStream = entity.getContent();
            int c;
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
            outputStream.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpGet.releaseConnection();
        }
    }
    
}
