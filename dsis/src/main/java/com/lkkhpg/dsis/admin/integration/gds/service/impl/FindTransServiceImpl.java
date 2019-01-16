/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChange;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindTransService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.member.constant.MemberConstants;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.integration.gds.resource.dealers.model.DealersPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.CodeValue;
import com.lkkhpg.dsis.platform.mapper.system.CodeValueMapper;
import com.lkkhpg.dsis.platform.service.ICodeService;

/**
 * 下载转入转出会员处理清单接口实现类(定时被动).
 * 
 * @author mclin
 */
@Service
@Transactional
public class FindTransServiceImpl implements IFindTransService {

    private Logger logger = LoggerFactory.getLogger(FindTransServiceImpl.class);

    @Autowired
    private IsgMarketChangeMapper isgMarketChangeMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private IDistributorCallBackService distributorCallBackService;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    
    @Autowired
    private CodeValueMapper codeValueMapper;
    
    @Autowired
    private ICodeService codeService;
    
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void findTransOutList(String adviseNo, String orgCode, List<ResultsGETResponse> response,
            Exception exception) {
        // 插入接口表(转出)
        List<NotifyPOSTBody> list = insertOutInterface(adviseNo, orgCode, response, exception);

        // String gdsOrgCode = gdsUtilService.getGdsOrgCode(orgCode);
        try {
            if (exception == null) {
                // 更新或插入业务表（只要下载成功则更新业务表,不回滚）
                updateOutMemberMarket(response);
                updateOutInterface(adviseNo, IntegrationConstant.SUCCESS_STATUS, null);

                // 调用GDS接口上传
                gdsService.transOut(adviseNo, orgCode, list);
            }
        } catch (Exception e) {
            // 整个过程中出现异常则写错误日志并往外抛异常
            if (logger.isErrorEnabled()) {
                logger.error("findTransOut Error:", e.getStackTrace()[0]+"---->"+e.getMessage());
            }
            // 更新接口表错误消息（必然插表不回滚）
            updateOutInterface(adviseNo, IntegrationConstant.ERROR_STATUS, e.getStackTrace()[0]+"---->"+e.getMessage());
            // 异常代码和格式待定
//            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void findTransInList(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response,
            Exception exception) {
        // 插入接口表(转出)
        List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> list = insertInInterface(
                adviseNo, orgCode, response, exception);

        // String gdsOrgCode = gdsUtilService.getGdsOrgCode(orgCode);
        try {
            if (exception == null) {
                // 更新或插入业务表（只要下载成功则更新业务表,不回滚）
                updateInMemberMarket(response);
                updateInInterface(adviseNo, IntegrationConstant.SUCCESS_STATUS, null);
                // 调用GDS接口上传
                gdsService.transIn(adviseNo, orgCode, list);
            }
        } catch (Exception e) {
            // 整个过程中出现异常则写错误日志并往外抛异常
            if (logger.isErrorEnabled()) {
                logger.error("findTransIn:", e.getStackTrace()[0]+"---->"+e.getMessage());
            }
            // 更新接口表错误消息（必然插表不回滚）
            updateInInterface(adviseNo, IntegrationConstant.ERROR_STATUS, e.getStackTrace()[0]+"---->"+e.getMessage());
            // 异常代码和格式待定
//            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }

    /*
     * ******************** ** 转出 ** ********************
     */
    // 插入接口表（必然插表不回滚）
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    List<NotifyPOSTBody> insertOutInterface(String adviseNo, String orgCode, List<ResultsGETResponse> response,
            Exception exception) {
        try {
            // IGdsServiceDataProvider执行无返回异常
            List<NotifyPOSTBody> list = new ArrayList<>();
            if (exception == null) {
                for (ResultsGETResponse result : response) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    String newMarketCode = gdsUtilService.getOrgCode(result.getNewSaleOrgCode());
                    map.put("marketCode", newMarketCode);
                    map.put("memberCode",result.getDealerNo());
                    Member member = memberMapper.selectMemberByMarketAndMemberCode(map);
                    if (member != null) { //如果会员不为空,表示已经处理过该记录,则不作插入操作,只构造上传的数据
                        NotifyPOSTBody postBody = new NotifyPOSTBody();
                        postBody.setId(result.getId());
                        list.add(postBody);
                        continue;
                    }
                    IsgMarketChange imc = new IsgMarketChange();
                    // 操作类型
                    imc.setOperationType(IntegrationConstant.OUT_MARKET_CHANGE);
                    // 通知号
                    imc.setAdviseNo(adviseNo);
                    // 下载成功,上传标志为N
                    imc.setUploadFlag(IntegrationConstant.DOWNLOAD_STATUS);

                    // 记录ID
                    imc.setGdsId(result.getId());
                    // 卡号
                    imc.setDealerNo(result.getDealerNo());
                    // 原市场
                    imc.setOldSaleOrgCode(result.getOldSaleOrgCode());
                    // 新市场
                    imc.setNewSaleOrgCode(result.getNewSaleOrgCode());
                    // 原身份类型
                    imc.setNewDealerType(result.getNewDealerType());
                    // 新身份类型
                    imc.setNewDealerSubType(result.getNewDealerSubType());
                    // 姓名
                    imc.setAppFullName(result.getAppFullName());
                    // 姓
                    imc.setAppLastName(null);
                    // 名
                    imc.setAppFirstName(null);
                    // 证件类型
                    imc.setAppCertificateType(result.getAppCertificateType());
                    // 证件号
                    imc.setAppCertificateNo(result.getAppCertificateNo());

                    // 插入接口表
                    isgMarketChangeMapper.insertSelective(imc);
                    NotifyPOSTBody postBody = new NotifyPOSTBody();
                    postBody.setId(imc.getGdsId());
                    list.add(postBody);
                }
            } else {
                // 写异常日志
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                IsgMarketChange imc = new IsgMarketChange();
                // 通知号和异常信息插入一条数据到接口表
                imc.setAdviseNo(adviseNo);
                imc.setProcessStatus(IntegrationConstant.ERROR_STATUS);
                imc.setProcessMessage(exception.getMessage());
                imc.setUploadFlag(IntegrationConstant.DOWNLOAD_STATUS);
                isgMarketChangeMapper.insertSelective(imc);
            }
            return list;
        } catch (Exception e) {
            // 整个过程中出现异常则写错误日志并往外抛异常
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            // 异常代码和格式待定
            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }

    // 更新或插入业务表(转出,由接口异常决定是否回滚）
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    void updateOutMemberMarket(List<ResultsGETResponse> response) throws IntegrationException {
        for (ResultsGETResponse rel : response) {
            List<IsgMarketChange> list = isgMarketChangeMapper.querySuccessByGdsId(rel.getId());
            if (logger.isDebugEnabled()) {
                logger.debug("长度：{}", list.isEmpty());
            }
            if (list.isEmpty()) {
                String orgCode = gdsUtilService.getOrgCode(rel.getNewSaleOrgCode());
                if (orgCode != null) {
                    // 更新会员新市场前失效其市场下的优惠券及Ecoupon --by linyuheng
                    IRequest iRequest = RequestHelper.newEmptyRequest();
                    iRequest.setAccountId(-1L);
                    iRequest.setLocale(BaseConstants.DEFAULT_LANG);
                    Member member = memberMapper.selectMembersByMemberCode(rel.getDealerNo());
                    voucherService.invalidMemberVoucher(iRequest, member);
                    // 更新新市场
                    memberMapper.updateMarketByMemberCode(rel.getDealerNo(), orgCode);
                    member.setMarketCode(orgCode);
                    // 调用callback
                    distributorCallBackService.callbackDistributor(iRequest, member,
                            IntegrationConstant.ACTION_TYPE_UPDATE);
                } else {
                    throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING, null);
                }
            }
        }
    }

    // 更新接口表状态和消息字段（转出,必然提交不回滚）
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    void updateOutInterface(String adviseNo, String status, String message) {
        IsgMarketChange imc = new IsgMarketChange();
        imc.setOperationType(IntegrationConstant.OUT_MARKET_CHANGE);
        imc.setProcessStatus(status);
        imc.setProcessMessage(message);
        imc.setAdviseNo(adviseNo);
        isgMarketChangeMapper.updateProcessStatusByAdviseNo(imc);
    };

    /*
     * ******************** ** 转入 ** ********************
     */
    // 插入接口表（必然插表不回滚）
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> insertInInterface(
            String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response,
            Exception exception) {
        try {
            // IGdsServiceDataProvider执行无返回异常
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> list = new ArrayList<>();
            if (exception == null) {
                for (com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse result : response) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    String newMarketCode = gdsUtilService.getOrgCode(result.getNewSaleOrgCode());
                    map.put("marketCode", newMarketCode);
                    map.put("memberCode",result.getDealerNo());
                    Member member = memberMapper.selectMemberByMarketAndMemberCode(map);
                    if (member != null) { //如果会员不为空,表示已经处理过该记录,则不作插入操作,只构造上传的数据
                        com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody postBody = new com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody();
                        postBody.setId(result.getId());
                        list.add(postBody);
                        continue;
                    }
                    IsgMarketChange imc = new IsgMarketChange();
                    // 操作类型
                    imc.setOperationType(IntegrationConstant.IN_MARKET_CHANGE);
                    // 通知号
                    imc.setAdviseNo(adviseNo);
                    // 下载成功,上传标志为N
                    imc.setUploadFlag(IntegrationConstant.DOWNLOAD_STATUS);

                    // 记录ID
                    imc.setGdsId(result.getId());
                    // 卡号
                    imc.setDealerNo(result.getDealerNo());
                    // 原市场
                    imc.setOldSaleOrgCode(result.getOldSaleOrgCode());
                    // 新市场
                    imc.setNewSaleOrgCode(result.getNewSaleOrgCode());
                    // 原身份类型
                    imc.setNewDealerType(result.getNewDealerType());
                    // 新身份类型
                    imc.setNewDealerSubType(result.getNewDealerSubType());
                    // 姓名
                    imc.setAppFullName(result.getAppFullName());
                    // 姓
                    imc.setAppLastName(null);
                    // 名
                    imc.setAppFirstName(null);
                    // 证件类型
                    imc.setAppCertificateType(result.getAppCertificateType());
                    // 证件号
                    imc.setAppCertificateNo(result.getAppCertificateNo());

                    // 插入接口表
                    isgMarketChangeMapper.insertSelective(imc);
                    com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody postBody = new com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody();
                    postBody.setId(imc.getGdsId());
                    list.add(postBody);
                }
            } else {
                // 写异常日志
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                IsgMarketChange imc = new IsgMarketChange();
                // 通知号和异常信息插入一条数据到接口表
                imc.setAdviseNo(adviseNo);
                imc.setProcessStatus(IntegrationConstant.ERROR_STATUS);
                imc.setProcessMessage(exception.getMessage());
                imc.setUploadFlag(IntegrationConstant.DOWNLOAD_STATUS);
                isgMarketChangeMapper.insertSelective(imc);
            }
            return list;
        } catch (Exception e) {
            // 整个过程中出现异常则写错误日志并往外抛异常
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            // 异常代码和格式待定
            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }

    // 更新或插入业务表（转入,由接口异常决定是否回滚）
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    void updateInMemberMarket(
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response)
                    throws IntegrationException {
        for (com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse rel : response) {
            List<IsgMarketChange> list = isgMarketChangeMapper.querySuccessByGdsId(rel.getId());
            if (logger.isDebugEnabled()) {
                logger.debug("长度：{}", list.isEmpty());
            }
            if (list.isEmpty()) {
                String orgCode = gdsUtilService.getOrgCode(rel.getNewSaleOrgCode());

                if (orgCode != null) {
                    // 更新会员新市场前失效其市场下的优惠券及Ecoupon --by linyuheng
                    IRequest iRequest = RequestHelper.newEmptyRequest();
                    iRequest.setAccountId(-1L);
                    iRequest.setLocale(BaseConstants.DEFAULT_LANG);
                    Member member = memberMapper.selectMembersByMemberCode(rel.getDealerNo());
                    // 会员不存在的时候创建，存在的时候更新
                    if (member != null) {
                        voucherService.invalidMemberVoucher(iRequest, member);
                        // 更新新市场
                        memberMapper.updateMarketByMemberCode(rel.getDealerNo(), orgCode);
                        member.setMarketCode(orgCode);
                        // 调用callback
                        distributorCallBackService.callbackDistributor(iRequest, member,
                                IntegrationConstant.ACTION_TYPE_UPDATE);
                    } else {
                        Member mm = new Member();
                        // 会员编号
                        mm.setMemberCode(rel.getDealerNo());
                        // 市场ID
                        SpmMarket sm = spmMarketMapper.selectMarketWithoutEnabled(orgCode);
                        mm.setMarketId(sm.getMarketId());
                        // 会员角色
                        if (StringUtils.isNotEmpty(rel.getNewDealerType())) {
                            mm.setMemberRole(codeValueMapper.getMemberRoleCodeByDescription(rel.getNewDealerType()));
                        }
                        // 会员名字
                        if (StringUtils.isNotEmpty(rel.getAppFirstName())) {
                            mm.setChineseFirstName(rel.getAppFirstName());
                        }
                        if (StringUtils.isNotEmpty(rel.getAppLastName())) {
                            mm.setChineseLastName(rel.getAppLastName());
                        }
                        // mm.setChineseName(rel.getAppFullName());
                        if (StringUtils.isNotEmpty(rel.getAppCertificateType())) {
                            List<CodeValue> cvs = codeValueMapper.queryMemberTypeCodeByDescription(rel.getAppCertificateType());
                            if (false == cvs.isEmpty()) {
                                // 不为空
                                if (1 < cvs.size()) {
                                    // 不止一个
                                    for (CodeValue cv : cvs) {
                                        if (MemberConstants.MM_TYPE_IDV.equals(cv.getValue())) {
                                            mm.setMemberType(cv.getValue());
                                            break;
                                        }
                                        mm.setMemberType(cv.getValue());
                                    }
                                } else {
                                    mm.setMemberType(cvs.get(0).getValue());
                                }
                            } else {
                                mm.setMemberType(MemberConstants.MM_TYPE_IDV);
                            }
                        } else {
                            throw new IntegrationException(IntegrationException.MSG_ERROR_MEMBER_TYPE_NOT_NULL, null);
                        }
                        // 证件类型
                        mm.setIdType(MemberConstants.MM_CERTIFICATE_TYPE_IC);
                        // 证件编码
                        if (StringUtils.isNotEmpty(rel.getAppCertificateNo())) {
                            mm.setIdNumber(rel.getAppCertificateNo());
                        }
                        // 推荐人No.
                        DealersPOSTBody sponsor = rel.getSponsor();
                        if (sponsor == null) {
                            if (logger.isErrorEnabled()) {
                                logger.error("The sponsor is not exist");
                            }
                        } else {
                            if (StringUtils.isNotEmpty(sponsor.getDealerNo())) {
                                mm.setSponsorNo(sponsor.getDealerNo());
                            }
                        }

                        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
                        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
                        try {
                            // 批准日期
                            if (StringUtils.isNotEmpty(rel.getPostEffectiveDate())) {
                                mm.setApprovalDate(format1.parse(rel.getPostEffectiveDate()));
                            }
                            // 终止日期
                            if (StringUtils.isNotEmpty(rel.getPostInactiveDate())) {
                                mm.setTerminationDate(format1.parse(rel.getPostInactiveDate()));
                            }
                            // 加入日期
                            if (StringUtils.isNotEmpty(rel.getAppPeriod()) && rel.getAppPeriod().length() == 6) {
                                mm.setJointDate(format2.parse(rel.getAppPeriod()+"01"));
                            } else if (StringUtils.isNotEmpty(rel.getAppPeriod())) {
                                mm.setJointDate(format2.parse(rel.getAppPeriod()));
                            }
                        } catch (Exception e) {
                            throw new IntegrationException(IntegrationException.MSG_ERROR_DATE_STR_FORMAT, null);
                        }
                        // 国家
                        mm.setCountry(codeValueMapper.getDefaultCountryByMarketCode(orgCode));
                        // 同步标识
                        mm.setSyncFlag(BaseConstants.YES);
                        mm.setDappSyncFlag(BaseConstants.NO);
                        // 会员状态
                        mm.setStatus(MemberConstants.MEMBER_STATUS_ACTIVE);
                        // 领取奖金方式
                        mm.setBonusRcvMethod(MemberConstants.MM_BONUS_RECV_TYPE_BANK);
                        mm.setLastUpdatedBy(-1L);
                        // 公司
                        mm.setCompanyId(sm.getCompanyId());
                        // jointSite
                        String jointSite = codeService.getCodeMeaningByValue(iRequest, 
                                com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant.DAPP_DEFAULT_JOINT_SITE, orgCode);
                        if (jointSite == null) {
                            throw new IntegrationException(IntegrationException.MSG_ERROR_SALES_ORG_NO_EXIST, null);
                        }
                        SpmSalesOrganization record = new SpmSalesOrganization();
                        record.setMarketId(sm.getMarketId());
                        record.setCode(jointSite);
                        List<SpmSalesOrganization> spmSalesOrganizations = spmSalesOrganizationMapper
                                .getSalesOrgByCodeAndMarket(record);
                        if (spmSalesOrganizations != null && spmSalesOrganizations.size() > 0) {
                            mm.setJointSite(String.valueOf(spmSalesOrganizations.get(0).getSalesOrgId()));
                        }
                        memberMapper.insertSelective(mm);
                        // 调用callback
                        distributorCallBackService.callbackDistributor(iRequest, mm,
                                IntegrationConstant.ACTION_TYPE_ADD);
                    }
                } else {
                    throw new IntegrationException(IntegrationException.MSG_ERROR_ORG_CODE_NO_MAPPING, null);
                }
            }
        }
    }

    // 更新接口表状态和消息字段（转入,必然提交不回滚）
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    void updateInInterface(String adviseNo, String status, String message) {
        IsgMarketChange imc = new IsgMarketChange();
        imc.setOperationType(IntegrationConstant.IN_MARKET_CHANGE);
        imc.setProcessStatus(status);
        imc.setProcessMessage(message);
        imc.setAdviseNo(adviseNo);
        isgMarketChangeMapper.updateProcessStatusByAdviseNo(imc);
    };
}
