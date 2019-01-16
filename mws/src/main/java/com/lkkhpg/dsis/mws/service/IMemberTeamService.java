/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;
import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;
import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;
import com.lkkhpg.dsis.mws.dto.MyTeam;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员-我的团队服务接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IMemberTeamService extends ProxySelf<IMemberTeamService> {

    /**
     * mws我的团队-个人信息.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员id
     * @return 个人信息
     */
    List<MyTeam> selectTeamByMemberId(IRequest request, Long memberId);

    /**
     * mws我的团队-获取时间段的memRank信息.
     * 
     * @param request
     *            统一上下文
     * @param memberCode
     *            会员code
     * @param begin
     *            开始时间
     * @param end
     *            结束时间
     * @return 时间段内的memRank信息
     */
    List<MyTeam> queryDatePeriod(IRequest request, String memberCode);

    /**
     * mws我的团队-点击菜单获取信息.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员id
     * @param memberCode
     * @return 个人信息集合
     */
    List<MyTeam> query(IRequest request, Long memberId, String memberCode);
    
    /**
     * mws我的团队-点击树形菜单获取信息.
     * 
     * @param request
     *            统一上下文
     * @param orsb
     *            tree
     * @return 个人信息
     */
    List<OmkRtlSalaryBalance> getMemberTree(IRequest request, OmkRtlSalaryBalance orsb);
    
    List<GdsMeDealerTree> queryGdsMealerTree(IRequest request, GdsMeDealerTree tree);
    
    List<Map<String, Object>> queryBasicSalesBonusRoot(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryBasicSalesBonusLeaf(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryExtraSalesBonusLeaf(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryPerformanceBonusLeaf(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryLeadershipBonusLeaf(GdsSalaryMaster gdsSalaryMaster);
    
    List<SpmPeriod> queryBonusDetail(IRequest request, Long marketId, String period);
    
    List<MyTeam> selectNotMemberId(IRequest request, String memberCode);
    
    /**
     * 根据会员卡号查询市场CODE.
     * 
     * @param memberCode
     *            会员卡号
     * @return 市场CODE
     */
    String selectMarketCodeByMemberCode(String memberCode);
}
