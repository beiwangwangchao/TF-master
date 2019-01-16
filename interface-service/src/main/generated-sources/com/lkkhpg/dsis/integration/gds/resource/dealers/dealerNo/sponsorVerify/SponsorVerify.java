
package com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTQueryParam;

public class SponsorVerify {

    private String _baseUrl;
    private Client client;

    public SponsorVerify(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/sponsorVerify");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (sponsorVerify) 推薦人即時鑒別 - 推薦人即時鑒別 (由 POS 系統的會員申請介面發起介面查詢請求,連接到 GDS 即時查詢推薦人資料。)
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse post(SponsorVerifyPOSTQueryParam queryParameters, SponsorVerifyPOSTHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
        if (queryParameters.getOrgCode()!= null) {
            target = target.queryParam("orgCode", queryParameters.getOrgCode());
        }
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        if (headers.getAppKey()!= null) {
            invocationBuilder.header("app_key", headers.getAppKey());
        }
        if (headers.getTimestamp()!= null) {
            invocationBuilder.header("timestamp", headers.getTimestamp());
        }
        if (headers.getSign()!= null) {
            invocationBuilder.header("sign", headers.getSign());
        }
        Response response = invocationBuilder.post(null);
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse.class);
    }

}
