/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.mapper.SpmCompanyMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccountingBalance;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.MemRank;
import com.lkkhpg.dsis.common.member.mapper.MemAccountingBalanceMapper;
import com.lkkhpg.dsis.common.member.mapper.MemAccountingTrxMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRankMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.IMemberBalanceTrxService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员账务事务处理.
 * 
 * @author houmin
 *
 */
@Service
@Transactional
public class MemberBalanceTrxServiceImpl implements IMemberBalanceTrxService {

    private Logger logger = LoggerFactory.getLogger(MemberBalanceTrxServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SpmCompanyMapper spmCompanyMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private MemAccountingTrxMapper memAccountingTrxMapper;
    @Autowired
    private MemAccountingBalanceMapper memAccountingBalanceMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private MemRankMapper memRankMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processAccountingTrx(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException {
        // 1.校验
        self().valiAccountingTrx(request, memAccountingTrx);
        // 2.记录会员资产交易信息
        memAccountingTrxMapper.insertMemAccTrx(memAccountingTrx);
        // 3.判断账务类型
        if (MemberConstants.ACCOUNTING_TYPE_PV.equals(memAccountingTrx.getAccountingType())) {
            self().processTransferTrxPV(request, memAccountingTrx);
        } else {
            self().processTransferTrxBalance(request, memAccountingTrx);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processTransferTrxBalance(IRequest request, MemAccountingTrx memAccountingTrx)
            throws CommMemberException {
        long trxId = memAccountingTrx.getTrxId();
        long memberId = memAccountingTrx.getMemberId();
        String accountingType = memAccountingTrx.getAccountingType();
        // 事务处理金额
        BigDecimal trxValue = memAccountingTrx.getTrxValue();
        MemAccountingBalance memAccountingBalance = new MemAccountingBalance();
        memAccountingBalance.setMemberId(memberId);
        memAccountingBalance.setBalance(trxValue);
        memAccountingBalance.setAccountingType(accountingType);
        memAccountingBalance.setLastTrxId(trxId);
        // 1.查询MM_ACCOUNTING_BALANCE表是否存在记录
        MemAccountingBalance recoder = memAccountingBalanceMapper.selectByMemIdAndAccType(memberId, accountingType);
        // 2.执行insert或update
        if (recoder == null) {
            // 第一笔影响该余额的事务ID
            memAccountingBalance.setInitTrxId(trxId);
            memAccountingBalanceMapper.insertSelective(memAccountingBalance);
            // 3.更新事务表余额
            memAccountingTrxMapper.updateBalanceByTrxId(trxId, trxValue);
        } else {
            memAccountingBalanceMapper.updateMemAccountBalance(memAccountingBalance);
            // 3.更新事务表余额
            memAccountingTrxMapper.updateBalanceByTrxId(trxId, recoder.getBalance().add(trxValue));
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processTransferTrxPV(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException {
        Long memberId = memAccountingTrx.getMemberId();
        // 事务来源行ID
        Long headerId = memAccountingTrx.getTrxSourceLineId();
        // 期间名称
        String periodName;
        // 订单日期
        if (MemberConstants.TRX_SOURCE_TYPE_ORDER.equals(memAccountingTrx.getTrxSourceType())) {
            // 1.取对应订单日期
            SalesOrder order = salesOrderMapper.selectByPrimaryKey(headerId);
            Long periodId = order.getPeriodId();
            // 2.根据日期在SPM_BONUS_PERIOD表中查询满足日期在期间内且closing_status!='Y'的period_name字段信息
            periodName = spmPeriodMapper.selectSpmPeriodByOrderDate(periodId, MemberConstants.SPM_PERIOD_STATUS_CLOSED);
            if (periodName == null) {
                throw new CommMemberException(CommMemberException.MSG_ERROR_OM_BONUS_MONTH_ERROR, null);
            } else {
                MemRank memRank = new MemRank();
                memRank.setMemberId(memberId);
                memRank.setPv(memAccountingTrx.getTrxValue());
                // 转换月份格式
                StringBuffer sBuffer = new StringBuffer(periodName);
                sBuffer.insert(4, '-');
                memRank.setMonth(sBuffer.toString());
                memRank.setRank(0L);
                // 3.插入或更新表mm_mem_rank表
                if (memRankMapper.selectByMemberIdAndMonth(memberId, sBuffer.toString()) == null) {
                    memRankMapper.insertMemRank(memRank);
                } else {
                    memRankMapper.updateByMemberIdAndMonth(memRank);
                }
            }
        }
    }

    @Override
    public void valiAccountingTrx(IRequest request, MemAccountingTrx memAccountingTrx) throws CommMemberException {
        // 会员ID校验
        Long memberId = memAccountingTrx.getMemberId();
        if (memberMapper.selectByPrimaryKey(memberId) == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("MemberId is not exist.MemberId: {}", new Object[] { memberId });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_MEMBER_CODE_EXIST, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate memberId is ok,MemberId: {}", new Object[] { memberId });
        }
        // 公司ID校验
        Long companyId = memAccountingTrx.getCompanyId();
        if (spmCompanyMapper.selectByPrimaryKey(companyId) == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("CompanyId is not exist.CompanyId: {}", new Object[] { companyId });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_SPM_COMPANY_ID_NOT_EXIST, null);
        }
        // 销售组织ID
        Long salesOrgId = memAccountingTrx.getSalesOrgId();
        if (spmSalesOrganizationMapper.selectByPrimaryKey(salesOrgId) == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("SalesOrgId is not exist.SalesOrgId : {}", new Object[] { salesOrgId });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_SPM_ORGANIZATION_ID_NOT_EXIST, null);
        }
        // 事务处理来源键值校验
        if (memAccountingTrx.getTrxSourceId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxSourceId invalid: {}", new Object[] { memAccountingTrx.getTrxSourceId() });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_TRX_SOURCE_KEY_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxSourceId is ok, TrxSourceId: {}",
                    new Object[] { memAccountingTrx.getTrxSourceId() });
        }
        // 事务处理来源行键值校验
        if (memAccountingTrx.getTrxSourceLineId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxSourceLineId invalid: {}", new Object[] { memAccountingTrx.getTrxSourceLineId() });
            }
            throw new CommMemberException(CommMemberException.MSG_ERROR_TRX_SOURCE_LINE_KEY_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxSourceLineId is ok, TrxSourceLineId: {}",
                    new Object[] { memAccountingTrx.getTrxSourceLineId() });
        }

    }
}
