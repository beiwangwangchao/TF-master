package com.lkkhpg.dsis.integration.payment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.integration.payment.configration.PaymentConfigrationManager;
import com.lkkhpg.dsis.integration.payment.dto.PaymentConfig;
import com.lkkhpg.dsis.integration.payment.exception.PaymentException;
import com.lkkhpg.dsis.integration.payment.mapper.PaymentConfigMapper;
import com.lkkhpg.dsis.integration.payment.service.IPaymentConfigService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;

@Transactional
@Service
public class PaymentConfigServiceImpl implements IPaymentConfigService {

    @Autowired
    private PaymentConfigMapper configMapper;
    @Autowired
    private IMessagePublisher msgPublisher;

    @Autowired
    private PaymentConfigrationManager paymentConfigrationManager;

    @Override
    public void update(String type, String key, String value) {
        // configMapper.update success
        PaymentConfig message = new PaymentConfig();
        message.setPaymentType(type);
        message.setKey(type);
        message.setValue(type);
        msgPublisher.publish(PaymentConfigrationManager.PAYMENT_CONFIG, message);
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public boolean insertUnit(IRequest request, @StdWho List<PaymentConfig> paymentConfigs){
       try {
           for (PaymentConfig config: paymentConfigs) {
               if(config.getConfigID()==null){
                   configMapper.insertUnit(config);
               }

               else {
                   configMapper.updateValueByPrimaryKey(config);
                   msgPublisher.publish(PaymentConfigrationManager.PAYMENT_CONFIG, config);
               }
           }
       }catch (Exception e){
         return  false;
       }
     return  true;
    }

    @Override
    public List<PaymentConfig> queryPaymentConfigsByType(IRequest request, String type) {
        return configMapper.selectByType(type);
    }

    @Override
    public void batchUpdate(IRequest request, @StdWho List<PaymentConfig> paymentConfigs) throws PaymentException {
        for (PaymentConfig paymentConfig : paymentConfigs) {
            paymentConfig = paymentConfigrationManager.encrypt(paymentConfig);
            configMapper.updateValueByPrimaryKey(paymentConfig);
            msgPublisher.publish(PaymentConfigrationManager.PAYMENT_CONFIG, paymentConfig);
        }
    }


    @Override
    public List<PaymentConfig> queryConfigsByType( String type){
        return configMapper.selectByType(type);
    }
}
