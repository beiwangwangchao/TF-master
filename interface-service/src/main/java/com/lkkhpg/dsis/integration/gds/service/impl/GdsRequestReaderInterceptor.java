/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Provider
public class GdsRequestReaderInterceptor implements ReaderInterceptor {

    private String response;

    private String fakeData;
    private Logger logger = LoggerFactory.getLogger(GdsRequestReaderInterceptor.class);

    public void setFakeData(String data) {
        this.fakeData = data;
        if (fakeData != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("prepare fake response data for request(for test only)");
            }
        }
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        InputStream is = context.getInputStream();
        if (fakeData != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("use fake response data(for test only)");
            }
            response = fakeData;
        } else {
            response = IOUtils.toString(is, "UTF-8");
        }
        context.setInputStream(new ByteArrayInputStream(response.getBytes("UTF-8")));
        return context.proceed();
    }

    public String getResponse() {
        return response;
    }
}
