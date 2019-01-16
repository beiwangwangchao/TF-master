package com.lkkhpg.dsis.integration.payment.mapper;

import java.util.List;

import com.lkkhpg.dsis.integration.payment.dto.PaymentConfig;

public interface PaymentConfigMapper {
    
    List<PaymentConfig> selectByType(String type);
    
    Integer updateValueByPrimaryKey(PaymentConfig paymentConfig);

    int  insertUnit(PaymentConfig paymentConfig);
}
