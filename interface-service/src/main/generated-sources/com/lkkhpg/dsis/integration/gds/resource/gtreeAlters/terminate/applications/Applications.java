
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.AppNo;

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

    public final AppNo appNo(String appNo) {
        return new AppNo(getBaseUri(), getClient(), appNo);
    }

}
