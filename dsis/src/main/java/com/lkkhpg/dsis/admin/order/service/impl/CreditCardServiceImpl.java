/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.order.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.order.exception.OrderException;
import com.lkkhpg.dsis.admin.order.service.ICreditCardService;
import com.lkkhpg.dsis.common.constant.OrderConstants;
import com.lkkhpg.dsis.common.order.dto.CreditCard;
import com.lkkhpg.dsis.common.order.mapper.CreditCardMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.security.service.IAESClientService;

/**
 * 信用卡服务类实现.
 * 
 * @author wuyichu
 */
@Service
@Transactional
public class CreditCardServiceImpl implements ICreditCardService {

    @Autowired
    private CreditCardMapper creditCardMapper;

    @Autowired
    private IAESClientService aesClientService;

    /**
     * 提交信用卡信息.
     * 
     * @param request
     *            请求的基础信息
     * @param creditCard
     *            信用卡数据
     * @return 提交后的信用卡信息
     * @throws OrderException
     *             写入或更新失败时抛出
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CreditCard submit(IRequest request, CreditCard creditCard) throws OrderException, IOException {
        if (creditCard.getCreditCardNum() != null) {
            String maskedCreditCardNum = creditCard.getCreditCardNum()
                    .substring(creditCard.getCreditCardNum().length() - OrderConstants.CREDITCARD_SHOW_DIGIT);
            creditCard.setMaskedCreditCardNum(maskedCreditCardNum);

            // 加密数据
            String aseCardNumber = aesClientService.encrypt(creditCard.getCreditCardNum());
            creditCard.setCreditCardNum(aseCardNumber);
        }
        // 加密持卡人
        if (creditCard.getCardHolder() != null) {
            String aseCardHolder = aesClientService.encrypt(creditCard.getCardHolder());
            creditCard.setCardHolder(aseCardHolder);
        }
        //加密有效期年份
        if (creditCard.getValidYear() != null) {
            String aseValidYear = aesClientService.encrypt(creditCard.getValidYear());
            creditCard.setValidYear(aseValidYear);
        }
        //加密后的有效期月份
        if (creditCard.getValidMonth() != null) {
            String aseValidMonth = aesClientService.encrypt(creditCard.getValidMonth());
            creditCard.setValidMonth(aseValidMonth);
        }
        
        if (creditCard.getCreditCardId() != null) {
            creditCardMapper.updateByPrimaryKey(creditCard);
        } else {
            creditCardMapper.insert(creditCard);
        }
        return creditCard;
    }

}
