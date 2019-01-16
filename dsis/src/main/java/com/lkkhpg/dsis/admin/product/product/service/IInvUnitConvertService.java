/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service;

import java.util.List;

import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.ItemPackType;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 商品转换率接口类.
 * 
 * @author mclin
 */
public interface IInvUnitConvertService extends ProxySelf<IInvUnitConvertService> {
    /**
     * 查出商品单位转换率.
     * 
     * @param requestContext
     *            请求上下文
     * @param invUnitConvert
     *            商品转换率Dto
     * @return ItemUnitConvert 商品单位转换率Dto
     */
    InvUnitConvert getInvUnitConvert(IRequest requestContext, InvUnitConvert invUnitConvert);

    /**
     * 根据单位转化率查询出商品下所有单位.
     * 
     * @param request
     *            请求上下文
     * @param itemUnitConvert
     *            商品转化率
     * @return 商品包装行类型list
     */
    List<ItemPackType> queryItemPackTypes(IRequest request, InvUnitConvert itemUnitConvert);

}
