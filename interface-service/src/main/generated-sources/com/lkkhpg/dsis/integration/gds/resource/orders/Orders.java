
package com.lkkhpg.dsis.integration.gds.resource.orders;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.BatchSave;

public class Orders {

    private String _baseUrl;
    private Client client;
    public final BatchSave batchSave;

    public Orders(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/orders");
        this.client = client;
        batchSave = new BatchSave(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
