
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTBody;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTHeader;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTQueryParam;

public class AppNo {

    private String _baseUrl;
    private Client client;

    public AppNo(String baseUrl, Client client, String uriParam) {
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
     * (applyMoveLine) 會員移線申請 - POS 系統處理會員移線資料後,即時自動更新 GDS 系統的會員資料庫。同時也須具備有批次整批傳送的功能介面, 可過濾特定會員編號起迄區間的資料再上傳
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTResponse put(AppNoPUTBody body, AppNoPUTQueryParam queryParameters, AppNoPUTHeader headers) {
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
        Response response = invocationBuilder.put(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTResponse.class);
    }

}
