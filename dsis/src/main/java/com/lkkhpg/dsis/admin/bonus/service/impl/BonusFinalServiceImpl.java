/*
 *
 */
package com.lkkhpg.dsis.admin.bonus.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.bonus.service.IBonusFinalService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusRetransferService;
import com.lkkhpg.dsis.admin.bonus.service.IBonusService;
import com.lkkhpg.dsis.common.bonus.dto.BonusFinal;
import com.lkkhpg.dsis.common.bonus.dto.BonusRelease;
import com.lkkhpg.dsis.common.bonus.dto.BonusReleaseCombine;
import com.lkkhpg.dsis.common.bonus.dto.BonusRetransfer;
import com.lkkhpg.dsis.common.bonus.mapper.BonusFinalMapper;
import com.lkkhpg.dsis.common.bonus.mapper.BonusRetransferMapper;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmBankCharges;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmBankChargesMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmBankMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommBonusException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemAccountingTrx;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.IMemberBalanceTrxService;
import com.lkkhpg.dsis.common.service.IParamService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.ICodeService;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 最终奖金Service接口实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional
public class BonusFinalServiceImpl implements IBonusFinalService {
    @Autowired
    private BonusFinalMapper bonusFinalMapper;

    @Autowired
    private IBonusService bonusService;

    @Autowired
    private IParamService paramService;

    @Autowired
    private IBonusRetransferService bonusRetransferService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemAccountMapper memAccountMapper;

    @Autowired
    private IMemberBalanceTrxService memberBalanceTrxService;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private IDocSequenceService docSequenceService;

    @Autowired
    private SpmBankMapper spmbankMapper;

    @Autowired
    private SpmBankChargesMapper spmBankChargesMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private BonusRetransferMapper bonusRetransferMapper;

    public static final String DOC_TYPE = "BONUS_FINAL";

    public static final String DOC_PREFIX = "BF";

    public static final Long INIT_SEQUENCE = 1L;

    public static final String REMIT_FLAG_Y = "Y";

    public static final String REMIT_MODE_BANK = "BANK";

    public static final String FINAL_BONUS_STATUS_N = "N";

    public static final String BANK_CHARGES_TYPE_FIX = "FIX";

    public static final String BANK_CHARGES_TYPE_PER = "PER";

    public static final int BANK_CHARGES_PERCENT = 100;

    public static final String BANKFILE_NAME_SG_LOCAL = "InfinitusLocalBonus";

    public static final String BANKFILE_NAME_SG_INTER = "InfinitusInternationalBonus";

    @Override
    public List<BonusFinal> queryBonusFinal(IRequest request, BonusFinal bonusFinal, int page, int pagesize) {
        // String marketId =
        // request.getAttribute(SystemProfileConstants.MARKET_ID).toString();
        // bonusFinal.setMarketId(Long.parseLong(marketId));
        if (-1 != pagesize) {
            PageHelper.startPage(page, pagesize);
        }
        return bonusFinalMapper.queryDetailsByBonusFinal(bonusFinal);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<String> checkBonusAndUpdate(IRequest request, BonusFinal bonusFinal) throws CommMemberException {
        List<String> result = new ArrayList<String>();
        Long marketId = bonusFinal.getMarketId();
        BonusFinal bonus = new BonusFinal();
        bonus = bonusFinalMapper.selectByPrimaryKey(bonusFinal.getBonusId());
        if (BonusConstants.BONUS_FLAG_Y.equals(bonusFinal.getRemitFlag())
                && BonusConstants.BONUS_FLAG_Y.equals(bonusFinal.getLockFlag())
                && BonusConstants.BONUS_FLAG_Y.equals(bonusFinal.getAutoFaliledFlag())
                && !BonusConstants.BM_PROCESS_MODE_SDBF.equals(bonus.getProcessMode())
                && !BonusConstants.BM_PROCESS_MODE_TKBF.equals(bonus.getProcessMode())) {
            // 判断失败原因是银行退款，则需要校验当前记录是否是上次发放的记录，若不是，则提示，若是，则执行失败流程
            if (BonusConstants.BM_PROCESS_MODE_YHTK.equals(bonusFinal.getProcessMode())) {
                BonusFinal lastBonusFinal = bonusFinalMapper.queryLastRecordByMarket(marketId);
                // 如果选中记录的期间，类型和上次发放类型不同，则返回false；
                if (!lastBonusFinal.getPeriodId().equals(bonusFinal.getPeriodId())
                        || !lastBonusFinal.getBonusType().equals(bonusFinal.getBonusType())) {
                    result.add(BonusConstants.BONUS_PAYFAIL_ERROR_ONE);
                    return result;
                }
            }
            // 修改支付状态
            BonusFinal updateData = new BonusFinal();
            updateData.setBonusId(bonus.getBonusId());
            updateData.setRemitFlag(BonusConstants.BONUS_FLAG_N);
            // updateData.setPeriodId(bonus.getPeriodId());
            // updateData.setMarketId(bonus.getMarketId());
            // updateData.setBonusType(bonus.getBonusType());
            // updateData.setMemberId(bonus.getMemberId());
            bonusFinalMapper.updateByPrimaryKeySelective(updateData);
            // 生成retransfer
            List<BonusFinal> bonusFinals = new ArrayList<BonusFinal>();
            bonusFinals.add(bonusFinal);
            // self().updateStatusByPayfall(request, bonusFinals);
            self().retransferOrRemain(request, bonusFinals);
            // 手动失败根据选择的失败原因更新表格
            List<String> code = paramService.getMarketParamValues(request, BonusConstants.SPM_ATUO_WRITE_OFF, marketId);
            if (!code.isEmpty() && BonusConstants.BONUS_FLAG_Y.equals(code.get(0))) {
                BonusFinal bonusTemp = new BonusFinal();
                bonusTemp.setBonusId(bonusFinal.getBonusId());
                // 组织参数为Y的情况下：手动失败-》手动补发 ； 银行退款-》退款补发
                if (BonusConstants.BM_PROCESS_MODE_SDSB.equals(bonusFinal.getProcessMode())) {
                    bonusTemp.setProcessMode(BonusConstants.BM_PROCESS_MODE_SDBF);
                } else if (BonusConstants.BM_PROCESS_MODE_YHTK.equals(bonusFinal.getProcessMode())) {
                    bonusTemp.setProcessMode(BonusConstants.BM_PROCESS_MODE_TKBF);
                }
                bonusFinalMapper.updateByPrimaryKeySelective(bonusTemp);
            } else {
                BonusFinal bonusTemp = new BonusFinal();
                bonusTemp.setBonusId(bonusFinal.getBonusId());
                bonusTemp.setProcessMode(bonusFinal.getProcessMode());
                bonusFinalMapper.updateByPrimaryKeySelective(bonusTemp);
            }
            // // TODO: 有可能去除会员限制
            // Member member =
            // memberMapper.selectByPrimaryKey(bonusFinal.getMemberId());
            // if
            // (MemberConstants.MEM_STATUS_CHANGE_AUTO_TERMINATE.equals(member.getStatus())
            // ||
            // MemberConstants.MEM_STATUS_CHANGE_TERMINATE.equals(member.getStatus()))
            // {
            // BonusFinal bonusTemp = new BonusFinal();
            // bonusTemp.setBonusId(bonusFinal.getBonusId());
            // bonusTemp.setProcessMode(bonusFinal.getProcessMode());
            // bonusFinalMapper.updateByPrimaryKeySelective(bonusTemp);
            // } else {
            // }
            result.add(BonusConstants.BONUS_PAYFAIL_SUCCESS);
            return result;
        }
        result.add(BonusConstants.BONUS_PAYFAIL_ERROR);
        return result;

    }

    @Override
    public void createFinalBonus(IRequest request, BonusReleaseCombine releaseCombine) {
        Map<String, Object> combineInfo = new HashMap<String, Object>();
        combineInfo.put("periodId", releaseCombine.getPeriodId());
        combineInfo.put("marketId", releaseCombine.getMarketId());
        combineInfo.put("bonusType", releaseCombine.getBonusType());
        combineInfo.put("remitDate", releaseCombine.getRemitDate());
        combineInfo.put("createBy", request.getAccountId());
        combineInfo.put("requestId", -1);
        combineInfo.put("programId", -1);
        StringBuffer retransfers = new StringBuffer();
        for (BonusRetransfer retransfer : releaseCombine.getRetransfers()) {
            retransfers.append(retransfer.getRetransId()).append(",");
        }
        if (retransfers.length() > 0) {
            retransfers.deleteCharAt(retransfers.length() - 1);
        }
        combineInfo.put("retransfers", retransfers.toString());

        StringBuffer memWithdrawStr = new StringBuffer();

        for (MemWithdraw memWithdraw : releaseCombine.getMemWithdraws()) {
            memWithdrawStr.append(memWithdraw.getWithdrawId()).append(",");
        }
        if (memWithdrawStr.length() > 0) {
            memWithdrawStr.deleteCharAt(memWithdrawStr.length() - 1);
        }
        combineInfo.put("withdraws", memWithdrawStr.toString());

        List<String> currencies = paramService.getMarketParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                releaseCombine.getMarketId());
        combineInfo.put("currencyCode", currencies.get(0));
        bonusFinalMapper.createFinalBonus(combineInfo);
    }

