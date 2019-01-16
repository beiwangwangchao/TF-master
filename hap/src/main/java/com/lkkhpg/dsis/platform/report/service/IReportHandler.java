/*
 *
 */
package com.lkkhpg.dsis.platform.report.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 报表处理器.
 * @author chenjingxiong
 */
public interface IReportHandler {


    /**
     * 将输入流转换成相应格式的输出流.
     * @param in 输入内容.
     * @param out 输出内容.
     */
    void process(InputStream in, OutputStream out) throws IOException;

    Map<String, String> getReportConfig();

    String getExtension();
    
    String getContentType();
    
}
