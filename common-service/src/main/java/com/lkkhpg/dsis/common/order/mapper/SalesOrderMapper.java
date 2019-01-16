/*
 *
 */
package com.lkkhpg.dsis.common.order.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.order.dto.SalesOrder;

/**
 * 销售订单头mapper.
 * 
 * @author wuyichu
 * @author liuxiawang
 */
public interface SalesOrderMapper {
    int deleteByPrimaryKey(SalesOrder salesOrder);

    int insert(SalesOrder salesOrder);

    int insertSelective(SalesOrder salesOrder);

    SalesOrder selectByPrimaryKey(Long headerId);

    SalesOrder selectByPrimaryKeyOnly(Long headerId);

    boolean updateQueryOrder(@Param("attribute15")String attribute15,@Param("orderNumber") String orderNumber);
    String selectOrderStatus(String orderNumber);

    /**
     * 根据订单查询是否使用折扣
     * @param orderNumber
     * @return
     */
    List<SalesOrder>selectDiscountTrx(String orderNumber);

    /**
     * 查询销售渠道为AUTO订单状态为 未付款的订单.
     * 
     * @return 订单集合
     */
    List<SalesOrder> queryAutoshipWpayOrder();

    /**
     * 据局订单编码查询订单.
     * 
     * @param orderNumber
     *            订单编码
     * @return 订单
     */
    SalesOrder queryByOrderNumber(String orderNumber);

    /**
     * 根据订单编号跟新取消定订单状态，让他更新为已完成
     * @param OrderNumber
     */
    void updateOrderStatusRefunds(String OrderNumber);

    /**
     * 根据headerId查询订单信息(for update).
     * 
     * @param headerId
     *            订单头ID
     * @return 订单详情
     */
    SalesOrder selectForUpdateByHeadId(Long headerId);

    int updateByPrimaryKeySelective(SalesOrder salesOrder);

    int updateByPrimaryKey(SalesOrder salesOrder);

    List<SalesOrder> queryOrdersId(SalesOrder salesOrder);

    /**
     * 查询销售订单.
     * 
     * @param salesOrder
     *            查询参数
     * @return 满足条件的订单集合
     */
    List<SalesOrder> queryOrders(SalesOrder salesOrder);

    /**
     * 查询完整销售订单.
     * 
     * @param salesOrder
     *            查询参数
     * @return 满足条件的订单
     */
    List<SalesOrder> queryFullOrders(SalesOrder salesOrder);

    /**
     * 根据批次号查询订单.
     * 
     * @param batchNumber
     *            批次号
     * @return 订单集合
     */
    List<SalesOrder> queryBatchNumber(@Param("batchNumber") String batchNumber);

    /**
     * 根据订单头ID更新订单状态.
     * 
     * @param headerId
     *            订单头id
     * @param orderStatus
     *            订单状态
     * @return 修改的行数
     */
    int updateOrderStatus(SalesOrder salesOrder);

    /**
     * 根据订单号更新退款订单状态
     * @param salesOrder
     * @return
     */
    int updateOrderStatusRefund(SalesOrder salesOrder);

    /**
     * 根据订单头ID更新订单状态.
     * 
     * @param headerId
     *            订单头id
     * @param orderStatus
     *            订单状态
     * @param orderFormerStatus
     *            订单原状态
     * @return 修改的行数
     */
    int updateOrderStatusWithFormerStatus(@Param("headerId") Long headerId, @Param("orderStatus") String orderStatus,
            @Param("orderFormerStatus") String orderFormerStatus);

    /**
     * 根据订单头ID更新订单状态及支付日期.
     * 
     * @param headerId
     *            订单头ID
     * @param orderStatus
     *            订单状态
     * @return 更新的行数
     */
    int updateOrderAfterPayment(SalesOrder salesOrder);

    /**
     * 根据订单头ID查询订单的实际支付金额.
     * 
     * @param headerId
     *            订单头id
     * @return 实际支付金额
     */
    BigDecimal selectActrualPayAmtByHeaderId(@Param("headerId") Long headerId);

    /**
     * 根据订单头ID更新订单发票编号.
     * 
     * @param invoiceNumber
     *            发票编号
     * @param headerId
     *            订单头id
     * @return 更改的行数
     */
    int updateInvoiceNumberByHeaderId(@Param("invoiceNumber") String invoiceNumber, @Param("headerId") Long headerId);

    /**
     * 查询待同步到GDS的订单.
     * 
     * @param marketIds
     *            市场Id集合
     * @return 需要同步的订单集合
     */

    List<SalesOrder> selectWaitSyncGds(List<Long> marketIds);

    /**
     * 根据订单编号修改同步状态.
     * 
     * @param orderNumber
     *            订单编号
     * @param syncFlag
     *            同步状态
     * @param adviseNo
     *            通知号
     */

    void updateSyncByOrderNumber(@Param("orderNumber") String orderNumber, @Param("syncFlag") String syncFlag,
            @Param("adviseNo") String adviseNo);

    /**
     * 根据会员表主键查询非取消状态的订单集合.
     * 
     * @param memberId
     *            会员表主键
     * @return 销售订单列表
     */
    List<SalesOrder> selectOrderByMemberId(Long memberId);

    /**
     * 根据批次号查询该批次总记录数.
     * 
     * @param batchNumber
     *            批次号
     * @return 该批次记录数
     */
    long queryOrdersNumByBatchNumber(@Param("batchNumber") String batchNumber);

