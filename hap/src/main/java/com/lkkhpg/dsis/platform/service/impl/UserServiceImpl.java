/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.dto.system.AccountRelation;
import com.lkkhpg.dsis.platform.dto.system.User;
import com.lkkhpg.dsis.platform.exception.BaseException;
import com.lkkhpg.dsis.platform.mapper.system.AccountRelationMapper;
import com.lkkhpg.dsis.platform.mapper.system.UserMapper;
import com.lkkhpg.dsis.platform.service.IAccountService;
import com.lkkhpg.dsis.platform.service.IUserService;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年1月28日
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountRelationMapper accountRelationMapper;

    @Autowired
    private IAccountService accountService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Long getUserIdByAccountId(Long accountId) {
        return userMapper.selectUserIdByAccountId(accountId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Long getAccountIdByUserId(Long userId) {
        return userMapper.selectAccountIdByUserId(userId);
    }

    @Override
    public User create(IRequest requestContext, User user) {
        if (user.getUserType() == null) {
            user.setUserType(User.TYPE_INNER);
        }
        if (user.getStatus() == null) {
            user.setStatus(User.STATUS_ACTIVE);
        }
        userMapper.insert(user);
        Account dbUser = accountService.insert(user);
        AccountRelation ar = new AccountRelation();
        ar.setAccountId(dbUser.getAccountId());
        ar.setRelPartyId(user.getUserId());
        ar.setCreatedBy(user.getCreatedBy());
        ar.setCreationDate(user.getCreationDate());
        ar.setLastUpdatedBy(user.getLastUpdatedBy());
        ar.setLastUpdateDate(user.getLastUpdateDate());
        accountRelationMapper.insert(ar);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectUsers(User user, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return userMapper.selectUsers(user);
    }

    @Override
    public User delete(User user) throws BaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User update(IRequest request, User user) {
        userMapper.updateByPrimaryKey(user);
        accountService.update(user);
        return user;
    }

    @Override
    public List<User> batchUpdate(IRequest request, List<User> users) {
        for (User user : users) {
            if (user.getAccountId() == null) {
                self().create(request, user);
            } else {
                self().update(request, user);
            }
        }
        return users;
    }

    @Override
    public void batchDelete(List<User> users) {
        for (User user : users) {
            userMapper.deleteByPrimaryKey(user.getUserId());
            accountService.delete(user);
            accountRelationMapper.deleteByPrimaryKey(user.getAccountId());
        }
    }

}