    @Override
    public void insertFinalBonus(IRequest request, BonusReleaseCombine releaseCombine) {
        BonusRelease query = new BonusRelease();
        query.setPeriodId(releaseCombine.getPeriodId());
        query.setMarketId(releaseCombine.getMarketId());
        query.setBonusType(releaseCombine.getBonusType());
        List<BonusFinal> bonusFinals = bonusFinalMapper.queryBonusFinal(releaseCombine);
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(releaseCombine.getPeriodId());

        Map<Long, Map<String, Object>> memberReleaseInfos = new HashMap<Long, Map<String, Object>>();

        for (BonusFinal bonusFinal : bonusFinals) {
            Map<String, Object> releaseInfo = new HashMap<String, Object>();
            releaseInfo.put("finalBonu", bonusFinal);
            releaseInfo.put("retransferAmt", BigDecimal.ZERO);
            releaseInfo.put("withdrawsAmt", BigDecimal.ZERO);
            memberReleaseInfos.put(bonusFinal.getMemberId(), releaseInfo);
        }
        for (BonusRetransfer bonusRetransfer : releaseCombine.getRetransfers()) {
            Map<String, Object> releaseInfo = new HashMap<String, Object>();
            if (memberReleaseInfos.containsKey(bonusRetransfer.getMemberId())) {
                releaseInfo = (Map<String, Object>) memberReleaseInfos.get(bonusRetransfer.getMemberId());
                BigDecimal retransferAmout = new BigDecimal(releaseInfo.get("retransferAmt").toString());
                retransferAmout = retransferAmout.add(bonusRetransfer.getRetransAmt());
                releaseInfo.put("retransferAmt", retransferAmout);
            } else {
                releaseInfo.put("finalBonu", setDefaultBonusFinal(bonusRetransfer.getMemberId()));
                releaseInfo.put("retransferAmt", bonusRetransfer.getRetransAmt());
                releaseInfo.put("withdrawsAmt", BigDecimal.ZERO);
                memberReleaseInfos.put(bonusRetransfer.getMemberId(), releaseInfo);
            }
        }

        for (MemWithdraw memWithdraw : releaseCombine.getMemWithdraws()) {
            Map<String, Object> releaseInfo = new HashMap<String, Object>();
            if (memberReleaseInfos.containsKey(memWithdraw.getMemberId())) {
                releaseInfo = memberReleaseInfos.get(memWithdraw.getMemberId());
                BigDecimal withdrawsAmout = (BigDecimal) releaseInfo.get("withdrawsAmt");
                withdrawsAmout = withdrawsAmout.add(memWithdraw.getAmount());
                releaseInfo.put("withdrawsAmt", withdrawsAmout);
            } else {
                releaseInfo.put("finalBonu", setDefaultBonusFinal(memWithdraw.getMemberId()));
                releaseInfo.put("retransferAmt", BigDecimal.ZERO);
                releaseInfo.put("withdrawsAmt", memWithdraw.getAmount());
                memberReleaseInfos.put(memWithdraw.getMemberId(), releaseInfo);
            }
        }

        List<String> currencies = paramService.getMarketParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                releaseCombine.getMarketId());
        // 根据当前marketId获取汇入银行数据
        Long marketId = releaseCombine.getMarketId();
        SpmMarket market = spmMarketMapper.selectByPrimaryKey(marketId);
        String marketCode = market.getCode();
        String deliverBankCode = codeService.getCodeMeaningByValue(request, "BM.PAID_BANK", marketCode);
        List<SpmBank> bankInfo = new ArrayList<SpmBank>();
        List<SpmBankCharges> charges = new ArrayList<SpmBankCharges>();
        if (null != deliverBankCode) {
            bankInfo = spmbankMapper.queryBankByNumber(deliverBankCode);
            if (!bankInfo.isEmpty()) {
                Long remitBankId = bankInfo.get(0).getBankId();
                SpmBankCharges tempCharges = new SpmBankCharges();
                tempCharges.setBankId(remitBankId);
                charges = spmBankChargesMapper.querySpmBankCharces(tempCharges);
            }
        }
        for (Long memberId : memberReleaseInfos.keySet()) {
            Map<String, Object> memReleaseInfo = memberReleaseInfos.get(memberId);
            BonusFinal bonusFinal = (BonusFinal) memReleaseInfo.get("finalBonu");
            bonusFinal.setPeriodId(releaseCombine.getPeriodId());
            bonusFinal.setMemberId(memberId);
            bonusFinal.setCurrencyCode(currencies.get(0));
            BigDecimal preTaxAmtAdjust = new BigDecimal(BigInteger.ZERO);
            preTaxAmtAdjust = preTaxAmtAdjust.add((BigDecimal) memReleaseInfo.get("retransferAmt"));
            preTaxAmtAdjust = preTaxAmtAdjust.add((BigDecimal) memReleaseInfo.get("withdrawsAmt"));
            bonusFinal.setPreTaxAmtAdjust(preTaxAmtAdjust);

            // 支付金额 = 发放金额 + retransfer金额 +冲销节余金额
            BigDecimal deliveryAmt = new BigDecimal(BigInteger.ZERO);
            if (null != bonusFinal.getDeliverAmt()) {
                deliveryAmt = deliveryAmt.add(bonusFinal.getDeliverAmt());
            }
            deliveryAmt = deliveryAmt.add((BigDecimal) memReleaseInfo.get("retransferAmt"));
            deliveryAmt = deliveryAmt.add((BigDecimal) memReleaseInfo.get("withdrawsAmt"));
            bonusFinal.setDeliverAmt(deliveryAmt);
            // TODO 根据市场获取汇入银行code，计算手续费
            bonusFinal.setDeliverBankCode(deliverBankCode);
            BigDecimal bankFee = BigDecimal.ZERO;
            if (!bankInfo.isEmpty() && !charges.isEmpty()) {
                for (SpmBankCharges temp : charges) {
                    if (deliveryAmt.compareTo(new BigDecimal(temp.getBonusFrom())) >= 0
                            && deliveryAmt.compareTo(new BigDecimal(temp.getBonusTo())) < 0) {
                        if (BANK_CHARGES_TYPE_FIX.equals(temp.getChargeType())) {
                            bankFee = temp.getCharges();
                        } else if (BANK_CHARGES_TYPE_PER.equals(temp.getChargeType())) {
                            bankFee = deliveryAmt
                                    .multiply(temp.getCharges().divide(new BigDecimal(BANK_CHARGES_PERCENT)))
                                    .setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        break;
                    }
                }
            }
            bonusFinal.setDeliverBankFee(bankFee);
            // 汇入净金额 = 支付金额 - 银行手续费
            bonusFinal.setRemitNetAmt(deliveryAmt.subtract(bankFee));
            bonusFinal.setRemitFlag(REMIT_FLAG_Y);
            bonusFinal.setMarketId(releaseCombine.getMarketId());

            bonusFinal.setCreatedBy(request.getAccountId());
            // 设置奖金领取方式
            Member member = memberMapper.selectByPrimaryKey(memberId);
            bonusFinal.setRemitMode(member.getBonusRcvMethod());
            // 最终奖金编号
            String bonusCode = docSequenceService.getSequence(request,
                    new DocSequence(DOC_TYPE, DOC_PREFIX + spmPeriod.getPeriodName(), null, null, null, null),
                    DOC_PREFIX + spmPeriod.getPeriodName(), BonusConstants.CODE_LENGTH_FIVE, INIT_SEQUENCE);
            bonusFinal.setBonusCode(bonusCode);
            bonusFinal.setBonusType(releaseCombine.getBonusType());
            bonusFinal.setLockFlag(BonusConstants.BONUS_FLAG_N);
            bonusFinal.setAutoFaliledFlag(BonusConstants.BONUS_FLAG_N);
            bonusFinal.setProcessMode(BonusConstants.BM_PROCESS_MODE_ZDCG);
            bonusFinalMapper.insertSelective(bonusFinal);
        }
    }

