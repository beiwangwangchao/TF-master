/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.CommodityList;

/**
 * 商品列表接口.
 * 
 * @author houmin
 *
 */
public interface CommodityListMapper {

    /**
     * 根据订单编号查询订单信息.
     * 
     * @param orderHeaderId
     *            订单头ID
     * @return 对应订单的商品列表信息
     */
    List<CommodityList> selectItemsByOrderHeaderId(@Param("orderHeaderId") Long orderHeaderId);

}
