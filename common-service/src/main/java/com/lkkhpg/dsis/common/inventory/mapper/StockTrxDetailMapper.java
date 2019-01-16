/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.dto.StockTrx;
import com.lkkhpg.dsis.common.inventory.dto.StockTrxDetail;

/**
 * 出入库Mapper.
 * 
 * @author mclin
 */
public interface StockTrxDetailMapper {
    int deleteByPrimaryKey(StockTrxDetail record);

    int insert(StockTrxDetail record);

    Long insertStockTrxDetail(StockTrxDetail record);

    StockTrxDetail selectByPrimaryKey(Long trxDetailId);

    int updateByPrimaryKeySelective(StockTrxDetail record);

    int updateByPrimaryKey(StockTrxDetail record);

    List<StockTrxDetail> queryDetails(@Param("trxId") Long trxId, @Param("organizationId") Long organizationId);

    int deleteByTrxId(StockTrx stockTrx);

    List<StockTrxDetail> getOutRefundItem(String outRefundNo);
}