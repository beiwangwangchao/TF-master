
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model
        .AuditPOSTQueryParam;

public class Audit {

    private String _baseUrl;
    private Client client;

    public Audit(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/audit");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (saveGdealerChgOrgAppAudit) 保存其他市場轉入本市場（目標市場）申請的審核結果
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTResponse post(AuditPOSTBody body, AuditPOSTQueryParam queryParameters, AuditPOSTHeader headers) {
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
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTResponse.class);
    }

}
