/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.OrgSelection;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;

/**
 * 库存组织MAPPER.
 * 
 * @author frank.li
 */
public interface SpmInvOrganizationMapper {
    int deleteByPrimaryKey(Long invOrgId);

    int insert(SpmInvOrganization record);

    int insertSelective(SpmInvOrganization record);

    SpmInvOrganization selectByPrimaryKey(Long invOrgId);

    SpmInvOrganization queryBaseInfo(@Param("invOrgId") Long invOrgId);

    int updateByPrimaryKeySelective(SpmInvOrganization record);

    int updateByPrimaryKey(SpmInvOrganization record);

    List<SpmInvOrganization> querySpmInvOrganizations(SpmInvOrganization spmInvOrganization);

    /**
     * 获取有效期结束时间 大于当前时间 或 为null的库存组织.
     * 
     * @return 库存组织list
     */
    List<SpmInvOrganization> getValidOrg();
    /**
     * 获取有效期结束时间 大于当前时间 或 为null的库存组织.
     *
     * 数据屏蔽  只显示当前市场所在公司的组织架构中的所有库存组织（公司组织架构下总分公司的所有库存组织）
     * @return 库存组织list
     */
    List<SpmInvOrganization> getValidOrg2();

    OrgSelection getOrgSelection(SpmInvOrganization record);

    List<SpmInvOrganization> getCurrentOrganization(Map<String, Object> map);

    List<SpmInvOrganization> queryByUserAndRole();

    /**
     * 根据业务实体ID查询库存组织.
     * 
     * @param spmInvOrganization
     * @return 库存组织List
     */
    List<SpmInvOrganization> queryOrgByOperationUnitId(SpmInvOrganization spmInvOrganization);

    /**
     * 根据业务实体ID关联库存组织.
     * 
     * @param record
     * @return int
     */
    int updateOrgByOperationUnitId(SpmInvOrganization record);

    /**
     * 获取当前供货库存组织.
     * 
     * @return 库存组织实体.
     */
    List<SpmInvOrganization> getCurrentSupplyInvOrgs();

    /**
     * 获取当前供货库存组织.
     * 
     * @param salesOrgId
     *            销售组织Id
     * @return 库存组织实体.
     */

    List<SpmInvOrganization> getSupplyInvOrgsBySalesOrg(Long salesOrgId);

    /**
     * 获取当前供货库存组织.
     * 
     * @param orderId
     *            订单Id
     * @return 库存组织实体.
     */

    List<SpmInvOrganization> getSupplyInvOrgsByOrderId(Long orderId);

    /**
     * 获取用户可访问的库存组织.
     * 
     * @param roleId
     *            角色ID
     * @param userId
     *            用户ID
     * @return 可访问的库存组织列表
     */
    List<SpmInvOrganization> queryInvOrgsByRole(@Param("roleId") Long roleId, @Param("userId") Long userId);

    /**
     * 当前用户下可访问的库存归集中心.
     * 
     * @return 可访问的库存归集中心集合
     */
    List<SpmInvOrganization> queryCostOrgsByRole();

}