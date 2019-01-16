/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.dealers.batchDelete.model.BatchDeletePOSTResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 删除会员资料接口类.
 * 
 * @author linyuheng
 */
public interface IDeleteDealerService extends ProxySelf<IDeleteDealerService> {

    /**
     * 获取差异会员卡号列表.
     * 
     * @param adviseNo
     *            通知号
     * @return 差异会员卡号列表
     */
    List<BatchDeletePOSTBody> getDealers(String adviseNo);

    /**
     * 删除GDS会员表更新.
     * 
     * @param adviseNo
     *            通知号
     * @param requestData
     *            请求参数
     * @param response
     *            返回参数
     * @param exception
     *            异常
     */
    void deleteDealers(String adviseNo, List<BatchDeletePOSTBody> requestData, List<BatchDeletePOSTResponse> response,
            Exception exception);

    /**
     * 删除会员资料 - 主动调用.
     * 
     * @param orgCode
     *            组织参数
     * @param adviseNo
     *            通知号
     * @param checkEntityNo
     *            卡号
     * @return 是否删除成功标识
     * @throws IntegrationException
     *             接口异常
     */
    boolean deleteDealers(String orgCode, String adviseNo, String checkEntityNo) throws IntegrationException;

    /**
     * 新开事务，更新接口表.
     * 
     * @param map
     *            入参
     */
    void updateInterfaceStatus(Map<String, Object> map);
}
