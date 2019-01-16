/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberInfoSync;

/**
 * 会员同步个人信息Mapper.
 * @author linyuheng
 *
 */
public interface IsgMemberInfoSyncMapper {
    int deleteByPrimaryKey(Short interfaceDetailId);

    int insert(IsgMemberInfoSync record);

    int insertSelective(IsgMemberInfoSync record);

    IsgMemberInfoSync selectByPrimaryKey(Short interfaceDetailId);

    int updateByPrimaryKeySelective(IsgMemberInfoSync record);

    int updateByPrimaryKey(IsgMemberInfoSync record);

    List<IsgMemberInfoSync> selectByParams(Map<String, Object> params);
}