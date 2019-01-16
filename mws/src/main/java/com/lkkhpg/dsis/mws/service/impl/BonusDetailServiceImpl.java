/*
 *
 */

package com.lkkhpg.dsis.mws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.mws.dto.BmBonusDetail;
import com.lkkhpg.dsis.mws.dto.BmBonusPayDetail;
import com.lkkhpg.dsis.mws.mapper.BmBonusDetailMapper;
import com.lkkhpg.dsis.mws.mapper.BmBonusPayDetailMapper;
import com.lkkhpg.dsis.mws.service.IBonusDetailService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * mws会员奖金明细Service接口实现.
 * 
 * @author guanghui.liu
 */
@Service
@Transactional
public class BonusDetailServiceImpl implements IBonusDetailService {

    @Autowired
    private BmBonusDetailMapper bmBonusDetailMapper;

    @Autowired
    private BmBonusPayDetailMapper bmBonusPayDetailMapper;

    @Override
    public List<BmBonusDetail> queryBonusDetails(IRequest request, BmBonusDetail bmBonusDetail) {
        Long memberId = request.getAttribute(MemberConstants.MWS_MEMBER_ID);
        bmBonusDetail.setMemberId(memberId);
        List<BmBonusDetail> details = bmBonusDetailMapper.selectDetailsByMemberAndPeriod(bmBonusDetail);
        return details;
    }

    @Override
    public List<BmBonusPayDetail> queryBonusPayDetail(IRequest request, BmBonusPayDetail bmBonusPayDetail) {
        Long memberId = request.getAttribute(MemberConstants.MWS_MEMBER_ID);
        bmBonusPayDetail.setMemberId(memberId);
        List<BmBonusPayDetail> result = bmBonusPayDetailMapper.selectByMemberAndPeriod(bmBonusPayDetail);
        return result;
    }

}