
package com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;

public class BatchDelete {

    private String _baseUrl;
    private Client client;

    public BatchDelete(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/batchDelete");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (deleteDealer) 刪除會員資料 - GDS每晚HealthCheck將GDS比DS/POS多出的會員資料記錄，保存在UPL_CHECK_RESULT表中，GDS定時發起刪除批令，DS調用，GDS做刪除處理，刪除確認的會員在SGDEALER、GDEALER_PERSONAL_INFO、GDEALER_FAMILY_INFO及GDEALER_TYPE_STATUS中對應的記錄
     * 
     */
    public List<BatchDeletePOSTResponse> post(List<BatchDeletePOSTBody> body, BatchDeletePOSTQueryParam queryParameters, BatchDeletePOSTHeader headers) {
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
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            throw new WebApplicationException(response);
        }
        return response.readEntity(new GenericType<List<BatchDeletePOSTResponse>>() {


        }
        );
    }

}
