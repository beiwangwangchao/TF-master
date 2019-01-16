/*
 *
 */
package com.lkkhpg.dsis.common.bonus.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;

/**
 * re_transfer mapper.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface BonusRetransferMapper {
    int deleteByPrimaryKey(BigDecimal retransId);

    int insert(BonusRetransfer record);

    int insertSelective(BonusRetransfer record);

    BonusRetransfer selectByPrimaryKey(BigDecimal retransId);

    int updateByPrimaryKeySelective(BonusRetransfer record);

    int updateByPrimaryKey(BonusRetransfer record);

    List<BonusRetransfer> selectBonusRetransfer(BonusRetransfer retransferOrder);

    int updateRetransferStatus(BonusReleaseCombine bonusReleaseCombine);

    void rollback(@Param("bonusFinal") List<BonusFinal> bonusFinal, @Param("status") String status);

    void deleteRecordByFinal(BonusRetransfer record);

}