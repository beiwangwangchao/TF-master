/*
 *
 */
package com.lkkhpg.dsis.platform.report.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.platform.report.view.FreemarkerConfiguration;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker模板生成HTML.
 * @author chenjingxiong
 */
public class HtmlGenerator {

    private static Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);

    private FreemarkerConfiguration freemarkerConfiguration;
    
    private String encoding = "UTF-8";
    
    /**
     * 获取HTML内容.
     * @param template
     * @param contextMap
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String generate(String template, Map<String, Object> contextMap) throws IOException, TemplateException {
        BufferedWriter writer = null;
        String htmlContent = null;
        try {
            Configuration config = freemarkerConfiguration.getConfiguration();
            String path = freemarkerConfiguration.getTemplateDir();
            template = template.replace(path, "");
            if (template.startsWith("/")) {
                template = template.substring(1);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("template is : {}", template);
            }

            Template tp = config.getTemplate(template);
            StringWriter stringWriter = new StringWriter();
            writer = new BufferedWriter(stringWriter);
            tp.setEncoding(encoding);
            tp.process(contextMap, writer);
            htmlContent = stringWriter.toString();
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return htmlContent;
    }
    

    public FreemarkerConfiguration getFreemarkerConfiguration() {
        return freemarkerConfiguration;
    }

    public void setFreemarkerConfiguration(FreemarkerConfiguration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }



    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}