/*
 *
 */
package com.lkkhpg.dsis.admin.user.service;

import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.common.user.dto.SendRetrieve;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 发送次数限制服务接口.
 * 
 * @author Zhao
 *
 */
public interface ISendRetrieveService extends ProxySelf<ISendRetrieveService> {

    /**
     * insert发送次数记录.
     * 
     * @param request
     *            统一上下文
     * @param sendRetrieve
     *            发送记录dto
     * @return 记录
     * @throws UserException
     *             用户异常
     */
    Integer insert(IRequest request, SendRetrieve sendRetrieve) throws UserException;
}
