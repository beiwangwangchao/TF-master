/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service;

import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.pojo.SalesOrderDetail;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.*;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 销售订单服务接口.
 * 
 * @author wuyichu
 */
public interface ISalesOrderService extends ProxySelf<ISalesOrderService> {

    /**
     * 根据条件查询销售订单.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrder
     *            销售订单
     * @param page
     *            页码
     * @param pageSize
     *            页数
     * @return 满足查询条件的销售订单
     */
    List<SalesOrder> queryOrders(IRequest request, SalesOrder salesOrder, int page, int pageSize);

    /**
     * 根据条件查出销售报表
     * @param request
     * @param reportSales
     * @param page
     * @param pageSize
     * @return
     */
    List<ReportSales>reportSales(IRequest request, ReportSales reportSales, int page, int pageSize);



    /**
     * 根据订单编号差订单ID.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrder
     *            销售订单
     * @return 订单信息
     */
    List<SalesOrder> queryOrdersId(IRequest request, SalesOrder salesOrder);

    /**
     * 获取订单的详细信息.
     * 
     * @param request
     *            请求基础数据
     * @param headId
     *            订单头id
     * @return 查询不到时返回null.
     */
    SalesOrder getDetailOrder(IRequest request, Long headId);

    /**
     * 保存销售订单.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrder
     *            销售订单信息
     * @return 保存后的销售订单数据
     * @throws CommOrderException
     *             写入出错时抛出
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    SalesOrder insertOrder(final IRequest request, @StdWho final SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException;

    /**
     * 删除销售订单.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrder
     *            销售订单信息
     */
    @AuditEntry("ORDER")
    void deleteOrder(final IRequest request, final SalesOrder salesOrder);

    /**
     * 批量删除销售订单.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrders
     *            销售订单集合
     */
    void deleteOrderList(final IRequest request, final List<SalesOrder> salesOrders);

