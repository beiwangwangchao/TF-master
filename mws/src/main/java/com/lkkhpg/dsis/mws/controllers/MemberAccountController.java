/*
 *
 */
package com.lkkhpg.dsis.mws.controllers;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SMSConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.service.IMemberAccountService;

import com.lkkhpg.dsis.platform.adaptor.ILoginAdaptor;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.security.captcha.ICaptchaManager;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 会员登录、密码controller.
 *
 * @author Zhaoqi
 */
@Controller
public class MemberAccountController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(MemberAccountController.class);

    @Autowired
    private IMemberAccountService memberAccountService;
    @Autowired
    private ICaptchaManager captchaManager;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    ILoginAdaptor loginAdaptor;

    /**
     * 会员用户首次修改密码.
     *
     * @param request  统一上下文
     * @param newPwd   新密码
     * @param againPwd 再次输入
     * @return 无返回操作
     * @throws AccountException 账户异常
     */
    @RequestMapping(value = "/account/updatePassword", method = RequestMethod.POST)
    public ResponseData updatePassword(HttpServletRequest request, String newPwd, String againPwd)
            throws AccountException {
        IRequest requestContext = createRequestContext(request);
        memberAccountService.updatePassword(requestContext, newPwd, againPwd);
        return new ResponseData();
    }


    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    public ResponseData createAccount(HttpServletRequest request, String uname, String password, String phoneNum) throws AccountException {
        IRequest requestContext = createRequestContext(request);
        Member member = new Member();
        member.setPhoneNo(phoneNum);
        member.setLoginName(uname);

        memberAccountService.createAccount(requestContext, member);


        return new ResponseData();
    }


    /**
     * mws忘记密码-第一步页面请求.
     *
     * @param request   统一上下文
     * @param loginName 会员用户名
     * @return 响应信息
     * @throws AccountException 账户异常
     */
    @RequestMapping(value = "/account/isExistsMember")
    public ResponseData isExistsMember(HttpServletRequest request, String loginName) throws AccountException {
        IRequest requestContext = createRequestContext(request);
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 校验码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new AccountException(MemberException.ERROR_SEND_VERIFICATION, MemberException.ERROR_SEND_VERIFICATION,
                    new Object[]{});
        }
        Account account = new Account();
        account.setLoginName(loginName);
        Member member = memberAccountService.isExistsMember(requestContext, account);
        if (member == null) {
            throw new AccountException(MemberException.MENBER_NOT_EXIST, MemberException.MENBER_NOT_EXIST,
                    new Object[]{});
        }

        if (MemberConstants.MM_ROLE_VIP.equals(member.getMemberRole())) {
            throw new AccountException(MemberException.NOT_YET_DISTRIBUTOR, MemberException.NOT_YET_DISTRIBUTOR,
                    new Object[]{});
        }
        if (MemberConstants.MEMBER_STATUS_REJECTED.equals(member.getStatus())
                || MemberConstants.MEMBER_STATUS_SUSPENDED.equals(member.getStatus())
                || MemberConstants.MEMBER_STATUS_TERMINATED.equals(member.getStatus())
                || MemberConstants.MEMBER_STATUS_AUTO_TERMINATED.equals(member.getStatus())) {
            throw new AccountException(MemberException.MEMBER_STATUS_EXCEPTION, MemberException.MEMBER_STATUS_EXCEPTION,
                    new Object[]{});
        }
        /*HttpSession session = request.getSession();
        session.setAttribute(Account.FIELD_ACCOUNT_ID, member.getAccountId());*/
        String uuidKey = generateUuid();
        redisTemplate.opsForValue().set(MemberConstants.FORGER_PWD_KEY + ":" + uuidKey,
                member.getAccountId().toString(), MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uuidKey", uuidKey);
        list.add(map);
        return new ResponseData(list);
    }

    /**
     * 获取会员信息.
     *
     * @param request 统一上下文
     * @param uuidKey uuid
     * @return 返回会员信息
     */
    @RequestMapping(value = "/account/getMemberInfo")
    public ResponseData getMemberInfo(HttpServletRequest request, String uuidKey) {
        Account account = new Account();
        IRequest requestContext = createRequestContext(request);
        Long accountId = Long
                .parseLong(redisTemplate.opsForValue().get(MemberConstants.FORGER_PWD_KEY + ":" + uuidKey));
        account.setAccountId(accountId);
        Member member = memberAccountService.isExistsMember(requestContext, account);
        StringBuilder secretLoginName = new StringBuilder(member.getLoginName());
        for (int i = 2; i < secretLoginName.length() - 2; i++) { // 用户名6-20位,显示前两位后两位
            secretLoginName.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
        }
        Member mem = new Member();
        mem.setLoginName(secretLoginName.toString());
        // email
        if (member.getEmail() != null && !"".equals(member.getEmail())) { // 如果用户有邮箱
            StringBuilder secretEmail = new StringBuilder(member.getEmail());
            int subLength = secretEmail.substring(0, secretEmail.indexOf(UserConstants.SC_EMAIL_AT)).length();
            if (subLength > 2) { // @之前的长度大于2位显示后两位
                for (int i = 0; i < subLength - 2; i++) {
                    secretEmail.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
                }
            } else { // 否则,显示最后一位
                for (int i = 0; i < subLength - 1; i++) {
                    secretEmail.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
                }
            }
            mem.setEmail(secretEmail.toString());
        }
        // phone
        StringBuilder secretPhone = new StringBuilder(member.getPhoneNo());
        for (int i = 0; i < secretPhone.length() - UserConstants.SC_PHONE_AFTER_HIDE_REMINE; i++) { // 手机,显示后四位
            secretPhone.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
        }
        mem.setPhoneNo(secretPhone.toString());
        // 隐藏部分loginName/email/phone信息---end
        List<Member> list = new ArrayList<Member>();
        list.add(mem);
        return new ResponseData(list);
    }

    /**
     * 忘记密码-发送验证码请求.
     *
     * @param request 统一上下文
     * @param way     发送到phone或者email
     * @param uuidKey uuid
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/account/sendCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData sendCaptcha(HttpServletRequest request, String way, String uuidKey) throws Exception {
        IRequest requestContext = createRequestContext(request);

        Account account = new Account();
        Long accountId = Long
                .parseLong(redisTemplate.opsForValue().get(MemberConstants.FORGER_PWD_KEY + ":" + uuidKey));
        account.setAccountId(accountId);
        String vcode = "";
        Member member = memberAccountService.isExistsMember(requestContext, account);
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        if (UserConstants.SC_VERIFY_WAY_PHONE.equals(way)) {
            if (redisTemplate.opsForValue()
                    .get(MemberConstants.FIELD_WAY_TIME_PASSWORD_PHONE + ":" + uuidKey) != null) {
                Long birthInMillis = Long.parseLong(
                        redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PASSWORD_PHONE + ":" + uuidKey));
                Long nowInMillis = new Date().getTime();
                // 距离上次发送验证码不足60秒
                if ((nowInMillis - birthInMillis) < MemberConstants.VERIFICATION_WAITING_TIME) {
                    throw new AccountException(MemberException.SEND_TIME, MemberException.SEND_TIME, new Object[]{});
                }
            }
            redisTemplate.opsForValue().set(MemberConstants.FIELD_WAY_TIME_PASSWORD_PHONE + ":" + uuidKey,
                    Long.toString(new Date().getTime()), MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            vcode = generateCheckCode();
            redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_VERIFICODE, vcode,
                    MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                    MemberConstants.FIELD_VERIFICATION_PHONE, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            // 根据accountId获取marketId
            Member mb = memberAccountService.selectByMemberCode(account);
            // 发送验证码到手机
            receiver.setMessageAddress(member.getAreaPhone());
            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receiver.setReceiverId(accountId);
            receiverlist.add(receiver);
            // 设置message模板里的数据
            data.put("verifyCode", vcode);
            data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
            data.put("loginName", member.getLoginName());

            /*sign by furong.tang*/
            //updated by 11816 on 2017/12/15 15:46
            String sms_interface_address = profileService.getProfileValue(requestContext, SMSConstants.SMS_INTERFACE_ADDRESS);
            String sms_userid = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERID);
            String sms_username = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERNAME);
            String sms_password = profileService.getProfileValue(requestContext, SMSConstants.SMS_PASSWORD);

            data.put("sms_interface_address", sms_interface_address);
            data.put("sms_userid", sms_userid);
            data.put("sms_username", sms_username);
            data.put("sms_password", sms_password);

            messageService.sendSmsMessage(-1L, mb.getMarketId(), MemberConstants.PHONE_SC_FORGET_PASSWORD, "PASSWORD",
                    data, receiverlist);


            if (logger.isDebugEnabled()) {
                logger.debug("此时发送验证码到用户手机(--->{}),验证码为{}", member.getAreaPhone(), vcode);
            }
        } else if (UserConstants.SC_VERIRY_WAY_EMAIL.equals(way)) {
            if (redisTemplate.opsForValue()
                    .get(MemberConstants.FIELD_WAY_TIME_PASSWORD_EMAIL + ":" + uuidKey) != null) {
                Long birthInMillis = Long.parseLong(
                        redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PASSWORD_EMAIL + ":" + uuidKey));
                Long nowInMillis = new Date().getTime();
                // 距离上次发送验证码不足60秒
                if ((nowInMillis - birthInMillis) < MemberConstants.VERIFICATION_WAITING_TIME) {
                    throw new AccountException(MemberException.SEND_TIME, MemberException.SEND_TIME, new Object[]{});
                }
            }
            redisTemplate.opsForValue().set(MemberConstants.FIELD_WAY_TIME_PASSWORD_EMAIL + ":" + uuidKey,
                    Long.toString(new Date().getTime()), MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            vcode = generateCheckCode();
            redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_VERIFICODE, vcode,
                    MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                    MemberConstants.FIELD_VERIFICATION_EMAIL, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            // 发送验证码到手机
            receiver.setMessageAddress(member.getEmail());
            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receiver.setReceiverId(accountId);
            receiverlist.add(receiver);
            // 设置message模板里的数据
            data.put("verifyCode", vcode);
            data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
            data.put("loginName", member.getLoginName());
            messageService.sendEmailMessage(-1L, null, "EMAIL_SC_FORGET_PASSWORD", "PASSWORD", data, receiverlist,
                    null);
            if (logger.isDebugEnabled()) {
                logger.debug("此时发送验证码到用户邮箱(--->{}),验证码为{}", member.getEmail(), vcode);
            }
        }
        //List<String> list = new ArrayList<String>();
        //list.add(vcode);
        return new ResponseData();
    }


    /**
     * mws忘记密码-第二步页面提交请求.
     *
     * @param request    统一上下文
     * @param verifiCode 校验码
     * @param way        验证方式
     * @param uuidKey    uuid
     * @return 响应信息
     * @throws AccountException
     */
    @RequestMapping(value = "/account/checkCaptcha")
    public ResponseData checkCaptcha(HttpServletRequest request, String verifiCode, String way, String uuidKey)
            throws AccountException {
        if (("").equals(verifiCode)) {
            throw new AccountException(MemberException.VERIFICATION_NOT_EMPTY, MemberException.VERIFICATION_NOT_EMPTY,
                    new Object[]{});
        }
        if (StringUtils.isEmpty(redisTemplate.opsForValue().get(MemberConstants.FIELD_SEND_VERIFICODE))) {
            throw new AccountException(MemberException.VERIFICATION_OVERDUE, MemberException.VERIFICATION_OVERDUE,
                    new Object[]{});
        }
        if (!way.equals((redisTemplate.opsForValue().get(MemberConstants.FIELD_VERIFICATION)))) {
            throw new AccountException(MemberException.ERROR_SEND_VERIFICATION, MemberException.ERROR_SEND_VERIFICATION,
                    new Object[]{});
        }
        if (!(redisTemplate.opsForValue().get(MemberConstants.FIELD_SEND_VERIFICODE)).equals(verifiCode)) {
            throw new AccountException(MemberException.ERROR_SEND_VERIFICATION, MemberException.ERROR_SEND_VERIFICATION,
                    new Object[]{});
        }
        // 第一次发送校验码时间
        // Long beforeSendTime = Long
        // .parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PASSWORD + ":" + uuidKey));
        Long beforeSendTime = 0L;
        if (MemberConstants.FIELD_VERIFICATION_EMAIL.equals(way)) {
            beforeSendTime = Long.parseLong(
                    redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PASSWORD_EMAIL + ":" + uuidKey));
        } else if (MemberConstants.FIELD_VERIFICATION_PHONE.equals(way)) {
            beforeSendTime = Long.parseLong(
                    redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PASSWORD_PHONE + ":" + uuidKey));
        }
        // 当前时间
        Long afterSendTime = new Date().getTime();
        if (afterSendTime - beforeSendTime > MemberConstants.REDIS_EXPIRATION_TIME) {
            throw new AccountException(MemberException.VERIFICATION_OVERDUE, MemberException.VERIFICATION_OVERDUE,
                    new Object[]{});
        }

        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            connection.del((MemberConstants.FORGER_PWD_KEY + ":" + uuidKey).getBytes());
            connection.del(MemberConstants.FIELD_SEND_VERIFICODE.getBytes());
            connection.del((MemberConstants.FIELD_WAY_TIME_PASSWORD_EMAIL + ":" + uuidKey).getBytes());
            connection.del((MemberConstants.FIELD_WAY_TIME_PASSWORD_PHONE + ":" + uuidKey).getBytes());
            return null;
        });
        return new ResponseData();
    }

    /**
     * 根据uuid拦截方法.
     *
     * @param uuidKey uuid
     * @return 跳转
     */
    @RequestMapping(value = "/retrieve_password_way")
    @ResponseBody
    public ModelAndView memberAccountValidate(String uuidKey) {
        ModelAndView mav = new ModelAndView();
        // 先检查uuidKey是否正确
        String uk = redisTemplate.opsForValue().get(MemberConstants.FORGER_PWD_KEY + ":" + uuidKey);
        if (uk == null || "".equals(uk)) { // key不对,未通过验证
            mav.setViewName(MemberConstants.REDIRECT + MemberConstants.FORGER_PWD_REDIRECT);
        } else {
            mav.setViewName(getViewPath() + MemberConstants.FORGER_PWD_WAY);
            mav.addObject(MemberConstants.FIELD_JUMP_UUID, uuidKey);
        }
        return mav;
    }

    /*
     * 生成UUID.
     */
    private String generateUuid() {
        String str = UUID.randomUUID().toString();
        return str;
    }


    /**
     * 找回用户-发送校验码到邮箱或联系电话.
     *
     * @param request   统一上下文
     * @param phoneNo   电话
     * @param areaPhone 区号+号码
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/account/sendRegCode")
    public ResponseData sendRegCode(HttpServletRequest request, String phoneNo, String areaPhone) throws Exception {
        IRequest requestContext = createRequestContext(request);

        String vcode = generateCheckCode();
        Long afterSendTime = new Date().getTime();
        Long beforeSendTime;
        Member mem = new Member();
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();

        mem.setPhoneNo(phoneNo);
        mem.setAreaPhone(areaPhone);
        //查询用户是否已经存在
        List<Member> memberList = memberAccountService.checkUser(requestContext, mem);
        if (memberList.isEmpty() == false) {
            throw new AccountException(MemberException.USER_ALREADY_EXIST, MemberException.USER_ALREADY_EXIST,
                    new Object[]{});
        }


        if (redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE) != null) {
            beforeSendTime = Long.parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE));
            if ((afterSendTime - beforeSendTime) < MemberConstants.VERIFICATION_WAITING_TIME) {
                throw new AccountException(MemberException.SEND_TIME, MemberException.SEND_TIME, new Object[]{});
            }
        }

        redisTemplate.opsForValue().set(MemberConstants.FIELD_WAY_TIME_PHONE, Long.toString(new Date().getTime()),
                MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
        // 根据memberId拿到marketId
        Member marketIdList = memberAccountService.selectMarketId(requestContext, memberList.get(0).getMemberId());


        redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_TIME_USER, vcode, MemberConstants.FIELD_TIME_OUT,
                TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                MemberConstants.FIELD_VERIFICATION_PHONE, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
        // 发送验证码到手机

        receiver.setMessageAddress(areaPhone);
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(memberList.get(0).getAccountId());
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("verifyCode", vcode);
        data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);

        //短信注册用户
        messageService.sendSmsMessage(-1L, marketIdList.getMarketId(), MemberConstants.PHONE_USER_TEMP_PWD,
                "PASSWORD", data, receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到用户手机(--->{}),验证码为{}", phoneNo, vcode);
        }

        //List<String> list = new ArrayList<String>();
        //list.add(vcode);
        return new ResponseData();
    }


    /**
     * 找回用户-发送校验码到邮箱或联系电话.
     *
     * @param request   统一上下文
     * @param email     邮箱
     * @param phoneNo   电话
     * @param areaPhone 区号+号码
     * @param type      验证方式
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/account/sendCheckCode")
    public ResponseData sendCheckCode(HttpServletRequest request, String email, String phoneNo, String areaPhone,
                                      String type,Long marketId) throws Exception {
        IRequest requestContext = createRequestContext(request);
        String vcode = generateCheckCode();
        Long afterSendTime = new Date().getTime();
        Long beforeSendTime;
        Member mem = new Member();
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        if (MemberConstants.FIELD_VERIFICATION_EMAIL.equals(type)) {
            mem.setEmail(email);
            List<Member> memberList = memberAccountService.checkPhoneOrEmail(requestContext, mem);
            if (memberList.isEmpty()) {
                throw new AccountException(MemberException.EMAIL_NOT_EXIST, MemberException.EMAIL_NOT_EXIST,
                        new Object[]{});
            }
            if (redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_EMAIL) != null) {
                beforeSendTime = Long.parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_EMAIL));
                if ((afterSendTime - beforeSendTime) < MemberConstants.VERIFICATION_WAITING_TIME) {
                    throw new AccountException(MemberException.SEND_TIME, MemberException.SEND_TIME, new Object[]{});
                }
            }
            redisTemplate.opsForValue().set(MemberConstants.FIELD_WAY_TIME_EMAIL, Long.toString(new Date().getTime()),
                    MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            // List<SpmMarket> marketList = memberShopService.queryMarket(requestContext,
            // memberList.get(0).getMemberId());
            redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_TIME_USER, vcode, MemberConstants.FIELD_TIME_OUT,
                    TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                    MemberConstants.FIELD_VERIFICATION_EMAIL, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            // 发送验证码到邮箱
            receiver.setMessageAddress(email);
            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receiver.setReceiverId(memberList.get(0).getAccountId());
            receiverlist.add(receiver);
            // 设置message模板里的数据
            data.put("verifyCode", vcode);
            data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
            messageService.sendEmailMessage(-1L, null, MemberConstants.EMAIL_FORGET_ACCOUNT_VCODE, "PASSWORD", data,
                    receiverlist, null);
            if (logger.isDebugEnabled()) {
                logger.debug("此时发送验证码到邮箱(--->{}),验证码为{}", email, vcode);
            }
        } else if (MemberConstants.FIELD_VERIFICATION_PHONE.equals(type)) {
            mem.setPhoneNo(phoneNo);
            mem.setAreaPhone(areaPhone);
            List<Member> memberList = memberAccountService.checkPhoneOrEmail(requestContext, mem);
            if (memberList.isEmpty()) {
                throw new AccountException(MemberException.PHONE_NOT_EXIST, MemberException.PHONE_NOT_EXIST,
                        new Object[]{});
            }
            if (redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE) != null) {
                beforeSendTime = Long.parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE));
                if ((afterSendTime - beforeSendTime) < MemberConstants.VERIFICATION_WAITING_TIME) {
                    throw new AccountException(MemberException.SEND_TIME, MemberException.SEND_TIME, new Object[]{});
                }
            }
            redisTemplate.opsForValue().set(MemberConstants.FIELD_WAY_TIME_PHONE, Long.toString(new Date().getTime()),
                    MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            // 根据memberId拿到marketId
           // Member marketIdList = memberAccountService.selectMarketId(requestContext, memberList.get(0).getMemberId());
            redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_TIME_USER, vcode, MemberConstants.FIELD_TIME_OUT,
                    TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                    MemberConstants.FIELD_VERIFICATION_PHONE, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
            // 发送验证码到手机
            receiver.setMessageAddress(areaPhone);
            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
            receiver.setReceiverId(memberList.get(0).getAccountId());
            receiverlist.add(receiver);
            // 设置message模板里的数据
            data.put("verifyCode", vcode);
            data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
            //updated by 13525 on 2018/03/05
            String sms_interface_address = profileService.getProfileValue(requestContext, SMSConstants.SMS_INTERFACE_ADDRESS);
            String sms_userid = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERID);
            String sms_username = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERNAME);
            String sms_password = profileService.getProfileValue(requestContext, SMSConstants.SMS_PASSWORD);

            data.put("sms_interface_address", sms_interface_address);
            data.put("sms_userid", sms_userid);
            data.put("sms_username", sms_username);
            data.put("sms_password", sms_password);
            messageService.sendSmsMessage(-1L, marketId, MemberConstants.FORGET_ACCOUNT_PHONE,
                    "PASSWORD", data, receiverlist);
            if (logger.isDebugEnabled()) {
                logger.debug("此时发送验证码到用户手机(--->{}),验证码为{}", phoneNo, vcode);
            }
        }
        return new ResponseData();
    }


    /**
     * 找回用户对应的组织
     *
     * @param request
     * @param phoneNo
     * @param areaPhone
     * @return
     * @throws Exception
     */
    /* created by 13525 at 2018.03.19 */
    @RequestMapping(value = "/account/selectMarket")
    public ResponseData selectMarker(HttpServletRequest request, String phoneNo, String areaPhone) throws Exception {
        IRequest requestContext = createRequestContext(request);
        Member mem = new Member();
        mem.setPhoneNo(phoneNo);
        mem.setAreaPhone(areaPhone);
        List<Member> memberList = memberAccountService.checkPhoneOrEmail(requestContext, mem);
        if (memberList.isEmpty()) {
            throw new AccountException(MemberException.PHONE_NOT_EXIST, MemberException.PHONE_NOT_EXIST,
                    new Object[]{});
        }

        // 根据memberId拿到marketId
        List<SpmMarket> marketList = memberAccountService.selectSpmMarketByMemberId(memberList);
        return new ResponseData(marketList);

    }




    /*
     * 找回用户-生成校验码.
     */
    private String generateCheckCode() {
        // 验证码
        String vcode = "";
        for (int i = 0; i < MemberConstants.BASE_MENBER_SIX; i++) {
            vcode = vcode + (int) (Math.random() * MemberConstants.BASE_MEMBER_NINE);
        }
        return vcode;
    }

    /**
     * 找回用户-电话方式校验.
     *
     * @param request    统一上下文
     * @param member     会员信息
     * @param way        验证方式
     * @param verifiCode send验证码
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/account/sendMessageByPhone")
    public ResponseData sendMessageByPhone(HttpServletRequest request, Member member, String way, String verifiCode)
            throws Exception {
        IRequest requestContext = createRequestContext(request);
        if (member.getPhoneNo() == null) {
            throw new AccountException(MemberException.PHONE_NOT_EMPTY, MemberException.PHONE_NOT_EMPTY,
                    new Object[]{});
        }
        // 电话格式校验
        if (MemberConstants.PHONE_REGEX.matches(member.getPhoneNo())) {
            throw new AccountException(MemberException.PHONE_FORMAT_ERROR, MemberException.PHONE_FORMAT_ERROR,
                    new Object[]{});
        }
        checkCode(request, way, verifiCode);
        List<Member> loginNameList = memberAccountService.checkPhoneOrEmail(requestContext, member);
        if (loginNameList.isEmpty()) {
            throw new AccountException(MemberException.PHONE_NOT_EXIST, MemberException.PHONE_NOT_EXIST,
                    new Object[]{});
        }
        List<Member> loginNames = new ArrayList<Member>();
        for (Member m: loginNameList ) {
            if (m.getMarketId().equals(member.getMarketId())) {
                loginNames.add(m);
            }
        }
        //updated by 13525 2018.03.06
        return new ResponseData(loginNames);
      /*  Member marketIdList = memberAccountService.selectMarketId(requestContext, loginNameList.get(0).getMemberId());
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(member.getPhoneNo());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(loginNameList.get(0).getAccountId());
        receiverlist.add(receiver);
        // 设置message模板里的数据

        data.put("loginName", loginNameList.get(0).getLoginName());
        messageService.sendSmsMessage(-1L, marketIdList.getMarketId(), MemberConstants.FORGET_ACCOUNT_PHONE, "PASSWORD",
                data, receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到手机(--->{}),用户名{}", member.getPhoneNo());
        }
        // TODO 会员状态是否有效
        return new ResponseData();*/
    }



    /**
     * 找回用户-邮箱方式校验.
     *
     * @param request    统一上下文
     * @param member     会员信息
     * @param way        验证方式
     * @param verifiCode send验证码
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/account/sendMessageByEmail")
    public ResponseData sendMessageByEmail(HttpServletRequest request, Member member, String way, String verifiCode)
            throws Exception {
        IRequest requestContext = createRequestContext(request);
        if (member.getEmail() == null) {
            throw new AccountException(MemberException.EMAIL_NOT_EMPTY, MemberException.EMAIL_NOT_EMPTY,
                    new Object[]{});
        }
        // 电话格式校验
        if (MemberConstants.EMAIL_REGEX.matches(member.getEmail())) {
            throw new AccountException(MemberException.EMAIL_FORMAT_ERROR, MemberException.EMAIL_FORMAT_ERROR,
                    new Object[]{});
        }
        checkCode(request, way, verifiCode);
        List<Member> loginNameList = memberAccountService.checkPhoneOrEmail(requestContext, member);
        if (loginNameList.isEmpty()) {
            throw new AccountException(MemberException.EMAIL_NOT_EXIST, MemberException.EMAIL_NOT_EXIST,
                    new Object[]{});
        }
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(member.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(loginNameList.get(0).getAccountId());
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("loginNameList", loginNameList);
        messageService.sendEmailMessage(-1L, null, MemberConstants.FORGET_ACCOUNT_EMAIL, "PASSWORD", data, receiverlist,
                null);
        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到邮箱(--->{}),用户名{}", member.getEmail());
        }
        // TODO 会员状态是否有效
        return new ResponseData();
    }

    /*
     * 找回用户-校验验证码.
     */
    private void checkCode(HttpServletRequest request, String way, String verifiCode) throws AccountException {
        if (verifiCode == null) {
            throw new AccountException(MemberException.VERIFICATION_NOT_EMPTY, MemberException.VERIFICATION_NOT_EMPTY,
                    new Object[]{});
        }
        if (redisTemplate.opsForValue().get(MemberConstants.FIELD_SEND_TIME_USER) == null) {
            throw new AccountException(MemberException.VERIFICATION_OVERDUE, MemberException.VERIFICATION_OVERDUE,
                    new Object[]{});
        }
        if (!way.equals(redisTemplate.opsForValue().get(MemberConstants.FIELD_VERIFICATION))) {
            throw new AccountException(MemberException.ERROR_SEND_VERIFICATION, MemberException.ERROR_SEND_VERIFICATION,
                    new Object[]{});
        }
        if (!(redisTemplate.opsForValue().get(MemberConstants.FIELD_SEND_TIME_USER)).equals(verifiCode)) {
            throw new AccountException(MemberException.ERROR_SEND_VERIFICATION, MemberException.ERROR_SEND_VERIFICATION,
                    new Object[]{});
        }
        // 第一次发送校验码时间
        Long beforeSendTime = 0L;
        if (MemberConstants.FIELD_VERIFICATION_EMAIL.equals(way)) {
            beforeSendTime = Long.parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_EMAIL));
        } else if (MemberConstants.FIELD_VERIFICATION_PHONE.equals(way)) {
            beforeSendTime = Long.parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE));
        }
        // 当前时间
        Long afterSendTime = new Date().getTime();
        if (afterSendTime - beforeSendTime > MemberConstants.REDIS_EXPIRATION_TIME) {
            throw new AccountException(MemberException.VERIFICATION_OVERDUE, MemberException.VERIFICATION_OVERDUE,
                    new Object[]{});
        }
    }

    /**
     * mws修改密码.
     *
     * @param request  统一上下文
     * @param oldPwd   原密码
     * @param newPwd   新密码
     * @param againPwd 再次输入
     * @return 是否
     * @throws MemberException 抛出验证密码失败的业务异常
     */
    @RequestMapping(value = "/account/member/updatePassword")
    public ResponseData updatePassword(HttpServletRequest request, String oldPwd, String newPwd, String againPwd)
            throws MemberException {
        Long accountId = createRequestContext(request).getAccountId();
        if (memberAccountService.validatePassword(createRequestContext(request), oldPwd, newPwd, againPwd, accountId)) {
            accountService.updatePassword(accountId, newPwd);
        }
        return new ResponseData(true);
    }

    /**
     * 注册用户-发送校验码到联系电话.
     *
     * @param request   统一上下文
     * @param email     邮箱
     * @param phoneNo   电话
     * @param areaPhone 区号+号码
     * @param type      验证方式
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/register/sendCheckCode")
    public ResponseData sendRegCode(HttpServletRequest request, String email, String phoneNo, String areaPhone,
                                      String type) throws Exception {
        IRequest requestContext = createRequestContext(request);
        String vcode = generateCheckCode();
        Long afterSendTime = new Date().getTime();
        Long beforeSendTime;
        Member mem = new Member();
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();

        mem.setPhoneNo(phoneNo);
        mem.setAreaPhone(areaPhone);
        //List<Member> memberList = memberAccountService.checkPhoneOrEmail(requestContext, mem);

        //判断手机号是否存在
        if (memberAccountService.selectMember(phoneNo)) {
            throw new AccountException(MemberException.PHONE_EXIST_ALREADY, MemberException.PHONE_EXIST_ALREADY,
                    new Object[]{});
        }
        if (redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE) != null) {
            beforeSendTime = Long.parseLong(redisTemplate.opsForValue().get(MemberConstants.FIELD_WAY_TIME_PHONE));
            if ((afterSendTime - beforeSendTime) < MemberConstants.VERIFICATION_WAITING_TIME) {
                throw new AccountException(MemberException.SEND_TIME, MemberException.SEND_TIME, new Object[]{});
            }
        }
        redisTemplate.opsForValue().set(MemberConstants.FIELD_WAY_TIME_PHONE, Long.toString(new Date().getTime()),
                MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
        // 根据memberId拿到marketId
        //Member marketIdList = memberAccountService.selectMarketId(requestContext, memberList.get(0).getMemberId());
        redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_TIME_USER, vcode, MemberConstants.FIELD_TIME_OUT,
                TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                MemberConstants.FIELD_VERIFICATION_PHONE, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
        // 发送验证码到手机
        receiver.setMessageAddress(areaPhone);
        //receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        //receiver.setReceiverId(memberList.get(0).getAccountId());
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("verifyCode", vcode);
        data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
        //updated by 13525 on 2018/03/05
        String sms_interface_address = profileService.getProfileValue(requestContext, SMSConstants.SMS_INTERFACE_ADDRESS);
        String sms_userid = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERID);
        String sms_username = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERNAME);
        String sms_password = profileService.getProfileValue(requestContext, SMSConstants.SMS_PASSWORD);

        data.put("sms_interface_address", sms_interface_address);
        data.put("sms_userid", sms_userid);
        data.put("sms_username", sms_username);
        data.put("sms_password", sms_password);

        messageService.sendSmsMessage(-1L,"PASSWORD", data, receiverlist);

        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到用户手机(--->{}),验证码为{}", phoneNo, vcode);
        }
        return new ResponseData();
    }

    /**
     * 注册用户-电话方式校验.
     * @param response
     * @param request   统一上下文
     * @param newPwd    密码
     * @param phoneNo   电话
     * @param uname     账号
     * @param arePhone  区号
     * @param againPwd  密码
     * @param gender    性别
     * @param verifiCode  send验证码
     * @return  响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/register/sendMessageByPhone",method = RequestMethod.POST)
    public ResponseData sendRegisterMessageByPhone(HttpServletResponse response, HttpServletRequest request, String newPwd, String phoneNo,
                                                   String uname, String arePhone, String againPwd, String gender,Date dob,
                                                   Long salesOrgId,String saleOrgName,String verifiCode) throws Exception {
        IRequest requestContext = createRequestContext(request);
        if (phoneNo == null) {
            throw new AccountException(MemberException.PHONE_NOT_EMPTY, MemberException.PHONE_NOT_EMPTY,
                    new Object[]{});
        }
        // 电话格式校验
        if (MemberConstants.PHONE_REGEX.matches(phoneNo)) {
            throw new AccountException(MemberException.PHONE_FORMAT_ERROR, MemberException.PHONE_FORMAT_ERROR,
                    new Object[]{});
        }


        checkCode(request, "PHONE", verifiCode);

        IRequest iRequest = createRequestContext(request);
        memberAccountService.validatePassword(iRequest, newPwd, againPwd);

        Member m = memberAccountService.saveMember(request,newPwd, phoneNo, arePhone,uname,gender,dob,salesOrgId,saleOrgName);

        HttpSession session = request.getSession(true);
        session.setAttribute("_salesOrgId", m.getSalesOrgId());
        session.setAttribute("_marketCode",m.getMarketCode() );
        session.setAttribute("_marketId", m.getMarketId());
        session.setAttribute("_salesOrgName", m.getSalesOrganization());
        session.setAttribute("memberId", m.getMemberId());
        session.setAttribute("memberName", m.getMemberName());
        session.setAttribute("accountId", m.getAccountId());

        //返回视图login
        Account account = new Account();
        account.setLoginName(uname);
        account.setPassword(newPwd);
        account.setFirstLoginFlag("N");
        //return loginAdaptor.doLogin(account,request,response);

        return new ResponseData();
    }


    /**
     * 检查用户是否存在.
     *
     * @param request    统一上下文
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/register/sendDetectionName", method = RequestMethod.POST)
    public ResponseData sendDetectionName(HttpServletRequest request,String uname)throws Exception {
        IRequest requestContext = createRequestContext(request);
        //判断用户是否存在
        String  name = uname.toLowerCase();
        //Account mc = memberAccountService.selectByLoginName(name);
        if (memberAccountService.selectByLoginName(name) != null) {
            throw new AccountException(MemberException.USER_ALREADY_EXIST, MemberException.USER_ALREADY_EXIST,
                    new Object[]{});
        }
        return new ResponseData();
    }


    /**
     * 查询销售区域.
     *
     * @param salesOrganization
     *            销售区域DTO
     * @param page
     *            页
     * @param pagesize
     *            分页大小
     * @param request
     *            请求上下文
     * @return responseData 响应数据
     */
    @RequestMapping(value = "/register/salesOrganization/query")
    @ResponseBody
    public ResponseData querySalesOrganization(SpmSalesOrganization salesOrganization,
                                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                memberAccountService.querySalesOrganization(requestContext, salesOrganization, page, pagesize));
    }

}
