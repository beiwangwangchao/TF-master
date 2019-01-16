/*
 *
 */

package com.lkkhpg.dsis.admin.member.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.integration.dapp.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.dapp.service.IDistributorCallBackService;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.ISaveDealerService;
import com.lkkhpg.dsis.admin.integration.gds.service.ISponsorVerifyService;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemAccount;
import com.lkkhpg.dsis.common.member.dto.MemCard;
import com.lkkhpg.dsis.common.member.dto.MemRank;
import com.lkkhpg.dsis.common.member.dto.MemRelationship;
import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.dto.Sponsor;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.common.promotion.dto.VoucherAssign;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.IMarketParamService;
import com.lkkhpg.dsis.integration.gds.service.impl.GdsSwitch;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.service.IRoleFunctionService;

/**
 * 会员 Controller,处理会员增删查改操作.
 *
 * @author frank.li
 */
@Controller
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private IMemberService memberService;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    private ISponsorVerifyService sponsorVerifyService;

    @Autowired
    private ISaveDealerService saveDealerService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IDistributorCallBackService registrationCallBackService;

    @Autowired
    private IMarketParamService marketParamService;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GdsSwitch gdsSwitch;

    /**
     * 编辑会员详情.
     *
     * @param request 请求上下文
     * @return 页面跳转
     */
    @RequestMapping(value = "/mm/mm_member_edit.html")
    public ModelAndView editMember(final HttpServletRequest request, Long memberId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(getViewPath() + "/mm/mm_member_edit");
        IRequest requestContext = createRequestContext(request);

        HttpSession session = request.getSession(false);
        String marketId = session.getAttribute(SystemProfileConstants.MARKET_ID).toString();
        Member member = memberService.getMember(requestContext, memberId);
        if (null != member) {
            marketId = String.valueOf(member.getMarketId());
        }
        // 家庭住址
        String paramHomeAddress = marketParamService.getParamValue(requestContext,
                SystemProfileConstants.PARAM_MEMBER_HOME_ADDRESS, SystemProfileConstants.MARKET, marketId,
                SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberHomeAddress", paramHomeAddress == null ? "N" : paramHomeAddress);

        // 种族
        String paramRrace = marketParamService.getParamValue(requestContext, SystemProfileConstants.PARAM_MEMBER_RACE,
                SystemProfileConstants.MARKET, marketId, SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberRace", paramRrace == null ? "N" : paramRrace);

        // 校验GST ID号码
        String paramGstId = marketParamService.getParamValue(requestContext, SystemProfileConstants.PARAM_MEMBER_GST_ID,
                SystemProfileConstants.MARKET, marketId, SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberGstId", paramGstId == null ? "N" : paramGstId);

        // 校验健税保外
        String paramNhiTaxExcluded = marketParamService.getParamValue(requestContext,
                SystemProfileConstants.PARAM_MEMBER_NHI_TAX_EXCLUDED, SystemProfileConstants.MARKET, marketId,
                SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberNhiTaxExcluded", paramNhiTaxExcluded == null ? "N" : paramNhiTaxExcluded);

        // 公民类型
        /*
         * String paramCitizenType =
         * marketParamService.getParamValue(requestContext,
         * SystemProfileConstants.PARAM_MEMBER_HOME_CITIZEN_TYPE,
         * SystemProfileConstants.MARKET, marketId,
         * SystemProfileConstants.ORG_TYPE_MARKET);
         * mav.addObject("spmParamMemberHomeCitizenType", paramCitizenType ==
         * null ? "N" : paramCitizenType);
         */

        // 申请清单
        String paramApplicitionList = marketParamService.getParamValue(requestContext,
                SystemProfileConstants.PARAM_MEMBER_APPLICITION_LIST, SystemProfileConstants.MARKET, marketId,
                SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberApplicitionList", paramApplicitionList == null ? "N" : paramApplicitionList);

        // 会员年龄预警
        String spmParamMemberAgeAlert = marketParamService.getParamValue(requestContext,
                SystemProfileConstants.PARAM_MEMBER_AGE_ALERT, SystemProfileConstants.MARKET, marketId,
                SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberAgeAlert", spmParamMemberAgeAlert == null ? "N" : spmParamMemberAgeAlert);

        // 支行
        String spmParamMemberBankBranch = marketParamService.getParamValue(requestContext,
                SystemProfileConstants.PARAM_MEMBER_BANK_BRANCH, SystemProfileConstants.MARKET, marketId,
                SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberBankBranch", spmParamMemberBankBranch == null ? "N" : spmParamMemberBankBranch);

        // 启用会员姓编辑
        String spmParamMemberLastNameEditable = marketParamService.getParamValue(requestContext,
                SystemProfileConstants.PARAM_MEMBER_LAST_NAME_EDITABLE, SystemProfileConstants.MARKET, marketId,
                SystemProfileConstants.ORG_TYPE_MARKET);
        mav.addObject("spmParamMemberLastNameEditable",
                spmParamMemberLastNameEditable == null ? "N" : spmParamMemberLastNameEditable);

        if (logger.isDebugEnabled()) {
            logger.debug("paramHomeAddress: {}", paramHomeAddress);
            logger.debug("paramRrace: {}", paramRrace);
            logger.debug("paramGstId: {}", paramGstId);
            logger.debug("paramNhiTaxExcluded: {}", paramNhiTaxExcluded);
            logger.debug("paramApplicitionList: {}", paramApplicitionList);
            logger.debug("spmParamMemberAgeAlert: {}", spmParamMemberAgeAlert);
            logger.debug("spmParamMemberBankBranch: {}", spmParamMemberBankBranch);
            logger.debug("spmParamMemberLastNameEditable: {}", spmParamMemberLastNameEditable);
        }

        return mav;
    }

    /**
     * 推荐人即时鉴别.
     *
     * @param memberCode 会员ID
     * @param request    请求上下文
     * @return responseData 响应数据
     * @throws IntegrationException 接口统一异常
     */
    @RequestMapping(value = "/mm/member/sponsorVerify")
    @ResponseBody
    public ResponseData sponsorVerify(String memberCode, HttpServletRequest request) throws IntegrationException {
        IRequest requestContext = createRequestContext(request);
        String orgCode = gdsUtilService.getCurrentOrgCode(requestContext);
        String gdsOrgCode = gdsUtilService.getGdsOrgCode(requestContext, orgCode);
        Sponsor sponsor = new Sponsor();
        Member member = memberMapper.selectMembersByMemberCode(memberCode);
        if (member != null && !MemberConstants.MARKET_CN.equals(member.getMarketCode())
                && MemberConstants.MEMBER_STATUS_ACTIVE.equals(member.getStatus())) {
            if (MemberConstants.MM_ROLE_VIP.equals(member.getMemberRole())) {
                String enable = marketParamService.getParamValue(requestContext, SystemProfileConstants.SPM_MEMBER_VIP_SPONSOR,
                        SystemProfileConstants.MARKET, String.valueOf(member.getMarketId()),
                        SystemProfileConstants.ORG_TYPE_MARKET);
                if (enable == null || BaseConstants.NO.equals(enable)) {
                    throw new IntegrationException(IntegrationException.MSG_ERROR_INVALID_SPONSOR_TW_VIP, null);
                }
            }
//            sponsor.setSponsorNo(memberCode);
//            sponsor.setSponsorChineseName(member.getChineseName());
//            sponsor.setSponsorEnglishName(member.getEnglishName());
            sponsor.setSponsorNo(memberCode);
            sponsor.setSponsorChineseName("张三");
            sponsor.setSponsorEnglishName("zhansan");
        } else {
            sponsor = sponsorVerifyService.sponsorVerify(requestContext, memberCode, gdsOrgCode);
        }
        List<Sponsor> sponsors = new ArrayList<Sponsor>();
        sponsors.add(sponsor);

        return new ResponseData(sponsors);
    }


    /**
     * 获取会员详情.
     *
     * @param memberId 会员Id
     * @param request  请求上下文
     * @return responseData 响应数据
     * @throws MemberException 会员异常
     */
    @RequestMapping(value = "/mm/member/get")
    @ResponseBody
    public ResponseData getMembers(Long memberId, HttpServletRequest request) throws MemberException {
        IRequest requestContext = createRequestContext(request);
        Member member = memberService.getMember(requestContext, memberId);

        List<Member> members = new ArrayList<Member>();

        // 判断当前用户是否IPOINT用户，I-point用户只能查看、更新今天创建的会员
        HttpSession session = request.getSession(false);
        String userType = (String) session.getAttribute(User.FIELD_USER_TYPE);
        if (User.USER_TYPE_IPOINT.equals(userType)) {
            Date nowDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            if (!nowDate.equals(DateUtils.truncate(member.getJointDate(), Calendar.DAY_OF_MONTH))) {
                throw new MemberException(MemberException.MSG_ERROR_IPOINT_ONLY_QUERY_CURR_JOINT_DATE_MEMBER, null);
            }
        }

        // 获取当前销售区域
        // Long salesOrgId = (Long)
        // session.getAttribute(SystemProfileConstants.SALES_ORG_ID);
        // 获取当前市场
        /*
         * Long marketId = (Long)
         * session.getAttribute(SystemProfileConstants.MARKET_ID); // 会员销售组织数据屏蔽
         * if (!marketId.equals(member.getMarketId())) { // throw new //
         * MemberException(MemberException.
         * MSG_ERROR_MEMBER_MARKET_ORG_DATA_MASK, // null); return new
         * ResponseData(members); }
         */

        members.add(member);
        return new ResponseData(members);
    }

    /**
     * 保存会员.
     *
     * @param members 会员List
     * @param result  数据绑定结果
     * @param request 请求上下文
     * @return responseData 响应数据
     * @throws CommMemberException  会员统一异常
     * @throws IntegrationException 接口异常
     */
    @RequestMapping(value = "/mm/member/save", method = RequestMethod.POST)
    public ResponseData saveMember(@RequestBody List<Member> members, BindingResult result, HttpServletRequest request)
            throws CommMemberException, IntegrationException {


        System.out.println("---------debug start 2018/01/04 10:09-----------------");
        System.out.println(members.toString());
        System.out.println(members.size());
        System.out.println(members.get(0).getDiscount());

        getValidator().validate(members, result);

        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        final IRequest requestContext = createRequestContext(request);

        if (logger.isDebugEnabled()) {
            logger.debug("members.size(): {}", members.size());
        }

        // 判断操作类型： I/U
        String operType;
        if (members.get(0).getMemberId() == null) {
            operType = IntegrationConstant.OPER_TYPE_I;
        } else {
            operType = IntegrationConstant.OPER_TYPE_U;
        }

        Member member = memberService.saveMember(requestContext, members.get(0));
        members.set(0, member);

        if (logger.isDebugEnabled()) {
            logger.debug("call memberService.saveMember success");
        }
        return new ResponseData(members);
    }

    /**
     * 同步会员.
     *
     * @param memberId 会员表Id
     * @param request  请求上下文
     * @return responseData 响应数据
     * @throws IntegrationException 接口异常
     */
    @RequestMapping(value = "/mm/member/sync", method = RequestMethod.POST)
    public ResponseData syncMember(Long memberId, HttpServletRequest request) throws IntegrationException {
        if (!gdsSwitch.isSwitchFlag()) {
            throw new IntegrationException(IntegrationException.MSG_ERROR_GDS_IS_SHUTDOWN, null);
        }
        IRequest requestContext = createRequestContext(request);
        Member member = new Member();
        member.setMemberId(memberId);

        // 调用即時會員資料保存(调用接口失败不影响POS会员保存)
//        saveDealerService.saveDealer(requestContext, member);
//        if (logger.isDebugEnabled()) {
//            logger.debug("call saveDealerService.saveDealer success");
//        }
        return new ResponseData();
    }

    /**
     * 提交会员.
     *
     * @param members 会员List
     * @param request 请求上下文
     * @return responseData 响应数据
     * @throws MemberException 会员统一异常
     */
    @RequestMapping(value = "/mm/member/submit")
    @ResponseBody
    public ResponseData submitMembers(@RequestBody List<Member> members, HttpServletRequest request)
            throws MemberException {
        IRequest requestContext = createRequestContext(request);

        Member member = memberService.submitMember(requestContext, members.get(0));
        members.set(0, member);

        return new ResponseData(members);
    }

    /**
     * 查询会员详情列表.
     *
     * @param member   会员DTO
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/member/queryDetail")
    @ResponseBody
    public ResponseData queryMemberDetails(Member member, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMembers(requestContext, member, page, pagesize));
    }

    /**
     * 查询相关人.
     *
     * @param relationship 相关人DTO
     * @param page         页
     * @param pagesize     分页大小
     * @param request      请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/relationship/query")
    @ResponseBody
    public ResponseData getMemberRelationships(MemRelationship relationship,
                                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                memberService.queryRelationships(requestContext, relationship.getMemberId(), page, pagesize));
    }

    /**
     * 查询会员地点.
     *
     * @param site     会员地点DTO
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/site/query")
    @ResponseBody
    public ResponseData getMemSites(MemSite site, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.querySites(requestContext, site.getMemberId(), page, pagesize));
    }

    /**
     * 查询会员账户.
     *
     * @param account  会员账户DTO
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/account/query")
    @ResponseBody
    public ResponseData getMemAccounts(MemAccount account, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryAccounts(requestContext, account.getMemberId(), page, pagesize));
    }

    /**
     * 查询会员银行卡.
     *
     * @param card     会员银行卡DTO
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/card/query")
    @ResponseBody
    public ResponseData getMemCards(MemCard card, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryCards(requestContext, card.getMemberId(), page, pagesize));
    }

    /**
     * 自动订货单查询会员银行卡.
     *
     * @param memberId 会员ID
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/card/autoquery")
    @ResponseBody
    public ResponseData autoQueryCard(Long memberId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.autoQueryCard(requestContext, memberId));
    }

    /**
     * 查询会员账务余额.
     *
     * @param memberId  会员Id
     * @param monthFrom 月份从
     * @param monthTo   月份至
     * @param page      页
     * @param pagesize  分页大小
     * @param request   请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/accountingbalance/query")
    @ResponseBody
    public ResponseData getMemAccountingBalances(Long memberId, Date monthFrom, Date monthTo,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMemAccountingBalances(requestContext, memberId));
    }

    /**
     * 查询会员账务事务处理.
     *
     * @param memberId       会员Id
     * @param accountingType 账务类型
     * @param trxDateFrom    事务处理日期从
     * @param trxDateTo      事务处理日期至
     * @param page           页
     * @param pagesize       分页大小
     * @param request        请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/accountingtrx/query")
    @ResponseBody
    public ResponseData getMemAccountingTrxs(Long memberId, String accountingType, Date trxDateFrom, Date trxDateTo,
                                             @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMemAccountingTrxs(requestContext, memberId, accountingType,
                trxDateFrom, trxDateTo, page, pagesize));
    }

    /**
     * 查询会员等级.
     *
     * @param memberCode 会员Id
     * @param monthFrom  月份从
     * @param monthTo    月份至
     * @param page       页
     * @param pagesize   分页大小
     * @param request    请求上下文
     * @return responseData 响应数据
     * @throws CommMemberException 会员统一异常
     */
    @RequestMapping(value = "/mm/rank/query")
    @ResponseBody
    public ResponseData getMemRanks(@PathParam("memberCode") String memberCode, Date monthFrom, Date monthTo,
                                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
            throws CommMemberException {
        IRequest requestContext = createRequestContext(request);
        List<MemRank> queryRanks = memberService.queryRanks(requestContext, memberCode, monthFrom, monthTo, page,
                pagesize);
        return new ResponseData(queryRanks);
    }

    /**
     * 查询冲销节余.
     *
     * @param withdraw 冲销节余DTO
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/withdraw/query")
    @ResponseBody
    public ResponseData getMemWithdraws(MemWithdraw withdraw, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryWithdraws(requestContext, withdraw.getMemberId(), page, pagesize));
    }

    /**
     * 查询会员优惠券.
     *
     * @param voucherAssign 优惠券分配DTO
     * @param page          页
     * @param pagesize      分页大小
     * @param request       请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/voucherAssign/query")
    @ResponseBody
    public ResponseData queryVoucherAssigns(VoucherAssign voucherAssign,
                                            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                memberService.queryVoucherAssigns(requestContext, voucherAssign.getMemberId(), page, pagesize));
    }

    /**
     * 查询会员下线.
     *
     * @param memberId 会员Id
     * @param request  请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/downLines/query")
    @ResponseBody
    public ResponseData queryDownLines(Long memberId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryDownLines(requestContext, memberId));
    }

    /**
     * 查询会员OMK下线树.
     *
     * @param memberCode  会员卡号
     * @param level       层次
     * @param excludeSelf 排除本身
     * @param request     请求上下文
     * @return List 响应数据
     */
    @RequestMapping(value = "/mm/omkDowmlines/get")
    @ResponseBody
    public List<Member> getDowmLines(String memberCode, Integer level, String excludeSelf, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<Member> list = commMemberService.getOmkDownLines(requestContext, memberCode, level, excludeSelf);
        // List<Member> ls = null;
        for (int i = 0; i < list.size(); i++) {
            List<Member> ls = commMemberService.getOmkDownLines(requestContext, list.get(i).getMemberCode(), level,
                    excludeSelf);
            if (ls.isEmpty()) {
                list.get(i).setIsLeaf("N");
            }

        }
        return list;
    }

    /**
     * 查询会员下线树.
     *
     * @param memberId    会员Id
     * @param level       层次
     * @param excludeSelf 排除本身
     * @param request     请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/dowmlines/get")
    @ResponseBody
    public ResponseData getDowmLines(Long memberId, Integer level, String excludeSelf, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(commMemberService.getDownLines(requestContext, memberId, level, excludeSelf));
    }

    /**
     * 查询会员上线.
     *
     * @param memberId    会员Id
     * @param level       层次
     * @param excludeSelf 排除本身
     * @param request     请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/uplines/get")
    @ResponseBody
    public ResponseData getUpLines(Long memberId, int level, String excludeSelf, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(commMemberService.getUpLines(requestContext, memberId, level, excludeSelf));
    }

    /**
     * 查询会员.
     *
     * @param request  请求上下文
     * @param member   会员DTO
     * @param yearFrom 年份从
     * @param yearTo   年份至
     * @param month    月份
     * @param page     页
     * @param pagesize 分页大小
     * @return responseData 响应数据
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "/mm/member/query")
    @ResponseBody
    public ResponseData queryMembers(Member member, String month, String yearFrom, String yearTo,
                                     @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     HttpServletResponse response,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize,
                                     HttpServletRequest request)
            throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest requestContext = createRequestContext(request);

        // 判断当前用户是否IPOINT用户，I-point用户只能查看、更新今天创建的会员
        HttpSession session = request.getSession(false);
        String userType = (String) session.getAttribute(User.FIELD_USER_TYPE);

        List<Member> members = memberService.queryMembers(requestContext, member, month, yearFrom, yearTo, userType,
                page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, members);
            return null;
        } else {
            return new ResponseData(members);
        }

    }

    /**
     * 订单使用的会员查询.
     *
     * @param member   会员参数
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     * @throws IntegrationException
     */
    @RequestMapping(value = "/mm/member/queryMembersForOrder")
    @ResponseBody
    public ResponseData queryMembersForOrder(Member member, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
            throws IntegrationException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMembersForOrder(requestContext, member, page, pagesize));
    }

    /**
     * ipoint订单使用的会员查询.
     *
     * @param member   会员参数
     * @param page     页
     * @param pagesize 分页大小
     * @param request  请求上下文
     * @return responseData 响应数据
     * @throws IntegrationException
     */
    @RequestMapping(value = "/mm/member/queryMembersForIpointOrder")
    @ResponseBody
    public ResponseData queryMembersForIpointOrder(Member member, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
            throws IntegrationException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMembersForIpointOrder(requestContext, member, page, pagesize));
    }

    /**
     * 消息中查询所有激活状态的会员.
     *
     * @param request  请求上下文
     * @param member   查询条件
     * @param page     页
     * @param pagesize 分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/membermessage/query")
    @ResponseBody
    public ResponseData queryMembers(HttpServletRequest request, Member member,
                                     @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMessageMember(requestContext, member, page, pagesize));
    }

    /**
     * 根据条件查询所有会员冲销节余.
     *
     * @param request    请求上下文
     * @param memberCode 会员Id
     * @param periodFrom 年份从
     * @param periodTo   年份至
     * @param memberId   会员Id
     * @param memberName 会员名称
     * @param page       页
     * @param pagesize   分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/withdraw/queryByParams")
    @ResponseBody
    public ResponseData queryWithdrawsByParams(HttpServletRequest request, String memberCode, String periodFrom,
                                               String periodTo, Long memberId, String memberName, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, Long marketId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryWithdrawsByParams(requestContext, memberCode, periodFrom, periodTo,
                memberId, memberName, page, pagesize, marketId));
    }

    /**
     * 会员申请冲销节余.
     *
     * @param request          请求上下文
     * @param memWithdraw      冲销节余DTO
     * @param remainingBalance 会员剩余奖金
     * @param page             页
     * @param pagesize         分页大小
     * @param isConfirm        是否确认执行冲销
     * @return responseData 响应数据
     * @throws CommMemberException 会员异常
     */
    @RequestMapping(value = "/mm/withdraw/insertWithdraw", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveWithdraw(HttpServletRequest request, @RequestBody MemWithdraw memWithdraw,
                                     @RequestParam String remainingBalance, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, boolean isConfirm)
            throws CommMemberException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                memberService.addWithdraw(requestContext, memWithdraw, remainingBalance, page, pagesize, isConfirm));
    }

    /**
     * 根据会员ID获取剩余奖金.
     *
     * @param request  请求上下文
     * @param memberId 会员表主键
     * @param page     页
     * @param pagesize 分页大小
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/withdraw/getBalanceByMemberId")
    @ResponseBody
    public ResponseData getBalanceByMemberId(HttpServletRequest request, @RequestParam String memberId,
                                             @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.getBalanceByMemberId(requestContext, memberId, page, pagesize));
    }

    /**
     * 根据会员ID获取激活状态的会员.
     *
     * @param request    请求上下文
     * @param memberCode 会员ID
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/member/getMemberIdByCode")
    @ResponseBody
    public ResponseData getMemberIdByCode(HttpServletRequest request, @RequestParam String memberCode,
                                          @RequestParam Long marketId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.getMemberIdByCode(requestContext, memberCode, marketId));
    }

    /**
     * 当前月份是否是会员的生日月份.
     *
     * @param request  请求上下文
     * @param memberId 会员ID
     * @return 结果集
     */
    @RequestMapping(value = "/mm/member/isMemberBirthdayMonth")
    @ResponseBody
    public ResponseData isMemberBirthdayMonth(HttpServletRequest request, Long memberId) {

        Map map = memberService.isMemberBirthdayMonth(createRequestContext(request), memberId);
        List list = new ArrayList<>();
        list.add(map);
        ResponseData response = new ResponseData(list);
        return response;
    }

    /**
     * 会员查询页-发送通知，保存通知人.
     *
     * @param request   请求上下文
     * @param memberIds 会员信息
     * @return 结果集
     */
    @RequestMapping(value = "/mm/member/saveReceiver", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveReceiver(HttpServletRequest request, @RequestBody List<Long> memberIds) {
        return new ResponseData(memberService.saveReceiver(createRequestContext(request), memberIds));
    }

    /**
     * 会员详情页-更改角色.
     *
     * @param request  请求上下文
     * @param memberId 会员Id
     * @return 结果集
     * @throws CommMemberException 会员统一异常
     */
    @RequestMapping(value = "/mm/member/changeRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData changeRole(HttpServletRequest request, @RequestBody Long memberId) throws CommMemberException {
        IRequest iRequest = createRequestContext(request);
        memberService.vipChangeToDistributor(iRequest, memberId);
        return new ResponseData();
    }

    /**
     * 获取当前会员完整的银行卡信息（解密卡号）.
     *
     * @param memberId 会员id.
     * @param request  统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/mm/query/bankInfo")
    @ResponseBody
    public ResponseData queryBankCard(Long memberId, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<MemCard> result = memberService.queryBankInfo(iRequest, memberId);
        ResponseData data = new ResponseData(result);
        return data;
    }

    /**
     * 根据会员编号获取会员地址（账单，发运）.
     *
     * @param memberCode 会员编号.
     * @param request    统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/mm/memSites/query")
    @ResponseBody
    public ResponseData queryMemSites(String memberCode, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<MemSite> memSites = memberService.queryMemSites(memberCode, iRequest);
        ResponseData data = new ResponseData(memSites);
        return data;
    }

    /**
     * 根据角色Id查看是否有权限访问奖金页面.
     *
     * @param member  会员
     * @param request 统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/mm/member/isRoleAssignBonusFinal")
    @ResponseBody
    public ResponseData isRoleAssignBonusFinal(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Boolean bool = memberService.isRoleAssignBonusFinal(iRequest.getRoleId());
        ResponseData data = new ResponseData();
        if (bool) {
            data.setCode(BaseConstants.YES);
        } else {
            data.setCode(BaseConstants.NO);
        }
        data.setSuccess(Boolean.TRUE);
        return data;
    }

    /**
     * 校验是否银行账户重复.
     *
     * @param member  会员.
     * @param request 统一上下文.
     * @return 返回结果.
     */
    @RequestMapping(value = "/mm/member/isSameAccount")
    public ResponseData isSameAccount(HttpServletRequest request, @RequestBody Member member) {
        IRequest iRequest = createRequestContext(request);
        String memberCode = memberService.isSameAccount(iRequest, member);
        ResponseData data = new ResponseData();
        if (memberCode != null) {// 若不为空则表示重复
            data.setCode(BaseConstants.YES);
            data.setMessage(memberCode);
        } else {
            data.setCode(BaseConstants.NO);
        }
        data.setSuccess(Boolean.TRUE);
        return data;
    }


    @RequestMapping(value = "/mm/member/queryDiscount")
    @ResponseBody
    public ResponseData queryDiscount(HttpServletRequest requestContext, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, Member member) {
        IRequest request = createRequestContext(requestContext);

        return new ResponseData(memberService.queryDiscount(request, member, page, pageSize));
    }


    @RequestMapping(value = "/mm/member/queryMemberByMarket")
    @ResponseBody
    public ResponseData queryMemberByMarket(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMemberByMarket(requestContext));
    }
}

