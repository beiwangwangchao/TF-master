
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.AppID;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTQueryParam;

public class Applications {

    private String _baseUrl;
    private Client client;

    public Applications(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/applications");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (findGdealerChgOrgAppList) 獲取本市場（源市場）轉出申請的列表
     * 
     */
    public List<ApplicationsGETResponse> get(ApplicationsGETQueryParam queryParameters, ApplicationsGETHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
        if (queryParameters.getSubOrg()!= null) {
            target = target.queryParam("subOrg", queryParameters.getSubOrg());
        }
        if (queryParameters.getOrgCode()!= null) {
            target = target.queryParam("orgCode", queryParameters.getOrgCode());
        }
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
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
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(new GenericType<List<ApplicationsGETResponse>>() {


        }
        );
    }

    /**
     * (saveGdealerChgOrgApp) 保存轉出本市場（源市場）的申請
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse post(ApplicationsPOSTBody body, ApplicationsPOSTQueryParam queryParameters, ApplicationsPOSTHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
        if (queryParameters.getOrgCode()!= null) {
            target = target.queryParam("orgCode", queryParameters.getOrgCode());
        }
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        if (headers.getAppKey()!= null) {
            invocationBuilder.header("app_key", headers.getAppKey());
        }
        if (headers.getTimestamp()!= null) {
            invocationBuilder.header("timestamp", headers.getTimestamp());
        }
        if (headers.getSign()!= null) {
            invocationBuilder.header("sign", headers.getSign());
        }
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse.class);
    }

    public final AppID appID(String appID) {
        return new AppID(getBaseUri(), getClient(), appID);
    }

}
