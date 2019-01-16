/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.product.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lkkhpg.dsis.admin.product.product.service.IUomService;
import com.lkkhpg.dsis.common.product.dto.InvUnitConvert;
import com.lkkhpg.dsis.common.product.dto.InvUnitOfMeasureB;
import com.lkkhpg.dsis.common.product.mapper.InvUnitConvertMapper;
import com.lkkhpg.dsis.common.product.mapper.InvUnitOfMeasureBMapper;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 单位接口实现类.
 * 
 * @author mclin
 */
@Transactional
@Service
public class UomServiceImpl implements IUomService {

    @Autowired
    private InvUnitOfMeasureBMapper invUnitOfMeasureBMapper;
    
    @Autowired
    private InvUnitConvertMapper invUnitConvertMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InvUnitConvert> queryUomSelection(IRequest request, Long itemId, String uomCode) {
        List<InvUnitConvert> iucs = new ArrayList<>();
        if (itemId == null || StringUtils.isEmpty(uomCode)) {
            return iucs;
        }
        InvUnitOfMeasureB uom = invUnitOfMeasureBMapper.getUom(uomCode);
        if (uom == null) {
            return iucs;
        }
        //找出该商品主单位可以转换的所有目标单位
        iucs = invUnitConvertMapper.queryIucSelection(itemId, uomCode);
        InvUnitConvert iuc = new InvUnitConvert();
        iuc.setToUom(uom.getUomCode());
        iuc.setFromUom(uom.getUomCode());
        iuc.setName(uom.getName());
        iucs.add(iuc);
        
        return iucs;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InvUnitOfMeasureB> queryAllUom(IRequest request) {
        return invUnitOfMeasureBMapper.queryAllUom();
    }

}
