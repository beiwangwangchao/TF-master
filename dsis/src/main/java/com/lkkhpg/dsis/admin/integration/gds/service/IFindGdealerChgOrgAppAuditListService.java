/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeList;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 获取其他市场转入本市场（目标市场）申请的待审批列表.
 * 
 * @author shenqb
 */
public interface IFindGdealerChgOrgAppAuditListService extends ProxySelf<IFindGdealerChgOrgAppAuditListService> {

    /**
     * 根据源销售机构（源市场）代号获取其他市场转入本市场（目标市场）申请的待审批列表.
     * 
     * @param request
     *            请求上下文
     * @param subOrg
     *            源销售机构（源市场）代号
     * @param orgCode
     *            市场编码
     * @return 会员市场变更列表
     * @throws Exception
     */
    List<MemMarketChange> findGdealerChgOrgAppAuditList(IRequest request, String subOrg, String orgCode)
            throws Exception;

    /**
     * 新开事务，插入接口表.
     * 
     * @param isgMarketChangeList
     *            gds市场变更申请列表DTO
     */
    void insertInterface(IsgMarketChangeList isgMarketChangeList);
}
