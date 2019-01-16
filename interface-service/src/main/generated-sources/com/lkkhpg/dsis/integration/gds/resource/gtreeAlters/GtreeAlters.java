
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.Applications;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.MoveLine;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.Terminate;

public class GtreeAlters {

    private String _baseUrl;
    private Client client;
    public final MoveLine moveLine;
    public final Terminate terminate;
    public final Applications applications;

    public GtreeAlters(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/gtreeAlters");
        this.client = client;
        moveLine = new MoveLine(getBaseUri(), getClient());
        terminate = new Terminate(getBaseUri(), getClient());
        applications = new Applications(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
