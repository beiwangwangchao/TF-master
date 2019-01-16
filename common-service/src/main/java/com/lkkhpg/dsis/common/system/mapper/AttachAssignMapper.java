/*
 *
 */
package com.lkkhpg.dsis.common.system.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.dto.AttachAssign;

/**
 * 附件目录分配权限Mapper.
 * 
 * @author liang.rao
 *
 */
public interface AttachAssignMapper {
    
    int deleteByPrimaryKey(Long AttachAssignId);

    int insert(AttachAssign record);

    int insertSelective(AttachAssign record);

    AttachAssign selectByPrimaryKey(Long AttachAssignId);

    int updateByPrimaryKeySelective(AttachAssign record);

    int updateByPrimaryKey(Long AttachAssignId);

    List<AttachAssign> selectByRecord(AttachAssign record);
    
    List<AttachAssign> selectNotIn(AttachAssign record);
}
