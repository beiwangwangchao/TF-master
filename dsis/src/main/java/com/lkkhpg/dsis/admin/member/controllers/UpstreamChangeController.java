/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.admin.member.service.IUpstreamChangeService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemUpstreamChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.integration.gds.service.impl.GdsSwitch;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 会员上线变更控制层.
 * 
 * @author linyuheng
 */
@Controller
public class UpstreamChangeController extends BaseController {

    @Autowired
    private IUpstreamChangeService upstreamChangeService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private GdsSwitch gdsSwitch;

    /**
     * 查询会员上线变更记录.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            会员变更上线DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 数据返回对象
     */
    @RequestMapping(value = "/mm/upstreamChange/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryUpstreamChange(HttpServletRequest request,
            @ModelAttribute MemUpstreamChange upstreamChange, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(upstreamChangeService.queryUpstreamChange(iRequest, upstreamChange, page, pagesize));
    }

    /**
     * 查询原上线.
     * 
     * @param request
     *            请求上下文
     * @param memUpstreamChange
     *            会员变更上线DTO
     * @return ResponseData 数据返回对象
     * @throws IntegrationException
     *             接口统一异常
     * @throws MemberException
     *             会员异常
     */
    @RequestMapping(value = "/mm/upstreamChange/queryOldUpstream", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryOldUpstreamByManual(HttpServletRequest request,
            @ModelAttribute MemUpstreamChange memUpstreamChange) throws IntegrationException, MemberException {
        IRequest iRequest = createRequestContext(request);
        List<MemUpstreamChange> memUpstreamChanges = upstreamChangeService.queryOldUpstream(iRequest,
                memUpstreamChange);
        return new ResponseData(memUpstreamChanges);
    }

    /**
     * 检查新上线.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            变更市场DTO
     * @return ResponseData 数据返回对象
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口统一异常
     */
    @RequestMapping(value = "/mm/upstreamChange/queryNewUpstream", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryNewUpstream(HttpServletRequest request, @ModelAttribute MemUpstreamChange upstreamChange)
            throws MemberException, IntegrationException {
        IRequest iRequest = createRequestContext(request);
        upstreamChangeService.checkNewSponsorValidity(iRequest, upstreamChange);
        return new ResponseData();
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
    @RequestMapping(value = "/mm/upstreamChange/queryMember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryMember(HttpServletRequest request, @ModelAttribute Member member,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws MemberException {
        IRequest iRequest = createRequestContext(request);
        List<Member> members = memberService.queryMember(iRequest, member, page, pagesize);
        return new ResponseData(members);
    }

    /**
     * 提交变更上线信息.
     * 
     * @param request
     *            请求上下文
     * @param upstreamChange
     *            会员变更上线DTO
     * @param result
     *            数据绑定结果
     * @return ResponseData 数据返回对象
     * @throws MemberException
     *             会员统一异常
     * @throws IntegrationException
     *             接口统一异常
     */
    @RequestMapping(value = "/mm/upstreamChange/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitUpstreamChange(HttpServletRequest request,
            @ModelAttribute MemUpstreamChange upstreamChange, BindingResult result)
                    throws MemberException, IntegrationException {
        getValidator().validate(upstreamChange, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        String message = nls(request, MemberConstants.MSG_APPLY_SUCCESS);
        ResponseData responseData = new ResponseData();
        String memberCode = upstreamChangeService.submitUpstreamChange(iRequest, upstreamChange);
        if (!gdsSwitch.isSwitchFlag()) {
            throw new IntegrationException(IntegrationException.MSG_ERROR_GDS_IS_SHUTDOWN, null);
        }
        upstreamChangeService.applyMoveLine(iRequest, memberCode, upstreamChange);
        responseData.setMessage(message);
        responseData.setCode(MemberConstants.MSG_APPLY_SUCCESS);
        return responseData;
    }

    /**
     * 手动同步至GDS.
     * 
     * @param request
     *            请求上下文
     * @param memUpstreamChanges
     *            待同步的变更上线列表
     * @return ResponseData 数据返回对象
     * @throws IntegrationException
     *             接口异常
     */
    @RequestMapping(value = "/mm/upstreamChange/syntogds", method = RequestMethod.POST)
    public ResponseData synToGds(HttpServletRequest request, @RequestBody List<MemUpstreamChange> memUpstreamChanges)
            throws IntegrationException {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        upstreamChangeService.synToGds(iRequest, memUpstreamChanges);
        return responseData;
    }
}
