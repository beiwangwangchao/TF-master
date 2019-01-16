/*
 * #{copyright}#
 */
package com.lkkhpg.dsis.common.report.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.report.dto.TWSalesOrderReturnHeader;
import com.lkkhpg.dsis.common.report.dto.TWSalesOrderReturnLine;
import com.lkkhpg.dsis.common.report.dto.TWSalesOrderReturnfoot;
/**
 * 台湾营业人销货退回单报表.
 * 
 * @author zhenyang.he
 *
 */
public interface TWSalesOrderReturnMapper {

    TWSalesOrderReturnHeader queryReturnHeader(String returnNumber);
    
    TWSalesOrderReturnfoot queryReturnfoot(String returnNumber);
    
    List<TWSalesOrderReturnLine> queryReturnList(String returnNumber);
    
    List<TWSalesOrderReturnfoot> queryAdjustmentAmount(Long headerId);
    
    TWSalesOrderReturnfoot queryShippingFee(Long headerId);
}
