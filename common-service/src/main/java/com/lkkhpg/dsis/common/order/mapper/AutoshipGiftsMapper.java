/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.order.dto.AutoshipGifts;

/**
 * AutoShip礼品mapper.
 * 
 * @author zhangchuangsheng
 *
 */
public interface AutoshipGiftsMapper {
    int deleteByPrimaryKey(Long giftId);

    int insert(AutoshipGifts record);

    int insertSelective(AutoshipGifts record);

    AutoshipGifts selectByPrimaryKey(Long giftId);

    List<AutoshipGifts> selectByRuleId(Long ruleId);

    int updateByPrimaryKeySelective(AutoshipGifts record);

    int updateByPrimaryKey(AutoshipGifts record);

    List<AutoshipGifts> queryAutoshipGifts(AutoshipGifts record);
}