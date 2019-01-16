
package com.lkkhpg.dsis.integration.gds.resource.marketChanges;

import javax.ws.rs.client.Client;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.TransferIn;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.TransferOut;

public class MarketChanges {

    private String _baseUrl;
    private Client client;
    public final TransferOut transferOut;
    public final TransferIn transferIn;

    public MarketChanges(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/marketChanges");
        this.client = client;
        transferOut = new TransferOut(getBaseUri(), getClient());
        transferIn = new TransferIn(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
