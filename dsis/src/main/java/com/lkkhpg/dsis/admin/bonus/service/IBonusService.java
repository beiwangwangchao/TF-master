/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 奖金service.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface IBonusService extends ProxySelf<IBonusService> {

    /**
     * 查询期间.
     * 
     * @param request
     *            上下文请求
     * @param marketId
     *            页面市场id
     * @return 期间列表
     */
    List<SpmPeriod> getPeriod(IRequest request, Long marketId);

    /**
     * 根据期间类型查询期间.
     * 
     * @param request
     *            上下文请求
     * @param periodType
     *            期间类型
     * @param marketId
     *            市场id
     * @return 期间列表
     */
    List<SpmPeriod> getPeriod(IRequest request, String periodType, Long marketId);

    /**
     * 可用奖金期间.
     * 
     * @param request
     *            上下文请求
     * @param marketId
     *            市场id
     * @return 期间列表
     */
    List<SpmPeriod> getUsablePeriod(IRequest request, Long marketId);

    /**
     * 获取最近期间.
     * 
     * @param request
     *            上下文请求
     * @param marketId
     *            市场id
     * @return 奖金期间
     */
    SpmPeriod getLastClosedPeriod(IRequest request, Long marketId);

    /**
     * 根据传入参数获取下一个状态为打开的奖金区间.
     * 
     * @param request
     *            统一上下文.
     * @param period
     *            条件奖金区间
     * @param marketId
     *            市场id
     * @return 返回符合条件的奖金区间.
     */
    SpmPeriod getNextOpenPeriod(IRequest request, SpmPeriod period, Long marketId);

    /**
     * 最近关闭的前一个期间.
     * 
     * @param request
     *            上下文请求
     * @param marketId
     *            市场id
     * @return 奖金期间
     */
    SpmPeriod getPreviousCloseSpmPeriod(IRequest request, Long marketId);

    /**
     * 查询已关闭奖金区间.
     * 
     * @param request
     *            统一上下文.
     * @param marketId
     *            市场id.
     * @return 奖金区间集合.
     */
    List<SpmPeriod> getCloseSpmPeriod(IRequest request, Long marketId);

    /**
     * 根据奖金类型获取关闭的奖金期间.
     * 
     * @param request
     *            统一上下文.
     * @param bonusType
     *            奖金类型.
     * @param queryType
     *            查询期间类型.
     * @param marketId
     *            市场id.
     * @return 符合条件的奖金期间.
     */
    SpmPeriod getSpmPeriodByType(IRequest request, String bonusType, String queryType, Long marketId);
}
