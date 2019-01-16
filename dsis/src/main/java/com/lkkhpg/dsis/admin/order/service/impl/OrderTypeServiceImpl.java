/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.order.service.IOrderTypeService;
import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.common.order.dto.SalesType;
import com.lkkhpg.dsis.common.order.mapper.OrderTypeMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesTypeMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 订单类型维护Service实现类.
 * 
 * @author zhiwei.zhang@hand-china.com [#PE20-4][ADD],2016-12-06 14:20:03.
 */
@Service
@Transactional
public class OrderTypeServiceImpl implements IOrderTypeService {

    @Autowired
    private OrderTypeMapper orderTypeMapper;

    @Autowired
    private SalesTypeMapper salesTypeMapper;

    @Override
    public List<OrderType> batchUpdate(IRequest requestContext, List<OrderType> orderTypes) {
        for (OrderType orderType : orderTypes) {
            if (orderType.getOrderTypeId() != null) {
                self().updateOrderType(orderType);
            } else {
                self().createOrderType(orderType);
            }
        }
        return orderTypes;
    }

    @Override
    public OrderType updateOrderType(OrderType orderType) {
        orderTypeMapper.update(orderType);
        return orderType;
    }

    @Override
    public OrderType createOrderType(OrderType orderType) {
        orderTypeMapper.insert(orderType);
        return orderType;
    }

    @Override
    public List<OrderType> queryByOrderType(IRequest createRequestContext, OrderType orderType, int page, int pagesize) {
    	PageHelper.startPage(page, pagesize);
    	return orderTypeMapper.queryByOrderType(orderType);
    }

    @Override
    @Transactional
    public void batchDelete(IRequest requestContext, List<OrderType> orderTypes) {
        if (orderTypes == null || orderTypes.isEmpty()) {
            return;
        }
        for (OrderType orderType : orderTypes) {
            salesTypeMapper.deleteByOrderTypeId(orderType.getOrderTypeId());
            delete(orderType);
        }
    }

    private void delete(OrderType orderType) {
        if (orderType == null || orderType.getOrderTypeId() == null) {
            return;
        }
        orderTypeMapper.deleteByPrimaryKey(orderType.getOrderTypeId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lkkhpg.dsis.admin.order.service.IOrderTypeService#
     * querySalesTypeForOrder(com.lkkhpg.dsis.platform.core.IRequest,
     * java.lang.Long, java.lang.String, java.lang.String)
     */
    @Override
    public List<SalesType> querySalesTypeForOrder(IRequest request, Long salesOrgId, String orderType,
                                                  String userType) {

        String salesType = null;

       /* if (!StringUtils.isEmpty(userType)) {
            if (UserConstants.USER_TYPE_IPONT.equals(userType)) {
                switch (userType) {
                case OrderConstants.ORDER_TYPE_BIGF:
                    salesType = OrderConstants.LINE_SALETYPE_GIFT;
                    break;
                default:
                    salesType = OrderConstants.LINE_SALETYPE_PURC;
                    break;
                }
            }
        }*/
        return salesTypeMapper.querySalesTypeForOrder(salesOrgId, orderType, salesType);
    }

	@Override
	public List<OrderType> queryAll(IRequest requestContext) {
		return orderTypeMapper.queryAll();
	}

    @Override
    public List<Map> queryBySalesOrgId(IRequest requestContext, Long salesOrgId) {
        // TODO Auto-generated method stub
        return orderTypeMapper.queryBySalesOrgId(salesOrgId);
    }

}
