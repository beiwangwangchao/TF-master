/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgPeriodClose;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTResponse;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 关闭奖金期间接口.
 * 
 * @author guanghui.liu
 */
public interface IClosePeriodService extends ProxySelf<IClosePeriodService> {

    /**
     * 关闭奖金期间.
     * 
     * @param request
     *            请求上下文
     * @param orgCode
     *            調用GDS传递的OrgCode
     * @param period
     *            月份
     * @return 接口返回数据
     * @throws IntegrationException
     */
    ClosePOSTResponse closePeriod(IRequest request, String orgCode, String period) throws IntegrationException;

    /**
     * 插入接口表. 必然不回滚
     * 
     * @param record
     *            包含需要插入的接口表信息
     * @return 插入的条数
     */
    int insertInterface(IsgPeriodClose record);

    /**
     * 更新业务表. 遇到异常回滚
     * 
     * @param record
     *            包含需要更新的业务表信息
     * @return 更新的条数
     */
    int updateSpmPeriod(SpmPeriod record);
}
