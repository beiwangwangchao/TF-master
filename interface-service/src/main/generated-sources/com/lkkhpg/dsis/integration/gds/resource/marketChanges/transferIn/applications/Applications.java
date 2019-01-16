
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.AppID;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model.ApplicationsGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model.ApplicationsGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model.ApplicationsGETResponse;

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
     * (findGdealerChgOrgAppAuditList) 獲取其他市場轉入本市場（目標市場）申請的待審批列表
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
        return response.readEntity(new GenericType<List<ApplicationsGETResponse>>() {


        }
        );
    }

    public final AppID appID(String appID) {
        return new AppID(getBaseUri(), getClient(), appID);
    }

}
