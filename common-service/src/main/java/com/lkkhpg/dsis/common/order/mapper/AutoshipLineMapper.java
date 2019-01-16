/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.AutoshipLine;

/**
 * autoship 订单行的mapper.
 * 
 * @author wuyichu
 */
public interface AutoshipLineMapper {
    int deleteByPrimaryKey(Long lineId);

    int insert(AutoshipLine autoshipLine);

    int insertSelective(AutoshipLine autoshipLine);

    AutoshipLine selectByPrimaryKey(Long lineId);

    int updateByPrimaryKeySelective(AutoshipLine autoshipLine);

    int updateByPrimaryKey(AutoshipLine autoshipLine);

    /**
     * 根据订单头查询订单行.
     * 
     * @param headerId
     *            订单头id
     * @return autoship订单行集合
     */
    List<AutoshipLine> selectByHeaderId(Long headerId);

    /**
     * 根据自动订货头Id删除行信息.
     * 
     * @param headerId
     *            自动订货头ID.
     * @return 返回删除的行数.
     */
    int deleteByHeaderId(Long headerId);
    
    List<AutoshipLine> selectSimpleLine(Long autoShipId);
}