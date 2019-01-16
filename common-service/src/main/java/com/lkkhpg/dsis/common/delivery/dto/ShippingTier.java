/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.delivery.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.lkkhpg.dsis.common.constant.ProductConstants;
import com.lkkhpg.dsis.platform.annotation.Children;
import com.lkkhpg.dsis.platform.dto.BaseDTO;

/**
 * 承运商.
 * 
 * @author RUNBAI.CHEN
 */
public class ShippingTier extends BaseDTO {

    private static final long serialVersionUID = 5909367656294058819L;

    private Long shippingTierId;
    @NotEmpty
    private String shippingTierCode;

    private String shippingTierName;

    private String description;

    private String enabledFlag;

    private Date startActiveDate;

    private Date endActiveDate;

    private Long salesOrgId;
    private String salesOrgName;

    private String calculationType;

    private String privilegeFlag;

    private Long companyId;

    private String globalFlag;
    
    private String dsis = ProductConstants.NO;
    
    private String agencyWeb = ProductConstants.NO;
    
    private String agencyApp = ProductConstants.NO;

    @Children
    private List<ShippingTierSeg> shippingTierSegs;

    @Children
    private List<ShippingTierDtl> shippingTierDtls;

    public List<ShippingTierSeg> getShippingTierSegs() {
        return shippingTierSegs;
    }

    public void setShippingTierSegs(List<ShippingTierSeg> shippingTierSegs) {
        this.shippingTierSegs = shippingTierSegs;
    }

    public List<ShippingTierDtl> getShippingTierDtls() {
        return shippingTierDtls;
    }

    public void setShippingTierDtls(List<ShippingTierDtl> shippingTierDtls) {
        this.shippingTierDtls = shippingTierDtls;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getGlobalFlag() {
        return "N";
    }

    public void setGlobalFlag(String globalFlag) {
        this.globalFlag = globalFlag;
    }

    public Long getShippingTierId() {
        return shippingTierId;
    }

    public void setShippingTierId(Long shippingTierId) {
        this.shippingTierId = shippingTierId;
    }

    public String getShippingTierCode() {
        return shippingTierCode;
    }

    public void setShippingTierCode(String shippingTierCode) {
        this.shippingTierCode = shippingTierCode == null ? null : shippingTierCode.trim();
    }

    public String getShippingTierName() {
        return shippingTierName;
    }

    public void setShippingTierName(String shippingTierName) {
        this.shippingTierName = shippingTierName == null ? null : shippingTierName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getEnabledFlag() {
        return "Y";
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

    public Long getSalesOrgId() {
        return salesOrgId;
    }

    public void setSalesOrgId(Long salesOrgId) {
        this.salesOrgId = salesOrgId;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType == null ? null : calculationType.trim();
    }

    public String getPrivilegeFlag() {
        return privilegeFlag;
    }

    public void setPrivilegeFlag(String privilegeFlag) {
        this.privilegeFlag = privilegeFlag == null ? null : privilegeFlag.trim();
    }

    public String getSalesOrgName() {
        return salesOrgName;
    }

    public void setSalesOrgName(String salesOrgName) {
        this.salesOrgName = salesOrgName;
    }

    public String getDsis() {
        return dsis;
    }

    public void setDsis(String dsis) {
        this.dsis = dsis;
    }

    public String getAgencyWeb() {
        return agencyWeb;
    }

    public void setAgencyWeb(String agencyWeb) {
        this.agencyWeb = agencyWeb;
    }

    public String getAgencyApp() {
        return agencyApp;
    }

    public void setAgencyApp(String agencyApp) {
        this.agencyApp = agencyApp;
    }
    
}