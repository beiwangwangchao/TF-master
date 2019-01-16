/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.mapper;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgEmulator;

/**
 * GDS模拟器Mapper.
 * 
 * @author linyuheng
 */
public interface IsgEmulatorMapper {

    List<IsgEmulator> selectAll();

    void updateIsgEmulator(IsgEmulator isgEmulator);
}