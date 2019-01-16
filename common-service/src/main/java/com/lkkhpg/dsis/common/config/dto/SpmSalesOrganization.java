/*
 *
 */
package com.lkkhpg.dsis.common.config.dto;

import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.annotation.MultiLanguage;
import com.lkkhpg.dsis.platform.annotation.MultiLanguageField;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * 销售区域DTO.
 * 
 * @author frank.li
 */
@MultiLanguage
@Table(name = "SPM_SALES_ORGANIZATION_B")
public class SpmSalesOrganization extends BaseDTO {
    
    @Id
    @Column(name = "SALES_ORG_ID")
    private Long salesOrgId;

    private String code;

    @MultiLanguageField
    @Column(name = "name")
    private String name;

    @MultiLanguageField
    @Column(name = "DESCRIPTION")
    private String description;

    private Long parentOrgId;

    private Long marketId;
    
    private String marketCode;
    
    private String marketName;

    private Long locationId;

    private String salesFlag;

    private String defaultFlag;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private Long objectVersionNumber;

    private Long requestId;

    private Long programId;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Long lastUpdateLogin;

    private SpmMarket market;

    private String currency;
    
    private String salesOrgType;

    private Long companyId;

    @Children
    private List<SpmSalesOrganization> children;
    
    public List<SpmSalesOrganization> getChildren() {
        return children;
    }

    public void setChildren(List<SpmSalesOrganization> children) {
        this.children = children;
    }

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long bsalesOrgId) {
        this.salesOrgId = bsalesOrgId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getSalesFlag() {
        return salesFlag;
    }

    public void setSalesFlag(String salesFlag) {
        this.salesFlag = salesFlag == null ? null : salesFlag.trim();
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag == null ? null : enabledFlag.trim();
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Long lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public SpmMarket getMarket() {
        return market;
    }

    public void setMarket(SpmMarket market) {
        this.market = market;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getSalesOrgType() {
        return salesOrgType;
    }

    public void setSalesOrgType(String salesOrgType) {
        this.salesOrgType = salesOrgType;
    }

}