/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IIpointBonusService;
import com.lkkhpg.dsis.common.bonus.dto.BonusAdjust;
import com.lkkhpg.dsis.common.bonus.dto.IpointBonus;
import com.lkkhpg.dsis.common.bonus.mapper.BonusAdjustMapper;
import com.lkkhpg.dsis.common.bonus.mapper.IpointBonusMapper;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * ipoint奖金记录service实现类.
 * 
 * @author wangc
 *
 */
@Service
@Transactional
public class IpointBonusServiceImpl implements IIpointBonusService {

    private final Logger logger = LoggerFactory.getLogger(IpointBonusServiceImpl.class);

    @Autowired
    private IpointBonusMapper ipointBonusMapper;
    @Autowired
    private BonusAdjustMapper bonusAdjustMapper;
    @Autowired
    private IParamService iparamService;
    @Autowired
    private IDocSequenceService docSequenceService;

    public static final String DOC_TYPE = "BONUS_ADJUST";

    public static final String DOC_PREFIX = "BA";

    public static final Long INIT_SEQUENCE = 10000L;

    @Override
    public List<IpointBonus> queryIpointBonuses(IRequest request, IpointBonus ipointBonus, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        String status = ipointBonus.getStatus();
        List<String> statusList = new ArrayList<String>();
        if (status != null) {
            Collections.addAll(statusList, status.split(";"));
        } else {
            statusList = null;
        }
        return ipointBonusMapper.queryIpointBonus(ipointBonus, statusList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointBonus> submitIpointBonuses(IRequest createRequestContext, List<IpointBonus> ipointBonus)
            throws CommBonusException {
        for (IpointBonus ib : ipointBonus) {
            String status = ib.getStatus();
            if (!BonusConstants.IPOINT_BONUS_STATUS_REJECT.equals(status)) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_IPOINT_BONUS_SUBMIT_EXCEPTION, null);
            }
            ipointBonusMapper.submitIpointBonus(ib);
        }
        return ipointBonus;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointBonus> approveIpointBonuses(IRequest request, List<IpointBonus> ipointBonus)
            throws CommBonusException {
        for (IpointBonus ib : ipointBonus) {
            String status = ib.getStatus();
            if (!BonusConstants.IPOINT_BONUS_STATUS_ALING.equals(status)) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_IPOINT_BONUS_APPROVE_EXCEPTION, null);
            }
            ipointBonusMapper.approveIpointBonus(ib);
            // 生成奖金调整
            BonusAdjust ba = new BonusAdjust();
            ba.setAdjCode(docSequenceService.getSequence(request,
                    new DocSequence(DOC_TYPE, DOC_PREFIX, null, null, null, null), DOC_PREFIX,
                    BonusConstants.CODE_LENGTH_FIVE, INIT_SEQUENCE));
            ba.setMarketId(ib.getMarketId());
            ba.setAdjStatus(BonusConstants.BONUS_ADJUSTMENT_STATUS_NEW);
            ba.setPeriodId(ib.getPeriodId());
            ba.setMemberId(ib.getMemberId());
            ba.setAdjReason(BonusConstants.BONUS_ADJUSTMENT_REASON_IPOINT);
            ba.setAdjAmt(ib.getAmount());
            ba.setAdjType(BonusConstants.BONUS_ADJUSTMENT_TYPE_ADD);
            ba.setAdjCategory(BonusConstants.BONUS_ADJUST_CATEGORY_R97);
            ba.setDescription(ib.getRemark());
            ba.setSyncFlag(BonusConstants.BONUS_ADJUST_SYNC_FLAG_DEDAULT);
            List<String> currencyCode = iparamService.getParamValues(request, "SPM.CURRENCY", "SALES");
            ba.setCurrencyCode(currencyCode.get(0));
            bonusAdjustMapper.insertSelective(ba);
        }
        return ipointBonus;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointBonus> rejectIpointBonuses(IRequest createRequestContext, List<IpointBonus> ipointBonus)
            throws CommBonusException {
        for (IpointBonus ib : ipointBonus) {
            String status = ib.getStatus();
            if (!BonusConstants.IPOINT_BONUS_STATUS_ALING.equals(status)) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_IPOINT_BONUS_REJECT_EXCEPTION, null);
            }
            ipointBonusMapper.rejectIpointBonus(ib);
        }
        return ipointBonus;
    }
}
