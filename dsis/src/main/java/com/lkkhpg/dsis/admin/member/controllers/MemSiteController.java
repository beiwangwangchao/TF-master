/*
 *
 */

package com.lkkhpg.dsis.admin.member.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.common.member.dto.MemSite;
import com.lkkhpg.dsis.common.service.ICommMemSiteService;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;

/**
 * 会员地点Controller.
 * 
 * @author frank.li
 */
@Controller
public class MemSiteController extends BaseController {

    @Autowired
    private ICommMemSiteService commMemSiteService;

    /**
     * 保存（创建/更新）会员地点.
     * 
     * @param memSites
     *            会员地点List
     * @param result
     *            数据绑定结果
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/mm/memsite/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveMember(@RequestBody List<MemSite> memSites, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(memSites, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest requestContext = createRequestContext(request);

        MemSite memSite = commMemSiteService.saveMemSite(requestContext, memSites.get(0));
        memSites.set(0, memSite);
        return new ResponseData(memSites);
    }
    

    /**
     * 删除会员地点.
     * @param request 请求上下文.
     * @param memSiteId MemberSiteId.
     * @return responseData 响应数据.
     */
    @RequestMapping(value = "/mm/memsite/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteMemberSite(HttpServletRequest request, Long memSiteId) {
        IRequest requestContext = createRequestContext(request);
        MemSite memsite = new MemSite();
        memsite.setSiteId(memSiteId);
        boolean result = commMemSiteService.deleteMemSite(requestContext, memsite);
        return new ResponseData(result);
    }
}