    /**
     * 更新销售订单信息.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrder
     *            销售订单信息
     * @return 更新后销售订单信息
     * @throws CommOrderException
     *             修改出错时抛出
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    SalesOrder updateOrder(final IRequest request, @StdWho final SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException;

    /**
     * 更新销售订单部分信息.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            销售订单信息
     * @return 更新后销售订单信息
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommVoucherException
     *             优惠券统一异常
     */
    SalesOrder updateOrderBySelective(final IRequest request, @StdWho final SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException;

    /**
     * 提交销售订单数据到后台.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrder
     *            销售订单信息
     * @return 更新后的订单数据
     * @throws CommOrderException
     *             写入或修改出错时抛出
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    SalesOrder submitOrder(final IRequest request, @StdWho final SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException;

    /**
     * 订单行删除.
     * 
     * @param request
     *            请求基础数据
     * @param lines
     *            删除的订单行集合
     */
    void deleteLine(final IRequest request, final List<SalesLine> lines);

    /**
     * 计算订单行的金额.
     * 
     * @param request
     *            请求基础数据
     * @param lines
     *            订单行集合
     * @param salesOrder
     *            销售订单信息
     * @return 计算过后的订单行集合
     * @throws CommOrderException
     *             订单行数据校验不通过时抛出
     */
    List<SalesLine> calculateLinePrice(final IRequest request, final List<SalesLine> lines, final SalesOrder salesOrder)
            throws CommOrderException;

    /**
     * 计算订单行的金额.
     * 
     * @param request
     *            请求基础数据
     * @param line
     *            订单行
     * @param salesOrder
     *            销售订单信息
     * @param allowInvOrgs
     *            允许的库存组织 空则不需要校验库存组织
     * @param allowSaleTypes
     *            允许的销售方式
     * @param invItemParams
     *            商品查询参数
     * @return 计算过后的订单行
     * @throws CommOrderException
     *             订单行数据校验不通过时抛出
     */
    SalesLine calculateLinePrice(final IRequest request, final SalesLine line, final SalesOrder salesOrder,
                                 final Set<Long> allowInvOrgs, final Set<String> allowSaleTypes, final InvItem invItemParams)
            throws CommOrderException;

    /**
     * 计算订单总金额.
     * 
     * @param request
     *            请求基础数据
     * @param order
     *            销售订单.
     * @return 如果传入的销售订单为空则返回null
     * @throws CommOrderException
     *             订单行金额积分都为null或数量为0时抛出
     */
    SalesOrder calculateOrderPrice(final IRequest request, final SalesOrder order) throws CommOrderException;

    /**
     * 计算订单支付调整.
     * 
     * @param request
     *            请求基础数据
     * @param adjustMents
     *            支付调整集合
     * @return 如果支付调整为空则返回0
     * @throws CommOrderException
     *             查询计算出错时抛出
     */
    BigDecimal caculateAdjustMents(final IRequest request, final List<SalesAdjustment> adjustMents)
            throws CommOrderException;

    /**
     * 批量写入销售订单.
     * 
     * @param request
     *            请求基础数据
     * @param salesOrders
     *            销售订单集合
     * @return 写入后的销售订单数据集合
     * @throws CommOrderException
     *             写入出错时抛出
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    List<SalesOrder> batchInsert(final IRequest request, @StdWho final List<SalesOrder> salesOrders)
            throws CommOrderException, CommVoucherException;

    /**
     * 根据订单生成订单号.
     * 
     * @param request
     *            请求基础数据
     * @param sequenceType
     *            号码类型.
     * @param param
     *            规则参数
     * @return 返回订单号.
     */
    String generateCode(IRequest request, final SequenceType sequenceType, Object param);

    /**
     * 根据订单编号更新订单状态.
     * 
     * @param request
     *            统一上下文
     * @param orderHeaderId
     *            订单头ID
     * @param orderStatus
     *            订单状态
     * @return 更新的记录数
     */
    int updateOrderStatus(final IRequest request, Long orderHeaderId, String orderStatus);

    /**
     * 订单失效（马来西亚），支付金额退回至RemainingBalance.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @param remark
     *            退款备注.
     * @throws MemberException
     *             更新失败时抛出
     * @throws CommMemberException
     *             会员统一异常
     * @throws CommVoucherException
     *             优惠券冲销事务处理异常
     */
    void invalidOrderToRemaining(final IRequest request, Long headerId, String remark)
            throws MemberException, CommMemberException, CommVoucherException;

    /**
     * 订单失效（非马来西亚），保存退款信息至PaymentRefund.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @param paymentRefunds
     *            退款信息集合.
     * @throws CommOrderException
     *             更新失败时抛出
     * @throws MemberException
     *             更新失败时抛出
     * @throws CommMemberException
     *             会员统一异常
     * @throws CommVoucherException
     *             优惠券冲销事务处理异常
     */
    void invalidOrderToRefund(final IRequest request, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException, MemberException, CommMemberException, CommVoucherException;

    /**
     * 根据销售订单头ID更新发票编号.
     * 
     * @param request
     *            统一上下文
     * @param invoiceNumber
     *            发票编号
     * @param headerId
     *            订单头ID
     * @return 更新的记录条数
     * @throws CommOrderException
     *             更新失败时抛出
     */
    int updateInvoiceNumberByHeaderId(final IRequest request, String invoiceNumber, Long headerId)
            throws CommOrderException;

    /**
     * 删除支付调整.
     * 
     * @param request
     *            统一上下文
     * @param adjustmentId
     *            支付调整的id
     */
    void deleteAdjustment(final IRequest request, final Long adjustmentId);

    /**
     * 订单新建基础数据获取.
     * 
     * @param request
     *            统一上下文
     * @param salesOrderDetail
     *            订单详情数据
     * @return 订单所需基础数据
     * @throws OrderException
     *             订单基础数据缺失时抛出.
     */
    Map<String, Object> getBasicDataForCreate(final IRequest request, final SalesOrderDetail salesOrderDetail)
            throws OrderException;

    /**
     * 订单新建基础数据获取.
     * 
     * @param request
     *            统一上下文
     * @param salesOrderDetail
     *            订单详情数据
     * @return 订单所需基础数据
     * @throws OrderException
     *             订单基础数据缺失时抛出.
     */
    Map<String, Object> getBasicDataForConfirm(final IRequest request, final SalesOrderDetail salesOrderDetail)
            throws OrderException;
    
    
    /**
     * 订单详情基础数据获取.
     * 
     * @param request
     *            统一上下文
     * @param salesOrderDetail
     *            订单详情数据
     * @return 订单所需基础数据
     * @throws OrderException
     *             订单基础数据缺失时抛出.
     */
    Map<String, Object> getBasicDataForDetail(final IRequest request, final SalesOrderDetail salesOrderDetail)
            throws OrderException;
    /**
     * 将创建的订单同步到dapp系统.
     * 
     * @param request
     *            统一上下文
     * @param order
     *            销售订单DTO
     */
    void dappSync(IRequest request, SalesOrder order);

    /**
     * 获取订单跳转的URL.
     * 
     * @param request
     *            统一上下文
     * @param headerId
     *            订单头ID
     * @return 详情路径、确认路径、支付路径
     */
    Map<String, String> orderInfoUrl(IRequest request, Long headerId);

    /**
     * 校验订单可选择的奖金区间.
     * 
     * @param request
     *            统一上下文
     * @param headerId
     *            订单头ID
     * @return 订单可选择的奖金区间
     */
    List<SpmPeriod> checkOrderPeriod(IRequest request, Long headerId);


    /**
     * 查询headerId
     * @param orderNumber
     * @return
     */
    List<SalesOrder>selectHeaderId(String orderNumber);

    /**
     * 修改订单奖金区间以及支付日期.
     * 
     * @param request
     *            统一上下文
     * @param orderDetail
     *            订单详情
     * @return 是否修改成功
     * @throws OrderException
     *             数据校验出错时抛出
     */
    boolean updateOrderPeriod(IRequest request, SalesOrderDetail orderDetail) throws OrderException;

    /**
     * dapp - 更新订单同步状态.
     * 
     * @param order
     *            订单参数
     */
    void updateSyncFlag(SalesOrder order);

    /**
     * 判断订单是否已全部发运.
     * 
     * @param headerId
     *            订单头ID
     * @return 是否已全部发运
     */
    boolean checkOrderShipStatus(Long headerId);

    /**
     * 根据订单Id查询发票，如果发票不能存在，则重新生成发票.
     * 
     * @param request
     *            统一上下文
     * @param orderId
     *            订单ID
     * @return 发票信息
     * 
     * @throws CommOrderException
     *             订单统一异常
     * @throws CommSystemProfileException
     *             系统配置统一异常
     */
    Invoice printInvoice(IRequest request, Long orderId) throws CommOrderException, CommSystemProfileException;

    /**
     * 订单由待支付状态回退到保存状态
     * 
     * @param request
     *            请求上下文
     * @param orderId
     *            订单ID
     * @return 修改过的订单
     * @throws CommOrderException
     *             订单异常
     * @throws CommVoucherException
     *             优惠券异常
     * 
     */

    SalesOrder orderWPayToSave(final IRequest request, Long orderId) throws CommOrderException, CommVoucherException;

    /**
     * 取消代付款、以保存状态下订单.
     * 
     * @throws CommOrderException
     *             订单异常
     * @throws CommVoucherException
     *             优惠券异常
     */
    void cancelWPayORSavedOrder() throws CommOrderException, CommVoucherException;



    /**
     * 修改待付款订单状态
     * @throws Exception
     * add by furong.tang
     */
    void modifyWPayOrder() throws Exception;

    //void publishSysMessage(String orderNumber,Long marketId,String memberId) throws Exception;

}
