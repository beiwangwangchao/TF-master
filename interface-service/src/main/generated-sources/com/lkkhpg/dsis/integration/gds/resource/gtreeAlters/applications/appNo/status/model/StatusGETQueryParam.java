
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model;


public class StatusGETQueryParam {

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
    public StatusGETQueryParam(String orgCode) {
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
