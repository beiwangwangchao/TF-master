/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.lkkhpg.dsis.admin.system.exception.SystemException;
import com.lkkhpg.dsis.platform.dto.system.MessageAddress;
import com.lkkhpg.dsis.platform.mail.MessageTypeEnum;
import com.lkkhpg.dsis.platform.service.IMessageAddressService;

/**
 * @author shiliyan
 *
 */
public class MessageAddressService implements InitializingBean {

    private Map<String, IMessageAddressService> typeServices = new HashMap<String, IMessageAddressService>();

    private String address;

    public String toAddressString(MessageAddress address) {

        String type = address.getType();
        String a = address.getAddress();

        StringBuilder uri = new StringBuilder(type);
        uri = uri.append(":").append("//").append(a);

        return uri.toString();
    }

    public List<String> getMessageAddress(String address, MessageTypeEnum messageType, boolean isSystem)
            throws SystemException {
        return getMessageAddress(address, messageType);
    }

    // user role/2
    // user://3
    public List<String> getMessageAddress(String address, MessageTypeEnum messageType) throws SystemException {

        MessageAddress _address = this.toAddressObject(address);
        String type = _address.getType();
        IMessageAddressService service = getTypeServices().get(type);
        if (service == null) {
            throw new SystemException("msg.error.message.address.service.not.found", type);
        }
        List<String> queryMessageAddress = service.queryMessageAddress(messageType, _address);
        return queryMessageAddress;

    }

    // role://1
    public MessageAddress toAddressObject(String s) {

        URI uri = URI.create(s);

        MessageAddress address = new MessageAddress();
        address.setAddress(uri.getHost());
        address.setType(uri.getScheme());

        return address;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, IMessageAddressService> getTypeServices() {
        return typeServices;
    }

    public void setTypeServices(Map<String, IMessageAddressService> typeServices) {
        this.typeServices = typeServices;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // System.out.println(typeServices);

    }

}
