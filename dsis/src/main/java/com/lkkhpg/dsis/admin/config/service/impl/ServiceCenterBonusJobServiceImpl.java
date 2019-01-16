/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.config.service.IServiceCenterBonusJobService;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.bonus.dto.BonusAdjust;
import com.lkkhpg.dsis.common.bonus.mapper.BonusAdjustMapper;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenter;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmServiceCenterMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * Service Center 奖金计算Service实现类.
 * 
 * @author houmin
 *
 */
@Service
@Transactional
public class ServiceCenterBonusJobServiceImpl implements IServiceCenterBonusJobService {

    private Logger logger = LoggerFactory.getLogger(ServiceCenterBonusJobServiceImpl.class);

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private BonusAdjustMapper bonusAdjustMapper;
    @Autowired
    private IDocSequenceService docSequenceService;
    @Autowired
    private SpmServiceCenterMapper spmServiceCenterMapper;
    @Autowired
    private IParamService paramService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void bonusCalculationOfSCB() {
        SimpleDateFormat sdf = new SimpleDateFormat(BonusConstants.BONUS_PERIOD_NAME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String periodName = sdf.format(calendar.getTime());
        Long serviceCenterId;
        SpmServiceCenter spmServiceCenter;
        BigDecimal pvSum = BigDecimal.ZERO;
        // Service Center月度PV上限
        BigDecimal pvMax = BigDecimal.ZERO;
        // Service Center奖金比例
        BigDecimal bonusPercentage = BigDecimal.ZERO;
        // Service Center 奖金系数
        BigDecimal bonusRatio = BigDecimal.ZERO;
        String currencyCode = "";
        // 奖金调整金额
        BigDecimal adjAmt = BigDecimal.ZERO;
        DsisServiceRequest request = new DsisServiceRequest();
        // 奖金月份
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        spmPeriod.setPeriodName(periodName);
        spmPeriod.setClosingStatus(BonusConstants.BONUS_CLOSING_STATUS);
        List<SpmPeriod> spmPeriods = spmPeriodMapper.selectSpmPeriod(spmPeriod);
        if (spmPeriods != null && !spmPeriods.isEmpty()) {
            for (SpmPeriod obj : spmPeriods) {
                List<SalesOrder> salesOrders = salesOrderMapper.getPvSum(periodName, obj.getOrgId());
                for (SalesOrder salesOrder : salesOrders) {
                    pvSum = salesOrder.getPvSum();
                    serviceCenterId = salesOrder.getServiceCenterId();
                    spmServiceCenter = spmServiceCenterMapper.selectById(serviceCenterId);
                    if (logger.isDebugEnabled()) {
                        if (spmServiceCenter == null) {
                            logger.debug("Not found the {} service center.", serviceCenterId);
                        }
                    }
                    // 组织参数-Service Center奖金比例(%).
                    List<String> bonusPercentageList = paramService.getSalesParamValues(request,
                            BonusConstants.SPM_BONUS_PERCENTAGE_SERVICE_CENTER, spmServiceCenter.getSalesOrgId());
                    if (logger.isDebugEnabled()) {
                        if (bonusPercentageList == null || bonusPercentageList.isEmpty()) {
                            logger.debug("Service Center Bonus Percentage Param not found! ParamName : {}",
                                    new Object[] { BonusConstants.SPM_BONUS_PERCENTAGE_SERVICE_CENTER });
                        }
                    }
                    BigDecimal bonusPercentageValue = new BigDecimal(bonusPercentageList.get(0));
                    bonusPercentage = bonusPercentageValue.divide(new BigDecimal(BonusConstants.SPM_BONUS_PERCENTAGE));
                    // 组织参数-Service Center月度PV上限.
                    List<String> pvMaxList = paramService.getSalesParamValues(request,
                            BonusConstants.SPM_MAX_PV_SERVICE_CENTER, spmServiceCenter.getSalesOrgId());
                    if (logger.isDebugEnabled()) {
                        if (pvMaxList == null || pvMaxList.isEmpty()) {
                            logger.debug("Service Center PVSum Limit Param not found! ParamName : {}",
                                    new Object[] { BonusConstants.SPM_MAX_PV_SERVICE_CENTER });
                        }
                    }
                    pvMax = new BigDecimal(pvMaxList.get(0));
                    // 组织参数-Service Center奖金系数.
                    List<String> bonusRatioList = paramService.getSalesParamValues(request,
                            BonusConstants.SPM_BONUS_RATIO_SERVICE_CENTER, spmServiceCenter.getSalesOrgId());
                    if (logger.isDebugEnabled()) {
                        if (bonusRatioList == null || bonusRatioList.isEmpty()) {
                            logger.debug("Service Center Bonus Ratio Param not found! ParamName : {}",
                                    new Object[] { BonusConstants.SPM_BONUS_RATIO_SERVICE_CENTER });
                        }
                    }
                    bonusRatio = new BigDecimal(bonusRatioList.get(0));
                    // 组织参数-本位币.
                    List<String> currencyCodeList = paramService.getSalesParamValues(request,
                            SystemProfileConstants.SPM_CURRENCY, spmServiceCenter.getSalesOrgId());
                    if (logger.isDebugEnabled()) {
                        if (currencyCodeList == null || currencyCodeList.isEmpty()) {
                            logger.debug("Currency Param not found! ParamName : {}",
                                    new Object[] { SystemProfileConstants.SPM_CURRENCY });
                        }
                    }
                    currencyCode = currencyCodeList.get(0);
                    if (pvSum.compareTo(pvMax) > 0) {
                        adjAmt = pvSum.multiply(bonusPercentage).multiply(bonusRatio);
                        // 组装奖金调整Dto
                        BonusAdjust bonusAdjust = new BonusAdjust();
                        bonusAdjust.setSyncFlag(BonusConstants.BONUS_ADJUST_SYNC_FLAG_DEDAULT);
                        DocSequence docSequence = new DocSequence(BonusConstants.BONUS_ADJUST,
                                BonusConstants.BONUS_ADJ_CODE_BEGIN, null, null, null, null);
                        request.setAccountId(-1L);
                        bonusAdjust.setAdjCode(docSequenceService.getSequence(request, docSequence,
                                BonusConstants.BONUS_ADJ_CODE_BEGIN, BonusConstants.BONUS_ADJ_CODE_LENGTH, null));
                        bonusAdjust.setAdjStatus(BonusConstants.BONUS_ADJUST_STATUS_NEW);
                        bonusAdjust.setPeriodId(obj.getPeriodId());
                        bonusAdjust.setMarketId(obj.getOrgId());
                        bonusAdjust.setCurrencyCode(currencyCode);
                        bonusAdjust.setMemberId(spmServiceCenter.getChargeMemberId());
                        bonusAdjust.setAdjReason(BonusConstants.BONUS_ADJUST_REASON_SCB);
                        bonusAdjust.setAdjAmt(adjAmt);
                        bonusAdjust.setAdjType(BonusConstants.BONUS_ADJUST_TYPE_ADD);
                        bonusAdjust.setAdjCategory(BonusConstants.BONUS_ADJUST_CATEGORY_R97);

                        if (bonusAdjustMapper.insertSelective(bonusAdjust) > 0) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Service Center Bonus Job execute success! ServiceCenterId : {}",
                                        new Object[] { serviceCenterId });
                            }
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Service Center Bonus Job execute error! ServiceCenterId : {}",
                                        new Object[] { serviceCenterId });
                            }
                        }
                    }
                }
            }
        }
    }
}

