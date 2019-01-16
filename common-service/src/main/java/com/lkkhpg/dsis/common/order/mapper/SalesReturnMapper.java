/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesReturn;

/**
 * 退货Mapper.
 * 
 * @author houmin
 */
public interface SalesReturnMapper {
    int deleteByPrimaryKey(Long returnId);

    int insert(SalesReturn record);

    int insertSelective(SalesReturn record);

    SalesReturn selectByPrimaryKey(Long returnId);

    int updateByPrimaryKeySelective(SalesReturn record);

    int updateByPrimaryKey(SalesReturn record);

    List<SalesReturn> selectSalesReturnByParas(SalesReturn salesReturn);

    /**
     * 锁住所查询退货单记录.
     * 
     * @param returnId
     *            退货单Id
     * @return 退货单头详情
     */
    SalesReturn selectByReturnIdForLock(Long returnId);

    /**
     * 获取退货单详情.
     * 
     * @param returnId
     *            退货单头ID
     * @return 退货单详情
     */
    SalesReturn selectDetailByReturnId(Long returnId);

    /**
     * 是否已经返还人工调整.
     * 
     * @param headerId
     *            退货订单头ID
     * @param returnId
     *            退货单头ID
     * @return 满足条件的数量
     */
    int isReturnAdjustFlag(@Param("headerId") Long headerId, @Param("returnId") Long returnId);

    /**
     * 是否已经返还运费.
     * 
     * @param headerId
     *            退货订单头ID
     * @param returnId
     *            退货单头ID
     * @return 满足条件的数量
     */
    int isReturnShippingFeeFlag(@Param("headerId") Long headerId, @Param("returnId") Long returnId);

    /**
     * 获取同步标记不为Y的退货记录.
     * 
     * @param map
     *            字段映射
     * @return 退货单集合
     */
    List<SalesReturn> selectBySync(Map<String, Object> map);

    /**
     * 更新同步标记.
     * 
     * @param returnNumber
     *            退货单编号
     * @param syncFlag
     *            同步标记
     */
    void updateSyncFlagByReturnNumber(@Param("returnNumber") String returnNumber, @Param("syncFlag") String syncFlag);

    /**
     * 获取订单相关金额。
     * 
     * @param salesReturn
     *            退货单对象
     * @return 订单相关联金额
     */
    SalesReturn getAmount(SalesReturn salesReturn);

    /**
     * 根据去除首位的退单编号查找退货单.
     * 
     * @param map
     *            字段映射
     * @return SalesReturn 退货单信息
     */
    SalesReturn selectRObySubRm(Map<String, Object> map);
}