/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.product.product.service.IInvUnitConvertService;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.ItemPackType;
import com.lkkhpg.dsis.common.product.mapper.InvItemMapper;
import com.lkkhpg.dsis.common.product.mapper.InvUnitConvertMapper;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 商品单位转换率接口实现类.
 * 
 * @author mclin
 */
@Transactional
@Service
public class InvUnitConvertServiceImpl implements IInvUnitConvertService {

    @Autowired
    private InvUnitConvertMapper itemUnitConvertMapper;

    @Autowired
    private InvItemMapper invItemMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public InvUnitConvert getInvUnitConvert(IRequest requestContext, InvUnitConvert invUnitConvert) {
        if (invUnitConvert.getItemId() == null || StringUtils.isEmpty(invUnitConvert.getFromUom())
                || StringUtils.isEmpty(invUnitConvert.getToUom())) {
            return null;
        }
        return itemUnitConvertMapper.getInvUnitConvert(invUnitConvert);
    }

    @Override
    public List<ItemPackType> queryItemPackTypes(IRequest request, InvUnitConvert invUnitConvert) {
        // 商品主单位
        InvItem item = invItemMapper.selectByPrimaryKey(invUnitConvert.getItemId());
        if (item == null) {
            return Collections.emptyList();
        }
        List<ItemPackType> iucs = new ArrayList<ItemPackType>();
        // 将商品自身的的单位放进list。
        ItemPackType type = new ItemPackType();
        type.setSourcePackingType(item.getUomCode());
        type.setPackingType(item.getUomCode());
        type.setConvertRate(1L);
        iucs.add(type);

        invUnitConvert.setFromUom(item.getUomCode());
        List<InvUnitConvert> itemUnits = itemUnitConvertMapper.selectInvUnitConverts(invUnitConvert);
        // 根据转化率生成主单位
        for (InvUnitConvert itemU : itemUnits) {
            type = new ItemPackType();
            type.setSourcePackingType(itemU.getFromUom());
            type.setPackingType(itemU.getToUom());
            type.setConvertRate(itemU.getConvertRate());
            iucs.add(type);
        }
        return iucs;
    }
}
