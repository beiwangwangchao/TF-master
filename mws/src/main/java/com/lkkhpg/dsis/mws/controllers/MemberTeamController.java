/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ISpmPeriodService;
import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;
import com.lkkhpg.dsis.common.system.report.dto.OmkRtlSalaryBalance;
import com.lkkhpg.dsis.mws.dto.MyTeam;
import com.lkkhpg.dsis.mws.service.IMemberTeamService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * mws-我的团队controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class MemberTeamController extends BaseController {

    @Autowired
    private IMemberTeamService memberTeamService;
    @Autowired
    private ICommMemberService cmmMemberService;
    @Autowired
    private ISpmPeriodService spmPeriodService;

    public static final String URL_IMG = "/resources/img/ztree/diy/ccc.png";

    public static final String URL_IMG_NEW = "/resources/img/ztree/diy/cccc.png";

    public static final String ACCOUNT_BONUS_DETAIL = "/account/account-bonus-detail";
    /**
     * 父节点.
     */
    public static final Long ISLEAF_N = 0L;
    /**
     * 子节点.
     */
    public static final Long ISLEAF_Y = 1L;

    /**
     * 当前组织结构查询初始化.
     * 
     * @param request
     *            统一上下文
     *
     * @return 个人信息
     */
    @RequestMapping(value = "/account/selectTeamByMemberId", method = RequestMethod.POST)
    public ResponseData selectTeamByMemberId(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        String memberCode = requestContext.getAttribute(Member.FIELD_MEMBER_CODE);
        OmkRtlSalaryBalance orsb = new OmkRtlSalaryBalance();
        orsb.setMemberNo(memberCode);
        orsb.setLevel(0L);
        List<OmkRtlSalaryBalance> list = memberTeamService.getMemberTree(requestContext, orsb);
        return new ResponseData(list);
    }

    /**
     * 历史组织结构查询初始化.
     * 
     * @param request
     *            统一上下文
     * @param bonusPeriod
     *            奖金期间
     * @return 历史组织结构集合
     */
    @RequestMapping(value = "/team/initInquiryHistory")
    public ResponseData initInquiryHistory(HttpServletRequest request, String bonusPeriod) {
        IRequest requestContext = createRequestContext(request);
        String memberCode = requestContext.getAttribute(Member.FIELD_MEMBER_CODE);
        // String sysMarketCode = requestContext.getAttribute(SystemProfileConstants.MARKET_CODE);
        GdsMeDealerTree gmdt = new GdsMeDealerTree();
        gmdt.setMemberNo(memberCode);
        gmdt.setBonusPeriod(bonusPeriod);
        List<GdsMeDealerTree> list = memberTeamService.queryGdsMealerTree(requestContext, gmdt);
        /*
         * for (GdsMeDealerTree gst : list) { String marketCode =
         * memberTeamService.selectMarketCodeByMemberCode(gst.getDealerNo()); // 当前市场跟显示会员的市场比较 if
         * (sysMarketCode.equals(marketCode)) { String cutDealerName = gst.getDealername().substring(0,
         * gst.getDealername().indexOf("(")); gst.setDealername(cutDealerName); } }
         */
        return new ResponseData(list);
    }

    /**
     * 历史组织结构查询.
     * 
     * @param request
     *            统一上下文
     * @param memberCode
     *            会员code
     * @param bonusPeriod
     *            奖金期间
     * @return 历史组织结构集合
     */
    @RequestMapping(value = "/team/getHistoryTree")
    public List<Map<String, Object>> getHistoryTree(HttpServletRequest request, String memberCode, String bonusPeriod) {
        IRequest requestContext = createRequestContext(request);
        //String sysMarketCode = requestContext.getAttribute(SystemProfileConstants.MARKET_CODE);
        GdsMeDealerTree gmdt = new GdsMeDealerTree();
        gmdt.setMemberNo(requestContext.getAttribute(Member.FIELD_MEMBER_CODE));
        gmdt.setSponsorNo(memberCode);
        gmdt.setBonusPeriod(bonusPeriod);
        List<GdsMeDealerTree> list = memberTeamService.queryGdsMealerTree(requestContext, gmdt);
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM"); // 可以方便地修改日期格式
        String nowDate = dateFormat.format(now);
        if (list.isEmpty()) {
            return null;
        } else {
            for (GdsMeDealerTree mr : list) {
                Long levelq = mr.getLevelq();
                String dealerNo = mr.getDealerNo();
                Long dealerPostCode = mr.getDealerPostCode();
                String dealername = mr.getDealername();
                // String marketCode = memberTeamService.selectMarketCodeByMemberCode(mr.getDealerNo());
                Long ps = mr.getPs();
                Long lastgv = mr.getLastgv();
                String typeeffectivedate = mr.getTypeeffectivedate();
                String name = "<" + (levelq - 1) + ">" + dealerNo + " / " + dealerPostCode + " / " + dealername + " / "
                        + ps + " / " + lastgv + " / " + typeeffectivedate;
                // 对应html页面各个属性.
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", mr.getDealerNo());
                map.put("name", name);
                map.put("bonusPeriod", bonusPeriod);
                map.put("pid", mr.getSponsorNo());
                // 是否是子节点
                if (ISLEAF_N.equals(mr.getIsleaf())) {
                    map.put("isParent", true);
                } else if (ISLEAF_Y.equals(mr.getIsleaf())) {
                    map.put("isParent", false);
                }
                if (nowDate.equals(typeeffectivedate)) {
                    map.put("icon", request.getContextPath() + URL_IMG_NEW);
                } else {
                    map.put("icon", request.getContextPath() + URL_IMG);
                }
                lt.add(map);
            }
        }
        return lt;
    }

    /**
     * 查询奖金期间.
     * 
     * @param request
     *            统一上下文
     * @param param
     *            关闭期间
     * @return 奖金期间集合
     */
    @RequestMapping(value = "/team/queryPeriod")
    public ResponseData queryPeriod(HttpServletRequest request, String param) {
        return new ResponseData(spmPeriodService.getSpmPeriodNameBySalesOrgIdA(createRequestContext(request), param));
    }

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
    @RequestMapping(value = "/account/queryDatePeriod", method = RequestMethod.POST)
    public ResponseData queryDatePeriod(HttpServletRequest request, String memberCode, String begin, String end) {
        IRequest requestContext = createRequestContext(request);
        List<MyTeam> list = memberTeamService.queryDatePeriod(requestContext, memberCode);
        return new ResponseData(list);
    }

    /**
     * 当前组织结构查询.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员id
     * @param memberCode
     * @param level
     *            层次
     * @param excludeSelf
     *            排除本身
     * @return 个人信息
     */
    @RequestMapping(value = "/account/getMemberTree")
    public List<Map<String, Object>> getMemberTree(HttpServletRequest request, Long memberId, String memberCode,
            Integer level, String excludeSelf) {
        IRequest requestContext = createRequestContext(request);
        OmkRtlSalaryBalance orsb = new OmkRtlSalaryBalance();
        orsb.setMemberNo(requestContext.getAttribute(Member.FIELD_MEMBER_CODE));
        // orsb.setMemberNo(memberCode);
        orsb.setSponsorNo(memberCode);
        orsb.setLevel(9L);
        List<OmkRtlSalaryBalance> list = memberTeamService.getMemberTree(requestContext, orsb);
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM"); // 可以方便地修改日期格式
        String nowDate = dateFormat.format(now);
        if (list.isEmpty()) {
            return null;
        } else {
            for (OmkRtlSalaryBalance mr : list) {
                Long levelq = mr.getLevelq();
                String dealerNo = mr.getDealerNo();
                Long dealerPostCode = mr.getDealerPostCode();
                String dealername = mr.getDealername();
                BigDecimal ps = mr.getPs();
                BigDecimal gpv = mr.getGpv();
                Long lastgv = mr.getLastgv();
                String typeeffectivedate = mr.getTypeeffectivedate();
                String name = "<" + (levelq - 1) + ">" + dealerNo + " / " + dealerPostCode + " / " + dealername + " / "
                        + ps + " / " + gpv + " / " + lastgv + " / " + typeeffectivedate;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", mr.getDealerNo());
                map.put("name", name);
                map.put("memberCode", mr.getDealerNo());
                map.put("pid", mr.getSponsorNo());
                if (ISLEAF_N.equals(mr.getIsleaf())) {
                    map.put("isParent", true);
                } else if (ISLEAF_Y.equals(mr.getIsleaf())) {
                    map.put("isParent", false);
                }
                if (nowDate.equals(typeeffectivedate)) {
                    map.put("icon", request.getContextPath() + URL_IMG_NEW);
                } else {
                    map.put("icon", request.getContextPath() + URL_IMG);
                }
                lt.add(map);
            }
        }
        return lt;
    }

    /**
     * 佣金明细初始化.
     * 
     * @param request
     *            统一上下文
     * @param bonusPeriod
     *            奖金期间
     * @param memberCode
     *            会员code
     * @return 明细
     */
    @RequestMapping(value = "/team/initBonusDetail")
    public List<Map<String, Object>> initBonusDetail(HttpServletRequest request, String bonusPeriod,
            String memberCode) {
        GdsSalaryMaster gsm = new GdsSalaryMaster();
        gsm.setPeriod(bonusPeriod);
        gsm.setDealerNo(createRequestContext(request).getAttribute(Member.FIELD_MEMBER_CODE));
        List<Map<String, Object>> list = memberTeamService.queryBasicSalesBonusRoot(gsm);
        return list;
    }

    /**
     * Basic佣金明细查询.
     * 
     * @param request
     *            统一上下文
     * @param bonusPeriod
     *            奖金期间
     * @param memberCode
     *            会员code
     * @param level
     *            树分支
     * @return 下线信息
     */
    @RequestMapping(value = "/team/getBasicTree")
    public List<Map<String, Object>> getBasicTree(HttpServletRequest request, String bonusPeriod, String memberCode,
            Integer level) {
        // IRequest requestContext = createRequestContext(request);
        GdsSalaryMaster gsm = new GdsSalaryMaster();
        gsm.setSponsorNo(memberCode);
        gsm.setPeriod(bonusPeriod);
        // orsb.setLevel(9L);
        List<GdsSalaryMaster> list = memberTeamService.queryBasicSalesBonusLeaf(gsm);
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        if (list.isEmpty()) {
            return null;
        } else {
            for (GdsSalaryMaster mr : list) {
                String dealerNo = mr.getDealerNo();
                String saleOrgCode = mr.getSaleOrgCode();
                BigDecimal pv = mr.getPv();
                BigDecimal gv = mr.getGv();
                String parsedTitlerate = mr.getParsedTitlerate();
                BigDecimal gdcamt = mr.getGdcamt();
                String name = "<" + (level + 1) + ">" + dealerNo + " / " + saleOrgCode + " / " + pv + " / " + gv + " / "
                        + parsedTitlerate + " / " + gdcamt;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", mr.getDealerNo());
                map.put("name", name);
                map.put("bonusPeriod", bonusPeriod);
                map.put("pid", mr.getSponsorNo());
                if (!"Y".equals(mr.getIsLeaf())) {
                    map.put("isParent", true);
                } else {
                    map.put("isParent", false);
                }
                map.put("icon", request.getContextPath() + URL_IMG);
                lt.add(map);
            }
        }
        return lt;
    }

    /**
     * Extra佣金明细查询.
     * 
     * @param request
     *            统一上下文
     * @param bonusPeriod
     *            奖金期间
     * @param memberCode
     *            会员code
     * @param level
     *            树分支
     * @return 下线信息
     */
    @RequestMapping(value = "/team/getExtraTree")
    public List<Map<String, Object>> getExtraTree(HttpServletRequest request, String bonusPeriod, String memberCode,
            Integer level) {
        GdsSalaryMaster gsm = new GdsSalaryMaster();
        gsm.setSponsorNo(memberCode);
        gsm.setPeriod(bonusPeriod);
        // orsb.setLevel(9L);
        List<GdsSalaryMaster> list = memberTeamService.queryExtraSalesBonusLeaf(gsm);
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        if (list.isEmpty()) {
            return null;
        } else {
            for (GdsSalaryMaster mr : list) {
                String dealerNo = mr.getDealerNo();
                String saleOrgCode = mr.getSaleOrgCode();
                BigDecimal pv = mr.getPv();
                BigDecimal gv = mr.getGv();
                BigDecimal lbcnt = mr.getLbcnt();
                BigDecimal lbgv = mr.getLbgv();
                BigDecimal lbamt = mr.getLbamt();
                String name = "<" + (level + 1) + ">" + dealerNo + " / " + saleOrgCode + " / " + pv + " / " + gv + " / "
                        + lbcnt + " / " + lbgv + " / " + lbamt;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", mr.getDealerNo());
                map.put("name", name);
                map.put("bonusPeriod", bonusPeriod);
                map.put("pid", mr.getSponsorNo());
                if (!"Y".equals(mr.getIsLeaf())) {
                    map.put("isParent", true);
                } else {
                    map.put("isParent", false);
                }
                map.put("icon", request.getContextPath() + URL_IMG);
                lt.add(map);
            }
        }
        return lt;
    }

    /**
     * Performance佣金明细查询.
     * 
     * @param request
     *            统一上下文
     * @param bonusPeriod
     *            奖金期间
     * @param memberCode
     *            会员code
     * @param level
     *            树分支
     * @return 下线信息
     */
    @RequestMapping(value = "/team/getPerformanceTree")
    public List<Map<String, Object>> getPerformanceTree(HttpServletRequest request, String bonusPeriod,
            String memberCode, Integer level) {
        // IRequest requestContext = createRequestContext(request);
        GdsSalaryMaster gsm = new GdsSalaryMaster();
        gsm.setSponsorNo(memberCode);
        gsm.setPeriod(bonusPeriod);
        // orsb.setLevel(9L);
        List<GdsSalaryMaster> list = memberTeamService.queryPerformanceBonusLeaf(gsm);
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        if (list.isEmpty()) {
            return null;
        } else {
            for (GdsSalaryMaster mr : list) {
                String dealerNo = mr.getDealerNo();
                String saleOrgCode = mr.getSaleOrgCode();
                BigDecimal pv = mr.getPv();
                BigDecimal gv = mr.getGv();
                String parsedPbrate = mr.getParsedPbrate();
                BigDecimal pbamt = mr.getPbamt();
                String name = "<" + (level + 1) + ">" + dealerNo + " / " + saleOrgCode + " / " + pv + " / " + gv + " / "
                        + parsedPbrate + " / " + pbamt;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", mr.getDealerNo());
                map.put("name", name);
                map.put("bonusPeriod", bonusPeriod);
                map.put("pid", mr.getSponsorNo());
                if (!"Y".equals(mr.getIsLeaf())) {
                    map.put("isParent", true);
                } else {
                    map.put("isParent", false);
                }
                map.put("icon", request.getContextPath() + URL_IMG);
                lt.add(map);
            }
        }
        return lt;
    }

    /**
     * Leadership佣金明细查询.
     * 
     * @param request
     *            统一上下文
     * @param bonusPeriod
     *            奖金期间
     * @param memberCode
     *            会员code
     * @param level
     *            树分支
     * @return 下线信息
     */
    @RequestMapping(value = "/team/getLeadershipTree")
    public List<Map<String, Object>> getLeadershipTree(HttpServletRequest request, String bonusPeriod,
            String memberCode, Integer level) {
        GdsSalaryMaster gsm = new GdsSalaryMaster();
        // gsm.setSponsorNo(memberCode);
        gsm.setBbRefno(memberCode);
        gsm.setPeriod(bonusPeriod);
        // orsb.setLevel(9L);
        List<GdsSalaryMaster> list = memberTeamService.queryLeadershipBonusLeaf(gsm);
        List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
        if (list.isEmpty()) {
            return null;
        } else {
            for (GdsSalaryMaster mr : list) {
                String dealerNo = mr.getDealerNo();
                String saleOrgCode = mr.getSaleOrgCode();
                BigDecimal pv = mr.getPv();
                BigDecimal bbcnt = mr.getBbcnt();
                BigDecimal bbgv = mr.getBbgv();
                BigDecimal bblevelno = mr.getBblevelno();
                BigDecimal bbamt = mr.getBbamt();
                String name = "<" + (level + 1) + ">" + dealerNo + " / " + saleOrgCode + " / " + pv + " / " + bbcnt
                        + " / " + bbgv + " / " + bblevelno + " / " + bbamt;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", mr.getDealerNo());
                map.put("name", name);
                map.put("bonusPeriod", bonusPeriod);
                map.put("pid", mr.getSponsorNo());
                if (!"Y".equals(mr.getIsLeaf())) {
                    map.put("isParent", true);
                } else {
                    map.put("isParent", false);
                }
                map.put("icon", request.getContextPath() + URL_IMG);
                lt.add(map);
            }
        }
        return lt;
    }

    /**
     * mws-我的团队-获取下线信息.
     * 
     * @param request
     *            统一上下文
     * @param memberId
     *            会员id
     * @param memberCode
     * @return 个人信息
     */
    @RequestMapping(value = "/account/query")
    public ResponseData query(HttpServletRequest request, Long memberId, String memberCode) {
        IRequest requestContext = createRequestContext(request);
        List<MyTeam> list = memberTeamService.query(requestContext, memberId, memberCode);
        if (memberId != null) {
            Map<String, Object> map = cmmMemberService.getDeadLine(requestContext, memberCode);
            for (MyTeam myTeam : list) {
                myTeam.setDeadLine((Integer) map.get("deadLine"));
            }
        }
        return new ResponseData(list);
    }

    @RequestMapping(value = "/account/selectNotMemberId")
    public ResponseData selectNotMemberId(HttpServletRequest request, String memberCode) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberTeamService.selectNotMemberId(requestContext, memberCode));
    }

    /**
     * mws-我的团队-奖金明细.
     * 
     * @param request
     *            统一上下文
     * @param period
     *            奖金期间
     * @return 详细页面
     */
    @RequestMapping(value = "/account/myTeamBonusDetail")
    public ModelAndView queryBonusDetail(HttpServletRequest request, String period) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView result = new ModelAndView();
        Long marketId = requestContext.getAttribute(SystemProfileConstants.MARKET_ID);
        List<SpmPeriod> spmPeriodList = memberTeamService.queryBonusDetail(requestContext, marketId, period);
        result.setViewName(getViewPath() + ACCOUNT_BONUS_DETAIL);
        // String str = "";
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < spmPeriodList.size(); i++) {
            if (i == 0) {
                str = str.append(spmPeriodList.get(i).getPeriodId());
            } else {
                str = str.append(',').append(spmPeriodList.get(i).getPeriodId());
            }
        }
        result.addObject("periodId", str);
        return result;
    }
}
