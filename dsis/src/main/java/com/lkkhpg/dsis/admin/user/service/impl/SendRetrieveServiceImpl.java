/*
 *
 */
package com.lkkhpg.dsis.admin.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.admin.user.service.ISendRetrieveService;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.user.dto.SendRetrieve;
import com.lkkhpg.dsis.common.user.mapper.SendRetrieveMapper;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 发送次数限制服务接口实现.
 * 
 * @author Zhao
 *
 */
@Service
@Transactional
public class SendRetrieveServiceImpl implements ISendRetrieveService {

    @Autowired
    private SendRetrieveMapper sendRetrieveMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer insert(IRequest request, SendRetrieve sendRetrieve) throws UserException {
        // TODO Auto-generated method stub
        sendRetrieveMapper.insert(sendRetrieve);
        Integer result = sendRetrieveMapper.query(sendRetrieve);
        if (result > UserConstants.SENT_LIMIT) {
            throw new UserException(UserConstants.SENT_LIMIT_ERROR, new Object[] {});
        }
        return result;
    }
}
