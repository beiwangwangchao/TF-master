/*
 *
 */
package com.lkkhpg.dsis.platform.adaptor.impl;

import com.lkkhpg.dsis.platform.adaptor.ILoginAdaptor;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.ILanguageProvider;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.*;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.exception.RoleException;
import com.lkkhpg.dsis.platform.security.TokenUtils;
import com.lkkhpg.dsis.platform.security.captcha.ICaptchaManager;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.IRoleService;
import com.lkkhpg.dsis.platform.service.ISysPreferencesService;
import com.lkkhpg.dsis.platform.service.IUserService;
import com.lkkhpg.dsis.platform.util.TimeZoneUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 默认登陆代理类.
 * 
 * @author njq.niu@hand-china.com
 * @author xiawang.liu@hand-china.com 2016年1月19日
 * TODO:URL和页面分开
 */
@Component
public class DefaultLoginAdaptor implements ILoginAdaptor {

    // 跳转
    protected static final String REDIRECT = "redirect:";

    // 校验码
    private static final String KEY_VERIFICODE = "verifiCode";

    // 默认主页
    private static final String VIEW_INDEX = "/";

    // 默认的登录页
    private static final String VIEW_LOGIN = "/login";

    private static final String VIEW_LOGIN_NEW= "/loginHJF";
    // 默认的登录页
    private static final String VIEW_LOGIN_ADMIN = "/loginUserInfo";

    // 默认角色选择路径
    private static final String VIEW_ROLE_SELECT = "/role";

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ICaptchaManager captchaManager;

    @Autowired
    private ILanguageProvider languageProvider;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysPreferencesService preferencesService;

    public ISysPreferencesService getPreferencesService() {
        return preferencesService;
    }

    @Override
    public ModelAndView doLogin(Account account, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Locale locale = RequestContextUtils.getLocale(request);
        view.setViewName(getLoginView(request));
        fillContextLanguagesData(view);
        System.out.println("Default Adapter");
        System.out.println(view.toString());

        // 记录用户输入的用户名，登录失败刷新页面时，不需要重新输入
        try {
            beforeLogin(view, account, request, response);
            //checkCaptcha(view, account, request, response);
            //updated by 13525 at 2018.03.09
            if (!"N".equals(account.getFirstLoginFlag())) {
                checkCaptcha(view, account, request, response);
            }
            account.setFirstLoginFlag("");
            account = accountService.login(account);
            HttpSession session = request.getSession(true);
            session.setAttribute(Account.FIELD_ACCOUNT_ID, account.getAccountId());
            session.setAttribute(Account.FIELD_LOGIN_NAME, account.getLoginName());
            session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
            setTimeZoneFromPreference(session, account.getAccountId());
            generateSecurityKey(session);
            afterLogin(view, account, request, response);
        } catch (AccountException e) {
            view.addObject("_loginName", account.getLoginName());
            view.addObject("msg", messageSource.getMessage(e.getCode(), e.getParameters(), locale));
            view.addObject("code", e.getCode());
            processLoginException(view, account, e, request, response);
        }
        return view;
    }

    @Override
    public ModelAndView doLoginSomeOtherUsers(Account account, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Locale locale = RequestContextUtils.getLocale(request);
        modelAndView.setViewName(getViewLoginAdminView(request));
        fillContextLanguagesData(modelAndView);
        try {
            beforeLogin(modelAndView, account, request, response);

            if (!"N".equals(account.getFirstLoginFlag())) {
                checkCaptcha(modelAndView, account, request, response);
            }
            account.setFirstLoginFlag("");
            account = accountService.loginSomeOtherUsers(account);
            HttpSession session = request.getSession(true);
            session.setAttribute(Account.FIELD_ACCOUNT_ID, account.getAccountId());
            session.setAttribute(Account.FIELD_LOGIN_NAME, account.getLoginName());
            session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
            setTimeZoneFromPreference(session, account.getAccountId());
            generateSecurityKey(session);
            afterLogin(modelAndView, account, request, response);
        } catch (AccountException e) {
            modelAndView.addObject("_loginName", account.getLoginName());
            modelAndView.addObject("msg", messageSource.getMessage(e.getCode(), e.getParameters(), locale));
            modelAndView.addObject("code", e.getCode());
            processLoginException(modelAndView, account, e, request, response);
        }
        return modelAndView;
    }


