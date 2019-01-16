/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgDiffCheck;
import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 差异查询接口.
 * 
 * @author Clerifen Li
 */
public interface IDiffCheckService extends ProxySelf<IDiffCheckService> {

    /**
     * 接受海外推荐人资料插入海外推荐人临时表.
     * 
     * @param adviseNo
     *            通知号.
     * @param orgCode
     *            所属机构code.
     * @param response
     *            接收到的差异数据
     * @param exception
     *            异常
     */
    void insertDiffCheckData(String adviseNo, String orgCode, List<CheckResultsGETResponse> response,
            Exception exception);

    /**
     * 获取差异查询列表.
     * 
     * @param iRequest
     *            请求上下文.
     * @param page
     *            页
     * @param pagesize
     *            页数
     * @param isgDiffCheck
     *            差异查询DTO
     * @return 差异查询列表
     */
    List<IsgDiffCheck> queryDiffChecks(IRequest iRequest, int page, int pagesize, IsgDiffCheck isgDiffCheck);
}
