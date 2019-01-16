/*
 *
 */
package com.lkkhpg.dsis.integration.payment.service.impl;


import com.lkkhpg.dsis.common.discount.constract.DiscountConstract;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxHeadDto;
import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxHeadService;
import com.lkkhpg.dsis.common.exception.CommDeliveryException;
import com.lkkhpg.dsis.common.order.dto.SalesOrder;
import com.lkkhpg.dsis.common.order.mapper.SalesLineMapper;
import com.lkkhpg.dsis.common.order.mapper.SalesOrderMapper;
import com.lkkhpg.dsis.common.service.ICommDeliveryService;
import com.lkkhpg.dsis.integration.payment.dto.TianFuPaymentResult;
import com.lkkhpg.dsis.integration.payment.dto.TianFuPaymentTransaction;
import com.lkkhpg.dsis.integration.payment.mapper.TianFuPaymentResultMapper;
import com.lkkhpg.dsis.integration.payment.mapper.TianFuPaymentTransactionMapper;
import com.lkkhpg.dsis.integration.payment.service.ITianFuPaymentTransationService;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * created by li.liu06@hand-china.com 29/11/2017
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TianFuPaymentTransationServiceImpl implements ITianFuPaymentTransationService {
    private final Logger logger = LoggerFactory.getLogger(TianFuPaymentTransationServiceImpl.class);

    @Autowired
    ICommDeliveryService commDeliveryService;

    @Autowired
    IDiscountTrxHeadService discountTrxHeadService;

    @Autowired
    private TianFuPaymentTransactionMapper transactionMapper;

    @Autowired
    private TianFuPaymentResultMapper tianFuPaymentResultMapper;
    @Autowired
    private TianFuPaymentResultMapper resultMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private SalesLineMapper salesLineMapper;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean paymentRecord(TianFuPaymentTransaction tianFuPaymentTransaction) throws Exception {
        try {
            transactionMapper.insertUnit(tianFuPaymentTransaction);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TianFuPaymentTransaction queryPaymentRecord(String orderNumber, String transChannel) {
        TianFuPaymentTransaction transaction = null;
        try {

            TianFuPaymentTransaction t = new TianFuPaymentTransaction();
            t.setOutTradeNo(orderNumber);
            t.setTransChannel(transChannel);
            transaction = transactionMapper.selectByPrimaryKey(t);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transaction;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean deletePaymentRecord(String orderNumber, String transChannel) {
        TianFuPaymentTransaction t = new TianFuPaymentTransaction();
        try {
            t.setOutTradeNo(orderNumber);
            t.setTransChannel(transChannel);
            transactionMapper.deleteByPrimaryKey(t);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

//    /**
//     * 取消退款，将保存在退款中的记录删除
//     * @param out_trade_no
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
//    public boolean deleteRefund(String out_trade_no) {
//        try{
//            tianFuPaymentResultMapper.deleteRefund(out_trade_no);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean paymentResult(TianFuPaymentResult paymentResult) throws Exception{

        try {
            resultMapper.insert(paymentResult);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    ;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<TianFuPaymentResult>  selectUnit(String out_trade_no) {

        return tianFuPaymentResultMapper.selectUnit(out_trade_no);
    }

    @Override
    public List<TianFuPaymentTransaction> selectPartner(String outTradeNo) {
        return transactionMapper.selectPartner(outTradeNo);
    }


    ;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TianFuPaymentResult queryStatus(IRequest request,String orderNumber) {
        return resultMapper.selectByPrimaryKey(orderNumber);
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public SalesOrder querySalesOrderStatus(String orderNumber){
        Long headerId = transactionMapper.queryByOrderNumber(orderNumber);
        SalesOrder salesOrder= salesOrderMapper.selectByPrimaryKey(headerId);
        return salesOrder;
    }
    ;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updatePaymentRecord(Map maps) {


        try {
            transactionMapper.updateStatus(maps);


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updateSalesOrderStatus(Map maps, String OrderNumber) {
        try {
            Long headerId = transactionMapper.queryByOrderNumber(OrderNumber);
            maps.put("headerId", headerId);
            transactionMapper.updateByPrimaryKeySelective(maps);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean updateSalesOrder(IRequest request, SalesOrder orders) {

        try {
            System.out.println(orders.getOrderNumber());
            Long headerId = transactionMapper.queryByOrderNumber(orders.getOrderNumber());
            orders.setHeaderId(headerId);
            transactionMapper.updateOrders(orders);


            if(orders.getDiscountAmt() != null) {
                //modeified by 11816 at 2018/01/25 19:16 use for process transaction of discount
                DiscountTrxHeadDto discountTrxHeadDto = new DiscountTrxHeadDto();
                DiscountTrxLineDto discountTrxLineDto = new DiscountTrxLineDto();
                // status type reason  processDate creationDate salesOrgId

                discountTrxHeadDto.setStatus(DiscountConstract.TRX_ADJ_STATUS_NEW);
                discountTrxHeadDto.setAdjustReason(DiscountConstract.TRX_ADJ_REASON_ORDER);
                discountTrxHeadDto.setAdjustType(DiscountConstract.TRX_TYPE_ADDE);
                discountTrxHeadDto.setProcessDate(new Date());
                discountTrxHeadDto.setSalesOrgId(orders.getSalesOrgId());
                discountTrxHeadDto.setCreationDate(new Date());
                discountTrxHeadDto.setCreatedBy(orders.getMemberId());
                discountTrxHeadDto.setLastUpdateDate(new Date());
                discountTrxHeadDto.setLastUpdatedBy(orders.getMemberId());

                discountTrxLineDto.setCreatedBy(orders.getMemberId());
                discountTrxLineDto.setCreationDate(new Date());
                discountTrxLineDto.setLastUpdatedBy(orders.getMemberId());
                discountTrxLineDto.setLastUpdateDate(orders.getLastUpdateDate());
                discountTrxLineDto.setDiscountAdjustAmt(orders.getDiscountAmt());
                discountTrxLineDto.setMemberId(orders.getMemberId());

                List<DiscountTrxLineDto> discountTrxLineDtoList = new ArrayList<>();
                discountTrxLineDtoList.add(0, discountTrxLineDto);
                discountTrxHeadDto.setDiscountTrxLineDto(discountTrxLineDtoList);

                discountTrxHeadService.submitDiscountTransaction(request, discountTrxHeadDto);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void refund() {

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String selectList(String appCode) {
        return transactionMapper.selectList(appCode);
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean autoShip(IRequest request, String orderNumber){

        TianFuPaymentResult results = resultMapper.selectByPrimaryKey(orderNumber);

        boolean flag = false;

        if("".equals(results.getNotify_id()) == false){
            SalesOrder salesOrder =  salesOrderMapper.queryByOrderNumber(orderNumber);
            salesOrder.setLines(salesLineMapper.selectSalesLineByHead(salesOrder.getHeaderId()));

            try {
                  if(commDeliveryService.createDeliveriesByOrder(request,salesOrder).size() > 0){
                      flag = true;
                  };
            } catch (CommDeliveryException e) {
                e.printStackTrace();
            }

        }

        return flag;
    }
}