    private BonusFinal setDefaultBonusFinal(Long memberId) {
        BonusFinal bonusfinal = new BonusFinal();
        List<MemAccount> accounts = memAccountMapper.selectByMemberId(memberId);
        if (!accounts.isEmpty()) {
            MemAccount account = accounts.get(0);
            if (null != account.getAccountNumber()) {
                if (null != account.getBankId()) {
                    SpmBank bank = spmbankMapper.selectByPrimaryKey(account.getBankId());
                    if (null != bank) {
                        bonusfinal.setRemitBankCode(bank.getBankNumber());
                    }
                }
                // SpmBank bank =
                // spmbankMapper.selectByPrimaryKey(account.getBankId());
                // bonusfinal.setRemitBankCode(bank.getBankNumber());
                if (null != account.getBankBranchId()) {
                    SpmBank branchBank = spmbankMapper.selectByPrimaryKey(account.getBankBranchId());
                    if (null != branchBank) {
                        bonusfinal.setBankBranchCode(branchBank.getBankNumber());
                    }
                }
                bonusfinal.setRemitBankAccount(account.getAccountNumber());
                if (null != account.getIdNumber()) {
                    bonusfinal.setIdNumber(account.getIdNumber());
                }
                if (null != account.getAccountHolder()) {
                    bonusfinal.setRemitBankAccountName(account.getAccountHolder());
                }

            }
        }
        bonusfinal.setPreTaxAmt(BigDecimal.ZERO);
        bonusfinal.setPreTaxAmtAdjust(BigDecimal.ZERO);
        bonusfinal.setAfterTaxAmt(BigDecimal.ZERO);
        bonusfinal.setDeliverAmt(BigDecimal.ZERO);
        bonusfinal.setStatus(FINAL_BONUS_STATUS_N);
        return bonusfinal;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void downloadFinalBonus(IRequest request, HttpServletResponse response, Long periodId, String bonusType,
            Long marketId) throws CommBonusException {
        SpmMarket market = spmMarketMapper.selectByPrimaryKey(marketId);
        BonusFinal bonus = new BonusFinal();
        try {
            bonus.setMarketId(marketId);
        } catch (NumberFormatException e) {
            throw new CommBonusException(CommBonusException.MSG_ERROR_BONUS_CREATE_FILE_FAIL, null);
        }
        bonus.setPeriodId(periodId);
        bonus.setBonusType(bonusType);
        bonus.setRemitMode(REMIT_MODE_BANK);
        bonus.setRemitFlag(REMIT_FLAG_Y);
        bonus.setProcessMode(BonusConstants.BM_PROCESS_MODE_ZDCG);
        // 台湾市场，马来市场文件名所需字段
        SimpleDateFormat format = new SimpleDateFormat(BonusConstants.BONUS_CREATE_FILE_NAME_TIME);
        String createDate = format.format(new java.util.Date());
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(periodId);
        String periodName = spmPeriod.getPeriodName();
        StringBuilder fileName = new StringBuilder();
        if (BonusConstants.MARKET_HK.equals(market.getCode())) {
            response.setContentType("application/csv;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=commiss.apc");
            try (PrintWriter out = response.getWriter()) {
                createHGfile(request, bonus, out);
            } catch (IOException e) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_BONUS_CREATE_FILE_FAIL, null);
            }
        } else if (BonusConstants.MARKET_TW.equals(market.getCode())) {
            fileName.append(createDate);
            fileName.append(BonusConstants.MARKET_TW);
            fileName.append(periodName);
            fileName.append(BonusConstants.BONUS_FILE_TYPE_TXT);
            response.setContentType("application/csv;charset=BIG5");
            response.setCharacterEncoding("BIG5");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName.toString());
            try (PrintWriter out = response.getWriter()) {
                createTWfile(request, bonus, out);
            } catch (IOException e) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_BONUS_CREATE_FILE_FAIL, null);
            }
        } else if (BonusConstants.MARKET_MY.equals(market.getCode())) {
            fileName.append(createDate);
            fileName.append(BonusConstants.MARKET_MY);
            fileName.append(periodName);
            fileName.append(BonusConstants.BONUS_FILE_TYPE_TXT);
            response.setContentType("application/csv;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName.toString());
            try (PrintWriter out = response.getWriter()) {
                createMLfile(request, bonus, out);
            } catch (IOException e) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_BONUS_CREATE_FILE_FAIL, null);
            }
        } else if (BonusConstants.MARKET_SG.equals(market.getCode())) {
            if (BonusConstants.BONUS_TYPE_LOCAL.equals(bonusType)) {
                fileName.append(BANKFILE_NAME_SG_LOCAL);
            } else if (BonusConstants.BONUS_TYPE_INTER.equals(bonusType)) {
                fileName.append(BANKFILE_NAME_SG_INTER);
            }
            fileName.append(periodName);
            fileName.append(BonusConstants.BONUS_FILE_TYPE_CSV);
            response.setContentType("application/csv;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName.toString());
            try (PrintWriter out = response.getWriter()) {
                createSGfile(request, bonus, out);
            } catch (IOException e) {
                throw new CommBonusException(CommBonusException.MSG_ERROR_BONUS_CREATE_FILE_FAIL, null);
            }
        }
    }

    /**
     * 格式化字符串.
     * 
     * @param str
     *            需要格式化的字符串
     * @param strLength
     *            格式化长度
     * @param flag
     *            格式化类型
     * @return 格式化后的字符串
     */
    public static String addZeroOrNullForStr(String str, int strLength, String flag) {
        if (null == str) {
            str = "";
        }
        if (BonusConstants.BONUS_FLAG_N.equals(flag)) {
            return StringUtils.leftPad(str, strLength, "0");
        } else {
            return StringUtils.rightPad(str, strLength);
        }
    }

    /**
     * 根据月份返回英文月份大写前三位.
     * 
     * @param month
     *            月份数值.
     * @return 英文月份前三位.
     */
    public String monthConvert(String month) {
        Map<String, String> monthList = new HashMap<String, String>();
        monthList.put("01", "JAN");
        monthList.put("02", "FEB");
        monthList.put("03", "MAR");
        monthList.put("04", "APR");
        monthList.put("05", "MAY");
        monthList.put("06", "JUN");
        monthList.put("07", "JUL");
        monthList.put("08", "AUG");
        monthList.put("09", "SEP");
        monthList.put("10", "OCT");
        monthList.put("11", "NOV");
        monthList.put("12", "DEC");
        return monthList.get(month);
    }

    /**
     * 生成香港市场最终奖金文件.
     * 
     * @param request
     *            统一上下文.
     * @param bonus
     *            查询奖金条件.
     * @param out
     *            文件输出流.
     * @throws UnsupportedEncodingException
     */
    private void createHGfile(IRequest request, BonusFinal bonus, PrintWriter out) throws UnsupportedEncodingException {
        // 奖金条数及奖金总金额统计
        BonusFinal totalResult = bonusFinalMapper.queryCreateFile(bonus);
        // 奖金记录明细
        List<BonusFinal> bonusList = bonusFinalMapper.queryDetailsForBankFile(bonus, "HK");
        DecimalFormat decimalFormat = new DecimalFormat(BonusConstants.BONUS_AMT_STYLE);
        if (!bonusList.isEmpty()) {
            // 文件头
            StringBuilder head = new StringBuilder();
            head.append("F267256808001N01COMMISS");
            head.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_ONE, BonusConstants.BONUS_FLAG_Y));
            SimpleDateFormat format = new SimpleDateFormat(BonusConstants.BONUS_CREATE_FILE_TIME);
            String dateStr = format.format(bonusList.get(0).getRemitDate());
            head.append(
                    monthConvert(dateStr.substring(BonusConstants.CODE_LENGTH_TWO, BonusConstants.CODE_LENGTH_FOUR)));
            head.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_ONE, BonusConstants.BONUS_FLAG_Y));
            head.append(dateStr);
            head.append("K********");
            head.append(addZeroOrNullForStr(totalResult.getAmount().toString(), BonusConstants.CODE_LENGTH_FIVE,
                    BonusConstants.BONUS_FLAG_N));
            String totalBonus = decimalFormat.format(totalResult.getTotalBonus()).replace(".", "");
            head.append(addZeroOrNullForStr(totalBonus, BonusConstants.CODE_LENGTH_TEN, BonusConstants.BONUS_FLAG_N));
            head.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_TWENTY_ONE, BonusConstants.BONUS_FLAG_Y));
            head.append("1");
            out.print(head.toString());
        }
        // 根据奖金记录查询写入文件
        StringBuilder row = new StringBuilder();
        for (BonusFinal temp : bonusList) {
            if (StringUtils.isEmpty(temp.getRemitBankAccountName())
                    || StringUtils.isEmpty(temp.getRemitBankAccount())) {
                // 会员帐号信息不完整，跳过这条记录
                continue;
            }
            row.delete(0, row.length());
            row.append(addZeroOrNullForStr("", 1, BonusConstants.BONUS_FLAG_Y));
            row.append(addZeroOrNullForStr(temp.getMemberCode(), BonusConstants.CODE_LENGTH_TWELVE,
                    BonusConstants.BONUS_FLAG_Y));
            // String bankAccountName = "";
            // if (20 < (temp.getRemitBankAccountName() + "").length()) {
            // bankAccountName = (temp.getRemitBankAccountName() +
            // "").trim().substring(0, 20);
            // } else {
            // bankAccountName = (temp.getRemitBankAccountName() + "");
            // }
            byte[] bankAccountName = (temp.getRemitBankAccountName().toUpperCase() + "").getBytes("UTF-8");
            int accountLength = bankAccountName.length;
            StringBuffer bankAccountStr = new StringBuffer((temp.getRemitBankAccountName().toUpperCase() + ""));
            if (20 < accountLength) {
                byte[] accountTempByte = new byte[20];
                for (int i = 0; i < 20; i++) {
                    accountTempByte[i] = bankAccountName[i];
                }
                row.append(new String(accountTempByte, "UTF-8"));
            } else {
                for (int j = accountLength; j < 20; j++) {
                    bankAccountStr.append(" ");
                }
                row.append(bankAccountStr);
            }

            // row.append(addZeroOrNullForStr(bankAccountName.toUpperCase(),
            // BonusConstants.CODE_LENGTH_TWENTY,
            // BonusConstants.BONUS_FLAG_Y));
            row.append(addZeroOrNullForStr(temp.getRemitBankAccount(), BonusConstants.CODE_LENGTH_FIFTEEN,
                    BonusConstants.BONUS_FLAG_Y));
            String itemBonus = decimalFormat.format(temp.getRemitNetAmt()).replace(".", "");
            row.append(addZeroOrNullForStr(itemBonus, BonusConstants.CODE_LENGTH_TEN, BonusConstants.BONUS_FLAG_N));
            row.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_TWENTY_TWO, BonusConstants.BONUS_FLAG_Y));
            out.print(row.toString());
        }
        out.flush();
    }

    /**
     * 生成台湾市场最终奖金文件.
     * 
     * @param request
     *            统一上下文.
     * @param bonus
     *            查询奖金条件.
     * @param out
     *            文件输出流.
     * @throws UnsupportedEncodingException
     */
    private void createTWfile(IRequest request, BonusFinal bonus, PrintWriter out) throws UnsupportedEncodingException {
        // 奖金记录明细
        List<BonusFinal> bonusList = bonusFinalMapper.queryDetailsForBankFile(bonus, "TW");
        StringBuilder row = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat(BonusConstants.BONUS_AMT_STYLE);
        DocSequence docSequence = new DocSequence(BonusConstants.BONUS_TW_FILE_CODE, null, null, null, null, null);
        for (BonusFinal temp : bonusList) {
            if (StringUtils.isEmpty(temp.getRemitBankCode()) || StringUtils.isEmpty(temp.getBankBranchCode())
                    || StringUtils.isEmpty(temp.getRemitBankAccount()) || StringUtils.isEmpty(temp.getIdNumber())
                    || StringUtils.isEmpty(temp.getRemitBankAccountName())) {
                // 会员帐号信息不完整，跳过这条记录
                continue;
            }
            row.delete(0, row.length());
            row.append(addZeroOrNullForStr(temp.getIdNumber(), BonusConstants.CODE_LENGTH_ELEVEN,
                    BonusConstants.BONUS_FLAG_Y));
            String fileCode = docSequenceService.getSequence(request, docSequence, "", BonusConstants.CODE_LENGTH_SIX,
                    1L);
            row.append(fileCode);
            // 汇款种类
            String remitBankCode = temp.getRemitBankCode().substring(0, BonusConstants.CODE_LENGTH_THREE);
            if ("822".equals(remitBankCode)) {
                row.append("01");
            } else {
                row.append("11");
            }
            // 解款行代號
            row.append(remitBankCode);
            // 解款分行代號
            int codeLength = temp.getBankBranchCode().length();
            row.append(temp.getBankBranchCode().substring(codeLength - BonusConstants.CODE_LENGTH_FOUR, codeLength));
            // 付款金额
            String itemBonus = decimalFormat.format(temp.getRemitNetAmt()).replace(".", "");
            row.append(
                    addZeroOrNullForStr(itemBonus, BonusConstants.CODE_LENGTH_THIRTEEN, BonusConstants.BONUS_FLAG_N));
            // 摘要
            row.append("2");
            // 收款人帳號
            row.append(addZeroOrNullForStr(temp.getRemitBankAccount(), BonusConstants.CODE_LENGTH_FOURTEEN,
                    BonusConstants.BONUS_FLAG_Y));
            // 收款人戶名
            byte[] bankAccountName = (temp.getRemitBankAccountName() + "").getBytes("BIG5");
            int accountLength = bankAccountName.length;
            StringBuffer bankAccountStr = new StringBuffer((temp.getRemitBankAccountName() + ""));
            if (80 < accountLength) {
                byte[] accountTempByte = new byte[80];
                for (int i = 0; i < 80; i++) {
                    accountTempByte[i] = bankAccountName[i];
                }
                row.append(new String(accountTempByte, "BIG5"));
            } else {
                for (int j = accountLength; j < 80; j++) {
                    bankAccountStr.append(" ");
                }
                row.append(bankAccountStr);
            }
            // row.append(addZeroOrNullForStr((temp.getRemitBankAccountName() +
            // "").toUpperCase(),
            // BonusConstants.CODE_LENGTH_EIGHTY, BonusConstants.BONUS_FLAG_Y));
            // 匯款人ID 24239897
            row.append(addZeroOrNullForStr("24239897", BonusConstants.CODE_LENGTH_ELEVEN, BonusConstants.BONUS_FLAG_Y));
            // 匯款人電話 空白
            row.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_TEN, BonusConstants.BONUS_FLAG_Y));
            // 匯款人名稱
            StringBuffer companyNameStr = new StringBuffer("無限極國際有限公司");
            byte[] companyName = "無限極國際有限公司".getBytes("BIG5");
            int companyTempLength = companyName.length;
            for (int k = companyTempLength; k < 80; k++) {
                companyNameStr.append(" ");
            }
            row.append(companyNameStr);
            // row.append(
            // addZeroOrNullForStr("無限極國際有限公司",
            // BonusConstants.CODE_LENGTH_EIGHTY, BonusConstants.BONUS_FLAG_Y));
            // 附言
            row.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_EIGHTY, BonusConstants.BONUS_FLAG_Y));
            // 匯款日期
            SimpleDateFormat format = new SimpleDateFormat(BonusConstants.BONUS_CREATE_FILE_TIME_TW);
            String dateStr = format.format(temp.getRemitDate());
            SimpleDateFormat format2 = new SimpleDateFormat(BonusConstants.BONUS_CREATE_FILE_TIME_YEAR);
            String yearStr = format2.format(temp.getRemitDate());
            Integer year = Integer.parseInt(yearStr) - 1911;
            StringBuffer resultYear = new StringBuffer();
            resultYear.append(year.toString().substring(year.toString().length() - 2, year.toString().length()));
            resultYear.append(dateStr.substring(2, 6));
            row.append(resultYear);
            row.append("\r\n");
            out.print(row.toString());
        }
        out.flush();
    }

    /**
     * 生成马来西亚市场最终奖金文件.
     * 
     * @param request
     *            统一上下文.
     * @param bonus
     *            查询奖金条件.
     * @param out
     *            文件输出流.
     * @throws UnsupportedEncodingException
     */
    private void createMLfile(IRequest request, BonusFinal bonus, PrintWriter out) throws UnsupportedEncodingException {
        // 奖金记录明细
        List<BonusFinal> bonusList = bonusFinalMapper.queryDetailsForBankFile(bonus, "MY");
        if (bonusList.isEmpty()) {
            out.flush();
            return;
        }
        StringBuilder head = new StringBuilder();
        // Record type
        head.append("01");
        // Organisation Code
        head.append("41115");
        // Organization Name
        head.append(addZeroOrNullForStr("INFINITUS INTERNATIONAL (MALAYSIA) SB", BonusConstants.CODE_LENGTH_FORTY,
                BonusConstants.BONUS_FLAG_Y));
        // Crediting Date
        SimpleDateFormat format = new SimpleDateFormat(BonusConstants.BONUS_CREATE_FILE_TIME_MY);
        String dateStr = format.format(bonusList.get(0).getRemitDate());
        head.append(dateStr);
        // Security Code
        head.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_SIXTEEN, BonusConstants.BONUS_FLAG_N));
        // Filler
        head.append(addZeroOrNullForStr("", BonusConstants.CODE_LENGTH_TWO, BonusConstants.BONUS_FLAG_Y));
        head.append("\r\n");
        out.print(head.toString());
        DecimalFormat decimalFormat = new DecimalFormat(BonusConstants.BONUS_AMT_STYLE);
        StringBuilder row = new StringBuilder();
        for (BonusFinal temp : bonusList) {
            if (StringUtils.isEmpty(temp.getRemitBankAccountName()) || StringUtils.isEmpty(temp.getRemitBankAccount())
                    || StringUtils.isEmpty(temp.getRemitBankCode())) {
                // 会员帐号信息不完整，跳过这条记录
                continue;
            }
            row.delete(0, row.length());
            // Record Type
            row.append("02");
            // BNM code
            // TODO:后续需要更具具体银行代码动态添加
            String bnm = codeService.getCodeValueByMeaning(request, "BM.BNM_CODE",
                    temp.getRemitBankCode().toUpperCase());
            if (bnm == null) {
                bnm = "";
            }
            StringBuffer bnmTemp = new StringBuffer(bnm);
            for (int i = bnmTemp.length(); i < BonusConstants.CODE_LENGTH_SEVEN; i++) {
                bnmTemp.append("0");
            }
            row.append(bnmTemp);
            // Account Number
            row.append(addZeroOrNullForStr(updateForValueMY(temp.getRemitBankAccount()),
                    BonusConstants.CODE_LENGTH_SIXTEEN, BonusConstants.BONUS_FLAG_Y));
            // Beneficiary Name
            byte[] bankAccountName = (updateForValueMY(temp.getRemitBankAccountName()).toUpperCase() + "")
                    .getBytes("UTF-8");
            int accountLength = bankAccountName.length;
            StringBuffer bankAccountStr = new StringBuffer(
                    (updateForValueMY(temp.getRemitBankAccountName()).toUpperCase() + ""));
            if (40 < accountLength) {
                byte[] accountTempByte = new byte[40];
                for (int i = 0; i < 40; i++) {
                    accountTempByte[i] = bankAccountName[i];
                }
                row.append(new String(accountTempByte, "UTF-8"));
            } else {
                for (int j = accountLength; j < 40; j++) {
                    bankAccountStr.append(" ");
                }
                row.append(bankAccountStr);
            }
            // row.append(addZeroOrNullForStr((temp.getRemitBankAccountName() +
            // "").toUpperCase(),
            // BonusConstants.CODE_LENGTH_FORTY, BonusConstants.BONUS_FLAG_Y));
            // Payment Amount
            String itemBonus = decimalFormat.format(temp.getRemitNetAmt()).toString().replace(".", "");
            row.append(addZeroOrNullForStr(itemBonus, BonusConstants.CODE_LENGTH_ELEVEN, BonusConstants.BONUS_FLAG_N));
            // Reference Number
            row.append(addZeroOrNullForStr(updateForValueMY(temp.getMemberCode()), BonusConstants.CODE_LENGTH_THIRTY,
                    BonusConstants.BONUS_FLAG_Y));
            // Beneficiary ID
            row.append(addZeroOrNullForStr(updateForValueMY(temp.getIdNumber()), BonusConstants.CODE_LENGTH_TWENTY,
                    BonusConstants.BONUS_FLAG_Y));
            // Transaction Type
            row.append("2");
            row.append("\r\n");
            out.print(row.toString());
        }
        StringBuilder end = new StringBuilder();
        end.append("03");
        // 奖金条数及奖金总金额统计
        BonusFinal totalResult = bonusFinalMapper.queryCreateFile(bonus);
        // Total Number of Records
        end.append(addZeroOrNullForStr(totalResult.getAmount().toString(), BonusConstants.CODE_LENGTH_SIX,
                BonusConstants.BONUS_FLAG_N));
        // Total Amount
        String totalBonus = decimalFormat.format(totalResult.getTotalBonus()).replace(".", "");
        end.append(addZeroOrNullForStr(totalBonus, BonusConstants.CODE_LENGTH_THIRTEEN, BonusConstants.BONUS_FLAG_N));
        end.append("\r\n");
        out.print(end.toString());
        out.flush();
    }

    private String updateForValueMY(String str) {
        str = str.replace("-", "");
        str = str.replace("/", "");
        return str;
    }

    /**
     * 生成新加坡市场最终奖金文件.
     * 
     * @param request
     *            统一上下文.
     * @param bonus
     *            查询奖金条件.
     * @param out
     *            文件输出流.
     */
    private void createSGfile(IRequest request, BonusFinal bonus, PrintWriter out) {
        // 奖金条数及奖金总金额统计
        BonusFinal totalResult = bonusFinalMapper.queryCreateFile(bonus);
        // 奖金记录明细
        List<BonusFinal> bonusList = bonusFinalMapper.queryDetailsForBankFile(bonus, "SG");
        if (bonusList.isEmpty()) {
            out.flush();
            return;
        }
        StringBuilder head = new StringBuilder();
        // 1 Record Type 定值 IFH
        appendValueForSG(head, "IFH");
        // 2 File Format 定值 IFILE
        appendValueForSG(head, "IFILE");
        // 3 File Type 定值 CSV
        appendValueForSG(head, "CSV");
        // 4 HSBC Connect Customer ID
        appendValueForSG(head, "ABC77371001");
        // 5 HSBCnet Customer ID
        appendValueForSG(head, "HKHBAPGHK534057005");
        // 6 File Reference 取时间戳
        SimpleDateFormat format = new SimpleDateFormat(BonusConstants.BONUS_CREATE_FILE_NAME_TIME);
        String createDate = format.format(new java.util.Date());
        appendValueForSG(head, createDate);
        // 7 File Creation Date CCYY/MM/DD 取汇款日期
        SimpleDateFormat formatRemit = new SimpleDateFormat("yyyy/MM/dd");
        appendValueForSG(head, formatRemit.format(bonusList.get(0).getRemitDate()));
        // 8 File Creation Time HH:MM:SS
        SimpleDateFormat formatRemitTime = new SimpleDateFormat("HH:mm:ss");
        appendValueForSG(head, formatRemitTime.format(bonusList.get(0).getRemitDate()));
        // 9 Authorization Type: P Instruction Level Authorised
        appendValueForSG(head, "P");
        // 10 File Version 1.0
        appendValueForSG(head, "1.0");
        // 11 Record Count 所有记录数+ 头行1 + batch行数1
        appendValueForSG(head, new Long(totalResult.getAmount() + 2L).toString());
        // 12 Local Language Character Set U801
        appendValueForSG(head, "U801");
        head.deleteCharAt(head.length() - 1);
        head.append("\r\n");
        out.print(head.toString());

        StringBuilder batch = new StringBuilder();
        // 1 Record Type
        appendValueForSG(batch, "BATHDR");
        // 2 Instruction Type
        appendValueForSG(batch, "ACH-CR");
        // 3 Total number of instructions in batch 奖金记录数
        appendValueForSG(batch, totalResult.getAmount().toString());
        // 4-6 null
        appendNullForSG(batch, 3);
        // 7 Regulatory Reporting (Batch Level)
        appendValueForSG(batch, "COMM");
        // 8-9 null
        appendNullForSG(batch, 2);
        // 10 Constant Eye Catcher @1ST@
        appendValueForSG(batch, "@1ST@");
        // 11 Value Date (YYYYMMDD)
        SimpleDateFormat formatValueDate = new SimpleDateFormat("yyyyMMdd");
        appendValueForSG(batch, formatValueDate.format(bonusList.get(0).getRemitDate()));
        // 12 TODO：First Party Account
        appendValueForSG(batch, "052422383001");
        // 13 Transaction Currency
        appendValueForSG(batch, "SGD");
        // 14 Transaction Amount
        appendValueForSG(batch, totalResult.getTotalBonus().toString());
        // 15 - 20 null
        appendNullForSG(batch, 6);
        // 21 TODO : First Party Name
        appendValueForSG(batch, "INFINITUS HEALTH PRODUCTS (SINGAPORE) PTE LTD");
        // 22 First Party Information Line 1
        appendValueForSG(batch, "80 ROBINSON ROAD?,#02-00?,SINGAPORE");
        // 23 First Party Information Line 2
        appendValueForSG(batch, "068898");
        // 24 First Party Information Line 3
        appendValueForSG(batch, "");
        // 25 null
        appendNullForSG(batch, 1);
        // 26 Payment Code E01
        appendValueForSG(batch, "E01");
        // 27 Reference Line 1
        SpmPeriod spmPeriod = spmPeriodMapper.selectByPrimaryKey(bonus.getPeriodId());
        String periodName = spmPeriod.getPeriodName();
        if (BonusConstants.BONUS_TYPE_LOCAL.equals(bonus.getBonusType())) {
            appendValueForSG(batch,
                    "INF SG LOCAL COMM " + monthConvert(periodName.substring(4)) + periodName.substring(2, 4));
        } else if (BonusConstants.BONUS_TYPE_INTER.equals(bonus.getBonusType())) {
            appendValueForSG(batch,
                    "INF SG INTL COMM " + monthConvert(periodName.substring(4)) + periodName.substring(2, 4));
        }
        // 28 -29 null
        appendNullForSG(batch, 2);
        batch.deleteCharAt(batch.length() - 1);
        batch.append("\r\n");
        out.print(batch.toString());

        StringBuilder row = new StringBuilder();
        for (BonusFinal temp : bonusList) {
            row.delete(0, row.length());
            // 1 Record Type : SECPTY
            appendValueForSG(row, "SECPTY");
            // 2 Second Party Account Number
            appendValueForSG(row, temp.getRemitBankAccount().toUpperCase());
            // 3 Second Party Name
            appendValueForSG(row, temp.getRemitBankAccountName().toUpperCase());
            // 4 Second Party Identifier
            appendValueForSG(row, temp.getMemberCode());
            // 5 -7 null
            appendNullForSG(row, 3);
            // 8 Second Party Transaction Amount
            appendValueForSG(row, temp.getRemitNetAmt().toString());
            // 9 null
            appendValueForSG(row, "");
            // 10 Second Party Reference
            if (BonusConstants.BONUS_TYPE_LOCAL.equals(temp.getBonusType())) {
                appendValueForSG(row,
                        "INF SG LOCAL COMM " + monthConvert(periodName.substring(4)) + periodName.substring(2, 4));
            } else if (BonusConstants.BONUS_TYPE_INTER.equals(temp.getBonusType())) {
                appendValueForSG(row,
                        "INF SG INTL COMM " + monthConvert(periodName.substring(4)) + periodName.substring(2, 4));
            }
            // 11 - 14 null
            appendNullForSG(row, 4);
            // 15 Advice Indicator N
            appendValueForSG(row, "N");
            // 16 WHT Indicator N
            appendValueForSG(row, "N");
            // 17 -21 null
            appendNullForSG(row, 5);
            // 22 Constant Eye Catcher @LVP@
            appendValueForSG(row, "@LVP@");
            // 23 - 34 null
            appendNullForSG(row, 12);
            // 35 Beneficiary Bank ID/SWIFT Address Code SWF
            appendValueForSG(row, "SWF");
            // 36 Beneficiary Bank ID/SWIFT Address
            String bankCode = codeService.getCodeMeaningByValue(request, "BM.SINGAPORE_BANK_SWIFT",
                    temp.getRemitBankCode());
            appendValueForSG(row, bankCode.toUpperCase());
            // 37 - 41 null
            appendNullForSG(row, 5);
            // 42 Beneficiary Bank Country 取银行code 5，6位
            appendValueForSG(row, bankCode.substring(4, 6));
            // 43 - 70 NULL
            appendNullForSG(row, 28);
            row.deleteCharAt(row.length() - 1);
            row.append("\r\n");
            out.print(row.toString());
        }
    }

    /**
     * 新加坡文件增加字段方法，以','结尾.
     * 
     * @param str
     *            对象.
     * @param value
     *            值.
     */
    private void appendValueForSG(StringBuilder str, String value) {
        str.append(value);
        str.append(',');
    }

    /**
     * 新加坡文件增加空内容.
     * 
     * @param str
     *            对象.
     * @param number
     *            遍历次数.
     */
    private void appendNullForSG(StringBuilder str, int number) {
        for (int i = 0; i < number; i++) {
            str.append(',');
        }
    }

    @Override
    public List<String> checkBonusRecord(IRequest request, Long periodId, String bonusType, Long marketId) {
        BonusFinal bonus = new BonusFinal();
        List<String> result = new ArrayList<String>();
        // String marketId =
        // request.getAttribute(SystemProfileConstants.MARKET_ID).toString();
        bonus.setMarketId(marketId);
        bonus.setPeriodId(periodId);
        bonus.setBonusType(bonusType);
        List<BonusFinal> totalResult = self().queryBonusFinal(request, bonus, 1, -1);
        if (totalResult.isEmpty()) {
            result.add("empty");
        } else {
            if (BonusConstants.BONUS_FLAG_N.equals(totalResult.get(0).getLockFlag())) {
                result.add("error");
            } else {
                result.add("success");
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateStatusByPayfall(IRequest request, List<BonusFinal> bonusFinal) {
        BonusFinal updateData = new BonusFinal();
        for (BonusFinal temp : bonusFinal) {
            updateData = new BonusFinal();
            updateData.setBonusId(temp.getBonusId());
            updateData.setRemitFlag(BonusConstants.BONUS_FLAG_N);
            updateData.setPeriodId(temp.getPeriodId());
            updateData.setMarketId(temp.getMarketId());
            updateData.setBonusType(temp.getBonusType());
            updateData.setMemberId(temp.getMemberId());
            updateData.setRemitNetAmt(temp.getRemitNetAmt().add(temp.getDeliverBankFee()));
            updateData.setDeliverBankFee(BigDecimal.ZERO);
            bonusFinalMapper.updateByPrimaryKeySelective(updateData);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void retransferOrRemain(IRequest request, List<BonusFinal> bonusFinals) throws CommMemberException {
        if (bonusFinals.isEmpty()) {
            return;
        }
        Long marketId = bonusFinals.get(0).getMarketId();
        // Long marketId =
        // Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString());
        List<String> code = paramService.getMarketParamValues(request, BonusConstants.SPM_ATUO_WRITE_OFF, marketId);
        for (BonusFinal bonusFinal : bonusFinals) {
            Member member = memberMapper.selectByPrimaryKey(bonusFinal.getMemberId());
            // 如果组织参数为空或者为N,则走retransfer，为Y则走remainingbalance
            if (!code.isEmpty() && BonusConstants.BONUS_FLAG_Y.equals(code.get(0))) {
                // 组织参数为Y则走remainingbalance
                // 支付奖金添加到remaining balance
                // 生成账务事务处理dto
                MemAccountingTrx trx = new MemAccountingTrx();
                trx.setMemberId(member.getMemberId());
                trx.setCompanyId(member.getCompanyId());
                trx.setSalesOrgId(Long.parseLong(member.getJointSite()));
                trx.setTrxDate(new Date(System.currentTimeMillis()));
                trx.setTrxType(BonusConstants.TRX_TYPE_BONUS);
                trx.setTrxSourceType(BonusConstants.BM_BONUS_FINAL);
                trx.setTrxSourceId(bonusFinal.getBonusId()); // 设置事务来源ID
                trx.setTrxSourceLineId(bonusFinal.getBonusId());
                trx.setAccountingType(MemberConstants.ACCOUNTING_TYPE_RB);
                trx.setTrxValue(bonusFinal.getDeliverAmt());
                memberBalanceTrxService.processAccountingTrx(request, trx);
                // 同时生成状态为”结余“的retransfer，
                createRetransferByFinal(request, bonusFinal, BonusConstants.RETRANSFER_STATUS_RE);
                // 修改最终奖金的发放状态为Y，processMode改为手动补发
                BonusFinal finalTemp = new BonusFinal();
                finalTemp.setBonusId(bonusFinal.getBonusId());
                finalTemp.setRemitFlag(BonusConstants.BONUS_FLAG_Y);
                finalTemp.setProcessMode(BonusConstants.BM_PROCESS_MODE_SDBF);
                bonusFinalMapper.updateByPrimaryKeySelective(finalTemp);
            } else {
                // 这笔支付失败的奖金转换为一条属于此会员的、奖金年月为此支付失败奖金的奖金年月下一个奖金年月的re-transfer奖金纪录
                createRetransferByFinal(request, bonusFinal, BonusConstants.BONUS_RETRANSFER_STATUS_NEW);
            }
            // Member member =
            // memberMapper.selectByPrimaryKey(bonusFinal.getMemberId());
            // if
            // (MemberConstants.MEM_STATUS_CHANGE_AUTO_TERMINATE.equals(member.getStatus())
            // ||
            // MemberConstants.MEM_STATUS_CHANGE_TERMINATE.equals(member.getStatus()))
            // {
            // // 会员的状态是否是终止、自动终止，如果是，直接走retransfer
            // createRetransferByFinal(request, bonusFinal,
            // BonusConstants.BONUS_RETRANSFER_STATUS_NEW);
            // } else {
            //
            // }
        }
    }

    /**
     * 根据最终奖金创建retransfer记录.
     * 
     * @param request
     *            统一上下文.
     * @param bonusFinal
     *            最终奖金记录.
     * @param status
     *            retransfer状态.
     */
    private void createRetransferByFinal(IRequest request, BonusFinal bonusFinal, String status) {
        // Long marketId =
        // Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString());
        Long marketId = bonusFinal.getMarketId();
        BonusRetransfer bonusRetransfer = new BonusRetransfer();
        bonusRetransfer.setBonusId(bonusFinal.getBonusId());
        bonusRetransfer.setMemberId(bonusFinal.getMemberId());
        List<String> currencies = paramService.getMarketParamValues(request, SystemProfileConstants.SPM_CURRENCY,
                marketId);
        if (!currencies.isEmpty()) {
            bonusRetransfer.setCurrencyCode(currencies.get(0));
        }
        bonusRetransfer.setMarketId(marketId);
        // 此支付失败奖金的奖金年月下一个奖金年月
        // SpmPeriod spmPeriod =
        // spmPeriodMapper.selectByPrimaryKey(bonusFinal.getPeriodId());
        // SpmPeriod nextPeriod =
        // bonusService.getNextOpenPeriod(request, spmPeriod);
        bonusRetransfer.setPeriodId(bonusFinal.getPeriodId());
        bonusRetransfer.setStatus(status);
        bonusRetransfer.setRetransAmt(bonusFinal.getRemitNetAmt());
        bonusRetransferService.insertRetransfer(request, bonusRetransfer);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<String> bonusPay(IRequest request, BonusReleaseCombine combine) throws CommMemberException {
        List<String> result = new ArrayList<String>();
        // 根据奖金期间，奖金类型，获取最终奖金集合
        BonusFinal bonusFinalTemp = new BonusFinal();
        bonusFinalTemp.setPeriodId(combine.getPeriodId());
        bonusFinalTemp.setBonusType(combine.getBonusType());
        bonusFinalTemp.setMarketId(combine.getMarketId());
        // 符合条件所有的最终奖金记录
        List<BonusFinal> allBonus = self().queryBonusFinal(request, bonusFinalTemp, 1, -1);
        // 符合条件的支付失败奖金记录
        bonusFinalTemp.setRemitFlag(BonusConstants.BONUS_FLAG_N);
        List<BonusFinal> fallBonus = self().queryBonusFinal(request, bonusFinalTemp, 1, -1);
        // 该集合若为空，提示无奖金支付
        if (allBonus.isEmpty()) {
            result.add("empty");
            return result;
        }
        // 若锁定flag为Y，则提示奖金已支付
        // 若锁定flag为N，则判断是否已经自动失败操作，并重新设置预付款日期，将锁定flag改为Y
        // 若auto-flag为N，则执行自动失败操作，并更改auto-flag为Y
        // 若auto-flag为Y,则跳过自动失败
        if (BonusConstants.BONUS_FLAG_Y.equals(allBonus.get(0).getLockFlag())) {
            result.add("error");
            return result;
        } else {
            // 判断为第一次支付，auto-flag为N
            if (!allBonus.isEmpty() && BonusConstants.BONUS_FLAG_N.equals(allBonus.get(0).getAutoFaliledFlag())) {
                // 若auto-flag为N，则执行自动失败操作，并更改auto-flag为Y
                if (!fallBonus.isEmpty() && BonusConstants.BONUS_FLAG_N.equals(fallBonus.get(0).getAutoFaliledFlag())) {
                    updateLockByEach(fallBonus, null, null, null, BonusConstants.BM_PROCESS_MODE_ZDSB);
                    self().retransferOrRemain(request, fallBonus);
                }
                updateLockByEach(allBonus, null, BonusConstants.BONUS_FLAG_Y, null, null);
            }
            // 若锁定flag为N，重新设置预付款日期，将锁定flag改为Y
            updateLockByEach(allBonus, BonusConstants.BONUS_FLAG_Y, null, combine.getRemitDate(), null);
        }
        result.add("success");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<String> unlockBonusFinal(IRequest request, BonusReleaseCombine combine) {
        List<String> result = new ArrayList<String>();
        // 根据奖金期间，奖金类型，获取最终奖金集合
        BonusFinal bonusFinalTemp = new BonusFinal();
        bonusFinalTemp.setPeriodId(combine.getPeriodId());
        bonusFinalTemp.setBonusType(combine.getBonusType());
        bonusFinalTemp.setMarketId(combine.getMarketId());
        // 符合条件所有的最终奖金记录
        List<BonusFinal> allBonus = self().queryBonusFinal(request, bonusFinalTemp, 1, -1);
        if (allBonus.isEmpty()) {
            result.add("empty");
            return result;
        } else {
            if (BonusConstants.BONUS_FLAG_N.equals(allBonus.get(0).getLockFlag())) {
                result.add("error");
                return result;
            } else {
                updateLockByEach(allBonus, BonusConstants.BONUS_FLAG_N, null, null, null);
                result.add("success");
                return result;
            }
        }
    }

    private void updateLockByEach(List<BonusFinal> bonusFinal, String lockFlag, String autoFlag, Date remitDate,
            String processMode) {
        int size = bonusFinal.size() % BonusConstants.BONUS_LIST_SIZE != 0
                ? bonusFinal.size() / BonusConstants.BONUS_LIST_SIZE + 1
                : bonusFinal.size() / BonusConstants.BONUS_LIST_SIZE;
        for (int i = 0; i < size; i++) {
            List<BonusFinal> temp = null;
            if (((i + 1) * BonusConstants.BONUS_LIST_SIZE) > bonusFinal.size()) {
                temp = bonusFinal.subList(i * BonusConstants.BONUS_LIST_SIZE, bonusFinal.size());
            } else {
                temp = bonusFinal.subList(i * BonusConstants.BONUS_LIST_SIZE, (i + 1) * BonusConstants.BONUS_LIST_SIZE);
            }
            bonusFinalMapper.updateLockFlag(temp, lockFlag, autoFlag, remitDate, processMode);
        }
    }

    @Override
    public List<String> bonusCheckAndReissue(IRequest request, BonusFinal bonusFinal) {
        List<String> result = new ArrayList<String>();
//        Long marketId = Long.parseLong(request.getAttribute(SystemProfileConstants.MARKET_ID).toString());
        Long marketId = bonusFinal.getMarketId();
        // 首先判断该奖金记录是否可以补发：只能补发最近一次combine生成的奖金记录
        // 查询最新一条final数据相同类型的奖金类型，奖金期间的数据，判断补发记录是否和最新记录相同，并且补发记录状态为支付失败且自动失败flag为Y
        BonusFinal lastBonusFinal = bonusFinalMapper.queryLastRecordByMarket(marketId);
        
        List<String> code = paramService.getMarketParamValues(request, BonusConstants.SPM_ATUO_WRITE_OFF, marketId);
        // 若退回到remainingbalance，则不允许进行手动补发
        if (!code.isEmpty() && BonusConstants.BONUS_FLAG_Y.equals(code.get(0))) {
            result.add("error");
        } else {
            if (BonusConstants.BONUS_FLAG_N.equals(bonusFinal.getRemitFlag())
                    && BonusConstants.BONUS_FLAG_Y.equals(bonusFinal.getAutoFaliledFlag())
                    && lastBonusFinal.getPeriodId().equals(bonusFinal.getPeriodId())
                    && lastBonusFinal.getBonusType().equals(bonusFinal.getBonusType())) {
                // 若符合条件修改字段processMode为手动补发，支付状态改为成功，并删除源奖金id为该条记录的retransfer记录
                BonusFinal tempData = new BonusFinal();
                tempData.setBonusId(bonusFinal.getBonusId());
                tempData.setRemitFlag(BonusConstants.BONUS_FLAG_Y);
                if (BonusConstants.BM_PROCESS_MODE_YHTK.equals(bonusFinal.getProcessMode())) {
                    tempData.setProcessMode(BonusConstants.BM_PROCESS_MODE_TKBF);
                } else {
                    tempData.setProcessMode(BonusConstants.BM_PROCESS_MODE_SDBF);
                }
                // 计算市场手续费
                if (bonusFinal.getDeliverBankFee().compareTo(BigDecimal.ZERO) == 0) {
                    SpmMarket market = spmMarketMapper.selectByPrimaryKey(marketId);
                    String marketCode = market.getCode();
                    String deliverBankCode = codeService.getCodeMeaningByValue(request, "BM.PAID_BANK", marketCode);
                    List<SpmBank> bankInfo = new ArrayList<SpmBank>();
                    List<SpmBankCharges> charges = new ArrayList<SpmBankCharges>();
                    if (null != deliverBankCode) {
                        bankInfo = spmbankMapper.queryBankByNumber(deliverBankCode);
                        if (!bankInfo.isEmpty()) {
                            Long remitBankId = bankInfo.get(0).getBankId();
                            SpmBankCharges tempCharges = new SpmBankCharges();
                            tempCharges.setBankId(remitBankId);
                            charges = spmBankChargesMapper.querySpmBankCharces(tempCharges);
                        }
                    }
                    BigDecimal bankFee = BigDecimal.ZERO;
                    BigDecimal deliveryAmt = bonusFinal.getDeliverAmt();
                    if (!bankInfo.isEmpty() && !charges.isEmpty()) {
                        for (SpmBankCharges temp : charges) {
                            if (deliveryAmt.compareTo(new BigDecimal(temp.getBonusFrom())) >= 0
                                    && deliveryAmt.compareTo(new BigDecimal(temp.getBonusTo())) < 0) {
                                if (BANK_CHARGES_TYPE_FIX.equals(temp.getChargeType())) {
                                    bankFee = temp.getCharges();
                                } else if (BANK_CHARGES_TYPE_PER.equals(temp.getChargeType())) {
                                    bankFee = deliveryAmt
                                            .multiply(temp.getCharges().divide(new BigDecimal(BANK_CHARGES_PERCENT)))
                                            .setScale(2, BigDecimal.ROUND_HALF_UP);
                                }
                                break;
                            }
                        }
                    }
                    tempData.setDeliverBankFee(bankFee);
                    tempData.setRemitNetAmt(deliveryAmt.subtract(bankFee));
                }

                bonusFinalMapper.updateByPrimaryKeySelective(tempData);
                BonusRetransfer retransfer = new BonusRetransfer();
                retransfer.setBonusId(bonusFinal.getBonusId());
                bonusRetransferMapper.deleteRecordByFinal(retransfer);
                result.add("success");
            } else {
                result.add("error1");
            }
        }
        return result;
    }

    @Override
    public int updateComments(IRequest request, BonusFinal bonusFinal) {
        return bonusFinalMapper.updateByPrimaryKeySelective(bonusFinal);
    }

}
