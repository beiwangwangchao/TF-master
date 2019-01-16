/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 最终奖金service接口.
 * 
 * @author gulin
 *
 */
public interface IBonusFinalService extends ProxySelf<IBonusFinalService> {
    /**
     * 根据查询条件查询最终奖金集合.
     * 
     * @param request
     *            统一上下问.
     * @param bonusFinal
     *            查询条件dto.
     * @param page
     *            页码.
     * @param pageseize
     *            页面size.
     * @return 返回符合条件的最终奖金集合.
     */
    List<BonusFinal> queryBonusFinal(IRequest request, BonusFinal bonusFinal, int page, int pageseize);

    /**
     * 判断该最终奖金记录是否可以支付失败，若可以，则更新.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            需要判断的记录.
     * @return 不符合条件则返回error,符合条件则继续支付失败逻辑，最终返回success.
     * @throws CommMemberException
     *             抛出账务事务处理异常
     */
    List<String> checkBonusAndUpdate(IRequest request, BonusFinal bonusFinal) throws CommMemberException;

    /**
     * 生成最终奖金文件（不同市场）.
     * 
     * @param request
     *            统一上下文.
     * @param response
     *            HttpServletResponse.
     * @param periodId
     *            奖金区间.
     * @param bonusType
     *            奖金类型.
     * @param marketId
     *            市场id.
     * @throws CommBonusException
     *             抛出奖金文件生成异常.
     */
    void downloadFinalBonus(IRequest request, HttpServletResponse response, Long periodId, String bonusType,
            Long marketId) throws CommBonusException;

    /**
     * 奖金combine生成最终奖金.(oracel).
     * 
     * 
     * @param request
     *            上下文请求
     * @param releaseCombine
     *            奖金combine
     */
    void createFinalBonus(IRequest request, BonusReleaseCombine releaseCombine);

    /**
     * 奖金combine生成最终奖金.(java).
     * 
     * @param request
     *            上下文请求
     * @param releaseCombine
     *            奖金combine
     */
    void insertFinalBonus(IRequest request, BonusReleaseCombine releaseCombine);

    /**
     * 检验是否存在最终奖金记录.
     * 
     * @param request
     *            统一上下文.
     * @param periodId
     *            奖金期间ID.
     * @param bonusType
     *            奖金类型.
     * @param marketId
     *            市场id.
     * @return 校验结果.
     */
    List<String> checkBonusRecord(IRequest request, Long periodId, String bonusType, Long marketId);

    /**
     * 支付失败后更新最终奖金状态.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            支付失败的最终奖金集合.
     */
    void updateStatusByPayfall(IRequest request, List<BonusFinal> bonusFinal);

    /**
     * 支付失败后生成retansfer或者Remaining_balance.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinals
     *            支付失败的最终奖金集合.
     * @throws CommMemberException
     *             会员账务处理异常.
     */
    void retransferOrRemain(IRequest request, List<BonusFinal> bonusFinals) throws CommMemberException;

    /**
     * 奖金支付逻辑.
     * 
     * @param request
     *            统一上下文.
     * @param combine
     *            支付奖金条件.
     * @return 返回支付结果.
     * @throws CommMemberException
     *             抛出会员账务异常
     */
    List<String> bonusPay(IRequest request, BonusReleaseCombine combine) throws CommMemberException;

    /**
     * 奖金支付解锁逻辑.
     * 
     * @param request
     *            统一上下文.
     * @param combine
     *            奖金条件.
     * @return 更新结果.
     */
    List<String> unlockBonusFinal(IRequest request, BonusReleaseCombine combine);

    /**
     * 奖金补发逻辑.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            补发奖金记录.
     * @return 返回补发结果.
     */
    List<String> bonusCheckAndReissue(IRequest request, BonusFinal bonusFinal);

    /**
     * 更新最终奖金记录备注.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            奖金记录.
     * @return 返回更新结果数.
     */
    int updateComments(IRequest request, BonusFinal bonusFinal);

}
