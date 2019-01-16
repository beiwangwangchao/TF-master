/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.AutoshipGiftRule;

/**
 * AutoShip礼品规则mapper.
 * 
 * @author zhangchuangsheng
 *
 */
public interface AutoshipGiftRuleMapper {

    int deleteByPrimaryKey(Long ruleId);

    int insert(AutoshipGiftRule record);

    int insertSelective(AutoshipGiftRule record);

    AutoshipGiftRule selectByPrimaryKey(Long ruleId);

    int updateByPrimaryKeySelective(AutoshipGiftRule record);

    int updateByPrimaryKey(AutoshipGiftRule record);

    List<AutoshipGiftRule> queryAutoshipGiftRule(AutoshipGiftRule record);

    /**
     * 查询市场下唯一一条启用规则.
     * 
     * @param marketId
     *            市场id
     * @return atuo 礼品规则
     */
    AutoshipGiftRule queryAutoshipGiftRuleBySalesOrgId(@Param(value = "salesOrgId") Long salesOrgId);
    
    int queryAutoshipGiftRuleCount(AutoshipGiftRule record);
}