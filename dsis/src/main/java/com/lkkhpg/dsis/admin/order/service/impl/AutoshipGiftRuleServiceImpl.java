/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.order.service.IAutoshipGiftRuleService;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.AutoshipGiftRule;
import com.lkkhpg.dsis.common.order.dto.AutoshipGifts;
import com.lkkhpg.dsis.common.order.mapper.AutoshipGiftRuleMapper;
import com.lkkhpg.dsis.common.order.mapper.AutoshipGiftsMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 自动订货赠品规则serviceImpl.
 * 
 * @author hanrui.huang
 *
 */
@Service
@Transactional
public class AutoshipGiftRuleServiceImpl implements IAutoshipGiftRuleService {

    private Logger logger = LoggerFactory.getLogger(AutoshipGiftRuleServiceImpl.class);

    @Autowired
    private AutoshipGiftRuleMapper autoshipGiftRuleMapper;

    @Autowired
    private AutoshipGiftsMapper autoshipGiftsMapper;

    @Override
    public List<AutoshipGiftRule> queryAutoshipGiftRules(IRequest request, AutoshipGiftRule autoshipGiftRule, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        return autoshipGiftRuleMapper.queryAutoshipGiftRule(autoshipGiftRule);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<AutoshipGiftRule> saveAutoshipGiftRules(IRequest request,
            @StdWho List<AutoshipGiftRule> autoshipGiftRules) throws CommOrderException {
        for (AutoshipGiftRule autoshipGiftRule : autoshipGiftRules) {
            if (DTOStatus.ADD.equals(autoshipGiftRule.get__status())) {
                AutoshipGiftRule rule = new AutoshipGiftRule();
                rule.setRuleCode(autoshipGiftRule.getRuleCode());
                AutoshipGiftRule rule2 = new AutoshipGiftRule();
                rule2.setRuleName(autoshipGiftRule.getRuleName());
                if (autoshipGiftRuleMapper.queryAutoshipGiftRuleCount(rule) > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("msg_error_autoshipgiftrulr_code_unique: {}",
                                new Object[] { autoshipGiftRule.getRuleCode() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AUTOSHIPGIFTRULR_CODE_UNIQUE,
                            new Object[] { autoshipGiftRule.getRuleCode() });
                } else if (autoshipGiftRuleMapper.queryAutoshipGiftRuleCount(rule2) > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("msg_error_autoshipgiftrulr_name_unique: {}",
                                new Object[] { autoshipGiftRule.getRuleName() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AUTOSHIPGIFTRULR_NAME_UNIQUE,
                            new Object[] { autoshipGiftRule.getRuleName() });
                } else {
                    // 同一个市场下只能有一个 启用的规则吗
                    AutoshipGiftRule rule3 = new AutoshipGiftRule();
                    rule3.setStatus(SystemProfileConstants.YES);
                    List<AutoshipGiftRule> AutoshipGiftRules = autoshipGiftRuleMapper.queryAutoshipGiftRule(rule3);
                    if (SystemProfileConstants.YES.equals(autoshipGiftRule.getStatus())) {
                        if (!AutoshipGiftRules.isEmpty()
                                && !AutoshipGiftRules.get(0).getRuleCode().equals(autoshipGiftRule.getRuleCode())) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("msg_error_autoshipgiftrulr_status_y_unique: {}",
                                        new Object[] { SystemProfileConstants.YES });
                            }
                            throw new CommOrderException(
                                    CommOrderException.MSG_ERROR_OM_AUTOSHIPGIFTRULR_STATUS_Y_UNIQUE,
                                    new Object[] { SystemProfileConstants.YES });
                        } else {
                            autoshipGiftRuleMapper.insert(autoshipGiftRule);
                            self().processAutoshipGifts(autoshipGiftRule.getAutoshipGifts(),
                                    autoshipGiftRule.getRuleId());
                        }
                    } else {
                        autoshipGiftRuleMapper.insert(autoshipGiftRule);
                        self().processAutoshipGifts(autoshipGiftRule.getAutoshipGifts(), autoshipGiftRule.getRuleId());
                    }
                }
            } else if (DTOStatus.UPDATE.equals(autoshipGiftRule.get__status())) {
                AutoshipGiftRule rule = new AutoshipGiftRule();
                rule.setRuleCode(autoshipGiftRule.getRuleCode());
                rule.setRuleName(autoshipGiftRule.getRuleName());
                AutoshipGiftRule rule2 = new AutoshipGiftRule();
                rule2.setRuleName(autoshipGiftRule.getRuleName());
                if (autoshipGiftRuleMapper.queryAutoshipGiftRuleCount(rule) == 0
                        && autoshipGiftRuleMapper.queryAutoshipGiftRuleCount(rule2) > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("msg_error_autoshipgiftrulr_name_unique: {}",
                                new Object[] { autoshipGiftRule.getRuleName() });
                    }
                    throw new CommOrderException(CommOrderException.MSG_ERROR_OM_AUTOSHIPGIFTRULR_NAME_UNIQUE,
                            new Object[] { autoshipGiftRule.getRuleName() });
                } else {
                    // 同一个市场下只能有一个 启用的规则吗
                    AutoshipGiftRule rule3 = new AutoshipGiftRule();
                    rule3.setStatus(SystemProfileConstants.YES);
                    List<AutoshipGiftRule> AutoshipGiftRules = autoshipGiftRuleMapper.queryAutoshipGiftRule(rule3);
                    // 首先判断是不是启动
                    if (SystemProfileConstants.YES.equals(autoshipGiftRule.getStatus())) {
                        if (!AutoshipGiftRules.isEmpty()
                                && !AutoshipGiftRules.get(0).getRuleCode().equals(autoshipGiftRule.getRuleCode())) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("msg_error_autoshipgiftrulr_status_y_unique: {}",
                                        new Object[] { SystemProfileConstants.YES });
                            }
                            throw new CommOrderException(
                                    CommOrderException.MSG_ERROR_OM_AUTOSHIPGIFTRULR_STATUS_Y_UNIQUE,
                                    new Object[] { SystemProfileConstants.YES });
                        } else {
                            autoshipGiftRuleMapper.updateByPrimaryKeySelective(autoshipGiftRule);
                            self().processAutoshipGifts(autoshipGiftRule.getAutoshipGifts(),
                                    autoshipGiftRule.getRuleId());
                        }
                    } else {
                        autoshipGiftRuleMapper.updateByPrimaryKeySelective(autoshipGiftRule);
                        self().processAutoshipGifts(autoshipGiftRule.getAutoshipGifts(), autoshipGiftRule.getRuleId());
                    }
                }
            } else if (DTOStatus.DELETE.equals(autoshipGiftRule.get__status())) {
                autoshipGiftRuleMapper.deleteByPrimaryKey(autoshipGiftRule.getRuleId());
            }
        }
        return autoshipGiftRules;
    }

    @Override
    public void processAutoshipGifts(@StdWho List<AutoshipGifts> autoshipGifts, Long ruleId) {
        for (AutoshipGifts gifts : autoshipGifts) {
            if (DTOStatus.ADD.equals(gifts.get__status())) {
                gifts.setRuleId(ruleId);
                autoshipGiftsMapper.insert(gifts);
            } else if (DTOStatus.UPDATE.equals(gifts.get__status())) {
                AutoshipGifts gifts2 = autoshipGiftsMapper.selectByPrimaryKey(gifts.getGiftId());
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                Calendar c = Calendar.getInstance();
                c.setTime(gifts.getEndActiveDate());
                c.add(Calendar.DATE, +1);
                String endActiveDate = df.format(c.getTime());
                String endActiveDate2 = df.format(gifts2.getEndActiveDate());
                if (gifts2 != null && gifts2.getEndActiveDate() != null && endActiveDate.equals(endActiveDate2)) {
                    c.setTime(gifts2.getEndActiveDate());
                    c.add(Calendar.DATE, -1);
                    gifts.setEndActiveDate(c.getTime());
                }
                autoshipGiftsMapper.updateByPrimaryKeySelective(gifts);
            } else if (DTOStatus.DELETE.equals(gifts.get__status())) {
                autoshipGiftsMapper.deleteByPrimaryKey(gifts.getGiftId());
            }
        }
    }

    @Override
    public List<AutoshipGifts> queryAutoshipGifts(IRequest request, @StdWho AutoshipGifts autoshipGifts, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        return autoshipGiftsMapper.queryAutoshipGifts(autoshipGifts);
    }

    @Override
    public AutoshipGiftRule queryAutoshipGiftRuleBySalesOrgId(IRequest request, Long salesOrgId) {
        // TODO Auto-generated method stub
        return autoshipGiftRuleMapper.queryAutoshipGiftRuleBySalesOrgId(salesOrgId);

    }

}
