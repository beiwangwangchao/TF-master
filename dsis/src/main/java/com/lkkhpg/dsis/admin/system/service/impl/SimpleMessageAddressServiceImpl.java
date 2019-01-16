/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.platform.dto.system.MessageAddress;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.service.IMessageAddressService;

/**
 * @author shiliyan
 *
 */
@Service
public class SimpleMessageAddressServiceImpl implements IMessageAddressService {


    @Override
    public List<String> queryMessageAddress(MessageTypeEnum msgType, MessageAddress address) {

        // phone://12321432151
        // mail://a@abc.com
        String s = address.getAddress();
        List<String> r = new ArrayList<String>();
        r.add(s);
        return r;
    }

}
