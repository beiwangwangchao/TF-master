/*
 *
 */
package com.lkkhpg.dsis.platform.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lkkhpg.dsis.platform.constant.BaseConstants;
import com.lkkhpg.dsis.platform.dto.system.Account;
import com.lkkhpg.dsis.platform.exception.AccountException;
import com.lkkhpg.dsis.platform.mapper.system.AccountMapper;
import com.lkkhpg.dsis.platform.security.PasswordManager;
import com.lkkhpg.dsis.platform.service.IAccountService;

/**
 * 账户管理服务.
 * 
 * @author wuyichu
 * @author njq.niu@hand-china.com
 * @author xiawang.liu@hand-china.com
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    private Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordManager passwordManager;
    /**
     * 账号过期.
     */
    private static final String ACCOUNT_STATUS_EXPR = "EXPR";
    
    /**
     * 随机密码长度.
     */
    private static final int PASSWORD_LENGTH = 8;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Account login(Account account) throws AccountException {
        if (account == null || StringUtils.isEmpty(account.getLoginName())
                || StringUtils.isEmpty(account.getPassword())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        final Account dbAccount = self().selectByLoginName(account.getLoginName());

        if(dbAccount == null || dbAccount.getAccountId()==1 ){
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }

        if (dbAccount == null) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        if (ACCOUNT_STATUS_EXPR.equals(dbAccount.getStatus())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        if (dbAccount.getPwdExpiryDate() != null
                && dbAccount.getPwdExpiryDate().getTime() < System.currentTimeMillis()) {
            throw new AccountException(AccountException.MSG_LOGIN_PWD_INVALID, AccountException.MSG_LOGIN_PWD_INVALID,
                    null);
        }
        if (!passwordManager.encode(account.getPassword()).equalsIgnoreCase(dbAccount.getPassword())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }

        return dbAccount;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Account loginSomeOtherUsers(final Account account) throws AccountException{
        if (account == null || StringUtils.isEmpty(account.getLoginName())
                || StringUtils.isEmpty(account.getPassword())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        final Account dbAccount = self().selectByLoginName(account.getLoginName());


        if (dbAccount == null) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        if (ACCOUNT_STATUS_EXPR.equals(dbAccount.getStatus())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        if (dbAccount.getPwdExpiryDate() != null
                && dbAccount.getPwdExpiryDate().getTime() < System.currentTimeMillis()) {
            throw new AccountException(AccountException.MSG_LOGIN_PWD_INVALID, AccountException.MSG_LOGIN_PWD_INVALID,
                    null);
        }
        if (!passwordManager.encode(account.getPassword()).equalsIgnoreCase(dbAccount.getPassword())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }

        return dbAccount;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Account dappLogin(Account account) throws AccountException {
        if (account == null || StringUtils.isEmpty(account.getLoginName())
                || StringUtils.isEmpty(account.getPassword())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        final Account dbAccount = self().selectByLoginName(account.getLoginName());
        if (dbAccount == null) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        if (ACCOUNT_STATUS_EXPR.equals(dbAccount.getStatus())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }
        if (dbAccount.getPwdExpiryDate() != null
                && dbAccount.getPwdExpiryDate().getTime() < System.currentTimeMillis()) {
            throw new AccountException(AccountException.MSG_LOGIN_PWD_INVALID, AccountException.MSG_LOGIN_PWD_INVALID,
                    null);
        }
        if (!account.getPassword().equalsIgnoreCase(dbAccount.getPassword())) {
            throw new AccountException(AccountException.MSG_LOGIN_NAME_PASSWORD,
                    AccountException.MSG_LOGIN_NAME_PASSWORD, null);
        }

        return dbAccount;
    }

    @Override
    public void updatePassword(final Long accountId, final String newPassword) {
        self().updatePassword(accountId, newPassword, null, null);
    }

    @Override
    public void updatePassword(final Long accountId, final String newPassword, final Date pwdExpiryDate, String firstLoginFlag) {

        Account account = new Account();
        account.setAccountId(accountId);
        account.setPassword(passwordManager.encode(newPassword));
        account.setLastUpdateDate(new Date());
        account.setLastUpdatedBy(accountId);
        account.setLastUpdateLogin(accountId);
        // 如果日期不为空，则更新过期日期，否则，过期日期设置系统日期最大值
        if (pwdExpiryDate != null) {
            account.setPwdExpiryDate(pwdExpiryDate);
        } else {
            try {
                account.setPwdExpiryDate(
                        new SimpleDateFormat(BaseConstants.SYSTEM_DATE_FORMAT).parse(BaseConstants.SYSTEM_MAX_DATE));
            } catch (ParseException e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.toString() + BaseConstants.SYSTEM_MAX_DATE);
                }
            }
        }
        
        // 如果首次登陆标记不为Y，则更新该字段
        if (BaseConstants.YES.equals(firstLoginFlag)) {
            account.setFirstLoginFlag(firstLoginFlag);
        }

        accountMapper.updatePassword(account);
    }

    @Override
    public Account insert(Account account) {
        String pass = account.getPassword();
        if (!StringUtils.hasText(pass)) {
            pass = passwordManager.getDefaultPassword();
        }
        account.setPassword(passwordManager.encode(pass));
        accountMapper.insert(account);
        return account;
    }

    @Override
    public Account delete(Account account) {
        accountMapper.deleteByPrimaryKey(account.getAccountId());
        return account;
    }

    @Override
    public Account update(Account account) {
        accountMapper.updateByPrimaryKey(account);
        return account;
    }

    @Override
    public Account selectByLoginName(String loginName) {
        return accountMapper.selectByLoginName(loginName);
    }

    @Override
    public Account getAccountByAccountId(Long accountId) {
        return accountMapper.selectByPrimaryKey(accountId);
    }
    
    
    @Override
    public String generateRandomPassword() {
        String password = "";
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password = password + (int) (Math.random() * 9L);
        }
        return password;
    }

}
