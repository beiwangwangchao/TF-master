/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IAppEligibleSuspendService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.member.mapper.MemStatusChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTBody;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.applications.appNo.model.AppNoPUTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员申请资格中止实现类.
 * 
 * @author linyuheng
 */
@Service
public class AppEligibleSuspendServiceImpl implements IAppEligibleSuspendService {

    private final Logger logger = LoggerFactory.getLogger(AppEligibleSuspendServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private MemStatusChangeMapper memStatusChangeMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 会员申请资格中止.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void appEligibleSuspend(IRequest request, String dealerNo, String appNo, String appDocNo, String appPeriod,
            String appMemo, String orgCode) throws IntegrationException {
        AppNoPUTBody body = new AppNoPUTBody(dealerNo, appDocNo, appPeriod, appMemo);
        orgCode = gdsUtilService.getGdsOrgCode(orgCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appNo", appNo);
        map.put("memberId", memberMapper.selectMembersByMemberCode(dealerNo).getMemberId());
        try {
            AppNoPUTResponse response = gdsService.appEligibleSuspend(appNo, orgCode, body);
            if (!dealerNo.equals(response.getDealerNo())) {
                map.put("synStatus", IntegrationConstant.ERROR_STATUS);
            } else {
                map.put("synStatus", IntegrationConstant.SUCCESS_STATUS);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_APP_ELIGIBLE_SUSPEND, e);
            }

            map.put("synStatus", IntegrationConstant.ERROR_STATUS);
            map.put("synMessage", e.getMessage());
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_APP_ELIGIBLE_SUSPEND,
                    new Object[] { e.toString(), ":", e.getMessage() });
        } finally {
            self().updateInterfaceStatus(map);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateInterfaceStatus(Map<String, Object> map) {
        memStatusChangeMapper.updateSynStatusAndMsgByMemberId(map);
    }

}
