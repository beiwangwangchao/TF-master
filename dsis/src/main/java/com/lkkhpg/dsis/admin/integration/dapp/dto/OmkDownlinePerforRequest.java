/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

/**
 * 获取下线绩效列表请求DTO.
 * 
 * @author songyuanhuang
 */
public class OmkDownlinePerforRequest {
    private String distributorId;
    private String period;
    private String lang;
    private Long totalPv;

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(Long totalPv) {
        this.totalPv = totalPv;
    }

}
