/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.SalesReturn;
import com.lkkhpg.dsis.common.report.dto.SgReturnOrderLine;

/**
 * 新加坡退货单报表.
 * @author zhenyang.he
 *
 */
public interface SgReturnOrderMapper {
    
    SalesReturn queryRetrunSales(String returnNumber);

    List<SgReturnOrderLine> queryReturnOrderLine(String returnNumber);
    
}
