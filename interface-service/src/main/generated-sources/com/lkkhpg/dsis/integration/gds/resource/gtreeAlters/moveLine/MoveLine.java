
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.Applications;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.Notify;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.results.Results;

public class MoveLine {

    private String _baseUrl;
    private Client client;
    public final Results results;
    public final Notify notify;
    public final Applications applications;

    public MoveLine(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/moveLine");
        this.client = client;
        results = new Results(getBaseUri(), getClient());
        notify = new Notify(getBaseUri(), getClient());
        applications = new Applications(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
