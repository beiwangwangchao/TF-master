/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.mws.adaptor;

import com.lkkhpg.dsis.common.config.dto.SpmCompany;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.ISpmCompanyService;
import com.lkkhpg.dsis.mws.service.IMemberAccountService;
import com.lkkhpg.dsis.mws.service.IMemberShopService;
import com.lkkhpg.dsis.platform.adaptor.impl.DefaultLoginAdaptor;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.SysPreferences;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.service.IProfileService;
import com.lkkhpg.dsis.platform.util.TimeZoneUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * MWS登陆适配器.
 *
 * @author zhaoqi
 * @author njq.niu@hand-china.com
 */
public class LoginAdaptor extends DefaultLoginAdaptor {

    private Logger logger = LoggerFactory.getLogger(LoginAdaptor.class);

    private static final String VIEW_UPDATE_PWD = "user/update_password.html";

    private static final int PASSWORD_RETRY_NO = 3;

    private static final int ACCOUNT_LOCK_TIME_MINUTES = 15;

    private static final int ACCOUNT_LOCK_TIME = ACCOUNT_LOCK_TIME_MINUTES * 60 * 1000;

    private static final String ACCOUNT_LOCK_PREFIX = "mws:account:login:lock.";

    private static final String ACCOUNT_LOCK_TRY_TIMES = "try_times";

    private static final String ACCOUNT_LOCK_LAST_LOGIN_TIME = "last_login_time";

    private static final String ACOUNT_SHOP = "/account/account-shop";

    private static final String ISDISPLAY = "isDisplay";

    @Autowired
    private IMemberAccountService loginMemberService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IMemberShopService memberShopService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private ISpmCompanyService spmCompanyService;

    /**
     * 查询登陆会员.
     */
    protected void afterLogin(ModelAndView view, Account account, HttpServletRequest request,
                              HttpServletResponse response) throws AccountException {
        // processPasswordFail(view,account,request,response);
        clearAccountLockStatus(account);
        HttpSession session = request.getSession();
        Locale locale = RequestContextUtils.getLocale(request);
        String message = null;
        Member member = loginMemberService.selectByMemberCode(account);

        //SpmSalesOrganization so = loginMemberService.selectByMarketId(member.getMarketId());
        if (member != null && session != null) {
            //view.clear();
            view.setViewName(REDIRECT + getIndexView(request));
            session.setAttribute(Member.FIELD_MEMBER_NAME, member.getLoginName());
            session.setAttribute(Member.FIELD_MEMBER_ID, member.getMemberId());
            session.setAttribute(SystemProfileConstants.MARKET_ID, member.getMarketId()); // 当前用户市场id
            session.setAttribute(SystemProfileConstants.MARKET_CODE, member.getMarketCode());
            //session.setAttribute(SystemProfileConstants.SALES_ORG_ID, 10201L); // TODO 预留功能
            session.setAttribute(Member.FIELD_MEMBER_CODE, member.getMemberCode());
            session.setAttribute(MemberConstants.FIELD_MEMBER_ROLE, member.getMemberRole());
            /*add by furong.tang*/
            IRequest requestContext = RequestHelper.createServiceRequest(request);
            SpmCompany company = new SpmCompany();
            company.setCompanyId(member.getCompanyId());
            List<SpmCompany> spmCompanyList = spmCompanyService.queryCompany(requestContext, company, 1, 1);
            String isDisplay = spmCompanyList.get(0).getAttribute1() == null ? "N" : spmCompanyList.get(0).getAttribute1();
            session.setAttribute(ISDISPLAY, isDisplay);
            //String crop_codes = profileService.getProfileValue(requestContext, "CROP.CODE");
            //session.setAttribute("cropCodes",crop_codes);
            // 会员状态为新建下
//            if (MemberConstants.MEMBER_STATUS_NEW.equals(member.getStatus())) {
//                // TODO:跳转预留
//            }
            // 激活、新建和审核状态下
            if (MemberConstants.MEMBER_STATUS_ACTIVE.equals(member.getStatus())
                    || MemberConstants.MEMBER_STATUS_NEW.equals(member.getStatus())
                    || MemberConstants.MEMBER_STATUS_PENDING.equals(member.getStatus())) {
                view.setViewName(REDIRECT + ACOUNT_SHOP);
            }
            // 是否首次登陆
            if (member.getFirstLoginFlag() != null && BaseConstants.YES.equals(member.getFirstLoginFlag())) {
                view.setViewName(REDIRECT + VIEW_UPDATE_PWD);
            }
            if (MemberConstants.MEMBER_STATUS_REJECTED.equals(member.getStatus())
                    || MemberConstants.MEMBER_STATUS_SUSPENDED.equals(member.getStatus())
                    || MemberConstants.MEMBER_STATUS_TERMINATED.equals(member.getStatus())
                    || MemberConstants.MEMBER_STATUS_AUTO_TERMINATED.equals(member.getStatus())
                    || MemberConstants.MEMBER_STATUS_WCHG.equals(member.getStatus())) {
                message = messageSource.getMessage(AccountException.MSG_LOGIN_ACCOUNT_NOT_INACTIVE, null, locale);
                view.setViewName(getLoginView(request));
                view.addObject("msg", message);
            }
        } else {
            message = messageSource.getMessage(AccountException.ACCOUNT_TYPE_ERROR, null, locale);
            view.setViewName(getLoginView(request));
            view.addObject("msg", message);
        }
    }

