
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model;


public class ApplicationsGETQueryParam {

    private String _subOrg;
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
    public ApplicationsGETQueryParam(String orgCode) {
        _orgCode = orgCode;
    }

    public ApplicationsGETQueryParam withSubOrg(String subOrg) {
        _subOrg = subOrg;
        return this;
    }

    public void setSubOrg(String subOrg) {
        _subOrg = subOrg;
    }

    public String getSubOrg() {
        return _subOrg;
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
