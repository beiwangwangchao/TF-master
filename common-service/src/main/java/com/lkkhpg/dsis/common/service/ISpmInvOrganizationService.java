/*
 *
 */
package com.lkkhpg.dsis.common.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.config.dto.OrgSelection;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 地址Service接口.
 * 
 * @author frank.li
 */
public interface ISpmInvOrganizationService extends ProxySelf<ISpmInvOrganizationService> {

    /**
     * 保存库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganizations
     *            库存组织List
     * @return 库存组织List
     */
    List<SpmInvOrganization> saveInvOrganization(IRequest request, @StdWho List<SpmInvOrganization> invOrganizations);

    /**
     * 删除库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganizations
     *            库存组织List
     * @return boolean
     */
    boolean deleteInvOrganization(IRequest request, List<SpmInvOrganization> invOrganizations);

    /**
     * 查询库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganization
     *            库存组织DTO
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return 库存组织List
     */
    List<SpmInvOrganization> queryInvOrganizations(IRequest request, SpmInvOrganization invOrganization, int page,
            int pagesize);

    /**
     * 不分页查询库存组织.
     * 
     * @param request
     *            请求上下文
     * @param invOrganization
     *            库存组织dto
     * @return 库存组织list
     */
    List<SpmInvOrganization> querySpmInvOrganizations(IRequest request, SpmInvOrganization invOrganization);

    /**
     * 获取有效期结束时间 大于当前时间 或 为null的库存组织.
     * 
     * @param request
     *            请求上下文
     * @return 库存组织list
     */
    List<SpmInvOrganization> getValidOrg(IRequest request);

    /**
     * 获取有效期结束时间 大于当前时间 或 为null的库存组织.
     *
     * @param request
     *            请求上下文
     * @return 库存组织list
     */
    List<SpmInvOrganization> getValidOrg2(IRequest request);

    /**
     * 根据条件查出库存组织信息（包括对应的OU）.
     * 
     * @param request
     *            请求上下文
     * @param defaultOrgId
     *            默认库存组织ID
     * @return orgSelection 库存组织Selection Dto
     */
    OrgSelection getOrgSelection(IRequest request, Long defaultOrgId);

    /**
     * 获取分配的库存组织.
     * 
     * @param request
     *            请求上下文.
     * @return 已分配的库存组织.
     */
    List<SpmInvOrganization> getAssignInvOrganization(IRequest request);

    /**
     * 获取当前供货的库存组织.
     * 
     * @param request
     *            请求上下文
     * @return 当前供货的库存组织
     */
    List<SpmInvOrganization> getCurrentSupplyInvOrgs(IRequest request);

    /**
     * 获取销售组织的供货组织
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织
     * @return 供货组织
     */
    List<SpmInvOrganization> getSupplyInvOrgsBySalesOrg(IRequest request, Long salesOrgId);

    /**
     * 根据订单ID获取供货组织.
     * 
     * @param request
     *            请求上下文
     * @param orderId
     *            订单ID
     * @return 销售组织
     */

    public List<SpmInvOrganization> getSupplyInvOrgsByOrderId(IRequest request, Long orderId);

    /**
     * 获取销售组织的供货的库存组织和货币明细.
     * 
     * @param request
     *            请求上下文
     * @param salesOrgId
     *            销售组织id
     * @return 销售组织的供货的库存组织和货币明细.
     */
    Map<String, Object> getSupplyInvOrgsAndCurrencyBySalesOrg(IRequest request, Long salesOrgId);

    /**
     * 获取用户可访问的库存组织.
     * 
     * @param requestContext
     *            请求上下文
     * @return 可访问的库存组织列表
     */
    List<SpmInvOrganization> queryInvOrgsByRole(IRequest requestContext);

    /**
     * 获取用户可访问的库存归集中心组织.
     * 
     * @param requestContext
     *            请求上下文
     * @return 可访问的库存归集中心组织列表
     */
    List<SpmInvOrganization> queryCostOrgsByRole(IRequest requestContext);

}
