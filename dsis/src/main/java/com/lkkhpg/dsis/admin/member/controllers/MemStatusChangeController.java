/*
 *
 */

package com.lkkhpg.dsis.admin.member.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemStatusChangeService;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.member.dto.MemStatusChange;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 状态变更Controller.
 * 
 * @author yuchuan.zeng@hand-china.com
 */
@Controller
public class MemStatusChangeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MemStatusChangeController.class);

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IMemStatusChangeService memStatusChangeService;

    /**
     * 会员状态变更记录查询(根据memberId和申请日期查询).
     * 
     * @param memStatusChange
     *            会员状态变更dto
     * @param page
     *            第几页
     * @param pagesize
     *            每页显示几条
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @RequestMapping(value = "mm/statusChange/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getMemStatusChanges(@ModelAttribute MemStatusChange memStatusChange,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page, HttpServletResponse response,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest requestContext = createRequestContext(request);

        List<MemStatusChange> memStatusChanges = memStatusChangeService.queryApplyDateAndMemberId(requestContext, memStatusChange, page, pagesize);
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, memStatusChanges);
            return null;
        } else {
            return new ResponseData(memStatusChanges);
        }
    }

    /**
     * 批量保存MemStatusChange.
     * 
     * @param memStatusChanges
     *            会员状态变更dto
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws MemberException
     *             会员异常
     * @throws IntegrationException
     *             接口异常
     */
    @RequestMapping(value = "mm/statusChange/bulksave", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseData bulkSaveMemStatusChanges(@RequestBody List<MemStatusChange> memStatusChanges,
            HttpServletRequest request) throws MemberException, IntegrationException {
        IRequest requestContext = createRequestContext(request);
        if (logger.isDebugEnabled()) {
            logger.debug("memStatusChanges.size(): {}", memStatusChanges.size());
            logger.debug("bulksave, memStatusChanges: {}", memStatusChanges);
        }

        List<MemStatusChange> listChanges = memStatusChangeService.saveMemStatusChanges(requestContext,
                memStatusChanges);
        return new ResponseData(listChanges);
    }

    /**
     * 保存MemStatusChange.
     * 
     * @param memStatusChange
     *            会员状态变更dto
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws MemberException
     *             会员异常
     * @throws IntegrationException
     *             接口异常
     */
    @RequestMapping(value = "mm/statusChange/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveMemStatusChange(@RequestBody MemStatusChange memStatusChange, HttpServletRequest request)
            throws MemberException, IntegrationException {
        IRequest requestContext = createRequestContext(request);
        MemStatusChange m = memStatusChangeService.saveMemStatusChange(requestContext, memStatusChange);
        List<MemStatusChange> memStatusChanges = Arrays.asList(m);
        ResponseData r = new ResponseData(memStatusChanges);
        if (MemberConstants.MEM_STATUS_CHANGE_REACTIVE.equals(m.getOperationType())
                || MemberConstants.MEM_STATUS_CHANGE_SUSPEND.equals(m.getOperationType())) {
            r.setMessage(nls(request, MemberConstants.MSG_CHANGE_SUCCESS));
        } else {
            r.setMessage(nls(request, MemberConstants.MSG_APPLY_SUCCESS));
        }
        return r;
    }

    /**
     * @param member
     *            会员dto
     * @param page
     *            第几页
     * @param pagesize
     *            每页几条
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws MemberException
     *             会员异常
     */
    @RequestMapping(value = "mm/statusChange/querymembers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryMembers(Member member, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
                    throws MemberException {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(memberService.queryMember(requestContext, member, page, pagesize));
    }

    /**
     * 根据memberCode查询members并且根据判断是否status有有效的记录.
     * 
     * @param member
     *            会员dto
     * @param page
     *            第几页
     * @param pagesize
     *            每页几条
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws MemberException
     *             会员异常
     */
    @RequestMapping(value = "mm/statusChange/queryMembersByCode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData queryMembersByCode(Member member, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
                    throws MemberException {
        IRequest requestContext = createRequestContext(request);
        List<Member> members = memberService.queryMember(requestContext, member, page, pagesize);
        ResponseData responseData = new ResponseData(false);
        for (Member m : members) {
            if (MemberConstants.MEMBER_STATUS_ACTIVE.equals(m.getStatus())
                    || MemberConstants.MEMBER_STATUS_SUSPENDED.equals(m.getStatus())) {
                List<Member> result = Arrays.asList(m);
                responseData.setRows(result);
                /* mclin修改 */
                responseData.setSuccess(true);
                return responseData;
            }
        }
        return responseData;
    }

    /**
     * 手动同步至GDS.
     * 
     * @param request
     *            请求上下文
     * @param memStatusChange
     *            待同步的变更状态列表
     * @return ResponseData 数据返回对象
     * @throws IntegrationException
     *             接口异常
     */
    @RequestMapping(value = "/mm/statusChange/syntogds", method = RequestMethod.POST)
    public ResponseData synToGds(HttpServletRequest request, @RequestBody List<MemStatusChange> memStatusChange)
            throws IntegrationException {
        IRequest iRequest = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        memStatusChangeService.synToGds(iRequest, memStatusChange);
        return responseData;
    }

    /**
     * 判断会员是否有一条状态为“审核中”的终止或者自动终止记录.
     * 
     * @param memStatusChange
     *            会员状态变更DTO
     * @param request
     *            请求上下文
     * @return ResponseData 响应数据
     * @throws MemberException
     *             会员异常
     */
    @RequestMapping(value = "mm/statusChange/validRecord", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData validRecord(MemStatusChange memStatusChange, HttpServletRequest request)
            throws MemberException {
        IRequest requestContext = createRequestContext(request);
        boolean hasRecord = memStatusChangeService.validRecord(requestContext, memStatusChange);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("isSuccess", hasRecord);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(result);
        ResponseData responseData = new ResponseData(list);
        return responseData;
    }
}
