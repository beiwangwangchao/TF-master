/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 下载移线会员资料接口类.
 * 
 * @author linyuheng
 */
public interface IFindMoveSponsorService extends ProxySelf<IFindMoveSponsorService> {
    /**
     * 移线会员资料新增.
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
    void findMoveSponsor(String adviseNo, String orgCode, List<ResultsGETResponse> response, Exception exception);

    /**
     * 新开事务，更新接口表.
     * 
     * @param map
     *            入参
     */
    void updateInterface(Map<String, Object> map);

    /**
     * 更新会员上线.
     * 
     * @param response
     *            接口响应(头部数据)
     * @param request
     *            请求上下文
     * @param posOrgCode
     *            市场代码
     */
    void updateMemberSponsor(ResultsGETResponse response, IRequest request, String posOrgCode);
}
