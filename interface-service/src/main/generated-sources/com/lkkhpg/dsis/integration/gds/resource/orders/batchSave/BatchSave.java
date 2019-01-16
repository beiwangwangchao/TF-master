
package com.lkkhpg.dsis.integration.gds.resource.orders.batchSave;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTHeader;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTResponse;

public class BatchSave {

    private String _baseUrl;
    private Client client;

    public BatchSave(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/batchSave");
        this.client = client;
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * (batchSaveSo) 小批量同步銷售資料 - 在批量同步銷售資料時調用該介面方法在每天固定時間 GDS 向 DS/POS 發出指令,通知 DS/POS 將需要未同步的銷售資料(包括新增、修改、刪除[寫刪除標 誌]的銷售單)上傳到 GDS,DS/POS 接指令後掃描未同步的銷售資料,調用 GDS 的該介面上傳資料,並根據返回的結 果更新同步標誌,如果有物理刪除的記錄,晚上健康檢查出來後,找出多出的會員記錄,DS/POS 根據 GDS 的調度指 令,從 GDS 獲取差異結果,對於 GDS 多出記錄的差異結果,DS/POS 自動檢索在本系統中是否存在該記錄,沒有則向 GDS 確認,GDS 從庫中刪除相應記錄。
     * 
     */
    public List<BatchSavePOSTResponse> post(List<BatchSavePOSTBody> body, BatchSavePOSTQueryParam queryParameters, BatchSavePOSTHeader headers) {
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
        return response.readEntity(new GenericType<List<BatchSavePOSTResponse>>() {


        }
        );
    }

}
