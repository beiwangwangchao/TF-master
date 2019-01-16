/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.common.inventory.dto.RepackTrxDetail;

/**
 * 重新分包明细Mapper.
 * 
 * @author hanrui.huang
 */
public interface RepackTrxDetailMapper {
    
    int deleteByPrimaryKey(Long trxDetailId);

    int insert(RepackTrxDetail record);

    int insertSelective(RepackTrxDetail record);

    RepackTrxDetail selectByPrimaryKey(Long trxDetailId);

    int updateByPrimaryKeySelective(RepackTrxDetail record);

    int updateByPrimaryKey(RepackTrxDetail record);

    int deleteByTrxId(Long trxId);

    List<RepackTrxDetail> selectRepackTrxDetail(RepackTrxDetail record);
    
    List<RepackTrxDetail> selectRepackTrxDetails(RepackTrxDetail record);

    List<InvOnhandQuantity> queryComposeLot(InvOnhandQuantity record);
}