
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.Applications;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.Notify;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.Results;

public class TransferIn {

    private String _baseUrl;
    private Client client;
    public final Results results;
    public final Notify notify;
    public final Applications applications;

    public TransferIn(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/transferIn");
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
