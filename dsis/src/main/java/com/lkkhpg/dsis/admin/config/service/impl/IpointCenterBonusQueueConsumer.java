/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.bonus.dto.IpointBonus;
import com.lkkhpg.dsis.common.bonus.mapper.IpointBonusMapper;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.IManualMessageService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.common.system.dto.SysMsMessage;
import com.lkkhpg.dsis.common.system.dto.SysMsMessageAssign;
import com.lkkhpg.dsis.common.user.dto.IpointUser;
import com.lkkhpg.dsis.common.user.mapper.IpointUserMapper;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.message.IMessageConsumer;
import com.lkkhpg.dsis.platform.message.QueueMonitor;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * IPoint Center 奖金记录生成实现类(关闭奖金期间后执行).
 * 
 * @author houmin
 *
 */
@QueueMonitor(queue = "BIZ:CAL_IC_BONUS")
public class IpointCenterBonusQueueConsumer implements IMessageConsumer<SpmPeriod> {

    private Logger logger = LoggerFactory.getLogger(IpointCenterBonusQueueConsumer.class);

    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private IParamService paramService;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private IDocSequenceService docSequenceService;
    @Autowired
    private IpointBonusMapper ipointBonusMapper;
    @Autowired
    private IpointUserMapper ipointUserMapper;
    @Autowired
    private IManualMessageService manualMessageService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void onMessage(SpmPeriod spmPeriod_, String pattern) {
        logger.info("Starting do Ipoint Center Bonus adjust ...");
        DsisServiceRequest request = new DsisServiceRequest();
        // 奖金月份
        String periodName = spmPeriod_.getPeriodName();
        // 当前市场Id
        Long marketId = spmPeriod_.getOrgId();
        Long salesOrgId;
        // 汇总的销售额,iPoint Center月度销售额上限
        BigDecimal actrualPayAmtSum, maxSalesAmt;
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setOrgType(BonusConstants.BONUS_ORG_TYPE);
        spmPeriod.setOrgId(marketId);
        spmPeriod.setPeriodName(periodName);
        spmPeriod.setClosingStatus(BonusConstants.BONUS_FLAG_Y);
        // 根据条件：当前市场，奖金月份，已关闭的奖金月份
        List<SpmPeriod> spmPeriods = spmPeriodMapper.selectSpmPeriod(spmPeriod);
        if (spmPeriods != null && !spmPeriods.isEmpty() && spmPeriods.size() > 0) {
            for (SpmPeriod obj : spmPeriods) {
                // 获取满足条件的销售订单,按照不同的销售区域和创建人，汇总销售额
                List<SalesOrder> salesOrders = salesOrderMapper.getActrualPayAmtSum(periodName, marketId);
                for (SalesOrder salesOrder : salesOrders) {
                    salesOrgId = salesOrder.getSalesOrgId();
                    actrualPayAmtSum = salesOrder.getActrualPayAmtSum();
                    maxSalesAmt = new BigDecimal(
                            paramService.getParamValues(request, BonusConstants.SPM_MAX_SALES_IPOINT_CENTER,
                                    SystemProfileConstants.ORG_TYPE_SALES, salesOrgId).get(0));
                    if (actrualPayAmtSum.compareTo(maxSalesAmt) > 0) {
                        if (createIpointBonus(request, periodName, salesOrder, obj.getPeriodId()) > 0) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Sending message ... ");
                            }
                            // 发送站内信
                            if (sendMessage(request, salesOrgId, periodName)) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Send message is ended!");
                                }
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Send message is error!");
                                }
                            }
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Create iPoint Center record failed.", salesOrder.getSalesOrgId());
                            }
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("ActrualPayAmtSum is larger than maxSalesAmtSum.(ActrualPayAmtSum : {}, "
                                    + "MaxSalesAmtSum : {})", new Object[] { actrualPayAmtSum, maxSalesAmt });
                        }
                    }
                }
            }
        }
        logger.info("Do Ipoint Center Bonus adjust ended.");
    }

    /**
     * 创建iPoint Center奖金记录.
     * 
     * @param request
     *            统一上下文
     * @param periodName
     *            月份名称
     * @param salesOrder
     *            销售订单对象
     * @return 生成的记录数
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private int createIpointBonus(DsisServiceRequest request, String periodName, SalesOrder salesOrder, Long periodId) {
        Long salesOrgId = salesOrder.getSalesOrgId();
        BigDecimal pvSum = salesOrder.getPvSum();
        BigDecimal bonusPercentage = new BigDecimal(
                paramService.getParamValues(request, BonusConstants.SPM_BONUS_PERCENTAGE_IPOINT_CENTER,
                        SystemProfileConstants.ORG_TYPE_SALES, salesOrgId).get(0))
                                .divide(new BigDecimal(BonusConstants.SPM_BONUS_PERCENTAGE));
        BigDecimal bonusRatio = new BigDecimal(
                paramService.getParamValues(request, BonusConstants.SPM_BONUS_RATIO_SIPOINT_CENTER,
                        SystemProfileConstants.ORG_TYPE_SALES, salesOrgId).get(0));
        // 计算奖金
        BigDecimal iPointBonusAmt = pvSum.multiply(bonusPercentage).multiply(bonusRatio);
        // 生成iPoint奖金记录
        IpointBonus iPointBonus = new IpointBonus();
        iPointBonus.setSalesOrgId(salesOrgId);
        DocSequence docSequence = new DocSequence(BonusConstants.BONUS_IPOINT, BonusConstants.IPOINT_BONUS_CODE_BEGIN,
                null, null, null, null);
        request.setAccountId(-1L);
        iPointBonus.setBonusNumber(
                docSequenceService.getSequence(request, docSequence, BonusConstants.IPOINT_BONUS_CODE_BEGIN,
                        BonusConstants.IPOINT_BONUS_CODE_LENGTH, BonusConstants.IPOINT_BONUS_CODE_INIT_VALUE));
        iPointBonus.setBonusType(BonusConstants.BONUS_ADJUST_REASON_IB);
        iPointBonus.setAdjType(BonusConstants.BONUS_ADJUST_TYPE_ADD);
        // 负责会员-订单表—>创建人，根据创建人取其用户关联的会员账号
        Long createUserId = salesOrder.getCreateUserId();
        IpointUser ipointUser = new IpointUser();
        ipointUser.setAccountId(createUserId);
        List<IpointUser> lists = ipointUserMapper.selectIpointUsers(ipointUser);
        if (lists == null || lists.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The [{}] accountId not found memberId.", createUserId);
            }
            return 0;
        }
        iPointBonus.setMemberId(lists.get(0).getMemberId());
        iPointBonus.setPeriodId(periodId);
        iPointBonus.setStatus(BonusConstants.IC_IPOINT_BONUS_STATUS_ALING);
        iPointBonus.setAmount(iPointBonusAmt);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        iPointBonus.setApprovedDate(java.sql.Date.valueOf(sdf.format(new Date())));

        return ipointBonusMapper.insertSelective(iPointBonus);
    }

    /**
     * 给iPoint奖金记录审核人发送站内信.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织Id
     * @param periodName
     *            奖金月份
     * @return true-发送成功；false-发送失败
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private boolean sendMessage(DsisServiceRequest request, Long salesOrgId, String periodName) {
        // 根据销售组织获取对应市场
        SpmSalesOrganization spmSalesOrganization = spmSalesOrganizationMapper.selectByPrimaryKey(salesOrgId);
        if (spmSalesOrganization == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("SalesOrgId {} not found the marketId!", new Object[] { salesOrgId });
            }
            return false;
        }
        Long marketId = spmSalesOrganization.getMarketId();
        // 获取iPoint奖金记录审核人
        List<String> lists = paramService.getParamValues(request, BonusConstants.SPM_IPOINT_BONUS_RECORD_APPROVER,
                SystemProfileConstants.ORG_TYPE_SALES, salesOrgId);
        if (lists.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Ipoint Center Bonus approval user not found! salesOrgId : {}", salesOrgId);
            }
            return false;
        }
        Long userId = Long.parseLong(lists.iterator().next());
        User user = new User();
        user.setUserId(userId);
        List<User> users = userMapper.selectUsers(user);
        if (users == null || users.isEmpty() || users.get(0).getAccountId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("The User not found Account information!User : {}", new Object[] { userId });
            }
            return false;
        }
        // 组装站内信模板
        SysMsMessage sysMsMessage = new SysMsMessage();
        sysMsMessage.setPublishChannel(String.valueOf(MessageTypeEnum.DSIS));
        sysMsMessage.setSenderCode("-1");
        sysMsMessage.setSender(1L); // admin用户
        List<SysMsMessageAssign> sysMsMessageAssigns = new ArrayList<SysMsMessageAssign>();
        SysMsMessageAssign sysMsMessageAssign = new SysMsMessageAssign();
        sysMsMessageAssign.setAssignType(SysMsMessageAssign.USER);
        sysMsMessageAssign.setAssignValue(String.valueOf(userId));
        sysMsMessageAssign.setAccountId(users.get(0).getAccountId());
        sysMsMessageAssigns.add(sysMsMessageAssign);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("userName", users.get(0).getUserName());
        data.put("periodName", periodName);
        taskExecutor.execute(() -> {
            try {
                manualMessageService.publishMessage(sysMsMessage, sysMsMessageAssigns,
                        BonusConstants.SPM_IPOINT_BONUS_APPROVE, data, marketId);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("Send message error", e);
                }
            }
        });
        return true;
    }

}
