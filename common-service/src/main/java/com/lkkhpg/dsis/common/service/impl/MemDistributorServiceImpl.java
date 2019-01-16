/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.member.dto.MemDistributor;
import com.lkkhpg.dsis.common.member.mapper.MemDistributorMapper;
import com.lkkhpg.dsis.common.service.IMemDistributorService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * @author runbai.chen
 */
@Service
@Transactional
public class MemDistributorServiceImpl implements IMemDistributorService {

    @Autowired
    private MemDistributorMapper mmDistributorMapper;

    @Override
    public MemDistributor saveMemDistributor(IRequest request, MemDistributor mmDistributor) {
        if (mmDistributor.getRecordId() != null) {
            mmDistributorMapper.update(mmDistributor);
        } else {
            mmDistributorMapper.insert(mmDistributor);
        }
        return mmDistributor;
    }

    @Override
    public List<MemDistributor> getMemDistributor(MemDistributor mmDistributor) {
        return mmDistributorMapper.selectByAttributes(mmDistributor);
    }

}
