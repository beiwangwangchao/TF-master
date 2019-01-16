/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.integration.gds.service.IGdsServiceDataProvider;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class DummyGdsServiceDataProvider implements IGdsServiceDataProvider {

    @Autowired
    private IGdsService gdsService;

    @Override
    public void processQueryCheckResultResponse(String adviseNo, String orgCode, List<CheckResultsGETResponse> response,
            Exception exception) {

    }

    @Override
    public List<BatchSavePOSTBody> prepareBatchSaveDealer(String adviseNo, String orgCode) {
        return Arrays.asList();
    }

    @Override
    public void processBatchSaveDealerResponse(String adviseNo, String orgCode, List<BatchSavePOSTBody> requestData,
            List<BatchSavePOSTResponse> response, Exception exception) {

    }

    @Override
    public List<BatchDeletePOSTBody> prepareDeleteDealer(String adviseNo, String orgCode) {
        return Arrays.asList();
    }

    @Override
    public void processDeleteDealerResponse(String adviseNo, String orgCode, List<BatchDeletePOSTBody> requestData,
            List<BatchDeletePOSTResponse> response, Exception exception) {

    }

    @Override
    public void processFindForeignSponsorsResponse(String adviseNo, String orgCode,
            List<ForeignSponsorsGETResponse> response, Exception exception) {

    }

    @Override
    public void processFindMoveSponsorResponse(String adviseNo, String orgCode, List<ResultsGETResponse> response,
            Exception exception) {

    }

    @Override
    public void processFindExpelDealerResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse> response,
            Exception exception) {

    }

    @Override
    public List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody> prepareBatchSaveSo(
            String adviseNo, String orgCode) {
        return Arrays.asList();
    }

    @Override
    public void processBatchSaveSoResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.orders.batchSave.model.BatchSavePOSTResponse> response,
            Exception exception) {

    }

    @Override
    public void processFindTransOutListResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model.ResultsGETResponse> response,
            Exception exception) {
        if (exception == Arrays.asList()) {
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody> list = new ArrayList<>();
            for (com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model.ResultsGETResponse r : response) {
                com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody b = new com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody();
                list.add(b);
            }
            gdsService.transOut(adviseNo, orgCode, list);
        }
    }

    @Override
    public void processTransOutResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTResponse> response,
            Exception exception) {

    }

    @Override
    public void processFindTransInListResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response,
            Exception exception) {
        if (exception == Arrays.asList()) {
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> list = new ArrayList<>();
            for (com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse r : response) {
                com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody b = new com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody();
                list.add(b);
            }
            gdsService.transIn(adviseNo, orgCode, list);
        }

    }

    @Override
    public void processTransInResponse(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse> response,
            Exception exception) {

    }
}
