
package com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.Applications;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.notify.Notify;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.Results;

public class Terminate {

    private String _baseUrl;
    private Client client;
    public final Results results;
    public final Notify notify;
    public final Applications applications;

    public Terminate(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/terminate");
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
