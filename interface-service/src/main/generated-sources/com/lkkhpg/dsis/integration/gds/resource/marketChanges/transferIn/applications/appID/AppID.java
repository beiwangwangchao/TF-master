
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.Audit;

public class AppID {

    private String _baseUrl;
    private Client client;
    public final Audit audit;

    public AppID(String baseUrl, Client client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        this.client = client;
        audit = new Audit(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
