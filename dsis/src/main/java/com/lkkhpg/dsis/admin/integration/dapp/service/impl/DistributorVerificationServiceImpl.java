/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.dto.DistributorsVerificationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorVerificationService;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;
import com.lkkhpg.dsis.platform.service.IAccountService;

/**
 * 业务员登录校验接口 .
 * 
 * @author fengwanjun
 */
@Transactional
@Service
public class DistributorVerificationServiceImpl implements IDistributorVerificationService {

    @Autowired
    private IAccountService accountServiceImpl;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Map<String, Object> loadVerification(DistributorsVerificationRequest distributorsVerificationRequest)
            throws DAppException {
        HashMap<String, String> distributorMap = new HashMap<>();

        String distributorNumber = distributorsVerificationRequest.getDistributorNumber();
        String password = distributorsVerificationRequest.getPassword();
        String market = distributorsVerificationRequest.getMarket();

        distributorMap.put("distributorNumber", distributorNumber);
        distributorMap.put("market", market);
        Account account;

        Map<String, Object> resultMap = new HashMap<>();

        // 根据会员号和市场查找对应帐号
        account = accountMapper.selectByMember(distributorMap);

        if (account == null) {
            throw new DAppException(IntegrationException.MSG_ERROR_DISTRIBUTOR_NOT_EXIST, 
                    IntegrationConstant.STATUS_CODE_MEMBER,
                    "distributorNumber=" + distributorNumber + "&market=" + market);
        }
        try {
            account.setPassword(password);
            // 验证密码是否正确，捕获到AccountException异常，则表示校验失败
            accountServiceImpl.dappLogin(account);

            resultMap.put("result", 1);
            resultMap.put("message", null);
        } catch (AccountException e) {
            resultMap.put("result", -1);
            resultMap.put("message", IntegrationConstant.STATUS_CODE_MEMBER + ":" + e.getMessage());
        }
        return resultMap;
    }
}