/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorCallBackService;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberDelete;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberDeleteDetail;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberDeleteDetailMapper;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberDeleteMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyStatusQueryService;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindExpelDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemStatusChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.GtreeAlterDetail;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;

/**
 * 下载批删会员资料实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class FindExpelDealerServiceImpl implements IFindExpelDealerService {

    private Logger logger = LoggerFactory.getLogger(FindExpelDealerServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IsgMemberDeleteMapper isgMemberDeleteMapper;

    @Autowired
    private IsgMemberDeleteDetailMapper isgMemberDeleteDetailMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Autowired
    private IDistributorCallBackService distributorCallBackService;
    
    @Autowired
    private IApplyStatusQueryService applyStatusQueryService;
    
    @Autowired
    private MemStatusChangeMapper memStatusChangeMapper;

    @Override
    public void insertExpelDealer(String adviseNo, String orgCode, List<ResultsGETResponse> response,
            Exception exception) {
        IRequest request = RequestHelper.newEmptyRequest();
        RequestHelper.setCurrentRequest(request);
        String posOrgCode = null;
        try {
            posOrgCode = gdsUtilService.getOrgCode(orgCode);
            SpmMarket market = spmMarketMapper.selectMarketByCode(posOrgCode);
            if (market != null) {
                request.setAttribute(SystemProfileConstants.MARKET_ID, market.getMarketId());
                request.setAccountId(-1L);
            }
        } catch (IntegrationException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("adviseNo", adviseNo);
        NotifyPOSTBody body = null;
        boolean notifyFlag = false;
        for (ResultsGETResponse resultsGETResponse : response) {
            self().insertInterface(adviseNo, posOrgCode, resultsGETResponse, exception);
            map.put("alterNo", resultsGETResponse.getAlterNo());
            try {
                self().updateMemberStatus(resultsGETResponse, request, posOrgCode);
                map.put("processStatus", IntegrationConstant.SUCCESS_STATUS);
                notifyFlag = true;
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("变更编号{}更新失败,已回滚", resultsGETResponse.getAlterNo());
                }
                map.put("processMessage", e.getMessage());
                map.put("processStatus", IntegrationConstant.ERROR_STATUS);
                notifyFlag = false;
            } finally {
                self().updateInterface(map);
            }
            // 停权通知GDS
            if (notifyFlag) {
             // 取该头下的所有行都调用通知
                List<GtreeAlterDetail> details = resultsGETResponse.getGtreeAlterDetails();
                for (GtreeAlterDetail gtreeAlterDetail : details) {
                    map.put("alterNo", gtreeAlterDetail.getAlterNo());
                    body = new NotifyPOSTBody();
                    body.setAlterNo(gtreeAlterDetail.getAlterNo());
                    body.setTranDealerNo(gtreeAlterDetail.getTranDealerNo());
                    try {
                        gdsService.treeAlterPrecess(adviseNo, orgCode, body);
                        updateDetailUploadFlag(map);
                    } catch (Exception e) {
                        if (logger.isErrorEnabled()) {
                            logger.error(IntegrationException.MSG_ERROR_ISG_FIND_MOVE_SPONSOR + e.getMessage(), e);
                        }
                    }
                }
                map.put("alterNo", resultsGETResponse.getAlterNo());
                updateInterfaceUploadFlag(map);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateMemberStatus(ResultsGETResponse response, IRequest request, String posOrgCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 头上的信息不处理
        // 以下处理行信息
        List<GtreeAlterDetail> gtreeAlterDetails = response.getGtreeAlterDetails();
        for (GtreeAlterDetail gtreeAlterDetail : gtreeAlterDetails) {
            String tranDealerNo = gtreeAlterDetail.getTranDealerNo();
            Member tranDetailMember = memberMapper.selectMembersByMemberCode(tranDealerNo);
            if (IntegrationConstant.TRANMODE_DEL.equals(gtreeAlterDetail.getTranMode())) {
                String tranFromDealerType = gtreeAlterDetail.getTranToDealerType();
                if (IntegrationConstant.DEALERTYPE_DISTRIBUTOR.equals(tranFromDealerType)) {
                    map.put("memberRole", IntegrationConstant.MEMBER_ROLE_DISTRIBUTOR);
                } else if (IntegrationConstant.DEALERTYPE_SUPPORT.equals(tranFromDealerType)) {
                    map.put("memberRole", IntegrationConstant.MEMBER_ROLE_SUPPORT);
                }
                Member tranDealer = memberMapper.selectMembersByMemberCode(tranDealerNo);
                if (tranDealer == null) {
                    if (logger.isErrorEnabled()) {
                        logger.error("member:{} not exist!", tranDealerNo);
                    }
                    throw new RuntimeException();
                }
                map.put("memberCode", tranDealerNo);
                String tranPeriod = gtreeAlterDetail.getTranPeriod();
                if (StringUtils.isNoneEmpty(tranPeriod)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    Date tranPeriodDate = null;
                    try {
                        tranPeriodDate = sdf.parse(tranPeriod);
                    } catch (ParseException e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("member:{} 's tranPeriod:{} format error !", tranDealerNo,tranPeriod);
                        }
                        throw new RuntimeException();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(tranPeriodDate);
                    calendar.roll(Calendar.DATE, false);
                    tranPeriodDate = calendar.getTime();
                    map.put("terminationDate", tranPeriodDate);
                }
                memberMapper.updateStatusByMemberCode(map);
                distributorCallBackService.callbackDistributor(request, tranDetailMember,
                        IntegrationConstant.ACTION_TYPE_UPDATE);
                String appNo = memStatusChangeMapper.selectAppNoByMemberCode(tranDealerNo);
                if (appNo != null) {
                    callApplyStatusQuery(request, posOrgCode, appNo, IntegrationConstant.APPLY_TYPE_STATUSCHANGE);
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("appNo is null");
                    }
                }
            } else if (IntegrationConstant.TRANMODE_MOV.equals(gtreeAlterDetail.getTranMode())) {
                String tranToSponsorNo = gtreeAlterDetail.getTranToSponsorNo();
                Member toSponSorMember = memberMapper.selectMembersByMemberCode(tranToSponsorNo);
                if (toSponSorMember == null) {
                    map.put("sponsorNo", tranToSponsorNo);
                    map.put("sponsorId", "");
                } else {
                    map.put("sponsorNo", tranToSponsorNo);
                    map.put("sponsorId", toSponSorMember.getMemberId());
                }
                String tranFromDealerType = gtreeAlterDetail.getTranToDealerType();
                if (IntegrationConstant.DEALERTYPE_DISTRIBUTOR.equals(tranFromDealerType)) {
                    map.put("memberRole", IntegrationConstant.MEMBER_ROLE_DISTRIBUTOR);
                } else if (IntegrationConstant.DEALERTYPE_SUPPORT.equals(tranFromDealerType)) {
                    map.put("memberRole", IntegrationConstant.MEMBER_ROLE_SUPPORT);
                }
                Member tranDealer = memberMapper.selectMembersByMemberCode(tranDealerNo);
                if (tranDealer == null) {
                    if (logger.isErrorEnabled()) {
                        logger.error("member:{} not exist!", tranDealerNo);
                    }
                    throw new RuntimeException();
                }
                map.put("memberCode", tranDealerNo);
                memberMapper.updateSponsorByMemberCode(map);
                distributorCallBackService.callbackDistributor(request, tranDetailMember,
                        IntegrationConstant.ACTION_TYPE_UPDATE);
            }
        }
    }

    @Override
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateInterface(Map<String, Object> map) {
        try {
            isgMemberDeleteMapper.updateStatusAndMessage(map);
            isgMemberDeleteDetailMapper.updateStatusAndMessage(map);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("failed to update interface table" + e.getMessage(), e);
            }
        }
    }

    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    private void updateInterfaceUploadFlag(Map<String, Object> map) {
        try {
            isgMemberDeleteMapper.updateUploadFlagToY(map);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("failed to update upload flag" + e.getMessage(), e);
            }
        }
    }

    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    private void updateDetailUploadFlag(Map<String, Object> map) {
        try {
            isgMemberDeleteDetailMapper.updateUploadFlagToY(map);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("failed to update detail upload flag" + e.getMessage(), e);
            }
        }
    }
    
    @Override
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void insertInterface(String adviseNo, String orgCode, ResultsGETResponse resultsGETResponse,
            Exception exception) {
        try {
            if (exception == null) {
                IsgMemberDelete isgMemberDelete = new IsgMemberDelete();
                isgMemberDelete.setAlterNo(resultsGETResponse.getAlterNo());
                isgMemberDelete.setAlterDealerNo(resultsGETResponse.getAlterDealerNo());
                isgMemberDelete.setAlterMode(resultsGETResponse.getAlterMode());
                isgMemberDelete.setAlterPeriod(resultsGETResponse.getAlterPeriod());
                isgMemberDelete.setAlterOrgCode(resultsGETResponse.getAlterOrgCode());
                isgMemberDelete.setRefFromSponsorNo(resultsGETResponse.getRefFromSponsorNo());
                isgMemberDelete.setRefToSponsorNo(resultsGETResponse.getRefToSponsorNo());
                isgMemberDelete.setRefSoNo(resultsGETResponse.getRefSoNo());
                isgMemberDelete.setComments(resultsGETResponse.getComments());
                isgMemberDelete.setLastUpdatedTime(resultsGETResponse.getLastUpdatedTime());
                isgMemberDelete.setLastUpdatedBy(resultsGETResponse.getLastUpdatedBy());
                isgMemberDelete.setAppNo(resultsGETResponse.getAppNo());
                isgMemberDelete.setAdviseNo(adviseNo);
                isgMemberDelete.setUploadFlag(IntegrationConstant.NO);
                isgMemberDeleteMapper.insertSelective(isgMemberDelete);
                // 详细信息
                List<IsgMemberDeleteDetail> list = new ArrayList<IsgMemberDeleteDetail>();
                List<GtreeAlterDetail> gtreeAlterDetails = resultsGETResponse.getGtreeAlterDetails();
                for (GtreeAlterDetail gtreeAlterDetail : gtreeAlterDetails) {
                    IsgMemberDeleteDetail isgMemberDeleteDetail = new IsgMemberDeleteDetail();
                    isgMemberDeleteDetail.setInterfaceId(isgMemberDelete.getInterfaceId());
                    isgMemberDeleteDetail.setAlterNo(gtreeAlterDetail.getAlterNo());
                    isgMemberDeleteDetail.setTranPeriod(gtreeAlterDetail.getTranPeriod());
                    isgMemberDeleteDetail.setTranOrgCode(gtreeAlterDetail.getTranOrgCode());
                    isgMemberDeleteDetail.setTranDealerNo(gtreeAlterDetail.getTranDealerNo());
                    isgMemberDeleteDetail.setTranBase(String.valueOf(gtreeAlterDetail.getTranBase()));
                    isgMemberDeleteDetail.setTranMode(gtreeAlterDetail.getTranMode());
                    isgMemberDeleteDetail.setTranFromSponsorNo(gtreeAlterDetail.getTranFromSponsorNo());
                    isgMemberDeleteDetail.setTranFromDealerType(gtreeAlterDetail.getTranFromDealerType());
                    isgMemberDeleteDetail.setTranToSponsorNo(gtreeAlterDetail.getTranToSponsorNo());
                    isgMemberDeleteDetail.setTranToDealerType(gtreeAlterDetail.getTranToDealerType());
                    isgMemberDeleteDetail.setOrgSyn(String.valueOf(gtreeAlterDetail.getOrgSyn()));
                    isgMemberDeleteDetail.setOrgSynTime(gtreeAlterDetail.getOrgSynTime());
                    isgMemberDeleteDetail.setOrgSynBy(gtreeAlterDetail.getOrgSynBy());
                    isgMemberDeleteDetail.setGdsRecheckFlag(gtreeAlterDetail.getGdsRecheckFlag());
                    isgMemberDeleteDetail.setGdsRecheckTime(gtreeAlterDetail.getGdsRecheckTime());
                    isgMemberDeleteDetail.setGdsRecheckBy(gtreeAlterDetail.getGdsRecheckBy());
                    isgMemberDeleteDetail.setComments(gtreeAlterDetail.getComments());
                    isgMemberDeleteDetail.setLastUpdatedTime(gtreeAlterDetail.getLastUpdateTime());
                    isgMemberDeleteDetail.setLastUpdatedBy(gtreeAlterDetail.getLastUpdateBy());
                    isgMemberDeleteDetail.setAppNo(isgMemberDelete.getAppNo());
                    isgMemberDeleteDetail.setUploadFlag(IntegrationConstant.NO);
                    isgMemberDeleteDetail.setAdviseNo(adviseNo);
                    list.add(isgMemberDeleteDetail);

                    isgMemberDeleteDetailMapper.insertSelective(isgMemberDeleteDetail);
                }
                isgMemberDelete.setDetails(list);
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error(IntegrationException.MSG_ERROR_ISG_FIND_EXPEL_DEALER + exception.getMessage(),
                            exception);
                }
                IsgMemberDelete isgMemberDelete = new IsgMemberDelete();
                isgMemberDelete.setAdviseNo(adviseNo);
                isgMemberDelete.setUploadFlag(IntegrationConstant.NO);
                isgMemberDelete.setProcessMessage(exception.getMessage());
                isgMemberDelete.setProcessStatus(IntegrationConstant.ERROR_STATUS);
                isgMemberDeleteMapper.insertSelective(isgMemberDelete);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("failed to insert into interface table" + e.getMessage(), e);
            }
        }
    }
    
    public void callApplyStatusQuery(IRequest requestContext,String orgCode,String appNo,String type) {
        if (logger.isDebugEnabled()) {
            logger.debug("----call ApplyStatusQuery Interface----");
            logger.debug("orgCode:{}",orgCode);
            logger.debug("appNo:{}",appNo);
        }
        try {
            applyStatusQueryService.applyStatusQuery(requestContext, orgCode, appNo, type);
        } catch (IntegrationException e) {
            if (logger.isErrorEnabled()) {
                logger.error("appNo:{} call applyStatusQuery failed",appNo);
            }
        }
    }
}
