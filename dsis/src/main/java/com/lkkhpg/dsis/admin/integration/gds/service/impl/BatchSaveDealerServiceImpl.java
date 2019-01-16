/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberInfoSync;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberRelSync;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMemberSync;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberInfoSyncMapper;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberRelSyncMapper;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMemberSyncMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IBatchSaveDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemRank;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRankMapper;
import com.lkkhpg.dsis.common.member.mapper.MemRelationshipMapper;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.BatchSavePOSTResponse;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.GdealerPersonalInfo;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchSave.model.GdealerSpouseInfo;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 小批量同步会员资料.
 *
 * @author linyuheng
 */
@Service
@Transactional
public class BatchSaveDealerServiceImpl implements IBatchSaveDealerService {

    private Logger logger = LoggerFactory.getLogger(BatchSaveDealerServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemRankMapper rankMapper;

    @Autowired
    private MemSiteMapper memSiteMapper;

    @Autowired
    private MemRelationshipMapper memRelationshipMapper;

    @Autowired
    private MemAccountMapper memAccountMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private IsgMemberSyncMapper isgMemberSyncMapper;

    @Autowired
    private IsgMemberInfoSyncMapper isgMemberInfoSyncMapper;

    @Autowired
    private IsgMemberRelSyncMapper isgMemberRelSyncMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<BatchSavePOSTBody> getDealer(String adviseNo, String orgCode) {
        IsgMemberSync isgMemberSync = new IsgMemberSync();
        List<BatchSavePOSTBody> gDealers = new ArrayList<BatchSavePOSTBody>();
        List<Long> marketIdList = new ArrayList<Long>();
        try {
            marketIdList = gdsUtilService.getMarketIds(orgCode);
        } catch (IntegrationException e1) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING, e1);
            }
        }
        List<Member> members = memberMapper.selectBySyncFlag(marketIdList);
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);
        for (Member member : members) {
            List<IsgMemberInfoSync> isgMemberInfoSyncs = new ArrayList<IsgMemberInfoSync>();
            List<IsgMemberRelSync> isgMemberRelSyncs = new ArrayList<IsgMemberRelSync>();
            try {
                Long memberId = member.getMemberId();
                BatchSavePOSTBody batchSavePOSTBody = new BatchSavePOSTBody();
                batchSavePOSTBody.setDealerNo(member.getMemberCode()); // 卡号
                String dealerType = IntegrationConstant.DEALERTYPE_DISTRIBUTOR;
                if (member.getMemberRole() != null) {
                    String roleDesc = codeService.getCodeDescByValue(request, IntegrationConstant.MM_MEMBER_ROLE,
                            member.getMemberRole());
                    if (roleDesc != null) {
                        dealerType = roleDesc;
                    }
                }
                batchSavePOSTBody.setDealerType(dealerType); // 身份类型
                // 身份子类型
                if (IntegrationConstant.DEALERTYPE_SUPPORT.equals(dealerType)) {
                    batchSavePOSTBody.setDealerSubType(IntegrationConstant.DEALERSUBTYPE_SALE);
                } else if (IntegrationConstant.DEALERTYPE_DISTRIBUTOR.equals(dealerType)) {
                    batchSavePOSTBody.setDealerSubType(IntegrationConstant.DEALERSUBTYPE_PRODEALER);
                }
                // 联级代码
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("memberCode", member.getMemberCode());
                MemRank memRank = rankMapper.selectByCurrentMonthRangFromOmk(map);
                if (memRank != null) {
                    batchSavePOSTBody.setDealerPostCode(memRank.getRank());
                } else {
                    batchSavePOSTBody.setDealerPostCode(0L);
                }
                batchSavePOSTBody.setSponsorNo(member.getSponsorNo()); // 推荐人卡号取推荐人ID
                // 会员姓名
                //当会员类型为"公司"，中英文姓名传公司的中英文姓名过去
                if (IntegrationConstant.COMPANY.equals(member.getMemberType())) {
                    if (member.getRefCompany() != null) {
                        batchSavePOSTBody.setDealerName(member.getRefCompany());
                    } else if (member.getRefCompanyEn() != null) {
                        batchSavePOSTBody.setDealerName(member.getRefCompanyEn());
                    }
                } else {
                    if (member.getChineseName() != null) {
                        batchSavePOSTBody.setDealerName(member.getChineseName());
                    } else if (member.getEnglishName() != null) {
                        batchSavePOSTBody.setDealerName(member.getEnglishName());
                    }
                }
                batchSavePOSTBody.setRegisterSpouse(false); // 是否夫妻联名
                List<MemRelationship> memRelationships = memRelationshipMapper.selectByMemberId(memberId);
                MemRelationship spouseRelationship = null;
                for (MemRelationship memRelationship : memRelationships) {
                    if (MemberConstants.MEMBER_REL_TYPE_SPOUS.equals(memRelationship.getRelType())) {
                        spouseRelationship = memRelationship;
                        batchSavePOSTBody.setRegisterSpouse(true);
                    }
                }
                // 批准日期格式化为yyyy-MM-dd
                String approvalDate = "";
                if (member.getApprovalDate() != null) {
                    approvalDate = gdsUtilService.toDateString(member.getApprovalDate());
                } else if (member.getJointDate() != null) {
                    approvalDate = gdsUtilService.toDateString(member.getJointDate());
                }
                batchSavePOSTBody.setTypeEffectiveDate(approvalDate);
                String code = null;
                if (member.getMarketCode() != null) {
                    code = gdsUtilService.getGdsOrgCode(member.getMarketCode());
                }
                batchSavePOSTBody.setSaleOrgCode(code); // 所属机构代号取所属市场CODE
                SpmSalesOrganization salesOrganization = spmSalesOrganizationMapper
                        .selectByPrimaryKey(Long.valueOf(member.getJointSite()));
                if (salesOrganization == null) {
                    logger.error("member {} joint site not exist : {}", member.getMemberCode(), member.getJointSite());
                    continue;
                }
                String saleBranchNo = salesOrganization.getCode();
                if (saleBranchNo != null && saleBranchNo.length() > IntegrationConstant.SALE_BRANCH_NO_MAX_LENGTH) {
                    saleBranchNo = saleBranchNo.substring(1, IntegrationConstant.BRANCH_NO_SUB_NUMBER);
                }
                batchSavePOSTBody.setSaleBranchNo(saleBranchNo); // 所属分支机构代号取所属销售区域CODE
                batchSavePOSTBody.setSaleZoneNo(""); // 销售片区代号，传NULL
                // 加入日期格式化为yyyyMM
                String appPeriod = null;
                if (member.getJointDate() != null) {
                    appPeriod = gdsUtilService.toPeriodString(member.getJointDate());
                    batchSavePOSTBody.setAppPeriod(appPeriod);
                }
                batchSavePOSTBody.setAppFirstSoNo("*NONE"); // 第一个销售订单编号 GDS固定值
                String sponsorNo = member.getSponsorNo();
                if (sponsorNo != null) {
                    String sponsorMarketCode = memberMapper.selectMarketCodeByMemberCode(sponsorNo);
                    if (sponsorMarketCode != null && member.getMarketCode().equals(sponsorMarketCode)) {
                        batchSavePOSTBody.setAppForInternational(false); // 是否国际推荐
                    } else {
                        batchSavePOSTBody.setAppForInternational(true); // 是否国际推荐
                    }
                } else {
                    batchSavePOSTBody.setAppForInternational(true);
                }
                batchSavePOSTBody.setSex(member.getGender());
                //当会员类型为"公司"，中英文姓名传公司的中英文姓名过去
                if (IntegrationConstant.COMPANY.equals(member.getMemberType())) {
                    batchSavePOSTBody.setEnglishName(member.getRefCompanyEn());
                } else {
                    batchSavePOSTBody.setEnglishName(member.getEnglishName()); // 英文名
                    String englishFirstName = member.getEnglishFirstName();
                    String englishLastName = member.getEnglishLastName();
                    String lfEngName = ""; // english_last_name + english_first_name
                    if (StringUtils.isNoneEmpty(englishFirstName) && StringUtils.isNoneEmpty(englishLastName)) {
                        lfEngName = englishLastName + " " + englishFirstName;
                    } else if (StringUtils.isNoneEmpty(englishFirstName)) {
                        lfEngName = englishFirstName;
                    } else if (StringUtils.isNoneEmpty(englishLastName)) {
                        lfEngName = englishLastName;
                    }
                    List<MemAccount> memAccounts = memAccountMapper.selectByMemberId(memberId);
                    if (memAccounts != null && memAccounts.size() > 0) {
                        // 把这两个名字分别和账户持有人姓名作对比，把对的上的那个传过去,如果都对不上，就传english_first_name
                        // + english_last_name
                        if (lfEngName.equals(memAccounts.get(0).getAccountHolder())) {
                            batchSavePOSTBody.setEnglishName(lfEngName);
                        }
                    }
                }
                batchSavePOSTBody.setComments(member.getRemark()); // 备注
                batchSavePOSTBody.setOpLockStatus(IntegrationConstant.NO); // 业务锁状态
                if (member.getMarketCode() != null && member.getMemberType() != null) {
                    batchSavePOSTBody.setTaxCustCode(codeService.getCodeValueByMeaningAndDesc(request,
                            IntegrationConstant.ISG_GDS_TAX_CUST_CODE, orgCode, member.getMemberType()));
                } else {
                    batchSavePOSTBody.setTaxCustCode("KHK1");
                }
                isgMemberSync = getIsgMemberSync(batchSavePOSTBody, member);
                isgMemberSync.setAdviseNo(adviseNo);
                isgMemberSync.setOperationType(IntegrationConstant.OPERATION_TYPE_SYNC); // 操作类型，

                // -----------------------经销商个人资讯----------------------------
                GdealerPersonalInfo gdealerPersonalInfo = createPersonalInfo(request, member, memberId);
                IsgMemberInfoSync isgMemberInfoSync = getIsgMemberInfoSync(gdealerPersonalInfo, member);
                isgMemberInfoSync.setAdviseNo(adviseNo);
                isgMemberInfoSyncs.add(isgMemberInfoSync);
                // -----------------------gdealerSpouseInfo----------------------
                if (spouseRelationship != null && spouseRelationship.getIdType() != null
                        && spouseRelationship.getIdNumber() != null) {
                    GdealerSpouseInfo gdealerSpouseInfo = createSpouseInfo(spouseRelationship, saleBranchNo, appPeriod);
                    IsgMemberRelSync isgMemberRelSync = getIsgMemberRelSync(gdealerSpouseInfo, member);
                    isgMemberRelSync.setAdviseNo(adviseNo);
                    isgMemberRelSyncs.add(isgMemberRelSync);
                    batchSavePOSTBody.setGdealerSpouseInfo(gdealerSpouseInfo);
                }
                batchSavePOSTBody.setGdealerPersonalInfo(gdealerPersonalInfo);
                gDealers.add(batchSavePOSTBody);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(IntegrationException.MSG_ERROR_ISG_BATCH_SAVE_DEALER, e);
                }
                isgMemberSync.setProcessStatus(IntegrationConstant.ERROR_STATUS);
                isgMemberSync.setProcessMessage(e.toString() + ":" + e.getMessage());
            } finally {
                self().insertIsgMemberSyncInterface(isgMemberSync, isgMemberInfoSyncs, isgMemberRelSyncs);
            }
        }
        return gDealers;
    }

    public GdealerSpouseInfo createSpouseInfo(MemRelationship spouseRelationship, String saleBranchNo,
            String appPeriod) {
        GdealerSpouseInfo gdealerSpouseInfo = new GdealerSpouseInfo();
        Member spouse = memberMapper.selectByPrimaryKey(spouseRelationship.getMemberId());
        // 卡号
        gdealerSpouseInfo.setDealerNo(spouse.getMemberCode());
        // 与saleBranchNo相同
        gdealerSpouseInfo.setAppDealerNo(saleBranchNo);
        // 申请月份留空
        gdealerSpouseInfo.setAppPeriod(appPeriod); // 待确认
        gdealerSpouseInfo.setAppType(IntegrationConstant.APP_TYPE);
        // 家庭成员全称留空
        if (spouseRelationship.getChineseName() != null) {
            gdealerSpouseInfo.setSpouseFullName(spouseRelationship.getChineseName());
        } else if (spouseRelationship.getEnglishName() != null) {
            gdealerSpouseInfo.setSpouseFullName(spouseRelationship.getEnglishName());
        }
        // 家庭成员证件国家留空
        gdealerSpouseInfo.setSpouseCertificateNationCode(spouse.getCountry());
        gdealerSpouseInfo.setSpouseCertificateType(IntegrationConstant.CERTIFICATE_TYPE_INDV);
        gdealerSpouseInfo.setSpouseCertificateNo(spouseRelationship.getIdNumber());
        if (IntegrationConstant.SEX_M.equals(spouse.getGender())) {
            gdealerSpouseInfo.setSpouseSex(IntegrationConstant.SEX_F);
        } else {
            gdealerSpouseInfo.setSpouseSex(IntegrationConstant.SEX_M);
        }
        gdealerSpouseInfo.setSpouseMobile("");
        gdealerSpouseInfo.setSpouseTele("");
        gdealerSpouseInfo.setSpouseRace("");
        // gdealerSpouseInfo.setSpouseRefDealerNo("");//
        // 接口暂无配偶卡号来源字段
        gdealerSpouseInfo.setComments("");
        // gdealerSpouseInfo.setId(null); //生成body不存在
        gdealerSpouseInfo.setCreateBy(spouseRelationship.getCreatedBy().toString());
        // gdealerSpouseInfo.createTime(null); //生成body不存在
        gdealerSpouseInfo.setLastUpdateBy(spouseRelationship.getLastUpdatedBy().toString());
        if (spouseRelationship.getLastUpdateDate() != null) {
            gdealerSpouseInfo
                    .setLastUpdateTime(gdsUtilService.toDateTimeString(spouseRelationship.getLastUpdateDate()));
        } else {
            gdealerSpouseInfo.setLastUpdateTime("");
        }
        return gdealerSpouseInfo;
    }

    public GdealerPersonalInfo createPersonalInfo(IRequest request, Member member, Long memberId) {
        GdealerPersonalInfo gdealerPersonalInfo = new GdealerPersonalInfo();
        gdealerPersonalInfo.setDealerNo(member.getMemberCode()); // ID
        gdealerPersonalInfo.setPrivacyFlag(IntegrationConstant.YES); // 隐私标志
        // 按默认传值
        String certificateNationCode = member.getCountry(); // 证件国家
        gdealerPersonalInfo.setCertificateNationCode(certificateNationCode);
        // 证件类型
        String certificateType = null;
        if (member.getMemberType() != null) {
            certificateType = codeService.getCodeDescByValue(request, IntegrationConstant.MM_MEMBER_TYPE,
                    member.getMemberType());
        }
        gdealerPersonalInfo.setCertificateType(certificateType);
        gdealerPersonalInfo.setCertificateNo(member.getIdNumber()); // 证件编号
        // ID_NUMBER
        gdealerPersonalInfo.setCertificateMemo(""); // 证件备注留空
        gdealerPersonalInfo.setCertificateAddrZipCode(""); // 证件地址信息留空
        gdealerPersonalInfo.setCertificateAddrProvince("");
        gdealerPersonalInfo.setCertificateAddrCity("");
        gdealerPersonalInfo.setCertificateAddrTail01("");
        gdealerPersonalInfo.setCertificateAddrTail02("");
        // 地址信息
        List<MemSite> memSites = memSiteMapper.selectCtactByMemberId(memberId);
        if (memSites != null && memSites.size() > 0) {
            SpmLocation spmLocation = spmLocationMapper.selectByPrimaryKey(memSites.get(0).getLocationId());
            String countryCode = spmLocation.getCountryCode();
            String stateCode = spmLocation.getStateCode();
            String cityCode = spmLocation.getCityCode();
            String zipCode = spmLocation.getZipCode();
            String addressLine1 = spmLocation.getAddressLine1();
            String addressLine2 = spmLocation.getAddressLine2();
            if (addressLine1 != null && addressLine1.length() > IntegrationConstant.ADDRESS_MAX_LENGTH) {
                addressLine1 = addressLine1.substring(1, IntegrationConstant.ADDRESS_MAX_LENGTH);
            }
            if (addressLine2 != null && addressLine2.length() > IntegrationConstant.ADDRESS_MAX_LENGTH) {
                addressLine2 = addressLine2.substring(1, IntegrationConstant.ADDRESS_MAX_LENGTH);
            }
            gdealerPersonalInfo.setAddrNationCode(countryCode);
            gdealerPersonalInfo.setAddrZipCode(zipCode);
            gdealerPersonalInfo.setAddrProvince(stateCode);
            gdealerPersonalInfo.setAddrCity(cityCode);
            gdealerPersonalInfo.setAddrTail01(addressLine1);
            gdealerPersonalInfo.setAddrTail02(addressLine2);
            String enabledFlag = spmLocation.getEnabledFlag(); // 地址有效性
            if (IntegrationConstant.YES.equals(enabledFlag)) {
                gdealerPersonalInfo.setAddrEnabled(true);
            } else {
                gdealerPersonalInfo.setAddrEnabled(false);
            }
        } else {
            gdealerPersonalInfo.setAddrEnabled(false);
        }
        gdealerPersonalInfo.setAddrMemo(""); // 位址备注信息留空
        String phoneNumber = member.getPhoneNo(); // 联系号码
        gdealerPersonalInfo.setContactTele(phoneNumber);
        gdealerPersonalInfo.setContactFaxNo(""); // 传真号码和行动电话留空
        gdealerPersonalInfo.setContactMobile("");
        gdealerPersonalInfo.setContactEmail(member.getEmail()); // 电子邮箱地址

        if (member.getRace() != null) {
            String race = codeService.getCodeDescByValue(request, IntegrationConstant.MM_MEMBER_RACE, member.getRace());
            if (race != null) {
                gdealerPersonalInfo.setRace(race); // 种族
            }
        }

        if (member.getEducation() != null) {
            String education = codeService.getCodeDescByValue(request, IntegrationConstant.MM_MEMBER_EDUCATION,
                    member.getEducation());
            if (education != null) {
                gdealerPersonalInfo.setEducation(education); // 学历
            }
        }

        gdealerPersonalInfo.setOccupation(""); // 职业留空
        if (member.getDob() != null) {
            gdealerPersonalInfo.setBirthday(gdsUtilService.toDateString(member.getDob())); // 出生日期
        }
        gdealerPersonalInfo.setComments(""); // 备注留空
        return gdealerPersonalInfo;
    }

    @Override
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void insertIsgMemberSyncInterface(IsgMemberSync isgMemberSync, List<IsgMemberInfoSync> isgMemberInfoSyncs,
            List<IsgMemberRelSync> isgMemberRelSyncs) {
        try {
            isgMemberSyncMapper.insertSelective(isgMemberSync);
            for (IsgMemberInfoSync isgMemberInfoSync : isgMemberInfoSyncs) {
                isgMemberInfoSync.setInterfaceId(isgMemberSync.getInterfaceId());
                isgMemberInfoSyncMapper.insertSelective(isgMemberInfoSync);
            }
            for (IsgMemberRelSync isgMemberRelSync : isgMemberRelSyncs) {
                isgMemberRelSync.setInterfaceId(isgMemberSync.getInterfaceId());
                isgMemberRelSyncMapper.insertSelective(isgMemberRelSync);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDealer(String adviseNo, List<BatchSavePOSTBody> requestData, List<BatchSavePOSTResponse> response,
            Exception exception) {
        if (exception == null) {
            for (BatchSavePOSTResponse GdsReturnMessage : response) {
                try {
                    // 若message无值，则接口调用正常
                    if (GdsReturnMessage.getMessage() == null) {
                        String memberCode = GdsReturnMessage.getDealerNo();
                        if (memberCode == null || "".equals(memberCode)) {
                            if (logger.isErrorEnabled()) {
                                logger.error(IntegrationException.GDS_MEMBER_CODE_NULL);
                            }
                            throw new IntegrationException(IntegrationException.GDS_MEMBER_CODE_NULL, null);
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("memberId",
                                memberMapper.selectMembersByMemberCode(GdsReturnMessage.getDealerNo()).getMemberId());
                        map.put("syncFlag", IntegrationConstant.YES);
                        self().updateSynFlag(map);
                    } else {
                        if (logger.isErrorEnabled()) {
                            logger.error(GdsReturnMessage.getMessage());
                        }
                    }
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        } else {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_BATCH_SAVE_DEALER, exception);
            }
            return;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSynFlag(Map<String, Object> map) {
        memberMapper.updateSynFlagByMemberId(map);
    }

    /**
     * 获取计税客户代号.
     *
     * @param member
     *            会员DTO
     * @return 计税客户代号
     */
    public String getTaxCustCode(Member member) {
        Long marketId = member.getMarketId();
        String country = member.getCountry();
        String memberType = member.getMemberType();

        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_HK) && IntegrationConstant.COUNTRY_HK.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KHK1;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_HK) && IntegrationConstant.COUNTRY_HK.equals(country)
                && IntegrationConstant.COMPANY.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KHK2;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_HK)
                && !IntegrationConstant.COUNTRY_HK.equals(country) && !IntegrationConstant.COUNTRY_CN.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KHK3;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_HK) && IntegrationConstant.COUNTRY_CN.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KHK4;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_ML) && IntegrationConstant.COUNTRY_ML.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KMY1;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_ML) && IntegrationConstant.COUNTRY_ML.equals(country)
                && IntegrationConstant.COMPANY.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KMY2;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_ML)
                && !IntegrationConstant.COUNTRY_ML.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KMY3;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_TW) && IntegrationConstant.COUNTRY_TW.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KTW1;
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_TW) && IntegrationConstant.COUNTRY_TW.equals(country)
                && IntegrationConstant.COMPANY.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KTW2;
        }
        if (!IntegrationConstant.COUNTRY_CN.equals(country)) {
            if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_TW)
                    && !IntegrationConstant.COUNTRY_TW.equals(country)
                    && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
                return IntegrationConstant.TAXCUXCODE_KTW3;
            }
        }
        if (Objects.equals(marketId, IntegrationConstant.MARKET_ID_TW) && IntegrationConstant.COUNTRY_CN.equals(country)
                && IntegrationConstant.INDIVIDUAL.equals(memberType)) {
            return IntegrationConstant.TAXCUXCODE_KTW4;
        }
        return IntegrationConstant.TAXCUXCODE_KCN1;
    }

    /**
     * 获取会员同步数据.
     *
     * @param batchSavePOSTBody
     *            会员同步数据请求body
     * @param member
     *            会员DTO
     * @return 会员同步数据
     */
    public IsgMemberSync getIsgMemberSync(BatchSavePOSTBody batchSavePOSTBody, Member member) {
        IRequest request = RequestHelper.newEmptyRequest();
        request.setLocale(BaseConstants.DEFAULT_LANG);

        IsgMemberSync isgMemberSync = new IsgMemberSync();
        isgMemberSync.setOperationType(IntegrationConstant.OPERATION_TYPE_SYNC); // 操作类型，
        isgMemberSync.setDealerNo(batchSavePOSTBody.getDealerNo()); // 卡号
        isgMemberSync.setDealerType(batchSavePOSTBody.getDealerType()); // 身份类型
        isgMemberSync.setDealerSubType(batchSavePOSTBody.getDealerSubType()); // 身份子類型
        isgMemberSync.setDealerPostCode(String.valueOf(batchSavePOSTBody.getDealerPostCode())); // 職級代碼
        isgMemberSync.setSponsorNo(batchSavePOSTBody.getSponsorNo()); // 推薦人卡號
        isgMemberSync.setDealerName(batchSavePOSTBody.getDealerName()); // 姓名
        if (batchSavePOSTBody.getRegisterSpouse()) {
            isgMemberSync.setRegisterSpouse(IntegrationConstant.YES); // 是否登記了夫妻聯名
        } else {
            isgMemberSync.setRegisterSpouse(IntegrationConstant.NO); // 是否登記了夫妻聯名
        }
        isgMemberSync.setTypeEffectiveDate(member.getApprovalDate()); // 資格開始日期
        isgMemberSync.setSaleOrgCode(batchSavePOSTBody.getSaleOrgCode()); // 所屬機構代號
        isgMemberSync.setSaleBranchNo(batchSavePOSTBody.getSaleBranchNo()); // 所屬分支機搆代號
        isgMemberSync.setSaleZoneNo(batchSavePOSTBody.getSaleZoneNo()); // 銷售片區代號
        isgMemberSync.setAppPeriod(batchSavePOSTBody.getAppPeriod()); // 申請辦卡月份
        isgMemberSync.setAppFirstSoNo(batchSavePOSTBody.getAppFirstSoNo()); // 申請首單單號
        if (batchSavePOSTBody.getAppForInternational()) {
            isgMemberSync.setAppForInternational(IntegrationConstant.YES); // 是否國際推薦
        } else {
            isgMemberSync.setAppForInternational(IntegrationConstant.NO); // 是否國際推薦
        }
        isgMemberSync.setSex(batchSavePOSTBody.getSex()); // 性別
        isgMemberSync.setEnglishName(batchSavePOSTBody.getEnglishName()); // 英文姓名
        isgMemberSync.setComments(batchSavePOSTBody.getComments()); // 備註
        isgMemberSync.setOpLockStatus(batchSavePOSTBody.getOpLockStatus()); // 業務鎖狀態
        isgMemberSync.setTaxCustCode(batchSavePOSTBody.getTaxCustCode()); // 計稅客戶代號
        isgMemberSync.setProcessStatus(IntegrationConstant.SUCCESS_STATUS); // 处理状态，
        // isgMemberSync.setProcessMessage(batchSavePOSTBody.getProcessMessage());
        // //处理信息
        return isgMemberSync;
    }

    /**
     * 获取会员同步个人信息行数据.
     *
     * @param gdealerPersonalInfo
     *            会员同步个人信息行数据请求body
     * @param member
     *            会员DTO
     * @return 会员同步个人信息行数据
     */
    public IsgMemberInfoSync getIsgMemberInfoSync(GdealerPersonalInfo gdealerPersonalInfo, Member member) {
        IsgMemberInfoSync isgMemberInfoSync = new IsgMemberInfoSync();
        isgMemberInfoSync.setDealerNo(gdealerPersonalInfo.getDealerNo()); // 卡號
        isgMemberInfoSync.setPrivacyFlag(gdealerPersonalInfo.getPrivacyFlag()); // 隱私標誌
        isgMemberInfoSync.setCertificateNationCode(gdealerPersonalInfo.getCertificateNationCode()); // 證件國家
        isgMemberInfoSync.setCertificateType(gdealerPersonalInfo.getCertificateType()); // 證件類型
        isgMemberInfoSync.setCertificateNo(gdealerPersonalInfo.getCertificateNo()); // 證件號碼
        isgMemberInfoSync.setCertificateMemo(gdealerPersonalInfo.getCertificateMemo()); // 證件備註
        isgMemberInfoSync.setCertificateAddrZipCode(gdealerPersonalInfo.getCertificateAddrZipCode()); // 證件地址郵編
        isgMemberInfoSync.setCertificateAddrProvince(gdealerPersonalInfo.getCertificateAddrProvince()); // 證件地址省份
        isgMemberInfoSync.setCertificateAddrCity(gdealerPersonalInfo.getCertificateAddrCity()); // 證件地址城市
        isgMemberInfoSync.setCertificateAddrTail01(gdealerPersonalInfo.getCertificateAddrTail01()); // 證件區內詳細地址
        // 01
        isgMemberInfoSync.setCertificateAddrTail02(gdealerPersonalInfo.getCertificateAddrTail02()); // 證件區內詳細地址
        // 02
        isgMemberInfoSync.setAddrNationCode(gdealerPersonalInfo.getAddrNationCode()); // 地址國家
        isgMemberInfoSync.setAddrZipCode(gdealerPersonalInfo.getAddrZipCode()); // 地址郵編
        isgMemberInfoSync.setAddrProvince(gdealerPersonalInfo.getAddrProvince()); // 地址省份
        isgMemberInfoSync.setAddrCity(gdealerPersonalInfo.getAddrCity()); // 地址城市
        isgMemberInfoSync.setAddrTail01(gdealerPersonalInfo.getAddrTail01()); // 區內詳細地址
        // 01
        isgMemberInfoSync.setAddrTail02(gdealerPersonalInfo.getAddrTail02()); // 區內詳細地址
        // 02
        if (gdealerPersonalInfo.getAddrEnabled()) {
            isgMemberInfoSync.setAddrEnabled(IntegrationConstant.FLAG_Y); // 位址是否有效
        } else {
            isgMemberInfoSync.setAddrEnabled(IntegrationConstant.FLAG_N); // 位址是否有效
        }
        isgMemberInfoSync.setAddrMemo(gdealerPersonalInfo.getAddrMemo()); // 位址備註資訊
        isgMemberInfoSync.setContactTele(gdealerPersonalInfo.getContactTele()); // 聯絡電話
        isgMemberInfoSync.setContactFaxNo(gdealerPersonalInfo.getContactFaxNo()); // 傳真號碼
        isgMemberInfoSync.setContactMobile(gdealerPersonalInfo.getContactMobile()); // 行動電話
        isgMemberInfoSync.setContactEmail(gdealerPersonalInfo.getContactEmail()); // 電子郵寄地址
        isgMemberInfoSync.setRace(gdealerPersonalInfo.getRace()); // 种族
        isgMemberInfoSync.setEducation(gdealerPersonalInfo.getEducation()); // 教育程度
        isgMemberInfoSync.setOccupation(gdealerPersonalInfo.getOccupation()); // 職業
        isgMemberInfoSync.setBirthday(gdealerPersonalInfo.getBirthday()); // 生日
        isgMemberInfoSync.setComments(gdealerPersonalInfo.getComments()); // 備註
        isgMemberInfoSync.setProcessStatus(IntegrationConstant.SUCCESS_STATUS); // 处理状态，
        // isgMemberInfoSync.setProcessMessage(gdealerPersonalInfo.getProcessMessage());
        // //处理信息

        return isgMemberInfoSync;
    }

    /**
     * 获取会员同步相关人信息行数据.
     *
     * @param gdealerSpouseInfo
     *            会员同步相关人信息行数据请求body
     * @param member
     *            会员DTO
     * @return 会员同步相关人信息行数据
     */
    public IsgMemberRelSync getIsgMemberRelSync(GdealerSpouseInfo gdealerSpouseInfo, Member member) {
        IsgMemberRelSync isgMemberRelSync = new IsgMemberRelSync();
        isgMemberRelSync.setDealerNo(gdealerSpouseInfo.getDealerNo()); // 卡號
        isgMemberRelSync.setAppDealerNo(gdealerSpouseInfo.getAppDealerNo()); // 申請經銷商號
        isgMemberRelSync.setAppPeriod(gdealerSpouseInfo.getAppPeriod()); // 申請月份
        isgMemberRelSync.setAppType(gdealerSpouseInfo.getAppType()); // 申請類型
        isgMemberRelSync.setSpouseFullName(gdealerSpouseInfo.getSpouseFullName()); // 家庭成員全稱
        isgMemberRelSync.setSpouseCertificateNationCode(gdealerSpouseInfo.getSpouseCertificateNationCode()); // 家庭成員證件國家
        isgMemberRelSync.setSpouseCertificateType(gdealerSpouseInfo.getSpouseCertificateType()); // 家庭成員證件類型
        isgMemberRelSync.setSpouseCertificateNo(gdealerSpouseInfo.getSpouseCertificateNo()); // 家庭成員證件號碼
        isgMemberRelSync.setSpouseSex(gdealerSpouseInfo.getSpouseSex()); // 家庭成員性別
        isgMemberRelSync.setSpouseMobile(gdealerSpouseInfo.getSpouseMobile()); // 家庭成員電話
        isgMemberRelSync.setSpouseTele(gdealerSpouseInfo.getSpouseTele()); // 家庭成員電話
        isgMemberRelSync.setSpouseRace(gdealerSpouseInfo.getSpouseRace()); // 配偶民族
        // isgMemberRelSync.setSpouseRefDealerNo(gdealerSpouseInfo.getSpouseRefDealerNo());
        // //配偶卡號來源
        isgMemberRelSync.setComments(gdealerSpouseInfo.getComments()); // 備註
        isgMemberRelSync.setProcessStatus(IntegrationConstant.SUCCESS_STATUS); // 处理状态，
        // isgMemberRelSync.setProcessMessage(gdealerSpouseInfo.getProcessMessage());
        // //处理信息

        return isgMemberRelSync;
    }
}
