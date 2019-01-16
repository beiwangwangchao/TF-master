
package com.lkkhpg.dsis.integration.gds.resource.dealers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.BatchDelete;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.BatchSave;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.DealerNo;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.ForeignSponsors;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTQueryParam;

public class Dealers {

    private String _baseUrl;
    private Client client;
    public final BatchSave batchSave;
    public final BatchDelete batchDelete;
    public final ForeignSponsors foreignSponsors;

    public Dealers(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/dealers");
        this.client = client;
        batchSave = new BatchSave(getBaseUri(), getClient());
        batchDelete = new BatchDelete(getBaseUri(), getClient());
        foreignSponsors = new ForeignSponsors(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (saveDealer) 即時會員資料保存 - POS 新增會員、更改重要資料、移線、資格狀態變更(非中止資格)等操作,引起資料變化時調用該介面即時自動更新 GDS系統的會員資料庫。GDS 將DS/POS上傳的會員資料 gDealer 更新到資料表 GDEALER、 GDEALER_PERSONAL_INFO 及 GDEALER_FAMILY_INFO 中,同時判斷身份狀態是否有改變,有改變則根據規則生 成 GDEALER_TYPE_STATUS 記錄。如果資料變化是從非普通消費者變到普通消費者(如資格中止),則通過資格中 止介面處理。
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTResponse post(DealersPOSTBody body, DealersPOSTQueryParam queryParameters, DealersPOSTHeader headers) {
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
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTResponse.class);
    }

    public final DealerNo dealerNo(String dealerNo) {
        return new DealerNo(getBaseUri(), getClient(), dealerNo);
    }

}
