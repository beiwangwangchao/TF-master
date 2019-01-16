/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import com.lkkhpg.dsis.common.order.dto.OrderType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单类型Mapper.
 * 
 * @author zhiwei.zhang@hand-china.com [#PE20-4][ADD],2016-12-06 14:20:03.
 */
public interface OrderTypeMapper {

    int update(OrderType orderType);

    int insert(OrderType orderType);

    List<OrderType> queryByOrderType(OrderType orderType);

    int deleteByPrimaryKey(Long orderTypeId);

    OrderType queryForOrder(@Param("salesOrgId") Long salesOrgId, @Param("orderType") String orderType,
                            @Param("salesType") String salesType);

    OrderType queryBySalesOrgAndOrderType(@Param("salesOrgId") Long salesOrgId, @Param("orderType") String orderType);

	List<OrderType> queryAll();
	
	List<Map> queryBySalesOrgId(@Param("salesOrgId") Long salesOrgId);

}
