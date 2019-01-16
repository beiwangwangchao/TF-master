/*
 *
 */
package com.lkkhpg.dsis.mws.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.mws.service.IIndexImageService;
import com.lkkhpg.dsis.mws.service.IShopCartService;
import com.lkkhpg.dsis.platform.web.DefaultFreeMarkerView;

import freemarker.template.SimpleHash;

/**
 * MWSfreemark.
 * 
 * @author gulin
 * @author xiawang.liu
 *
 */
public class MWSFreemarkerView extends DefaultFreeMarkerView {

    private IShopCartService shopCartService;
    private IIndexImageService indexImageService;
    private IManualMessageService manualMessageService;

    protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
        shopCartService = getApplicationContext().getBean(IShopCartService.class);
        indexImageService = getApplicationContext().getBean(IIndexImageService.class);
        manualMessageService = getApplicationContext().getBean(IManualMessageService.class);
        return super.autodetectConfiguration();
    }

    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        fmModel.put("shopCartService", shopCartService);
        fmModel.put("indexImageService", indexImageService);
        fmModel.put("manualMessageService", manualMessageService);
        return fmModel;
    }
}
