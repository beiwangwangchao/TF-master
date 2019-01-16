/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.mws.dto.IndexImage;
import com.lkkhpg.dsis.mws.service.IIndexImageService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取MWS页面页眉图片、首页图片controller.
 * 
 * @author xiawang.liu
 *
 */
@Controller
public class IndexImageController extends BaseController {

    @Autowired
    private IIndexImageService iIndexImageService;
    
    /**
     * 获取MWS首页图片.
     * 
     * @param request
     *            请求上下文
     * @return 视图
     */
    @RequestMapping(value = "/index.html")
    @ResponseBody
    public ModelAndView getIndexImages(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("/index");
        IRequest requestContext = createRequestContext(request);
        IndexImage indexImage = new IndexImage();
        //String marketId = requestContext.getAttribute(SystemProfileConstants.MARKET_ID); 
        indexImage.setMarketId(requestContext.getAttribute(SystemProfileConstants.MARKET_ID));
        List<IndexImage> sliderImages = iIndexImageService.getIndexSliderImages(requestContext, indexImage);
        List<IndexImage> contentImages = iIndexImageService.getIndexContentImages(requestContext, indexImage);
        view.addObject("sliderImages", sliderImages);
        view.addObject("contentImages", contentImages);
        return view;
    }
    
}
