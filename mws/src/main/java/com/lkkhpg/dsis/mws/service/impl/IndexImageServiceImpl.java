/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lkkhpg.dsis.mws.constant.ProductConstants;
import com.lkkhpg.dsis.mws.dto.IndexImage;
import com.lkkhpg.dsis.mws.mapper.IndexImageMapper;
import com.lkkhpg.dsis.mws.service.IIndexImageService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取MWS页面页眉图片、首页图片接口实现.
 * @author xiawang.liu
 *
 */
@Service
@Transactional
public class IndexImageServiceImpl implements IIndexImageService {

    @Autowired
    private IndexImageMapper indexImageMapper;

    @Override
    public List<IndexImage> getIndexSliderImages(IRequest requestContext, IndexImage indexImage) {
        return indexImageMapper.getIndexSliderImages(indexImage);
    }

    @Override
    public List<IndexImage> getIndexContentImages(IRequest requestContext, IndexImage indexImage) {
        return indexImageMapper.getIndexContentImages(indexImage);
    }

    @Override
	public Long getHeadersImageId(String moduleCode) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpSession session = request.getSession(false);
        Long marketId = (Long) session.getAttribute(ProductConstants.MARKET_ID);
        IndexImage indexImage = new IndexImage();
        indexImage.setMarketId(marketId);
        indexImage.setModuleCode(moduleCode);
        Long fileId = indexImageMapper.getHeadersImageId(indexImage);
        if (fileId == null) {
        	fileId = ProductConstants.DEFAULT_FILEID;
        }
        return fileId;
	}

    @Override
	public String getHeadersImageUrl(String moduleCode) {
    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                 .getRequest();
         HttpSession session = request.getSession(false);
         Long marketId = (Long) session.getAttribute(ProductConstants.MARKET_ID);
         IndexImage indexImage = new IndexImage();
         indexImage.setMarketId(marketId);
         indexImage.setModuleCode(moduleCode);
         String url = indexImageMapper.getHeadersImageUrl(indexImage);
         if (url == null) {
        	 url = ProductConstants.DEFAULT_URL;
         }
        return url;
	}

}