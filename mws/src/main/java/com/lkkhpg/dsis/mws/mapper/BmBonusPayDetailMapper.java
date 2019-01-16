/*
 *
 */

package com.lkkhpg.dsis.mws.mapper;

import java.util.List;

import com.lkkhpg.dsis.mws.dto.BmBonusPayDetail;

/**
 * @author guanghui.liu
 */
public interface BmBonusPayDetailMapper {

    List<BmBonusPayDetail> selectByMemberAndPeriod(BmBonusPayDetail record);

}