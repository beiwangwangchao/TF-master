/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeList;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeListMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindGdealerChgOrgAppAuditListService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.applications.model.ApplicationsGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 获取其他市场转入本市场（目标市场）申请的待审批列表.
 * 
 * @author shenqb
 */
@Service
@Transactional
public class FindGdealerChgOrgAppAuditListServiceImpl implements IFindGdealerChgOrgAppAuditListService {

    private final Logger log = LoggerFactory.getLogger(FindGdealerChgOrgAppAuditListServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IsgMarketChangeListMapper isgMarketChangeListMapper;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MemMarketChange> findGdealerChgOrgAppAuditList(IRequest request, String subOrg, String orgCode)
            throws IntegrationException {
        orgCode = gdsUtilService.getGdsOrgCode(orgCode);

        List<MemMarketChange> marketChanges = new ArrayList<MemMarketChange>();
        List<ApplicationsGETResponse> responses = new ArrayList<ApplicationsGETResponse>();
        IsgMarketChangeList isgMarketChangeList = new IsgMarketChangeList();
        isgMarketChangeList.setSubOrg(subOrg);
        Date date = new Date();
        isgMarketChangeList.setProcessDate(date);
        isgMarketChangeList.setOperationType(IntegrationConstant.ISG_MARKET_CHANGE_OPERATION_TYPE_IN);
        try {
            responses = gdsService.findGdealerChgOrgAppAuditList("", orgCode);
            if (responses.size() == 0) {
                return null;
            }
            for (ApplicationsGETResponse response : responses) {
                MemMarketChange memMarketChange = new MemMarketChange();
                memMarketChange.setGdsId(response.getId());
                Date applyDate = null;
                Date approvalDate = null;

                if (response.getAppDate() != null) {
                    applyDate = gdsUtilService.dateStringToDate(response.getAppDate());
                    if (null == applyDate) {
                        throw new IntegrationException(IntegrationException.APPLY_DATE_FORMAT_ERROR, null);
                    }
                }
                if (response.getNewOrgTranTime() != null) {
                    approvalDate = gdsUtilService.dateStringToDate(response.getNewOrgTranTime());
                    if (null == applyDate) {
                        throw new IntegrationException(IntegrationException.AUDIT_DATE_FORMAT_ERROR, null);
                    }
                }

                // 界面显示字段
                memMarketChange.setApplyDate(applyDate);
                memMarketChange.setMemberCode(response.getDealerNo());
               /* String fromMarketName = gdsUtilService.getOrgCode(request, response.getOldSaleOrgCode());
                String toMarketName = gdsUtilService.getOrgCode(request, response.getNewSaleOrgCode());*/
                //界面直接显示gds市场编码
                String fromMarketName = response.getOldSaleOrgCode();
                String toMarketName = response.getNewSaleOrgCode();
                if (StringUtils.isAnyEmpty(fromMarketName, toMarketName)) {
                    throw new IntegrationException(IntegrationException.MARKET_CODE_NOT_FOUND, null);
                }
                memMarketChange.setFromMarketName(fromMarketName);
                memMarketChange.setToMarketName(toMarketName);
                memMarketChange.setRemark(response.getAppAuditedMemo());
                memMarketChange.setApprovalStatus(IntegrationConstant.MARKET_CHANGE_NEW);
                memMarketChange.setApprovalDate(approvalDate);
                memMarketChange.setMemberName(response.getAppFullName());
                marketChanges.add(memMarketChange);

                // 入库接口表
                isgMarketChangeList.setGdsId(response.getId());
                isgMarketChangeList.setAppAuditedBy(response.getAppAuditedBy());
                isgMarketChangeList.setAppAuditedMemo(response.getAppAuditedMemo());
                isgMarketChangeList.setAppAuditedTime(response.getAppAuditedTime());
                isgMarketChangeList.setAppCertificateNationCode(response.getAppCertificateNationCode());
                isgMarketChangeList.setAppCertificateNo(response.getAppCertificateNo());
                isgMarketChangeList.setAppCertificateType(response.getAppCertificateType());
                isgMarketChangeList.setAppCheckedBy(response.getAppCheckedBy());
                isgMarketChangeList.setAppCheckedMemo(response.getAppCheckedMemo());
                isgMarketChangeList.setAppDate(response.getAppDate());
                isgMarketChangeList.setAppDocNo(response.getAppDocNo());
                isgMarketChangeList.setAppEntryBy(response.getAppEntryBy());
                isgMarketChangeList.setAppEntryTime(response.getAppEntryTime());
                isgMarketChangeList.setAppFirstName(response.getAppFirstName());
                isgMarketChangeList.setAppFullName(response.getAppFullName());
                isgMarketChangeList.setAppLastName(response.getAppLastName());
                isgMarketChangeList.setComments(response.getComments());
                isgMarketChangeList.setCreateby(response.getCreateBy());
                isgMarketChangeList.setCreateTime(response.getCreateTime());
                isgMarketChangeList.setDealerNo(response.getDealerNo());
                isgMarketChangeList.setGdsTranBy(response.getGdsTranSoPeriod());
                isgMarketChangeList.setGdsTranStatus(response.getGdsTranStatus());
                isgMarketChangeList.setGdsTranTime(response.getGdsTranTime());
                isgMarketChangeList.setLastUpdateby(response.getLastUpdateBy());
                isgMarketChangeList.setLastUpdateTime(response.getLastUpdateTime());
                isgMarketChangeList.setNewDealerSubType(response.getNewDealerSubType());
                isgMarketChangeList.setNewDealerType(response.getNewDealerType());
                isgMarketChangeList.setNewOrgTranBy(response.getNewOrgTranBy());
                isgMarketChangeList.setNewOrgTranFlag(response.getNewOrgTranFlag());
                isgMarketChangeList.setNewOrgTranMemo(response.getNewOrgTranMemo());
                isgMarketChangeList.setNewOrgTranTime(response.getNewOrgTranTime());
                isgMarketChangeList.setNewSaleBranchNo(response.getNewSaleBranchNo());
                isgMarketChangeList.setNewSaleOrgCode(response.getNewSaleOrgCode());
                isgMarketChangeList.setNewSaleZoneNo(response.getNewSaleZoneNo());
                isgMarketChangeList.setOldOrgTranBy(response.getOldOrgTranBy());
                isgMarketChangeList.setOldOrgTranFlag(response.getOldOrgTranFlag());
                isgMarketChangeList.setOldOrgTranMemo(response.getOldOrgTranMemo());
                isgMarketChangeList.setOldOrgTranTime(response.getOldOrgTranTime());
                isgMarketChangeList.setOldSaleOrgCode(response.getOldSaleOrgCode());
                isgMarketChangeList.setProcessStatus(IntegrationConstant.ISG_MARKET_CHANGE_PROCESS_SUCCESS);

                isgMarketChangeListMapper.insert(isgMarketChangeList);

            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("调用gds findGdealerChgOrgAppAuditList接口失败： " + e.getMessage(), e);
            }
            isgMarketChangeList.setProcessStatus(IntegrationConstant.ISG_MARKET_CHANGE_PROCESS_ERROR);
            isgMarketChangeList.setProcessMessage(e.getMessage());
            self().insertInterface(isgMarketChangeList);

            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE,
                        new Object[] { ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }

            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_FIND_GDEALER_CHG_ORG_APP_AUDIT_LIST,
                    new Object[] { e.getMessage() });
        }

        return marketChanges;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertInterface(IsgMarketChangeList isgMarketChangeList) {
        isgMarketChangeListMapper.insert(isgMarketChangeList);
    }

}
