/*
 *
 */
package com.lkkhpg.dsis.platform.report.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.platform.report.service.IReportHandler;

/**
 * 输出HTML.
 * @author chenjingxiong
 */
public class HtmlReportHandler implements IReportHandler {

    private static Logger logger = LoggerFactory.getLogger(HtmlReportHandler.class);

    @Override
    public void process(InputStream in, OutputStream out) throws IOException {
        try {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            out.flush();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Override
    public Map<String, String> getReportConfig() {
        return null;
    }

    @Override
    public String getExtension() {
        return "html";
    }

    @Override
    public String getContentType() {
        return "text/html;charset=UTF-8";
    }

}
