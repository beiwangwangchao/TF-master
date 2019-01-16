/*
 *
 */

package com.lkkhpg.dsis.mws.service;

import java.util.List;

import com.lkkhpg.dsis.mws.dto.BmBonusDetail;
import com.lkkhpg.dsis.mws.dto.BmBonusPayDetail;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * mws会员奖金明细Service接口.
 * 
 * @author guanghui.liu
 */
public interface IBonusDetailService extends ProxySelf<IBonusDetailService> {

    /**
     * 查询某月奖金明细.
     * 
     * @param request
     *            统一上下文
     * @param bmBonusDetail
     *            包含period
     * @return 返回奖金明细
     */
    List<BmBonusDetail> queryBonusDetails(IRequest request, BmBonusDetail bmBonusDetail);

    /**
     * 查询某月奖金支付明细.
     * 
     * @param request
     * @param bmBonusPayDetail
     *            包含period
     * @return 返回奖金明细
     */
    List<BmBonusPayDetail> queryBonusPayDetail(IRequest request, BmBonusPayDetail bmBonusPayDetail);

}
