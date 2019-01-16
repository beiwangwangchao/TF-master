/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeInApprove;

/**
 * GDS市场变更审批接口表Mapper.
 * @author shenqb
 *
 */
public interface IsgMarketChangeInApproveMapper {
    int insert(IsgMarketChangeInApprove record);

    int insertSelective(IsgMarketChangeInApprove record);

    List<IsgMarketChangeInApprove> selectByParams(Map<String, Object> params);
}