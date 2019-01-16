/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员移线、停权状态申请查询接口类.
 * 
 * @author linyuheng
 */
public interface IApplyStatusQueryService extends ProxySelf<IApplyStatusQueryService> {
    /**
     * 会员移线、停权状态申请查询.
     * 
     * @param requestContext
     *            请求上下文
     * @param orgCode
     *            组织参数
     * @param appNo
     *            申请号
     * @param type
     *            请求类型
     * @throws IntegrationException
     *             接口异常
     */
    void applyStatusQuery(IRequest requestContext, String orgCode, String appNo, String type)
            throws IntegrationException;

    /**
     * 更新申请表的审核状态.
     * 
     * @param type
     *            申请类型
     * @param map
     *            map
     */
    void updateApproveStatusByAppNo(String type, Map<String, Object> map);
}
