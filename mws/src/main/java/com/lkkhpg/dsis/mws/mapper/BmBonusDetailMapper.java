/*
 *
 */

package com.lkkhpg.dsis.mws.mapper;

import java.util.List;

import com.lkkhpg.dsis.mws.dto.BmBonusDetail;

/**
 * @author guanghui.liu
 */
public interface BmBonusDetailMapper {

    List<BmBonusDetail> selectDetailsByMemberAndPeriod(BmBonusDetail record);

}