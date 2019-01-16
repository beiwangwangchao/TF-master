/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.lkkhpg.dsis.common.enums.SequenceType;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommOrderException;
import com.lkkhpg.dsis.common.exception.CommVoucherException;
import com.lkkhpg.dsis.common.order.dto.PaymentRefund;
import com.lkkhpg.dsis.common.order.dto.SalesAdjustment;
import com.lkkhpg.dsis.common.order.dto.SalesLine;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.product.dto.InvItem;
import com.lkkhpg.dsis.platform.annotation.AuditEntry;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 销售订单服务接口.
 * 
 * @author wuyichu
 */
public interface ICommSalesOrderService extends ProxySelf<ICommSalesOrderService> {

    /**
     * 根据订单号查询折扣是否使用
     * @param orderNumber
     * @return
     */
    List<SalesOrder>selectDiscountTrx(String orderNumber);
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
    List<SalesOrder> queryMwsOrders(IRequest request, SalesOrder salesOrder, int page, int pageSize);

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
     * 获取订单的详细信息(包含用户信息销售组织市场等).
     * 
     * @param request
     *            请求基础数据
     * @param headId
     *            订单头id
     * @return 查询不到时返回null.
     */
    SalesOrder getDetailOrder(IRequest request, Long headId);

    /**
     * 获取订单信息(包含订单头、订单行、订单支付调整、配送地址).
     * 
     * @param request
     *            请求基础数据
     * @param headId
     *            订单头id
     * @param isVirtual
     *            是否包含虚拟商品包行.
     * @param isDetail
     *            是否包含虚拟商品包行的明细.
     * @return 查询不到时返回null.
     */
    SalesOrder getOrder(IRequest request, Long headId, boolean isVirtual, boolean isDetail);

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
     */
    @AuditEntry("ORDER")
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
    @AuditEntry("ORDER")
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
    @AuditEntry("ORDER")
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
     */
    BigDecimal caculateAdjustMents(final IRequest request, final List<SalesAdjustment> adjustMents);

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
    @AuditEntry("ORDER")
    int updateOrderStatus(final IRequest request, Long orderHeaderId, String orderStatus);

    /**
     * 拒绝退款发送信息
     * @param orderNumber
     * @param marketId
     * @param memberId
     * @param member_code
     * @return
     */
   boolean pulishSysRefuseMessage(String orderNumber, Long marketId, String memberId,String member_code);

    /**
     * 退款，更新订单状态
     * @param salesOrder
     * @return
     * @throws Exception
     */
     boolean updateOrderStatusRefund(SalesOrder salesOrder ) throws Exception;

    /**
     * 根据订单编号跟新取消定订单状态，让他更新为已完成
     * @param OrderNumber
     * @return
     * @throws Exception
     */
     boolean updateOrderStatusRefunds(String OrderNumber)throws Exception;

    /***
     * 根据订单号查询订单状态
     * @param OrderNumber
     * @return
     * @throws Exception
     */
     String selectOrderStatus(String OrderNumber)throws Exception;

    /**
     * 根据订单编号更新订单状态.
     * 
     * @param request
     *            统一上下文
     * @param headerId
     * @param orderHeaderId
     *            订单头ID
     * @param orderStatus
     *            订单状态
     * @param orderFormerStatus
     *            订单元状态
     * @return 更新的记录数
     */
    int updateOrderStatusWithFormerStatus(final IRequest request, Long headerId, String orderStatus,
            String orderFormerStatus);

    /**
     * 订单失效（马来西亚），支付金额退回至RemainingBalance.
     * 
     * @param request
     *            统一上下文.
     * @param headerId
     *            订单头ID.
     * @param remark
     *            退款备注.
     * @throws CommMemberException
     *             更新失败时抛出
     * @throws CommVoucherException
     *             优惠券事务处理异常
     */
    void invalidOrderToRemaining(final IRequest request, Long headerId, String remark)
            throws CommMemberException, CommVoucherException;

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
     * @throws CommMemberException
     *             更新失败时抛出
     * @throws CommVoucherException
     *             优惠券事务处理异常
     */
    void invalidOrderToRefund(final IRequest request, Long headerId, List<PaymentRefund> paymentRefunds)
            throws CommOrderException, CommMemberException, CommVoucherException;

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
     */
    int updateInvoiceNumberByHeaderId(final IRequest request, String invoiceNumber, Long headerId);

    /**
     * 根据id删除支付调整.
     * 
     * @param request
     *            统一上下文
     * @param adjustmentId
     *            支付调整id
     */
    void deleteAdjustment(final IRequest request, final Long adjustmentId);

    /**
     * 校验销售订单数据.
     * 
     * @param request
     *            统一上下文
     * @param salesOrder
     *            销售订单数
     * @throws CommOrderException
     *             校验出错时抛出
     * @throws CommVoucherException
     *             优惠券校验出错时抛出
     */
    void validateOrderData(final IRequest request, final SalesOrder salesOrder)
            throws CommOrderException, CommVoucherException;

    /**
     * 虚拟商品包的订单行进行拆行.
     * 
     * @param request
     *            统一上下文
     * @param line
     *            使用了虚拟商品包的订单行
     */
    void processVirtualPackageItem(final IRequest request, @StdWho final SalesLine line);

    /**
     * 查找固定时间内符合 自动订货单符合赠品的数量.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员id
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 查询到的数量
     */
    Long queryNumberwithGiftRule(final IRequest request, Long memberId, Date startTime, Date endTime);

    /**
     * 更新赠品标识符状态
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员id
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return
     */
    int updateSalesOrderGiftRuleFlag(final IRequest request, Long memberId, Date startTime, Date endTime);

    /**
     * 查询销售渠道为AUTO订单状态为 未付款的订单.
     * 
     * @return 订单集合
     */
    List<SalesOrder> queryAutoshipWpayOrder();

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
     *             发票异常
     */

    SalesOrder orderWPayToSave(final IRequest request, Long orderId) throws CommOrderException, CommVoucherException;

    int updateByPrimaryKey(final IRequest request, @StdWho SalesOrder salesOrder);

    /**
     * 判断订单是否完全退货
     * 
     * @param request
     *            请求上下文
     * @param salesOrder
     *            销售订单
     * @return 结果
     */
    public Boolean isOrderReturnAll(IRequest request, SalesOrder salesOrder);


    Long querByOrderNumber(String orderNumber);

    /**
     * 根据ID查询销售订单状态
     * add by furong.tang
     * @param headerId
     * @return
     */
    String queryOrderStatusByKey(Long headerId);
}
