/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.dto;

/**
 * GDS接口模拟器DTO.
 * 
 * @author linyuheng
 */
public class IsgEmulator {
    private String serviceName;
    private String methodName;
    private String description;
    private String requestData;
    private String response;
    private String autoResp;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAutoResp() {
        return autoResp;
    }

    public void setAutoResp(String autoResp) {
        this.autoResp = autoResp;
    }

}
