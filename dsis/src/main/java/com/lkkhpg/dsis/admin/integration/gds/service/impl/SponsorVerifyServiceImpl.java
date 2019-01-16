/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.ISponsorVerifyService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.MemOverseasSponsor;
import com.lkkhpg.dsis.common.member.dto.Sponsor;
import com.lkkhpg.dsis.common.member.mapper.MemOverseasSponsorMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.dealers.dealerNo.sponsorVerify.model.SponsorVerifyPOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 推荐人即时鉴别实现类.
 * 
 * @author linyuheng
 */
@Service
public class SponsorVerifyServiceImpl implements ISponsorVerifyService {

    private final Logger logger = LoggerFactory.getLogger(SponsorVerifyServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemOverseasSponsorMapper memOverseasSponsorMapper;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public Sponsor sponsorVerify(IRequest request, String memberCode, String orgCode) throws IntegrationException {
        SponsorVerifyPOSTResponse sponsorVerifyPOSTResponse;
        try {
            sponsorVerifyPOSTResponse = gdsService.sponsorVerify(memberCode, orgCode);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_SPONSOR_VERIFY, e);
            }
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                        ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() });
            }
            throw new IntegrationException(e.getMessage(), null);
        }

        // GDS返回会员卡号不匹配
        if (!memberCode.equals(sponsorVerifyPOSTResponse.getDealerNo())) {
            throw new IntegrationException(IntegrationException.GDS_MEMBER_CODE_NOT_MATCH, null);
        }

        // 推荐人卡号为空
        if (sponsorVerifyPOSTResponse.getDealerNo() == null || sponsorVerifyPOSTResponse.getDealerNo().equals("")) {
            throw new IntegrationException(IntegrationException.MSG_ERROR_INVALID_SPONSOR, null);
        }

        // 不允许台湾VIP会员作为推荐人
        if (sponsorVerifyPOSTResponse.getSaleOrgCode() != null && sponsorVerifyPOSTResponse.getDealerType() != null) {
            String dealerType = sponsorVerifyPOSTResponse.getDealerType();
            String memberRole = codeService.getCodeValueByDesc(request, IntegrationConstant.MM_MEMBER_ROLE, dealerType);
            String sponsorOrgCode = gdsUtilService.getOrgCode(sponsorVerifyPOSTResponse.getSaleOrgCode());
            if (MemberConstants.MM_ROLE_VIP.equals(memberRole)) {
                SpmMarket sm = spmMarketMapper.selectMarketWithoutEnabled(sponsorOrgCode);
                if (sm != null) {
                    String enable = marketParamService.getParamValue(request,
                            SystemProfileConstants.SPM_MEMBER_VIP_SPONSOR, SystemProfileConstants.MARKET,
                            String.valueOf(sm.getMarketId()), SystemProfileConstants.ORG_TYPE_MARKET);
                    if (enable == null || BaseConstants.NO.equals(enable)) {
                        throw new IntegrationException(IntegrationException.MSG_ERROR_INVALID_SPONSOR_TW_VIP, null);
                    }
                }
            }
        }

        // 判断会员表和海外推荐人表是否存在该会员，若没有则新增一条记录
        String result = memberMapper.isInMemtabAndMemOvertab(sponsorVerifyPOSTResponse.getDealerNo());
        if (!BaseConstants.YES.equals(result)) {
            if (logger.isDebugEnabled()) {
                logger.debug("海外推荐人表新增会员:{}", sponsorVerifyPOSTResponse.getDealerNo());
            }
            MemOverseasSponsor memOverseasSponsor = new MemOverseasSponsor();
            memOverseasSponsor.setDealerNo(sponsorVerifyPOSTResponse.getDealerNo());
            memOverseasSponsor.setDealerName(sponsorVerifyPOSTResponse.getDealerName());
            Long dealerPostCode = sponsorVerifyPOSTResponse.getDealerPostCode();
            memOverseasSponsor.setDealerPostCode(dealerPostCode == null ? "" : String.valueOf(dealerPostCode));
            memOverseasSponsor.setSponsorNo(sponsorVerifyPOSTResponse.getSponsorNo());
            memOverseasSponsor.setSaleBranchNo(sponsorVerifyPOSTResponse.getSaleBranchNo());
            memOverseasSponsor.setSaleOrgCode(sponsorVerifyPOSTResponse.getSaleOrgCode());
            memOverseasSponsor.setTaxCustCode(sponsorVerifyPOSTResponse.getTaxCustCode());
            String adviseNo = sponsorVerifyPOSTResponse.getAdviseNo();
            memOverseasSponsor.setAdviseNo(adviseNo == null ? "null" : sponsorVerifyPOSTResponse.getAdviseNo());
            memOverseasSponsorMapper.insertSelective(memOverseasSponsor);
            if (logger.isDebugEnabled()) {
                logger.debug("新增成功");
            }
        }

        // 鉴别成功，返回推荐人信息
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorNo(sponsorVerifyPOSTResponse.getDealerNo());
        sponsor.setSponsorChineseName(sponsorVerifyPOSTResponse.getDealerName());
        sponsor.setSponsorEnglishName(sponsorVerifyPOSTResponse.getEnglishName());

        return sponsor;

    }
}
