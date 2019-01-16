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
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyMoveLineService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.member.mapper.MemUpstreamChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.applications.appNo.model.AppNoPUTBody;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员移线申请实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class ApplyMoveLineServiceImpl implements IApplyMoveLineService {

    private final Logger logger = LoggerFactory.getLogger(ApplyMoveLineServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private MemUpstreamChangeMapper memUpStreamChangeMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    /**
     * 会员移线申请.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void applyMoveLine(IRequest request, String dealerNo, String appNo, String sponsorNo, String appDocNo,
            String appMemo, String orgCode) throws IntegrationException {
        AppNoPUTBody body = new AppNoPUTBody(dealerNo, sponsorNo, appDocNo, appMemo);
        orgCode = gdsUtilService.getGdsOrgCode(orgCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appNo", appNo);
        map.put("memberId", memberMapper.selectMembersByMemberCode(dealerNo).getMemberId());
        try {
            gdsService.applyMoveLine(appNo, orgCode, body);
            map.put("synStatus", IntegrationConstant.SUCCESS_STATUS);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_MOVE_LINE, e);
            }
            map.put("synStatus", IntegrationConstant.ERROR_STATUS);
            map.put("synMessage", e.getMessage());
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }
            if (IntegrationException.MSG_ERROR_APP_NO_NOT_MATCH
                    .equals(((IntegrationException) e).getDescriptionKey())) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_APP_NO_NOT_MATCH, null);
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_MOVE_LINE,
                    new Object[] { e.toString(), ":", e.getMessage() });
        } finally {
            self().updateInterfaceStatus(map);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateInterfaceStatus(Map<String, Object> map) {
        memUpStreamChangeMapper.updateSynStatusAndMsgByMemberId(map);
    }

}
