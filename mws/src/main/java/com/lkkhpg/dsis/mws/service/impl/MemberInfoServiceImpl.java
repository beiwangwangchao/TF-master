/*
 *
 */

package com.lkkhpg.dsis.mws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmLocation;
import com.lkkhpg.dsis.common.config.mapper.SpmLocationMapper;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.mapper.MemAccountMapper;
import com.lkkhpg.dsis.common.member.mapper.MemSiteMapper;
import com.lkkhpg.dsis.mws.dto.MemberInfo;
import com.lkkhpg.dsis.mws.mapper.MemberInfoMapper;
import com.lkkhpg.dsis.mws.service.IMemberInfoService;
import com.lkkhpg.dsis.mws.service.IMemberSiteService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * mws会员信息Service接口实现.
 * 
 * @author guanghui.liu
 */
@Service
@Transactional
public class MemberInfoServiceImpl implements IMemberInfoService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private IMemberSiteService memberSiteService;

    @Autowired
    private MemSiteMapper memSiteMapper;

    @Autowired
    private SpmLocationMapper spmLocationMapper;

    @Autowired
    private MemAccountMapper memAccountMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public MemberInfo queryMemberInfo(IRequest request, Long memberId) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo = memberInfoMapper.selectByPrimaryKey(memberId);
        return memberInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateMemberInfo(IRequest request, MemberInfo memberInfo) {
        int v = memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
        // 调用MemberSiteService保存会员地点
        List<MemSite> sites = memberInfo.getMemSites();
        // account
        this.updateMemberAccountArchive(memberInfo.getMemberId());
        if (sites != null) {
            // home
            this.updateMemberHomeAddressArchive(memberInfo.getMemberId());
            memberSiteService.saveCtactMemSite(request, sites.get(0));
        } else {
            // home
            this.updateMemberHomeAddressArchive(memberInfo.getMemberId());
            // ctact
            this.updateMemberCtactAddressArchive(memberInfo.getMemberId());
        }
        return v;
    }

    @Override
    public MemberInfo queryRemBal(IRequest request, Long memberId) {
        return memberInfoMapper.queryRemBalByPrimaryKey(memberId);
    }

    @Override
    public int updateMemberLastUpdateDate(MemberInfo memberInfo) {
        return memberInfoMapper.updateMemberLastUpdateDate(memberInfo);
    }

    @Override
    public int updateMemberHomeAddressArchive(Long memberId) {
        Long locationId = memSiteMapper.selectLocationId(memberId, "HOME");
        if (locationId != null) {
            SpmLocation spmLocation = new SpmLocation();
            spmLocation.setLocationId(locationId);
            spmLocationMapper.updateAddressLastUpdateDate(spmLocation);
        }
        return -1;
    }

    @Override
    public int updateMemberCtactAddressArchive(Long memberId) {
        Long locationId = memSiteMapper.selectLocationId(memberId, "CTACT");
        if (locationId != null) {
            SpmLocation spmLocation = new SpmLocation();
            spmLocation.setLocationId(locationId);
            spmLocationMapper.updateAddressLastUpdateDate(spmLocation);
        }
        return -1;
    }

    @Override
    public int updateMemberAccountArchive(Long memberId) {
        Long accountId = memAccountMapper.selectAccountId(memberId);
        MemAccount memAccount = new MemAccount();
        memAccount.setAccountId(accountId);
        memAccountMapper.updateAccountLastUpdateDate(memAccount);
        return -1;
    }

}