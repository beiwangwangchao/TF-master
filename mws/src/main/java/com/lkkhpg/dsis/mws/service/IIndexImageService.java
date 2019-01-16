/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.mws.dto.IndexImage;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 获取MWS页面页眉图片、首页图片接口.
 * @author xiawang.liu
 *
 */
public interface IIndexImageService extends ProxySelf<IIndexImageService> {
    
    /**
     * 获得首页轮播图片信息.
     * @param requestContext 
     *            统一上下文
     * @param indexImage 
     *            图片信息
     * @return 图片信息
     */
    List<IndexImage> getIndexSliderImages(IRequest requestContext, IndexImage indexImage);
    
    /**
     * 获得首页主体图片信息.
     * @param requestContext 
     *            统一上下文
     * @param indexImage 
     *            图片信息
     * @return 图片信息
     */
    List<IndexImage> getIndexContentImages(IRequest requestContext, IndexImage indexImage);
    
    /**
     * 获得页眉图片ID.
     * 
     * @param moduleCode 
     *            页面code
     * @return 图片信息
     */
    Long getHeadersImageId(String moduleCode);
    
    /**
     * 获得页眉图片链接URL.
     * 
     * @param moduleCode 
     *            页面code
     * @return 图片信息
     */
    String getHeadersImageUrl(String moduleCode);
}
