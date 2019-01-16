/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.mws.mapper;

import java.util.List;

import com.lkkhpg.dsis.mws.dto.IndexImage;

/**
 * @author xiawang.liu
 */
public interface IndexImageMapper {

    /**
     * 获得首页轮播图片信息.
     * 
     * @param indexImage 
     *            
     * @return 图片信息
     */
    List<IndexImage> getIndexSliderImages(IndexImage indexImage);
    
    /**
     * 获得首页主体图片信息.
     * 
     * @param indexImage 
     * 
     * @return 图片信息
     */
    List<IndexImage> getIndexContentImages(IndexImage indexImage);
    
    /**
     * 获得页眉图片ID.
     * 
     * @param indexImage
     *            
     * @return fileId
     */
    Long getHeadersImageId(IndexImage indexImage);
    
    /**
     * 获得页眉图片链接URL.
     * 
     * @param indexImage
     *            
     * @return URL
     */
    String getHeadersImageUrl(IndexImage indexImage);

}