/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.member.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 新会员审批Contorller.
 * 
 * @author mclin
 */
@Controller
public class MemberExamineController extends BaseController {

    @Autowired
    private IMemberService memberService;

    /**
     * 新会员审核会员查询.
     * 
     * @param request
     *            请求上下文
     * @param member
     *            会员DTO
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return responseData 响应数据
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @RequestMapping(value = "/mm/member/examineQuery")
    @ResponseBody
    public ResponseData queryMembers(HttpServletRequest request, Member member,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page, HttpServletResponse response,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws IllegalArgumentException, IllegalAccessException, IOException {
        IRequest requestContext = createRequestContext(request);
        List<Member> members = memberService.queryExamineMembers(requestContext, member, page, pagesize);
        /*for (Member mem : members) {
            if ((mem.getChineseName() == null && mem.getEnglishName() != null)) {
                mem.setMemberName(mem.getEnglishName());
            } else if (mem.getChineseName() != null && mem.getEnglishName() == null) {
                mem.setMemberName(mem.getChineseName());
            } else if (mem.getChineseName() != null && mem.getEnglishName() != null) {
                mem.setMemberName(mem.getEnglishName() + '/' + mem.getChineseName());
            }
        }*/
        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, members);
            return null;
        } else {
            return new ResponseData(members);
        }
    }

    /**
     * 新会员审核会员激活.
     * 
     * @param request
     *            请求上下文
     * @param members
     *            会员List
     * @return responseData 响应数据
     * @throws MemberException
     *             会员统一异常
     */
    @RequestMapping(value = "/mm/member/active")
    public ResponseData activeMembers(HttpServletRequest request, @RequestBody List<Member> members)
            throws MemberException {
        IRequest requestContext = createRequestContext(request);
        memberService.batchActive(requestContext, members);
        return new ResponseData();
    }

    /**
     * 新会员审核会员拒绝.
     * 
     * @param request
     *            请求上下文
     * @param members
     *            会员List
     * @return responseData 响应数据
     * @throws MemberException
     *             会员统一异常
     */
    @RequestMapping(value = "/mm/member/reject")
    public ResponseData rejectMembers(HttpServletRequest request, @RequestBody List<Member> members)
            throws MemberException {
        IRequest requestContext = createRequestContext(request);
        memberService.batchReject(requestContext, members);
        return new ResponseData();
    }
}
