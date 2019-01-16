/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgUpstreamChange;

/**
 * 下载移线会员资料Mapper.
 * @author linyuheng
 *
 */
public interface IsgUpstreamChangeMapper {
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgUpstreamChange record);

    int insertSelective(IsgUpstreamChange record);

    IsgUpstreamChange selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgUpstreamChange record);

    int updateByPrimaryKey(IsgUpstreamChange record);

    void updateUploadFlagToY(Map<String, Object> map);

    void updateStatusAndMessage(Map<String, Object> map);
    
    List<IsgUpstreamChange> selectProcessStatusIsS(String alertNo);

    List<IsgUpstreamChange> selectByParams(Map<String, Object> params);
}