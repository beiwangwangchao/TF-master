/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderCancelRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OrderCancelResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 订单作废接口.
 * 
 * @author fengwanjun
 */
public interface IOrderCancelService {

    /**
     * 订单作废接口.
     * 
     * @param orderCancelRequestList
     *            saleOrganization 销售组织 companyCode 公司代码 market 市场 orderNumber
     *            订单编号
     * @return 相应数据
     * @throws DAppException
     */
    List<OrderCancelResponse> orderCancel(List<OrderCancelRequest> orderCancelRequestList) throws DAppException;
}