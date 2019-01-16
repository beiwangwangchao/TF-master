
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse;

public class Results {

    private String _baseUrl;
    private Client client;

    public Results(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/results");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (findExpelDealer) 下載批刪會員資料
     * 
     */
    public List<ResultsGETResponse> get(ResultsGETQueryParam queryParameters, ResultsGETHeader headers) {
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
        return response.readEntity(new GenericType<List<ResultsGETResponse>>() {


        }
        );
    }

}
