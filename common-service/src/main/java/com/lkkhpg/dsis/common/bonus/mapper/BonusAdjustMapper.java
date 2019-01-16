/*
 *
 */
package com.lkkhpg.dsis.common.bonus.mapper;


import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.bonus.dto.BonusAdjust;
/**
 * 奖金调整mapper.
 * @author li.peng@hand-china.com
 *
 */
public interface BonusAdjustMapper {
    int deleteByPrimaryKey(BonusAdjust record);

    int insert(BonusAdjust record);

    int insertSelective(BonusAdjust record);

    BonusAdjust selectByPrimaryKey(Long adjId);

    int updateByPrimaryKeySelective(BonusAdjust record);

    int updateByPrimaryKey(BonusAdjust record);
    
    List<BonusAdjust> queryBonusAdjust(BonusAdjust bonusAdjust);
    
    int deleteBonusAdjust(Map<String, Object> bonusAdjustsMap);
    
    int updateBonusStatus(BonusAdjust bonusAdjust);
}