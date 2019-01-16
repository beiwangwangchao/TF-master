/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 单位接口.
 * 
 * @author mclin
 */
public interface IUomService extends ProxySelf<IUomService> {
    /**
     * 根据条件查出单位信息(包装类型).
     * 
     * @param request
     *            请求上下文
     * @param itemId
     *            商品ID
     * @param uomCode
     *            主单位代码
     * @return iuc 单位转换Dto
     */
    List<InvUnitConvert> queryUomSelection(IRequest request, Long itemId, String uomCode);
    
    /**
     * 查出初始单位信息(包装类型).
     * 
     * @param request
     *            请求上下文
     * @return List 单位Dto列表
     */
    List<InvUnitOfMeasureB> queryAllUom(IRequest request);
}
