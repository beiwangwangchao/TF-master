/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.DemoUser;
/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年1月26日
 */
public interface DemoUserMapper {
    
    int insert(DemoUser record);

    void delete(Long id);
    
    int update(DemoUser record);
    
    List select();
}