/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesRtnLine;

/**
 * 退货行Mapper.
 * 
 * @author houmin
 */
public interface SalesRtnLineMapper {
    int deleteByPrimaryKey(Long returnLineId);

    int insert(SalesRtnLine record);

    int insertSelective(SalesRtnLine record);

    SalesRtnLine selectByPrimaryKey(Long returnLineId);

    int updateByPrimaryKeySelective(SalesRtnLine record);

    int updateByPrimaryKey(SalesRtnLine record);

    /**
     * 根据销售订单行ID查询已退货总数量.
     * 
     * @param orderLineId
     *            销售订单行ID
     * @param returnStatus
     *            退货单状态
     * @param returnId
     *            退货单头ID
     * @return 已退货总数
     */
    BigDecimal selectSumQtyByOrderLineId(@Param("orderLineId") Long orderLineId,
            @Param("returnStatus") String returnStatus, @Param("returnId") Long returnId);

    /**
     * 获取商品包已发运数量.
     * 
     * @param parentLineId
     *            订单父级行Id
     * @param packItemId
     *            商品包ID
     * @return 以发运数量
     */
    BigDecimal getOutstandQtyByPkg(@Param("parentLineId") Long parentLineId, @Param("packItemId") Long packItemId);

    /**
     * 根据销售订单行ID查询返还优惠总和.
     * 
     * @param orderLineId
     *            销售订单行ID
     * @return 返回优惠总和
     */
    BigDecimal selectSumPtnByOrderLineId(Long orderLineId);

    /**
     * 查询商品计算库存和批次控制标识.
     * 
     * @param itemId
     *            商品ID
     * @param orgId
     *            库存组织ID
     * @return 计算库存和批次控制标识
     */
    Map<String, Object> selectByItemAndOrgId(@Param("itemId") Long itemId, @Param("orgId") Long orgId);

    /**
     * 根据退货单头Id删除退货单行信息.
     * 
     * @param returnId
     *            退货单头Id
     * @return 删除的记录数
     */
    int deleteByReturnId(Long returnId);

    /**
     * 查询退货单行详情.
     * 
     * @param salesOrgId
     *            销售组织ID
     * @param invOrgId
     *            库存组织ID
     * @param returnId
     *            退货单头ID
     * @param parentLineId
     *            订单父级行ID
     * @return 退货单行详情
     */
    List<SalesRtnLine> selectDetailByReturnId(@Param("salesOrgId") Long salesOrgId, @Param("invOrgId") Long invOrgId,
            @Param("returnId") Long returnId, @Param("returnStatus") String returnStatus,
            @Param("parentLineId") Long parentLineId);

    /**
     * 根据退货单头Id查找行详情.
     * 
     * @param map
     *            字段映射
     * @return 退货单行详情
     */
    List<SalesRtnLine> selectByReturnId(Map<String, Object> map);

    /**
     * 根据销售订单行ID查询已退货总数量 - dapp专用.
     * 
     * @param orderLineId
     *            销售订单行ID
     * @param returnStatus
     *            退货单状态
     * @return 已退货总数
     */
    BigDecimal selectSumQtyByOrderLineIdForDapp(@Param("orderLineId") Long orderLineId);

}