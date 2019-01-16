/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesLine;

/**
 * 订单行mapper.
 * 
 * @author wuyichu
 */
public interface SalesLineMapper {
    int deleteByPrimaryKey(SalesLine record);

    int insert(SalesLine record);

    int insertSelective(SalesLine record);

    SalesLine selectByPrimaryKey(Long lineId);

    SalesLine selectByPrimaryKeyForLock(Long lineId);

    int updateByPrimaryKeySelective(SalesLine record);

    int updateByPrimaryKey(SalesLine record);

    /**
     * 根据订单头的id删除订单行.
     * 
     * @param headerId
     *            订单头的id
     * @return 删除的行数
     */
    int deleteByHeadId(@Param("headerId") Long headerId);

    /**
     * 根据订单头的id查询订单行.
     * 
     * @param headerId
     *            订单头的id
     * @param isVirtual
     *            是否包含虚拟商品行.
     * @param isDetail
     *            是否包含子行.
     * @return 订单行集合
     */
    List<SalesLine> selectByHeadId(@Param("headerId") Long headerId, @Param("isVirtual") boolean isVirtual,
            @Param("isDetail") boolean isDetail);

    /**
     * 根据父级行ID删除.
     * 
     * @param parentLineId
     *            父级行ID
     * @return 删除的数目
     */
    int deleteByParentLineId(Long parentLineId);

    /**
     * 根据父级行ID查询.
     * 
     * @param parentLineId
     *            父级行ID
     * @return 查询的行集合
     */
    List<SalesLine> selectByParentLineId(Long parentLineId);

    /**
     * 查询退货单信息.
     * 
     * @param salesLine
     *            订单行信息
     * @return 行信息
     */
    List<SalesLine> selectByReturnHeaderId(SalesLine salesLine);

    /**
     * 根据头ID查找行信息.
     * 
     * @param map
     *            字段映射
     * @return 行信息
     */
    List<SalesLine> selectByHeaderId(Map<String, Object> map);

    /**
     * 根据退货单行Id查找销售单行信息.
     * 
     * @param map
     *            字段映射
     * @return 销售单行信息
     */
    SalesLine selectOrderLine(Map<String, Object> map);

    /**
     * 根据订单号查询的订单行集合.
     * 
     * @param orderId
     *            订单ID
     * @return 销售订单行列表
     */
    List<SalesLine> queryOrdersLine(Long orderId);

    /**
     * 发票模板根据头ID查找行信息.
     * 
     * @param map
     * @return 行信息
     */
    List<SalesLine> querySalesLineByHeaderId(Map<String, Object> map);

    /**
     * 判断订单头发运状态.
     * 
     * @param headerId
     *            订单头ID
     * @return 未发运行数
     */
    Long checkOrderShipStatus(Long headerId);
    
    /**
     * 判断订单行上商品类型.
     * 
     * @param headerId
     *            订单头ID
     * @return 是否为商品（非商品包）
     */
    Long checkOrderItemType(Long headerId);
    
    List<SalesLine> getLinesForDapp(Long headerId);
    
    List<SalesLine> getLinesByHeadersForDapp(Map headerIds);

    List<SalesLine> selectSalesLineByHead(Long headerId);
}