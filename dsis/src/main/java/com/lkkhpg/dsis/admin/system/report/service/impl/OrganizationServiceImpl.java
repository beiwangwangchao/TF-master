/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.system.report.service.impl;

import java.util.List;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.system.report.service.OrganizationService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;
import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;
import com.lkkhpg.dsis.common.system.report.mapper.GdsMeDealerTreeMapper;
import com.lkkhpg.dsis.common.system.report.mapper.OmkRtlSalaryBalanceMapper;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 实现层.
 * @author HuangJiaJing
 *
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private GdsMeDealerTreeMapper metreeMapper;
    
    @Autowired
    private OmkRtlSalaryBalanceMapper rtltreeMapper;
    
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Override
    public List<GdsMeDealerTree> queryMeTree(IRequest request, GdsMeDealerTree tree) {
        //Long marketId = request.getAttribute(SystemProfileConstants.MARKET_ID);
        //SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(marketId);
        String salesOrgCode = metreeMapper.querySalesOrgCode();
        String dealerNo = metreeMapper.queryByDealerNo(tree.getMemberNo());
        tree.setSalesOrgCode(salesOrgCode);
        tree.setMarketCode(dealerNo);
        List<GdsMeDealerTree> list = metreeMapper.queryGdsMealerTree(tree);
        for (GdsMeDealerTree gdsMeDealerTree : list) {
            String memberCode = gdsMeDealerTree.getDealerNo();
            Member member = memberMapper.selectMembersByMemberCode(memberCode);
            if (MemberConstants.MM_ROLE_VIP.equals(member.getMemberRole())) {
                gdsMeDealerTree.setDealerNo(memberCode + "*");
            }
        }
        return list;
    }

    @Override
    public List<SpmSalesOrganization> queryOrganization(Long marketId) {
        List<SpmSalesOrganization>list2 =spmSalesOrganizationMapper.queryOrganization(marketId);
        return list2;
    }

    @Override
    public List<SpmSalesOrganization> selectOrganization(IRequest request, SpmSalesOrganization marketId) {
        List<SpmSalesOrganization> list1 =spmSalesOrganizationMapper.selectOrganization(marketId);
        System.out.println(list1.get(0).getSalesOrgId());

        return list1;
    }

    @Override
    public List<OmkRtlSalaryBalance> queryRtlTree(IRequest request, OmkRtlSalaryBalance tree) {
        //Long marketId = request.getAttribute(SystemProfileConstants.MARKET_ID);
        //SpmMarket spmMarket = spmMarketMapper.selectByPrimaryKey(marketId);
        String dealerNo = metreeMapper.queryByDealerNo(tree.getMemberNo());
        tree.setMarketCode(dealerNo);
        List<OmkRtlSalaryBalance> list = rtltreeMapper.queryRtlSalaryBalance(tree);
        for (OmkRtlSalaryBalance omkRtlSalaryBalance : list) {
            String memberCode = omkRtlSalaryBalance.getDealerNo();
            Member member = memberMapper.selectMembersByMemberCode(memberCode);
            if (MemberConstants.MM_ROLE_VIP.equals(member.getMemberRole())) {
                omkRtlSalaryBalance.setDealerNo(memberCode + "*");
            }
        }
        return list;
    }

    

}
