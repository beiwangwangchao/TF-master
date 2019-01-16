/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemSite;

/**
 * 会员地点Mapper.
 * 
 * @author frank.li
 */
public interface MemSiteMapper {
    int deleteByPrimaryKey(MemSite memSite);

    int insert(MemSite record);

    int insertSelective(MemSite record);

    MemSite selectByPrimaryKey(Long siteId);

    List<MemSite> selectByMemberId(Long memberId);

    List<MemSite> selectHomeCtactLocByMemberId(Long memberId);
    
    List<MemSite> selectMemSite(MemSite memSite);

    int updateByPrimaryKeySelective(MemSite record);

    int updateByPrimaryKey(MemSite record);

    int updateDefaultFlag(@Param("memberId") Long memberId, @Param("siteUseCode") String siteUseCode, @Param("locationId") Long locationId);
    
    List<MemSite> selectCtactByMemberId(Long memberId);
    
    int deleteByMemberId(Long memberId);
    
    int upgradeMemberId(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);
    
    
    ////
    Long selectLocationId(@Param("memberId") Long memberId, @Param("siteUseCode") String siteUseCode);
    
    /**
     * 根据市场code判断是否需要隐藏州省
     * @param code 市场
     * @return Y为是
     */
    String isHideState(@Param("code") String code);
}