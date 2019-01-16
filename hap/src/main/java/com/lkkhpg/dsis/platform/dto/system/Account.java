/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import java.util.Date;

import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 账户DTO.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月28日
 */
public class Account extends BaseDTO {

    public static final String FIELD_ACCOUNT_ID = "accountId";

    public static final String FIELD_LOGIN_NAME = "loginName";

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACTIVE = "ACTV";

    public static final String ACCOUNT_TYPE_MEM = "MEM";

    public static final String ACCOUNT_TYPE_USER = "USER";

    public static final Long SYSADMIN_ACCOUNT_ID = 1L;

    @Id
    private Long accountId;

    private String firstLoginFlag;

    @NotEmpty
    private String loginName;

    private String password;

    private String status;

    private Date pwdExpiryDate;

    public Date getPwdExpiryDate() {
        return pwdExpiryDate;
    }

    public void setPwdExpiryDate(Date pwdExpiryDate) {
        this.pwdExpiryDate = pwdExpiryDate;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getFirstLoginFlag() {
        return firstLoginFlag;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setFirstLoginFlag(String firstLoginFlag) {
        this.firstLoginFlag = firstLoginFlag;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim().toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

}