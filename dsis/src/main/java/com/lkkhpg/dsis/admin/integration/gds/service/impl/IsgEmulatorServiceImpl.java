/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgEmulator;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgEmulatorMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IIsgEmulatorService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * GDS模拟器查询实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class IsgEmulatorServiceImpl implements IIsgEmulatorService {

    @Autowired
    private IsgEmulatorMapper isgEmulatorMapper;

    @Override
    public List<IsgEmulator> selectAll(IRequest iRequest, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return isgEmulatorMapper.selectAll();
    }

    @Override
    public void updateIsgEmulator(IRequest iRequest, IsgEmulator isgEmulator) {
        isgEmulatorMapper.updateIsgEmulator(isgEmulator);
    }

}
