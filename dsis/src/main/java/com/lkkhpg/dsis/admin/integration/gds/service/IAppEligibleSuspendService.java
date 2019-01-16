/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员申请资格中止接口类.
 * 
 * @author linyuheng
 */
public interface IAppEligibleSuspendService extends ProxySelf<IAppEligibleSuspendService> {
    /**
     * 会员申请资格中止.
     * 
     * @param requestContext
     *            请求上下文
     * @param dealerNo
     *            会员卡号
     * @param appNo
     *            申请号
     * @param appDocNo
     *            文件号
     * @param appPeriod
     *            申请月份
     * @param appMemo
     *            申请备注
     * @param orgCode
     *            组织参数
     * @throws IntegrationException
     *             接口异常
     */
    void appEligibleSuspend(IRequest requestContext, String dealerNo, String appNo, String appDocNo, String appPeriod,
            String appMemo, String orgCode) throws IntegrationException;
    
    /**
     * 新开事务，执行接口表的更新.
     * 
     * @param map
     *            入参
     */
    void updateInterfaceStatus(Map<String, Object> map);
}
