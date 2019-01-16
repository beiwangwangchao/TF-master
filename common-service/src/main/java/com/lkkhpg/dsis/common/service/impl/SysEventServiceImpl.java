/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.member.mapper.MemberListMapper;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ISysEventService;
import com.lkkhpg.dsis.common.system.dto.SysEvent;
import com.lkkhpg.dsis.common.system.dto.SysEventAssign;
import com.lkkhpg.dsis.common.system.mapper.SysEventAssignMapper;
import com.lkkhpg.dsis.common.system.mapper.SysEventMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.DTOStatus;
import com.lkkhpg.dsis.platform.dto.system.DocSequence;
import com.lkkhpg.dsis.platform.service.IDocSequenceService;

/**
 * 事件管理service.
 * 
 * @author wangc
 *
 */
@Service
@Transactional
public class SysEventServiceImpl implements ISysEventService {

    @Autowired
    private SysEventMapper sysEventMapper;
    @Autowired
    private SysEventAssignMapper sysEventAssignMapper;
    @Autowired
    private IDocSequenceService docSequenceService;
    @Autowired
    private ICommMemberService commMemberService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberListMapper memberListMapper;

    public static final String DOC_TYPE = "SYS_EVENT";

    public static final String DOC_PREFIX = "EV";

    public static final Long INIT_SEQUENCE = 10000L;

    @Override
    public List<SysEvent> queryEvents(IRequest request, SysEvent event, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return sysEventMapper.queryEvents(event);
    }

