/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeInApprove;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeList;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 市场列表Service.
 * 
 * @author yuchuan.zeng@hand-china.com
 */
public interface IIsgMarketChangeListService extends ProxySelf<IIsgMarketChangeListService> {

    /**
     * 获取本市场（源市场）转出申请的列表.
     * 
     * @param request
     *            请求上下文 可以为null
     * @param subOrg
     *            新销售机构（源市场）代号
     * @return List<IsgMarketChangeList> 市场（源市场）转出申请的列表
     */
    List<IsgMarketChangeList> findGdealerChgOrgAppListService(IRequest request, String subOrg);

    /**
     * 保存 变更市场申请.
     * 
     * @param request
     *            请求上下文
     * @param httpRequest
     *            页面请求
     * @param body
     *            保存审批响应体
     * @param memMarketChange
     *            市场变更DTO
     * @return Boolean
     * @throws Exception
     */
    Boolean saveGdealerChgOrgAppAudit(IRequest request, MemMarketChange memMarketChange) throws Exception;

    /**
     * 新开事务，插入IsgMarketChangeList接口表.
     * 
     * @param mc
     *            gds市场变更申请列表DTO
     */
    void insertIsgMarketChangeListInterface(IsgMarketChangeList mc);

    /**
     * 新开事务，插入IsgMarketChangeInApprove接口表.
     * 
     * @param isgMarketChangeInApprove
     *            市场变更审批接口表DTO
     */
    void insertIsgMarketChangeInApproveInterface(IsgMarketChangeInApprove isgMarketChangeInApprove);
}
