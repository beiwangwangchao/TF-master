/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeDelete;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 删除转出本市场（源市场）的申请.
 * 
 * @author shenqb
 */
public interface IDeleteGdealerChgOrgAppService extends ProxySelf<IDeleteGdealerChgOrgAppService> {

    /**
     * 删除转出本市场（源市场）的申请.
     * 
     * @param request
     *            请求上下文
     * @param gdealerChgOrgCode
     *            变更市场记录编号
     * @param orgCode
     *            市场编码
     * @return Flag 删除标识
     * @throws IntegrationException
     *             接口统一异常
     */
    Boolean deleteGdealerChgOrgApp(IRequest request, Long gdealerChgOrgCode, String orgCode)
            throws IntegrationException;

    /**
     * 新开事务，插入接口表.
     * 
     * @param isgMarketChangeDelete
     *            gds市场变更申请删除DTO
     */
    void insertInterface(IsgMarketChangeDelete isgMarketChangeDelete);

}
