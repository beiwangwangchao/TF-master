/*
 *
 */
package com.lkkhpg.dsis.admin.config.service;

import java.util.List;

import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmOperatingUnit;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 业务实体Service接口.
 * @author hanrui.huang
 *
 */
public interface ISpmOperatingUnitService {

    /**
     * 业务实体查询.
     * @param request
     *            请求上下文
     * @param spmOperatingUnit
     *            业务实体DTO.
     * @param page
     *            页.
     * @param pagesize
     *            页数.
     * @return SpmOperatingUnits 业务实体List.
     */
    List<SpmOperatingUnit> querySpmOperatingUnit(IRequest request, SpmOperatingUnit spmOperatingUnit, int page,
            int pagesize);

    /**
     * 业务实体查询.
     *
     * 数据屏蔽  只显示和它所在公司有关的公司业务实体
     *
     * @param request
     *            请求上下文
     * @param spmOperatingUnit
     *            业务实体DTO.
     * @param page
     *            页.
     * @param pagesize
     *            页数.
     * @return SpmOperatingUnits 业务实体List.
     */
    List<SpmOperatingUnit> querySpmOperatingUnit2(IRequest request, SpmOperatingUnit spmOperatingUnit, int page,
                                                 int pagesize);
    /**
     * 保存业务实体.
     * @param request
     *            请求上下文
     * @param spmOperatingUnit
     *            业务实体DTO.
     * @return SpmOperatingUnits 业务实体List
     * @throws SystemProfileException
     */
    List<SpmOperatingUnit> saveSpmOperatingUnit(IRequest request, @StdWho List<SpmOperatingUnit> spmOperatingUnit)
            throws SystemProfileException;

    /**
     * 查询库存组织.
     * 
     * @param request
     *            请求上下文
     * @param spmInvOrganization
     *            库存组织DTO
     * @param page
     *            页
     * @param pageSize
     *            页数
     * @return SpmInvOrganizations 库存组织List
     */
    List<SpmInvOrganization> querySpmInvOrganization(IRequest request, SpmInvOrganization spmInvOrganization,
            int page, int pageSize);

    /**
     * 保存库存组织.
     * @param request
     *            请求上下文
     * @param spmInvOrganizations
     *            库存组织DTO.
     * @return SpmOperatingUnits 库存组织List
     */
    List<SpmInvOrganization> saveSpmInvOrganization(IRequest request,
            @StdWho List<SpmInvOrganization> spmInvOrganizations);

}
