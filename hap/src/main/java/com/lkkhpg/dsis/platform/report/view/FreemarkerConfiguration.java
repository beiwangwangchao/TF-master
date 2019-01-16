/*
 *
 */
package com.lkkhpg.dsis.platform.report.view;


import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lkkhpg.dsis.platform.util.PathResolver;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * 报表FreemarkerConfiguration. 
 * @author chenjingxiong
 */
public class FreemarkerConfiguration {
    
    private static Logger logger = LoggerFactory.getLogger(FreemarkerConfiguration.class);

    private static final String TEMPLATE_DIR_SQL_ID =
            "com.lkkhpg.dsis.platform.mapper.report.ReportMapper.getReportUploadPath";
  
    private static final String FILE_PREFIX = "file:";
    
    private static final String DEFAULT_REPORT_TEMPLATE_DIR = "/report/template";

    private SqlSessionFactory sqlSessionFactory;

    private FreeMarkerConfigurer freeMarkerConfigurer; 
    
    private Configuration configuration;

    private String templateDir;

    public synchronized Configuration getConfiguration() {
        if (configuration == null) {
            freeMarkerConfigurer.setTemplateLoaderPath(FILE_PREFIX + getTemplateDir());
            try {
                configuration = freeMarkerConfigurer.createConfiguration();
            } catch (IOException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("freeMarkerConfigurer init faild:", e);
                }
            } catch (TemplateException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("freeMarkerConfigurer init faild:", e);
                }
            }
        }
        return configuration;
    }

    public String getTemplateDir() {
        if (templateDir != null) {
            return templateDir;
        }

        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            templateDir = sqlSession.selectOne(TEMPLATE_DIR_SQL_ID);
            
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("get template dir error:", e);
            }
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug("Template Dir is :{}", templateDir);
        }
        
        if (templateDir == null) {
            return DEFAULT_REPORT_TEMPLATE_DIR;
        }
        
        templateDir = PathResolver.getRelativePath(templateDir);
        return templateDir;
    }
    
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public FreeMarkerConfigurer getFreeMarkerConfigurer() {
        return freeMarkerConfigurer;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }
    
    
}
