/*
 *
 */

package com.lkkhpg.dsis.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.lkkhpg.dsis.integration.gds.api.LKKHPGGDSClient;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.DealerNo;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.model.DealerNoPUTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.Status;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTBody;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTResponse;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model.NotifyPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model
        .AuditPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model.ApplicationsGETQueryParam;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.orgList.model.OrgListGETResponse;
import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.integration.gds.service.IGdsServiceDataProvider;
import com.lkkhpg.dsis.platform.message.profile.GlobalProfileListener;

/**
 * 将接口服务作为普通的 service 使用.
 * <p>
 * 这个 service 中的所有方法都是手工写的,无法自动生成.<br/>
 * 仅针对 GDS 被动调用的场景.与文档描述保持一致.
 * <p>
 * 其他主动调用的接口,也可以写到这里面.通过 service 访问接口,可以屏蔽细节,达到和使用本地 service 一致的效果.
 * <p>
 * 如果有需要指定的参数,可以不使用@Service,改成手动注册 bean
 *
 * @author shengyang.zhou@hand-china.com
 */
@Component("gdsService")
public class GdsServiceImpl implements IGdsService, InitializingBean, ApplicationContextAware, GlobalProfileListener {

    private String baseUrl;
    private String appKey = "xxx";
    private String secret = "secret";

    @Autowired(required = false)
    private IGdsServiceDataProvider dataProvider;

    private GdsServiceInternalInvoker invoker = new GdsServiceInternalInvoker();
    private transient AutowireCapableBeanFactory beanFactory;

    private Logger logger = LoggerFactory.getLogger(GdsServiceImpl.class);
    
    @Autowired
    private GdsSwitch gdsSwitch;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public IGdsServiceDataProvider getDataProvider() {
        return dataProvider;
    }

