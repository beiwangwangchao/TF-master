/*
 *
 */
package com.lkkhpg.dsis.admin.order.service;

import com.lkkhpg.dsis.common.order.dto.OrderType;
import com.lkkhpg.dsis.common.order.dto.SalesType;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

/**
 * 销售类型维护Service.
 * 
 * @author zhiwei.zhang@hand-china.com 
 * [#PE20-4][ADD],2016-12-06 14:20:03.
 */
public interface ISalesTypeService extends ProxySelf<ISalesTypeService> {

	/**
	 * 批量修改或新增销售类型.
	 * @param requestContext
	 * @param salesTypes
	 * @param orderType
	 * @return
	 */
	List<SalesType> batchUpdate(IRequest requestContext, List<SalesType> salesTypes, OrderType orderType);

	/**
	 * 更新销售类型.
	 * @param salesType
	 * @return
	 */
	SalesType updateSalesType(SalesType salesType);

	/**
	 * 新增销售类型.
	 * @param salesType
	 * @return
	 */
	SalesType createSalesType(SalesType salesType);

	/**
	 * 根据订单类型ID查找销售类型.
	 * @param createRequestContext
	 * @param orderTypeId
	 * @return
	 */
	List<SalesType> queryByOrderTypeId(IRequest createRequestContext, Long orderTypeId);

	/**
	 * 批量删除销售类型
	 * @param requestContext
	 * @param salesTypes
	 */
	void batchDelete(IRequest requestContext, List<SalesType> salesTypes);

}
