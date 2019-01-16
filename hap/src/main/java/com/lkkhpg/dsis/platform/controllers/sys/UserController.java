/*
 *
 */
package com.lkkhpg.dsis.platform.controllers.sys;

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

import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.service.IUserService;

/**
 * UserController.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月29日
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 查询用户数据.
     *
     * @param user
     *            用户
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/user/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectUsers(@ModelAttribute User user, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(userService.selectUsers(user, page, pagesize));
    }

    /**
     * 保存更新账户数据.
     * 
     * @param users
     *            用户
     * @param result
     *            BindingResult
     * @param request
     *            请求上下文
     * @return ResponseData ResponseData
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = "/sys/user/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitUsers(@RequestBody List<User> users, BindingResult result, HttpServletRequest request)
            throws BaseException {
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        } else {
            IRequest requestCtx = createRequestContext(request);
            userService.batchUpdate(requestCtx, users);
            return new ResponseData(users);
        }
    }

    /**
     * 删除账户.
     *
     * @param users
     *            用户列表
     * @return ResponseData ResponseData
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = "/sys/user/remove", method = RequestMethod.POST)
    public ResponseData remove(@RequestBody List<User> users) throws BaseException {
        userService.batchDelete(users);
        return new ResponseData(users);
    }

}
