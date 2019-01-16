
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEHeader;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model
        .AppIDDELETEQueryParam;

public class AppID {

    private String _baseUrl;
    private Client client;

    public AppID(String baseUrl, Client client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (deleteGdealerChgOrgApp) 刪除轉出本市場（源市場）的申請
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEResponse delete(AppIDDELETEBody body, AppIDDELETEQueryParam queryParameters, AppIDDELETEHeader headers) {
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
        Response response = invocationBuilder.method("DELETE", Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEResponse.class);
    }

}
