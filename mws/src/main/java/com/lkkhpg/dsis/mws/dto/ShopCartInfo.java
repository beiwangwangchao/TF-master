/*
 *
 */
package com.lkkhpg.dsis.mws.dto;



/**
 * MWS购物车信息dto.
 * @author gulin
 *
 */
public class ShopCartInfo {
    private String uuid;
    
    private Long memberId;
    
    private Long salesOrgId;
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }


}
