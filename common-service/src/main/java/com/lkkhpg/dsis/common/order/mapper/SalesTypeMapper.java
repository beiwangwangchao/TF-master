/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.order.mapper;

import com.lkkhpg.dsis.common.order.dto.SalesType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售类型Mapper.
 * 
 * @author zhiwei.zhang@hand-china.com [#PE20-4][ADD],2016-12-06 14:20:03.
 */
public interface SalesTypeMapper {

    int update(SalesType salesType);

    int insert(SalesType salesType);

    List<SalesType> queryByOrderTypeId(Long orderTypeId);

    int deleteByPrimaryKey(Long orderTypeId);

    int deleteByOrderTypeId(Long orderTypeId);

    List<SalesType> querySalesTypeForOrder(@Param(value = "salesOrgId") Long salesOrgId,
                                           @Param(value = "orderType") String orderType, @Param(value = "salesType") String salesType);
    
    //#POS2SUPT-1158 2017-07-18 BEGIN 
    SalesType queryByOrderTypeIdAndSalesType(SalesType salesType);
    //#POS2SUPT-1158 2017-07-18 END 
    
    // POS2SUPT-1171 2017-09-04 BEGIN
    List<SalesType> selectBySalesType(@Param(value = "salesOrgId") Long salesOrgId, @Param(value = "salesType") String salesType);
    // POS2SUPT-1171 2017-09-04 END
}
