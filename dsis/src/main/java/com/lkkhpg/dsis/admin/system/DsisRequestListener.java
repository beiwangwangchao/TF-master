/*
 *
 */
package com.lkkhpg.dsis.admin.system;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.IRequestListener;
import com.lkkhpg.dsis.platform.dto.system.User;

/**
 * Request生成监听器.
 * 
 * @author chenjingxiong
 */
public class DsisRequestListener implements IRequestListener {

    private Logger logger = LoggerFactory.getLogger(DsisRequestListener.class);

    @Override
    public IRequest newInstance() {
        if (logger.isDebugEnabled()) {
            logger.debug("the current request context is DsisServiceRequest.");
        }
        return new DsisServiceRequest();
    }

    @Override
    public void afterInitialize(HttpServletRequest httpServletRequest, IRequest request) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("processing org info into request context.");
        }
        // 设置组织信息
        request.setAttribute(SystemProfileConstants.DEFAULT_INV_ORG_ID,
                session.getAttribute(SystemProfileConstants.DEFAULT_INV_ORG_ID));
        request.setAttribute(SystemProfileConstants.DEFAULT_SALES_ORG_ID,
                session.getAttribute(SystemProfileConstants.DEFAULT_SALES_ORG_ID));
        request.setAttribute(SystemProfileConstants.INV_ORG_IDS,
                session.getAttribute(SystemProfileConstants.INV_ORG_IDS));
        request.setAttribute(SystemProfileConstants.SALES_ORG_IDS,
                session.getAttribute(SystemProfileConstants.SALES_ORG_IDS));
        request.setAttribute(SystemProfileConstants.INV_ORG_ID,
                session.getAttribute(SystemProfileConstants.INV_ORG_ID));
        request.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                session.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        request.setAttribute(User.FILED_USER_ID, session.getAttribute(User.FILED_USER_ID));
        request.setAttribute(SystemProfileConstants.MARKET_ID,
                session.getAttribute(SystemProfileConstants.MARKET_ID));
    }
}
