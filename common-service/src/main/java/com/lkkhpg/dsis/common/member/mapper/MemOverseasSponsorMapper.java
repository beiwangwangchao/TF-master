/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import com.lkkhpg.dsis.common.member.dto.MemOverseasSponsor;

/**
 * 海外推荐人Mapper.
 * 
 * @author gulin
 */
public interface MemOverseasSponsorMapper {
    int deleteByPrimaryKey(Short sponsorId);

    int insert(MemOverseasSponsor record);

    int insertSelective(MemOverseasSponsor record);

    MemOverseasSponsor selectByPrimaryKey(Short sponsorId);

    int updateByPrimaryKeySelective(MemOverseasSponsor record);

    int updateByPrimaryKey(MemOverseasSponsor record);
    
    void deleteDataByOrgCode(String orgCode);
    
    void deleteByDealerNo(String dealerNo);
    
    void deleteAllData();
    
    /* Mclin 添加 */
    MemOverseasSponsor getOverseasSponsorByNo(String sponsorNo);
    
    int insertOrUpdateOverseasSponsor(MemOverseasSponsor memOverseasSponsor);
}