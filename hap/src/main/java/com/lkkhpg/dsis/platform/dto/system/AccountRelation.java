/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.dto.system;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * AccountRelation.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月29日
 */
public class AccountRelation extends BaseDTO {
    
    // 默认为用户类型
    public static final String ACCOUNT_TYPE_USER = "USER";

    private static final long serialVersionUID = -6138736697964135156L;

    private Long accountId;

    private String accountType = ACCOUNT_TYPE_USER;

    private Long relPartyId;

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public Long getRelPartyId() {
        return relPartyId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountType(String accType) {
        this.accountType = accType == null ? null : accType.trim();
    }

    public void setRelPartyId(Long relPartyId) {
        this.relPartyId = relPartyId;
    }

}