/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeDelete;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeDeleteMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IDeleteGdealerChgOrgAppService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.appID.model.AppIDDELETEResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 删除转出本市场（源市场）的申请.
 * 
 * @author shenqb
 */
@Service
@Transactional
public class DeleteGdealerChgOrgAppServiceImpl implements IDeleteGdealerChgOrgAppService {

    private final Logger log = LoggerFactory.getLogger(DeleteGdealerChgOrgAppServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IsgMarketChangeDeleteMapper isgMarketChangeDeleteMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteGdealerChgOrgApp(IRequest request, Long gdealerChgOrgCode, String orgCode)
            throws IntegrationException {
        Boolean flag = false;
        AppIDDELETEResponse response = null;
        IsgMarketChangeDelete isgMarketChangeDelete = new IsgMarketChangeDelete();
        Date date = new Date();
        isgMarketChangeDelete.setProcessDate(date);
        String appNo = gdsUtilService.getAppNo(request);
        AppIDDELETEBody body = new AppIDDELETEBody();
        body.setId(gdealerChgOrgCode);
        String gdsOrgCode = gdsUtilService.getGdsOrgCode(orgCode);
        try {
            response = gdsService.deleteGdealerChgOrgApp(appNo, gdsOrgCode, body);
            isgMarketChangeDelete.setProcessStatus(IntegrationConstant.ISG_MARKET_CHANGE_PROCESS_SUCCESS);
            flag = true;

            if (log.isDebugEnabled()) {
                log.debug("DealerNo: {0}, Message: {1}",
                        new Object[] { response.getDealerNo(), response.getMessage() });
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(IntegrationException.MSG_ERROR_ISG_DELETE_GDEALER_CHG_ORG_APP, e);
            }
            isgMarketChangeDelete.setProcessStatus(IntegrationConstant.ISG_MARKET_CHANGE_PROCESS_ERROR);
            isgMarketChangeDelete.setProcessMessage(e.toString() + ":" + e.getMessage());
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }
            return false;
        } finally {
            self().insertInterface(isgMarketChangeDelete);
        }

        return flag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertInterface(IsgMarketChangeDelete isgMarketChangeDelete) {
        isgMarketChangeDeleteMapper.insert(isgMarketChangeDelete);
    }
}
