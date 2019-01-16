/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 产品列表查询参数DTO.
 * 
 * @author fengwanjun
 *
 */
public class GetProductListRequest {

    @NotNull
    private String lang;
    @NotNull
    private String saleOrganizations;
    
    /**
     * 非接口参数字段，仅用于接收saleOrganizations转换后的list.
     */
    private List<String> saleOrganizationList;

    private long cateCode;

    private String productCodes;
    
    /**
     * 非接口参数字段，仅用于接收productCodes转换后的list.
     */
    private List<String> productCodeList;
    
    private String dapp;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSaleOrganizations() {
        return saleOrganizations;
    }

    public void setSaleOrganizations(String saleOrganizations) {
        this.saleOrganizations = saleOrganizations;
    }

    public long getCateCode() {
        return cateCode;
    }

    public void setCateCode(long cateCode) {
        this.cateCode = cateCode;
    }

    public String getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(String productCodes) {
        this.productCodes = productCodes;
    }

    public List<String> getSaleOrganizationList() {
        return saleOrganizationList;
    }

    public void setSaleOrganizationList(List<String> saleOrganizationList) {
        this.saleOrganizationList = saleOrganizationList;
    }

    public List<String> getProductCodeList() {
        return productCodeList;
    }

    public void setProductCodeList(List<String> productCodeList) {
        this.productCodeList = productCodeList;
    }

    public String getDapp() {
        return dapp;
    }

    public void setDapp(String dapp) {
        this.dapp = dapp;
    }
}