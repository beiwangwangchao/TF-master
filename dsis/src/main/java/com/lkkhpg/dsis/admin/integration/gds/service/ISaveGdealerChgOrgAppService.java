/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 保存转出本市场（源市场）的申请.
 * 
 * @author chuangsheng.zhang.
 */
public interface ISaveGdealerChgOrgAppService extends ProxySelf<ISaveGdealerChgOrgAppService>{

    /**
     * POS中完成申请转出本市场（源市场）后，通过此接口将数据同步到GDS.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员市场变更DTO
     * @return gds 返回的值/若发生异常返回空.
     * @throws IntegrationException
     *             接口统一异常
     */
    ApplicationsPOSTResponse saveGdealerChgOrgApp(IRequest request, MemMarketChange memMarketChange)
            throws IntegrationException;

}
