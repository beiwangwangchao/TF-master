
package com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model;


public class DealerNoPUTQueryParam {

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
    public DealerNoPUTQueryParam(String orgCode) {
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
