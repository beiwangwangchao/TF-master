/*
 *
 */
package com.lkkhpg.dsis.common.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.product.dto.InvItemAssign;

/**
 * 库存组织mapper.
 * 
 * @author wangchao
 *
 */
public interface InvItemAssignMapper {
    int deleteByPrimaryKey(Long assignId);

    int insert(InvItemAssign record);

    int insertSelective(InvItemAssign record);

    InvItemAssign selectByPrimaryKey(Long assignId);

    int updateByPrimaryKeySelective(InvItemAssign record);

    int updateByPrimaryKey(InvItemAssign record);

    List<InvItemAssign> getItemAssignsByItemId(@Param("itemId") Long itemId);
    
    Integer queryOrgIncludeItems(Map<String, Object> params); 
    
    Integer queryIncludeItemOrgs(Map<String, Object> params);
    

}