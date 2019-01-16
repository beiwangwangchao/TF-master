/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesLogistics;

/**
 * 订单配送mapper.
 * 
 * @author wuyichu
 */
public interface SalesLogisticsMapper {
    int deleteByPrimaryKey(Long logisticsId);

    int insert(SalesLogistics record);

    int insertSelective(SalesLogistics record);

    SalesLogistics selectByPrimaryKey(Long logisticsId);

    int updateByPrimaryKeySelective(SalesLogistics record);

    int updateByPrimaryKey(SalesLogistics record);

    /**
     * 根据订单头id查询.
     * 
     * @param headerId
     *            订单id
     * @param autoShipFlag
     *            订单是否autoship Y/N
     * @return 订单配送信息
     */
    SalesLogistics selectByHeaderId(@Param("headerId") Long headerId, @Param("autoShipFlag") String autoShipFlag);

    /**
     * 根据订单头id删除.
     * 
     * @param headerId
     *            订单头id
     * @param autoShipFlag
     *            订单是否autoship Y/N
     * @return 删除的行数
     */
    int deleteByHeaderId(@Param("headerId") Long headerId, @Param("autoShipFlag") String autoShipFlag);
    
    /**
     * 根据订单头id获取运费.
     * @param headerId 订单头id
     * @return 配送信息
     */
    SalesLogistics queryFreightByHeaderId(Long headerId);
}