
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETQueryParam;

public class Status {

    private String _baseUrl;
    private Client client;

    public Status(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/status");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (applyStatusQuery) 會員移線、停權申請狀態查詢
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETResponse get(StatusGETQueryParam queryParameters, StatusGETHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
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
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETResponse.class);
    }

}
