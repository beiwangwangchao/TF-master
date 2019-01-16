/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 通过ID获取业务请求DTO.
 * 
 * @author linyuheng
 */
public class GetDistributorRequest {

    private String distributorNumber;

    private String idNumber;

    public String getDistributorNumber() {
        return distributorNumber;
    }

    public void setDistributorNumber(String distributorNumber) {
        this.distributorNumber = distributorNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
