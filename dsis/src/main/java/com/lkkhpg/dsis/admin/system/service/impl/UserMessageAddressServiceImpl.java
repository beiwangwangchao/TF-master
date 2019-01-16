/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.system.mapper.MessageAddressMapper;
import com.lkkhpg.dsis.platform.dto.system.MessageAddress;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.service.IMessageAddressService;

/**
 * @author shiliyan
 *
 */
@Service
public class UserMessageAddressServiceImpl implements IMessageAddressService {

    @Autowired
    private MessageAddressMapper messageAddressMapper;

    private boolean isNotSupportAll = true;

    private List<String> queryAllUser(MessageTypeEnum msgType) {
        List<String> r = new ArrayList<String>();

        if (isNotSupportAll) {
            return r;
        }

        List<User> selectAllUser = messageAddressMapper.selectAllUser();

        for (User user : selectAllUser) {
            switch (msgType) {
            case EMAIL:
                r.add(user.getEmail());
                break;
            case DSIS:
                r.add(user.getUserId().toString());
                break;
            case SMS:
                r.add(user.getPhoneNo());
                break;
            default:
                break;
            }
        }
        return r;
    }

    @Override
    public List<String> queryMessageAddress(MessageTypeEnum msgType, MessageAddress address) {
        String string = address.getAddress();
        if ("all".equalsIgnoreCase(string)) {
            return queryAllUser(msgType);
        } else {
            List<String> r = new ArrayList<String>();
            r.add(string);
            return r;
        }
    }

}
