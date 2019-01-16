/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberDelete;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgUpstreamChange;

/**
 * 下载批删会员资料Mapper.
 * @author linyuheng
 *
 */
public interface IsgMemberDeleteMapper {
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgMemberDelete record);

    int insertSelective(IsgMemberDelete record);

    IsgMemberDelete selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgMemberDelete record);

    int updateByPrimaryKey(IsgMemberDelete record);

    void updateUploadFlagToY(Map<String, Object> map);

    void updateStatusAndMessage(Map<String, Object> map);

    List<IsgUpstreamChange> selectProcessStatusIsS(String alterNo);
    
    List<IsgUpstreamChange> selectByParams(Map<String, Object> params);
}