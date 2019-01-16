/*
 *
 */

package com.lkkhpg.dsis.admin.member.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.admin.integration.gds.service.IIsgMarketChangeListService;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMarketChangeService;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.admin.system.DsisServiceRequest;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.MemMarketChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 会员市场变更控制层.
 * 
 * @author linyuheng
 */

@Controller
public class MarketChangeController extends BaseController {

    @Autowired
    private IMarketChangeService marketChangeService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IGdsUtilService gdsUtilService;

    @Autowired
    private IIsgMarketChangeListService isgMarketChangeListService;

    /**
     * 变更市场_查询会员市场变更记录.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员市场变更DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/mm/marketChange/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryMarketChange(HttpServletRequest request, @ModelAttribute MemMarketChange memMarketChange,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(marketChangeService.queryMarketChange(iRequest, memMarketChange, page, pagesize));
    }

    /**
     * 变更市场审核_查询会员市场变更记录(审核中).
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/mm/marketChange/queryApproving", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryApprovingMarketChange(HttpServletRequest request,
            @ModelAttribute MemMarketChange memMarketChange, @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(
                marketChangeService.queryApprovingMarketChange(iRequest, memMarketChange, page, pagesize));
    }

    /**
     * 查询原市场.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/mm/marketChange/queryOldMarket", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryOldMarketByManual(HttpServletRequest request,
            @ModelAttribute MemMarketChange memMarketChange) {
        IRequest iRequest = createRequestContext(request);
        List<MemMarketChange> memMarketChanges = marketChangeService.queryOldMarket(iRequest, memMarketChange);
        return new ResponseData(memMarketChanges);
    }

    /**
     * 提交变更市场信息.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChange
     *            会员变更市场DTO
     * @param result
     *            数据绑定结果
     * @return ResponseData 数据返回对象
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口统一异常
     */
    @RequestMapping(value = "/mm/marketChange/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitMarketChange(HttpServletRequest request, @ModelAttribute MemMarketChange memMarketChange,
            BindingResult result) throws MemberException, IntegrationException {
        getValidator().validate(memMarketChange, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        Locale locale = RequestContextUtils.getLocale(request);
        ResponseData responseData = new ResponseData();

        String gdsId = marketChangeService.submitUpstreamChange(iRequest, memMarketChange);

        if (StringUtils.isNoneEmpty(gdsId)) {
            String message = getMessageSource().getMessage(MemberConstants.MSG_APPLY_SUCCESS, null, locale);
            responseData.setMessage(message + gdsId);
            responseData.setCode(MemberConstants.MSG_APPLY_SUCCESS);
            responseData.setSuccess(true);

            // 发送站内信通知审批人
            DsisServiceRequest dsisRequest = new DsisServiceRequest();
            try {
                marketChangeService.sendMessage(dsisRequest, iRequest.getAttribute(SystemProfileConstants.SALES_ORG_ID),
                        memMarketChange.getToMarketId());
            } catch (Exception e) {
                throw new IntegrationException(IntegrationException.SEND_MESSAGE_FAILED, null);
            }
        } else {
            responseData.setSuccess(false);
        }
        return responseData;
    }

    /**
     * 会员查询.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 数据返回对象
     * @throws MemberException
     *             会员统一异常
     */
    @RequestMapping(value = "/mm/marketChange/queryMember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData memberQuery(HttpServletRequest request, @ModelAttribute Member member,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws MemberException {
        IRequest iRequest = createRequestContext(request);
        List<Member> members = memberService.queryMember(iRequest, member, page, pagesize);
        return new ResponseData(members);
    }

    /**
     * 提交变更市场批准.
     * 
     * @param request
     *            请求上下文
     * @param marketChanges
     *            会员变更市场DTO列表
     * @param result
     *            数据绑定结果
     * @return ResponseData 数据返回对象
     * @throws Exception
     *             异常
     */
    @RequestMapping(value = "/mm/marketChange/submitApprove", method = RequestMethod.POST)
    public ResponseData submitMarketChangeApprove(HttpServletRequest request,
            @RequestBody List<MemMarketChange> marketChanges, BindingResult result) throws Exception {
        IRequest iRequest = createRequestContext(request);
        Locale locale = RequestContextUtils.getLocale(request);
        String message = getMessageSource().getMessage(MemberConstants.MSG_APPLY_SUCCESS, null, locale);
        boolean isSuccess = false;
        MemMarketChange memMarketChange = marketChanges.get(0);
        memMarketChange.setApprovalStatus(MemberConstants.MARKET_CHANGE_APPROVED);
        Date date = new Date();
        memMarketChange.setLastUpdateDate(date);
        memMarketChange.setApprovalDate(date);
        Long lastUpdatedBy = getUserId(request);
        memMarketChange.setLastUpdatedBy(lastUpdatedBy);
        isSuccess = marketChangeService.approveMarketChange(iRequest, request, memMarketChange);
        ResponseData responseData = new ResponseData();
        if (!isSuccess) {
            responseData.setSuccess(false);
            return responseData;
        }
        responseData.setMessage(message);
        responseData.setCode(MemberConstants.MSG_APPLY_SUCCESS);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 提交变更市场拒绝.
     * 
     * @param request
     *            请求上下文
     * @param marketChanges
     *            会员变更市场DTO列表
     * @param result
     *            数据绑定结果
     * @return ResponseData 数据返回对象
     * @throws Exception
     *             异常
     */
    @RequestMapping(value = "/mm/marketChange/rejectApprove", method = RequestMethod.POST)
    public ResponseData submitMarketChangeReject(HttpServletRequest request,
            @RequestBody List<MemMarketChange> marketChanges, BindingResult result) throws Exception {
        IRequest iRequest = createRequestContext(request);
        Locale locale = RequestContextUtils.getLocale(request);
        String message = getMessageSource().getMessage(MemberConstants.MSG_REJECT_SUCCESS, null, locale);
        boolean isSuccess = false;
        MemMarketChange memMarketChange = marketChanges.get(0);
        memMarketChange.setApprovalStatus(MemberConstants.MARKET_CHANGE_REJECT);
        Date date = new Date();
        memMarketChange.setLastUpdateDate(date);
        memMarketChange.setApprovalDate(date);
        Long lastUpdatedBy = getUserId(request);
        memMarketChange.setLastUpdatedBy(lastUpdatedBy);
        isSuccess = marketChangeService.approveMarketChange(iRequest, request, memMarketChange);
        ResponseData responseData = new ResponseData();
        if (!isSuccess) {
            responseData.setSuccess(false);
            return responseData;
        }
        responseData.setMessage(message);
        responseData.setCode(MemberConstants.MSG_REJECT_SUCCESS);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 获取其他市场转入本市场（目标市场）申请的待审批列表.
     * 
     * @param request
     *            请求上下文
     * @param subOrg
     *            源销售机构（源市场）代号
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @return ResponseData 数据返回对象
     * @throws Exception
     *             异常
     */
    @RequestMapping(value = "/mm/marketChange/queryNewMarketChangeFromGds", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryNewMarketChangeFromGds(HttpServletRequest request, String subOrg,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws Exception {
        IRequest iRequest = createRequestContext(request);
        String orgCode = gdsUtilService.getCurrentOrgCode(iRequest);
        List<MemMarketChange> memMarketChanges = marketChangeService.queryNewMarketChangeFromGds(iRequest, subOrg,
                orgCode);
        if (memMarketChanges != null) {
            return new ResponseData(memMarketChanges);
        } else {
            return new ResponseData();
        }
    }

    /**
     * 删除转出市场申请.
     * 
     * @param request
     *            请求上下文
     * @param memMarketChanges
     *            市场变更DTO
     * @return ResponseData结果集
     * @throws Exception
     *             异常信息
     */
    @RequestMapping(value = "/mm/marketChange/deleteMarketChange", method = RequestMethod.POST)
    public ResponseData deleteMarketChange(HttpServletRequest request,
            @RequestBody List<MemMarketChange> memMarketChanges) throws Exception {
        IRequest iRequest = createRequestContext(request);
        marketChangeService.deleteMarketChange(iRequest, memMarketChanges);
        return new ResponseData(memMarketChanges);
    }

    /**
     * 获取本市场（源市场）转出申请的列表.
     * 
     * @param request
     *            HttpServletRequest
     * @param subOrg
     *            请求参数
     * @return ResponseData响应数据
     */
    @RequestMapping(value = "mm/marketChange/queryApplyList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryApplyList(HttpServletRequest request, String subOrg) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(isgMarketChangeListService.findGdealerChgOrgAppListService(requestContext, subOrg));
    }
}
