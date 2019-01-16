/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import com.lkkhpg.dsis.platform.dto.system.AccountRelation;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月29日
 */
public interface AccountRelationMapper {

    int deleteByPrimaryKey(Long accountId);

    int insert(AccountRelation record);

    int update(AccountRelation record);
}