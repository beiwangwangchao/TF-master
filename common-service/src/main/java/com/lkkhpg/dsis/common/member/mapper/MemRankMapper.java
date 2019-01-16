/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemRank;

/**
 * 会员等级Mapper.
 * 
 * @author frank.li
 */
public interface MemRankMapper {
    int deleteByPrimaryKey(Long rankId);

    int insert(MemRank record);

    int insertSelective(MemRank record);

    MemRank selectByPrimaryKey(Long rankId);

    List<MemRank> selectByMemberId(Long memberId);

    List<MemRank> selectByMonthRang(Map<String, Object> map);

    List<MemRank> selectMemRanks(MemRank record);

    int updateByPrimaryKeySelective(MemRank record);

    int updateByPrimaryKey(MemRank record);

    BigDecimal selectSumPVByMemberId(Long memberId);

    /**
     * 根据会员Id和月份查询是否存在记录.
     * 
     * @param memberId
     *            会员ID
     * @param periodName
     *            月份名称
     * @return 为空-则执行后续insert;不为空-执行后续的update操作
     */
    MemRank selectByMemberIdAndMonth(@Param("memberId") Long memberId, @Param("periodName") String periodName);

    /**
     * 插入会员等级信息.
     * 
     * @param memRank
     *            会员等级信息
     * @return 插入的记录条数
     */
    int insertMemRank(MemRank memRank);

    /**
     * 更新会员等级信息.
     * 
     * @param memRank
     *            会员等级信息
     * @return 插入的记录条数
     */
    int updateByMemberIdAndMonth(MemRank memRank);

    /**
     * mws我的团队-个人信息.
     * 
     * @author Zhaoqi
     * @param memberId
     *            会员id
     * @return 个人信息
     */
    List<MemRank> selectTeamByMemberId(Long memberId);

    /**
     * mws我的团队-获取时间段的memRank信息.
     * 
     * @author Zhaoqi
     * @param map
     *            参数集合
     * @return 时间段内的memRank信息
     */
    List<MemRank> queryDatePeriod(Map<String, Object> map);

    /**
     * 根据会员卡号和当前月份从OMK表取得等级信息.
     * 
     * @param map
     *            字段映射
     * @return 会员等级信息
     */
    MemRank selectByCurrentMonthRangFromOmk(Map<String, Object> map);

    /**
     * 根据会员卡号和历史月份期间从OMK表取得等级信息.
     * 
     * @param map
     *            字段映射
     * @return 会员等级信息
     */
    List<MemRank> selectByHistoryMonthRangFromOmk(Map<String, Object> map);

    /**
     * 根据会员卡号从OMK表取得等级信息(当前和历史 ).
     * 
     * @param map
     *            字段映射
     * @return 会员等级信息
     */
    List<MemRank> selectCurrentAndHistoryRank(Map<String, Object> map);

    /**
     * 根据会员卡号从OMK表取得中国会员的等级信息(当前和历史 ).
     * 
     * @param map
     *            字段映射
     * @return 会员等级信息
     */
    List<MemRank> selectRankForChn(Map<String, Object> map);
}