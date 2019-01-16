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
public class RoleMessageAddressServiceImpl implements IMessageAddressService {
    @Autowired
    private MessageAddressMapper messageAddressMapper;

    private List<String> queryUserByRolePk(String pk, MessageTypeEnum msgType) {
        List<User> selectAllUserOfRole = messageAddressMapper.selectAllUserOfRole(Long.valueOf(pk));

        List<String> r = new ArrayList<String>();

        for (User user : selectAllUserOfRole) {
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
        return queryUserByRolePk(string, msgType);
    }

}
