/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackBody;
import com.lkkhpg.dsis.integration.dapp.dto.DAppCallbackResponse;
import com.lkkhpg.dsis.integration.dapp.service.IDAppCallbackService;
import com.lkkhpg.dsis.integration.recorder.service.IInterfaceRecorderService;
import com.lkkhpg.dsis.platform.message.profile.GlobalProfileListener;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class DAppCallbackServiceImpl implements IDAppCallbackService, GlobalProfileListener {

    @Autowired
    private IInterfaceRecorderService recorderService;

    private String callbackUrl = "http://omapi-dev.lkkhpgdi.com/callbacks";
    private String appKey = "3546406514182378";
    private String appSecret = "om42cu90jp6uqk1a0ojbigqorpk390jn";

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public DAppCallbackResponse callback(DAppCallbackBody body) {
        DAppCallbackResponse response = null;
        String postJson = null;
        String responseJson = null;
        Date beginTime = new Date();
        Exception exception = null;
        boolean success = false;
        StringBuilder queryString = new StringBuilder();
        if (logger.isDebugEnabled()) {
            logger.debug("begin notify OMK, with clientCallbackID:{},jobType:{}", body.getClientCallbackID(),
                    body.getJobType());
        }
        try {
            URL url = new URL(callbackUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.setUseCaches(false);
            long timeInSeconds = System.currentTimeMillis() / 1000;
            huc.setRequestProperty("x-auth-systoken", TOTPUtil.getSysToken(appKey, appSecret, timeInSeconds));
            huc.setRequestProperty("x-auth-appkey", appKey);
            huc.setRequestProperty("timestamp", "" + timeInSeconds);
            huc.setRequestProperty("Content-Type", "application/json");
            huc.getRequestProperties()
                    .forEach((k, v) -> queryString.append(k).append("=").append(v.get(0)).append(", "));

            postJson = objectMapper.writeValueAsString(body);
            try (OutputStream os = huc.getOutputStream()) {
                IOUtils.write(postJson, os, "UTF-8");
            }
            huc.connect();
            int code = huc.getResponseCode();
            if (logger.isDebugEnabled()) {
                logger.debug("response code:" + code);
            }
            if (code < 300) {
                success = true;
                try (InputStream is = huc.getInputStream()) {
                    responseJson = IOUtils.toString(is, "UTF-8");
                }
            } else {
                try (InputStream is = huc.getErrorStream()) {
                    responseJson = IOUtils.toString(is, "UTF-8");
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("response content:" + responseJson);
            }

            response = objectMapper.readValue(responseJson, DAppCallbackResponse.class);
        } catch (Exception e) {
            exception = e;
            success = false;
            if (logger.isErrorEnabled()) {
                logger.error("error occurred while notify OMK", e);
            }
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("notify OMK "
                        + (success ? IInterfaceRecorderService.SUCCESS : IInterfaceRecorderService.FAILED));
            }
            String header = StringUtils.stripEnd(queryString.toString(), ", ");
            long elapse = System.currentTimeMillis() - beginTime.getTime();
            if (success) {
                recorderService.recordSuccess(callbackUrl, header, postJson, responseJson, beginTime, elapse);
            } else {
                recorderService.recordFail(callbackUrl, header, postJson, responseJson, exception, beginTime, elapse);
            }
        }

        return response;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    public List<String> getAcceptedProfiles() {
        return Arrays.asList(BASE_URL, APP_KEY, APP_SECRET);
    }

    private static final String BASE_URL = "ISG.DAPP_BASE_URL";
    private static final String APP_KEY = "ISG.DAPP_APP_KEY";
    private static final String APP_SECRET = "ISG.DAPP_APP_SECRET";

    @Override
    public void updateProfile(String profileName, String profileValue) {
        switch (profileName) {
        case BASE_URL:
            this.callbackUrl = profileValue;
            break;
        case APP_KEY:
            this.appKey = profileValue;
            break;
        case APP_SECRET:
            this.appSecret = profileValue;
        default:
            break;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("change {} to :{}", profileName, profileValue);
        }
    }
}
