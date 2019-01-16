/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgEmulator;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * GDS接口模拟器接口类.
 * 
 * @author linyuheng
 */
public interface IIsgEmulatorService extends ProxySelf<IIsgEmulatorService>{

    List<IsgEmulator> selectAll(IRequest iRequest, int page, int pagesize);

    void updateIsgEmulator(IRequest iRequest, IsgEmulator isgEmulator);
}
