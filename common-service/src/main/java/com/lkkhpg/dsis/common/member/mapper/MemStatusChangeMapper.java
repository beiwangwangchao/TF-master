/*
 *
 */

package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemStatusChange;

/**
 * 会员状态改变mapper.
 * 
 * @author yuchuan.zeng@hand-china.com
 *
 */
public interface MemStatusChangeMapper {
    int deleteByPrimaryKey(Long changeId);

    int insert(MemStatusChange record);

    int insertSelective(MemStatusChange record);

    MemStatusChange selectByPrimaryKey(Long changeId);

    int updateByPrimaryKeySelective(MemStatusChange record);

    int updateByPrimaryKey(MemStatusChange record);

    List<MemStatusChange> selectByApplyDateAndmemberId(MemStatusChange memStatusChange);

    Long updateApproveStatusByAppNo(Map<String, Object> map);

    Long updateSynStatusAndMsgByMemberId(Map<String, Object> map);

    MemStatusChange selectByAppNo(String appNo);
    
    Long validRecord(MemStatusChange memStatusChange);

    /**
     * 根据会员卡号查询正在审核中的申请记录编号
     * 
     * @param memberCode
     *            会员卡号
     * @return 申请编号
     */
    String selectAppNoByMemberCode(@Param("memberCode") String memberCode);
}