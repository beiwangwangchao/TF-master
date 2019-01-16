/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmTax;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 税率Service.
 * 
 * @author chenjingxiong
 */
public interface ITaxService extends ProxySelf<ITaxService> {

    /**
     * 获取当前配置的税率定义.
     * 
     * @param request
     *            统一上下文定义
     * @return 当前税定义
     */
    SpmTax getTax(IRequest request);

    /**
     * 根据销售组织获取税率定义.
     * 
     * @param request
     *            统一上下文定义
     * @param salesOrgId
     *            销售组织Id
     * @return 当前税定义
     */
    SpmTax getTaxBySalesOrg(IRequest request, Long salesOrgId);

    /**
     * 计算税额.
     * 
     * @param request
     *            统一上下文定义
     * @param amount
     *            要计算的金额
     * @return 税额
     */
    BigDecimal getTaxAmount(IRequest request, BigDecimal amount);

    /**
     * 计算税额.
     * 
     * @param request
     *            统一上下文定义
     * @param amount
     *            要计算的金额
     * @param tax
     *            税
     * @return 税额
     */
    BigDecimal getTaxAmount(IRequest request, BigDecimal amount, SpmTax tax);

    /**
     * 查询税率.
     * 
     * @param request
     *            请求上下文
     * @param tax
     *            税率DTO
     * @return 税率List
     */
    List<SpmTax> queryTax(IRequest request, SpmTax tax);

    /**
     * 获取税率.(适用于不知道参数配置在哪个层次，根据组织层级优先关系一层一层查找).
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param orgType
     *            组织类型：INV、SALES
     * @param orgId
     *            组织ID(销售区域或者库存组织的ID)
     * @return 属性值列表
     */
    List<SpmTax> queryTaxPercentByParams(IRequest request, String paramName, String orgType, Long orgId);
}
