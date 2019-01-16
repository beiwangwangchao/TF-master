/*
 *
 */
package com.lkkhpg.dsis.admin.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.system.service.ISysUserRoleAssignService;
import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.admin.user.service.IUserInfoService;
import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.system.dto.SysUserRoleAssign;
import com.lkkhpg.dsis.common.user.dto.IpointUser;
import com.lkkhpg.dsis.common.user.dto.UserRelation;
import com.lkkhpg.dsis.common.user.mapper.IpointUserMapper;
import com.lkkhpg.dsis.common.user.mapper.UserRelationMapper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.dto.system.Role;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.RoleMapper;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.security.PasswordManager;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;
import com.lkkhpg.dsis.platform.service.IUserService;

/**
 * 用户信息服务接口实现.
 *
 * @author Zhaoqi
 */
@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {

    private final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    // 跳转
    protected static final String REDIRECT = "redirect:";

    private static final Long BASE_MENBER_EIGHT = 8L;

    @Autowired
    private PasswordManager passwordManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private IpointUserMapper ipointUserMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private UserRelationMapper userRelationMapper;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ISysUserRoleAssignService sysUserRoleAssignService;
    @Autowired
    private SpmMarketMapper spmMarketMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IpointUser create(IRequest request, IpointUser ipointUser) throws Exception {
        //String tmpPassword = "";
        /*if (logger.isDebugEnabled()) {
            logger.debug("此处应调用随机程序生成随机口令,暂时口令设置为-11111111");
        }*/
        // 调用程序生成随机口令
        String tmpPassword = generateRandomPassword();

        //账号临时密码失效时间
        String expiryHourStr = profileService.getProfileValue(request, MemberConstants.TEMP_PWD_EXPIRY_DATE);
        int expiryHour;
        if (expiryHourStr == null) {
            expiryHour = 1;
        } else {
            expiryHour = Integer.parseInt(expiryHourStr);
        }
        Date expiryDate = DateUtils.addHours(new Date(), expiryHour);
        //tmpPassword = UserConstants.TMP_PASSWORD;
        ipointUser.setPassword(tmpPassword);
        ipointUser.setPwdExpiryDate(expiryDate);
        ipointUser.setFirstLoginFlag(UserConstants.FIRST_LOGIN_FLAG_Y);
        User user = userService.create(request, ipointUser);
        if (user == null) {
            throw new UserException(UserException.USER_INSERT_FAIL, null);
        }
        // 成功创建USER之后
        if (logger.isDebugEnabled()) {
            logger.debug("成功调用HAP创建USER {}", ipointUser.toString());
        }
        // 调用接口发送短信通知用户
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(ipointUser.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(ipointUser.getUserId());
        receiverlist.add(receiver);
        // 设置邮件模板里的数据
        data.put("tmpPassword", tmpPassword);
        data.put("limit", UserConstants.DEFAULT_PAGE); // 临时口令有效期为1个小时
        data.put("loginName", ipointUser.getLoginName());
        messageService.sendEmailMessage(-1L, null, UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, "PASSWORD", data,
                receiverlist, null);
        //messageService.addMessage(null, UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, data, null, receiverlist);
        // 插入用户会员关系
        if (ipointUser.getMemberId() != null) {
            UserRelation ur = new UserRelation();
            ur.setUserId(ipointUser.getUserId());
            ur.setMemberId(ipointUser.getMemberId());
            ur.setCreatedBy(ipointUser.getCreatedBy());
            ur.setCreationDate(ipointUser.getCreationDate());
            ur.setLastUpdatedBy(ipointUser.getLastUpdatedBy());
            ur.setLastUpdateDate(ipointUser.getLastUpdateDate());
            userRelationMapper.insert(ur);
        }
        return ipointUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public IpointUser update(IRequest request, IpointUser ipointUser) throws Exception {
        // 如果用户修改了密码,同时修改首次登录标记
        Long accountId = userService.getAccountIdByUserId(ipointUser.getUserId());
        ipointUser.setFirstLoginFlag(UserConstants.FIRST_LOGIN_FLAG_N);
        if (ipointUser.getPassword() != null && !"".equals(ipointUser.getPassword())) {
            // 修改密码
            accountService.updatePassword(accountId, ipointUser.getPassword(),
                    new Date(System.currentTimeMillis() + UserConstants.PWD_EXPRIRY_DATE), null);
            // 修改首次登录标记
            ipointUser.setFirstLoginFlag(UserConstants.FIRST_LOGIN_FLAG_Y);
            // 调用接口发送短信通知用户
//            List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
//            MessageReceiver receiver = new MessageReceiver();
//            Map<String, Object> data = new HashMap<String, Object>();
//            receiver.setMessageAddress(ipointUser.getEmail());
//            receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
//            receiver.setReceiverId(ipointUser.getUserId());
//            receiverlist.add(receiver);
//            // 设置邮件模板里的数据
//            data.put("tmpPassword", ipointUser.getPassword());
//            data.put("limit", UserConstants.DEFAULT_PAGE); // 临时口令有效期为1个小时
//            data.put("loginName", ipointUser.getLoginName());
//            messageService.sendEmailMessage(-1L, null, UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, "PASSWORD", data,
//                    receiverlist, null);
            //messageService.addMessage(null, UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, data, null, receiverlist);
        }
        // 成功调用HAP创建USER之后
        User user = userService.update(request, ipointUser);

        if (user == null) {
            throw new UserException(UserException.USER_UPDATE_FAIL, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("成功update USER {}", ipointUser.getUserId());
        }
        // 插入用户会员关系
        if (ipointUser.getMemberId() != null) {
            UserRelation ur = new UserRelation();
            ur.setUserId(ipointUser.getUserId());
            List<UserRelation> list = userRelationMapper.selectUserRelation(ur);
            if (!(list.isEmpty())) {
                ur = list.get(0);
            }
            ur.setCreatedBy(ipointUser.getCreatedBy());
            ur.setCreationDate(ipointUser.getCreationDate());
            ur.setLastUpdatedBy(ipointUser.getLastUpdatedBy());
            ur.setLastUpdateDate(ipointUser.getLastUpdateDate());
            if (ur.getMemberId() == null) {
                ur.setMemberId(ipointUser.getMemberId());
                userRelationMapper.insert(ur);
            } else if (ur.getMemberId() != null) {
                ur.setMemberId(ipointUser.getMemberId());
                userRelationMapper.update(ur);
            }
        }
        self().queryCountByEmailPhone(ipointUser);
        return ipointUser;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<IpointUser> getIpointUsers(IRequest request, IpointUser ipointUser, int page, int pagesize)
            throws UserException {
        PageHelper.startPage(page, pagesize);
        return ipointUserMapper.selectIpointUsers(ipointUser);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<IpointUser> getIpointUsers(IRequest request, IpointUser ipointUser){
        return ipointUserMapper.selectIpointUsers(ipointUser);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long accountId)
            throws UserException {
        // 密码不为空校验
        if ("".equals(oldPwd) || "".equals(newPwd) || "".equals(newPwdAgain)) {
            throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
        }
        // 两次密码一致性检查
        if (!newPwd.equals(newPwdAgain)) {
            throw new UserException(UserException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }
        // 获取指定用户密码
        Account account = accountService.getAccountByAccountId(accountId);
        String pwd = account.getPassword();
        // 验证旧密码是否正确
        if (!passwordManager.encode(oldPwd).equals(pwd)) {
            throw new UserException(UserException.USER_PASSWORD_WRONG, null);
        }
        // 验证新密码有效1-格式
        // String regPwdOne
        // ="^(?![^a-zA-Z]+$)(?!\\D+$)[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$";
        if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
        }
        // 验证新密码有效2-与旧密码不一致
        if (passwordManager.encode(newPwd).equals(pwd)) {
            throw new UserException(UserException.USER_PASSWORD_SAME, null);
        }
        return true;

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean validateAccountPassword(IRequest request, String newPwd, String newPwdAgain, Long accountId)
            throws UserException {
        // 两次密码一致性检查
        if (!newPwd.equals(newPwdAgain)) {
            throw new UserException(UserException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }
        // 获取指定用户密码
        Account account = accountService.getAccountByAccountId(accountId);
        String pwd = account.getPassword();
        // 验证新密码有效1-格式
        // String regex =
        // "^[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$"; //
        // 此处最短应为8
        if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
        }
        // 验证新密码有效2-与旧密码不一致
        if (passwordManager.encode(newPwd).equals(pwd)) {
            throw new UserException(UserException.USER_PASSWORD_SAME, null);
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException {
        User user = new User();
        user.setUserId(userId);
        List<User> checkUsers = userService.selectUsers(user, UserConstants.DEFAULT_PAGE,
                UserConstants.DEFAULT_PAGE_SIZE);
        if (checkUsers.isEmpty()) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        User checkUser = checkUsers.get(0);
        if (!UserConstants.USER_STATUS_ACTIVE.equals(checkUser.getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, null);
        }
        return checkUser;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User selectUserByLoginName(IRequest request, String loginName) throws UserException {
        User user = new User();
        user.setLoginName(loginName);
        User checkUser = userMapper.selectUserByLoginName(user);
        if (checkUser == null) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        if (!UserConstants.USER_STATUS_ACTIVE.equals(checkUser.getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, null);
        }
        return checkUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointUser> checkLoginName(IRequest request, IpointUser ipointUser) throws Exception {
        isExistsUser(request, ipointUser);
        isExistsPhone(request, ipointUser);
        isExistsEmail(request, ipointUser);
        IpointUser user = create(request, ipointUser);
        List<IpointUser> list = new ArrayList<IpointUser>();
        list.add(user);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointUser> isExistsUser(IRequest request, IpointUser ipointUser) throws UserException {
        List<IpointUser> list = validateUser(request, ipointUser);
        if (!list.isEmpty()) {
            throw new UserException(UserException.USER_EXIST, new Object[]{});
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointUser> isExistsPhone(IRequest request, IpointUser ipointUser) throws UserException {
        List<IpointUser> list = validatePhone(request, ipointUser);
        if (!list.isEmpty()) {
            throw new UserException(UserException.PHONE_EXIST, new Object[]{});
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<IpointUser> isExistsEmail(IRequest request, IpointUser ipointUser) throws UserException {
        List<IpointUser> list = validateEmail(request, ipointUser);
        if (!list.isEmpty()) {
            throw new UserException(UserException.EMAIL_EXIST, new Object[]{});
        }
        return list;
    }

    /**
     * 用户名校验通用方法.
     *
     * @param request    统一上下文
     * @param ipointUser 包含用户名信息
     * @param page       页码
     * @param pagesize   页数
     * @throws UserException 抛出验证用户非空的业务异常
     */
    private List<IpointUser> validateUser(IRequest request, IpointUser ipointUser) throws UserException {
        // 非空验证
        //if (ipointUser.getLoginName() == null || "".equals(ipointUser.getLoginName())) {
        //throw new UserException(UserException.USER_NOT_ISEMPTY, new Object[] {});
        //}
        // 格式验证
        if (!ipointUser.getLoginName().matches(UserConstants.LOGIN_NAME_REGEX)) {
            throw new UserException(UserException.USER_FORMAT, new Object[]{});
        }
        IpointUser checkIpointUser = new IpointUser();
        checkIpointUser.setLoginName(ipointUser.getLoginName());
        List<IpointUser> list = ipointUserMapper.checkIpointUsers(checkIpointUser);
        return list;
    }

    /**
     * 邮箱校验通用方法.
     *
     * @param request    统一上下文
     * @param ipointUser 查询依据-email
     * @param page       页码
     * @param pagesize   页数
     * @throws UserException 抛出邮箱格式错误的业务异常
     */
    private List<IpointUser> validateEmail(IRequest request, IpointUser ipointUser) throws UserException {
        // 非空验证
//        if (ipointUser.getEmail() == null || "".equals(ipointUser.getEmail())) {
//            throw new UserException(UserException.EMAIL_NOT_ISEMPTY, new Object[] {});
//        }
        // 格式验证
        if (!ipointUser.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[]{});
        }
        List<IpointUser> list = new ArrayList<IpointUser>();
        //if (UserConstants.USER_TYPE_INNER.equals(ipointUser.getUserType())) {
        IpointUser checkIpointUser = new IpointUser();
        checkIpointUser.setEmail(ipointUser.getEmail());
        list = ipointUserMapper.checkIpointUsers(checkIpointUser);
        //}
        return list;
    }

    /**
     * 手机号码校验通用方法.
     *
     * @param request    统一上下文
     * @param ipointUser 查询依据-phone
     * @param page       页码
     * @param pagesize   页数
     * @throws UserException 抛出手机未通过验证的业务异常
     */
    private List<IpointUser> validatePhone(IRequest request, IpointUser ipointUser) throws UserException {
        // 非空验证
//        if (ipointUser.getPhoneNo() == null || "".equals(ipointUser.getPhoneNo())) {
//            throw new UserException(UserException.PHONE_NOT_ISEMPTY, new Object[] {});
//        }
        // 格式验证
        if (!ipointUser.getPhoneNo().matches(UserConstants.PHONE_REGEX)) {
            throw new UserException(UserException.PHONE_FORMAT, new Object[]{});
        }
        List<IpointUser> list = new ArrayList<IpointUser>();
        //if (UserConstants.USER_TYPE_INNER.equals(ipointUser.getUserType())) {
        IpointUser checkIpointUser = new IpointUser();
        checkIpointUser.setPhoneNo(ipointUser.getPhoneNo());
        checkIpointUser.setAreaCode(ipointUser.getAreaCode());
        list = ipointUserMapper.checkIpointUsers(checkIpointUser);
        //}
        return list;
    }

    @Override
    public int selectRolesByUserWithoutLang(IRequest iRequest, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ModelAndView view = new ModelAndView();
        User user = userMapper.selectUserByAccountId(iRequest.getAccountId());
        List<Role> roles = roleMapper.selectRolesByUserWithoutLang(user);
        if (roles.size() == 1) {
            // 只有一个角色
            Role role = roles.get(0);
            session.setAttribute(Role.FIELD_ROLE_ID, role.getRoleId());
            // 获取该角色分配的组织
            List<SysUserRoleAssign> assigns = sysUserRoleAssignService.getUserRoleAssigns(user.getUserId(),
                    role.getRoleId());
            Long salesOrgId = null;
            Long invOrgId = null;
            for (SysUserRoleAssign userRoleAssign : assigns) {
                switch (userRoleAssign.getAssignType()) {
                    // 销售区域
                    case SysUserRoleAssign.SALES_ORG_ASSIGN_TYPE:
                        if (salesOrgId == null) {
                            salesOrgId = userRoleAssign.getAssignValue();
                        }
                        if (SystemProfileConstants.YES.equals(userRoleAssign.getDefaultFlag())) {
                            salesOrgId = userRoleAssign.getAssignValue();
                        }
                        break;
                    // 库存组织
                    case SysUserRoleAssign.INV_ORG_ASSIGN_TYPE:
                        if (invOrgId == null) {
                            invOrgId = userRoleAssign.getAssignValue();
                        }
                        if (SystemProfileConstants.YES.equals(userRoleAssign.getDefaultFlag())) {
                            invOrgId = userRoleAssign.getAssignValue();
                        }
                        break;
                    default:
                        break;
                }
            }
            session.setAttribute(SystemProfileConstants.INV_ORG_ID, invOrgId);
            session.setAttribute(SystemProfileConstants.SALES_ORG_ID, salesOrgId);
            session.setAttribute(User.FILED_USER_ID, user.getUserId());
            // 将角色 库存组织 销售组织 市场放到cookie中
//            addCookie(User.FILED_USER_ID, user.getUserId().toString(), request, response);
            addCookie(Role.FIELD_ROLE_ID, role.getRoleId().toString(), request, response);
            addCookie(SystemProfileConstants.INV_ORG_ID, invOrgId == null ? null : invOrgId.toString(),
                    request, response);
            addCookie(SystemProfileConstants.SALES_ORG_ID,
                    salesOrgId == null ? null : salesOrgId.toString(), request, response);
            // 市场
            SpmMarket market = spmMarketMapper.selectBySalesOrgId(salesOrgId);
            if (market != null) {
                session.setAttribute(SystemProfileConstants.MARKET_ID, market.getMarketId());
                addCookie(SystemProfileConstants.MARKET_ID, market.getMarketId().toString(), request,
                        response);
            }
            if (logger.isDebugEnabled()) {
                logger.debug(" only one role, skip select role page...");
                logger.debug(" role id :{}", role.getRoleId());
                logger.debug(" inv org id :{}", invOrgId);
                logger.debug(" sales org id :{}", salesOrgId);
                logger.debug(" market id :{}", market != null ? market.getMarketId() : "");
            }
        }
        return roles.size();
    }

    private void addCookie(String cookieName, String cookieValue, HttpServletRequest request,
                           HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @Override
    public Integer queryCountByEmailPhone(IpointUser ipointUser) throws UserException {
        Integer countEmail = ipointUserMapper.queryCountByEmail(ipointUser.getEmail());
        if (countEmail > 1) {
            throw new UserException(UserException.EMAIL_EXIST, null);
        }
        IpointUser iuser = new IpointUser();
        iuser.setAreaCode(ipointUser.getAreaCode());
        iuser.setPhoneNo(ipointUser.getPhoneNo());
        Integer countPhone = ipointUserMapper.queryCountByPhone(iuser);
        if (countPhone > 1) {
            throw new UserException(UserException.PHONE_EXIST, null);
        }
        return null;
    }

    /*
     * 找回用户-生成八位随机密码.
     */
    private String generateRandomPassword() {
        // 验证码
        String password = "";
        for (int i = 0; i < BASE_MENBER_EIGHT; i++) {
            password = password + (int) (Math.random() * MemberConstants.BASE_MEMBER_NINE);
        }
        return password;
    }

    @Override
    public List<Member> checkPhone(IRequest request, Member member) {
        return memberMapper.checkPhoneOrEmail(member);
    }

    @Override
    public Member selectMarketId(IRequest request, Long memberId) {
        return memberMapper.selectMarketId(memberId);
    }
}
