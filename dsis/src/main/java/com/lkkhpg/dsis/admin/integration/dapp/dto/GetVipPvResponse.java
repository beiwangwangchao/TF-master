/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.math.BigDecimal;

/**
 * 查询VIP pv响应DTO.
 * 
 * @author shenqb
 *
 */
public class GetVipPvResponse {

    private String VIPNumber;

    private BigDecimal PV;

    public String getVIPNumber() {
        return VIPNumber;
    }

    public void setVIPNumber(String vIPNumber) {
        VIPNumber = vIPNumber;
    }

    public BigDecimal getPV() {
        return PV;
    }

    public void setPV(BigDecimal pV) {
        PV = pV;
    }

}
