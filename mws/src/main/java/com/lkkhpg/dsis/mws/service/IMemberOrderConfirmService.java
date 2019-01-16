/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.mws.dto.ShopCartItem;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.exception.OrderException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员地址service.
 * 
 * @author guanghui.liu
 */
public interface IMemberOrderConfirmService extends ProxySelf<IMemberOrderConfirmService> {

    /**
     * 查询基本信息.
     * 
     * @param request
     *            统一上下文
     * @param settlement
     *            结算信息
     * @return 包含订单信息、地址信息、物流信息
     * @throws IOException
     *             json转换时的异常
     * @throws OrderException 订单异常
     */
    Map<String, Object> queryBasicInfo(IRequest request, String settlement) throws IOException, OrderException;

    /**
     * 生成一张详细待付款订单.
     * 
     * @param request
     *            统一上下文
     * @param order
     *            订单的一些信息
     * @param cartItems
     *            订单的产品简单信息
     * @return 待创建的订单详细信息
     * @throws CommOrderException
     *             订单业务异常
     */
    SalesOrder generateSalesOrder(IRequest request, SalesOrder order, List<ShopCartItem> cartItems)
            throws CommOrderException;

    /**
     * 提交订单和清空购物车.
     * 
     * @param request
     *            统一上下文
     * @param order
     *            订单信息
     * @return 返回创建好的订单
     * @throws CommOrderException
     *             抛出订单创建异常
     * @throws MemberException
     *             抛出购物车清空异常
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    SalesOrder submitOrder(IRequest request, SalesOrder order)
            throws CommOrderException, MemberException, CommVoucherException;
}
