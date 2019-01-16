/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.service;

import com.lkkhpg.dsis.common.config.dto.SpmInvOrganization;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 获取当前组织信息的Service.
 * @author chenjingxiong
 */
public interface IOrgInfoService extends ProxySelf<IOrgInfoService> {

    /**
     * 获取当前的销售区域.
     * @param request 请求上下文.
     * @return 当前销售区域信息.
     */
    SpmSalesOrganization querySalesInfo(IRequest request);
    
    /**
     * 获取当前的库存组织.
     * @param request 请求上下文.
     * @return 当前的库存组织信息.
     */
    SpmInvOrganization queryInvInfo(IRequest request);
}
