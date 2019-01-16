/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvItemProperty;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 商品库存组织参数.
 * 
 * @author chuangsheng.zhang.
 */
public interface IInvItemPropertyService {

    /**
     * 查询商品库存组织参数.
     * 
     * @param invItemProperty
     *            商品库存组织参数dto
     * @return 商品库存组织参数
     */
    List<InvItemProperty> queryInvItemProperties(InvItemProperty invItemProperty);

    /**
     * 判断商品是否启用批次控制.
     * 
     * @param request
     *            请求上下文
     * @param organizationId
     *            库存组织Id
     * @param itemId
     *            商品Id
     * @return 批次控制 boolean
     * @author frank.li
     */
    boolean isLotControl(IRequest request, Long organizationId, Long itemId);
    
    /**
     * 判断商品是否计算库存.
     * 
     * @param request
     *            请求上下文
     * @param organizationId
     *            库存组织Id
     * @param itemId
     *            商品Id
     * @return 是否计算库存 boolean
     * @author shenqb
     */
    boolean isCountStack(IRequest request, Long organizationId, Long itemId);
}
