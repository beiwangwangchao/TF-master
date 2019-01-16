/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 功能资源DTO.
 * 
 * @author wuyichu
 */
public class FunctionResource extends BaseDTO {

    private static final long serialVersionUID = 2205839053452054599L;

    private Long funcSrcId;

    private Long functionId;

    private Long resourceId;

    public Long getFuncSrcId() {
        return funcSrcId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setFuncSrcId(Long funcSrcId) {
        this.funcSrcId = funcSrcId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}