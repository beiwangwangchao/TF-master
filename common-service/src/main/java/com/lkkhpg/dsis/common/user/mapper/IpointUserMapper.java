/*
 *
 */
package com.lkkhpg.dsis.common.user.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.user.dto.IpointUser;

/**
 * ipoint接口.
 * 
 * @author runbai.chen
 */
public interface IpointUserMapper {

    List<IpointUser> selectIpointUsers(IpointUser ipointUser);
    
    List<IpointUser> checkIpointUsers(IpointUser ipointUser);
    
    Integer queryCountByEmail(String email);
    
    Integer queryCountByPhone(IpointUser ipointUser);
}
