/*
 *
 */
package com.lkkhpg.dsis.admin.order.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.exception.CommInventoryException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesReturn;
import com.lkkhpg.dsis.common.order.dto.SalesRtnLine;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 退货单Service.
 * 
 * @author houmin
 *
 */
public interface ISalesReturnService extends ProxySelf<ISalesReturnService> {

    /**
     * 保存退货单详细信息.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            退货单详情
     * @return 订单详情,传入之为空则返回值为空
     * @throws CommOrderException
     *             订单统一异常
     */
    SalesReturn saveReturnOrderDetail(IRequest request, SalesReturn salesReturn) throws CommOrderException;

    /**
     * 提交退货单.
     * 
     * @param request
     *            统一上下文
     * @param salesReturn
     *            退货单详情
     * @return 提交后退货单详情
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommInventoryException
     *             库存统一异常
     * @throws CommMemberException
     *             会员统一异常
     */
    SalesReturn submitReturnOrderDetail(IRequest request, SalesReturn salesReturn)
            throws CommOrderException, CommInventoryException, CommMemberException;

    /**
     * 删除退货单.
     * 
     * @param request
     *            统一上下文
     * @param salesReturns
     *            退货单信息
     * @throws CommOrderException
     *             订单统一异常
     */
    void deleteSalesReturn(IRequest request, List<SalesReturn> salesReturns) throws CommOrderException;

    /**
     * 删除退货单行信息.
     * 
     * @param request
     *            统一上下文
     * @param salesRtnLines
     *            退货单行集合.
     * @throws CommOrderException
     *             订单统一异常
     */
    void deleteSalesRtnLine(IRequest request, List<SalesRtnLine> salesRtnLines) throws CommOrderException;

    /**
     * 获取退货单详情.
     * 
     * @param request
     *            统一上下文
     * @param returnId
     *            退货单头ID
     * @return 订单详情
     * @throws CommOrderException
     *             订单统一异常
     */
    SalesReturn getReturnDetail(IRequest request, Long returnId) throws CommOrderException;

    /**
     * 查询退货单.
     * 
     * @param request
     *            统一上下文.
     * @param salesReturn
     *            查询条件.
     * @param page
     *            页数.
     * @param pagesize
     *            每页记录数.
     * @return 退货单列表
     */
    List<SalesReturn> selectSalesReturn(IRequest request, SalesReturn salesReturn, int page, int pagesize);

    /**
     * 组织地址查询.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织ID
     * @param invOrgId
     *            库存组织ID
     * @return 组织对应地址
     */
    SpmLocation getLocations(IRequest request, Long salesOrgId, Long invOrgId);

    /**
     * 查询退货单信息.
     * 
     * @param request
     *            统一上下文
     * @param salesLine
     *            订单行信息
     * @return 行信息.
     */
    List<SalesLine> selectDetailByReturnId(IRequest request, SalesLine salesLine);

}
