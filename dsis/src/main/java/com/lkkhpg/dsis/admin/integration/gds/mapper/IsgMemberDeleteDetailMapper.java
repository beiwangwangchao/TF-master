/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberDeleteDetail;

/**
 * 下载批删会员信息行资料Mapper.
 * @author linyuheng
 *
 */
public interface IsgMemberDeleteDetailMapper {
    int deleteByPrimaryKey(Long interfaceDetailId);

    int insert(IsgMemberDeleteDetail record);

    int insertSelective(IsgMemberDeleteDetail record);

    IsgMemberDeleteDetail selectByPrimaryKey(Long interfaceDetailId);

    int updateByPrimaryKeySelective(IsgMemberDeleteDetail record);

    int updateByPrimaryKey(IsgMemberDeleteDetail record);

    void updateUploadFlagToY(Map<String, Object> map);

    void updateStatusAndMessage(Map<String, Object> map);

    List<IsgMemberDeleteDetail> selectByParams(Map<String, Object> params);
}