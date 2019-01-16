/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.dto.RepackTrx;

/**
 * 重新分包Mapper.
 * 
 * @author hanrui.huang
 */
public interface RepackTrxMapper {
    int deleteByPrimaryKey(Long trxId);

    int insert(RepackTrx record);

    int insertSelective(RepackTrx record);

    RepackTrx selectByPrimaryKey(Long trxId);
    
    RepackTrx selectByTrxNumber(String trxNumber);

    int updateByPrimaryKeySelective(RepackTrx record);

    int updateByPrimaryKey(RepackTrx record);

    List<RepackTrx> selectRepack(RepackTrx repackTrx);

    int deleteRepack(String trxNumber);

    int updateByTrxNumber(RepackTrx record);
}