/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChange;

/**
 * 会员转入、转出Mapper.
 * 
 * @author mclin
 */
public interface IsgMarketChangeMapper {
    int deleteByPrimaryKey(Long interfaceId);

    int insert(IsgMarketChange record);

    int insertSelective(IsgMarketChange record);

    IsgMarketChange selectByPrimaryKey(Long interfaceId);

    int updateByPrimaryKeySelective(IsgMarketChange record);

    int updateByPrimaryKey(IsgMarketChange record);
    
    List<IsgMarketChange> queryTransOutList(String adviseNo);
    
    List<IsgMarketChange> queryTransInList(String adviseNo);
    
    int updateProcessStatusByAdviseNo(IsgMarketChange record);
    
    int updateUploadFlag(IsgMarketChange record);
    
    List<IsgMarketChange> querySuccessByGdsId(String gdsId);
    
    List<IsgMarketChange> selectByParams(Map<String, Object> params);
}