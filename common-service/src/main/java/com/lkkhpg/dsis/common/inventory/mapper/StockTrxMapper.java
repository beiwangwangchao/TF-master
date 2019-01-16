/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.dto.SpmInvPeriod;
import com.lkkhpg.dsis.common.inventory.dto.StockTrx;

/**
 * 出入库Mapper.
 * 
 * @author mclin
 */
public interface StockTrxMapper {
    int deleteByPrimaryKey(StockTrx record);

    int insert(StockTrx record);

    int insertStockTrx(StockTrx record);

    StockTrx selectByPrimaryKey(@Param("trxId") Long trxId);   

    int updateByPrimaryKeySelective(StockTrx record);

    int updateByPrimaryKey(StockTrx record);

    List<StockTrx> queryStockTrxs(@Param("stockTrx") StockTrx stockTrx, @Param("list") List<String> list);

    int deleteStockTrx(StockTrx record);

    int updateStatus(StockTrx record);

    List<StockTrx> selectByVendorId(Long vendorId);

    /**
     * 根据 库存组织、状态、事务处理日期获取数据信息. (用于库存期间关闭时校验判断)
     * 
     * @param stockTrx
     *            出入库事务处理对象.
     * @return
     */
    List<StockTrx> queryStockTrxForInvPeriod(SpmInvPeriod spmInvPeriod);
    
    /**
     * 获取自增的出入库ID
     * 
     * @return
     */
	Long getTrxId();
}