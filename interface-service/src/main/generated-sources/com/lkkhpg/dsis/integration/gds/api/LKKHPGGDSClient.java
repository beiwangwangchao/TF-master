
package com.lkkhpg.dsis.integration.gds.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.lkkhpg.dsis.integration.gds.resource.checkResults.CheckResults;
import com.lkkhpg.dsis.integration.gds.resource.dealers.Dealers;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.GtreeAlters;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.MarketChanges;
import com.lkkhpg.dsis.integration.gds.resource.orders.Orders;
import com.lkkhpg.dsis.integration.gds.resource.orgList.OrgList;
import com.lkkhpg.dsis.integration.gds.resource.periods.Periods;

public class LKKHPGGDSClient {

    private String _baseUrl;
    public final CheckResults checkResults;
    public final Periods periods;
    public final Dealers dealers;
    public final GtreeAlters gtreeAlters;
    public final Orders orders;
    public final OrgList orgList;
    public final MarketChanges marketChanges;

    public LKKHPGGDSClient(String baseUrl) {
        _baseUrl = baseUrl;
        checkResults = new CheckResults(getBaseUri(), getClient());
        periods = new Periods(getBaseUri(), getClient());
        dealers = new Dealers(getBaseUri(), getClient());
        gtreeAlters = new GtreeAlters(getBaseUri(), getClient());
        orders = new Orders(getBaseUri(), getClient());
        orgList = new OrgList(getBaseUri(), getClient());
        marketChanges = new MarketChanges(getBaseUri(), getClient());
    }

    public LKKHPGGDSClient() {
        this("http://mocksvc.mulesoft.com/mocks/4249d80a-8fce-48fb-bd2f-b25260bc7358/api");
    }

    private Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static LKKHPGGDSClient create(String baseUrl) {
        return new LKKHPGGDSClient(baseUrl);
    }

    public static LKKHPGGDSClient create() {
        return new LKKHPGGDSClient();
    }

}