    protected void setTimeZoneFromPreference(HttpSession session, Long accountId) {
        SysPreferences para = new SysPreferences();
        para.setAccountId(accountId);
        para.setPreferencesLevel(10L);
        para.setPreferences(BaseConstants.TIME_ZONE);
        SysPreferences pref = preferencesService.querySysPreferencesLine(RequestHelper.newEmptyRequest(), para);
        String tz = pref == null ? null : pref.getPreferencesValue();
        if (StringUtils.isBlank(tz)) {
            tz = TimeZoneUtil.toGMTFormat(TimeZone.getDefault());
        }
        session.setAttribute(BaseConstants.TIME_ZONE, tz);
    }

    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    /**
     * 登陆前逻辑.
     * 
     * @param view
     *            视图
     * @param account
     *            账号
     * @param request
     *            请求
     * @param response
     *            响应
     * @throws AccountException
     *             异常
     */
    protected void beforeLogin(ModelAndView view, Account account, HttpServletRequest request,
            HttpServletResponse response) throws AccountException {

    }

    /**
     * 处理登陆异常.
     * 
     * @param view
     * @param account
     * @param e
     * @param request
     * @param response
     */
    protected void processLoginException(ModelAndView view, Account account, AccountException e,
            HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 校验验证码是否正确.
     * 
     * @param view
     *            视图
     * @param account
     *            账号
     * @param request
     *            请求
     * @param response
     *            响应
     * @throws AccountException
     *             异常
     */
    private void checkCaptcha(ModelAndView view, Account account, HttpServletRequest request,
            HttpServletResponse response) throws AccountException {
        if (captchaManager.isValidateFlag()) {
            Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
            String captchaCode = request.getParameter(KEY_VERIFICODE);
            if (cookie == null || StringUtils.isEmpty(captchaCode)
                    || !captchaManager.checkCaptcha(cookie.getValue(), captchaCode)) {
                //view.addObject("_password", account.getPassword());
                throw new AccountException(AccountException.MSG_LOGIN_INVALID_CODE,
                        AccountException.MSG_LOGIN_INVALID_CODE, null);
            }
        }
    }

    /**
     * 账号登陆成功后处理逻辑.
     * 
     * @param view
     *            视图
     * @param account
     *            账号
     * @param request
     *            请求
     * @param response
     *            响应
     * @throws AccountException
     *             异常
     */
    protected void afterLogin(ModelAndView view, Account account, HttpServletRequest request,
            HttpServletResponse response) throws AccountException {
        view.setViewName(REDIRECT + getRoleView(request));
        Cookie cookie = new Cookie(Account.FIELD_LOGIN_NAME, account.getLoginName());
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        System.out.println("parent after login");
        response.addCookie(cookie);
    }

    @Override
    public ModelAndView doLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView(REDIRECT + getLoginView(request));
    }

    @Override
    public ModelAndView doSelectRole(Role role, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ModelAndView result = new ModelAndView();
        // 选择角色
        HttpSession session = request.getSession(false);
        if (session != null && role != null && role.getRoleId() != null) {
            Long userId = (Long) session.getAttribute(User.FILED_USER_ID);
            roleService.checkUserRoleExists(userId, role.getRoleId());
            session.setAttribute(Role.FIELD_ROLE_ID, role.getRoleId());
            result.setViewName(REDIRECT + getIndexView(request));
        } else {
            result.setViewName(REDIRECT + getLoginView(request));
        }
        return result;
    }

