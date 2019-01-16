/*
 *
 */
package com.lkkhpg.dsis.admin.config.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.config.exception.SystemProfileException;
import com.lkkhpg.dsis.admin.config.service.ISpmServiceCenterService;
import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenter;
import com.lkkhpg.dsis.common.config.dto.SpmServiceCenterAssign;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmServiceCenterAssignMapper;
import com.lkkhpg.dsis.common.config.mapper.SpmServiceCenterMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.service.IMessageService;

/**
 * 服务中心ServiceImpl.
 * 
 * @author hanrui.huang
 */
@Service
@Transactional
public class SpmServiceCenterServiceImpl implements ISpmServiceCenterService {

    private Logger logger = LoggerFactory.getLogger(SpmServiceCenterServiceImpl.class);

    @Autowired
    private SpmServiceCenterMapper spmServiceCenterMapper;
    @Autowired
    private SpmServiceCenterAssignMapper spmServiceCenterAssignMapper;
    @Autowired
    private SpmLocationMapper spmLocationMapper;
    @Autowired
    private ICommMemberService commMemberService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private SpmMarketMapper spmMarketMapper;

    @Override
    public void validator(IRequest request, List<SpmServiceCenter> spmServiceCenters) throws SystemProfileException {

        Member member = new Member();
        // 校验所管理的会员是否已分配
        for (SpmServiceCenter spmServiceCenter : spmServiceCenters) {
            // 校验code唯一性
            Integer result = spmServiceCenterMapper.queryServiceCenterByCode(spmServiceCenter);
            if (result > 0) {
                throw new SystemProfileException(SystemProfileException.MSG_ERROR_SPM_SC_CODE_REPEAT, null);
            }
            member.setMemberId(spmServiceCenter.getChargeMemberId());
            member.setMarketId(spmServiceCenter.getMarketId());
            List<Member> members1 = memberMapper.selectMemberServiceCenter(member);
            List<SpmServiceCenter> lists1 = spmServiceCenterMapper.queryServiceCenterByMember(spmServiceCenter);

            if (members1.isEmpty()) {
                if (lists1 != null && !lists1.isEmpty()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("The chargeMember already exists in a Service Center.ChargeMemberId : {}",
                                new Object[] { member.getMemberId() });
                        throw new SystemProfileException(
                                SystemProfileException.MSG_ERROR_SPM_CHARGE_MEMBER_ALREADY_EXIST, null);
                    }
                }
            }
            for (SpmServiceCenterAssign spmMember : spmServiceCenter.getSpmMembers()) {
                // 校验不能添加重复的会员

                // 校验会员添加的会员是否已分配
                member.setMemberId(spmMember.getMemberId());
                member.setMarketId(spmServiceCenter.getMarketId());
                if (DTOStatus.ADD.equals(spmMember.get__status())) {
                    List<Member> members2 = memberMapper.selectMemberServiceCenter(member);
                    if (members2.isEmpty()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("The member already exists in a service center.MemberId : {}",
                                    new Object[] { member.getMemberId() });
                            throw new SystemProfileException(
                                    SystemProfileException.MSG_ERROR_SPM_SC_MEMBER_ALREADY_EXIST_SC, null);
                        }
                    }
                }
                if (DTOStatus.UPDATE.equals(spmMember.get__status())) {
                    // 1.判断是否是当前Service Center，是-忽略，否-则下一步
                    if (spmServiceCenterAssignMapper.judgeMemberInSC(spmMember) == null) {
                        // 2.判断是否是已经分配，是-则提示已分配；否-则下一步
                        List<Member> members3 = memberMapper.selectMemberServiceCenter(member);
                        if (members3.isEmpty()) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("The member already exists in a service center.MemberId : {}",
                                        new Object[] { member.getMemberId() });
                                throw new SystemProfileException(
                                        SystemProfileException.MSG_ERROR_SPM_SC_MEMBER_ALREADY_EXIST_SC, null);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<SpmServiceCenter> queryServiceCenter(IRequest request, SpmServiceCenter spmServiceCenter, int page,
            int pageSize) {
        PageHelper.startPage(page, pageSize);
        String status = spmServiceCenter.getStatus();
        List<String> statusList = new ArrayList<String>();
        if (status != null) {
            Collections.addAll(statusList, status.split(";"));
        } else {
            statusList = null;
        }
        List<SpmServiceCenter> spmServiceCenters = spmServiceCenterMapper.queryServiceCenter(spmServiceCenter,
                statusList);
        return spmServiceCenters;
    }

    @Override
    public List<SpmServiceCenter> queryServiceCenterWithMember(IRequest request, SpmServiceCenter spmServiceCenter,
            int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SpmServiceCenter> spmServiceCenters = spmServiceCenterMapper.queryServiceCenterForOrder(spmServiceCenter);
        for (SpmServiceCenter serviceCenter : spmServiceCenters) {
            Member member = memberMapper.selectByPrimaryKey(serviceCenter.getChargeMemberId());
            serviceCenter.setMember(member);
        }
        return spmServiceCenters;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmServiceCenter> saveSpmServiceCenter(IRequest requestContext,
            @StdWho List<SpmServiceCenter> spmServiceCenters) throws SystemProfileException {
        // 校验
        self().validator(requestContext, spmServiceCenters);

        for (SpmServiceCenter spmServiceCenter : spmServiceCenters) {
            Long serviceCenterId = spmServiceCenter.getServiceCenterId();
            if (serviceCenterId == null) {
                SpmLocation spmLocation = spmServiceCenter.getSpmLocation();
                SpmLocation spmLocationAdd = spmLocation;
                spmLocationAdd.setEnabledFlag(SystemProfileConstants.YES);
                spmLocationMapper.insertSelective(spmLocationAdd);
                Long locationId = spmLocationAdd.getLocationId();
                spmServiceCenter.setLocationId(locationId);
                spmServiceCenterMapper.insert(spmServiceCenter);
                List<SpmServiceCenterAssign> spmMembers = spmServiceCenter.getSpmMembers();
                for (SpmServiceCenterAssign member : spmMembers) {
                    member.setServiceCenterId(spmServiceCenter.getServiceCenterId());
                    spmServiceCenterAssignMapper.insert(member);
                }
            } else {
                Long locationId = spmServiceCenter.getLocationId();
                SpmLocation updateLocation = spmServiceCenter.getSpmLocation();
                updateLocation.setLocationId(locationId);
                spmLocationMapper.updateByPrimaryKeySelective(updateLocation);
                spmServiceCenterMapper.updateByPrimaryKey(spmServiceCenter);
                List<SpmServiceCenterAssign> members = spmServiceCenter.getSpmMembers();
                for (SpmServiceCenterAssign member : members) {
                    if (DTOStatus.ADD.equals(member.get__status())) {
                        member.setServiceCenterId(serviceCenterId);
                        spmServiceCenterAssignMapper.insert(member);
                    }
                    if (DTOStatus.UPDATE.equals(member.get__status())) {
                        member.setServiceCenterId(serviceCenterId);
                        spmServiceCenterAssignMapper.updateByPrimaryKey(member);
                    }
                }
            }
        }
        return spmServiceCenters;
    }

    @Override
    public SpmServiceCenter getSpmServiceCenter(IRequest requestContext, SpmServiceCenter spmServiceCenter) {
        SpmServiceCenter serviceCenter = spmServiceCenterMapper.selectByPrimaryKey(spmServiceCenter);
        // 根据国家获取地址
        SpmLocation spmLocation = spmLocationMapper.selectLocationById(serviceCenter.getLocationId());
        if (spmLocation != null) {
            serviceCenter.setSpmLocation(spmLocation);
            serviceCenter.setLocationName(spmLocation.getAddressReturn());
        }
        return serviceCenter;
    }

    @Override
    public List<SpmServiceCenterAssign> getSpmServiceCenterMembers(IRequest requestContext, Long serviceCenterId) {
        return spmServiceCenterAssignMapper.getSpmMembersByCenterId(serviceCenterId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmServiceCenterAssign> deleteSpmServiceCenterMembers(IRequest requestContext,
            List<SpmServiceCenterAssign> spmServiceCenterAssigns) {
        for (SpmServiceCenterAssign spmServiceCenterAssign : spmServiceCenterAssigns) {
            spmServiceCenterAssignMapper.deleteByPrimaryKey(spmServiceCenterAssign.getAssignId());
        }
        return spmServiceCenterAssigns;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmServiceCenter> submitServiceCenter(IRequest requestContext,
            @StdWho List<SpmServiceCenter> spmServiceCenters) throws SystemProfileException {
        // 校验
        self().validator(requestContext, spmServiceCenters);

        for (SpmServiceCenter spmServiceCenter : spmServiceCenters) {
            Long serviceCenterId = spmServiceCenter.getServiceCenterId();
            if (serviceCenterId == null) {
                SpmLocation spmLocation = spmServiceCenter.getSpmLocation();
                SpmLocation spmLocationAdd = spmLocation;
                spmLocationAdd.setEnabledFlag(SystemProfileConstants.YES);
                spmLocationMapper.insertSelective(spmLocationAdd);
                Long locationId = spmLocationAdd.getLocationId();
                spmServiceCenter.setLocationId(locationId);
                spmServiceCenter.setStatus(SystemProfileConstants.APPROVE_STATUS_ALING);
                spmServiceCenterMapper.insert(spmServiceCenter);
                List<SpmServiceCenterAssign> spmMembers = spmServiceCenter.getSpmMembers();
                for (SpmServiceCenterAssign member : spmMembers) {
                    member.setServiceCenterId(spmServiceCenter.getServiceCenterId());
                    spmServiceCenterAssignMapper.insert(member);
                }
            } else {
                Long locationId = spmServiceCenter.getLocationId();
                SpmLocation updateLocation = spmServiceCenter.getSpmLocation();
                updateLocation.setLocationId(locationId);
                spmLocationMapper.updateByPrimaryKeySelective(updateLocation);
                spmServiceCenter.setStatus(SystemProfileConstants.APPROVE_STATUS_ALING);
                spmServiceCenterMapper.updateByPrimaryKey(spmServiceCenter);
                List<SpmServiceCenterAssign> members = spmServiceCenter.getSpmMembers();
                for (SpmServiceCenterAssign member : members) {
                    if (DTOStatus.ADD.equals(member.get__status())) {
                        member.setServiceCenterId(serviceCenterId);
                        spmServiceCenterAssignMapper.insert(member);
                    }
                    if (DTOStatus.UPDATE.equals(member.get__status())) {
                        member.setServiceCenterId(serviceCenterId);
                        spmServiceCenterAssignMapper.updateByPrimaryKey(member);
                    }
                }
            }
        }
        return spmServiceCenters;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long approveServiceCenter(IRequest request, Long serviceCenterId, Long salesOrgId) throws Exception {
        Long marketId = request.getAttribute(SystemProfileConstants.MARKET_ID);
        spmServiceCenterMapper.approveServiceCenter(serviceCenterId);
        SpmServiceCenter ssc = new SpmServiceCenter();
        ssc.setServiceCenterId(serviceCenterId);
        ssc.setSalesOrgId(salesOrgId);
        SpmServiceCenter serviceCenter = spmServiceCenterMapper.selectByPrimaryKey(ssc);
        Member member = commMemberService.getMember(request, serviceCenter.getChargeMemberId());
        // 调用接口发送短信通知负责会员
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        receiver.setMessageAddress(member.getAreaCode() + member.getPhoneNo());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiverlist.add(receiver);
        // 设置邮件模板里的数据
        Map<String, Object> data = new HashMap<String, Object>();
        taskExecutor.execute(() -> {
            try {
                messageService.sendSmsMessage(-1L, marketId, "PHONE_SERVICE_CENTER_APPROVE", null, data, receiverlist);
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("send Sms Error", e);
                }
            }
        });
        return serviceCenterId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long rejectServiceCenter(IRequest requestContext, Long serviceCenterId) {
        spmServiceCenterMapper.rejectServiceCenter(serviceCenterId);
        return serviceCenterId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<SpmServiceCenter> closeServiceCenter(IRequest requestContext,
            @StdWho List<SpmServiceCenter> spmServiceCenters) throws SystemProfileException {
        // 校验
        self().validator(requestContext, spmServiceCenters);

        for (SpmServiceCenter spmServiceCenter : spmServiceCenters) {
            Long serviceCenterId = spmServiceCenter.getServiceCenterId();
            Long locationId = spmServiceCenter.getLocationId();
            SpmLocation updateLocation = spmServiceCenter.getSpmLocation();
            updateLocation.setLocationId(locationId);
            spmLocationMapper.updateByPrimaryKeySelective(updateLocation);
            spmServiceCenter.setStatus(SystemProfileConstants.APPROVE_STATUS_CLOSD);
            spmServiceCenterMapper.closeServiceCenter(spmServiceCenter);
            List<SpmServiceCenterAssign> members = spmServiceCenter.getSpmMembers();
            for (SpmServiceCenterAssign member : members) {
                if (DTOStatus.ADD.equals(member.get__status())) {
                    member.setServiceCenterId(serviceCenterId);
                    spmServiceCenterAssignMapper.insert(member);
                }
                if (DTOStatus.UPDATE.equals(member.get__status())) {
                    member.setServiceCenterId(serviceCenterId);
                    spmServiceCenterAssignMapper.updateByPrimaryKey(member);
                }
            }
        }
        return spmServiceCenters;
    }

    @Override
    public SpmMarket selectBySalesOrgId(IRequest requestContext, Long salesOrgId) {
        return spmMarketMapper.selectBySalesOrgId(salesOrgId);
    }

}
