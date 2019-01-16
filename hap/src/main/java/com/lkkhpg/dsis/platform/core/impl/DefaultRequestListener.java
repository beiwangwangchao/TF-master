/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.platform.core.impl;

import javax.servlet.http.HttpServletRequest;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.IRequestListener;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class DefaultRequestListener implements IRequestListener {
    
    @Override
    public IRequest newInstance() {
        return new ServiceRequest();
    }

    @Override
    public void afterInitialize(HttpServletRequest httpServletRequest, IRequest request) {

    }
}
