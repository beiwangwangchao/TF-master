/*
 *
 */
package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.SysPreferences;
import com.lkkhpg.dsis.platform.service.ISysPreferencesService;

/**
 * 系统首选项Controller.
 * 
 * @author zhangYang
 *
 */
@Controller
public class SysPreferencesController extends BaseController {

    @Autowired
    private ISysPreferencesService sysPreferencesService;

    /**
     * 系统首选项保存
     * 
     * @param request
     *            统一上下文
     * @param sysPreferences
     *            系统首选项信息集合
     * @param result
     * @return ResponseData 返回保存首选项集合，保存错误返回false
     * 
     */
    @RequestMapping(value = "/sys/preferences/savePreferences")
    @ResponseBody
    public ResponseData savePreferences(final HttpServletRequest request,
            @RequestBody List<SysPreferences> sysPreferences, BindingResult result) {
        IRequest contextRequest = createRequestContext(request);
        for (SysPreferences sp : sysPreferences) {
            if (sp.getPreferencesLevel() == 30) {//若30则为系统级,不设置accountId
                continue;
            }
            sp.setAccountId(contextRequest.getAccountId());
        }
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            //set timezone
            sysPreferences.forEach(preference -> {
                if (BaseConstants.TIME_ZONE.equals(preference.getPreferences())) {
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.setAttribute(BaseConstants.TIME_ZONE, preference.getPreferencesValue());
                    }
                }
            });
            
            List<SysPreferences> lists = sysPreferencesService.saveSysPreferences(createRequestContext(request),
                    sysPreferences);
            return new ResponseData(lists);
        }

    }

    /**
     * 查询当前用户首选项集合
     * 
     * @param request
     * @param sysPreferences
     *            根据SysPreferences.accountId;SysPreferences.preferencesLevel查询条件
     *            查询当前首选项
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/sys/preferences/queryPreferences")
    @ResponseBody
    public ResponseData queryPreferences(final HttpServletRequest request, @RequestBody SysPreferences sysPreferences) {
        List<SysPreferences> lists = sysPreferencesService.querySysPreferences(createRequestContext(request),
                sysPreferences);
        return new ResponseData(lists);
    }
}