    /**
     * 被动调用接口的数据提供,及处理方法.
     */
    public void setDataProvider(IGdsServiceDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public LKKHPGGDSClient getClient() {
        return baseUrl == null ? new LKKHPGGDSClient() : new LKKHPGGDSClient(baseUrl);
    }

    @Override
    public void queryCheckResult(String adviseNo, String orgCode) {
        Object delegate = getClient().checkResults;

        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "get", arr(adviseNo, orgCode), null);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processQueryCheckResultResponse(adviseNo, orgCode, response, exception);
        }
    }

    @Override
    public ClosePOSTResponse closePeriod(String orgCode, String period) {
        if(!gdsSwitch.isSwitchFlag()){
            /*ClosePOSTResponse response = new ClosePOSTResponse();
            response.setPeriod(period);
            return response;*/
                throw new RuntimeException( "GDS is close" );
        }
        return invoker.invoke(getClient().periods.close, "post", orgCode, period);
    }

    @Override
    public DealersPOSTResponse saveDealer(String orgCode, DealersPOSTBody dealer) {
        if (gdsSwitch.isSwitchFlag()) {
            return invoker.invoke(getClient().dealers, "post", orgCode, dealer);
        } else {
            DealersPOSTResponse dealersPOSTResponse = new DealersPOSTResponse();
            return dealersPOSTResponse;
        }
    }

    @Override
    public void batchSaveDealer(String adviseNo, String orgCode) {
        Object delegate = getClient().dealers.batchSave;
        List body = dataProvider.prepareBatchSaveDealer(adviseNo, orgCode);
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processBatchSaveDealerResponse(adviseNo, orgCode, body, response, exception);
        }
    }

    @Override
    public List<BatchDeletePOSTResponse> deleteDealer(String adviseNo, String orgCode, List<BatchDeletePOSTBody> body) {
        if (gdsSwitch.isSwitchFlag()) {
            Object delegate = getClient().dealers.batchDelete;
            return invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
        } else {
            List<BatchDeletePOSTResponse> responses = new ArrayList<BatchDeletePOSTResponse>();
            return responses;
        }
    }

    @Override
    public void deleteDealer(String adviseNo, String orgCode) {
        Object delegate = getClient().dealers.batchDelete;
        List body = dataProvider.prepareDeleteDealer(adviseNo, orgCode);
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processDeleteDealerResponse(adviseNo, orgCode, body, response, exception);
        }
    }

    @Override
    public void findForeignSponsors(String adviseNo, String orgCode) {
        Object delegate = getClient().dealers.foreignSponsors;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "get", arr(adviseNo, orgCode), null);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processFindForeignSponsorsResponse(adviseNo, orgCode, response, exception);
        }
    }

    @Override
    public DealerNoPUTResponse saveDealer(String dealerNo, String orgCode, DealerNoPUTBody body) {
        DealerNo dealer = getClient().dealers.dealerNo(dealerNo);
        return invoker.invoke(dealer, "put", orgCode, body);
    }

    @Override
    public void findTransOutList(String adviseNo, String orgCode) {
        Object delegate = getClient().marketChanges.transferOut.results;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "get", arr(adviseNo, orgCode), null);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processFindTransOutListResponse(adviseNo, orgCode, response, exception);
        }
    }

    @Override
    public void transOut(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody> body) {
        Object delegate = getClient().marketChanges.transferOut.notify;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processTransOutResponse(adviseNo, orgCode, body, response, exception);
        }
    }

    @Override
    public List<ApplicationsGETResponse> findGdealerChgOrgAppList(String subOrg, String orgCode) {
        if(!gdsSwitch.isSwitchFlag()){
            return new ArrayList<ApplicationsGETResponse>();
        }
        Object delegate = getClient().marketChanges.transferOut.applications;
        return invoker.invoke(delegate, "get",
                new com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETQueryParam(
                        orgCode).withSubOrg(subOrg),
                null);
    }

    @Override
    public ApplicationsPOSTResponse saveGdealerChgOrgApp(String orgCode, ApplicationsPOSTBody body) {
        if(!gdsSwitch.isSwitchFlag()){
            ApplicationsPOSTResponse response = new ApplicationsPOSTResponse();
            response.setId("-1");
            return response;
        }
        Object delegate = getClient().marketChanges.transferOut.applications;
        return invoker.invoke(delegate, "post", orgCode, body);
    }

    @Override
    public AppIDDELETEResponse deleteGdealerChgOrgApp(String appID, String orgCode, AppIDDELETEBody body) {
        if(!gdsSwitch.isSwitchFlag()){
            return new AppIDDELETEResponse();
        }
        Object delegate = getClient().marketChanges.transferOut.applications.appID(appID);
        return invoker.invoke(delegate, "delete", orgCode, body);
    }

    @Override
    public void findTransInList(String adviseNo, String orgCode) {
        Object delegate = getClient().marketChanges.transferIn.results;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "get", arr(adviseNo, orgCode), null);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processFindTransInListResponse(adviseNo, orgCode, response, exception);
        }
    }

    @Override
    public void transIn(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> body) {
        Object delegate = getClient().marketChanges.transferIn.notify;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processTransInResponse(adviseNo, orgCode, body, response, exception);
        }
    }

    @Override
    public List findGdealerChgOrgAppAuditList(String subOrg, String orgCode) {
        if(!gdsSwitch.isSwitchFlag()){
            return new ArrayList();
        }
        Object delegate = getClient().marketChanges.transferIn.applications;
        return invoker.invoke(delegate, "get", new ApplicationsGETQueryParam(orgCode).withSubOrg(subOrg), null);
    }

    @Override
    public AuditPOSTResponse saveGdealerChgOrgAppAudit(String appID, String orgCode, AuditPOSTBody body) {
        if(!gdsSwitch.isSwitchFlag()){
            return new AuditPOSTResponse();
        }
        Object delegate = getClient().marketChanges.transferIn.applications.appID(appID).audit;
        return invoker.invoke(delegate, "post", orgCode, body);
    }

    @Override
    public StatusGETResponse applyStatusQuery(String appNo, String orgCode) {
        if (gdsSwitch.isSwitchFlag()) {
            Status status = getClient().gtreeAlters.applications.appNo(appNo).status;
            return invoker.invoke(status, "get", orgCode, null);
        } else {
            StatusGETResponse statusGETResponse = new StatusGETResponse();
            statusGETResponse.setTranStatus("S");
            statusGETResponse.setDownStatus("Y");
            return statusGETResponse;
        }
    }

    @Override
    public void batchSaveSo(String adviseNo, String orgCode) {
        Object delegate = getClient().orders.batchSave;
        List body = dataProvider.prepareBatchSaveSo(adviseNo, orgCode);
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processBatchSaveSoResponse(adviseNo, orgCode, body, response, exception);
        }
    }

    @Override
    public List<OrgListGETResponse> findSaleOrgInfoList(String saleOrgCode, String languageCode, String orgCode) {
        if (gdsSwitch.isSwitchFlag()) {
            Object delegate = getClient().orgList;
            return invoker.invoke(delegate, "get", arr(saleOrgCode, languageCode, orgCode), null);
        } else {
            List<OrgListGETResponse> responses = new ArrayList<OrgListGETResponse>();
            OrgListGETResponse orgListGETResponse = new OrgListGETResponse();
            orgListGETResponse.setCode("TPE");
            orgListGETResponse.setValue("台湾分公司");
            responses.add(orgListGETResponse);
            orgListGETResponse = new OrgListGETResponse();
            orgListGETResponse.setCode("SIN");
            orgListGETResponse.setValue("新加坡分公司");
            responses.add(orgListGETResponse);
            orgListGETResponse = new OrgListGETResponse();
            orgListGETResponse.setCode("MAS");
            orgListGETResponse.setValue("马来西亚分公司");
            responses.add(orgListGETResponse);
            orgListGETResponse = new OrgListGETResponse();
            orgListGETResponse.setCode("CHN");
            orgListGETResponse.setValue("中国分公司");
            responses.add(orgListGETResponse);
            return responses;
        }
    }

    // @Override
    // public List<BranchesGETResponse> findSaleBranchList(String saleOrgCode,
    // String languageCode, String orgCode) {
    // Object delegate = getClient().branches;
    // return invoker.invoke(delegate, "get", arr(saleOrgCode, languageCode,
    // orgCode), null);
    // }

    @Override
    public SponsorVerifyPOSTResponse sponsorVerify(String dealerNo, String orgCode) {
        if (gdsSwitch.isSwitchFlag()) {
            Object delegate = getClient().dealers.dealerNo(dealerNo).sponsorVerify;
            return invoker.invoke(delegate, "post", orgCode, null);
        } else {
            SponsorVerifyPOSTResponse sponsorVerifyPOSTResponse = new SponsorVerifyPOSTResponse();
            sponsorVerifyPOSTResponse.setDealerNo(dealerNo);
            sponsorVerifyPOSTResponse.setDealerName("测试");
            sponsorVerifyPOSTResponse.setEnglishName("TEST");
            return sponsorVerifyPOSTResponse;
        }
    }

    @Override
    public void findMoveSponsor(String adviseNo, String orgCode) {
        Object delegate = getClient().gtreeAlters.moveLine.results;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "get", arr(adviseNo, orgCode), null);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processFindMoveSponsorResponse(adviseNo, orgCode, response, exception);
        }
    }

    @Override
    public NotifyPOSTResponse treeAlterPrecess(String adviseNo, String orgCode, NotifyPOSTBody body) {
        Object delegate = getClient().gtreeAlters.moveLine.notify;
        return invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
    }

    @Override
    public AppNoPUTResponse applyMoveLine(String appNo, String orgCode, AppNoPUTBody body) {
        if (gdsSwitch.isSwitchFlag()) {
            Object delegate = getClient().gtreeAlters.moveLine.applications.appNo(appNo);
            return invoker.invoke(delegate, "put", orgCode, body);
        } else {
            AppNoPUTResponse appNoPUTResponse = new AppNoPUTResponse();
            appNoPUTResponse.setAppNo(appNo);
            return appNoPUTResponse;
        }
    }

    @Override
    public void findExpelDealer(String adviseNo, String orgCode) {
        Object delegate = getClient().gtreeAlters.terminate.results;
        List response = null;
        Exception exception = null;
        try {
            response = invoker.invoke(delegate, "get", arr(adviseNo, orgCode), null);
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            dataProvider.processFindExpelDealerResponse(adviseNo, orgCode, response, exception);
        }
    }

    @Override
    public com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.notify.model.NotifyPOSTResponse treeAlterPrecess(
            String adviseNo, String orgCode,
            com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.notify.model.NotifyPOSTBody body) {
        Object delegate = getClient().gtreeAlters.terminate.notify;
        return invoker.invoke(delegate, "post", arr(adviseNo, orgCode), body);
    }

    @Override
    public com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTResponse appEligibleSuspend(
            String appNo, String orgCode,
            com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTBody body) {
        if (gdsSwitch.isSwitchFlag()) {
            Object delegate = getClient().gtreeAlters.terminate.applications.appNo(appNo);
            return invoker.invoke(delegate, "put", orgCode, body);
        } else {
            /*com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTResponse appNoPUTResponse = new com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTResponse();
            appNoPUTResponse.setDealerNo(body.getDealerNo());
            return appNoPUTResponse;*/
            throw new RuntimeException( "GDS is close" );
        }
    }

    private String[] arr(String... args) {
        return args;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        beanFactory.autowireBean(invoker);
        updateInvoker();

    }

    void updateInvoker() {
        invoker.setAppKey(appKey);
        invoker.setSecret(secret);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        switch (profileName) {
        case P_BASE_URL:
            this.baseUrl = profileValue;
            break;
        case P_SECRET:
            this.secret = profileValue;
            break;
        case P_APP_KEY:
            this.appKey = profileValue;
            break;
        default:
            break;
        }
        updateInvoker();
        if (logger.isDebugEnabled()) {
            logger.debug("update {} --> {}", profileName, profileValue);
        }
    }

    @Override
    public List<String> getAcceptedProfiles() {
        return Arrays.asList(P_BASE_URL, P_SECRET, P_APP_KEY);
    }

    private static final String P_BASE_URL = "ISG.GDS_BASE_URL";
    private static final String P_SECRET = "ISG.GDS_SECRET";
    private static final String P_APP_KEY = "ISG.GDS_APP_KEY";
}
