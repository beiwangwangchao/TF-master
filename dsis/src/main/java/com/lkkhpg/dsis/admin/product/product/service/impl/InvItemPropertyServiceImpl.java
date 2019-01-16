/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.product.product.service.IInvItemPropertyService;
import com.lkkhpg.dsis.common.constant.InventoryConstants;
import com.lkkhpg.dsis.common.product.dto.InvItemProperty;
import com.lkkhpg.dsis.common.product.dto.InvItemPropertyV;
import com.lkkhpg.dsis.common.product.mapper.InvItemPropertyMapper;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 商品库存组织参数.
 * 
 * @author chuangsheng.zhang.
 */
@Service
public class InvItemPropertyServiceImpl implements IInvItemPropertyService {

    @Autowired
    private InvItemPropertyMapper invItemPropertyMapper;

    @Override
    public List<InvItemProperty> queryInvItemProperties(InvItemProperty invItemProperty) {
        return invItemPropertyMapper.queryInvItemProperties(invItemProperty);
    }

    /**
     * 判断商品是否启用批次控制.
     * 
     * @param request
     *            请求上下文
     * @param organizationId
     *            库存组织Id
     * @param itemId
     *            商品Id
     * @return 批次控制
     */
    @Override
    public boolean isLotControl(IRequest request, Long organizationId, Long itemId) {
        InvItemPropertyV propertyV = getItemProperties(request, itemId, organizationId);
        
        if (propertyV != null && BaseConstants.YES.equals(propertyV.getLotControlFlag())) {
            return true;
        }
        return false;
    };

    /**
     * 判断商品是否计算库存.
     * 
     * @param request
     *            请求上下文
     * @param organizationId
     *            库存组织Id
     * @param itemId
     *            商品Id
     * @return 是否计算库存
     */
    @Override
    public boolean isCountStack(IRequest request, Long organizationId, Long itemId) {
        InvItemPropertyV invItemPropertyV = getItemProperties(request, itemId, organizationId);
        
        if (invItemPropertyV != null) {
            if (invItemPropertyV.getCountStockFlag() != null) {
                if (invItemPropertyV.getCountStockFlag().equals(InventoryConstants.PACKAGE_COUNT_STOCK_FLAG_ENABLE)
                        || invItemPropertyV.getCountStockFlag()
                                .equals(InventoryConstants.ITEM_COUNT_STOCK_FLAG_ENABLE)) {
                    return true;
                }

            }
        }
        // ADD 2016/04/30
        return false;
    };
    
    private InvItemPropertyV getItemProperties(IRequest request, Long itemId, Long invOrgId) {
        InvItemPropertyV pv = invItemPropertyMapper.queryItemPropertyVByItemIdAndOrgId(itemId, invOrgId);
        if (pv == null) {
            pv = invItemPropertyMapper.queryItemPropertyVByItemIdAndOrgId(itemId, null);
        }
        return pv;
    }
}
