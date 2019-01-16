/*
 *
 */
package com.lkkhpg.dsis.mws.mapper;

import com.lkkhpg.dsis.mws.dto.MemberInfo;

/**
 * mws会员信息Mapper.
 * 
 * @author guanghui.liu
 */
public interface MemberInfoMapper {

    MemberInfo selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(MemberInfo memberInfo);
    
    MemberInfo queryRemBalByPrimaryKey(Long memberId);
    
    int updateMemberLastUpdateDate(MemberInfo memberInfo);
}