/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberSync;

/**
 * 会员同步信息Mapper.
 * @author linyuheng
 *
 */
public interface IsgMemberSyncMapper {
    int deleteByPrimaryKey(Short interfaceId);

    int insert(IsgMemberSync record);

    int insertSelective(IsgMemberSync record);

    IsgMemberSync selectByPrimaryKey(Short interfaceId);

    int updateByPrimaryKeySelective(IsgMemberSync record);

    int updateByPrimaryKey(IsgMemberSync record);

    List<IsgMemberSync> selectByParams(Map<String, Object> params);
}