    /**
     * 根据批次号查询该批次总金额.
     * 
     * @param batchNumber
     *            批次号
     * @return 该批次总金额
     */
    BigDecimal queryOrdersPayByBatchNumber(@Param("batchNumber") String batchNumber);

    /**
     * 根据订单号查询订单信息.
     * 
     * @param orderNumber
     *            订单号
     * @return SalesOrder详情
     */
    SalesOrder selectByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 根据订单号修改订单状态为已完成.
     * 
     * @param orderNumber
     *            订单号
     * @return 修改的行数
     */
    int updateOrderStatusByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 根据订单编号市场代码查询订单头.
     * 
     * @param map
     *            订单编号数据集.
     * @return 订单头ID.
     */
    List<SalesOrder> getOrderPaymentByOrderId(Map<String, Object> map);

    /**
     * 根据订单头ID更新订单状态.
     * 
     * @param map
     *            订单头ID及订单状态.
     * @return 相应数据
     */
    int updateOrderStatusByHeaderId(HashMap<String, Object> map);

    /**
     * 根据dapp通知号判断dapp订单是否已存在.
     * 
     * @param dappAppNo
     *            dapp通知号
     * @return 存在记录条数
     */
    Long isExistDappNo(String dappAppNo);

    /**
     * 根据订单号查询订单的状态.
     * 
     * @param map
     * @param saleOrganization
     *            销售组织编码
     * @param companyCode
     *            公司编码
     * @param market
     *            市场编码
     * @param orderNumber
     *            订单编号
     * @return 订单头ID
     */
    SalesOrder selectByOrderNumberForDApp(Map<String, Object> map);

    /**
     * 取当前月份下，销售渠道=Service Center,状态=已完成/待确认的销售订单，再按照不同的Service Center代码汇总PV值.
     * 
     * @param periodName
     *            奖金月份
     * @param orgId
     *            组织Id
     * @return 销售订单对象集合
     */
    List<SalesOrder> getPvSum(@Param("periodName") String periodName, @Param("orgId") Long orgId);

    /**
     * 取当前月份下,当前月份=订单奖金月份;订单创建人的用户类型=ipoint;销售渠道=iPoint Center;状态=已完成/待确认.
     * 按照不同的销售区域和创建人，汇总销售额.
     * 
     * @param periodName
     *            奖金月份
     * @param orgId
     *            组织Id
     * @return 销售订单对象集合
     */
    List<SalesOrder> getActrualPayAmtSum(@Param("periodName") String periodName, @Param("orgId") Long orgId);

    /**
     * dapp接口-查询订单列表.
     * 
     * @param map
     *            查询参数
     * @return SalesOrder列表
     */
    List<SalesOrder> queryOrdersForDapp(Map<String, Object> map);

    /**
     * 查找固定时间内符合 自动订货单符合赠品的数量.
     * 
     * @param memberId
     *            会员id
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 查询到的数量
     */
    Long queryNumberwithGiftRule(@Param(value = "memberId") Long memberId, @Param(value = "startTime") Date startTime,
            @Param(value = "endTime") Date endTime);

    int updateSalesOrderGiftRuleFlag(@Param(value = "memberId") Long memberId,
            @Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime);

    /**
     * 获取订单状态.
     * 
     * @param headerId
     *            订单头ID
     * @return 订单状态
     */
    String getOrderStatusByPrimaryKey(@Param("headerId") Long headerId);

      List <SalesOrder>selectHeaderId( String orderNumber);

    /**
     * 获取订单详情.
     * 
     * @param headerId
     * @return 订单详情
     */
    SalesOrder selectSalesOrderByHeadId(Long headerId);

    /**
     * 更改订单的奖金区间以及支付日期.
     * 
     * @param headerId
     *            订单头Id
     * @param payDate
     *            支付日期
     * @param periodId
     *            奖金区间ID
     * @return 更改的数目
     */
    int updateOrderPeriodAndPayDate(@Param("headerId") Long headerId, @Param("payDate") Date payDate,
            @Param("periodId") Long periodId);

    /**
     * dapp - 更新订单dapp同步状态.
     * 
     * @param salesOrder
     *            订单参数
     * @return 更新条目
     */
    int updateSyncFlag(SalesOrder salesOrder);

    /**
     * 查询销售订单.
     * 
     * @param salesOrder
     *            查询参数
     * @return 满足条件的订单集合
     */
    List<SalesOrder> queryMwsOrders(SalesOrder salesOrder);

    /**
     * 查询VIP会员订单.
     * 
     * @param map
     *            条件字段映射
     * @return 符合条件的订单集合
     */
    List<SalesOrder> selectOrderForVIP(Map<String, Object> map);

    /**
     * 查询VIP会员的PV.
     * 
     * @param map
     *            入参
     * @return pv
     */
    BigDecimal getPvForVIP(Map<String, Object> map);
    
    /**
     * 根据去除首位字母的订单编号查询订单.
     * 
     * @param map
     *            入参
     * @return 订单信息
     */
    SalesOrder selectOrderBySubNumber(Map<String, Object> map);

    /**根据订单头的menberId获取会员信息.
     * @param memberId
     * @return 订单对应的会员信息
     */
    SalesOrder getBrNo(Long memberId);
    
    List<SalesOrder> querySaveAndWPayOrderbyMarketCode(String marketCode);

    /**
     * 查询会员最后一次订购日期(订单状态已完成)
     * @param MemberId
     * @return
     */
    Date selectMemberLastOrderDate(Long MemberId);
    
    
   BigDecimal notReturnedQuantity(Long headerId);
   
   
   List<SalesOrder> queryOrderByStatus(SalesOrder salesOrder);
}