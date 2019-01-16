/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.StringUtil;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeInApprove;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeList;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeInApproveMapper;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeListMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.IIsgMarketChangeListService;
import com.lkkhpg.dsis.admin.member.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemMarketChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model.AuditPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.appID.audit.model
        .AuditPOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 市场列表Service.
 * 
 * @author yuchuan.zeng@hand-china.com
 */
@Service
@Transactional
public class IsgMarketChangeListServiceImpl implements IIsgMarketChangeListService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IsgMarketChangeListMapper isgMarketChangeListMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private MemMarketChangeMapper memMarketChangMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IsgMarketChangeInApproveMapper isgMarketChangeInApproveMapper;

    @Autowired
    private ICodeService codeService;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public List<IsgMarketChangeList> findGdealerChgOrgAppListService(IRequest request, String subOrg) {
        List<IsgMarketChangeList> isgMarketChangeLists = new ArrayList<IsgMarketChangeList>();
        List<ApplicationsGETResponse> applicationsGETResponses = new ArrayList<ApplicationsGETResponse>();
        try {
            String currentOrgCode = gdsUtilService.getCurrentOrgCode(request);
            String gdsOrgCode = gdsUtilService.getGdsOrgCode(request, currentOrgCode);
            applicationsGETResponses = gdsService.findGdealerChgOrgAppList("", gdsOrgCode);
            for (ApplicationsGETResponse a : applicationsGETResponses) {
                IsgMarketChangeList isgMarketChangeList = new IsgMarketChangeList();
                isgMarketChangeList.setSubOrg(subOrg);
                isgMarketChangeList.setGdsId(a.getId());
                isgMarketChangeList.setAppAuditedBy(a.getAppAuditedBy());
                isgMarketChangeList.setAppAuditedMemo(a.getAppAuditedMemo());
                isgMarketChangeList.setAppAuditedTime(a.getAppAuditedTime());
                isgMarketChangeList.setAppCertificateNationCode(a.getAppCertificateNationCode());
                isgMarketChangeList.setAppCertificateNo(a.getAppCertificateNo());
                isgMarketChangeList.setAppCertificateType(a.getAppCertificateType());
                isgMarketChangeList.setAppCheckedBy(a.getAppCheckedBy());
                isgMarketChangeList.setAppCheckedMemo(a.getAppCheckedMemo());
                isgMarketChangeList.setAppCheckedTime(a.getAppCheckedTime());
                isgMarketChangeList.setAppDate(a.getAppDate());
                isgMarketChangeList.setAppDocNo(a.getAppDocNo());
                isgMarketChangeList.setAppEntryBy(a.getAppEntryBy());
                isgMarketChangeList.setAppEntryTime(a.getAppEntryTime());
                isgMarketChangeList.setAppFirstName(a.getAppFirstName());
                isgMarketChangeList.setAppFullName(a.getAppFullName());
                isgMarketChangeList.setAppLastName(a.getAppLastName());
                isgMarketChangeList.setComments(a.getComments());
                isgMarketChangeList.setCreateby(a.getCreateBy());
                isgMarketChangeList.setCreateTime(a.getCreateTime());
                isgMarketChangeList.setDealerNo(a.getDealerNo());
                isgMarketChangeList.setGdsTranBy(a.getGdsTranBy());
                isgMarketChangeList.setGdsTranEffectivePeriod(a.getGdsTranEffectivePeriod());
                isgMarketChangeList.setGdsTranMemo(a.getGdsTranMemo());
                isgMarketChangeList.setGdsTranSoPeriod(a.getGdsTranSoPeriod());
                String gdsTranStatus = a.getGdsTranStatus();
                if ("0".equals(gdsTranStatus)) {
                    isgMarketChangeList.setGdsTranStatus(IntegrationConstant.NOT_APPROVAL);
                }
                isgMarketChangeList.setGdsTranTime(a.getGdsTranTime());
                isgMarketChangeList.setLastUpdateby(a.getLastUpdateBy());
                isgMarketChangeList.setLastUpdateTime(a.getLastUpdateTime());
                isgMarketChangeList.setNewDealerSubType(a.getNewDealerSubType());
                isgMarketChangeList.setNewDealerType(a.getNewDealerType());
                isgMarketChangeList.setNewOrgTranBy(a.getNewOrgTranBy());
                isgMarketChangeList.setNewOrgTranFlag(a.getNewOrgTranFlag());
                isgMarketChangeList.setNewOrgTranMemo(a.getNewOrgTranMemo());
                isgMarketChangeList.setNewOrgTranTime(a.getNewOrgTranTime());
                isgMarketChangeList.setNewSaleBranchNo(a.getNewSaleBranchNo());
                isgMarketChangeList.setNewSaleOrgCode(a.getNewSaleOrgCode());
                isgMarketChangeList.setNewSaleZoneNo(a.getNewSaleZoneNo());
                isgMarketChangeList.setOldOrgTranBy(a.getOldOrgTranBy());
                isgMarketChangeList.setOldOrgTranFlag(a.getOldOrgTranFlag());
                isgMarketChangeList.setOldOrgTranMemo(a.getOldOrgTranMemo());
                isgMarketChangeList.setOldOrgTranTime(a.getOldOrgTranTime());
                isgMarketChangeList.setOldSaleOrgCode(a.getOldSaleOrgCode());
                isgMarketChangeList.setOperationType(IntegrationConstant.OUT_MARKET_CHANGE);
                isgMarketChangeList.setProcessStatus(IntegrationConstant.PROCESS_STATUS_S);
                isgMarketChangeList.setProcessMessage("Success");
                // isgMarketChangeList.setProcessDate(new Date());
                // CURRENT_TIMESTAMP
                isgMarketChangeList.setProgramId(-1L);
                isgMarketChangeList.setRequestId(-1L);
                isgMarketChangeLists.add(isgMarketChangeList);
            }
        } catch (Exception e) {
            IsgMarketChangeList isgMarketChangeList = new IsgMarketChangeList();
            isgMarketChangeList.setOperationType(IntegrationConstant.OUT_MARKET_CHANGE);
            isgMarketChangeList.setProcessStatus(IntegrationConstant.PROCESS_STATUS_E);
            isgMarketChangeList.setProcessMessage(e.getMessage());
            isgMarketChangeLists.add(isgMarketChangeList);
            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        } finally {
            for (IsgMarketChangeList mc : isgMarketChangeLists) {
                self().insertIsgMarketChangeListInterface(mc);
            }
        }
        return isgMarketChangeLists;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveGdealerChgOrgAppAudit(IRequest request, MemMarketChange marketChange)
            throws IntegrationException {
        AuditPOSTBody body = new AuditPOSTBody();
        AuditPOSTResponse auditPOSTResponse = null;
        IsgMarketChangeInApprove isgMarketChangeInApprove = new IsgMarketChangeInApprove();
        try {
            Member member = memberMapper.selectMembersByMemberCode(marketChange.getMemberCode());
            if (null == member) {
                //也可能是大陆市场待转过来但系统不存在的会员，从获取转入待审批接口交互表里取该会员信息
                member = new Member();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("gdsId", marketChange.getGdsId());
                params.put("dealerNo", marketChange.getMemberCode());
                List<IsgMarketChangeList> isgMarketChangeList = isgMarketChangeListMapper.selectByGdsIdAndMemberCode(params);
                if(null == isgMarketChangeList || isgMarketChangeList.size() == 0){
                    throw new IntegrationException("can not get record from table isgMarketChangeList by dealerNo and gdsId", null);
                }
                member.setMemberCode(isgMarketChangeList.get(0).getDealerNo());
                member.setMemberType(isgMarketChangeList.get(0).getAppCertificateType());
                member.setChineseName(isgMarketChangeList.get(0).getAppFullName());
                member.setChineseFirstName(isgMarketChangeList.get(0).getAppFirstName());
                member.setChineseLastName(isgMarketChangeList.get(0).getAppLastName());
                member.setIdNumber(isgMarketChangeList.get(0).getAppCertificateNo());
                //throw new IntegrationException(IntegrationException.MEMBER_NOT_EXISTS, null);
            }
            String certificateType = codeService.getCodeDescByValue(request, "MM.MEMBER_TYPE", member.getMemberType());
            if(null == member.getMemberId() && null == certificateType){
                certificateType = member.getMemberType();
            }
            body.setDealerNo(marketChange.getMemberCode());
            body.setOldSaleOrgCode(marketChange.getFromMarketName());
            body.setNewSaleOrgCode(marketChange.getToMarketName());
            body.setNewDealerType("");
            body.setNewDealerSubType("");
            body.setAppDate("");
            body.setAppDocNo("");
            body.setAppEntryTime("");
            body.setAppEntryBy("");
            body.setAppCheckedTime("");
            body.setAppCheckedBy("");
            body.setAppCheckedMemo("");
            body.setAppAuditedTime("");
            body.setAppAuditedBy(request.getAccountId().toString());
            body.setAppAuditedMemo("");
            body.setGdsTranStatus(marketChange.getApprovalStatus());
            /*
             * if (IntegrationConstant.APPROVAL_STATUS_S.equals(marketChange.
             * getApprovalStatus())) { body.setGdsTranStatus("2"); } else if
             * (IntegrationConstant.APPROVAL_STATUS_F.equals(marketChange.
             * getApprovalStatus())) { body.setGdsTranStatus("9"); }
             */
            body.setGdsTranSoPeriod("");
            body.setGdsTranEffectivePeriod("");
            body.setGdsTranTime("");
            body.setGdsTranBy("");
            body.setGdsTranMemo("");
            body.setOldOrgTranFlag("");
            body.setOldOrgTranTime("");
            body.setOldOrgTranBy("");
            body.setOldOrgTranMemo("");
            body.setNewOrgTranFlag("");
            body.setNewOrgTranTime("");
            body.setNewOrgTranBy("");
            body.setNewOrgTranMemo("");
            body.setAppFullName((null == member.getChineseName() ? member.getEnglishName() : member.getChineseName()));
            body.setAppFirstName((null == member.getChineseFirstName() ? member.getEnglishFirstName()
                    : member.getChineseFirstName()));
            body.setAppLastName(
                    (null == member.getChineseLastName() ? member.getEnglishLastName() : member.getChineseLastName()));
            body.setAppCertificateNationCode("");
            body.setAppCertificateType(certificateType);
            body.setAppCertificateNo(member.getIdNumber());
            body.setNewSaleBranchNo("");
            body.setNewSaleZoneNo("");
            body.setComments("");
            body.setId(marketChange.getGdsId());

            isgMarketChangeInApprove.setGdsId(body.getId());
            isgMarketChangeInApprove.setAppAuditedBy(body.getAppAuditedBy());
            isgMarketChangeInApprove.setAppAuditedMemo(body.getAppAuditedMemo());
            isgMarketChangeInApprove.setAppCertificateNo(body.getAppCertificateNo());
            isgMarketChangeInApprove.setAppCertificateType(body.getAppCertificateType());
            isgMarketChangeInApprove.setGdsTranStatus(body.getGdsTranStatus());
            isgMarketChangeInApprove.setProcessDate(marketChange.getApprovalDate());

            String orgCode = gdsUtilService.getCurrentOrgCode(request);
            orgCode = gdsUtilService.getGdsOrgCode(orgCode);
            auditPOSTResponse = gdsService.saveGdealerChgOrgAppAudit(gdsUtilService.getAppNo(request), orgCode, body);

            //pos系统存在的会员才把审批结果保存进市场变更记录
            if(null != member.getMemberId()){
             // 设置字段gdsId
                Long gdsId = auditPOSTResponse.getId();
                marketChange.setChangeId(gdsId);

                // 设置必输字段approvalStatus
                String status = "";
                if (IntegrationConstant.MARKET_CHANGE_PASS.equals(marketChange.getApprovalStatus())) {
                    status = MemberConstants.SYS_REVIEW_STATUS_YES;
                } else if (IntegrationConstant.MARKET_CHANGE_NO_PASS.equals(marketChange.getApprovalStatus())) {
                    status = MemberConstants.SYS_REVIEW_STATUS_NO;
                } else {
                    throw new IntegrationException(IntegrationException.AUDIT_CODE_NOT_FOUND, null);
                }
                marketChange.setApprovalStatus(status);

                // 设置必输字段memberId
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("memberCode", marketChange.getMemberCode());
                map.put("status", MemberConstants.MEMBER_STATUS_ACTIVE);
          
                Long memberId = member.getMemberId();
                marketChange.setMemberId(memberId);

                // 设置必输字段marketId
                map.put("enabledFlag", IntegrationConstant.FLAG_Y);
                Long fromMarketId =  member.getMarketId();
                Long toMarketId = gdsUtilService.getMarketId(marketChange.getToMarketName());
                if (null == fromMarketId || null == toMarketId) {
                    throw new IntegrationException(IntegrationException.MARKET_CODE_NOT_FOUND, null);
                }
                marketChange.setFromMarketId(fromMarketId);
                marketChange.setToMarketId(toMarketId);

                // 入库市场变更表
                memMarketChangMapper.insertSelective(marketChange);
            }
            
            isgMarketChangeInApprove.setProcessStatus(IntegrationConstant.ISG_MARKET_CHANGE_PROCESS_SUCCESS);
            isgMarketChangeInApprove.setProcessMessage("save memMarketChang success");
            return true;
        } catch (Exception e) {
            isgMarketChangeInApprove.setProcessStatus(IntegrationConstant.ISG_MARKET_CHANGE_PROCESS_ERROR);
            isgMarketChangeInApprove.setProcessMessage(e.getMessage());
            if (logger.isErrorEnabled()) {
                logger.error("调用 gds saveGdealerChgOrgAppAudit接口失败 ====> " + e.getMessage(), e);
            }

            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }

            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_SAVE_GDEALER_CHG_ORG_APP_AUDIT,
                    new Object[] { e.getMessage() });
        } finally {
            self().insertIsgMarketChangeInApproveInterface(isgMarketChangeInApprove);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertIsgMarketChangeListInterface(IsgMarketChangeList mc) {
        isgMarketChangeListMapper.insertSelective(mc);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertIsgMarketChangeInApproveInterface(IsgMarketChangeInApprove isgMarketChangeInApprove) {
        isgMarketChangeInApproveMapper.insert(isgMarketChangeInApprove);
    }

}
