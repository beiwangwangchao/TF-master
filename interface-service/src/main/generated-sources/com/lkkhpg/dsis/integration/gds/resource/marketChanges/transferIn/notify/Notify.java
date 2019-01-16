
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse;

public class Notify {

    private String _baseUrl;
    private Client client;

    public Notify(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/notify");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (transIn) 上傳轉入會員處理清單
     * 
     */
    public List<NotifyPOSTResponse> post(List<NotifyPOSTBody> body, NotifyPOSTQueryParam queryParameters, NotifyPOSTHeader headers) {
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
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(new GenericType<List<NotifyPOSTResponse>>() {


        }
        );
    }

}
