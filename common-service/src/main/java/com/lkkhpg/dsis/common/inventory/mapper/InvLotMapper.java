/*
 *
 */
package com.lkkhpg.dsis.common.inventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.inventory.dto.InvLot;

/**
 * Lot Mapper.
 * 
 * @author runbai.chen
 *
 */
public interface InvLotMapper {

    int insertInvLot(InvLot invLot);

    int insertSelective(InvLot invLot);

    int deleteInvLot(InvLot invLot);

    int updateInvLot(InvLot invLot);

    int queryCount(InvLot invLot);

    List<InvLot> selectInvLot(InvLot invLot);

    List<InvLot> selectInvLotsByParas(InvLot invLot);

    /**
     * 查询商品有库存量的批次.
     * 
     * @param itemId
     *            商品id
     * @param orgId
     *            库存组织id
     * @return 查询结果
     */
    List<InvLot> queryItemLots(@Param("itemId") Long itemId, @Param("orgId") Long orgId);

    /**
     * 批次数查询.
     * 
     * @param invLot
     *            批次对象
     * @return 批次数
     */
    int queryInvLotCount(InvLot invLot);
}
