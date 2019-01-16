/*
 *
 */
package com.lkkhpg.dsis.common.bonus.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusRelease;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;

/**
 * 奖金发放MAPPER.
 * @author runbai.chen
 */
public interface BonusReleaseMapper {
    int deleteByPrimaryKey(Long bonusId);

    int insert(BonusRelease record);

    int insertSelective(BonusRelease record);

    BonusRelease selectByPrimaryKey(Long bonusId);

    List<BonusRelease> selectBonusRelease(BonusRelease bonuRelease);

    int updateByPrimaryKeySelective(BonusRelease record);

    int updateByPrimaryKey(BonusRelease record);

    int countBonusRelease(BonusRelease bonuRelease);

    void updateReleaseStatus(BonusReleaseCombine bonusReleaseCombine);
    
    List<BonusRelease> queryYearRelease(BonusRelease bonuRelease);
    
    List<BonusRelease> queryMonthTWRelease(BonusRelease bonuRelease);
    
    List<BonusRelease> queryDetailByFinal(BonusFinal bonusFinal);
}