/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import com.lkkhpg.dsis.admin.order.service.ISalesTypeService;
import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.common.order.dto.SalesType;
import com.lkkhpg.dsis.common.order.mapper.OrderTypeMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesTypeMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 销售类型维护Service实现类.
 * 
 * @author zhiwei.zhang@hand-china.com 
 * [#PE20-4][ADD],2016-12-06 14:20:03.
 */
@Service
@Transactional
public class SalesTypeServiceImpl implements ISalesTypeService {
	
	@Autowired
	private SalesTypeMapper salesTypeMapper;
	
	@Autowired
	private OrderTypeMapper orderTypeMapper;

	@Override
	@Transactional
	public List<SalesType> batchUpdate(IRequest requestContext, List<SalesType> salesTypes, OrderType orderType) {
		
		if(orderType.getOrderTypeId() != null){
			orderTypeMapper.update(orderType);
			for (SalesType salesType : salesTypes) {
				if(salesType.getSalesTypeId() != null){
					if("delete".equals(salesType.get__status())){
						delete(salesType);
					}else{
						self().updateSalesType(salesType);
					}
				}else{
					salesType.setOrderTypeId(orderType.getOrderTypeId());
					self().createSalesType(salesType);
				}
	        }
		}else{
			orderTypeMapper.insert(orderType);
			for (SalesType salesType : salesTypes) {
	        	salesType.setOrderTypeId(orderType.getOrderTypeId());
	        	self().createSalesType(salesType);
	        }
		}
		
        return salesTypes;
	}

	@Override
	public SalesType updateSalesType(SalesType salesType) {
		salesTypeMapper.update(salesType);
		return salesType;
	}

	@Override
	public SalesType createSalesType(SalesType salesType) {
		salesTypeMapper.insert(salesType);
		return salesType;
	}

	@Override
	public List<SalesType> queryByOrderTypeId(IRequest createRequestContext, Long orderTypeId) {
		return salesTypeMapper.queryByOrderTypeId(orderTypeId);
	}

	@Override
	public void batchDelete(IRequest requestContext, List<SalesType> salesTypes) {
		if (salesTypes == null || salesTypes.isEmpty()) {
            return;
        }
        for (SalesType salesType : salesTypes) {
        	delete(salesType);
        }
	}

	private void delete(SalesType salesType) {
		if (salesType == null || salesType.getSalesTypeId() == null) {
            return;
        }
		salesTypeMapper.deleteByPrimaryKey(salesType.getSalesTypeId());
	}


}
