/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyMoveLineService;
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyStatusQueryService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.ISponsorVerifyService;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.admin.member.service.IUpstreamChangeService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.MemOverseasSponsor;
import com.lkkhpg.dsis.common.member.dto.MemUpstreamChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemOverseasSponsorMapper;
import com.lkkhpg.dsis.common.member.mapper.MemUpstreamChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员上线变更接口实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class UpstreamChangeServiceImpl implements IUpstreamChangeService {

    private Logger logger = LoggerFactory.getLogger(UpstreamChangeServiceImpl.class);

    @Autowired
    private MemUpstreamChangeMapper upstreamChangeMapper;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ISponsorVerifyService sponsorVerifyService;

    @Autowired
    private IApplyMoveLineService applyMoveLineService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IApplyStatusQueryService applyStatusQueryService;

    @Autowired
    private MemOverseasSponsorMapper memOverseasSponsorMapper;

    /**
     * 查询变更上线记录.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            会员上线变更DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 会员变更上线记录列表
     */
    @Override
    public List<MemUpstreamChange> queryUpstreamChange(IRequest request, MemUpstreamChange upstreamChange, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<MemUpstreamChange> upstreamChanges = upstreamChangeMapper.queryUpstreamChange(upstreamChange);

        for (int i = 0; i < upstreamChanges.size(); i++) {
            MemUpstreamChange upstreamChange_tmp = upstreamChanges.get(i);
            String chineseName = upstreamChange_tmp.getChineseName();
            String englishName = upstreamChange_tmp.getEnglishName();
            if (englishName != null && chineseName != null) {
                upstreamChange_tmp.setMemberName(englishName + "/" + chineseName);
            } else if (englishName != null) {
                upstreamChange_tmp.setMemberName(englishName);
            } else {
                upstreamChange_tmp.setMemberName(chineseName);
            }
            upstreamChanges.set(i, upstreamChange_tmp);
        }

        return upstreamChanges;
    }

    /**
     * 查询原上线.
     * 
     * @param request
     *            请求上下文
     * @param memUpstreamChange
     *            会员变更上线DTO
     * @return 会员变更上线列表
     * @throws MemberException
     *             会员异常
     */
    @Override
    public List<MemUpstreamChange> queryOldUpstream(IRequest request, MemUpstreamChange memUpstreamChange)
            throws MemberException {
        // 通过memberCode获得member
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("memberCode", memUpstreamChange.getMemberCode());
        queryMap.put("marketId", memUpstreamChange.getMarketId());
        Member member = memberMapper.selectValidMemberByMemberCode(queryMap);
        // 构造空的会员变更上线DTO集合
        ArrayList<MemUpstreamChange> memUpstreamChanges = new ArrayList<MemUpstreamChange>();
        if (member != null) {
            return self().checkValidity(request, memUpstreamChange, member, memUpstreamChanges);
        }
        return memUpstreamChanges;
    }

    /**
     * 提交变更上线信息.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            会员上线变更DTO
     * @return
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口统一异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String submitUpstreamChange(IRequest request, @StdWho MemUpstreamChange upstreamChange)
            throws MemberException, IntegrationException {
        String memberCode = memberMapper.selectByPrimaryKey(upstreamChange.getMemberId()).getMemberCode();
        // 提交前检验新上线有效性
        self().checkNewSponsorValidity(request, upstreamChange);
        // 获取新上线ID
        Member toMember = memberMapper.selectMembersByMemberCode(upstreamChange.getToUpMemberCode());
        if (toMember != null) {
            Long toUpMemberId = toMember.getMemberId();
            upstreamChange.setToUpMemberId(toUpMemberId);
        } else {
            upstreamChange.setToOverseasSponsor(upstreamChange.getToUpMemberCode());
        }
        Member fromMember = memberMapper.selectByPrimaryKey(upstreamChange.getFromUpMemberId());
        if (fromMember == null) {
            upstreamChange.setFromOverseasSponsor(upstreamChange.getFromUpMemberCode());
            upstreamChange.setFromUpMemberId(null);
        }
        // 获取当前日期
        Date applyDate = new Date();
        upstreamChange.setApplyDate(applyDate);
        // 设置状态为审核中
        upstreamChange.setApprovalStatus(MemberConstants.SYS_REVIEW_STATUS_ING);
        String appNo = gdsUtilService.getAppNo(request);
        upstreamChange.setAppNo(appNo);
        upstreamChange.setMarketId(request.getAttribute(SystemProfileConstants.MARKET_ID));
        upstreamChangeMapper.insertSelective(upstreamChange);
        return memberCode;
        // try {
        // 调用会员移线申请接口
        // applyMoveLineService.applyMoveLine(request, memberCode, appNo,
        // upstreamChange.getToUpMemberCode(), appNo,
        // upstreamChange.getRemark(), orgCode);
        // } catch (Exception e) {
        // if (logger.isErrorEnabled()) {
        // logger.error(e.getMessage(), e);
        // }
        // }
    }

    /**
     * 检查新上线有效性.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            变更上线DTO
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkNewSponsorValidity(IRequest request, MemUpstreamChange upstreamChange)
            throws MemberException, IntegrationException {
        String memberCode = upstreamChange.getMemberCode();
        Member member = memberMapper.selectMembersByMemberCode(memberCode);
        // 会员不存在时抛出异常
        if (member == null) {
            throw new MemberException(MemberException.MSG_ERROR_INVALID_SPONSOR, null);
        }
        String toUpstreamCode = upstreamChange.getToUpMemberCode();
        Member toUpMember = memberMapper.selectMembersByMemberCode(toUpstreamCode);
        // 新上线不存在会员表时
        if (toUpMember == null) {
            String orgCode = gdsUtilService.getCurrentOrgCode(request);
            String gdsOrgCode = gdsUtilService.getGdsOrgCode(orgCode);
            // 调用推荐人即时鉴别接口
            sponsorVerifyService.sponsorVerify(request, toUpstreamCode, gdsOrgCode);
        } else {
            Long toUpMeberSponsorId = toUpMember.getSponsorId();
            // 新上线为会员的下线时抛出异常
            if (toUpMeberSponsorId != null && toUpMeberSponsorId.equals(member.getMemberId())) {
                throw new MemberException(MemberException.MSG_ERROR_INVALID_SPONSOR, null);
            }
            String fromUpMemberCode = upstreamChange.getFromUpMemberCode();
            // Long toUpMemberId = toUpMember.getMemberId();
            // boolean newUpstreamStatus =
            // memberService.checkNewUpstreamStatus(request, toUpstreamCode);
            // 新上线等于会员,或者新上线等于原上线,或者新上线状态不为激活时抛出异常
            if ((toUpstreamCode.equals(memberCode) || toUpstreamCode.equals(fromUpMemberCode))) {
                throw new MemberException(MemberException.MSG_ERROR_INVALID_SPONSOR, null);
            }
        }
    }

    @Override
    public List<MemUpstreamChange> checkValidity(IRequest request, MemUpstreamChange memUpstreamChange, Member member,
            List<MemUpstreamChange> memUpstreamChanges) throws MemberException {
        String fromUpMemberCode = "";
        // 获得当前会员的所有变更记录
        List<MemUpstreamChange> upstreamChanges = upstreamChangeMapper
                .queryUpstreamChangeByMemberId(member.getMemberId());
        // 遍历所有的变更记录
        for (MemUpstreamChange upstreamChange : upstreamChanges) {
            // 如果该会员存在正在审核中的变更记录，则直接返回空集合
            if (MemberConstants.SYS_REVIEW_STATUS_ING.equals(upstreamChange.getApprovalStatus())) {
                // return memUpstreamChanges;
                if (logger.isDebugEnabled()) {
                    logger.debug(MemberException.MSG_ERROR_UPSTREAM_CHG_EXIST);
                }
                throw new MemberException(MemberException.MSG_ERROR_UPSTREAM_CHG_EXIST, null);
            }
        }
        // 会员为激活状态时
        if (MemberConstants.MEMBER_STATUS_ACTIVE.equals(member.getStatus())) {
            Member member_sponsorid = memberService.querySponsorId(request, member.getMemberId());
            /*
             * Member sponsor_memberId =
             * memberMapper.selectByPrimaryKey(member_sponsorid.getSponsorId());
             * if (sponsor_memberId != null) { fromUpMemberCode =
             * sponsor_memberId.getMemberCode(); } else { fromUpMemberCode = "";
             * }
             */
            fromUpMemberCode = member_sponsorid.getSponsorNo();
            MemUpstreamChange upstreamChange = new MemUpstreamChange();
            // upstreamChange.setFromUpMemberId(member_sponsorid.getSponsorId());

            /* Mclin修改 */
            if (member_sponsorid.getSponsorId() == null) {
                // 海外推荐人
                MemOverseasSponsor sponsor = memOverseasSponsorMapper
                        .getOverseasSponsorByNo(member_sponsorid.getSponsorNo());
                if (sponsor != null) {
                    upstreamChange.setFromUpMemberId(sponsor.getSponsorId());
                }
            } else {
                upstreamChange.setFromUpMemberId(member_sponsorid.getSponsorId());
            }
            upstreamChange.setFromUpMemberCode(fromUpMemberCode);
            upstreamChange.setMemberCode(member.getMemberCode());
            upstreamChange.setMemberId(member.getMemberId());
            memUpstreamChanges.add(upstreamChange);
        }
        return memUpstreamChanges;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyMoveLine(IRequest request, String memberCode, MemUpstreamChange upstreamChange)
            throws IntegrationException {
        String orgCode = gdsUtilService.getCurrentOrgCode(request);
        applyMoveLineService.applyMoveLine(request, memberCode, upstreamChange.getAppNo(),
                upstreamChange.getToUpMemberCode(), upstreamChange.getAppNo(), upstreamChange.getRemark(), orgCode);
    }

    @Override
    public void synToGds(IRequest request, List<MemUpstreamChange> memUpstreamChanges) throws IntegrationException {
        String orgCode = gdsUtilService.getCurrentOrgCode(request);
        for (MemUpstreamChange memUpstreamChange : memUpstreamChanges) {
            applyStatusQueryService.applyStatusQuery(request, orgCode, memUpstreamChange.getAppNo(),
                    IntegrationConstant.APPLY_TYPE_UPSTREAMCHANGE);
        }
    }

}
