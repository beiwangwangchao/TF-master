/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.AutoshipOrder;

/**
 * 自动订货单查询Mapper.
 * 
 * @author HuangJiaJing
 *
 */
public interface AutoshipOrderMapper {

    int deleteByPrimaryKey(Long autoshipId);

    int insert(AutoshipOrder autoshipOrder);

    int insertSelective(AutoshipOrder autoshipOrder);

    AutoshipOrder selectByPrimaryKey(Long autoshipId);
    
    AutoshipOrder selectByAutoshipNumber(String  autoshipNumber);

    int updateByPrimaryKeySelective(AutoshipOrder autoshipOrder);

    int updateByPrimaryKey(AutoshipOrder autoshipOrder);

    /**
     * 根据条件查询订单信息.
     * 
     * @param autoshipOrder
     *            订单查询对象
     * @return AutoShipOrder集合
     */
    List<AutoshipOrder> selectAutoshipOrdersByParas(AutoshipOrder autoshipOrder);

    /**
     * 修改autoShip的订单状态.
     * 
     * @param autoshipId
     *            autoshipid
     * @param autoshipStatus
     *            需要修改的状态
     * @return 修改行数
     */
    int updateStatusByPrimaryKey(@Param("autoshipId") Long autoshipId, @Param("autoshipStatus") String autoshipStatus);

    /**
     * 校验会员是否在系统中已经存在激活与暂停的单子.
     * 
     * @param memberId
     * @return 返回存在的单子的id.
     */
    Long checkByMemberId(@Param("memberId") Long memberId);

    /**
     * 校验该autoshipId是否在系统中已经存在相同用户激活与暂停的单子.
     * 
     * @param autoshipId
     * @return 返回存在的单子的id.
     */
    Long checkById(@Param("autoshipId") Long autoshipId);

    /**
     * 根据会员id，销售区域id查询自动订单头信息.
     * 
     * @param memberId
     *            会员id.
     * @param salesOrgId
     *            销售订单头id.
     * @return 返回查询到的销售订单头id，若无则返回空.
     */
    AutoshipOrder selectAutoShipOrderByMember(@Param("memberId") Long memberId, @Param("salesOrgId") Long salesOrgId);
}
