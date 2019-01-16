/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 下载批删会员资料接口类.
 * 
 * @author linyuheng
 */
public interface IFindExpelDealerService extends ProxySelf<IFindExpelDealerService> {
    /**
     * 批删会员资料新增.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            组织参数
     * @param response
     *            GDS返回数据
     * @param exception
     *            异常
     */
    void insertExpelDealer(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.terminate.results.model.ResultsGETResponse> response,
            Exception exception);

    /**
     * 新开事务，插入接口表.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            市场编码
     * @param resultsGETResponse
     *            响应结果
     * @param exception
     *            异常
     */
    void insertInterface(String adviseNo, String orgCode, ResultsGETResponse resultsGETResponse, Exception exception);

    /**
     * 更新会员状态.
     * 
     * @param resultsGETResponse
     *            停权资料数据
     * @param request
     *            请求上下文
     * @param posOrgCode
     *            市场代码
     */
    void updateMemberStatus(ResultsGETResponse resultsGETResponse, IRequest request, String posOrgCode);

    /**
     * 新开事务，更新接口表.
     * 
     * @param map
     *            入参
     */
    void updateInterface(Map<String, Object> map);
}
