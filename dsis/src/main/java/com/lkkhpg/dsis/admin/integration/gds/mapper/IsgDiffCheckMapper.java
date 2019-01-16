/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgDiffCheck;

/**
 * 差异查询表.
 * @author Clerifen Li
 */
public interface IsgDiffCheckMapper {
    
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgDiffCheck record);

    int insertSelective(IsgDiffCheck record);

    IsgDiffCheck selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgDiffCheck record);

    int updateByPrimaryKey(IsgDiffCheck record);

    List<IsgDiffCheck> selectDealerByCheckEntityType();

    List<IsgDiffCheck> selectByAdviseNo(String adviseNo);

    void updateSynStatusAndMsgById(Map<String, Object> map);

    void updateUploadFlag(Map<String, Object> map);

    List<IsgDiffCheck> queryDiffChecks(IsgDiffCheck record);

    void updateByAdviseAndEntityNo(Map<String, Object> map);

    List<IsgDiffCheck> selectByParams(Map<String, Object> params);
    
}