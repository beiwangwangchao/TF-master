
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model;


public class AppNoPUTQueryParam {

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
    public AppNoPUTQueryParam(String orgCode) {
        _orgCode = orgCode;
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
