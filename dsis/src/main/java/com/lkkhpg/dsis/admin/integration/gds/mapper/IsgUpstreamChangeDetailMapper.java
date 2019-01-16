/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgUpstreamChangeDetail;

/**
 * 下载移线会员信息行资料Mapper.
 * @author linyuheng
 *
 */
public interface IsgUpstreamChangeDetailMapper {
    int deleteByPrimaryKey(Long interfaceDetailId);

    int insert(IsgUpstreamChangeDetail record);

    int insertSelective(IsgUpstreamChangeDetail record);

    IsgUpstreamChangeDetail selectByPrimaryKey(Long interfaceDetailId);

    int updateByPrimaryKeySelective(IsgUpstreamChangeDetail record);

    int updateByPrimaryKey(IsgUpstreamChangeDetail record);

    void updateUploadFlagToY(Map<String, Object> map);

    void updateStatusAndMessage(Map<String, Object> map);

    List<IsgUpstreamChangeDetail> selectByParams(Map<String, Object> params);
}