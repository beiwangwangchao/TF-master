/**
 *
 */
package com.lkkhpg.dsis.mws.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkkhpg.dsis.common.constant.MemberConstants;
import com.lkkhpg.dsis.common.constant.SystemProfileConstants;
import com.lkkhpg.dsis.common.member.dto.Member;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.IRequestListener;

/**
 * @author runbai.chen
 */
public class MwsRequestListener implements IRequestListener {

    private Logger logger = LoggerFactory.getLogger(MwsRequestListener.class);

    @Override
    public IRequest newInstance() {
        if (logger.isDebugEnabled()) {
            logger.debug("the current request context is MwsServiceRequest.");
        }
        return new MwsServiceRequest();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.lkkhpg.dsis.platform.core.IRequestListener#afterInitialize(javax.servlet.http.HttpServletRequest,
     *      com.lkkhpg.dsis.platform.core.IRequest)
     */
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
        request.setAttribute(Member.FIELD_MEMBER_ID, session.getAttribute(Member.FIELD_MEMBER_ID));
        request.setAttribute(MemberConstants.FIELD_MEMBER_ROLE, session.getAttribute(MemberConstants.FIELD_MEMBER_ROLE));
        request.setAttribute(SystemProfileConstants.SALES_ORG_ID,
                session.getAttribute(SystemProfileConstants.SALES_ORG_ID));
        request.setAttribute(SystemProfileConstants.MARKET_ID, session.getAttribute(SystemProfileConstants.MARKET_ID));
        request.setAttribute(Member.FIELD_MEMBER_CODE, session.getAttribute(Member.FIELD_MEMBER_CODE));
        request.setAttribute(SystemProfileConstants.MARKET_CODE, session.getAttribute(SystemProfileConstants.MARKET_CODE));

    }

}
