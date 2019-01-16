/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * @author runbai.chen
 */
public interface ISpmPeriodService extends ProxySelf<ISpmPeriodService> {
	/**
	 * 查询期间.
	 * 
	 * @param request
	 *            请求上下文
	 * @param spmPeriod
	 *            期间
	 * @return 期间列表
	 */

	List<SpmPeriod> querySpmPeriod(IRequest request, SpmPeriod spmPeriod);

	/**
	 * 查询奖金期间.
	 * 
	 * @param request
	 * @param spmPeriod
	 * @param page
	 * @param pagesize
	 * @return 奖金期间列表
	 */
	List<SpmPeriod> queryBonusPeriod(IRequest request, SpmPeriod spmPeriod, int page, int pagesize);

	/**
	 * 插入期间.
	 * 
	 * @param spmPeriod
	 * @return 期间
	 */
	SpmPeriod inertSpmPeriod(@StdWho SpmPeriod spmPeriod);

	/**
	 * 插入奖金期间.
	 * 
	 * @param request
	 *            请求上下文
	 * @param spmPeriod
	 *            界面条件spmPeriod
	 * @return 期间列表
	 */
	List<SpmPeriod> generateBonusPeriod(IRequest request, @StdWho SpmPeriod spmPeriod);

	/**
	 * 关闭奖金期间.
	 * 
	 * @param request
	 *            请求上下文
	 * @param spmPeriod
	 *            界面条件spmPeriod
	 * @return 期间列表
	 * @throws CommSystemProfileException
	 *             不存在打开期间异常
	 */
	List<SpmPeriod> closeBonusPeriod(IRequest request, SpmPeriod spmPeriod) throws CommSystemProfileException;

	/**
	 * 获取奖金区间.
	 * <ul>
	 * <li>查询该奖金区间是否存在，存在则返回.</li>
	 * <li>不存在则新建，并且激活.</li>
	 * </ul>
	 * 
	 * @param request
	 * @param spmPeriod
	 * @return 奖金区间
	 */
	SpmPeriod getPeriod(IRequest request, SpmPeriod spmPeriod);

	/**
	 * 获取前一个奖金区间.
	 * 
	 * @param request
	 *            统一上下文
	 * @param spmPeriod
	 *            当前的奖金区间
	 * @return 上一个奖金区间
	 */
	SpmPeriod getPreviousPeriod(IRequest request, SpmPeriod spmPeriod);

	List<SpmMarket> queryMarket(IRequest request, SpmMarket market);

    /**
     * 查询当前销售组织对应的市场下的已关闭的奖金名称.
     * 
     * @param request
     *            统一上下文
     * @return 期间名称集合
     */
    List<SpmPeriod> getSpmPeriodNameBySalesOrgId(IRequest request, String param);
    
    
    /**
     * 查询当前销售组织对应的市场下的已关闭的奖金名称.
     * 
     * @param request
     *            统一上下文
     * @return 期间名称集合
     */
    List<SpmPeriod> getSpmPeriodNameBySalesOrgIdA(IRequest request, String param);
	
	/**
     * 查询当前销售组织对应的市场下的奖金名称.
     * 
     * @param request
     *            统一上下文
     * @return 期间名称集合
     */
    List<SpmPeriod> getSpmPeriodNameBySalesOrgIdNoClose(IRequest request);

	/**
	 * 查询台湾市场下所有关闭的奖金区间.
	 * 
	 * @return 奖金区间集合
	 */
	List<SpmPeriod> queryClosingPeriodInTw(IRequest request);
}