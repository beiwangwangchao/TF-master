/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeOutApply;

/**
 * 转出本市场（源市场）的申请 .
 * 
 * @author chuangsheng.zhang.
 */
public interface IsgMarketChangeOutApplyMapper {
    int deleteByPrimaryKey(Short interfaceId);

    int insert(IsgMarketChangeOutApply record);

    int insertSelective(IsgMarketChangeOutApply record);

    IsgMarketChangeOutApply selectByPrimaryKey(Short interfaceId);

    int updateByPrimaryKeySelective(IsgMarketChangeOutApply record);

    int updateByPrimaryKey(IsgMarketChangeOutApply record);

    List<IsgMarketChangeOutApply> selectByParams(Map<String, Object> params);
}