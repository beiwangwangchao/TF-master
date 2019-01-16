/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.common.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.exception.CommSystemProfileException;
import com.lkkhpg.dsis.common.member.dto.MemberList;
import com.lkkhpg.dsis.common.member.mapper.MemberListMapper;
import com.lkkhpg.dsis.common.service.ICommMemberListService;
import com.lkkhpg.dsis.common.service.ISysEventService;
import com.lkkhpg.dsis.common.service.ISysMsMessageService;
import com.lkkhpg.dsis.common.service.IVoucherService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 导入会员列表临时表接口实现类.
 * 
 * @author mclin
 */
@Service
@Transactional
public class CommMemberListServiceImpl implements ICommMemberListService {

    @Autowired
    private MemberListMapper memberListMapper;
    @Autowired
    private ISysEventService sysEventService;
    @Autowired
    private ISysMsMessageService messageService;
    @Autowired
    private IVoucherService voucherService;

    @Override
    public Long validate(IRequest request, List<MemberList> list, String idType, Long mentionId, Long marketId)
            throws CommMemberException {
        if (idType.equals(MemberConstants.MEMIMPORT_TYPE_EVENT)) {
            return sysEventService.validate(request, list, idType, mentionId, marketId);
        } else if (idType.equals(MemberConstants.MEMIMPORT_TYPE_VOUCHER)) {
            return voucherService.validate(request, list, idType, mentionId, marketId);
        } else if (idType.equals(MemberConstants.MEMIMPORT_TYPE_MESSAGE)) {
            return messageService.validate(request, list, idType, mentionId, marketId);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importMembers(IRequest request, Long groupId, String groupType, Long mentionId, BigDecimal maxMember)
            throws CommSystemProfileException {
        if (groupType.equals(MemberConstants.MEMIMPORT_TYPE_EVENT)) {
            sysEventService.importMemebers(request, groupId, mentionId, maxMember); 
            // 删除临时会员表
            memberListMapper.deleteByGroupId(groupId);
            return groupId;
        } else if (groupType.equals(MemberConstants.MEMIMPORT_TYPE_VOUCHER)) {
            voucherService.importMemebers(request, groupId, mentionId);
            memberListMapper.deleteByGroupId(groupId);
            return groupId;
        } else if (groupType.equals(MemberConstants.MEMIMPORT_TYPE_MESSAGE)) {
            messageService.importMembers(request, groupId, mentionId);
            memberListMapper.deleteByGroupId(groupId);
            return groupId;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long deleteImport(IRequest request, Long groupId, Long mentionId) {
        memberListMapper.deleteByGroupId(groupId);
        return groupId;
    }

    @Override
    public List<MemberList> queryImport(IRequest request, Long groupId) {
        return memberListMapper.getAllMemberListByGroupId(groupId);
    }

    @Override
    public void saveReceiver(IRequest request, @StdWho MemberList memberList) {
        memberListMapper.insert(memberList);
    }
}
