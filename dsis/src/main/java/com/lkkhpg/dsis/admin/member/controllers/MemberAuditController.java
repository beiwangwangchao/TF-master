/*
 *
 */

package com.lkkhpg.dsis.admin.member.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.admin.member.service.IMemberAuditService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 会员审计Controller.
 * 
 * @author shengyang.zhou@hand-china.com
 */
@Controller
public class MemberAuditController extends BaseController {

    @Autowired
    private IMemberAuditService auditService;
    
    /**
     * 会员审计查询.
     * 
     * @param request
     *            请求上下文
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 数据返回对象
     */
    @RequestMapping("/mm/member/audit")
    @ResponseBody
    public ResponseData getAuditHistory(HttpServletRequest request,Date auditTimestampFrom, Date auditTimestampTo,
    									String auditBy, String auditTransactionType, Long memberId,
                                        @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        Map<String, Object> param = new HashMap<>();
        //request.getParameterMap().forEach((k, v) -> param.put(k, v[0]));
        param.put("auditTimestampFrom", auditTimestampFrom);
        param.put("auditTimestampTo", auditTimestampTo);
        param.put("auditBy", auditBy);
        param.put("auditTransactionType", auditTransactionType);
        param.put("memberId", memberId);
        return new ResponseData(auditService.selectAuditHistory(iRequest, param, page, pagesize));
    }
    
    /**
     * 会员相关人审计查询.
     * 
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping("/mm/member/relationship/audit")
    @ResponseBody
    public ResponseData getRelationshipAuditHistory(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Map<String, Object> param = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> param.put(k, v[0]));
        return new ResponseData(auditService.selectRelationshipAuditHistory(iRequest, param));
    }
    
    /**
     * 会员信用卡审计查询.
     * 
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping("/mm/member/card/audit")
    @ResponseBody
    public ResponseData getCardAuditHistory(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Map<String, Object> param = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> param.put(k, v[0]));
        return new ResponseData(auditService.selectCardAuditHistory(iRequest, param));
    }
    
    /**
     * 会员地址审计查询.
     * 
     * @param request
     *            请求上下文
     * @return ResponseData 数据返回对象
     */
    @RequestMapping("/mm/member/site/audit")
    @ResponseBody
    public ResponseData getSiteAuditHistory(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Map<String, Object> param = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> param.put(k, v[0]));
        return new ResponseData(auditService.selectSiteAuditHistory(iRequest, param));
    }
    
    
}
