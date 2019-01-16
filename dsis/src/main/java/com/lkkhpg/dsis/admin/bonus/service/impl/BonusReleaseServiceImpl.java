/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusFinalService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusReleaseService;
import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusRelease;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;
import com.lkkhpg.dsis.common.bonus.mapper.BonusFinalMapper;
import com.lkkhpg.dsis.common.bonus.mapper.BonusReleaseMapper;
import com.lkkhpg.dsis.common.bonus.mapper.BonusRetransferMapper;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.mapper.MemWithdrawMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 奖金发放实现类.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Service
@Transactional
public class BonusReleaseServiceImpl implements IBonusReleaseService {

    @Autowired
    private BonusReleaseMapper bonusReleaseMapper;

    @Autowired
    private BonusRetransferMapper bonusRetransferMapper;

    @Autowired
    private MemWithdrawMapper withdrawMapper;

    @Autowired
    private IBonusFinalService bonusFinalService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private BonusFinalMapper bonusFinalMapper;

    @Override
    public List<BonusRelease> queryBonusRelease(IRequest request, BonusRelease bonuRelease, int page, int pagesize) {
        if (-1 != pagesize) {
            PageHelper.startPage(page, pagesize);
        }
        return bonusReleaseMapper.selectBonusRelease(bonuRelease);
    }

    @Override
    public int countBonusRelease(IRequest request, BonusRelease bonuRelease) {
        return bonusReleaseMapper.countBonusRelease(bonuRelease);
    }

    @Override
    @Transactional
    public boolean exportBonus(IRequest request, BonusReleaseCombine bonusReleaseCombine) throws CommMemberException {
        self().createFinalBonus(request, bonusReleaseCombine);
        return true;
    }

    @Override
    @Transactional
    public void createFinalBonus(IRequest request, BonusReleaseCombine bonusReleaseCombine) throws CommMemberException {
        bonusFinalService.insertFinalBonus(request, bonusReleaseCombine);
        List<BonusRetransfer> retransfers = bonusReleaseCombine.getRetransfers();
        // 更新retransfers的状态并写入最终奖金编号
        if (retransfers != null && retransfers.size() > 0) {
            bonusReleaseCombine.setRetransferStatus(BonusConstants.RETRANSFER_STATUS);
            int size = retransfers.size() % BonusConstants.BONUS_LIST_SIZE != 0
                    ? retransfers.size() / BonusConstants.BONUS_LIST_SIZE + 1
                    : retransfers.size() / BonusConstants.BONUS_LIST_SIZE;
            for (int i = 0; i < size; i++) {
                List<BonusRetransfer> temp = null;
                if (((i + 1) * BonusConstants.BONUS_LIST_SIZE) > retransfers.size()) {
                    temp = retransfers.subList(i * BonusConstants.BONUS_LIST_SIZE, retransfers.size());
                } else {
                    temp = retransfers.subList(i * BonusConstants.BONUS_LIST_SIZE,
                            (i + 1) * BonusConstants.BONUS_LIST_SIZE);
                }
                bonusReleaseCombine.setRetransfers(temp);
                bonusRetransferMapper.updateRetransferStatus(bonusReleaseCombine);
            }
            bonusReleaseCombine.setRetransfers(retransfers);
        }
//        // 更新冲销结余状态
//        List<MemWithdraw> memWithdraws = bonusReleaseCombine.getMemWithdraws();
//        if (memWithdraws != null && memWithdraws.size() > 0) {
//            withdrawMapper.updateWithdrawStatus(memWithdraws, BonusConstants.WITHDRAWS_STATUS, bonusReleaseCombine);
//        }
        // 变更月度奖金处理状态
        bonusReleaseCombine.setReleaseStatus(BonusConstants.BONUS_RELEASE_STATUS_Y);
        bonusReleaseMapper.updateReleaseStatus(bonusReleaseCombine);
        // 自动失败状态更新状态
        self().autoFailBonusFinal(request, bonusReleaseCombine);
    }

