package com.lkkhpg.dsis.integration.payment.service;

import java.util.List;

import com.lkkhpg.dsis.integration.payment.dto.PaymentConfig;
import com.lkkhpg.dsis.integration.payment.exception.PaymentException;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

public interface IPaymentConfigService {

    void update(String type, String key, String value);

    List<PaymentConfig> queryConfigsByType( String type);

    boolean insertUnit(IRequest request, @StdWho List<PaymentConfig> paymentConfigs);

    /**
     * 根据类型查询对应付款配置.
     * 
     * @param request
     *            上下文
     * @param type
     *            类型
     * @return list 付款配置列表
     */
    List<PaymentConfig> queryPaymentConfigsByType(IRequest request, String type);

    /**
     * 批量更新配置信息.
     * 
     * @param request
     *            上下文
     * @param paymentConfigs
     *            数据
     * @throws PaymentException
     *             异常
     */
    void batchUpdate(IRequest request, @StdWho List<PaymentConfig> paymentConfigs) throws PaymentException;
}
