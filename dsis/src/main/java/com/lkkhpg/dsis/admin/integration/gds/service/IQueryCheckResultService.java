/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 差异结果查询接口类.
 * 
 * @author linyuheng
 */
public interface IQueryCheckResultService extends ProxySelf<IQueryCheckResultService> {
    /**
     * 更新差异结果查询表.
     * 
     * @param adviseNo
     *            通知号
     * @param response
     *            GDS返回数据
     * @param exception
     *            异常
     */
    void updateIntGdsMemberDiff(String adviseNo, List<CheckResultsGETResponse> response, Exception exception);
}
