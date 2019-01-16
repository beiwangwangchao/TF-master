/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import com.lkkhpg.dsis.common.order.dto.QueryOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单查询接口.
 *
 * @author gulin
 */
public interface QueryOrderMapper {
    List<QueryOrder> selectOrders(@Param("queryOrder") QueryOrder queryOrder,
            @Param("statusList") List<String> statusList, @Param("userType") String userType);

    /**
     * 查询所有待付款订单的部分信息
     * add by furong.tang 2018.1.23
     *
     * @return
     */
    List<QueryOrder> queryOrders();

    /**
     * 根据Id查询订单状态
     * add by furong.tang 2018.1.23
     * @param headerId
     * @return
     */
    String queryOrderStatusByKey(@Param("headerId") Long headerId);

     boolean updateQueryOrder(String attribute15,String orderNumber);


}
