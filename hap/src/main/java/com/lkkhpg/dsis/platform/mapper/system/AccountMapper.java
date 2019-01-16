/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.HashMap;
import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.Account;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月28日
 */
public interface AccountMapper {

    int deleteByPrimaryKey(Long accountId);

    int insert(Account account);

    int updateByPrimaryKey(Account account);

    int updatePassword(Account account);

    Account selectByPrimaryKey(Long accountId);
    
    Account selectByLoginName(String loginName);

    List<Account> selectAccounts(Account account);
    
    int updateMemberPassword(Account account);
    
    Account selectByMember(HashMap<String, String> memberMap);

    Account selectByMemberId(Long memberId);
}