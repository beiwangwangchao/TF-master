/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 会员移线申请接口类.
 * 
 * @author linyuheng
 */
public interface IApplyMoveLineService extends ProxySelf<IApplyMoveLineService> {
    /**
     * 会员移线申请.
     * 
     * @param requestContext
     *            请求上下文
     * @param dealerNo
     *            会员卡号
     * @param appNo
     *            申请号
     * @param sponsorNo
     *            推荐人卡号
     * @param appDocNo
     *            文件号
     * @param appMemo
     *            申请备注
     * @param orgCode
     *            组织参数
     * @throws IntegrationException
     *             接口异常
     */
    void applyMoveLine(IRequest requestContext, String dealerNo, String appNo, String sponsorNo, String appDocNo,
            String appMemo, String orgCode) throws IntegrationException;

    /**
     * 新开事务，执行接口表的更新.
     * 
     * @param map
     *            入参
     */
    void updateInterfaceStatus(Map<String, Object> map);
}
