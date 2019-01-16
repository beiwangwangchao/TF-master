/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.member.dto.MemUpstreamChange;

/**
 * 会员上线变更Mapper.
 * 
 * @author linyuheng
 */
public interface MemUpstreamChangeMapper {
    int deleteByPrimaryKey(Long changeId);

    int insert(MemUpstreamChange record);

    int insertSelective(MemUpstreamChange record);

    MemUpstreamChange selectByPrimaryKey(Short changeId);

    /**
     * 查询变更上线记录.
     * 
     * @param upstreamChange
     *            变更上线DTO
     * @return 变更上线DTO列表
     */
    List<MemUpstreamChange> queryUpstreamChange(MemUpstreamChange upstreamChange);

    int updateByPrimaryKeySelective(MemUpstreamChange record);

    int updateByPrimaryKey(MemUpstreamChange record);

    /**
     * 根据memberId查找记录.
     * 
     * @param MemberId
     *            会员表主键
     * @return 会员变更上线列表
     */
    List<MemUpstreamChange> queryUpstreamChangeByMemberId(Long MemberId);

    /**
     * 更新同步状态和接口信息.
     * 
     * @param map
     */
    void updateSynStatusAndMsgByMemberId(Map<String, Object> map);

    /**
     * 更新审批状态.
     * 
     * @param map
     */
    void updateApproveStatusByAppNo(Map<String, Object> map);

    /**
     * 查询同步状态非S的记录.
     * 
     * @return 会员变更上线列表
     */
    List<MemUpstreamChange> selectBySynStatusNoS();

    /**
     * 根据AppNo查询.
     * 
     * @param appNo
     *            申请号
     * @return 会员变更上线DTO
     */
    MemUpstreamChange selectByAppNo(String appNo);

    /**
     * 根据会员卡号查询正在审核中的申请记录编号
     * 
     * @param memberCode
     *            会员卡号
     * @return 申请编号
     */
    String selectAppNoByMemberCode(@Param("memberCode") String memberCode);
}