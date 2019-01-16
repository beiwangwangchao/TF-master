/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.mws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemSite;

/**
 * 会员地址mapper.
 * 
 * @author guanghui.liu
 */
public interface MemberSiteMapper {

    MemSite selectByPrimaryKey(Long siteId);

    List<MemSite> selectMemSites(MemSite memSite);

    int selectCountByMemberAndType(MemSite memSite);

    Long selectDefaultSiteByMemberAndType(MemSite memSite);

    int insert(MemSite memSite);

    int updateByPrimaryKeySelective(MemSite memSite);

    int updateOtherSiteDefault(MemSite memSite);
    
    int updateOtherSiteDefault2(@Param("requestId")Long requestId, @Param("programId")Long programId, @Param("lastUpdatedBy")Long lastUpdatedBy, 
    		@Param("memberId")Long memberId, @Param("siteUseCode")String siteUseCode, @Param("siteId")Long siteId);

    int updateDefaultFlag(@Param("requestId")Long requestId, @Param("programId")Long programId, @Param("lastUpdatedBy")Long lastUpdatedBy, 
    		@Param("memberId")Long memberId, @Param("siteUseCode")String siteUseCode, @Param("siteId")Long siteId);
    
    int updateDefaultFlag2(MemSite memSite);

    int deleteByPrimaryKey(MemSite memSite);
}