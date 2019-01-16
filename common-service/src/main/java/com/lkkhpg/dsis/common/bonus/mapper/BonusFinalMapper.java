/*
 *
 */
package com.lkkhpg.dsis.common.bonus.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;

/**
 * 最终奖金mapper.
 * 
 * @author gulin
 *
 */
public interface BonusFinalMapper {
    int deleteByPrimaryKey(Long bonusId);

    int insert(BonusFinal record);

    int insertSelective(BonusFinal record);

    BonusFinal selectByPrimaryKey(Long bonusId);

    int updateByPrimaryKeySelective(BonusFinal record);

    int updateByPrimaryKey(BonusFinal record);

    List<BonusFinal> queryDetailsByBonusFinal(BonusFinal bonus);

    void createFinalBonus(Map<String, Object> combineInfo);

    BonusFinal queryCreateFile(BonusFinal bonus);

    BonusFinal queryCount(BonusFinal bonus);

    List<BonusFinal> queryBonusFinal(BonusReleaseCombine releaseCombine);

    List<BonusFinal> autoFailBonusFinal(BonusFinal bonusFinal);

    /**
     * 更新最终奖金flag.
     * 
     * @param bonusFinals
     *            需要更新的奖金记录.
     * @param lockFlag
     *            锁定flag.
     * @param autoFlag
     *            自动支付失败flag.
     * @param remitDate
     *            汇款日期.
     * @param processMode
     *            奖金处理标识.
     * @return 返回更新行数.
     */
    int updateLockFlag(@Param("bonusFinals") List<BonusFinal> bonusFinals, @Param("lockFlag") String lockFlag,
            @Param("autoFlag") String autoFlag, @Param("remitDate") Date remitDate,
            @Param("processMode") String processMode);

    /**
     * RPT-00172报表所用(台湾市场下的数据).
     * 
     * @param bonusFinal
     * @return bonusFinals
     */
    List<BonusFinal> queryDetails(BonusFinal bonusFinal);

    /**
     * 查询当前市场下最新一条奖金记录明细.
     * 
     * @param marketId
     *            市场id.
     * 
     * @return 返回最新一条奖金记录明细.
     */
    BonusFinal queryLastRecordByMarket(Long marketId);

    List<BonusFinal> queryDetailsForBankFile(@Param("bonus") BonusFinal bonus, @Param("marketCode") String marketCode);
}