    /**
     * 清除账户锁定状态.
     *
     * @param account
     */
    private void clearAccountLockStatus(Account account) {
        String key = ACCOUNT_LOCK_PREFIX + account.getLoginName();
        redisTemplate.execute((RedisCallback<?>) (connection) -> {
            connection.del(key.getBytes());
            return null;
        });
    }

    /**
     * 判断登陆账号是否锁定.
     *
     * @param view
     * @param account
     * @param request
     * @param response
     * @throws AccountException
     */
    @Override
    protected void beforeLogin(ModelAndView view, Account account, HttpServletRequest request,
                               HttpServletResponse response) throws AccountException {
        String key = ACCOUNT_LOCK_PREFIX + account.getLoginName();
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        String tryTimes = (String) hash.get(key, ACCOUNT_LOCK_TRY_TIMES);
        long lastLoginTime = -1;
        try {
            if (tryTimes != null && Long.parseLong(tryTimes) > PASSWORD_RETRY_NO) {
                String ts = (String) hash.get(key, ACCOUNT_LOCK_LAST_LOGIN_TIME);
                lastLoginTime = Long.parseLong(ts);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("parse last_login_time error.", e);
            }
        }
        if (System.currentTimeMillis() - lastLoginTime < ACCOUNT_LOCK_TIME) {
            throw new AccountException(AccountException.MSG_LOGIN_ACCOUNT_LOCK_MAX_RETRY_TIMES,
                    AccountException.MSG_LOGIN_ACCOUNT_LOCK_MAX_RETRY_TIMES,
                    new Object[]{PASSWORD_RETRY_NO, ACCOUNT_LOCK_TIME_MINUTES});
        }
    }

    protected void setTimeZoneFromPreference(HttpSession session, Long accountId) {
        SysPreferences para = new SysPreferences();
        para.setAccountId(accountId);
        para.setPreferencesLevel(20L);
        para.setPreferences(BaseConstants.TIME_ZONE);
        SysPreferences pref = getPreferencesService().querySysPreferencesLine(RequestHelper.newEmptyRequest(), para);
        String tz = pref == null ? null : pref.getPreferencesValue();
        if (StringUtils.isBlank(tz)) {
            tz = TimeZoneUtil.toGMTFormat(TimeZone.getDefault());
        }
        session.setAttribute(BaseConstants.TIME_ZONE, tz);
    }

    /**
     * 登陆失败次数统计.
     */
    @Override
    protected void processLoginException(ModelAndView view, Account account, AccountException ae,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (AccountException.MSG_LOGIN_NAME_PASSWORD.equals(ae.getCode())) {
            String key = ACCOUNT_LOCK_PREFIX + account.getLoginName();
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            Long tryTimes = hash.increment(key, ACCOUNT_LOCK_TRY_TIMES, 1);
            if (tryTimes < PASSWORD_RETRY_NO) {
                hash.put(key, ACCOUNT_LOCK_LAST_LOGIN_TIME, String.valueOf(System.currentTimeMillis()));
            }
        }
    }

    /**
     * MWS默认登陆地址.
     */
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(REDIRECT + "/index.html");
        return view;
    }

}
