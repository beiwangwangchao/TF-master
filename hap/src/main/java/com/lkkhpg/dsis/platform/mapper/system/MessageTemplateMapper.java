/*
 *
 */

package com.lkkhpg.dsis.platform.mapper.system;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.platform.dto.system.MessageTemplate;

public interface MessageTemplateMapper {
    int deleteByPrimaryKey(BigDecimal templateId);

    int insert(MessageTemplate record);

    int insertSelective(MessageTemplate record);

    MessageTemplate selectByPrimaryKey(BigDecimal templateId);
    
    MessageTemplate selectByCode(String templateCode);

    List<MessageTemplate> selectMessageTemplates(MessageTemplate record);
    
    int updateByPrimaryKeySelective(MessageTemplate record);

    int updateByPrimaryKeyWithBLOBs(MessageTemplate record);

    int updateByPrimaryKey(MessageTemplate record);
    
    MessageTemplate getMsgTemByCodeAndMarketId(@Param("templateId") BigDecimal templateId, 
            @Param("templateCode") String templateCode, @Param("marketId") Long marketId);
}