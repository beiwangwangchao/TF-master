/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.util.List;

import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusRelease;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 奖金发放.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface IBonusReleaseService extends ProxySelf<IBonusReleaseService> {

    /**
     * 查询奖金发放.
     * 
     * @param request
     *            上下文请求
     * @param bonuRelease
     *            奖金信息
     * @param page
     *            页码.
     * @param pagesize
     *            页面size.
     * @return 奖金发放列表
     */
    List<BonusRelease> queryBonusRelease(IRequest request, BonusRelease bonuRelease, int page, int pagesize);

    /**
     * 查询奖金发放条数.
     * 
     * @param request
     *            上下文请求
     * @param bonuRelease
     *            奖金发放条件
     * @return 奖金条数
     */
    int countBonusRelease(IRequest request, BonusRelease bonusRelease);

    /**
     * 导出奖金发放.
     * 
     * @param request
     *            上下文请求
     * @param bonusReleaseCombine
     *            奖金合并信息
     * @return 是否成功
     * @throws CommMemberException
     *             抛出账务事务处理异常
     */
    boolean exportBonus(IRequest request, BonusReleaseCombine bonusReleaseCombine) throws CommMemberException;

    /**
     * 创建最终奖金.
     * 
     * @param request
     *            上下文请求
     * @param bonusReleaseCombine
     *            奖金合并信息
     * @throws CommMemberException
     */
    void createFinalBonus(IRequest request, BonusReleaseCombine bonusReleaseCombine) throws CommMemberException;

    /**
     * 冲销节余.
     * 
     * @param request
     *            上下文请求
     * @param memWithdraw
     *            冲销节余查询条件
     * @return 冲销节余列表
     */
    List<MemWithdraw> getMemWithdraw(IRequest request, MemWithdraw memWithdraw);

    /**
     * 奖金支付自动失败.
     * 
     * @param request
     *            统一上下文
     * @param releaseCombine
     *            发放奖金信息
     * @throws CommMemberException
     *             异常信息
     */
    void autoFailBonusFinal(IRequest request, BonusReleaseCombine releaseCombine) throws CommMemberException;

    /**
     * 奖金回退操作.
     * 
     * @param request
     *            统一上下文.
     * @param spmPeriod
     *            奖金期间.
     * @param bonusType
     *            奖金类型.
     */
    void rollbackRelease(IRequest request, SpmPeriod spmPeriod, String bonusType);

    /**
     * 查询年度奖金税务.
     * 
     * @param request
     *            统一上下文.
     * @param bonusRelease
     *            查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            分页大小.
     * @return 返回符合条件的结果.
     */
    List<BonusRelease> queryYearRelease(IRequest request, BonusRelease bonusRelease, int page, int pagesize);

    /**
     * 查询月度奖金税务.
     * 
     * @param request
     *            统一上下文.
     * @param bonusRelease
     *            查询条件.
     * @param page
     *            页码.
     * @param pagesize
     *            分页大小.
     * @return 返回符合条件的结果.
     */
    List<BonusRelease> queryMonthRelease(IRequest request, BonusRelease bonusRelease, int page, int pagesize);

    /**
     * 根据最终奖金查询相关月度奖金.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            最终奖金记录.
     * @return 返回查询结果.
     */
    List<BonusRelease> queryDetailByFinal(IRequest request, BonusFinal bonusFinal);
}