    /**
     * 填充系统语言数据.
     * 
     * @param view
     */
    protected void fillContextLanguagesData(ModelAndView view) {
        List<Language> languages = languageProvider.getSupportedLanguages();
        view.addObject("languages", languages);
    }

    /**
     * 获取主界面.
     * 
     * @param request
     * @return 视图
     */
    protected String getIndexView(HttpServletRequest request) {
        return VIEW_INDEX;
    }

    /**
     * 获取登陆界面.
     * 
     * @param request
     * @return 视图
     */
    protected String getLoginView(HttpServletRequest request) {
        return VIEW_LOGIN;
    }

    protected String getLoginHJFView(HttpServletRequest request) {
        return VIEW_LOGIN_NEW;
    }


    protected String getViewLoginAdminView(HttpServletRequest request) {
        return VIEW_LOGIN_ADMIN;
    }
    /**
     * 获取角色选择界面.
     * 
     * @param request
     * @return 视图
     */
    protected String getRoleView(HttpServletRequest request) {
        return VIEW_ROLE_SELECT;
    }

    /**
     * 集成类中可扩展此方法实现不同的userService.
     * 
     * @return IUserService
     */
    public IUserService getUserService() {
        return userService;
    }

    @Override
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
            if (roleId == null) {
                return new ModelAndView(REDIRECT + getRoleView(request));
            }
        }

        ModelAndView view = indexModelAndView(request, response);
        fillContextLanguagesData(view);
        return view;
    }

    /**
     * 默认登陆页面.
     * 
     * @param request
     * @param response
     * @return 视图
     */
    public ModelAndView indexModelAndView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @Override
    public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(getLoginView(request));
        fillContextLanguagesData(view);
        return view;
    }

    @Override
    public ModelAndView loginHJFView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(getLoginHJFView(request));
        fillContextLanguagesData(view);
        return view;
    }


    @Override
    public ModelAndView loginAdminView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(getViewLoginAdminView(request));
        fillContextLanguagesData(view);
        return view;
    }
    @Override
    public ModelAndView roleView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView(getRoleView(request));
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 获取user
            Long accountId = (Long) session.getAttribute(Account.FIELD_ACCOUNT_ID);
            if (accountId != null) {
                Long userId = getUserService().getUserIdByAccountId(accountId);
                if (userId != null) {
                    User user = new User();
                    user.setUserId(userId);
                    session.setAttribute(User.FILED_USER_ID, userId);
//                    addCookie(User.FILED_USER_ID, userId.toString(), request, response);
                    List<Role> roles = roleService.selectRolesByUser(RequestHelper.createServiceRequest(request), user);
                    mv.addObject("roles", roles);
                }
            }
        }
        return mv;
    }
    
    
    protected void addCookie(String cookieName, String cookieValue, HttpServletRequest request,
            HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @Override
    public ResponseData sessionExpiredLogin(Account account, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ResponseData data = new ResponseData();
        ModelAndView view = this.doLogin(account, request, response);
        ModelMap mm = view.getModelMap();
        if (mm.containsAttribute("code")) {
            data.setSuccess(false);
            data.setCode((String) mm.get("code"));
            data.setMessage((String) mm.get("msg"));
        } else {
            Object roleIdObj = request.getParameter(Role.FIELD_ROLE_ID);
            HttpSession session = request.getSession(false);
            if (session != null) {
                Long accountId = (Long) session.getAttribute(Account.FIELD_ACCOUNT_ID);
                Long userId = getUserService().getUserIdByAccountId(accountId);
                session.setAttribute(User.FILED_USER_ID, userId);
                if (roleIdObj != null) {
                    Long roleId = Long.valueOf(roleIdObj.toString());
                    roleService.checkUserRoleExists(userId, roleId);
                    session.setAttribute(Role.FIELD_ROLE_ID, roleId);
                }
            }
        }
        return data;
    }
}