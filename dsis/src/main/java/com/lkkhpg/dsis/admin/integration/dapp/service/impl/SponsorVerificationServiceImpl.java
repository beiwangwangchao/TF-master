/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.bonus.constant.BonusConstants;
import com.lkkhpg.dsis.admin.integration.dapp.dto.SponsorVerificationRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.SponsorVerificationResponse;
import com.lkkhpg.dsis.admin.integration.dapp.service.ISponsorVerificationService;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 推荐人鉴别实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class SponsorVerificationServiceImpl implements ISponsorVerificationService {

    private final Logger logger = LoggerFactory.getLogger(SponsorVerificationServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private ICodeService codeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SponsorVerificationResponse sponsorVerfication(SponsorVerificationRequest sponsorVerificationRequest)
            throws DAppException, com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException {
        IRequest iRequest = RequestHelper.newEmptyRequest();
        iRequest.setLocale(BaseConstants.DEFAULT_LANG);
        String sponsorID = sponsorVerificationRequest.getSponsorID();
        String market = sponsorVerificationRequest.getMarket();
        String gdsOrgCode;
        gdsOrgCode = gdsUtilService.getGdsOrgCode(market);

        SponsorVerificationResponse sponsorVerificationResponse = new SponsorVerificationResponse();
        sponsorVerificationResponse.setSponsorID(sponsorID);
        SponsorVerifyPOSTResponse response = null;
        try {
            response = gdsService.sponsorVerify(sponsorID, gdsOrgCode);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_SPONSOR_VERIFY, e);
            }
            if (e instanceof ESBException) {
                sponsorVerificationResponse.setResult(BaseConstants.NO);
                sponsorVerificationResponse.setSponsorNameEng("");
                sponsorVerificationResponse.setSponsorNameChi("");
                sponsorVerificationResponse.setSponsorMarket("");
                sponsorVerificationResponse.setSponsorMemberRole("");
                return sponsorVerificationResponse;
            }
            throw new IntegrationException(e.getMessage(), null);
        }

        // 不允许台湾VIP会员作为推荐人
        if (response.getSaleOrgCode() != null && response.getDealerType() != null) {
            String dealerType = response.getDealerType();
            String memberRole = codeService.getCodeValueByDesc(iRequest, IntegrationConstant.MM_MEMBER_ROLE,
                    dealerType);
            String sponsorOrgCode = gdsUtilService.getOrgCode(response.getSaleOrgCode());
            if (BonusConstants.MARKET_TW.equals(sponsorOrgCode) && MemberConstants.MM_ROLE_VIP.equals(memberRole)) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_INVALID_SPONSOR_TW_VIP, null);
            }
        }
        // GDS返回会员卡号不匹配或推荐人卡号为空
        if (!sponsorID.equals(response.getDealerNo()) || response.getDealerNo() == null
                || response.getDealerNo().equals("")) {
            sponsorVerificationResponse.setResult(BaseConstants.NO);
            sponsorVerificationResponse.setSponsorNameEng("");
            sponsorVerificationResponse.setSponsorNameChi("");
            sponsorVerificationResponse.setSponsorMarket("");
            sponsorVerificationResponse.setSponsorMemberRole("");
        } else {
            String saleOrgCode = response.getSaleOrgCode();
            String dealerType = response.getDealerType();
            String orgCode = gdsUtilService.getOrgCode(saleOrgCode);
            String memberRole = codeService.getCodeValueByDesc(iRequest, IntegrationConstant.MM_MEMBER_ROLE,
                    dealerType);
            sponsorVerificationResponse.setResult(BaseConstants.YES);
            sponsorVerificationResponse.setSponsorNameEng(response.getEnglishName());
            sponsorVerificationResponse.setSponsorNameChi(response.getDealerName());
            sponsorVerificationResponse.setSponsorMarket(orgCode);
            sponsorVerificationResponse.setSponsorMemberRole(memberRole);
        }
        return sponsorVerificationResponse;
    }

}
