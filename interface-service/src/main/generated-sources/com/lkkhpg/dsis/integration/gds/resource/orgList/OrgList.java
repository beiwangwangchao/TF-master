
package com.lkkhpg.dsis.integration.gds.resource.orgList;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.orgList.model.OrgListGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.orgList.model.OrgListGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.orgList.model.OrgListGETResponse;

public class OrgList {

    private String _baseUrl;
    private Client client;

    public OrgList(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/orgList");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (findSaleOrgInfoList) 獲取銷售組織列表.
     * 
     */
    public List<OrgListGETResponse> get(OrgListGETQueryParam queryParameters, OrgListGETHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
        if (queryParameters.getSaleOrgCode()!= null) {
            target = target.queryParam("saleOrgCode", queryParameters.getSaleOrgCode());
        }
        if (queryParameters.getLanguageCode()!= null) {
            target = target.queryParam("languageCode", queryParameters.getLanguageCode());
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
        return response.readEntity(new GenericType<List<OrgListGETResponse>>() {


        }
        );
    }

}
