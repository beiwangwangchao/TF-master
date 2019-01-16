/*
 *
 */
package com.lkkhpg.dsis.platform.dto.system;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 邮箱配置
 * @author Clerifen Li
 */
public class MessageEmailConfig extends BaseDTO {
    
    private static final long serialVersionUID = 8742354571330468329L;

    private Long configId;

    private String configCode;

    private String host;

    private String port;

    private Long tryTimes;

    private String userName;
    
    private String password;
    
    private String description;

    private String organization;

    private String useWhiteList;
    
    @Children
    @NotNull
    private List<MessageEmailAccount> emailAccounts;
    
    @Children
    private List<MessageEmailWhiteList> whiteLists;
    
    public List<MessageEmailAccount> getEmailAccounts() {
        return emailAccounts;
    }

    public void setEmailAccounts(List<MessageEmailAccount> emailAccounts) {
        this.emailAccounts = emailAccounts;
    }

    public List<MessageEmailWhiteList> getWhiteLists() {
        return whiteLists;
    }

    public void setWhiteLists(List<MessageEmailWhiteList> whiteLists) {
        this.whiteLists = whiteLists;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Long tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUseWhiteList() {
        return useWhiteList;
    }

    public void setUseWhiteList(String useWhiteList) {
        this.useWhiteList = useWhiteList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}