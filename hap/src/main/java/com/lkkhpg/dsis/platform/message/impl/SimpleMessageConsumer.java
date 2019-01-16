/*
 *
 */

package com.lkkhpg.dsis.platform.message.impl;

import com.lkkhpg.dsis.platform.message.IMessageConsumer;

/**
 * 用于测试.
 * <p>
 * 直接打印收到的消息
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class SimpleMessageConsumer implements IMessageConsumer<String> {
    @Override
    public void onMessage(String message, String pattern) {

    }
}
