/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service;

import java.util.List;

import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.BaseException;

/**
 * HAP中此接口实现默认都是按照USER类型处理. 其他类型可自行实现此接口逻辑.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月28日
 */
public interface IUserService extends ProxySelf<IUserService> {

    /**
     * 根据账号获取用户Id. 默认取SYS_ACC_REL中ACCOUNT_TYPE为USER类型的数据.
     * 
     * @param accountId
     *            accountId
     * @return UserID
     */
    Long getUserIdByAccountId(Long accountId);

    /**
     * 根据账号获取用户Id. 默认取SYS_ACC_REL中ACCOUNT_TYPE为USER类型的数据.
     * 
     * @param userId
     *            UserID
     * @return AccountID
     */
    Long getAccountIdByUserId(Long userId);

    /**
     * 创建用户.
     * 
     * (1)创建sys_user (2)创建sys_user (3)创建sys_acc_rel(关系类型为USER)
     * 
     * @param requestContext
     *            session信息
     * @param user
     *            User
     * @return User
     */
    User create(IRequest requestContext, @StdWho User user);

    /**
     * 查询用户数据(包含账户信息).
     * 
     * @param user
     *            User
     * @param page
     *            页码
     * @param pagesize
     *            数量
     * @return User列表
     */
    List<User> selectUsers(User user, int page, int pagesize);

    /**
     * 删除用户.
     * 
     * @param user
     *            User
     * @return User
     * @throws BaseException
     *             BaseException
     */
    User delete(User user) throws BaseException;

    /**
     * 更新用户数据.
     * 
     * @param request
     *            session信息
     * @param user
     *            User
     * @return User
     */
    User update(IRequest request, User user);

    /**
     * 批量更新.
     * 
     * @param request
     *            session信息
     * @param users
     *            用户列表
     * @return 用户列表
     */
    List<User> batchUpdate(IRequest request, @StdWho List<User> users);

    /**
     * 批量删除.
     * 
     * @param users
     *            用户列表
     */
    void batchDelete(List<User> users);

}
