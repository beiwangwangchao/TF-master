package com.lkkhpg.dsis.integration.payment.service.impl;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.delivery.dto.OrderDelivery;
import com.lkkhpg.dsis.common.delivery.mapper.DeliveryMapper;
import com.lkkhpg.dsis.common.order.dto.ReportSales;
import com.lkkhpg.dsis.integration.payment.dto.PayRefundRequest;
import com.lkkhpg.dsis.integration.payment.dto.ReportRefunds;
import com.lkkhpg.dsis.integration.payment.mapper.PayRefundRequestMapper;
import com.lkkhpg.dsis.integration.payment.mapper.ReportRefundsMapper;
import com.lkkhpg.dsis.integration.payment.service.IPayRefundRequestService;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by miaoyifan on 2017/12/6.
 */
@Service
@Transactional
public class PayRefundRequestServiceImpl implements IPayRefundRequestService {
    @Autowired
    private PayRefundRequestMapper payRefundRequestMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private ReportRefundsMapper reportRefundsMapper;
    private final Logger logger = LoggerFactory.getLogger(PayRefundRequestServiceImpl.class);


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<PayRefundRequest> select(IRequest request, PayRefundRequest payRefundRequest, int page, int pagesize){
        PageHelper.startPage(page, pagesize);
        List<PayRefundRequest> list = payRefundRequestMapper.selectPayRefund(payRefundRequest);
       return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String payRefundquest(PayRefundRequest payRefundRequest) throws Exception {
        //String outTradeNo=payRefundRequest.getOutTradeNo();
          Long  No = payRefundRequestMapper.selectOutTradeNo(payRefundRequest);
          String str="";
        try{
            if(No==0){
                payRefundRequestMapper.insert(payRefundRequest);
            }else {
               str="订单已存在，请勿重复提交";
            }
        }catch (Exception e){
            e.printStackTrace();
             str="提交失败";
            return str;
        }
        return str;

    }

    /**
     * 退款完成更新状态
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean payRefundResult(PayRefundRequest payRefundRequest) throws Exception {
        try{
            //更新后台状态
            payRefundRequestMapper.update(payRefundRequest);

        }catch (Exception e){
            logger.error("the paymentResult insert is error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 取消退款，将保存在退款中的记录删除
     * @param out_trade_no
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean deleteRefund(String out_trade_no) {
        try{
            payRefundRequestMapper.deleteRefund(out_trade_no);
           // tianFuPaymentResultMapper.deleteRefund(out_trade_no);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<OrderDelivery> queryDeliveryStatus(String orderNumber) throws Exception {
     return deliveryMapper.queryDeliveryStatus(orderNumber);

    }

    @Override
    public List<ReportRefunds> reportRefunds(IRequest request, ReportRefunds reportRefunds, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return reportRefundsMapper.reportRefunds(reportRefunds);
    }
}
