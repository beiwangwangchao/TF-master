/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 功能资源.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年3月2日
 */
public class FunctionService extends BaseDTO {

    private static final long serialVersionUID = 1707656797175198422L;

    private Long functionServiceId;

    private Long functionId;

    private Long serviceId;

    public Long getFunctionServiceId() {
        return functionServiceId;
    }

    public void setFunctionServiceId(Long functionServiceId) {
        this.functionServiceId = functionServiceId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}