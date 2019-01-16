
package com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETHeader;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETResponse;

public class ForeignSponsors {

    private String _baseUrl;
    private Client client;

    public ForeignSponsors(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/foreignSponsors");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (findForeignSponsors) 小批量下載海外推薦人資料 - 在查詢海外推薦人時調用該介面方法 從 Gdealer 查詢出所屬機構代號為參數的全部會員號集合,從中排除一般會員即為海外推薦人
     * 
     */
    public List<ForeignSponsorsGETResponse> get(ForeignSponsorsGETQueryParam queryParameters, ForeignSponsorsGETHeader headers) {
        WebTarget target = this.client.target(getBaseUri());
        if (queryParameters.getAdviseNo()!= null) {
            target = target.queryParam("adviseNo", queryParameters.getAdviseNo());
        }
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
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(new GenericType<List<ForeignSponsorsGETResponse>>() {


        }
        );
    }

}
