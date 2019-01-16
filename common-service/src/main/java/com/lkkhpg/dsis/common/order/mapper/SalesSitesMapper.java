/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesSites;

/**
 * 订单地址mapper.
 * 
 * @author wuyichu
 */
public interface SalesSitesMapper {
    int deleteByPrimaryKey(Long salesSiteId);

    int insert(SalesSites record);

    int insertSelective(SalesSites record);

    SalesSites selectByPrimaryKey(Long salesSiteId);

    int updateByPrimaryKeySelective(SalesSites record);

    int updateByPrimaryKey(SalesSites record);

    /**
     * 根据订单头id以及订单类型查询订单地址.
     * 
     * @param isAutoship
     *            是否autoship N/Y
     * @param headerId
     *            订单头Id
     * @return 符合条件的地址集合
     */
    List<SalesSites> selectSitesByHeaderIdAndAutoshipFlag(@Param("isAutoship") String isAutoship,
            @Param("headerId") Long headerId);

    /**
     * 根据订单头id以及地址类型查询订单地址.
     * 
     * @param siteType
     *            地址类型
     * @param headerId
     *            订单头id
     * @return 地址类型
     */
    List<SalesSites> selectSitesByHeaderIdAndSiteType(@Param("siteType") String siteType,
            @Param("headerId") Long headerId);
    
    /**
     * 根据订单头id以及地址类型查询订单地址.
     * 
     * @param siteType
     *            地址类型
     * @param headerId
     *            订单头id
     * @return 地址类型
     */
    List<SalesSites> selectSitesByHeaderIdAndSiteTypeCAN(@Param("siteType") String siteType,
            @Param("headerId") Long headerId);

    /**
     * 根据订单头ID删除地址.
     * 
     * @param isAutoship
     *            是否自动订货单
     * @param headerId
     *            订单头ID
     * @return 删除的行数
     */
    int deleteByHeaderIdAndAutoshipFlag(@Param("isAutoship") String isAutoship, @Param("headerId") Long headerId);
    
    List<SalesSites> selectSites(@Param("siteType") String siteType,
            @Param("headerId") Long headerId);
}