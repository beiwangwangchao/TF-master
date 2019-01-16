/*
 *
 */
package com.lkkhpg.dsis.common.user.dto;

import java.util.Date;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 发送次数限制dto.
 * @author Zhao
 *
 */
public class SendRetrieve extends BaseDTO {
    private Long accountId;

    private String retrieveType;

    private Date retrieveDate;
    
    private String email;
    
    private String phoneNo;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getRetrieveType() {
        return retrieveType;
    }

    public void setRetrieveType(String retrieveType) {
        this.retrieveType = retrieveType == null ? null : retrieveType.trim();
    }

    public Date getRetrieveDate() {
        return retrieveDate;
    }

    public void setRetrieveDate(Date retrieveDate) {
        this.retrieveDate = retrieveDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
}