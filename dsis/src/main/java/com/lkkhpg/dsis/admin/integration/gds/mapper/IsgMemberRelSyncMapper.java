/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberRelSync;

/**
 * 会员同步相关人信息Mapper.
 * @author linyuheng
 *
 */
public interface IsgMemberRelSyncMapper {
    int deleteByPrimaryKey(Long interfaceDetailId);

    int insert(IsgMemberRelSync record);

    int insertSelective(IsgMemberRelSync record);

    IsgMemberRelSync selectByPrimaryKey(Long interfaceDetailId);

    int updateByPrimaryKeySelective(IsgMemberRelSync record);

    int updateByPrimaryKey(IsgMemberRelSync record);

    List<IsgMemberRelSync> selectByParams(Map<String, Object> params);
}