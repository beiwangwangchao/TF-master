/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesRtnLot;

/**
 * 退货批次Mapper.
 * 
 * @author houmin
 *
 */
public interface SalesRtnLotMapper {
    int deleteByPrimaryKey(Long returnLotId);

    int insert(SalesRtnLot record);

    int insertSelective(SalesRtnLot record);

    SalesRtnLot selectByPrimaryKey(Long returnLotId);

    int updateByPrimaryKeySelective(SalesRtnLot record);

    int updateByPrimaryKey(SalesRtnLot record);

    int saveSalesRtnLot(SalesRtnLot record);

    /**
     * 根据退货单行Id删除对应.
     * 
     * @param returnLineId
     *            退货单行Id
     * @return 删除记录数
     */
    int deleteByRtnLineId(Long returnLineId);

    /**
     * 同一订单行和批次号的已发运数量、退回数量和已退货数量.
     * 
     * @param returnId
     *            退货单Id
     * @param reutrnStatus
     *            退货单状态
     * @param itemId
     *            订单行上商品Id
     * @param lineId
     *            订单行Id
     * @param sortName
     * @param sortOrder
     * @return 批次对象信息
     */
    List<SalesRtnLot> selectLotsByItemIdAndLineId(@Param("returnId") Long returnId,
            @Param("returnStatus") String reutrnStatus, @Param("itemId") Long itemId, @Param("lineId") Long lineId,
            @Param("sortname") String sortName, @Param("sortorder") String sortOrder);

    /**
     * 根据退货单行ID查询批次信息.
     * 
     * @param returnLineId
     *            退货单行ID
     * @return 批次信息
     */
    List<SalesRtnLot> queryByReturnLineId(Long returnLineId);

}