/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmOrgDefination;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

/**
 * 组织定义Service接口.
 * 
 * @author wangc
 *
 */
public interface ISpmOrganizationService extends ProxySelf<ISpmOrganizationService> {
    
    /**
     * 
     * @param request
     *           请求上下文
     * @param spmOrgDefination
     *           组织定义dto
     * @param page
     *           页码
     * @param pageSize
     *           页数
     * @return 组织定义列表
     */
    List<SpmOrgDefination> queryByOrganization(IRequest request, SpmOrgDefination spmOrgDefination,
                                               int page, int pageSize);


    List<SpmOrgDefination> queryByOrganization(IRequest request, SpmOrgDefination spmOrgDefination);
    /**
     *
     * @param request
     *           请求上下文
     * @param spmOrgDefination
     *           组织定义dto
     * @param page
     *           页码
     * @param pageSize
     *           页数
     * @return 组织定义列表
     */
    List<SpmOrgDefination> queryByOrganization2(IRequest request, SpmOrgDefination spmOrgDefination,
                                                int page, int pageSize);
    
    /**
     * 
     * @param request
     *              请求上下文
     * @param spmOrgDefinations
     *              组织定义dto
     * @return 组织定义列表
     * @throws SystemProfileException
     */
    List<SpmOrgDefination> saveOrgDefinations(IRequest request, @StdWho List<SpmOrgDefination> spmOrgDefinations)
            throws SystemProfileException;

    /**
     * 编辑销售组织.
     * @param request
     * @param spmSalesOrganization
     * @param fal 
     * @return 响应数据
     */
    SpmSalesOrganization saveSpmSalesOrganization(IRequest request, @StdWho SpmSalesOrganization spmSalesOrganization, boolean fal);

    /**
     * 编辑库存组织.
     * @param request
     * @param spmInvOrganization
     * @param fal 
     * @return 响应数据
     */
    SpmInvOrganization saveSpmInvOrganization(IRequest request, @StdWho SpmInvOrganization spmInvOrganization, boolean fal);
}
