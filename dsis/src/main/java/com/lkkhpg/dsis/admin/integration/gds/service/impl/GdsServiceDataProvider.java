/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.gds.service.IBatchSaveDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.IBatchSaveSOService;
import com.lkkhpg.dsis.admin.integration.gds.service.IDeleteDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.IDiffCheckService;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindExpelDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindForeignSponsorsService;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindMoveSponsorService;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindTransService;
import com.lkkhpg.dsis.admin.integration.gds.service.ITransService;
import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsServiceDataProvider;

/**
 * GDS被动调用接口的数据提供和处理类.
 * 
 * @author linyuheng
 *
 */
@Service
public class GdsServiceDataProvider implements IGdsServiceDataProvider {

    @Autowired
    private IDiffCheckService diffCheckService;

    @Autowired
    private IBatchSaveDealerService batchSaveDealerService;

    @Autowired
    private IDeleteDealerService deleteDealerService;

    @Autowired
    private IFindMoveSponsorService findMoveSponsorService;

    @Autowired
    private IFindExpelDealerService findExpelDealerService;

    @Autowired
    private IFindTransService findTransService;

    @Autowired
    private ITransService transService;

    @Autowired
    private IBatchSaveSOService batchSaveSOService;

    @Autowired
    private IFindForeignSponsorsService findForeignSponsorsService;

    @Override
    public void processQueryCheckResultResponse(String adviseNo, String orgCode, List<CheckResultsGETResponse> response,
            Exception exception) {
        diffCheckService.insertDiffCheckData(adviseNo, orgCode, response, exception);
    }

    @Override
    public List<BatchSavePOSTBody> prepareBatchSaveDealer(String adviseNo, String orgCode) {
        List<BatchSavePOSTBody> dealers = batchSaveDealerService.getDealer(adviseNo, orgCode);
        return dealers;
    }

    @Override
    public void processBatchSaveDealerResponse(String adviseNo, String orgCode, List<BatchSavePOSTBody> requestData,
            List<BatchSavePOSTResponse> response, Exception exception) {
        batchSaveDealerService.updateDealer(adviseNo, requestData, response, exception);
    }

    @Override
    public List<BatchDeletePOSTBody> prepareDeleteDealer(String adviseNo, String orgCode) {
        List<BatchDeletePOSTBody> dealers = deleteDealerService.getDealers(adviseNo);
        return dealers;
    }

    @Override
    public void processDeleteDealerResponse(String adviseNo, String orgCode, List<BatchDeletePOSTBody> requestData,
            List<BatchDeletePOSTResponse> response, Exception exception) {
        deleteDealerService.deleteDealers(adviseNo, requestData, response, exception);
    }

    @Override
    public void processFindForeignSponsorsResponse(String adviseNo, String orgCode,
            List<ForeignSponsorsGETResponse> response, Exception exception) {
        findForeignSponsorsService.insertSponsors(adviseNo, orgCode, response, exception);

    }

    @Override
    public void processFindMoveSponsorResponse(String adviseNo, String orgCode, List<ResultsGETResponse> response,
            Exception exception) {
        findMoveSponsorService.findMoveSponsor(adviseNo, orgCode, response, exception);
    }

    @Override
    public void processFindExpelDealerResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse> response,
            Exception exception) {
        findExpelDealerService.insertExpelDealer(adviseNo, orgCode, response, exception);
    }

    @Override
    public List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody> prepareBatchSaveSo(
            String adviseNo, String orgCode) {
        return batchSaveSOService.getOrders(adviseNo, orgCode);
    }

    @Override
    public void processBatchSaveSoResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTResponse> response,
            Exception exception) {
        batchSaveSOService.updateOrders(adviseNo, requestData, response, exception);
    }

    @Override
    public void processFindTransOutListResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model.ResultsGETResponse> response,
            Exception exception) {
        findTransService.findTransOutList(adviseNo, orgCode, response, exception);
    }

    @Override
    public void processTransOutResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTResponse> response,
            Exception exception) {
        transService.processTransOut(adviseNo, orgCode, requestData, response, exception);
    }

    @Override
    public void processFindTransInListResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response,
            Exception exception) {
        findTransService.findTransInList(adviseNo, orgCode, response, exception);
    }

    @Override
    public void processTransInResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse> response,
            Exception exception) {
        transService.processTransIn(adviseNo, orgCode, requestData, response, exception);
    }

}