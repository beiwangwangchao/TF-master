/*
 *
 */
package com.lkkhpg.dsis.mws.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.mapper.SpmMarketMapper;
import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.service.ICommMemberService;
import com.lkkhpg.dsis.common.service.ISpmMarketService;
import com.lkkhpg.dsis.platform.core.impl.RequestHelper;
import com.lkkhpg.dsis.platform.dto.system.AccountRelation;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.mail.ReceiverTypeEnum;
import com.lkkhpg.dsis.platform.mapper.system.AccountRelationMapper;
import com.lkkhpg.dsis.platform.service.IMessageService;
import com.lkkhpg.dsis.platform.service.IProfileService;
import com.lkkhpg.dsis.platform.utils.SmsSend;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.config.mapper.SpmSalesOrganizationMapper;
import com.lkkhpg.dsis.common.constant.UserConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.member.mapper.MemberMapper;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.mws.service.IMemberAccountService;
import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;
import com.lkkhpg.dsis.platform.security.PasswordManager;
import com.lkkhpg.dsis.platform.service.IAccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 会员登录服务接口实现.
 *
 * @author Zhaoqi
 */
@Service
@Transactional
public class MemberAccountServiceImpl implements IMemberAccountService {

    private Logger log = LoggerFactory.getLogger(MemberAccountServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMessageService messageService;


    @Autowired
    private AccountRelationMapper accountRelationMapper;

    @Autowired
    private SpmSalesOrganizationMapper spmSalesOrganizationMapper;

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private ICommMemberService commMemberService;

    @Autowired
    SpmMarketMapper spmMarketMapper;

    @Override
    public Member selectByMemberCode(Account account) {
        return memberMapper.selectByAccountId(account);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer updatePassword(IRequest request, String newPwd, String againPwd) throws AccountException {
        Account account = new Account();
        // 密码不为空校验
        if ("".equals(newPwd) || "".equals(againPwd)) {
            throw new AccountException(MemberException.NEW_PASSWORD_NOT_NULL, MemberException.NEW_PASSWORD_NOT_NULL,
                    null);
        }
        // 两次密码一致性检查
        if (!newPwd.equals(againPwd)) {
            throw new AccountException(MemberException.TWE_PASSWORD_NOT_MATCH, MemberException.TWE_PASSWORD_NOT_MATCH,
                    null);
        }
        // 验证新密码有效1-格式
        if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new AccountException(MemberException.PASSWORD_FORMAT_ERROR, MemberException.PASSWORD_FORMAT_ERROR,
                    null);
        }
        account.setAccountId(request.getAccountId());
        account.setPassword(passwordManager.encode(newPwd));
        account.setFirstLoginFlag(UserConstants.FIRST_LOGIN_FLAG_N);
        account.setLastUpdatedBy(request.getAccountId());
        account.setLastUpdateDate(new Date());
        account.setCreatedBy(request.getAccountId());
        account.setCreationDate(new Date());
        //过期日期设置系统日期最大值
        try {
            account.setPwdExpiryDate(
                    new SimpleDateFormat(BaseConstants.SYSTEM_DATE_FORMAT).parse(BaseConstants.SYSTEM_MAX_DATE));
        } catch (ParseException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.toString() + BaseConstants.SYSTEM_MAX_DATE);
            }
        }
        return accountMapper.updateMemberPassword(account);
    }

    @Override
    public Member isExistsMember(IRequest request, Account account) {
        return memberMapper.selectByAccountId(account);
    }

    @Override
    public List<Member> checkPhoneOrEmail(IRequest request, Member member) {
        return memberMapper.checkPhoneOrEmail(member);
    }

    @Override
    public List<Member> checkUser(IRequest request, Member member) {
        return memberMapper.checkUser(member);
    }

    @Override
    public SpmSalesOrganization selectByMarketId(IRequest request, Long marketId) {
        return spmSalesOrganizationMapper.getSalesOrgId(marketId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean validatePassword(IRequest request, String oldPwd, String newPwd, String againPwd, Long accountId)
            throws MemberException {
        // 密码不为空校验
        if ("".equals(oldPwd) || "".equals(newPwd) || "".equals(againPwd)) {
            throw new MemberException(MemberException.PASSWORD_NOT_EMPTY, null);
        }
        // 两次密码一致性检查
        if (!newPwd.equals(againPwd)) {
            throw new MemberException(MemberException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }
        // 获取指定用户密码
        Account account = accountService.getAccountByAccountId(accountId);
        String pwd = account.getPassword();
        // 验证旧密码是否正确
        if (!passwordManager.encode(oldPwd).equals(pwd)) {
            throw new MemberException(MemberException.USER_PASSWORD_WRONG, null);
        }
        // 验证新密码有效1-格式
        // String regPwdOne
        // ="^(?![^a-zA-Z]+$)(?!\\D+$)[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$";
        if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new MemberException(MemberException.USER_PASSWORD_REQUIREMENT, null);
        }
        // 验证新密码有效2-与旧密码不一致
        if (passwordManager.encode(newPwd).equals(pwd)) {
            throw new MemberException(MemberException.USER_PASSWORD_SAME, null);
        }
        return true;
    }

    @Override
    public Member selectMarketId(IRequest request, Long memberId) {
        return memberMapper.selectMarketId(memberId);
    }

    @Override
    public Integer createAccount(IRequest requestContext, Member member) {
//        return memberMapper.createAccount(uname, passwordManager.encode(password), phoneNum);

//        member.setLoginName(uname);
//        member.setPhoneNo(phoneNum);

        // 校验会员账户是否已存在
        if (accountService.selectByLoginName(member.getMemberCode()) != null) {
            try {
                throw new CommMemberException(CommMemberException.MSG_ERROR_MEM_ACCOUNT_EXIST,
                        new Object[]{member.getMemberCode()});
            } catch (CommMemberException e) {
                e.printStackTrace();
            }
        }

        try {
            // 插入账户表
            Account account = new Account();
            account.setLoginName(member.getMemberCode());
            account.setPassword(passwordManager.getDefaultPassword()); // 初始密码有随机生成
            account.setStatus(Account.STATUS_ACTIVE);
            account.setFirstLoginFlag(BaseConstants.YES);
            account.setPwdExpiryDate(null);
            account.setCreatedBy(member.getCreatedBy());
            account.setCreationDate(member.getCreationDate());
            account.setLastUpdatedBy(member.getLastUpdatedBy());
            account.setLastUpdateDate(member.getLastUpdateDate());
            Account dbAccount = accountService.insert(account);


            // 插入账户关联表
            AccountRelation accountRel = new AccountRelation();
            accountRel.setAccountId(dbAccount.getAccountId());
            accountRel.setAccountType(Account.ACCOUNT_TYPE_MEM);
            accountRel.setRelPartyId(member.getMemberId());
            accountRel.setCreatedBy(member.getCreatedBy());
            accountRel.setCreationDate(member.getCreationDate());
            accountRel.setLastUpdatedBy(member.getLastUpdatedBy());
            accountRel.setLastUpdateDate(member.getLastUpdateDate());
            accountRelationMapper.insert(accountRel);

            // 由配置文件控制是否发送短信
            String sendSmsMsg = profileService.getProfileValue(requestContext, MemberConstants.PROFILE_MM_SEND_SMS_MSG);
            if (BaseConstants.YES.equals(sendSmsMsg)) {
                // 调用接口发送短信通知用户
                List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
                MessageReceiver receiver = new MessageReceiver();
                Map<String, Object> data = new HashMap<String, Object>();
                receiver.setMessageAddress(member.getAreaCode() + member.getPhoneNo());
                receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                receiver.setReceiverId(member.getMemberId());
                receiverlist.add(receiver);
                // 设置邮件模板里的数据
                data.put("tmpPassword", passwordManager.getDefaultPassword());
                data.put("limit", UserConstants.DEFAULT_PAGE); // 临时口令有效期为1个小时
                data.put("loginName", account.getLoginName());
                // messageService.addMessage(null, "phone_user_temp_pwd", data,
                // null, receiverlist);
                messageService.addSmsMessage(null, "forget_password", "phone_user_temp_pwd", data, receiverlist);

            }
        } catch (Exception e) {
            try {
                throw new CommMemberException(CommMemberException.MSG_ERROR_CREATE_MEM_ACCOUNT,
                        new Object[]{e.getMessage()});
            } catch (CommMemberException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Boolean selectMember(String phone){
        Member member = new Member();
        member.setPhoneNo(phone);
        //TODO:待改
        member.setSalesOrgId(61000341L);
        List<Member> memberList = memberMapper.selectMemberByPhone(member);
        if (memberList.size()==0) {
            return false;
        }
        return true;
    }

    @Override
    public Member saveMember(HttpServletRequest request, String newPwd, String phoneNo, String arePhone, String chinaName, String gender,Date dob,Long salesOrgId,String saleOrgName)
            throws Exception {
        Member member = new Member();
        member.setTempPassword(newPwd);
        member.setChineseName(chinaName);
        member.setChineseFirstName(chinaName);
        member.setPhoneNo(phoneNo);
        member.setAreaPhone(arePhone);
        member.setAreaCode(arePhone);
        member.setDob(dob);
        //TODO:待改
        member.setMemberType("IDV");
        member.setMarketId(61000321L);
        member.setCompanyId(61000321L);
        member.setSalesOrgId(salesOrgId);
        member.setSalesOrganization(saleOrgName);
        member.setMemberRole("DIS");
        member.setStatus("ACTV");
        //member.setMemberCode("");
        member.setJointSiteType("DSIS");
        member.setBonusRcvMethod("BANK");
        member.setSyncFlag("N");
        member.setAdOptIn("Y");
        member.setSysMsgIn("Y");
        member.setAcceptBonus("Y");
        member.setGender(gender);
        member.setObjectVersionNumber(1L);
        member.setRequestId(-1L);
        member.setProgramId(-1L);
        member.setCreatedBy(-1L);
        member.setLastUpdatedBy(1L);
        member.setFlag("N");
        member.setJointSite("61000362");
        member.setJointSiteCode("BSYMXS");
        member.setJointSiteType("DSIS");
        member.setStatusDesc("有效");




        IRequest iRequest = RequestHelper.createServiceRequest(request);
        validateMember(iRequest, member);

        return commMemberService.saveMember(iRequest, member);

        //member.setMemberCode();//会员账号
        //return memberMapper.insert(member);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean validatePassword(IRequest request,  String newPwd, String againPwd)
            throws MemberException {
        // 密码不为空校验
        if ("".equals(newPwd) || "".equals(againPwd)) {
            throw new MemberException(MemberException.PASSWORD_NOT_EMPTY, null);
        }
        // 两次密码一致性检查
        if (!newPwd.equals(againPwd)) {
            throw new MemberException(MemberException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }
        // 验证新密码有效1-格式
        // String regPwdOne
        // ="^(?![^a-zA-Z]+$)(?!\\D+$)[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$";
        if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new MemberException(MemberException.USER_PASSWORD_REQUIREMENT, null);
        }
        return true;
    }

    //输入中文名校验
    private void validateMember(IRequest request, Member member) throws CommMemberException {
    // 中文名或英文名至少输入一个
        if (StringUtils.isEmpty(member.getChineseFirstName()) && StringUtils.isEmpty(member.getEnglishFirstName())) {
        throw new CommMemberException(CommMemberException.MSG_ERROR_FIRST_NAME_REQUIRE_ONE, null);
    }

    // 中文姓和名汉字长度校验
    int chineseFirstNameLength = 0;
    int chineseLastNameLength = 0;
    chineseFirstNameLength = member.getChineseFirstName() != null ? member.getChineseFirstName().getBytes().length
                : 0;
    chineseLastNameLength = member.getChineseLastName() != null ? member.getChineseLastName().getBytes().length : 0;
        if (chineseFirstNameLength > 48) {
        throw new CommMemberException(CommMemberException.MSG_ERROR_CHINESE_FIRST_NAME_LENGTH_ERROR, null);
    }

        if (chineseLastNameLength > 45) {
        throw new CommMemberException(CommMemberException.MSG_ERROR_CHINESE_LAST_NAME_LENGTH_ERROR, null);
    }
    // 英文姓和名汉字长度校验
    int englishFirstNameLength = 0;
    int englishLastNameLength = 0;
    englishFirstNameLength = member.getEnglishFirstName() != null ? member.getEnglishFirstName().length() : 0;
    englishLastNameLength = member.getEnglishLastName() != null ? member.getEnglishLastName().length() : 0;
    if (englishFirstNameLength > 48) {
        throw new CommMemberException(CommMemberException.MSG_ERROR_ENGLISH_FIRST_NAME_LENGTH_ERROR, null);
    }

    if (englishLastNameLength > 45) {
        throw new CommMemberException(CommMemberException.MSG_ERROR_ENGLISH_LAST_NAME_LENGTH_ERROR, null);
    }
    }


    @Override
    public void sendSmsMessage(Long sender,String smsAccountCode,
                               Map<String, Object> data, List<MessageReceiver> receivers) throws Exception{



        String sms_interface_address = (String) data.get("sms_interface_address");
        String sms_userid = (String) data.get("sms_userid");
        String sms_username = (String) data.get("sms_username");
        String sms_password = (String) data.get("sms_password");


        SmsSend smsSend = new SmsSend();
        String content ="【天府电商】尊敬的用户：我们已收到您的忘记密码请求，验证码为： "+data.get("verifyCode")+", 有效期为:10分钟。";

        for (MessageReceiver current : receivers) {
            smsSend.anOtherWaySendSms(current.getMessageAddress(), content, sms_interface_address, sms_userid,
                    sms_username, sms_password);
        }
    }

    @Override
    public Account selectByLoginName(String loginName){
        return accountService.selectByLoginName(loginName);
    }

    public List<SpmMarket> selectSpmMarketByMemberId(List<Member> members){
        List<SpmMarket> spmMarketList = new ArrayList<SpmMarket> ();
        for (Member member:members) {
            List<SpmMarket> spmMarkets= spmMarketMapper.selectSpmMarketByMemberId(member.getMemberId());
            spmMarketList.addAll(spmMarkets);
        }
        return  spmMarketList;
    }


    /**
     * 查询销售区域.
     *
     * @param request
     *            请求上下文
     * @param salesOrganization
     *            销售区域DTO
     * @return 销售区域List
     */
    @Override
    public List<SpmSalesOrganization> querySalesOrganization(IRequest request, SpmSalesOrganization salesOrganization,
                                                             int page, int pagesize) {
        return spmSalesOrganizationMapper.querySalesOrgByMarkId(salesOrganization);
    }

}