
package com.lkkhpg.dsis.integration.gds.resource.orgList.model;


public class OrgListGETQueryParam {

    private String _saleOrgCode;
    private String _languageCode;
    /**
     * 調用GDS传递的OrgCode
     * 
     */
    private String _orgCode;

    /**
     * 
     * @param orgCode
     *     調用GDS传递的OrgCode
     */
    public OrgListGETQueryParam(String saleOrgCode, String languageCode, String orgCode) {
        _saleOrgCode = saleOrgCode;
        _languageCode = languageCode;
        _orgCode = orgCode;
    }

    public void setSaleOrgCode(String saleOrgCode) {
        _saleOrgCode = saleOrgCode;
    }

    public String getSaleOrgCode() {
        return _saleOrgCode;
    }

    public void setLanguageCode(String languageCode) {
        _languageCode = languageCode;
    }

    public String getLanguageCode() {
        return _languageCode;
    }

    public void setOrgCode(String orgCode) {
        _orgCode = orgCode;
    }

    /**
     * 
     * @return
     *     調用GDS传递的OrgCode
     */
    public String getOrgCode() {
        return _orgCode;
    }

}
