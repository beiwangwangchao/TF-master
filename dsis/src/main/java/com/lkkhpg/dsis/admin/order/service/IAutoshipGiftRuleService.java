/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service;

import java.util.List;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.AutoshipGiftRule;
import com.lkkhpg.dsis.common.order.dto.AutoshipGifts;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 自动订货赠品规则service.
 * 
 * @author hanrui.huang
 *
 */
public interface IAutoshipGiftRuleService extends ProxySelf<IAutoshipGiftRuleService> {

    /**
     * 查询自动订货赠品规则.
     * 
     * @param request
     *            请求上下文
     * @param autoshipGiftRule
     *            自动订货赠品规则DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 自动订货赠品规则List
     */
    List<AutoshipGiftRule> queryAutoshipGiftRules(IRequest request, AutoshipGiftRule autoshipGiftRule, int page,
            int pagesize);

    /**
     * 保存自动订货赠品规则.
     * 
     * @param request
     *            请求上下文
     * @param autoshipGiftRules
     *            自动订货赠品规则DTO
     * @return 自动订货赠品规则List
     * @throws CommOrderException
     *             系统配置统一异常
     */
    List<AutoshipGiftRule> saveAutoshipGiftRules(IRequest request, @StdWho List<AutoshipGiftRule> autoshipGiftRules)
            throws CommOrderException;

    /**
     * 查询自动订货单赠品.
     * 
     * @param request
     *            请求上下文
     * @param autoshipGifts
     *            自动订货单赠品DTO
     * @param page
     *            页
     * @param pagesize
     *            页大小
     * @return 自动订货单赠品List
     */
    List<AutoshipGifts> queryAutoshipGifts(IRequest request, AutoshipGifts autoshipGifts, int page, int pagesize);

    /**
     * 保存自动订货单赠品.
     * 
     * @param autoshipGifts
     *            自动订货单赠品DTO
     * @param ruleId
     *            自动订货赠品规则ID
     */
    void processAutoshipGifts(@StdWho List<AutoshipGifts> autoshipGifts, Long ruleId);

    /**
     * 根据市场ID获取唯一条启用的赠品规则.
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织id
     * @return 赠品规则
     */
    AutoshipGiftRule queryAutoshipGiftRuleBySalesOrgId(IRequest request, Long salesOrgId);
}
