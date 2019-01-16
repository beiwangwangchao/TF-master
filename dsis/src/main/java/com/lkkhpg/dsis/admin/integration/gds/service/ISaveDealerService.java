/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 即时同步会员资料接口类.
 * 
 * @author linyuheng
 */
public interface ISaveDealerService extends ProxySelf<ISaveDealerService> {
    /**
     * 即时同步会员资料.
     * 
     * @param requestContext
     *            请求上下文
     * @param member
     *            会员DTO
     * @throws IntegrationException
     *             接口异常
     */
    void saveDealer(IRequest requestContext, Member member) throws IntegrationException;
}
