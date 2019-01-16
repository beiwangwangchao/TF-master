/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

@Table(name = "ISG_DAPP_AUTH_CODE")
public class IsgDappAuthCode extends BaseDTO {

    @Id
    @Column(name = "app_code")
    @NotEmpty
    private String appCode;

    @NotEmpty
    private String appSecret;

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT)
    private Date expire;

    private String description;

    private String whiteListEnabled;

    private String whiteList;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getWhiteListEnabled() {
        return whiteListEnabled;
    }

    public void setWhiteListEnabled(String whiteListEnabled) {
        this.whiteListEnabled = whiteListEnabled == null ? null : whiteListEnabled.trim();
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList == null ? null : whiteList.trim();
    }
}