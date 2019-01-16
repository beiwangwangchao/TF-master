/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.LovItem;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年2月1日
 */
public interface LovItemMapper {
    
    int deleteByPrimaryKey(Long lovItemId);

    int insert(LovItem record);

    List<LovItem> selectByLovId(Long lovId);

    int updateByPrimaryKey(LovItem record);
    
    int deleteByLovId(Long lovId);
}