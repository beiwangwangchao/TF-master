/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 系统首选项.
 * 
 * @author zhangYang
 */
@Table(name = "SYS_PREFERENCES")
public class SysPreferences extends BaseDTO {

    private static final long serialVersionUID = 1L;
    private Long preferencesId;

    @NotNull
    private String preferences;

    @NotNull
    private Long preferencesLevel;

    @NotNull
    private String preferencesValue;

    private Long accountId;

    public Long getPreferencesId() {
        return preferencesId;
    }

    public void setPreferencesId(Long preferencesId) {
        this.preferencesId = preferencesId;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences == null ? null : preferences.trim();
    }

    public Long getPreferencesLevel() {
        return preferencesLevel;
    }

    public void setPreferencesLevel(Long preferencesLevel) {
        this.preferencesLevel = preferencesLevel;
    }

    public String getPreferencesValue() {
        return preferencesValue;
    }

    public void setPreferencesValue(String preferencesValue) {
        this.preferencesValue = preferencesValue == null ? null : preferencesValue.trim();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}