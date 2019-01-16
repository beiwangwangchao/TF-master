/*
 *
 */
package com.lkkhpg.dsis.integration.payment.service;


import com.lkkhpg.dsis.common.order.dto.SalesOrder;

import com.lkkhpg.dsis.integration.payment.dto.TianFuPaymentResult;
import com.lkkhpg.dsis.integration.payment.dto.TianFuPaymentTransaction;
import com.lkkhpg.dsis.platform.core.IRequest;

import java.util.List;
import java.util.Map;

/**
 * IPaymentTransationService.
 * 
 * @author li.liu06@hand-chian.com 29/11/2017
 *
 */
public interface ITianFuPaymentTransationService {
    /**
     *  向数据库保存交易信息
     * @param  tianFuPaymentTransaction 交易信息参数
     * @parma  Exception 抛出保存失败的异常并回滚
     * @return boolean   返回是否保存成功
     */
    boolean paymentRecord(TianFuPaymentTransaction tianFuPaymentTransaction)throws Exception;

    /**
     *  向数据库查询交易信息
     * @param  orderNumber 订单的信息
     * @parma  transChannel 支付方式
     * @return TianFuPaymentTransaction   返回查询交易的信息
     */
    TianFuPaymentTransaction queryPaymentRecord(String orderNumber,String transChannel);


    /**
     *  向数据库查保存交易的结果
     * @parma  paymentResult 支付信息反馈结果
     * @return boolean   保存是否成功
     */
    boolean paymentResult(TianFuPaymentResult paymentResult)throws Exception;


    /**
     *  向数据库查询交易的结果
     * @parma  out_trade_no 订单号
     * @return List   返回查询的结果
     */
    List<TianFuPaymentResult> selectUnit(String out_trade_no);

    /**
     *  向数据库查询交易的结果的商户号
     * @parma  out_trade_no 订单号
     * @return List   返回查询的结果
     */
    List<TianFuPaymentTransaction>selectPartner(String outTradeNo);

    /**
     *  向数据库查询交易的结果的商户号
     *  @param request 请求参数
     * @parma  out_trade_no 订单号
     * @return TianFuPaymentResult   返回查询的交易的反馈结果
     */
    TianFuPaymentResult queryStatus(IRequest request,String orderNumber);

    /**
     *  向数据库查询交易的商品信息
     * @parma  orderNumber 订单号
     * @return SalesOrder   返回订单的信息
     */
    SalesOrder querySalesOrderStatus(String orderNumber);

    /**
     *  向数据库跟新交易记录
     * @parma  maps 跟新的参数信息
     * @return boolean   跟新是否成功
     */
    boolean updatePaymentRecord(Map maps);

    /**
     *  向数据库跟新交易记录的状态
     * @parma  maps 跟新的参数信息
     * @parma  orderNumber 订单号
     * @return boolean   跟新是否成功
     */
    boolean updateSalesOrderStatus(Map maps,String OrderNumber);

    /**
     *  向数据库跟新交易记录的状态
     * @parma  request 请求参数
     * @parma  SalesOrder 订单信息
     * @return boolean   跟新是否成功
     */
    boolean updateSalesOrder(IRequest request,SalesOrder orders);

    /**
     *  向数据库删除交易记录
     * @param  orderNumber 订单的信息
     * @parma  transChannel 支付方式
     * @return boolean   删除是否成功
     */
    boolean deletePaymentRecord(String orderNumber,String transChannel);

   // boolean deleteRefund(String out_trade_no);

    void refund();

    /**
     *  向数据库查询密钥
     * @parma  appCode 支付方式
     * @return   String 返回字符串
     */
    String  selectList(String appCode);

    boolean autoShip(IRequest request, String orderNum);

}
