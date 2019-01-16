/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.VoucherConstants;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.promotion.dto.Voucher;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.promotion.dto.VoucherTransaction;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherAssignMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherMapper;
import com.lkkhpg.dsis.common.promotion.mapper.VoucherTransactionMapper;
import com.lkkhpg.dsis.common.service.IVoucherQuantityTrxService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 优惠券数量事务处理实现类.
 * 
 * @author houmin
 *
 */
@Service
@Transactional
public class VoucherQuantityTrxServiceImpl implements IVoucherQuantityTrxService {

    private Logger logger = LoggerFactory.getLogger(VoucherQuantityTrxServiceImpl.class);

    @Autowired
    private VoucherTransactionMapper voucherTrxMapper;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private VoucherAssignMapper voucherAssignMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processVoucherQuantityTrx(IRequest request, List<VoucherTransaction> voucherTrxs)
            throws CommVoucherException {
        for (VoucherTransaction voucherTrx : voucherTrxs) {
            // 校验优惠券事务对象属性
            self().valiVoucherTrx(request, voucherTrx);
            // 更新优惠券数量
            self().updateVoucherQty(request, voucherTrx);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void valiVoucherTrx(IRequest request, VoucherTransaction voucherTrx) throws CommVoucherException {
        // 暂时功能上不做市场ID校验
        // Long marketId = voucherTrx.getMarketId();
        // if (spmMarketMapper.selectByPrimaryKey(voucherTrx.getMarketId()) ==
        // null) {
        // if (logger.isDebugEnabled()) {
        // logger.debug("The marketId {} not exist!", new Object[] { marketId
        // });
        // }
        // throw new
        // CommVoucherException(CommVoucherException.MSG_ERROR_MARKET_ID_NOT_EXIST,
        // null);
        // }
        // if (logger.isDebugEnabled()) {
        // logger.debug("Validate marketId is ok, marketId : {}", new Object[] {
        // marketId });
        // }

        // 销售区域ID校验
        Long salesOrgId = voucherTrx.getSalesOrgId();
        if (spmSalesOrganizationMapper.selectByPrimaryKey(salesOrgId) == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("The salesOrgId {} not exist!", new Object[] { salesOrgId });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_SALES_ORG_ID_NOT_EXIST, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate salesOgId is ok, salesOrdId : {}", new Object[] { salesOrgId });
        }
        // 优惠券ID校验
        if (voucherTrx.getVoucherId() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("VoucherId invalid: {}", new Object[] { voucherTrx.getVoucherId() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_TRX_VOUCHER_ID_INVALID, null);
        }
        // 事务处理数量校验
        if (voucherTrx.getTrxQty() == null || BigDecimal.ZERO.equals(voucherTrx.getTrxQty())) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxQty invalid: {}", new Object[] { voucherTrx.getTrxQty() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_TRX_QTY_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxQty is ok, trxQty : {}", voucherTrx.getTrxQty());
        }
        // 事务处理类型校验
        CodeValue trxType = codeService.getCodeValue(request, VoucherConstants.TRX_TYPE, voucherTrx.getTrxType());
        if (trxType == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxType invalid: {}", new Object[] { voucherTrx.getTrxType() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_TRX_TYPE_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxType is ok, TrxType: {}", new Object[] { voucherTrx.getTrxType() });
        }
        // 事务处理来源类型
        CodeValue trxSourceType = codeService.getCodeValue(request, VoucherConstants.TRX_SOURCE_TYPE,
                voucherTrx.getTrxSourceType());
        if (trxSourceType == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxType invalid: {}", new Object[] { voucherTrx.getTrxSourceType() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_TRX_SOURCE_TYPE_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxSourceType is ok, TrxSourceType: {}",
                    new Object[] { voucherTrx.getTrxSourceType() });
        }
        // 事务处理来源键值校验
        if (voucherTrx.getTrxSourceKey() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("TrxSourceType invalid: {}", new Object[] { voucherTrx.getTrxSourceKey() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_TRX_SOURCE_KEY_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxSourceKey is ok, TrxSourceKey : {} ",
                    new Object[] { voucherTrx.getTrxSourceKey() });
        }
        // 事务处理来源参考
        if (voucherTrx.getTrxSourceReference() == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Validate trxSourceReference inValid.trxSourceReference: {}",
                        new Object[] { voucherTrx.getTrxSourceReference() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_TRX_SOURCE_REFERENCE_INVALID, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Validate trxSourceReference is ok, trxSourceReference : {}",
                    new Object[] { voucherTrx.getTrxSourceReference() });
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateVoucherQty(IRequest request, VoucherTransaction voucherTrx) throws CommVoucherException {
        // 1.校验优惠券是否有效且在有效期内
        Voucher voucher = new Voucher();
        voucher.setVoucherId(voucherTrx.getVoucherId());
        Voucher resultObj;
        // 退回的时候不用校验优惠券的有效性
        if (VoucherConstants.TRX_TYPE_BACK.equals(voucherTrx.getTrxType())) {
            resultObj = voucherMapper.selectByPrimaryKey(voucherTrx.getVoucherId());
        } else {
            resultObj = voucherMapper.selectEnableVoucher(voucher);
        }

        if (resultObj == null ) {
            if (logger.isDebugEnabled()) {
                logger.debug("Voucher not enabled or invalid, voucherId : {}",
                        new Object[] { voucherTrx.getVoucherId() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHAER_INVALID, null);
        }
        // 优惠券使用对象(SALES,MEM)
        String appScope = resultObj.getAppScope();
        // 优惠券使用对象是会员时，校验会员是否正确
        if (VoucherConstants.VOUCHER_ASSIGN_TYPE_MEM.equals(appScope)) {
            // 会员ID校验
            if (voucherTrx.getMemberId() == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The memberId is empty!");
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_MEMBER_ID_NOT_EXIST, null);
            }
            if (memberMapper.selectByPrimaryKey(voucherTrx.getMemberId()) == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("The memberId {} not exist! ", new Object[] { voucherTrx.getMemberId() });
                }
                throw new CommVoucherException(CommVoucherException.MSG_ERROR_MEMBER_ID_NOT_EXIST, null);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Validate memberId is ok, memberId : {}", new Object[] { voucherTrx.getMemberId() });
            }
        }
        // 2.查询优惠券分配表是存在记录
        VoucherAssign voucherAssign = new VoucherAssign();
        voucherAssign.setVoucherId(voucherTrx.getVoucherId());
        voucherAssign.setAssignType(appScope);
        // voucherAssign.setMarketId(voucherTrx.getMarketId());
        if (VoucherConstants.VOUCHER_ASSIGN_TYPE_SALES.equals(appScope)) {
            voucherAssign.setSalesOrgId(voucherTrx.getSalesOrgId());
        }
        if (VoucherConstants.VOUCHER_ASSIGN_TYPE_MEM.equals(appScope)) {
            voucherAssign.setMemberId(voucherTrx.getMemberId());
        }
        List<VoucherAssign> voucherAssigns = voucherAssignMapper.selectVoucherAssign(voucherAssign);
        if (voucherAssigns == null || voucherAssigns.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The member not assign the voucher,voucherId : {}.",
                        new Object[] { voucherTrx.getVoucherId() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_MEMBER_NOT_ASSIGN_VOUCHER, null);
        }
        Long quantity = voucherAssigns.get(0).getQuantity();
        // 事务处理数量负数-使用优惠券数；事务处理数量为正数-冲销优惠券数
        Long trxQuantity = voucherTrx.getTrxQty();
        // 3.当trxQuantity为负数时，判断优惠券分配表中数量是否够用
        if ((quantity + trxQuantity) < 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Member owned voucher quantity not enough, memberId : {}, voucherId : {}",
                        new Object[] { voucherTrx.getMemberId(), voucherTrx.getVoucherId() });
            }
            throw new CommVoucherException(CommVoucherException.MSG_ERROR_PDM_VOUCHER_QUANTITY_NOT_ENOUGH, null);
        }
        // 4.记录事务信息
        if (voucherTrxMapper.insertSelective(voucherTrx) < 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Insert voucher transaction is error, voucherId : {}",
                        new Object[] { voucherTrx.getVoucherId() });
            }
        }
        // 5. 更新优惠券数量
        voucherAssign.setQuantity(quantity + trxQuantity);
        if (voucherAssignMapper.updateVoucherAssignQty(voucherAssign) < 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("Update voucher assign quantity error, voucherId : {}",
                        new Object[] { voucherTrx.getVoucherId() });
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Update voucher assign quantity is successful! voucherId : {}",
                        new Object[] { voucherTrx.getVoucherId() });
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Voucher quantity transaction process is end! TransactionId : {}",
                    new Object[] { voucherTrx.getTransactionId() });
        }
    }

}
