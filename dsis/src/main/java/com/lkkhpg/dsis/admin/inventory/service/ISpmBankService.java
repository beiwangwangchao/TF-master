/*
 *
 */
package com.lkkhpg.dsis.admin.inventory.service;

import java.util.List;

import com.lkkhpg.dsis.admin.inventory.exception.InventoryException;
import com.lkkhpg.dsis.common.config.dto.SpmBank;
import com.lkkhpg.dsis.common.config.dto.SpmBankCharges;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
/**
 * 刘轩.
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
     * @return 查询结果
     */
    List<SpmBank> querySpmBank(IRequest request, SpmBank spmBank, int page, int pagesize);
    
    /**
     * 查询银行详情.
     * @param request 请求上下文
     * @param spmBankCharces  查询条件
     * @param page 页数
     * @param pagesize 每页记录数
     * @return 查询结果
     * @return银行手续费
     */
    List<SpmBankCharges> querSpmBankCharces(IRequest request, SpmBankCharges spmBankCharces, int page, int pagesize);
    
    /**
     * 保存.
     * @param request 请求上下文
     * @param spmBanks 银行
     * @return 已保存银行
     * @throws InventoryException 库存统一异常
     */
    List<SpmBank> saveSpmBank(IRequest request, @StdWho List<SpmBank> spmBanks) throws InventoryException;
    
    /**
     * 检验是否唯一.
     * @param request 请求上下文
     * @param spmBankCharces 行表集合
     * @return true/false 返回值
     */
    boolean isExistSpmBankCharcess(IRequest request, SpmBankCharges spmBankCharces);
}