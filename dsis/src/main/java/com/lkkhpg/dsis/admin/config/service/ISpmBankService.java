/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmBankCharges;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 银行Service.
 * @author liuxuan
 *
 */
public interface ISpmBankService extends ProxySelf<ISpmBankService> {
    
    /**
     * 验证手续费.
     * @param request 请求上下文
     * @param spmBanks 头表集合
     * @return true/false 返回值
     */
    Boolean validateSpmBank(IRequest request, List<SpmBank> spmBanks);
    
    /**
     * 查询银行.
     * @param request 请求上下文
     * @param spmBank 查询条件
     * @param page 页数
     * @param pagesize 每页记录数
     * @return 银行
     */
    List<SpmBank> querySpmBank(IRequest request, SpmBank spmBank, int page, int pagesize);
    
    /**
     * 查询银行手续费详情.
     * @param request  请求上下文
     * @param spmBankCharges 查询条件
     * @param page 页数
     * @param pagesize 每页记录数
     * @return 银行手续费
     */
    List<SpmBankCharges> querSpmBankCharces(IRequest request, SpmBankCharges spmBankCharges, int page, int pagesize);
    
    /**
     * 保存.
     * @param request  请求上下文
     * @param spmBanks 银行信息
     * @return 已保存银行
     * @throws SystemProfileException 系统配置异常
     */
    List<SpmBank> saveSpmBank(IRequest request, @StdWho List<SpmBank> spmBanks) throws SystemProfileException;
    
    /**
     * 检验是否唯一.
     * @param request 请求上下文
     * @param spmBankCharges 行表集合
     * @return true/false 返回值
     */
    boolean isExistSpmBankChargess(IRequest request, SpmBankCharges spmBankCharges);
}