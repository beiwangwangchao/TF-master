/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

/**
 * iPonit center 奖金计算Service.
 * 
 * @author homin
 *
 */
public interface IIpointCenterBonusJobService {

    /**
     * 奖金计算.
     * 
     * @param spmPeriod
     *            奖金月份对象
     */
    void bonusCalculationOfICB() throws Exception;

}
