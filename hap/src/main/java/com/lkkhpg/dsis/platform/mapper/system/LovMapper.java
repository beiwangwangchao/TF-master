/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Lov;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年2月1日
 */
public interface LovMapper {

    int deleteByPrimaryKey(Long lovId);

    int insert(Lov record);

    Lov selectByPrimaryKey(Long lovId);
    
    Lov selectByCode(String code);

    int updateByPrimaryKey(Lov record);
    
    List<Lov> selectLovs(Lov lov);
    
    
}