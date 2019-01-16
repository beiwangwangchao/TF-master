/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.dto.system.MessageAddress;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;

/**
 * @author shiliyan
 *
 */
public interface IMessageAddressService {
    /**
     * 
     * 返回消息地址列表.
     * 
     * @param msgType
     *            消息类型 MessageTypeEnum
     * @return 地址列表
     */
    List<String> queryMessageAddress(MessageTypeEnum msgType,MessageAddress address);
}
