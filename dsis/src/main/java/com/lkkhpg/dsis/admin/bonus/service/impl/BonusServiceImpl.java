/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusService;
import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.mapper.BonusFinalMapper;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.core.IRequest;


/**
 * 奖金service.
 * 
 * @author li.peng@hand-china.com
 *
 */
@Service
public class BonusServiceImpl implements IBonusService {


    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    
    @Autowired
    private BonusFinalMapper bonusFinalMapper;

    @Override
    @Transactional
    public List<SpmPeriod> getPeriod(IRequest request, Long marketId) {
        return self().getPeriod(request, BonusConstants.PEROID_TYPE, marketId);
    }

    @Override
    public List<SpmPeriod> getUsablePeriod(IRequest request, Long marketId) {
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setPeriodType(BonusConstants.PEROID_TYPE);
        if (null != marketId) {
            spmPeriod.setOrgId(marketId);
        } else {
            if (request.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
                spmPeriod.setOrgId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
            }
        }
        spmPeriod.setClosingStatus(BonusConstants.BONUS_CLOSING_STATUS);
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        return spmPeriodMapper.selectUsableSpmPeriod(spmPeriod);
    }

    
    @Override
    @Transactional
    public List<SpmPeriod> getPeriod(IRequest request, String periodType, Long marketId) {
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setPeriodType(periodType);
        if (null != marketId) {
            spmPeriod.setOrgId(marketId);
        } else {
            if (request.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
                spmPeriod.setOrgId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
            }
        }
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        return spmPeriodMapper.selectSpmPeriod(spmPeriod);
    }

    @Override
    public SpmPeriod getLastClosedPeriod(IRequest request, Long marketId) {
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setPeriodType(BonusConstants.PEROID_TYPE);
        if (null != marketId) {
            spmPeriod.setOrgId(marketId);
        } else {
            if (request.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
                spmPeriod.setOrgId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
            }
        }
        spmPeriod.setClosingStatus(SystemProfileConstants.CLOSING_STATUS_Y);
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        return spmPeriodMapper.getCurentClosedPeroid(spmPeriod);
    }

    @Override
    public SpmPeriod getNextOpenPeriod(IRequest request, SpmPeriod period, Long marketId) {
        
        return spmPeriodMapper.queryNextOpenPeriod(period);
    }

    @Override
    public SpmPeriod getPreviousCloseSpmPeriod(IRequest request, Long marketId) {
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setPeriodType(BonusConstants.PEROID_TYPE);
        if (null != marketId) {
            spmPeriod.setOrgId(marketId);
        } else {
            if (request.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
                spmPeriod.setOrgId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
            }
        }
        spmPeriod.setClosingStatus(SystemProfileConstants.CLOSING_STATUS_Y);
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        return spmPeriodMapper.getPreviousCloseSpmPeriod(spmPeriod);
    }

    @Override
    public List<SpmPeriod> getCloseSpmPeriod(IRequest request, Long marketId) {
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setPeriodType(BonusConstants.PEROID_TYPE);
        if (null != marketId) {
            spmPeriod.setOrgId(marketId);
        } else {
            if (request.getAttribute(SystemProfileConstants.MARKET_ID) != null) {
                spmPeriod.setOrgId(Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString()));
            }
        }
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        spmPeriod.setClosingStatus(BonusConstants.BONUS_FLAG_Y);
        return spmPeriodMapper.selectSpmPeriod(spmPeriod);
    }

    @Override
    public SpmPeriod getSpmPeriodByType(IRequest request, String bonusType, String queryType, Long marketId) {
        if (BonusConstants.BONUS_TYPE_LOCAL.equals(bonusType)) {
            SpmPeriod spmPeriod = new SpmPeriod();
            spmPeriod = self().getLastClosedPeriod(request, marketId);
            return spmPeriod;
        } else if (BonusConstants.BONUS_TYPE_INTER.equals(bonusType)) {
            SpmPeriod spmPeriodPre = new SpmPeriod();
            spmPeriodPre = self().getPreviousCloseSpmPeriod(request, marketId);
            SpmPeriod spmPeriodLast = new SpmPeriod();
            spmPeriodLast = self().getLastClosedPeriod(request, marketId);
            BonusFinal bonusTemp = new BonusFinal();
            bonusTemp.setBonusType(bonusType);
//            String marketId = request.getAttribute(SystemProfileConstants.MARKET_ID).toString();
            bonusTemp.setMarketId(marketId);
            // 根据查询类型获取不同逻辑的奖金期间.
            if ("issue".equals(queryType)) {
                bonusTemp.setPeriodId(spmPeriodPre.getPeriodId());
                BonusFinal resultFinal = new BonusFinal();
                resultFinal = bonusFinalMapper.queryCount(bonusTemp);
                if (resultFinal.getAmount().compareTo(new Long(0)) > 0) {
                    return spmPeriodLast;
                } else {
                    return spmPeriodPre;
                }
            } else if ("issueBack".equals(queryType)) {
                bonusTemp.setPeriodId(spmPeriodLast.getPeriodId());
                BonusFinal resultFinal = new BonusFinal();
                resultFinal = bonusFinalMapper.queryCount(bonusTemp);
                if (resultFinal.getAmount().compareTo(new Long(0)) > 0) {
                    return spmPeriodLast;
                } else {
                    return spmPeriodPre;
                }
            } else if ("pay".equals(queryType)) {
                bonusTemp.setPeriodId(spmPeriodPre.getPeriodId());
                bonusTemp.setAutoFaliledFlag(BonusConstants.BONUS_FLAG_Y);
                bonusTemp.setLockFlag(BonusConstants.BONUS_FLAG_Y);
                BonusFinal resultFinal = new BonusFinal();
                resultFinal = bonusFinalMapper.queryCount(bonusTemp);
                if (resultFinal.getAmount().compareTo(new Long(0)) > 0) {
                    return spmPeriodLast;
                } else {
                    return spmPeriodPre;
                }
            } else if ("payBack".equals(queryType)) {
                bonusTemp.setPeriodId(spmPeriodLast.getPeriodId());
                bonusTemp.setAutoFaliledFlag(BonusConstants.BONUS_FLAG_Y);
                BonusFinal resultFinal = new BonusFinal();
                resultFinal = bonusFinalMapper.queryCount(bonusTemp);
                if (resultFinal.getAmount().compareTo(new Long(0)) > 0) {
                    return spmPeriodLast;
                } else {
                    return spmPeriodPre;
                }
            } else {
                return spmPeriodPre;
            } 
            
        } else {
            return null;
        }
        
    }

    
}
