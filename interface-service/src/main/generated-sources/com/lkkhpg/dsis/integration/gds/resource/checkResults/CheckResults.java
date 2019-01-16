
package com.lkkhpg.dsis.integration.gds.resource.checkResults;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;

public class CheckResults {

    private String _baseUrl;
    private Client client;

    public CheckResults(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/checkResults");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (queryCheckResult) 日/月 差異結果查詢 - 在查詢異常處理結果時調用該方法,根據組織代碼從 UPL_CHECK_RESULT 中查詢出最近 3 天前 1000 條差異讀取狀態為"N"的差異結果集,並且將 UPL_CHECK_RESULT 的"讀取標誌"設為"Y"
     * 
     */
    public List<CheckResultsGETResponse> get(CheckResultsGETQueryParam queryParameters, CheckResultsGETHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
        if (queryParameters.getAdviseNo()!= null) {
            target = target.queryParam("adviseNo", queryParameters.getAdviseNo());
        }
        if (queryParameters.getOrgCode()!= null) {
            target = target.queryParam("orgCode", queryParameters.getOrgCode());
        }
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        if (headers.getAppKey()!= null) {
            invocationBuilder.header("app_key", headers.getAppKey());
        }
        if (headers.getTimestamp()!= null) {
            invocationBuilder.header("timestamp", headers.getTimestamp());
        }
        if (headers.getSign()!= null) {
            invocationBuilder.header("sign", headers.getSign());
        }
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(new GenericType<List<CheckResultsGETResponse>>() {


        }
        );
    }

}
