/*
 *
 */
package com.lkkhpg.dsis.common.service;

import com.lkkhpg.dsis.common.inventory.dto.InvOnhandQuantity;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存现有量Service接口.
 * 
 * @author hanrui.huang
 */
public interface IInvOnhandQuantityService extends ProxySelf<IInvOnhandQuantityService> {

    /**
     * 查库存现有量.
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            在手量
     * @return 库存现有量Dto
     */
    InvOnhandQuantity getOnhandQuantity(IRequest request, InvOnhandQuantity invOnhandQuantity);

    /**
     * 获取库存现有量.
     * 
     * @param request
     *            统一上下文
     * @param criteria
     *            约束条件.
     * @return 库存量
     */
    BigDecimal queryOnhandQuantity(IRequest request, InvOnhandQuantity criteria);
    
    /**
     * 获取库存实际可用量(现有量-保留量).
     * @param request 
     *            统一上下文.
     * @param criteria 
     *            约束条件.
     * @return 库存实际可用量
     */
    BigDecimal getAvailableQuantity(IRequest request, InvOnhandQuantity criteria);

    /**
     * add by furong.tang
     * 获取库存实际数量(库存量)
     * @param request
     *            统一上下文.
     * @param criteria
     *            约束条件.
     * @return 库存实际数量
     */
    BigDecimal getQuantity(IRequest request, InvOnhandQuantity criteria);
    
    /**
     * 创建库存量.
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            库存量DTO
     * @return 库存现有量List
     */
    InvOnhandQuantity createInvOnhandQuantity(IRequest request, @StdWho InvOnhandQuantity invOnhandQuantity);

    /**
     * 查询库存现有量.
     * 
     * @param request
     *            请求上下文
     * @param invOnhandQuantity
     *            库存量DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @return 库存现有量List
     */
    List<InvOnhandQuantity> queryOnhandQty(IRequest request, InvOnhandQuantity invOnhandQuantity, int page,
            int pagesize);
}
