/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.config.mapper;

import com.lkkhpg.dsis.common.config.dto.SpmSupply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供货关系MAPPER.
 * 
 * @author chenjingxiong
 */
public interface SpmSupplyMapper {
    int deleteByPrimaryKey(Long spmSupply);

    int insert(SpmSupply record);

    int insertSelective(SpmSupply record);

    SpmSupply selectByPrimaryKey(Long spmSupply);

    int updateByPrimaryKeySelective(SpmSupply record);

    int updateByPrimaryKey(SpmSupply record);

    List<SpmSupply> selectBySpmSupply(SpmSupply spmSupply);
    
    List<SpmSupply> queryBySpmSupply(SpmSupply record);

    /*add furong.tang*/
    List<SpmSupply> queryInvOrgBySpmSupply(SpmSupply spmSupply);

    Long queryCompanyIdByInvOrgId(@Param("invOrgId") Long invOrgId);

    String queryIsCheckByMarketId(@Param("marketId") Long marketId);
    
    List<SpmSupply> queryBySupply(SpmSupply record);

    List<SpmSupply> queryBySupplyByUserAndRole(SpmSupply record);

    List<SpmSupply> queryByAddress(SpmSupply record); 
    
    int deleteBySelective(SpmSupply record);
    
    Integer querySpmSupply(SpmSupply record);
    
}