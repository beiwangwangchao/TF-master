/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETResponse;

/**
 * Auto Generated Code.
 * 
 * @author shengyang.zhou@hand-china.com
 *
 *
 */
public interface IGdsServiceDataProvider {

    /**
     * 日/月 差異結果查詢.
     * <p>
     * uri:/checkResults <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processQueryCheckResultResponse(String adviseNo, String orgCode, List<CheckResultsGETResponse> response,
            Exception exception);

    /**
     * 小批量同步會員資料.
     * <p>
     * uri:/dealers/batchSave <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    List<com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTBody> prepareBatchSaveDealer(
            String adviseNo, String orgCode);

    /**
     * 小批量同步會員資料.
     * <p>
     * uri:/dealers/batchSave <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param requestData
     *            发送的数据
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processBatchSaveDealerResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTResponse> response,
            Exception exception);

    /**
     * 刪除會員資料.
     * <p>
     * uri:/dealers/batchDelete <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    List<BatchDeletePOSTBody> prepareDeleteDealer(String adviseNo, String orgCode);

    /**
     * 刪除會員資料.
     * <p>
     * uri:/dealers/batchDelete <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param requestData
     *            发送的数据
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processDeleteDealerResponse(String adviseNo, String orgCode, List<BatchDeletePOSTBody> requestData,
            List<BatchDeletePOSTResponse> response, Exception exception);

    /**
     * 小批量下載海外推薦人資料.
     * <p>
     * uri:/dealers/foreignSponsors <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processFindForeignSponsorsResponse(String adviseNo, String orgCode, List<ForeignSponsorsGETResponse> response,
            Exception exception);

    /**
     * 下載移線會員資料.
     * <p>
     * uri:/gtreeAlters/moveLine/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processFindMoveSponsorResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.results.model.ResultsGETResponse> response,
            Exception exception);

    /**
     * 下載批刪會員資料.
     * <p>
     * uri:/gtreeAlters/terminate/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processFindExpelDealerResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse> response,
            Exception exception);

    /**
     * 小批量同步銷售資料.
     * <p>
     * uri:/orders/batchSave <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     *
     *
     */
    List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody> prepareBatchSaveSo(
            String adviseNo, String orgCode);

    /**
     * 小批量同步銷售資料.
     * <p>
     * uri:/orders/batchSave <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param requestData
     *            发送的数据
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processBatchSaveSoResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTResponse> response,
            Exception exception);

    /**
     * 下載轉出會員清單.
     * <p>
     * uri:/marketChanges/transferOut/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processFindTransOutListResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model.ResultsGETResponse> response,
            Exception exception);

    /**
     * 上傳轉出會員處理清單.
     * <p>
     * uri:/marketChanges/transferOut/notify <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param requestData
     *            发送的数据
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processTransOutResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTResponse> response,
            Exception exception);

    /**
     * 下載轉入會員清單.
     * <p>
     * uri:/marketChanges/transferIn/results <br>
     * method:GET
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processFindTransInListResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response,
            Exception exception);

    /**
     * 上傳轉入會員處理清單.
     * <p>
     * uri:/marketChanges/transferIn/notify <br>
     * method:POST
     * 
     * @param adviseNo
     *            GDS調度時傳遞的號碼
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param requestData
     *            发送的数据
     * @param response
     *            收到的数据
     * @param exception
     *            调用时发生的异常.null表示无异常
     *
     *
     */
    void processTransInResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse> response,
            Exception exception);

}
