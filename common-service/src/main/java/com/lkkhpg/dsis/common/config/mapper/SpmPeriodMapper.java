/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;

/**
 * 奖金月份mapper接口.
 * 
 * @author gulin
 */
public interface SpmPeriodMapper {
    int deleteByPrimaryKey(Long periodId);

    int insert(SpmPeriod record);

    int insertSelective(SpmPeriod record);

    SpmPeriod selectByPrimaryKey(Long periodId);

    int updateByPrimaryKeySelective(SpmPeriod record);

    int closeBonusPeriod(SpmPeriod record);

    int updateByPrimaryKey(SpmPeriod record);

    int isExistsOpenPeriod(SpmPeriod record);

    /**
     * 查询是否在奖金期间且不是“已关闭”状态.
     * 
     * @param orderDate
     *            订单日期
     * @param closingStatus
     *            奖金期间关闭状态(Y-已关闭;N-未关闭)
     * @return 期间名称;可为空
     */
    String selectSpmPeriodByOrderDate(@Param("periodId") Long periodId, @Param("closingStatus") String closingStatus);

    /**
     * 查询奖金期间.
     * 
     * @param spmPeriod
     *            奖金期间查询条件
     * @return 期间列表
     */
    List<SpmPeriod> selectSpmPeriod(SpmPeriod spmPeriod);

    List<SpmPeriod> querySpmPeriod(SpmPeriod spmPeriod);

    int updateByTypeAndName(SpmPeriod record);

    /**
     * 生成奖金期间.
     * 
     * @param spmPeriod
     *            奖金期间查询条件
     * @return 响应数据
     */
    int insertBonusPeriod(SpmPeriod spmPeriod);

    /**
     * 根据条件奖金区间获取同类别下一个打开的区间.
     * 
     * @param spmPeriod
     *            条件奖金区间
     * @return 返回符合条件的奖金区间
     */
    SpmPeriod queryNextOpenPeriod(SpmPeriod spmPeriod);

    /**
     * 获取最近关闭的区间.
     * 
     * @param spmPeriod
     *            期间条件
     * @return 期间
     */
    SpmPeriod getCurentClosedPeroid(SpmPeriod spmPeriod);

    /**
     * 获取可以用的区间.
     * 
     * @param spmPeriod
     *            期间条件
     * @return 期间
     */
    List<SpmPeriod> selectUsableSpmPeriod(SpmPeriod spmPeriod);

    /**
     * 获取最近关闭前一个期间.
     * 
     * @param spmPeriod
     *            期间条件
     * @return 期间
     */
    SpmPeriod getPreviousCloseSpmPeriod(SpmPeriod spmPeriod);

    List<SpmPeriod> queryBonusDetail(SpmPeriod spmPeriod);

    /**
     * 根据订单获取奖金区间.
     * 
     * @param headerId
     *            订单id
     * @return 该订单的奖金区间
     */
    SpmPeriod getSpmPeriodByOrderId(Long headerId);

    /**
     * 查询当前销售组织对应的市场下的已关闭的奖金名称.
     * 
     * @return 奖金期间对象集合
     */
    List<SpmPeriod> getSpmPeriodNameBySalesOrgId();

    List<SpmPeriod> getSpmPeriodNameBySalesOrgIdNo();

    List<SpmPeriod> getSpmPeriodNameBySalesOrgIdA();

    List<SpmPeriod> getSpmPeriodNameBySalesOrgIdNoA();

    List<SpmPeriod> getSpmPeriodNameBySalesOrgIdNoClose();

    List<SpmPeriod> queryClosingPeriodInTw();

    /**
     * 根据日期和市场ID查询其所属期间.
     * 
     * @param betweenDate
     *            日期
     * @param orgId
     *            市场ID
     * @return 期间集合
     */
    List<SpmPeriod> selectPeriodByDateAndOrgId(@Param("betweenDate") Date betweenDate, @Param("orgId") Long orgId);
}