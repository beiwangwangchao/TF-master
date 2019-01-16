/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.member.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorCallBackService;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IAppEligibleSuspendService;
import com.lkkhpg.dsis.admin.integration.gds.service.IApplyStatusQueryService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemStatusChangeService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemStatusChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemStatusChangeMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.integration.gds.service.impl.GdsSwitch;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员状态变更Service接口实现类.
 * 
 * @author yuchuan.zeng@hand-china.com
 */
@Service
@Transactional
public class MemStatusChangeServiceImpl implements IMemStatusChangeService {

    @Autowired
    private MemStatusChangeMapper statusChangeMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IApplyStatusQueryService applyStatusQueryService;

    @Autowired
    private IAppEligibleSuspendService appEligibleSuspendService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IDistributorCallBackService distributorCallBackService;

    @Autowired
    private GdsSwitch gdsSwitch;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MemStatusChange> queryApplyDateAndMemberId(IRequest request, MemStatusChange memStatusChange, int page,
            int pagesize) {
        PageHelper.startPage(page, pagesize);
        memStatusChange.checkSortName(); // 把property 的值转化成对应表的column名
        List<MemStatusChange> lists = statusChangeMapper.selectByApplyDateAndmemberId(memStatusChange);
        for (MemStatusChange m : lists) {
            String memberName = "";
            if (StringUtils.isNotEmpty(m.getEnglishName())) {
                memberName += m.getEnglishName();
                if (StringUtils.isNotEmpty(m.getChineseName())) {
                    memberName += "/" + m.getChineseName();
                }
            } else {
                if (StringUtils.isNotEmpty(m.getChineseName())) {
                    memberName += m.getChineseName();
                }
            }
            m.setMemberName(memberName);
        }
        return lists;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MemStatusChange> saveMemStatusChanges(IRequest request, List<MemStatusChange> memStatusChanges)
            throws MemberException, IntegrationException {
        for (MemStatusChange memStatusChange : memStatusChanges) {
            self().saveMemStatusChange(request, memStatusChange);
        }
        return memStatusChanges;
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public MemStatusChange saveMemStatusChange(IRequest request, @StdWho MemStatusChange memStatusChange)
            throws MemberException, IntegrationException {
        String appNo = gdsUtilService.getAppNo(request);
        if (memStatusChange.getChangeId() == null || memStatusChange.getChangeId() == 0) { // (主键是否为null或者0)
            /*
             * 没有主键，添加功能
             */
            Member member = memberMapper.selectByPK(memStatusChange.getMemberId());
            if (member == null) {
                throw new MemberException(MemberException.MSG_ERROR_MEMBER_CODE_EXIST, new Object[] {});
            } else {
                String beforeStatus = member.getStatus();
                String applyStatus = memStatusChange.getOperationType();
                boolean flag = false;
                if (MemberConstants.MEMBER_STATUS_ACTIVE.equals(beforeStatus)) {
                    if (MemberConstants.MEM_STATUS_CHANGE_SUSPEND.equals(applyStatus)
                            || MemberConstants.MEM_STATUS_CHANGE_TERMINATE.equals(applyStatus)) {
                        flag = true;
                    }
                } else if (MemberConstants.MEMBER_STATUS_SUSPENDED.equals(beforeStatus)) {
                    if (MemberConstants.MEM_STATUS_CHANGE_REACTIVE.equals(applyStatus)) {
                        flag = true;
                    }
                }
                if (flag) {
                    Date nowDate = new Date();
                    memStatusChange.setMarketId(member.getMarketId());
                    memStatusChange.setAppNo(appNo);
                    memStatusChange.setApplyDate(nowDate);
                    if (MemberConstants.MEM_STATUS_CHANGE_REACTIVE.equals(memStatusChange.getOperationType())
                            || MemberConstants.MEM_STATUS_CHANGE_SUSPEND.equals(memStatusChange.getOperationType())) {
                        memStatusChange.setTerminateDate(null);
                        memStatusChange.setApprovalStatus(MemberConstants.SYS_REVIEW_STATUS_YES);
                        memStatusChange.setApprovalDate(nowDate);
                    } else if (MemberConstants.MEM_STATUS_CHANGE_TERMINATE.equals(memStatusChange.getOperationType())) {
                        //校验终止日期范围
                        Date today = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(today);
                        calendar.add(Calendar.MONTH, -1);
                        calendar.set(Calendar.DATE, 1);
                        Date lastMonthDate = calendar.getTime();
                        Date terminateDate = memStatusChange.getTerminateDate();
                        if (terminateDate.getTime() < lastMonthDate.getTime()
                                || terminateDate.getTime() > today.getTime()) {
                            throw new MemberException(MemberException.MSG_ERROR_TERMINATE_DATE_INVALID, new Object[] {});
                        }
                        memStatusChange.setApprovalStatus(MemberConstants.SYS_REVIEW_STATUS_ING);
                    }
                    memStatusChange.setMarketId(member.getMarketId());
                    int tempflag = statusChangeMapper.insertSelective(memStatusChange);
                    if (tempflag != 0) {
                        if (MemberConstants.MEM_STATUS_CHANGE_REACTIVE.equals(memStatusChange.getOperationType())) {
                            member.setStatus(MemberConstants.MEMBER_STATUS_ACTIVE);

                            memberMapper.updateStatusByMemberId(member);
                            distributorCallBackService.callbackDistributor(request, member,
                                    IntegrationConstant.ACTION_TYPE_UPDATE);
                        }
                        if (MemberConstants.MEM_STATUS_CHANGE_SUSPEND.equals(memStatusChange.getOperationType())) {
                            member.setStatus(MemberConstants.MEMBER_STATUS_SUSPENDED);

                            memberMapper.updateStatusByMemberId(member);
                            distributorCallBackService.callbackDistributor(request, member,
                                    IntegrationConstant.ACTION_TYPE_UPDATE);
                        }

                        // 终止会员不用马上更改会员状态，由GDS接口更改状态
                        if (MemberConstants.MEM_STATUS_CHANGE_TERMINATE.equals(memStatusChange.getOperationType())) {
                            // 将memStatusChange传到GDS，需要在GDS中进行审核；审核完成后，审核状态以及审核日期从GDS传到DSIS
                            member.setStatus(MemberConstants.MEMBER_STATUS_PENDING);
                            // 调用会员停权申请接口
                            if (!gdsSwitch.isSwitchFlag()) {
                                throw new IntegrationException(IntegrationException.MSG_ERROR_GDS_IS_SHUTDOWN, null);
                            }
                            SimpleDateFormat date_format = new SimpleDateFormat("yyyyMM");
                            Date terminateDate = memStatusChange.getTerminateDate();
                            String appPeriod = date_format.format(terminateDate);
                            String orgCode = gdsUtilService.getCurrentOrgCode(request);
                            try {
                                appEligibleSuspendService.appEligibleSuspend(request, member.getMemberCode(), appNo,
                                        appNo, appPeriod, memStatusChange.getRemark(), orgCode);
                            } catch (Exception e) {
                                throw new IntegrationException(IntegrationException.MSG_ERROR_GDS_IS_SHUTDOWN,
                                        new Object[] { ((IntegrationException) e).getMessage() });
                            }

                        }
                    }
                } else {
                    throw new MemberException(MemberException.MSG_WARNING_SYS_STATUSCHANGE_ILLEGAL, new Object[] {});
                }
            }
        } else {
            /*
             * 修改功能，实际就是审核流程
             */
            memStatusChange.setApprovalDate(new Date());
            statusChangeMapper.updateByPrimaryKeySelective(memStatusChange);
        }
        return memStatusChange;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMemStatusChangeForJob(IRequest request, MemStatusChange memStatusChange) {
        if (memStatusChange.getChangeId() == null || memStatusChange.getChangeId() == 0) { // (主键是否为null或者0)
            /*
             * 没有主键，添加功能
             */
            memStatusChange.setCreationDate(new Date());
            memStatusChange.setLastUpdateDate(new Date());
            int tempflag = statusChangeMapper.insertSelective(memStatusChange);
            return tempflag != 0;
        } else {
            /*
             * 修改功能，实际就是审核流程
             */
            memStatusChange.setApprovalDate(new Date());
            memStatusChange.setLastUpdateDate(new Date());
            int tempflag = statusChangeMapper.updateByPrimaryKeySelective(memStatusChange);
            return tempflag != 0;
        }
    }

    @Override
    public void synToGds(IRequest iRequest, List<MemStatusChange> memStatusChanges) throws IntegrationException {
        String orgCode = gdsUtilService.getCurrentOrgCode(iRequest);
        for (MemStatusChange memStatusChange : memStatusChanges) {
            applyStatusQueryService.applyStatusQuery(iRequest, orgCode, memStatusChange.getAppNo(),
                    IntegrationConstant.APPLY_TYPE_STATUSCHANGE);
        }
    }

    public boolean validRecord(IRequest request, MemStatusChange memStatusChange) throws MemberException {
        long record = statusChangeMapper.validRecord(memStatusChange);
        if (record > 0) {
            return false;
        }
        return true;
    }

}
