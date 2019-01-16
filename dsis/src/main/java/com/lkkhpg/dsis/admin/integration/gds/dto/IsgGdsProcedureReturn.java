/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

/**
 * GDS接口存储过程返回信息DTO.
 * 
 * @author frank.li
 */
public class IsgGdsProcedureReturn {

    private String returnStatus;

    private String returnMessage;

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }


}