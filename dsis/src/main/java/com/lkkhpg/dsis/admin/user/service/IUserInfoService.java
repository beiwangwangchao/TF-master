/*
 *
 */
package com.lkkhpg.dsis.admin.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lkkhpg.dsis.admin.user.exception.UserException;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.common.user.dto.IpointUser;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * 用户信息服务接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IUserInfoService extends ProxySelf<IUserInfoService> {

    /**
     * 创建新用户.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            用户信息
     * @return 响应信息
     * @throws UserException
     *             抛出创建用户失败异常
     * @throws Exception
     *             抛出短信接口异常
     */
    IpointUser create(IRequest request, @StdWho IpointUser ipointUser) throws UserException, Exception;

    /**
     * 更新用户信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            用户信息
     * @return 响应信息
     * @throws UserException
     *             抛出更新用户失败异常
     * @throws Exception
     *             抛出短信接口异常
     */
    IpointUser update(IRequest request, @StdWho IpointUser ipointUser) throws UserException, Exception;

    /**
     * 查询用户信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            包含传入的用户查找依据
     * @param page
     *            分页信息
     * @param pagesize
     *            分页信息
     * @return 返回查询到的用户
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    List<IpointUser> getIpointUsers(IRequest request, IpointUser ipointUser, int page, int pagesize)
            throws UserException;

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
     * @throws UserException
     *             抛出验证密码失败的业务异常
     */
    boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long accountId)
            throws UserException;

    /**
     * 用户密码修改前校验参数是否有效.
     * 
     * @param request
     *            统一上下文
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            新密码再次输入
     * @param accountId
     *            用户账户ID
     * @return 返回是否通过校验
     * @throws UserException
     *             抛出验证密码失败的业务异常
     */
    boolean validateAccountPassword(IRequest request, String newPwd, String newPwdAgain, Long accountId)
            throws UserException;

    /**
     * 忘记密码,接收用户ID查询用户详细信息.
     * 
     * @param request
     *            统一上下文
     * @param userId
     *            用户ID
     * @return 返回查询到的用户信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException;

    /**
     * 忘记密码,接收用户登录名查询用户详细信息.
     * 
     * @param request
     *            统一上下文
     * @param loginName
     *            用户登录名
     * @return 返回查询到的用户信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    User selectUserByLoginName(IRequest request, String loginName) throws UserException;
    
    List<IpointUser> checkLoginName(IRequest request, IpointUser ipointUser) throws Exception;
    
    List<IpointUser> isExistsUser(IRequest request, IpointUser ipointUser) throws UserException;
    
    List<IpointUser> isExistsPhone(IRequest request, IpointUser ipointUser) throws UserException;
    
    List<IpointUser> isExistsEmail(IRequest request, IpointUser ipointUser) throws UserException;
    
    int selectRolesByUserWithoutLang(IRequest iRequest, HttpServletRequest request, HttpServletResponse response);
    
    /**
     * 更新个人信息时电话邮箱的唯一性.
     * @param ipointUser update数据
     * @return 记录数量
     * @throws UserException 唯一性异常
     */
    Integer queryCountByEmailPhone(IpointUser ipointUser) throws UserException;

    List<Member> checkPhone(IRequest request, Member member);
    Member selectMarketId(IRequest request, Long memberId);

    /**
     * 查找用户
     * @param request
     * @param ipointUser 用户dto
     * @return
     */
    List<IpointUser> getIpointUsers(IRequest request, IpointUser ipointUser);

}
