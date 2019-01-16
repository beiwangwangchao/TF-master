/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import com.lkkhpg.dsis.common.config.dto.SpmSupplies;
import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.platform.core.IRequest;

import java.util.List;

/**
 * 供货组织Service接口.
 * 
 * @author wangc
 *
 */
public interface ISpmSupplyService {

    /**
     * 查询供货组织.
     * 
     * @param requestContext
     *            请求上下文
     * @param supply
     *            供货组织
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 供货关系列表
     */
    List<?> querySupply(IRequest requestContext, SpmSupply supply, int page, int pagesize);



    List<SpmSupply> querySupplyByUserAndRole(IRequest requestContext, SpmSupply supply, int page, int pagesize);
    /**
     * 保存供货组织.
     * 
     * @param requestContext
     *            请求上下文
     * @param supplies
     *            供货组织列表
     * @return 供货组织列表
     * @throws CommSystemProfileException 基础设置异常
     */
    SpmSupplies saveSupplies(IRequest requestContext, SpmSupplies supplies) throws CommSystemProfileException;
    
    /**
     * 删除供货组织.
     * 
     * @param requestContext
     *            请求上下文
     * @param supplies
     *            供货组织列表
     * @return 供货组织列表
     * @throws CommSystemProfileException 基础设置异常
     */
    boolean deleteSpmSupply(IRequest requestContext, List<SpmSupply> supplies) throws CommSystemProfileException;

    /**
     * 查询供货组织.
     * 
     * @param createRequestContext 请求上下文
     * @param supply 供货组织
     * @param page 页码
     * @param pagesize 页数
     * @return 供货组织列表
     */
    List<SpmSupply> query(IRequest createRequestContext, SpmSupply supply, int page, int pagesize);

    /**
     * 查询供货组织的销售区域.
     * 
     * @param request 请求上下文
     * @param spmSupply 供货组织
     * @param page 页码
     * @param pagesize 页数
     * @return 销售组织列表
     */
    List<SpmSupply> querySalesOrganization(IRequest request, SpmSupply spmSupply, int page,
                                           int pagesize);

    
    /**
     * 查询供货组织地点.
     * 
     * @param requestContext 请求上下文
     * @param spmSupply 供货组织
     * @param spmLocation 地址dto
     * @param page 页码
     * @param pagesize 页数
     * @return 地址
     */
    List<SpmSupply> queryAddress(IRequest requestContext, SpmSupply spmSupply, int page, int pagesize);

    /**
     * 删除供货组织销售区域.
     * 
     * @param requestContext
     *            请求上下文
     * @param spmSupplies
     *            供货组织列表
     */
     void deleteSupplySales(IRequest requestContext, List<SpmSupply> spmSupplies);

    /**
     * 删除供货组织地点.
     * 
     * @param requestContext
     *            请求上下文
     * @param spmSupplies
     *            供货组织列表
     */
     void deleteSupplyAddress(IRequest requestContext, List<SpmSupply> spmSupplies);

}
