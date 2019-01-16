/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmServiceCenterAssign;

/**
 * 服务中心分配会员Mapper.
 * 
 * @author wangc
 *
 */
public interface SpmServiceCenterAssignMapper {
    int deleteByPrimaryKey(Long assignId);

    int insert(SpmServiceCenterAssign record);

    int insertSelective(SpmServiceCenterAssign record);

    SpmServiceCenterAssign selectByPrimaryKey(Long assignId);

    int updateByPrimaryKeySelective(SpmServiceCenterAssign record);

    int updateByPrimaryKey(SpmServiceCenterAssign record);

    List<SpmServiceCenterAssign> getSpmMembersByCenterId(Long spmServiceCenterId);

    /**
     * 判断会员是否在指定ServiceCenter下.
     * 
     * @param record
     *            服务中心分配DTO.
     * @return 会员对应的服务中心分配DTO信息.
     */
    SpmServiceCenterAssign judgeMemberInSC(SpmServiceCenterAssign record);
}