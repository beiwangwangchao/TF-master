/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 组织属性获取Service.
 * 
 * @author chenjingxiong
 */
public interface IParamService extends ProxySelf<IParamService> {

    /**
     * 获取当前组织属性值.(适用于不知道参数配置在哪个层次，根据组织层级优先关系一层一层查找).
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param orgType
     *            组织类型：INV、SALES
     * @return 属性值列表
     */
    List<String> getParamValues(IRequest request, String paramName, String orgType);

    /**
     * 获取属性值.(适用于不知道参数配置在哪个层次，根据组织层级优先关系一层一层查找).
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
    List<String> getParamValues(IRequest request, String paramName, String orgType, Long orgId);
    
    
    /**
     * 获取销售组织参数.(适用于不知道参数配置在哪个层次，根据组织层级优先关系一层一层查找).
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param salesOrgId
     *            销售区域ID
     * @return 属性值列表
     */
    List<String> getSalesParamValues(IRequest request, String paramName, Long salesOrgId);
    
    /**
     * 获取库存组织参数.(适用于不知道参数配置在哪个层次，根据组织层级优先关系一层一层查找).
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param invOrgId
     *            库存组织ID
     * @return 属性值列表
     */
    List<String> getInvParamValues(IRequest request, String paramName, Long invOrgId);
    
    /**
     * 获取市场组织参数.(适用于准确地知道参数就配置在市场层次).
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param marketId
     *            市场ID
     * @return 属性值列表
     */
    List<String> getMarketParamValues(IRequest request, String paramName, Long marketId);
    
    /**
     * 获取OU组织参数.(适用于准确地知道参数就配置在业务实体层次).
     * 
     * @param request
     *            统一上下文
     * @param paramName
     *            参数名称
     * @param opertingUnitId
     *            业务实体ID
     * @return 属性值列表
     */
    List<String> getOuParamValues(IRequest request, String paramName, Long opertingUnitId);

}
