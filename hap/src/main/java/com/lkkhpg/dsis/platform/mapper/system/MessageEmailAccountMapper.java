/*
 *
 */
package com.lkkhpg.dsis.platform.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccount;
import com.lkkhpg.dsis.platform.dto.system.MessageEmailAccountVo;


/**
 * @author Clerifen Li
 */
public interface MessageEmailAccountMapper {
    
    int deleteByPrimaryKey(Long accountId);

    int deleteByConfigId(Long configId);
    
    int insert(MessageEmailAccount record);

    int insertSelective(MessageEmailAccount record);

    MessageEmailAccount selectByPrimaryKey(Long accountId);

    List<MessageEmailAccountVo> selectMessageEmailAccounts(MessageEmailAccount record);

    List<MessageEmailAccount> selectMessageEmailAccountPassword(MessageEmailAccount record);
    
    int updateByPrimaryKeySelective(MessageEmailAccount record);

    int updateByPrimaryKey(MessageEmailAccount record);

    MessageEmailAccount selectByMarketId(Long marketId);

    MessageEmailAccount selectByAccountCode(String accountCode);
    
    MessageEmailAccount getMsgEmailAccountByCodeAndMarketId(@Param("accountId") Long accountId, 
            @Param("accountCode") String accountCode, @Param("marketId") Long marketId);
}