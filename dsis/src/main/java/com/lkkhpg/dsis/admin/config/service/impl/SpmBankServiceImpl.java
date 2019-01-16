/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmBankService;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmBankCharges;
import com.lkkhpg.dsis.common.config.mapper.SpmBankChargesMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmBankMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;

/**
 * 银行Service.
 * @author liuxuan.
 *
 */
@Service
@Transactional
public class SpmBankServiceImpl implements ISpmBankService {
    
    @Autowired
    private SpmBankMapper spmBankMapper;
    
    @Autowired
    private SpmBankChargesMapper spmBankCharcesMapper;
    
    @Override
    public List<SpmBank> querySpmBank(IRequest request, SpmBank spmBank, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmBank> list = spmBankMapper.queryBySpmBank(spmBank);
        return list;
    }
    
   /**
    * 查询银行详情.
    */
    public List<SpmBankCharges> querSpmBankCharces(IRequest request, SpmBankCharges 
            spmBankCharges, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<SpmBankCharges> list = spmBankCharcesMapper.querySpmBankCharces(spmBankCharges);
        return list;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SpmBank> saveSpmBank(IRequest request, List<SpmBank> spmBanks) throws SystemProfileException {
        if (!self().validateSpmBank(request, spmBanks)) {
            throw new SystemProfileException(SystemProfileException.MSG_ERROR_BANK_BONUS_AMOUNT, null);
        }
        for (SpmBank spmBank : spmBanks) {
            if (DTOStatus.ADD.equals(spmBank.get__status())) {
                checkNumber(spmBank);
                spmBankMapper.insertSelective(spmBank);
                if (!spmBank.getSpmBankChargess().isEmpty()) {
                    processSpmBankCharces(spmBank.getSpmBankChargess(), spmBank.getBankId(), spmBank.getMarketId());
                }
            } else if (DTOStatus.UPDATE.equals(spmBank.get__status())) {
                spmBankMapper.updateByPrimaryKeySelective(spmBank);
                if (!spmBank.getSpmBankChargess().isEmpty()) {
                    processSpmBankCharces(spmBank.getSpmBankChargess(), spmBank.getBankId(), spmBank.getMarketId());
                }
            }
        }
        return spmBanks;
    }
    
    /**.
     * 判断是添加还是修改
     * @param SpmBankChargess
     * @param bankId
     * @param marketId
     * @throws SystemProfileException
     */
    private void processSpmBankCharces(List<SpmBankCharges> SpmBankChargess, Long bankId, 
            Long marketId) throws SystemProfileException {
        for (SpmBankCharges line : SpmBankChargess) {
            if (DTOStatus.ADD.equals(line.get__status())) {
                line.setBankId(bankId);
                line.setMarketId(marketId);
                if (line.getBonusFrom().compareTo(line.getBonusTo()) >= 0) {
                    throw new SystemProfileException(SystemProfileException.MSG_ERROR_BANK_BONUS_AMOUNT, null);
                }
                if (isExistSpmBank(line)) {
                    throw new SystemProfileException(SystemProfileException.MSG_ERROR_BANK_BONUS_AMOUNT, null);
                }
                spmBankCharcesMapper.insertSelective(line);
            } else if (DTOStatus.UPDATE.equals(line.get__status())) {
                if (line.getBonusFrom().compareTo(line.getBonusTo()) >= 0) {
                    throw new SystemProfileException(SystemProfileException.MSG_ERROR_BANK_BONUS_AMOUNT, null);
                }
                if (isExistSpmBank(line)) {
                    throw new SystemProfileException(SystemProfileException.MSG_ERROR_BANK_BONUS_AMOUNT, null);
                }
                spmBankCharcesMapper.updateByPrimaryKeySelective(line);
            } else if (DTOStatus.DELETE.equals(line.get__status())) {
                spmBankCharcesMapper.deleteByPrimaryKey(line.getBankChargesId());
            }
        }
    }
    
    @Override
    public Boolean validateSpmBank(IRequest request, List<SpmBank> spmBanks) {
        for (SpmBank spmBank : spmBanks) {
            // 如果子行不为空，则校验
            if (spmBank.getSpmBankChargess() != null) {
                // 创建新集合
                HashSet<String> SpmBankSet = new HashSet<String>();
                for (SpmBankCharges spmBankCharges : spmBank.getSpmBankChargess()) {
                    // 校验集合数据
                    Object[] ids = { spmBankCharges.getCharges() };
                    String key = StringUtils.join(ids, ":");
                    if (SpmBankSet.contains(key)) {
                        return false;
                    } else {
                        SpmBankSet.add(key);
                    }
                }
            }
        }
    
        for (SpmBank spmBank : spmBanks) {
            // 如果ID为空，则表示更新数据，需要校验数据库
            if (spmBank.getBankId() != null) {
                if (spmBank.getSpmBankChargess() != null) {
                    for (SpmBankCharges spmBankCharges : spmBank.getSpmBankChargess()) {
                        // 如果插入数据，需要校验数据库是否已经存在
                        if (DTOStatus.ADD.equals(spmBankCharges.get__status())) {
                            spmBankCharges.setBankId(spmBank.getBankId());
                            if (self().isExistSpmBankChargess(request, spmBankCharges)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * 校验银行编号是否唯一.
     * @param spmBank
     * @throws ItemException
     */
    private void checkNumber(SpmBank spmBank) throws SystemProfileException {
        Integer result = spmBankMapper.queryBySpmBankNumber(spmBank.getBankNumber());
        if (result > 0) {
            throw new SystemProfileException(SystemProfileException.MSG_ERROR_BANK_CHARCES, null);
        }
    }

    @Override
    public boolean isExistSpmBankChargess(IRequest request, SpmBankCharges spmBankCharges) {
        if (spmBankCharges.getBankId() == null) {
            return false;
        }
        int i = spmBankCharcesMapper.queryCount(spmBankCharges);
        if (i <= 0) {
            return false;
        }
        return true;
    }
    
    private Boolean isExistSpmBank(SpmBankCharges spmBankCharges) {
        int i = spmBankCharcesMapper.queryCount(spmBankCharges);
        if (i <= 0) {
            return false;
        }
        return true;
    }
}