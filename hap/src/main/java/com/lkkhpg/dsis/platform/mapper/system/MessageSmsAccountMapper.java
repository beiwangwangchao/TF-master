/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageSmsAccountVo;


/**
 * @author Clerifen Li
 */
public interface MessageSmsAccountMapper {
    int deleteByPrimaryKey(Long accountId);

    int insert(MessageSmsAccount record);

    int insertSelective(MessageSmsAccount record);

    MessageSmsAccount selectByPrimaryKey(Long accountId);
    
    List<MessageSmsAccountVo> selectMessageSmsAccounts(MessageSmsAccount record);

    List<MessageSmsAccountVo> selectMessageSmsAccountPassword(MessageSmsAccount record);

    int updateByPrimaryKeySelective(MessageSmsAccount record);

    int updateByPrimaryKey(MessageSmsAccount record);

    MessageSmsAccount selectByAccountCode(String accountCode);

    MessageSmsAccount selectByMarketId(Long marketId);
    
    Integer querySmsQuanties();
    
    List<MessageSmsAccount> getSmsByCodeOrMarketId(@Param("accountId") Long accountId,
            @Param("accountCode") String accountCode, @Param("marketId") Long marketId);
}