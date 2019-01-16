/*
 *
 */

package com.lkkhpg.dsis.platform.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.UserRole;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.service.IUserRoleService;

/**
 * 查询并保存角色的功能.
 * 
 * @author xiawang.liu@hand-china.com
 */
@Controller
public class UserRoleController extends BaseController {

    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 查询用户关联的所有角色.
     * 
     * @param request
     *            HttpServletRequest
     * @param example
     *            查询参数
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/sys/userrole/queryUserRoles")
    @ResponseBody
    public ResponseData getUserRoleIds(HttpServletRequest request, Role example) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userRoleService.selectUserRoles(requestContext, example));
    }

    /**
     * 保存为用户关联的所有角色.
     * 
     * @param request
     *            HttpServletRequest
     * @param userRoles
     *            角色列表
     * @param result
     *            BindingResult
     * @return ResponseData ResponseData
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = "/sys/userrole/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitUserRole(HttpServletRequest request, @RequestBody List<UserRole> userRoles,
            BindingResult result) throws BaseException {
        getValidator().validate(userRoles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userRoleService.processBatchUserRole(requestContext, userRoles));
    }

}
