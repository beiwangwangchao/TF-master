/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.user.controllers;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.admin.member.exception.MemberException;
import com.lkkhpg.dsis.admin.user.exception.AccountException;
import com.lkkhpg.dsis.common.constant.SMSConstants;
import com.lkkhpg.dsis.platform.service.IProfileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lkkhpg.dsis.admin.export.controllers.ExportController;
import com.lkkhpg.dsis.admin.export.utils.ExportUtils;
import com.lkkhpg.dsis.admin.member.service.IMemberService;
import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.admin.user.service.ISendRetrieveService;
import com.lkkhpg.dsis.admin.user.service.IUserInfoService;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.user.dto.IpointUser;
import com.lkkhpg.dsis.common.user.dto.SendRetrieve;
import com.lkkhpg.dsis.platform.controllers.BaseController;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.ResponseData;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.security.captcha.ICaptchaManager;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class UserInfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICaptchaManager captchaManager;
    @Autowired
    private ISendRetrieveService sendRetrieveService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IProfileService profileService;
    /**
     * insert个人信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            积分用户
     * @param page
     *            分页信息page
     * @param pagesize
     *            分页信息pagesize 用户信息
     * @param result
     *            自动校验
     * @return 结束时跳转页面
     * @throws UserException
     *             抛出创建或更新失败的异常
     * @throws Exception
     *             抛出短信接口异常
     */
    @RequestMapping(value = "/sys/um/insertIpointUser", method = RequestMethod.POST)
    public ResponseData insertIpointUser(HttpServletRequest request, @RequestBody IpointUser ipointUser,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, BindingResult result) throws Exception {
        if ("IPONT".equals(ipointUser.getUserType()) && StringUtils.isEmpty(ipointUser.getEmail())) {
            throw new UserException(UserException.IPONT_EMAIL_NOT_EMPTY, new Object[] {});
        } else {
            // 校验
            getValidator().validate(ipointUser, result);
            if (result.hasErrors()) {
                ResponseData rd = new ResponseData(false);
                rd.setMessage(getErrorMessage(result, request));
                return rd;
            }
        }
        IRequest requestContext = createRequestContext(request);
        List<IpointUser> list = userInfoService.checkLoginName(requestContext, ipointUser);
        return new ResponseData(list);
    }

    /**
     * 更新个人信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            包含需要更新的字段信息
     * @return 响应信息
     * @throws UserException
     *             抛出更新用户信息失败的异常
     * @throws Exception
     *             抛出短信接口异常
     */
    @RequestMapping(value = "/sys/um/updateUserInfo")
    public ResponseData updateUserInfo(HttpServletRequest request, @RequestBody IpointUser ipointUser)
            throws Exception {
        IRequest iRequest = createRequestContext(request);
        // 匹配电话格式.
        if (!ipointUser.getPhoneNo().matches(UserConstants.PHONE_REGEX)) {
            throw new UserException(UserException.PHONE_FORMAT, new Object[] {});
        }
        // 匹配邮箱格式.
        if (!ipointUser.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[] {});
        }
        userInfoService.update(iRequest, ipointUser);
        List<IpointUser> list = new ArrayList<IpointUser>();
        list.add(ipointUser);
        return new ResponseData(list);
    }

    /**
     * 校验用户.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            ipoint用户信息
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 响应信息
     * @throws UserException
     *             抛出用户已存在的业务异常
     */
    @RequestMapping(value = "/sys/um/isExistsUser")
    @ResponseBody
    public ResponseData isExistsUser(HttpServletRequest request, IpointUser ipointUser,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest requestContext = createRequestContext(request);
        List<IpointUser> list = userInfoService.isExistsUser(requestContext, ipointUser);
        return new ResponseData(list);
    }

    /**
     * 校验联系电话.
     * 
     * @param request
     *            统一上下文.
     * @param ipointUser
     *            ipoint用户信息
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 响应信息
     * @throws UserException
     *             抛出联系方式已存在的业务异常
     */
    @RequestMapping(value = "/sys/um/isExistsPhone")
    public ResponseData isExistsPhone(HttpServletRequest request, IpointUser ipointUser,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest contextRequest = createRequestContext(request);
        List<IpointUser> list = userInfoService.isExistsPhone(contextRequest, ipointUser);
        return new ResponseData(list);
    }

    /**
     * 验证邮箱是否被占用.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            用户信息
     * @param page
     *            分页信息page
     * @param pagesize
     *            分页信息pagesize
     * @return 返回检查结果
     * @throws UserException
     *             抛出邮箱被占用的业务异常
     */
    @RequestMapping(value = "/sys/um/isExistsEmail", method = RequestMethod.POST)
    public ResponseData isExistsEmail(HttpServletRequest request, IpointUser ipointUser,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest contextRequest = createRequestContext(request);
        List<IpointUser> list = userInfoService.isExistsEmail(contextRequest, ipointUser);
        return new ResponseData(list);
    }

    /**
     * 用户忘记账号发送验证码到手机
     * @param request
     * @param phoneNo 号码
     * @param areaPhone 区号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sys/um/sendCheckCode", method = RequestMethod.POST)
    public ResponseData sendMessageByPhone(HttpServletRequest request, String phoneNo, String areaPhone) throws
            Exception {
        IRequest requestContext = createRequestContext(request);
        String vcode = generateVerifyCode();
        Long afterSendTime = new Date().getTime();
        Long beforeSendTime;
        Member mem = new Member();
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        mem.setPhoneNo(phoneNo);
        mem.setAreaPhone(areaPhone);
        List<Member> memberList = userInfoService.checkPhone(requestContext, mem);

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
        Member marketIdList = userInfoService.selectMarketId(requestContext, memberList.get(0).getMemberId());
        redisTemplate.opsForValue().set(MemberConstants.FIELD_SEND_TIME_USER, vcode, MemberConstants.FIELD_TIME_OUT,
                TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(MemberConstants.FIELD_VERIFICATION,
                MemberConstants.FIELD_VERIFICATION_PHONE, MemberConstants.FIELD_TIME_OUT, TimeUnit.SECONDS);
        // 发送验证码到手机
        receiver.setMessageAddress(phoneNo);
        receiver.setMessageType(MemberConstants.FIELD_VERIFICATION_PHONE);
        receiver.setReceiverId(memberList.get(0).getAccountId());
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("verifyCode", vcode);
        data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);

        //updated by 13525 on 2018/3/2
            String sms_interface_address = profileService.getProfileValue(requestContext, SMSConstants.SMS_INTERFACE_ADDRESS);
            String sms_userid = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERID);
            String sms_username = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERNAME);
            String sms_password = profileService.getProfileValue(requestContext, SMSConstants.SMS_PASSWORD);

            data.put("sms_interface_address", sms_interface_address);
            data.put("sms_userid", sms_userid);
            data.put("sms_username", sms_username);
            data.put("sms_password", sms_password);

        messageService.sendSmsMessage(-1L, marketIdList.getMarketId(), MemberConstants.FORGET_ACCOUNT_PHONE,
                "PASSWORD", data, receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到用户手机(--->{}),验证码为{}", phoneNo, vcode);
        }
        return new ResponseData();
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
    @RequestMapping(value = "/sys/um/sendMessageByPhone2")
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
        IpointUser ipointUser = new IpointUser();
        ipointUser.setPhoneNo(member.getPhoneNo());
        //List<Member> loginNameList = userInfoService.checkPhone(requestContext, member);
        List<IpointUser> loginNameList = userInfoService.getIpointUsers(requestContext, ipointUser);
        // 无返回电话不存在
        if (loginNameList.isEmpty()) {
            throw new AccountException(MemberException.PHONE_NOT_EXIST, MemberException.PHONE_NOT_EXIST,
                    new Object[]{});
        }
        return new ResponseData(loginNameList);
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
         * 校验邮箱并发送信息.
         *
         * @param request
         *            统一上下文
         * @param ipointUser
         *            ipoint用户信息
         * @param page
         *            页码
         * @param pagesize
         *            页数
         * @param result
         *            自动校验
         * @return 响应信息
         * @throws Exception
         */
    @RequestMapping(value = "/sys/um/sendMessageByEmail")
    public ResponseData sendMessageByEmail(HttpServletRequest request, IpointUser ipointUser,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, BindingResult result) throws Exception {
        IRequest contextRequest = createRequestContext(request);
        // 校验
        // getValidator().validate(ipointUser, result);
        // if (result.hasErrors()) {
        // ResponseData rd = new ResponseData(false);
        // rd.setMessage(getErrorMessage(result, request));
        // return rd;
        // }
        // 非空验证
        if (ipointUser.getEmail() == null || "".equals(ipointUser.getEmail())) {
            throw new UserException(UserException.EMAIL_NOT_ISEMPTY, new Object[] {});
        }
        // 格式验证
        if (!ipointUser.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[] {});
        }
        List<IpointUser> loginNameList = userInfoService.getIpointUsers(contextRequest, ipointUser, page, pagesize);
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 校验码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, new Object[] {});
        }
        if (loginNameList.isEmpty()) {
            throw new UserException(UserException.EMAIL_NOT_EXIST, new Object[] {});
        }
        if ((UserConstants.USER_STATUS_EXPR).equals(loginNameList.get(0).getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, new Object[] {});
        }
        SendRetrieve sendRetrieve = new SendRetrieve();
        sendRetrieve.setAccountId(loginNameList.get(0).getAccountId());
        sendRetrieve.setCreatedBy(loginNameList.get(0).getAccountId());
        sendRetrieve.setLastUpdatedBy(loginNameList.get(0).getAccountId());
        sendRetrieve.setEmail(ipointUser.getEmail());
        sendRetrieveInsert(request, sendRetrieve);
        // 发送验证码到邮箱
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(ipointUser.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(loginNameList.get(0).getUserId());
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("loginNameList", loginNameList);
        messageService.sendEmailMessage(-1L, null, MemberConstants.FORGET_ACCOUNT_EMAIL, "PASSWORD", data, receiverlist,
                null);
        // messageService.addMessage(null, "forget_account_email", data, null,
        // receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("你的用户名是：{}", loginNameList.get(0).getLoginName());
        }
        return new ResponseData();
    }

    /**
     * 发送限制.
     * 
     * @param request
     *            统一上下文
     * @param sendRetrieve
     *            发送限制dto
     * @return 记录数量
     */
    private Integer sendRetrieveInsert(HttpServletRequest request, SendRetrieve sendRetrieve) throws UserException {
        SimpleDateFormat df = new SimpleDateFormat(UserConstants.DATE_FORMAT); // 设置日期格式
        try {
            // sendRetrieve.setRetrieveType("NAME"); // 类型先写死
            sendRetrieve.setRetrieveDate(df.parse(df.format(new Date()))); // insert当前时间
            sendRetrieve.setCreationDate(new Date());
            sendRetrieve.setLastUpdateDate(new Date());
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error(UserException.DATE_FORMAT, e);
            }
        }
        Integer result = sendRetrieveService.insert(createRequestContext(request), sendRetrieve);
        return result;
    }

    /**
     * 根据条件查询用户信息.
     * 
     * @param request
     *            统一上下文
     * @param response
     * @param ipointUser
     *            包含查询条件
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 响应信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @RequestMapping(value = "/sys/um/query", method = RequestMethod.POST)
    @ResponseBody
    public Object query(HttpServletRequest request, HttpServletResponse response, IpointUser ipointUser,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize)
            throws UserException, IOException, IllegalArgumentException, IllegalAccessException {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(User.FILED_USER_ID);

        List<IpointUser> list = new ArrayList<IpointUser>();
        if(userId == 1) {
           list = userInfoService.getIpointUsers(requestContext, ipointUser, page, pagesize);
        }else{
            ipointUser.setUserId(userId);
           list = userInfoService.getIpointUsers(requestContext, ipointUser, page, pagesize);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("查询出的用户信息：{}", list.toString());
        }

        if (request.getParameter("isExport2Excel") != null && request.getParameter("isExport2Excel").equals("Y")) {
            ExportUtils.export(request, response, list);
            return null;
        } else {
            return new ResponseData(list);
        }

    }

    /**
     * 查询单条用户信息.
     * 
     * @param request
     *            统一上下文
     * @param userId
     *            用户ID
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 响应信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    @RequestMapping(value = "/sys/um/getSingleUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getSingleUser(HttpServletRequest request, Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest requestContext = createRequestContext(request);
        IpointUser ipointUser = new IpointUser();
        ipointUser.setUserId(userId);
        List<IpointUser> list = userInfoService.getIpointUsers(requestContext, ipointUser, page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("查询单条用户信息时用户登录名:{}", list.get(0).getLoginName());
        }
        return new ResponseData(list);
    }

    /**
     * 用户资料详情页-查询会员-会员姓名autocomplete.
     * 
     * @param request
     *            统一上下文
     * @param key
     *            前台autocomplete请求参数
     * @return 响应信息
     */
    @RequestMapping(value = "/sys/um/queryMemberAuto")
    public ResponseData queryMemberAuto(HttpServletRequest request, String key) {
        List<Member> members = memberService.queryMemNameAuto(key);
        return new ResponseData(members);
    }

    /**
     * 用户登录密码更改,输入新密码点击确定修改密码.
     * 
     * @param request
     *            统一上下文
     * @param oldPwd
     *            当前密码
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            再次输入密码
     * @return 响应信息
     * @throws UserException
     *             抛出密码更新失败的异常
     */
    @RequestMapping(value = "/sys/um/updatePassword", method = RequestMethod.POST)
    public ResponseData updatePassword(HttpServletRequest request, String oldPwd, String newPwd, String newPwdAgain)
            throws UserException {
        IRequest iRequest = createRequestContext(request);
        Long accountId = iRequest.getAccountId();
        if (userInfoService.validatePassword(iRequest, oldPwd, newPwd, newPwdAgain, accountId)) {
            accountService.updatePassword(accountId, newPwd);
        }
        return new ResponseData(true);
    }

    /**
     * 
     * @param request
     *            统一上下文.
     * @param response
     *            统一上下文
     * @param first
     *            首次登陆判断
     * @param newPassword
     *            新密码
     * @param checkPassword
     *            再次输入密码
     * @return 响应信息
     * @throws UserException
     *             抛出密码更新失败的异常
     */
    @RequestMapping(value = "/sys/um/updateAccountPassword", method = RequestMethod.POST)
    public ResponseData updateAccountPassword(HttpServletRequest request, HttpServletResponse response, String first,
            String newPassword, String checkPassword) throws UserException {
        List<Object> result = new ArrayList<Object>();
        Map<String, String> map = new HashMap<String, String>();
        ModelAndView view = new ModelAndView();
        // 判断如果是首次登录
        if (first != null && UserConstants.FIRST_LOGIN_FLAG_Y.equals(first)) {
            IRequest iRequest = createRequestContext(request);
            Long accountId = iRequest.getAccountId();
            if (userInfoService.validateAccountPassword(iRequest, newPassword, checkPassword, accountId)) {
                // 获取当前account
                Account singleAccount = accountService.getAccountByAccountId(accountId);
                // 更新首次登录标识
                singleAccount.setFirstLoginFlag(UserConstants.FIRST_LOGIN_FLAG_N);
                // 提交数据库
                accountService.update(singleAccount);
                // 更新密码
                accountService.updatePassword(accountId, newPassword);
                // result.setViewName("redirect:" +
                // UserConstants.VIEW_ROLE_SELECT);
                int i = userInfoService.selectRolesByUserWithoutLang(createRequestContext(request), request, response);
                if (i == 1) {
                    map.put("returnUrl", UserConstants.VIEW_INDEX_SELECT);
                    // view.setViewName("redirect:" +
                    // UserConstants.USER_PWD_URL);
                } else {
                    map.put("returnUrl", UserConstants.VIEW_ROLE_SELECT);
                }
                result.add(map);
                return new ResponseData(result);
            }
        } else {
            // 获取会话里用户ID
            IRequest iRequest = createRequestContext(request);
            Long accountId = iRequest.getAccountId();
            if (userInfoService.validateAccountPassword(iRequest, newPassword, checkPassword, accountId)) {
                // 更新密码
                accountService.updatePassword(accountId, newPassword);
                HttpSession session = request.getSession();
                session.removeAttribute(UserConstants.FIRST_LOGIN_FLAG);
                view.setViewName("redirect:" + UserConstants.USER_PWD_URL);
                map.put("returnUrl", UserConstants.USER_PWD_URL);
                result.add(map);
                return new ResponseData(result);
            }
        }
        return new ResponseData(result);

    }

    /**
     * 忘记密码,第一步验证用户名.
     * 
     * @param request
     *            统一上下文
     * @param loginName
     *            需要验证的用户名
     * @return 返回信息给ajax回调
     * @throws UserException
     *             抛出验证码验证的业务异常
     */
    @RequestMapping(value = "/sys/sc/checkUser", method = RequestMethod.POST)
    public ResponseData checkUser(HttpServletRequest request, String loginName) throws UserException {
        // 判断用户是否输入完整信息
        StringBuilder errorMessage = new StringBuilder();
        if (StringUtils.isEmpty(loginName)) {
            errorMessage.append(UserException.ENTER_USERNAME);
        }
        String verifyCode = request.getParameter(UserConstants.KEY_VERIFICODE);
        if (StringUtils.isEmpty(verifyCode)) {
            if (errorMessage.length() == 0) {
                errorMessage.append(UserException.ENTER_VERIFICATION);
            } else { // 两个异常-用户没有输入用户名和密码时,功能文档说要加序号
                errorMessage.insert(0, UserConstants.TWO_EXCEPTION_SEQ_1);
                errorMessage.append(UserConstants.TWO_EXCEPTION_SEQ_2).append(UserException.ENTER_VERIFICATION);
            }
        }
        if (errorMessage.length() > 0) {
            throw new UserException(errorMessage.toString(), new Object[] {});
        }

        IRequest requestContext = createRequestContext(request);
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 校验码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, new Object[] {});
        }
        // 验证数据库是否有这个用户-不区分大小写-在service里已经做了用户不存在和用户失效的校验
        User checkUser = userInfoService.selectUserByLoginName(requestContext, loginName);
        // 用户名验证成功,跳转到选择验证方式界面
        // 生成一个key,策略采用uuid方式
        String key = generateCaptchaKey();
        // 将四位数字的验证码保存到Session中。
        redisTemplate.opsForValue().set(UserConstants.FORGET_PWD_KEY + ":" + key, checkUser.getUserId().toString(),
                UserConstants.KEY_EXPIRE_SIXTY_MIN, TimeUnit.SECONDS);
        // 处理返回信息
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(UserConstants.UUID_KEY, key);
        list.add(map);
        return new ResponseData(list);
    }

    /**
     * 拦截访问/sys/sc/sc_validate页面.
     * 
     * @param uuidKey
     *            key
     * @return 检查redis是否有钥匙,没有则跳转到验证用户名页面
     */
    @RequestMapping(value = "/sys/sc/sc_validate")
    @ResponseBody
    public ModelAndView scValidate(String uuidKey) {
        ModelAndView mav = new ModelAndView();
        // 先检查uuidKey是否正确
        String uk = redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey);
        if (StringUtils.isEmpty(uk)) { // key不对,未通过验证
            mav.setViewName(UserConstants.REDIRECT + UserConstants.VIEW_FORGET_PWD);
        } else {
            mav.setViewName(getViewPath() + UserConstants.VIEW_VALIDATE);
            mav.addObject(UserConstants.UUID_KEY, uuidKey);
        }
        return mav;
    }

    /**
     * 忘记密码,取得验证码界面ajax请求user数据填充页面.
     * 
     * @param request
     *            统一上下文
     * @param uuidKey
     *            key
     * @return 响应信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    @RequestMapping(value = "/sys/sc/getSingleUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getSingleUserInfo(HttpServletRequest request, String uuidKey) throws UserException {
        IRequest requestContext = createRequestContext(request);
        Long userId = Long.parseLong(redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey));
        User checkUser = userInfoService.selectUserByPrimaryKey(requestContext, userId);
        // new一个user用来存放需要返回的三个字符串信息
        User returnUser = new User();
        // 隐藏部分loginName/email/phone信息---start
        // loginName
        StringBuilder secretLoginName = new StringBuilder(checkUser.getLoginName());
        for (int i = 2; i < secretLoginName.length() - 2; i++) { // 用户名6-20位,显示前两位后两位
            secretLoginName.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
        }
        returnUser.setLoginName(secretLoginName.toString());
        // email
        if (StringUtils.isNotBlank(checkUser.getEmail())) { // 如果用户有邮箱
            StringBuilder secretEmail = new StringBuilder(checkUser.getEmail());
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
            returnUser.setEmail(secretEmail.toString());
        }
        // phone
        StringBuilder secretPhone = new StringBuilder(checkUser.getPhoneNo());
        for (int i = 0; i < secretPhone.length() - UserConstants.SC_PHONE_AFTER_HIDE_REMINE; i++) { // 手机,显示后四位
            secretPhone.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
        }
        returnUser.setPhoneNo(secretPhone.toString());
        // 隐藏部分loginName/email/phone信息---end
        List<User> list = new ArrayList<User>();
        list.add(returnUser);
        return new ResponseData(list);
    }

    /**
     * 忘记密码,点击发送验证码到邮箱或手机.
     * 
     * @param request
     *            统一上下文
     * @param uuidKey
     *            key
     * @return 返回发送情况信息给前台
     * @throws UserException
     *             抛出发送验证码相关的业务异常
     * @throws Exception
     *             抛出短信接口异常
     */
    @RequestMapping(value = "/sys/sc/sendCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData sendCaptcha(HttpServletRequest request, String uuidKey) throws Exception {
        IRequest requestContext = createRequestContext(request);
        ResponseData result = new ResponseData();
        // 检查是否属于频繁点击
        if (redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY) != null) {
            Long birthInMillis = Long
                    .parseLong(redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY));
            Long nowInMillis = new Date().getTime();
            // 距离上次发送验证码不足60秒
            if ((nowInMillis - birthInMillis)
                    / UserConstants.SC_MILLIS_PER_MINUTE < UserConstants.SC_VERIFICODE_SEND_INTERVAL) {
                result.setCode(UserConstants.SC_STILL_HAVE_TO_WAIT
                        + (UserConstants.SC_VERIFICODE_SEND_INTERVAL
                                - (nowInMillis - birthInMillis) / UserConstants.SC_MILLIS_PER_MINUTE)
                        + UserConstants.SC_SECOND);
                return result;
            }
        }
        Long userId = null;
        // 再检查uuidKey是否过期
        String uk = redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey);
        if (StringUtils.isEmpty(uk)) { // key已失效,报错
            result.setCode(UserConstants.PAGE_VALIDATE_EXPIRED);
            return result;
        } else {
            userId = Long.parseLong(uk);
        }
        User checkUser = userInfoService.selectUserByPrimaryKey(requestContext, userId);
        String verifyCode = "";
        // 随机生成6位不重复数字验证码
        String oldCode = redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE);
        if (oldCode == null) {
            verifyCode = generateVerifyCode();
        } else {
            verifyCode = oldCode;
        }
        redisTemplate.opsForValue().set(UserConstants.FILED_SC_VERIFICODE, verifyCode,
                UserConstants.KEY_EXPIRE_SIXTY_MIN, TimeUnit.SECONDS);
        // 发送验证码到邮箱
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(checkUser.getPhoneNo());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(userId);
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("verifyCode", verifyCode);
        data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
        data.put("loginName", checkUser.getLoginName());
        //updated by 13525 on 2018/03/05
        String sms_interface_address = profileService.getProfileValue(requestContext, SMSConstants.SMS_INTERFACE_ADDRESS);
        String sms_userid = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERID);
        String sms_username = profileService.getProfileValue(requestContext, SMSConstants.SMS_USERNAME);
        String sms_password = profileService.getProfileValue(requestContext, SMSConstants.SMS_PASSWORD);

        data.put("sms_interface_address", sms_interface_address);
        data.put("sms_userid", sms_userid);
        data.put("sms_username", sms_username);
        data.put("sms_password", sms_password);



       /* messageService.sendEmailMessage(-1L, null, UserConstants.EMAIL_SC_FORGET_PASSWORD, "PASSWORD", data,
                receiverlist, null);*/
        messageService.sendSmsMessage(-1L,"PASSWORD", data,receiverlist);
        // messageService.addMessage(null,
        // UserConstants.EMAIL_SC_FORGET_PASSWORD, data, null, receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到用户邮箱(--->{}),可能用到的参数userId={},验证码为{}", checkUser.getEmail(), userId, verifyCode);
        }
        // 发送验证码之后,在session里存当前时间防止用户频繁点击
        if (redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY) == null) {
            redisTemplate.opsForValue().set(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY,
                    Long.toString(new Date().getTime()), UserConstants.KEY_EXPIRE_SIXTY_MIN, TimeUnit.SECONDS);
        }
        List<Object> list = new ArrayList<Object>();
        Map<String, String> map = new HashMap<String, String>();
        // map.put("verifyCode", verifyCode);
        list.add(map);
        result.setRows(list);
        return result;
    }

    /**
     * 忘记密码,验证验证码.
     * 
     * @param request
     *            统一上下文
     * @param captcha
     *            验证码
     * @param way
     *            验证方式
     * @param uuidKey
     *            key
     * @return 返回验证情况给前台
     * @throws UserException
     *             抛出验证验证码相关业务异常
     */
    @RequestMapping(value = "/sys/sc/checkCaptcha")
    public ResponseData checkCaptcha(HttpServletRequest request, String captcha, String way, String uuidKey)
            throws UserException {
        HttpSession session = request.getSession();
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 四位验证码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, new Object[] {});
        }
        // 检查用户是否输入校验码
        if (StringUtils.isEmpty(captcha)) {
            throw new UserException(UserException.ENTER_CAPTCHA, new Object[] {});
        }
        // 检查session里是否有校验码
       /* if (redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE) == null) {
            throw new UserException(UserException.CAPTCHA_EXPIRED, new Object[] {});
        }*/
        String verifiCode = redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE);
        if (!verifiCode.equals(captcha)) { // 验证码不正确
            throw new UserException(UserException.CAPTCHA_ERROR, new Object[] {});
        }
        // 如果校验码正确,验证校验码是否已经过期
        Long birthInMillis = Long
                .parseLong(redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY));
        Long nowInMillis = new Date().getTime();
        if ((nowInMillis - birthInMillis)
                / UserConstants.SC_MILLIS_PER_MINUTE > UserConstants.SC_VERIFICODE_TIME_LIMIT) { // 距离上次发送验证码超过600秒
            throw new UserException(UserException.CAPTCHA_EXPIRED, new Object[] {});
        }
        // 校验码验证成功
        // 其实验证成功就相当于登录了,这里模拟一个登录后的状态,直接复用首次登录修改密码的页面
        Long userId = Long.parseLong(redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey));
        Long accountId = userService.getAccountIdByUserId(userId);
        session.setAttribute(Account.FIELD_ACCOUNT_ID, accountId);
        // 首次登录标记设置为N
        session.setAttribute(UserConstants.FIRST_LOGIN_FLAG, UserConstants.FIRST_LOGIN_FLAG_N);
        // 清除忘记密码无用的redis变量
        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            connection.del((UserConstants.FORGET_PWD_KEY + ":" + uuidKey).getBytes());
            connection.del(UserConstants.FILED_SC_VERIFICODE.getBytes());
            connection.del(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY.getBytes());
            return null;
        });
        // 返回成功到前台,前台执行跳转到修改密码界面
        return new ResponseData();
    }

    /**
     * 生成captchaKey.
     * 
     * @return 返回 UUID 形式的 captchaKey
     */
    private String generateCaptchaKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * 随机生成6位不重复数字验证码.
     * 
     * @return 验证码
     */
    private String generateVerifyCode() {
        StringBuilder sb = new StringBuilder();
        String str = UserConstants.SC_VERIFICODE_BASE_STR;
        Random r = new Random();
        for (int i = 0; i < UserConstants.SC_VERIFICODE_LENGTH; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }
}
