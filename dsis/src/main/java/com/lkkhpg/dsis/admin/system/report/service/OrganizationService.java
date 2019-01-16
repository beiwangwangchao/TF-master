/*
 *
 */
package com.lkkhpg.dsis.admin.system.report.service;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;
import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 报表service.
 * 
 * @author huangjiajing
 */
public interface OrganizationService extends ProxySelf<OrganizationService> {

    /**
     * 查询history树结构数据.
     * @param request 请求上下文
     * @param tree dto参数
     * @return list集合
     */
    List<GdsMeDealerTree> queryMeTree(IRequest request, GdsMeDealerTree tree);

    /**
     * 订单创建时查询销售组织
     * @param marketId
     * @return
     */
   List<SpmSalesOrganization>queryOrganization(Long marketId);
    /**
     * 查询销售组织
     * @param request
     * @param marketId
     * @return
     */
    List<SpmSalesOrganization> selectOrganization(IRequest request,SpmSalesOrganization marketId);

    /**
     * 查询website树结构数据.
     * @param request 请求上下文
     * @param tree dto参数
     * @return list集合
     */
    List<OmkRtlSalaryBalance> queryRtlTree(IRequest request, OmkRtlSalaryBalance tree);
    

}
