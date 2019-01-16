/*
 *
 */

package com.lkkhpg.dsis.integration.dapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lkkhpg.dsis.integration.dapp.IDAConstants;
import com.lkkhpg.dsis.integration.dapp.Token;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;
import com.lkkhpg.dsis.integration.dapp.service.DAppSecurityManager;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Controller
@RequestMapping("/restful/dapp/auth")
public class DAppAuthController extends DAppBaseController {

    @Autowired(required = false)
    private DAppSecurityManager securityManager;

    @RequestMapping("/getToken")
    public @ResponseBody AuthResult getAccessToken(HttpServletRequest request, Object fake) throws DAppException {
        Token token = securityManager.auth(request);
        AuthResult response = new AuthResult();
        response.setToken(token.getToken());
        response.setExpire(token.getExpireAt());
        return response;
    }

    @RequestMapping("/refreshToken")
    public @ResponseBody AuthResult refreshAccessToken(HttpServletRequest request, Object fake) throws DAppException {
        Token token = securityManager.refreshToken(request);

        AuthResult response = new AuthResult();
        response.setToken(token.getToken());
        response.setExpire(token.getExpireAt());
        return response;
    }

    @ExceptionHandler(value = { Exception.class })
    public @ResponseBody Object exceptionHandler(Exception exception) {
        Throwable thr = getRootCause(exception);
        if (getLogger().isErrorEnabled()) {
            getLogger().error(thr.getMessage(), thr);
        }
        AuthResult response = new AuthResult();
        if (thr instanceof DAppException) {
            DAppException dae = (DAppException) thr;
            response.setCode(dae.getCode());
            response.setMessage(dae.getMessage());
        } else {
            response.setCode(IDAConstants.ERROR_10000);
            response.setMessage(thr.getMessage());
        }
        return response;
    }

    public static class AuthResult {
        private int code = 0;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String token;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Long expire;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getExpire() {
            return expire;
        }

        public void setExpire(Long expire) {
            this.expire = expire;
        }
    }
}
