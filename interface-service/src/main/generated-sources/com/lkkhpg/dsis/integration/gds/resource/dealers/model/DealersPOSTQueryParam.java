
package com.lkkhpg.dsis.integration.gds.resource.dealers.model;


public class DealersPOSTQueryParam {

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
    public DealersPOSTQueryParam(String orgCode) {
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
