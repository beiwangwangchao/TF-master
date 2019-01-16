/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;
import com.lkkhpg.dsis.common.report.mapper.GdsSalaryMasterMapper;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;
import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;
import com.lkkhpg.dsis.common.system.report.mapper.GdsMeDealerTreeMapper;
import com.lkkhpg.dsis.common.system.report.mapper.OmkRtlSalaryBalanceMapper;
import com.lkkhpg.dsis.mws.dto.MyTeam;
import com.lkkhpg.dsis.mws.mapper.MyTeamMapper;
import com.lkkhpg.dsis.mws.service.IMemberTeamService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 会员-我的的团队服务接口实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
public class MemberTeamServiceImpl implements IMemberTeamService {

    private final Logger logger = LoggerFactory.getLogger(MemberTeamServiceImpl.class);

    @Autowired
    private MyTeamMapper myTeamMapper;
    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    @Autowired
    private ICommMemberService commMemberService;
    @Autowired
    private OmkRtlSalaryBalanceMapper rtltreeMapper;
    @Autowired
    private GdsMeDealerTreeMapper gdsMeDealerTreeMapper;
    @Autowired
    private GdsSalaryMasterMapper gdsSalaryMasterMapper;
    @Autowired
    private GdsMeDealerTreeMapper metreeMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<MyTeam> selectTeamByMemberId(IRequest request, Long memberId) {
        List<MyTeam> list = myTeamMapper.selectTeamByMemberId(memberId);
        return list;
    }

    @Override
    public List<MyTeam> queryDatePeriod(IRequest request, String memberCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberCode", memberCode);
        List<MyTeam> list = myTeamMapper.queryDatePeriod(map);
        return list;
    }

    @Override
    public List<MyTeam> query(IRequest request, Long memberId, String memberCode) {
        if (memberId == null) {
            return myTeamMapper.selectByMemberCode(memberCode);
        }
        return myTeamMapper.selectTeamByMemberId(memberId);
    }

    @Override
    public List<OmkRtlSalaryBalance> getMemberTree(IRequest request, OmkRtlSalaryBalance orsb) {
        // Member member = memberMapper.selectByPK(memberId);
        /*
         * Integer lev = 2; // 排除下线并列显示和排除本身 List<Member> list = commMemberService.getOmkDownLines(request, memberCode,
         * lev, excludeSelf);
         */
        String dealerNo = metreeMapper.queryByDealerNo(request.getAttribute(Member.FIELD_MEMBER_CODE));
        orsb.setMarketCode(dealerNo);
        List<OmkRtlSalaryBalance> list = rtltreeMapper.queryRtlSalaryBalance(orsb);
        return list;
    }

    @Override
    public List<SpmPeriod> queryBonusDetail(IRequest request, Long marketId, String period) {
        SpmPeriod spmPeriod = new SpmPeriod();
        spmPeriod.setOrgId(marketId);
        spmPeriod.setPeriodName(period);
        return spmPeriodMapper.queryBonusDetail(spmPeriod);
    }

    @Override
    public List<MyTeam> selectNotMemberId(IRequest request, String memberCode) {
        return myTeamMapper.selectNotMemberIdData(memberCode);
    }

    @Override
    public List<GdsMeDealerTree> queryGdsMealerTree(IRequest request, GdsMeDealerTree tree) {
        String salesOrgCode = metreeMapper.querySalesOrgCode();
        String dealerNo = metreeMapper.queryByDealerNo(request.getAttribute(Member.FIELD_MEMBER_CODE));
        tree.setSalesOrgCode(salesOrgCode);
        tree.setMarketCode(dealerNo);
        return gdsMeDealerTreeMapper.queryGdsMealerTree(tree);
    }

    @Override
    public List<Map<String, Object>> queryBasicSalesBonusRoot(GdsSalaryMaster gdsSalaryMaster) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map3 = new HashMap<String, Object>();
        Map<String, Object> map4 = new HashMap<String, Object>();
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        List<GdsSalaryMaster> list1 = gdsSalaryMasterMapper.queryBasicSalesBonusRoot(gdsSalaryMaster);
        List<GdsSalaryMaster> list2 = gdsSalaryMasterMapper.queryExtraSalesBonusRoot(gdsSalaryMaster);
        List<GdsSalaryMaster> list3 = gdsSalaryMasterMapper.queryPerformanceBonusRoot(gdsSalaryMaster);
        List<GdsSalaryMaster> list4 = gdsSalaryMasterMapper.queryLeadershipBonusRoot(gdsSalaryMaster);
        if (!list1.isEmpty()) {
            map1.put("tree1", list1);
            map1.put("treeName", "tree1");
            lt.add(map1);
        }
        if (!list2.isEmpty()) {
            map2.put("tree2", list2);
            map2.put("treeName", "tree2");
            lt.add(map2);
        }
        if (!list3.isEmpty()) {
            map3.put("tree3", list3);
            map3.put("treeName", "tree3");
            lt.add(map3);
        }
        if (!list4.isEmpty()) {
            map4.put("tree4", list4);
            map4.put("treeName", "tree4");
            lt.add(map4);
        }
        return lt;
    }

    @Override
    public List<GdsSalaryMaster> queryBasicSalesBonusLeaf(GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryBasicSalesBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryExtraSalesBonusLeaf(GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryExtraSalesBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryPerformanceBonusLeaf(GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryPerformanceBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryLeadershipBonusLeaf(GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryLeadershipBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public String selectMarketCodeByMemberCode(String memberCode) {
        return memberMapper.selectMarketCodeByMemberCode(memberCode);
    }

}
