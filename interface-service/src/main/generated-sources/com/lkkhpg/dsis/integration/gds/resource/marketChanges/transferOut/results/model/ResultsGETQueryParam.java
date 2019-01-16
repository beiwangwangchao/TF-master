
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model;


public class ResultsGETQueryParam {

    /**
     * GDS調度時傳遞的號碼
     * 
     */
    private String _adviseNo;
    /**
     * 調用GDS传递的OrgCode
     * 
     */
    private String _orgCode;

    /**
     * 
     * @param adviseNo
     *     GDS調度時傳遞的號碼
     * @param orgCode
     *     調用GDS传递的OrgCode
     */
    public ResultsGETQueryParam(String adviseNo, String orgCode) {
        _adviseNo = adviseNo;
        _orgCode = orgCode;
    }

    public void setAdviseNo(String adviseNo) {
        _adviseNo = adviseNo;
    }

    /**
     * 
     * @return
     *     GDS調度時傳遞的號碼
     */
    public String getAdviseNo() {
        return _adviseNo;
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
