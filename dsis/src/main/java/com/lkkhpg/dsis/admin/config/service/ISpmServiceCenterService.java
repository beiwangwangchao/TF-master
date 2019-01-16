/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenter;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenterAssign;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 服务中心Service.
 * 
 * @author hanrui.huang
 *
 */
public interface ISpmServiceCenterService extends ProxySelf<ISpmServiceCenterService> {

    /**
     * 校验.
     * 
     * @param request
     *            统一上下文
     * @param spmServiceCenters
     *            服务中心DTO集合
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    void validator(IRequest request, List<SpmServiceCenter> spmServiceCenters) throws SystemProfileException;

    /**
     * 服务中心查询.
     * 
     * @param request
     *            请求上下文
     * @param spmServiceCenter
     *            服务中心DTO
     * @param page
     *            页
     * @param pageSize
     *            页大小
     * @return SpmServiceCenters 服务中心List
     */
    List<SpmServiceCenter> queryServiceCenter(IRequest request, SpmServiceCenter spmServiceCenter, int page,
            int pageSize);

    /**
     * 服务中心查询和服务中心下面的会员.
     * 
     * @param request
     *            请求上下文
     * @param spmServiceCenter
     *            服务中心DTO
     * @param page
     *            页
     * @param pageSize
     *            页大小
     * @return SpmServiceCenters 服务中心List
     */
    List<SpmServiceCenter> queryServiceCenterWithMember(IRequest request, SpmServiceCenter spmServiceCenter, int page,
            int pageSize);

    /**
     * 服务中心保存.
     * 
     * @param requestContext
     *            请求上下文
     * @param spmServiceCenters
     *            服务中心DTO
     * @return 服务中心DTO
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    List<SpmServiceCenter> saveSpmServiceCenter(IRequest requestContext,
            @StdWho List<SpmServiceCenter> spmServiceCenters) throws SystemProfileException;

    /**
     * 服务中心详情页查询.
     * 
     * @param requestContext
     *            请求上下文
     * @param spmServiceCenter 服务对象
     * @return 服务中心DTO
     */
    SpmServiceCenter getSpmServiceCenter(IRequest requestContext, SpmServiceCenter spmServiceCenter);

    /**
     * 服务中心会员查询.
     * 
     * @param requestContext
     *            请求上下文
     * @param serviceCenterId
     *            服务中心id
     * @return 会员列表
     */
    List<SpmServiceCenterAssign> getSpmServiceCenterMembers(IRequest requestContext, Long serviceCenterId);

    /**
     * 服务中心会员删除.
     * 
     * @param requestContext
     *            请求上下文
     * @param spmServiceCenterAssigns
     *            服务中心会员列表
     * @return 会员列表
     */
    List<SpmServiceCenterAssign> deleteSpmServiceCenterMembers(IRequest requestContext,
            List<SpmServiceCenterAssign> spmServiceCenterAssigns);

    /**
     * 提交审核.
     * 
     * @param requestContext
     *            请求上下文
     * @param serviceCenters
     *            服务中心dyo
     * @return 服务中心列表
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    List<SpmServiceCenter> submitServiceCenter(IRequest requestContext, @StdWho List<SpmServiceCenter> serviceCenters)
            throws SystemProfileException;

    /**
     * 审核.
     * 
     * @param requestContext
     *            请求上下文
     * @param serviceCenterId
     *            服务中心ID
     * @param salesOrgId
     *            组织id
     * @return 是否操作成功
     * @throws Exception
     */
    Long approveServiceCenter(IRequest requestContext, Long serviceCenterId, Long salesOrgId) throws Exception;

    /**
     * 拒绝.
     * 
     * @param requestContext
     *            请求上下文
     * @param serviceCenterId
     *            服务中心ID
     * @return 是否操作成功
     */
    Long rejectServiceCenter(IRequest requestContext, Long serviceCenterId);

    /**
     * 关闭.
     * 
     * @param requestContext
     *            请求上下文
     * @param serviceCenters
     *            服务中心dyo
     * @return 服务中心列表
     * @throws SystemProfileException
     *             系统配置统一异常
     */
    List<SpmServiceCenter> closeServiceCenter(IRequest requestContext, @StdWho List<SpmServiceCenter> serviceCenters)
            throws SystemProfileException;

    /**
     * 动态切换市场.
     * 
     * @param request
     *            统一上下文
     * @param salesOrgId
     *            销售组织id
     * @return 返回市场
     */
    SpmMarket selectBySalesOrgId(IRequest requestContext, Long salesOrgId);
}
