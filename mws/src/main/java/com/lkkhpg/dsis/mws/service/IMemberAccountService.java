/*
 *
 */
package com.lkkhpg.dsis.mws.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;
import com.lkkhpg.dsis.common.config.dto.SpmSalesOrganization;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.mws.exception.MemberException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.MessageReceiver;
import com.lkkhpg.dsis.platform.exception.AccountException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会员登录服务接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IMemberAccountService extends ProxySelf<IMemberAccountService> {

    /**
     * 获取memberId等值.
     * 
     * @param account
     *            账户信息
     * @return 会员相关信息
     */
    Member selectByMemberCode(Account account);

    /**
     * 首次登陆修改密码.
     * 
     * @param request
     * @param newPwd
     *            新密码
     * @param againPwd
     *            再次输入密码
     * @return 响应信息
     * @throws AccountException
     */
    Integer updatePassword(IRequest request, String newPwd, String againPwd) throws AccountException;

    /**
     * 找回密码校验用户名.
     * 
     * @param request
     *            统一上下文
     * @param account
     *            会员账户信息
     * @return 响应信息
     */
    Member isExistsMember(IRequest request, Account account);

    List<Member> checkPhoneOrEmail(IRequest request, Member member);

    List<Member> checkUser(IRequest request, Member member);

    SpmSalesOrganization selectByMarketId(IRequest request, Long marketId);

    /**
     * 用户密码修改前校验参数是否有效.
     * 
     * @param request
     *            统一上下文
     * @param oldPwd
     *            原密码
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            新密码再次输入
     * @param accountId
     *            用户账户ID
     * @return 返回是否通过校验
     * @throws MemberException
     *             抛出验证密码失败的业务异常
     */
    boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long accountId)
            throws MemberException;

    Member selectMarketId(IRequest request, Long memberId);

    Integer createAccount(IRequest requestContext, Member member);

    /**
     * 查询是否存在电话号码相同的会员
     * @param phone
     * @return
     */
    Boolean selectMember(String phone);

    //Long insert(Member record);

    /**
     * 保存新建会员
     * @param
     * @return
     */
    Member saveMember(HttpServletRequest request, String newPwd, String phoneNo, String arePhone, String chinaName, String gender,Date dob,Long salesOrgId,String saleOrgName)throws Exception ;
/**
 * 用户密码设置校验参数是否有效.
 *
 * @param request
 *            统一上下文
 * @param newPwd
 *            新密码
 * @param againPwd
 *            新密码再次输入
 * @return 返回是否通过校验
 * @throws MemberException
 *             抛出验证密码失败的业务异常
 */
    public boolean validatePassword(IRequest request,  String newPwd, String againPwd)
            throws MemberException;

    /**
     * 发送短信
     * @param sender
     * @param smsAccountCode
     * @param data
     * @param receivers
     * @throws Exception
     */
    public void sendSmsMessage(Long sender, String smsAccountCode,
                               Map<String, Object> data, List<MessageReceiver> receivers) throws Exception;

    /**
     * 根据用户名查询
     * @param loginName
     * @return
     */
    public Account selectByLoginName(String loginName);

    /**
     * 根据会员id查询市场
     * @param members
     * @return
     */
    public List<SpmMarket> selectSpmMarketByMemberId(List<Member> members);

    /**
     * 查询销售区域.
     *
     * @param request           请求上下文
     * @param salesOrganization 销售区域DTO
     * @param page              页
     * @param pagesize          页数
     * @return 销售区域List
     */
    List<SpmSalesOrganization> querySalesOrganization(IRequest request, SpmSalesOrganization salesOrganization,
                                                      int page, int pagesize);
}
