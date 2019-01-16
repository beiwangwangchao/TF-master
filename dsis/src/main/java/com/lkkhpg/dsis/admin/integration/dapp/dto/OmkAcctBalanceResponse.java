/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 会员账号余额返回数据集合DTO.
 * 
 * @author zhenyang.he
 *
 */
public class OmkAcctBalanceResponse {

    private String distributorId;

    private String PPV;

    private String GPV;

    private String position;

    private String positionCode;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getPPV() {
        return PPV;
    }

    public void setPPV(String PPV) {
        this.PPV = PPV;
    }

    public String getGPV() {
        return GPV;
    }

    public void setGPV(String GPV) {
        this.GPV = GPV;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

}
