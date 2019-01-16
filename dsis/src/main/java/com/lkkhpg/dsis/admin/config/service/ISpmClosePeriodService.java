/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 关闭期间-调用接口.
 * 
 * @author Zhaoqi
 *
 */
public interface ISpmClosePeriodService extends ProxySelf<ISpmClosePeriodService> {

    /**
     * 关闭奖金期间.
     * 
     * @param request
     *            统一上下文
     * @param spmPeriod
     *            期间参数
     * @return 期间集合列表
     * @throws IntegrationException
     *             接口异常
     * @throws CommSystemProfileException
     */
    List<SpmPeriod> closeBonusPeriod(IRequest request, SpmPeriod spmPeriod)
            throws IntegrationException, CommSystemProfileException;

    /**
     * 关闭奖金期间后，生成Service Center和Ipoint Center奖金调整记录.
     * 
     * @param spmPeriod
     *            奖金期间集合
     */
    void doBonusAdjust(List<SpmPeriod> spmPeriods);
}
