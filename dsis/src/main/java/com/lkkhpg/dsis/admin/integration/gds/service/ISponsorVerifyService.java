/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.member.dto.Sponsor;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 推荐人即时鉴别接口类.
 * 
 * @author linyuheng
 */
public interface ISponsorVerifyService extends ProxySelf<ISponsorVerifyService> {

    /**
     * 推荐人即时鉴别.
     * 
     * @param requestContext
     *            请求上下文
     * @param memberCode
     *            会员卡号
     * @param orgCode
     *            GDS市场代码
     * @return sponsor 推荐人信息
     * @throws IntegrationException
     *             接口异常
     */
     Sponsor sponsorVerify(IRequest requestContext, String memberCode, String orgCode) throws IntegrationException;
}
