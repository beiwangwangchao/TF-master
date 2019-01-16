/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

/**
 * 销售区域Service接口.
 * 
 * @author frank.li
 * 
 * @author wuyichu
 */
public interface ISpmSalesOrganizationService extends ProxySelf<ISpmSalesOrganizationService> {

    /**
     * 保存销售区域.
     *
     * @param request            请求上下文
     * @param salesOrganizations 销售区域List
     * @return 销售区域List
     */
    List<SpmSalesOrganization> saveSalesOrganization(IRequest request,
                                                     @StdWho List<SpmSalesOrganization> salesOrganizations);

    /**
     * 增加树节点.
     *
     * @param request           请求上下文
     * @param salesOrganization 销售区域DTO
     * @return salesOrganization对象
     */
    SpmSalesOrganization submitSalesOrganization(IRequest request, SpmSalesOrganization salesOrganization);

    /**
     * 删除销售区域.
     *
     * @param request            请求上下文
     * @param salesOrganizations 销售区域List
     * @return boolean
     */
    boolean deleteSalesOrganization(IRequest request, List<SpmSalesOrganization> salesOrganizations);

    /**
     * 查询销售区域.
     *
     * @param request           请求上下文
     * @param salesOrganization 销售区域DTO
     * @param page              页
     * @param pagesize          页数
     * @return 销售区域List
     */
    List<SpmSalesOrganization> querySalesOrganization(IRequest request, SpmSalesOrganization salesOrganization,
                                                      int page, int pagesize);

    List<SpmSalesOrganization> querySalesOrgByTime(IRequest request, SpmSalesOrganization salesOrganization,
                                                   int page, int pagesize);

    /**
     * 查询所有销售区域.
     *
     * @param request           请求上下文
     * @param salesOrganization 销售区域DTO
     * @param page              页
     * @param pagesize          页数
     * @return 销售区域List
     */
    List<SpmSalesOrganization> queryAllSalesOrganization(IRequest requestContext, SpmSalesOrganization salesOrganization, int page,
                                                         int pagesize);

    /**
     * 查询父节点为空的数据.
     *
     * @param request           请求上下文
     * @param salesOrganization 销售组织dto
     * @param page              页
     * @param pagesize          每页显示的行数
     * @return 销售组织list
     */
    List<SpmSalesOrganization> queryNull(IRequest request, SpmSalesOrganization salesOrganization, int page,
                                         int pagesize);


    /**
     * 查询销售区域明细，主要返回包含销售区域的市场明细以及默认货币明细.
     *
     * @param request    请求上下文
     * @param salesOrgId 销售区域id
     * @return 销售区域详细信息
     */
    SpmSalesOrganization getSalesOrganizationDetail(IRequest request, Long salesOrgId);

    /**
     * 获取当前分配的销售区域.
     *
     * @param request 请求上下文.
     * @return 已分配的销售区域.
     */
    List<SpmSalesOrganization> getAssignSalesOrganization(IRequest request);

    /**
     * 查询父节点数据.
     *
     * @param request      请求上下文
     * @param organization 销售组织dto
     * @return 销售组织list
     */
    List<SpmSalesOrganization> queryBySalesFu(IRequest request, SpmSalesOrganization organization);

    /**
     * 查询子节点数据.
     *
     * @param request      请求上下文
     * @param organization 销售组织dto
     * @return 销售组织list
     */
    List<SpmSalesOrganization> queryBySalesZi(IRequest request, SpmSalesOrganization organization);


    List<SpmSalesOrganization> querySalesOrganizationByRole(IRequest request);
}