    @Override
    public List<SysEvent> queryEventsForWms(IRequest request, SysEvent event, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return sysEventMapper.queryEventsForWms(event);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEvent> saveSysEvent(IRequest request, List<SysEvent> events) throws CommSystemProfileException {
        if (events == null || events.isEmpty()) {
            return null;
        }
        for (SysEvent event : events) {
            String eventNumber = event.getEventNumber();
            List<SysEventAssign> eventAssigns = event.getEventAssigns();
            if (eventNumber == null || "".equals(eventNumber)) {
                // 设置参与人数，仅在新增时
                if (eventAssigns != null) {
                    event.setParticipants(BigDecimal.valueOf(eventAssigns.size()));
                } else {
                    event.setParticipants(BigDecimal.ZERO);
                }
                // 校验参与人数
                BigDecimal maxMember = event.getMaxMember();
                BigDecimal participants = event.getParticipants();
                if (maxMember != null && participants != null) {
                    if (maxMember.compareTo(participants) == -1) {
                        throw new CommSystemProfileException(
                                CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT, null);
                    }
                }
                event.setEventNumber(docSequenceService.getSequence(request,
                        new DocSequence(DOC_TYPE, DOC_PREFIX, null, null, null, null), DOC_PREFIX,
                        SystemProfileConstants.CODE_LENGTH_FIVE, INIT_SEQUENCE));
                sysEventMapper.insert(event);
                if (eventAssigns != null) {
                    for (SysEventAssign eventAssign : eventAssigns) {
                        if (DTOStatus.ADD.equals(eventAssign.get__status())) {
                            eventAssign.setEventId(event.getEventId());
                            eventAssign.setAssignType(SystemProfileConstants.EVENT_MEMBER_TYPE);
                            eventAssign.setAssignValue(Long.toString(eventAssign.getMemberId()));
                            eventAssign.setAttendance(new BigDecimal(1));
                            sysEventAssignMapper.insert(eventAssign);
                        }
                        if (DTOStatus.UPDATE.equals(eventAssign.get__status())) {
                            eventAssign.setAssignValue(Long.toString(eventAssign.getMemberId()));
                            sysEventAssignMapper.updateByPrimaryKey(eventAssign);
                        }
                    }
                }
            } else {
                // 校验参与人数
                BigDecimal maxMember = event.getMaxMember();
                BigDecimal participants = event.getParticipants();
                if (maxMember != null && participants != null) {
                    if (maxMember.compareTo(participants) == -1) {
                        throw new CommSystemProfileException(
                                CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT, null);
                    }
                }
                sysEventMapper.updateByPrimaryKey(event);
                if (eventAssigns != null) {
                    for (SysEventAssign eventAssign : eventAssigns) {
                        if (DTOStatus.ADD.equals(eventAssign.get__status())) {
                            eventAssign.setEventId(event.getEventId());
                            eventAssign.setAssignType(SystemProfileConstants.EVENT_MEMBER_TYPE);
                            eventAssign.setAssignValue(Long.toString(eventAssign.getMemberId()));
                            eventAssign.setAttendance(new BigDecimal(1));
                            sysEventAssignMapper.insert(eventAssign);
                        }
                        if (DTOStatus.UPDATE.equals(eventAssign.get__status())) {
                            eventAssign.setAssignValue(Long.toString(eventAssign.getMemberId()));
                            sysEventAssignMapper.updateByPrimaryKey(eventAssign);
                        }
                    }
                }

            }
        }
        return events;
    }

    @Override
    public SysEvent getEventById(IRequest request, Long eventId) {
        if (eventId == null) {
            SysEvent event = new SysEvent();
            event.setEventId(sysEventMapper.getEventId());
            event.setParticipants(BigDecimal.ZERO);
            event.setStatus(SystemProfileConstants.EVENT_STATUS_ACTV);
            return event;
        } else {
            return sysEventMapper.selectByPrimaryKey(eventId);
        }

    }

    @Override
    public List<SysEventAssign> getMemsByEventId(IRequest request, Long eventId) {
        return sysEventAssignMapper.queryByEventId(eventId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEventAssign> deleteMember(IRequest requestContext, List<SysEventAssign> eventAssigns) {
        // 更新活动参与人数
        Long eventId = eventAssigns.get(0).getEventId();
        int deleteNumber = eventAssigns.size();
        SysEvent eventForLock = sysEventMapper.selectByPrimaryKeyForLock(eventId);
        Long participants = eventForLock.getParticipants().longValue();
        Long totalMember = participants - deleteNumber;
        eventForLock.setParticipants(BigDecimal.valueOf(totalMember));
        sysEventMapper.updateByPrimaryKey(eventForLock);

        for (SysEventAssign sysEventAssign : eventAssigns) {
            sysEventAssignMapper.deleteByPrimaryKey(sysEventAssign.getEventAssignId());
        }
        return eventAssigns;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long joinEvent(IRequest request, Long eventId, Long memberId, BigDecimal participants)
            throws CommSystemProfileException {
        SysEvent event = sysEventMapper.selectByPrimaryKeyForLock(eventId);
        // 校验是否已发布
        String status = event.getStatus();
        if (!SystemProfileConstants.EVENT_STATUS_PUB.equals(status)) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_EVENT_STATUS_PUB, null);
        }
        BigDecimal maxMember = event.getMaxMember();
        // 校验参与人数
        if (maxMember != null) {
            if (maxMember.compareTo(participants.add(event.getParticipants())) == -1) {
                throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT,
                        null);
            }
        }
        // 会员是否属于活动的市场
        Member eventMember = commMemberService.getMember(request, memberId);
        if (!event.getMarketId().equals(eventMember.getMarketId())) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_MEMBER_EVENT_NOT_SAME_MARKET,
                    null);
        }
        // 会员是否参加过此活动
        SysEventAssign member = sysEventAssignMapper.getByMember(eventId, memberId.toString());
        if (member != null) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_JOIN_EVENT_REPEAT, null);
        }
        event.setParticipants(participants.add(event.getParticipants()));
        sysEventMapper.updateByPrimaryKey(event);
        // 新增分配会员
        SysEventAssign assign = new SysEventAssign();
        assign.setAssignType(SystemProfileConstants.EVENT_MEMBER_TYPE);
        assign.setAssignValue(memberId.toString());
        assign.setEventId(eventId);
        assign.setAttendance(participants);
        sysEventAssignMapper.insert(assign);
        return memberId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEvent> invalidEvent(IRequest request, List<SysEvent> events) throws CommSystemProfileException {
        for (SysEvent event : events) {
            // 校验参与人数
            BigDecimal maxMember = event.getMaxMember();
            BigDecimal participants = event.getParticipants();
            if (maxMember != null && participants != null) {
                if (maxMember.compareTo(participants) == -1) {
                    throw new CommSystemProfileException(
                            CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT, null);
                }
            }
            event.setStatus(SystemProfileConstants.EVENT_STATUS_VOID);
            sysEventMapper.updateByPrimaryKey(event);
        }
        return events;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEvent> publishEvent(IRequest request, List<SysEvent> events) throws CommSystemProfileException {
        for (SysEvent event : events) {
            // 活动保密性为公开才能发布
            String eventSecret = event.getEventSecret();
            if (!SystemProfileConstants.EVENT_SECRET_PUB.equals(eventSecret)) {
                throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_EVENT_SECRET_PUB, null);
            }
            // 校验参与人数
            BigDecimal maxMember = event.getMaxMember();
            BigDecimal participants = event.getParticipants();
            if (maxMember != null && participants != null) {
                if (maxMember.compareTo(participants) == -1) {
                    throw new CommSystemProfileException(
                            CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT, null);
                }
            }
            event.setStatus(SystemProfileConstants.EVENT_STATUS_PUB);
            sysEventMapper.updateByPrimaryKey(event);
        }
        return events;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long closeEvent(IRequest request, Long eventId) {
        sysEventMapper.closeEvent(eventId);
        return eventId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long cancelEventForWms(IRequest request, Long eventId, Long memberId) throws CommSystemProfileException {
        SysEvent indexEvent = sysEventMapper.selectByPrimaryKeyForLock(eventId);
        // 校验是否已发布
        String status = indexEvent.getStatus();
        if (!SystemProfileConstants.EVENT_STATUS_PUB.equals(status)) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_EVENT_STATUS_PUB, null);
        }
        // 会员是否参加过此活动
        SysEventAssign member = sysEventAssignMapper.getByMember(eventId, memberId.toString());
        if (member == null) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_JOIN_EVENT_REPEAT, null);
        }
        // 会员是否属于活动的市场
        //Member eventMember = commMemberService.getMember(request, memberId);
        Member eventMember = memberMapper.selectMarketId(memberId);
        if (!indexEvent.getMarketId().equals(eventMember.getMarketId())) {
            throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_MEMBER_EVENT_NOT_SAME_MARKET,
                    null);
        }

        SysEventAssign assign = sysEventAssignMapper.getByMember(eventId, memberId.toString());
        BigDecimal attendance = assign.getAttendance();
        sysEventAssignMapper.deleteByPrimaryKey(assign.getEventAssignId());
        SysEvent event = sysEventMapper.selectByPrimaryKeyForLock(eventId);
        // 更新活动参加人数
        event.setParticipants(event.getParticipants().subtract(attendance));
        sysEventMapper.updateByPrimaryKey(event);
        return memberId;
    }

    @Override
    public Long getAttendanceByMemberIdAndEventId(IRequest request, Long eventId, String memberId) {
        return sysEventAssignMapper.selectAttendanceByEventIdAndMemberId(eventId, memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException {
        Long groupId = memberListMapper.getNextGroupId();
        // 验证导入的会员
        for (MemberList memberList : list) {
            MemberList memList = new MemberList();
            String code = memberList.getMemberCode();
            Member member = memberMapper.selectMembersByMemberCode(code);
            memList.setGroupId(groupId);
            memList.setGroupType(MemberConstants.MEMIMPORT_TYPE_EVENT);
            memList.setMemberCode(code);
            // 会员是否存在
            if (member == null) {
                memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_ID_FAIL);
            } else {
                Long memMarketId = member.getMarketId();
                String status = member.getStatus();
                SysEventAssign assignMember = sysEventAssignMapper.getByMember(mentionId,
                        member.getMemberId().toString());
                memList.setMemberId(member.getMemberId());
                // 会员所属市场与活动市场不相同
                // 会员状态，终止／自动终止／无效／待变更无法导入
                // 会员是否参加过此活动
                if (memMarketId == null || !memMarketId.equals(marketId)) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_MARKET_FAIL);
                } else if (MemberConstants.MEMBER_STATUS_TERMINATED.equals(status)
                        || MemberConstants.MEMBER_STATUS_AUTO_TERMINATED.equals(status)
                        || MemberConstants.MEM_STATUS_CHANGE_AUTO_TERMINATE.equals(status)
                        || MemberConstants.MEMBER_STATUS_WCHG.equals(status)
                        || MemberConstants.MEM_STATUS_CHANGE_RJECT.equals(status)
                        || MemberConstants.MEMBER_STATUS_SUSPENDED.equals(status)
                        || MemberConstants.MEMBER_STATUS_PENDING.equals(status)
                        || MemberConstants.MEMBER_STATUS_NEW.equals(status)) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_STATUS_FAIL);
                } else if (assignMember != null) {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_DISABLE);
                    memList.setReasonCode(MemberConstants.MEMIMPORT_MEMBER_IMPORT_EVENT_EXIST);
                } else {
                    memList.setValidateFlag(MemberConstants.MEMIMPORT_FLAG_ENABLE);
                }
            }
            memberListMapper.insert(memList);
        }
        return groupId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importMemebers(IRequest request, Long groupId, Long mentionId, BigDecimal maxMember)
            throws CommSystemProfileException {
        // 根据groupid查处会员，写进活动会员关系表
        List<MemberList> memberLists = memberListMapper.getMemberListByGroupId(groupId);
        for (MemberList member : memberLists) {
            Long memberId = member.getMemberId();
            SysEventAssign assign = new SysEventAssign();
            assign.setEventId(mentionId);
            assign.setAssignValue(memberId.toString());
            assign.setAssignType(SystemProfileConstants.EVENT_MEMBER_TYPE);
            assign.setAttendance(BigDecimal.ONE);
            sysEventAssignMapper.insert(assign);
        }
        // 活动状态必须为新建，有效
        // 校验参与人数
        BigDecimal participants = new BigDecimal(memberLists.size());
        SysEvent event = sysEventMapper.selectByPrimaryKeyForLock(mentionId);
        if (maxMember != null) {
            if (maxMember.compareTo(participants.add(event.getParticipants())) == -1) {
                throw new CommSystemProfileException(CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT,
                        null);
            }
        }
        event.setMaxMember(maxMember);
        event.setParticipants(participants.add(event.getParticipants()));
        sysEventMapper.updateByPrimaryKey(event);
        return groupId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEventAssign> addMembers(IRequest request, List<SysEventAssign> assigns, Long eventId, Long maxMember)
            throws CommSystemProfileException {
        List<SysEventAssign> addAssigns = new ArrayList<SysEventAssign>();
        for (SysEventAssign assign : assigns) {
            if (DTOStatus.ADD.equals(assign.get__status())) {
                addAssigns.add(assign);
            }
        }
        if (addAssigns.size() == 0) {
            return null;
        }
        Long addNumber = (long) addAssigns.size();
        Long totalPaticipants = 0L;
        // 更新活动的参与人数
        SysEvent eventForLock = sysEventMapper.selectByPrimaryKeyForLock(eventId);
        if (eventForLock != null) {
            Long participants = eventForLock.getParticipants().longValue();
            totalPaticipants = participants + addNumber;
            if (maxMember != null) {
                if (totalPaticipants > maxMember) {
                    throw new CommSystemProfileException(
                            CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT, null);
                }
            }
            eventForLock.setParticipants(BigDecimal.valueOf(totalPaticipants));
            sysEventMapper.updateByPrimaryKey(eventForLock);
        } else {
            totalPaticipants = addNumber;
            if (maxMember != null) {
                if (totalPaticipants > maxMember) {
                    throw new CommSystemProfileException(
                            CommSystemProfileException.MSG_ERROR_PARTICIPANTS_MORE_THAN_LIMIT, null);
                }
            }
        }
        // 新增分配会员
        for (SysEventAssign eventAssign : addAssigns) {
            eventAssign.setEventId(eventId);
            eventAssign.setAssignType(SystemProfileConstants.EVENT_MEMBER_TYPE);
            eventAssign.setAssignValue(Long.toString(eventAssign.getMemberId()));
            eventAssign.setAttendance(new BigDecimal(1));
            sysEventAssignMapper.insert(eventAssign);
        }
        return assigns;
    }

    @Override
    public Long getTravelCountByMember(IRequest request, Long memberId) {
        return sysEventAssignMapper.getTravelCountByMember(memberId);
    }
}
