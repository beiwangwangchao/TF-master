/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.dto.TransferTrx;
import com.lkkhpg.dsis.common.inventory.dto.TransferTrxQuery;

/**
 * 移库事务MAPPER.
 * 
 * @author chenjingxiong
 */
public interface TransferTrxMapper {
    int deleteByPrimaryKey(Long trxId);

    int insert(TransferTrx record);

    int insertSelective(TransferTrx record);

    TransferTrx selectByPrimaryKey(Long trxId);

    int updateByPrimaryKeySelective(TransferTrx record);

    int updateByPrimaryKey(TransferTrx record);

    /**
     * 查询移库单据并加锁.
     * 
     * @param transferTrxQuery
     *            移库查询dto
     * @return 移库单list
     */
    List<TransferTrx> selectTransferTrxForUpdate(TransferTrxQuery transferTrxQuery);

    List<TransferTrx> selectTransferTrxs(TransferTrxQuery transferTrxQuery);

    List<TransferTrxQuery> selectInTransferTrxs(TransferTrxQuery transferTrxQuery);
    
    List<TransferTrx> selectTransferOutTrxsByOrg(@Param("trxNumber") String trxNumber);
}