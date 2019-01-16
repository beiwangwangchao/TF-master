
package com.lkkhpg.dsis.integration.gds.resource.periods.close;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTQueryParam;

public class Close {

    private String _baseUrl;
    private Client client;

    public Close(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/close");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (closePeriod) 關閉機構月份 - 根據月份和機構代號從 PERIOD_CTL_DETAIL 查詢出月份區間控制明細資訊記錄,如果該記錄為空,則返回包含失敗資訊的 GdsReturnMessage,否則繼續設置該記錄物件的機構關閉標誌為Y,保存該記錄物件
     * 
     */
    public com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTResponse post(ClosePOSTBody body, ClosePOSTQueryParam queryParameters, ClosePOSTHeader headers) {
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
        return response.readEntity(com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTResponse.class);
    }

}
