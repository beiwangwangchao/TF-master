/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service;

import java.util.List;

import com.lkkhpg.dsis.common.bonus.dto.BonusAdjust;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 奖金调整service.
 * 
 * @author li.peng@hand-china.com
 *
 */
public interface IBonusAdjustService extends ProxySelf<IBonusAdjustService> {

    /**
     * 奖金调整保存.
     * 
     * @param request
     *            上下文请求
     * @param bonusAdjust
     *            奖金调整信息
     * @return 奖金调整信息
     */
    @AuditEntry("BONUS")
    BonusAdjust saveBonusAdjust(IRequest request, @StdWho BonusAdjust bonusAdjust);

    /**
     * 奖金调整删除.
     *
     * @param request
     *            上下文请求
     * @param adjId
     *            奖金调整Id
     * @return 删除条数
     */
    @AuditEntry("BONUS")
    int deleteBonusAdjust(IRequest request, Long adjId);

    /**
     * 奖金调整批量删除.
     * 
     * @param request
     *            上下文请求
     * @param bonusAdjusts
     *            奖金调整列表
     * @return 删除条数
     */
    @AuditEntry("BONUS")
    int delBatchBonusAdjust(IRequest request, List<BonusAdjust> bonusAdjusts);

    /**
     * 奖金调整查询.
     * 
     * @param request
     *            上下文请求
     * @param bonusAdjust
     *            奖金调整信息
     * @param page
     *            页码
     * @param pageSize
     *            页面条数
     * @return 奖金调整列表
     */
    List<BonusAdjust> queryBonusAdjust(IRequest request, BonusAdjust bonusAdjust, int page, int pageSize);
}
