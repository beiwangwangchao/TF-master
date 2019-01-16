/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.dapp.dto.IsgDappAuthCode;
import com.lkkhpg.dsis.admin.integration.dapp.mapper.IsgDappAuthCodeMapper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDappCodeService;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.message.IMessagePublisher;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Service
public class DappCodeServiceImpl implements IDappCodeService {

    @Autowired
    private IsgDappAuthCodeMapper mapper;

    @Autowired
    private IMessagePublisher messagePublisher;

    @Override
    public List<IsgDappAuthCode> selectCodes(IsgDappAuthCode code, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectList(code);
    }

    @Override
    public IsgDappAuthCode insertCode(IsgDappAuthCode code) {
        mapper.insert(code);
        messagePublisher.publish("topic:dappcode", code);
        return code;
    }

    @Override
    public IsgDappAuthCode select(IsgDappAuthCode code) {
        return mapper.selectByPrimaryKey(code.getAppCode());
    }

    @Override
    public void deleteCode(IsgDappAuthCode code) {
        mapper.deleteByPrimaryKey(code.getAppCode());
        messagePublisher.publish("topic:dappcode:delete", code);
    }

    @Override
    public IsgDappAuthCode updateCode(IsgDappAuthCode code) {
        mapper.updateByPrimaryKey(code);
        messagePublisher.publish("topic:dappcode", code);
        return code;
    }

    @Override
    public List<IsgDappAuthCode> batchUpdate(List<IsgDappAuthCode> codes) {
        for (IsgDappAuthCode code : codes) {
            if (DTOStatus.ADD.equals(code.get__status())) {
                self().insertCode(code);
            } else if (DTOStatus.UPDATE.equals(code.get__status())) {
                self().updateCode(code);
            } else if (DTOStatus.DELETE.equals(code.get__status())) {
                self().deleteCode(code);
            }
        }
        return codes;
    }
}
