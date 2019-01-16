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
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lkkhpg.dsis.platform.report.pdf.ITextRendererObjectFactory;
import com.lkkhpg.dsis.platform.report.service.IReportHandler;

/**
 * 导出PDF.
 * @author chenjingxiong
 */
public class PdfReportHandler implements IReportHandler {

    private static Logger logger = LoggerFactory.getLogger(PdfReportHandler.class);

    @Override
    public void process(InputStream in, OutputStream out) throws IOException {
        ITextRenderer iTextRenderer = null;
        try {
            Tidy tidy = new Tidy();
            tidy.setInputEncoding("UTF-8");
            tidy.setQuiet(true);
            tidy.setOutputEncoding("UTF-8");
            tidy.setShowWarnings(false);
            tidy.setIndentContent(true);
            tidy.setSmartIndent(true);
            tidy.setIndentAttributes(false);
            //输出为xhtml
            tidy.setXHTML(true);
            Document doc = tidy.parseDOM(in, null);
            //获取对象池中对象
            iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();

            try {
                iTextRenderer.setDocument(doc, null);
                iTextRenderer.layout();
                iTextRenderer.createPDF(out);
            } catch (Exception e) {
                ITextRendererObjectFactory.getObjectPool().invalidateObject(iTextRenderer);
                iTextRenderer = null;
                throw e;
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("error!", e);
            }
        } finally {
            if (iTextRenderer != null) {
                try {
                    ITextRendererObjectFactory.getObjectPool().returnObject(iTextRenderer);
                } catch (Exception ex) {
                	if (logger.isErrorEnabled()) {
                		logger.error("Cannot return object from pool.", ex);
                	}
                }
            }
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
        return "pdf";
    }

    @Override
    public String getContentType() {
        return "application/pdf;charset=utf-8";
    }


}
