/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.Date;

import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.exception.AccountException;

/**
 * 用户管理服务接口.
 * 
 * @author wuyichu
 * @author njq.niu@hand-china.com
 */
public interface IAccountService extends ProxySelf<IAccountService> {

    /**
     * 根据用户名获取账户.
     * 
     * @param loginName
     *            用户名
     * @return Account or null
     */
    Account selectByLoginName(String loginName);

    /**
     * 登录校验.
     * 
     * @param account
     *            用户输入参数构造的 account
     * @return 数据库中存储的 account
     * @throws AccountException
     *             AccountException
     */
    Account login(final Account account) throws AccountException;


    Account loginSomeOtherUsers(final Account account) throws AccountException;
    /**
     * 业务员登录校验.
     * 
     * @param account
     *            用户输入参数构造的 account
     * @return 数据库中存储的 account
     * @throws AccountException
     *             AccountException
     */
    Account dappLogin(final Account account) throws AccountException;

    /**
     * 密码修改.
     * 
     * @param accountId
     *            accountId
     * @param newPassword
     *            新密码
     */
    void updatePassword(final Long accountId, final String newPassword);

    /**
     * 
     * @param accountId
     *            accountId
     * @param newPassword
     *            新密码
     * @param pwdExpiryDate
     *            密码过期时间
     * @param firstLoginFlag
     *            首次登陆标记
     */
    void updatePassword(final Long accountId, final String newPassword, final Date pwdExpiryDate,
            String firstLoginFlag);

    Account getAccountByAccountId(Long accountId);

    /**
     * 创建账户.
     * 
     * @param account
     *            没有主键的 account
     * @return 含有主键的 account
     */
    Account insert(Account account);

    /**
     * 删除账户.
     * 
     * @param account
     *            目标 account
     * @return Account
     */
    Account delete(Account account);

    /**
     * 更新账户数据.
     * 
     * @param account
     *            需要更新的 account,含有主键
     * @return 更新后的 account
     */
    Account update(Account account);
    
    /**
     * 生成八位随机密码.
     * @return password
     */
    String generateRandomPassword();

}
