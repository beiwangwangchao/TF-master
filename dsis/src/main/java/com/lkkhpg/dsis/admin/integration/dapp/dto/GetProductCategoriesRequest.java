/*
 *
 */
package com.lkkhpg.dsis.admin.integration.dapp.dto;

import javax.validation.constraints.NotNull;

/**
 * 产品类别查询参数DTO.
 * 
 * @author fengwanjun
 *
 */
public class GetProductCategoriesRequest {

    @NotNull
    private String lang;
    
    @NotNull
    private String saleOrganizations;

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

}