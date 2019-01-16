
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.Status;

public class AppNo {

    private String _baseUrl;
    private Client client;
    public final Status status;

    public AppNo(String baseUrl, Client client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        this.client = client;
        status = new Status(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
