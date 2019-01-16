/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgOverseasSponsor;

/**
 * 小批量下载海外推荐人mapper.
 * @author gulin
 *
 */
public interface IsgOverseasSponsorMapper {
    int deleteByPrimaryKey(Short interfaceId);

    int insert(IsgOverseasSponsor record);

    int insertSelective(IsgOverseasSponsor record);

    IsgOverseasSponsor selectByPrimaryKey(Short interfaceId);

    int updateByPrimaryKeySelective(IsgOverseasSponsor record);

    int updateByPrimaryKey(IsgOverseasSponsor record);

    List<IsgOverseasSponsor> selectByParams(Map<String, Object> params);
}