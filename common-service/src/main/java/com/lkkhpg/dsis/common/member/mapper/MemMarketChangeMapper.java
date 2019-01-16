/*
 *
 */
package com.lkkhpg.dsis.common.member.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.member.dto.MemMarketChange;

/**
 * 会员市场变更Mapper.
 * 
 * @author linyuheng
 */
public interface MemMarketChangeMapper {
    int deleteByPrimaryKey(Long changeId);

    int insert(MemMarketChange record);

    int insertSelective(MemMarketChange record);

    /**
     * 查询会员变更市场记录.
     * 
     * @param mmMemMarketChange
     *            会员变更市场DTO
     * @return 会员变更市场DTO列表
     */
    List<MemMarketChange> queryMarketChange(MemMarketChange mmMemMarketChange);

    /**
     * 查询审核中的变更市场记录.
     * 
     * @param memMarketChange
     *            会员变更市场DTO
     * @return 会员变更市场DTO列表
     */
    List<MemMarketChange> queryApprovingMarketChange(MemMarketChange memMarketChange);

    int updateByPrimaryKeySelective(MemMarketChange record);

    int updateByPrimaryKey(MemMarketChange record);

    /**
     * 根据memberId查找记录.
     * 
     * @param MemberId
     *            会员表主键
     * @return 市场DTO集合
     */
    List<MemMarketChange> queryMarketChangeByMemberId(Long MemberId);

    /**
     * 根据市场编码查市场ID .
     * 
     * @param map
     *            参数集合
     * @return 市场ID
     */
    Long getMarketIdByCode(Map<String, Object> map);
    
    /**
     * 根据变更市场表主键删除记录.
     * @param changeId 变更市场表主键
     */
    void deleteMarketChangeByChangeId(Long changeId);
}