/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChangeOutApply;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeOutApplyMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.ISaveGdealerChgOrgAppService;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.applications.model.ApplicationsPOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 保存转出本市场（源市场）的申请 .
 * 
 * @author chuangsheng.zhang.
 */
@Transactional
@Service
public class SaveGdealerChgOrgAppServiceImpl implements ISaveGdealerChgOrgAppService {

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private ISpmMarketService spmMarketService;

    @Autowired
    private IsgMarketChangeOutApplyMapper isgMarketChangeOutApplyMapper;

    @Autowired
    private IMemberService memberService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApplicationsPOSTResponse saveGdealerChgOrgApp(IRequest request, MemMarketChange memMarketChange)
            throws IntegrationException {

        ApplicationsPOSTBody body = new ApplicationsPOSTBody();
        String processsStatus = IntegrationConstant.PROCESS_STATUS_S;
        try {

            String orgCode = gdsUtilService.getCurrentOrgCode(request);
            orgCode = gdsUtilService.getGdsOrgCode(request, orgCode);

            Member member = memberService.getMember(request, memMarketChange.getMemberId());

            // 卡号
            body.setDealerNo(memMarketChange.getMemberCode());

            SpmMarket frompMarket = spmMarketService.queryByMarketId(request, memMarketChange.getFromMarketId());
            SpmMarket toMarket = spmMarketService.queryByMarketId(request, memMarketChange.getToMarketId());

            body.setOldSaleOrgCode(gdsUtilService.getGdsOrgCode(frompMarket.getCode()));
            body.setNewSaleOrgCode(gdsUtilService.getGdsOrgCode(toMarket.getCode()));
            body.setNewDealerType("");
            body.setNewDealerSubType("");
            body.setAppDate("");
            body.setAppDocNo("");
            body.setAppEntryTime("");
            body.setAppEntryBy(request.getAccountId().toString());
            body.setAppCheckedTime("");
            body.setAppCheckedBy("");
            body.setAppCheckedMemo("");
            body.setAppAuditedTime("");
            body.setAppAuditedBy("");
            body.setAppAuditedMemo("");
            body.setGdsTranStatus("P");
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
            body.setAppCertificateType("");
            body.setAppCertificateNo("");
            body.setNewSaleBranchNo("");
            body.setNewSaleZoneNo("");
            body.setComments("");
            body.setId(1L);
            ApplicationsPOSTResponse response = gdsService.saveGdealerChgOrgApp(orgCode, body);
            save(body, processsStatus, null);
            return response;
        } catch (Exception e) {
            processsStatus = IntegrationConstant.PROCESS_STATUS_E;
            save(body, processsStatus, e.getMessage());

            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }

            throw new IntegrationException(e.getMessage(), null);
        } finally {
            save(body, processsStatus, null);
        }
    }

    /**
     * 保存变更市场申请的原因.
     * 
     * @param body
     *            变更内容
     * @param processsStatus
     *            操作状态
     * @param errorMessage
     *            错误消息,可为 null
     * @return IsgMarketChangeOutApply dto.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private IsgMarketChangeOutApply save(ApplicationsPOSTBody body, String processsStatus, String errorMessage) {
        IsgMarketChangeOutApply marketChangeOutApply = new IsgMarketChangeOutApply();

        // 卡号
        marketChangeOutApply.setDealerNo(body.getDealerNo());

        // 申请人姓
        marketChangeOutApply.setAppLastName(body.getAppLastName());

        // 申请人名
        marketChangeOutApply.setAppFirstName(body.getAppFirstName());

        // 新销售机构代码

        marketChangeOutApply.setNewSaleOrgCode(body.getNewSaleOrgCode());

        marketChangeOutApply.setAppEntryBy(body.getAppEntryBy());

        // 备注
        marketChangeOutApply.setRemarks(body.getComments());

        marketChangeOutApply.setProcessStatus(processsStatus);

        marketChangeOutApply.setProcessMessage(errorMessage);

        isgMarketChangeOutApplyMapper.insert(marketChangeOutApply);
        return marketChangeOutApply;
    }

}