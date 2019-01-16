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
 * 导出Excel.
 * @author chenjingxiong
 */
public class ExcelReportHandler implements IReportHandler {
    
    private static Logger logger = LoggerFactory.getLogger(ExcelReportHandler.class);


    public void process(InputStream in, OutputStream out) throws IOException {
        try {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            out.flush();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Excel Handle Error!", e);
            }
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
        return "xls";
    }

    @Override
    public String getContentType() {
        return "application/vnd.ms-excel;charset=UTF-8";
    }

}
