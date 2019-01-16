/*
 * Copyright LKK Health Products Group Limited
 */

package com.lkkhpg.dsis.admin.member.service;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.exception.CommMemberException;
import com.lkkhpg.dsis.common.member.dto.MemWithdraw;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * @author yuchuan.zeng@hand-china.com 会员定时执行service
 */
public interface IMemberJobService extends ProxySelf<IMemberJobService> {

    /**
     * 定时执行逻辑方法.
     * 
     * @param memberCode
     *            会员卡号
     * @throws IntegrationException
     *             接口异常
     */
    void autoTerminateMemberJob(String memberCode) throws IntegrationException;

    /**
     * @param memWithdraw
     *            会员冲销节余Dto.
     * @return 操作结果
     */
    boolean addWithdraw(MemWithdraw memWithdraw);

    /**
     * vip转Distributor Service.
     * 
     * @throws CommMemberException
     *             通用会员service异常
     */
    void vipToDistributor() throws CommMemberException;
}
