
package com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.Applications;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.Notify;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.Results;

public class TransferOut {

    private String _baseUrl;
    private Client client;
    public final Results results;
    public final Notify notify;
    public final Applications applications;

    public TransferOut(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/transferOut");
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