    @Override
    @Transactional
    public List<MemWithdraw> getMemWithdraw(IRequest request, MemWithdraw memWithdraw) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("status", memWithdraw.getStatus());
        queryMap.put("marketId", memWithdraw.getMarketId());
        return withdrawMapper.queryWithdraws(queryMap);
    }

    @Override
    @Transactional
    public void autoFailBonusFinal(IRequest request, BonusReleaseCombine releaseCombine) throws CommMemberException {
        // createFinalBonus(request, releaseCombine);
        List<String> list = paramService.getMarketParamValues(request, SystemProfileConstants.BONUS_RELEASE_THRESHOLD,
                releaseCombine.getMarketId());
        BonusFinal bfl = new BonusFinal();
        bfl.setPeriodId(releaseCombine.getPeriodId());
        bfl.setMarketId(releaseCombine.getMarketId());
        bfl.setBonusType(releaseCombine.getBonusType());
        List<BonusFinal> bonusFinal;
        if (!list.isEmpty()) {
            BigDecimal bd = new BigDecimal(list.get(0));
            bfl.setDeliverAmt(bd);
            bonusFinal = bonusFinalMapper.autoFailBonusFinal(bfl);
        } else {
            bonusFinal = bonusFinalMapper.autoFailBonusFinal(bfl);
        }
        bonusFinalService.updateStatusByPayfall(request, bonusFinal);
    }

    @Override
    @Transactional
    public void rollbackRelease(IRequest request, SpmPeriod spmPeriod, String bonusType) {
        Long marketId = spmPeriod.getOrgId();
        // 查询符合条件的最终奖金记录
        BonusFinal bonusFinal = new BonusFinal();
        bonusFinal.setPeriodId(spmPeriod.getPeriodId());
        bonusFinal.setBonusType(bonusType);
        bonusFinal.setMarketId(marketId);
        List<BonusFinal> finalBonus = bonusFinalService.queryBonusFinal(request, bonusFinal, 1, -1);
        // 回退retransfers，去除最终奖金id，修改状态为NEW
        int size = finalBonus.size() % BonusConstants.BONUS_LIST_SIZE != 0
                ? finalBonus.size() / BonusConstants.BONUS_LIST_SIZE + 1
                : finalBonus.size() / BonusConstants.BONUS_LIST_SIZE;
        for (int i = 0; i < size; i++) {
            List<BonusFinal> temp = null;
            if (((i + 1) * BonusConstants.BONUS_LIST_SIZE) > finalBonus.size()) {
                temp = finalBonus.subList(i * BonusConstants.BONUS_LIST_SIZE, finalBonus.size());
            } else {
                temp = finalBonus.subList(i * BonusConstants.BONUS_LIST_SIZE,
                        (i + 1) * BonusConstants.BONUS_LIST_SIZE);
            }
            bonusRetransferMapper.rollback(temp, BonusConstants.BONUS_RETRANSFER_STATUS_NEW);
        }
        // 回退冲销节余数据，修改状态为NEW,去除source_type source_key 取消
//        withdrawMapper.rollbackWithdraw(finalBonus, BonusConstants.BONUS_RETRANSFER_STATUS_NEW);
        // 回退最终奖金记录，删除最终奖金记录
        for (BonusFinal temp : finalBonus) {
            bonusFinalMapper.deleteByPrimaryKey(temp.getBonusId());
        }
        // 根据奖金期间，奖金类型修改月度奖金记录为N(未处理)
        BonusReleaseCombine combine = new BonusReleaseCombine();
        combine.setMarketId(marketId);
//        combine.setMarketId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
        combine.setPeriodId(spmPeriod.getPeriodId());
        combine.setBonusType(bonusType);
        combine.setReleaseStatus(BonusConstants.BONUS_RELEASE_STATUS_N);
        bonusReleaseMapper.updateReleaseStatus(combine);
    }

    @Override
    public List<BonusRelease> queryYearRelease(IRequest request, BonusRelease bonusRelease, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return bonusReleaseMapper.queryYearRelease(bonusRelease);
    }

    @Override
    public List<BonusRelease> queryMonthRelease(IRequest request, BonusRelease bonusRelease, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return bonusReleaseMapper.queryMonthTWRelease(bonusRelease);
    }

    @Override
    public List<BonusRelease> queryDetailByFinal(IRequest request, BonusFinal bonusFinal) {
        return bonusReleaseMapper.queryDetailByFinal(bonusFinal);
    }
}
