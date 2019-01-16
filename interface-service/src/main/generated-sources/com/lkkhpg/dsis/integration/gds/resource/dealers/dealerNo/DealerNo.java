
package com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTHeader;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.SponsorVerify;

public class DealerNo {

    private String _baseUrl;
    private Client client;
    public final SponsorVerify sponsorVerify;

    public DealerNo(String baseUrl, Client client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        this.client = client;
        sponsorVerify = new SponsorVerify(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (saveDealer) 即時會員資料保存 - 當POS系統成功新增會員後，把會員資料即時上傳到GDS系統。
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTResponse put(DealerNoPUTBody body, DealerNoPUTQueryParam queryParameters, DealerNoPUTHeader headers) {
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
        Response response = invocationBuilder.put(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTResponse.class);
    }

}
