
package com.lkkhpg.dsis.integration.gds.resource.periods;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.periods.close.Close;

public class Periods {

    private String _baseUrl;
    private Client client;
    public final Close close;

    public Periods(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/periods");
        this.client = client;
        close = new Close(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
