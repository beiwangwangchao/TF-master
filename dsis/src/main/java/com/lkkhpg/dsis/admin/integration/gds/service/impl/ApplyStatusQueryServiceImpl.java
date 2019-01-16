/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IAppEligibleSuspendService;
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyMoveLineService;
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyStatusQueryService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.member.dto.MemStatusChange;
import com.lkkhpg.dsis.common.member.dto.MemUpstreamChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemStatusChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemUpstreamChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.applications.appNo.status.model.StatusGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员移线、停权状态申请查询实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class ApplyStatusQueryServiceImpl implements IApplyStatusQueryService {

    private final Logger logger = LoggerFactory.getLogger(ApplyStatusQueryServiceImpl.class);

    @Autowired
    private MemUpstreamChangeMapper memUpStreamChangeMapper;

    @Autowired
    private MemStatusChangeMapper memStatusChangeMapper;

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IAppEligibleSuspendService appEligibleSuspendService;

    @Autowired
    private IApplyMoveLineService applyMoveLineService;

    /* mclin修改 */
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    /**
     * 会员移线、停权状态申请查询.
     */
    @Override
    public void applyStatusQuery(IRequest requestContext, String orgCode, String appNo, String type)
            throws IntegrationException {
        String gdsOrgCode = gdsUtilService.getGdsOrgCode(orgCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appNo", appNo);
        StatusGETResponse response = null;

        try {
            // GDS接口
            response = gdsService.applyStatusQuery(appNo, gdsOrgCode);
            map.put("synStatus", IntegrationConstant.ERROR_STATUS);
            map.put("approvalStatus", IntegrationConstant.APPROVAL_STATUS_P);
            map.put("lastUpdatedBy", requestContext.getAccountId());
            // 若申请类型为移线状态查询
            if (IntegrationConstant.APPLY_TYPE_UPSTREAMCHANGE.equals(type)
                    && StringUtils.isNoneEmpty(response.getTranStatus())) {
                String tranStatus = response.getTranStatus();
                if (IntegrationConstant.TRANSTATUS_S.equals(tranStatus)) {
                    map.put("approvalStatus", IntegrationConstant.APPROVAL_STATUS_S);
                } else {
                    map.put("approvalStatus", IntegrationConstant.APPROVAL_STATUS_F);
                }
                map.put("synStatus", IntegrationConstant.SUCCESS_STATUS);
                map.put("approvalDate", new Date());
                map.put("lastUpdatedBy", requestContext.getAccountId());
            } else if (IntegrationConstant.APPLY_TYPE_STATUSCHANGE.equals(type)
                    && StringUtils.isNoneEmpty(response.getDownStatus())) {
                String downStatus = response.getDownStatus();
                if (IntegrationConstant.YES.equals(downStatus)) {
                    map.put("approvalStatus", IntegrationConstant.APPROVAL_STATUS_S);
                } else {
                    map.put("approvalStatus", IntegrationConstant.APPROVAL_STATUS_F);
                }
                map.put("synStatus", IntegrationConstant.SUCCESS_STATUS);
                map.put("approvalDate", new Date());
                map.put("lastUpdatedBy", requestContext.getAccountId());
            }

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            map.put("approvalStatus", IntegrationConstant.APPROVAL_STATUS_P);
            map.put("lastUpdatedBy", requestContext.getAccountId());

            if (e instanceof ESBException) {
                map.put("synMessage",
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse());
                if ("[E600]null".equals(((ESBException) e).getMessage())) {
                    map.put("synStatus", IntegrationConstant.SUCCESS_STATUS);
                    return;
                } else {
                    map.put("synStatus", IntegrationConstant.ERROR_STATUS);
                    throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE,
                            new Object[] { ((ESBException) e).getErrorCode() + " : "
                                    + ((ESBException) e).getErrorResponse() });
                }
            } else {
                map.put("synMessage", (e.toString() + ":" + e.getMessage()));
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_APPLY_STATUS_QUERY,
                        new Object[] { e.toString(), ":", e.getMessage() });
            }
        } finally {
            self().updateApproveStatusByAppNo(type, map);
        }

        // 若无返回状态则重新发起移线申请或停权申请
        if (StringUtils.isEmpty(response.getTranStatus()) && StringUtils.isEmpty(response.getDownStatus())) {
            if (IntegrationConstant.APPLY_TYPE_UPSTREAMCHANGE.equals(type)) {
                MemUpstreamChange upstreamChange = memUpStreamChangeMapper.selectByAppNo(appNo);
                Long memberId = upstreamChange.getMemberId();
                String memberCode = memberMapper.selectByPrimaryKey(memberId).getMemberCode();
                Member member2 = memberMapper.selectByPrimaryKey(upstreamChange.getToUpMemberId());
                String toUpMemberCode = null;
                if (member2 != null) {
                    toUpMemberCode = member2.getMemberCode();
                }
                String remark = upstreamChange.getRemark();
                applyMoveLineService.applyMoveLine(requestContext, memberCode, appNo, toUpMemberCode, appNo, remark,
                        orgCode);
            } else if (IntegrationConstant.APPLY_TYPE_STATUSCHANGE.equals(type)) {
                MemStatusChange statusChange = memStatusChangeMapper.selectByAppNo(appNo);
                Long memberId = statusChange.getMemberId();
                String memberCode = memberMapper.selectByPrimaryKey(memberId).getMemberCode();
                String applyDate = gdsUtilService.toPeriodString(statusChange.getApplyDate());
                String remark = statusChange.getRemark();
                appEligibleSuspendService.appEligibleSuspend(requestContext, memberCode, appNo, appNo, applyDate,
                        remark, orgCode);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateApproveStatusByAppNo(String type, Map<String, Object> map) {
        if (IntegrationConstant.APPLY_TYPE_UPSTREAMCHANGE.equals(type)) {
            memUpStreamChangeMapper.updateApproveStatusByAppNo(map);
        }
        if (IntegrationConstant.APPLY_TYPE_STATUSCHANGE.equals(type)) {
            memStatusChangeMapper.updateApproveStatusByAppNo(map);
        }
    }
}
