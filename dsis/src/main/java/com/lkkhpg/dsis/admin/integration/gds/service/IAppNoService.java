/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 申请号接口类.
 * 
 * @author frank.li
 */
public interface IAppNoService extends ProxySelf<IAppNoService> {
    /**
     * 生成申请号.
     * 
     * @param requestContext
     *            请求上下文
     * @return appNo 申请号
     */
    String getAppNo(IRequest requestContext);
}
