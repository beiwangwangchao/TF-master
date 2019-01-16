
package com.lkkhpg.dsis.integration.gds.resource.checkResults.model;


public class CheckResultsGETHeader {

    /**
     * app_key
     * 
     */
    private String _appKey;
    /**
     * timestamp
     * 
     */
    private String _timestamp;
    /**
     * sign
     * 
     */
    private String _sign;

    /**
     * 
     * @param sign
     *     sign
     * @param appKey
     *     app_key
     * @param timestamp
     *     timestamp
     */
    public CheckResultsGETHeader(String appKey, String timestamp, String sign) {
        _appKey = appKey;
        _timestamp = timestamp;
        _sign = sign;
    }

    public void setAppKey(String appKey) {
        _appKey = appKey;
    }

    /**
     * 
     * @return
     *     app_key
     */
    public String getAppKey() {
        return _appKey;
    }

    public void setTimestamp(String timestamp) {
        _timestamp = timestamp;
    }

    /**
     * 
     * @return
     *     timestamp
     */
    public String getTimestamp() {
        return _timestamp;
    }

    public void setSign(String sign) {
        _sign = sign;
    }

    /**
     * 
     * @return
     *     sign
     */
    public String getSign() {
        return _sign;
    }

}
