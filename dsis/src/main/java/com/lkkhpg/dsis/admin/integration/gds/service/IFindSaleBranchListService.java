/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 获取销售分公司列表.
 * 
 * @author chuangsheng.zhang.
 */

public interface IFindSaleBranchListService extends ProxySelf<IFindSaleBranchListService> {

    /**
     * 获取销售分公司列表.
     * 
     * @param request
     *            请求上下文
     * @param toMarketId
     *            市场ID
     * @return 市场list
     * @throws IntegrationException
     *             接口异常
     */
    List<SpmMarket> findSaleBranchList(IRequest request, Long toMarketId) throws IntegrationException;
}
