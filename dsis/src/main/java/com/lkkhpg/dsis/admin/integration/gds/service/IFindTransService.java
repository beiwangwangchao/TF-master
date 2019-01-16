/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.results.model.ResultsGETResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 下载转入转出会员处理清单接口类(定时被动).
 * 
 * @author mclin
 */
public interface IFindTransService extends ProxySelf<IFindTransService> {

    /**
     * 下载转出会员清单.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            调用GDS时传递的orgCode
     * @param response
     *            返回数据
     * @param exception
     *            异常
     */
    void findTransOutList(String adviseNo, String orgCode, List<ResultsGETResponse> response, Exception exception);

    /**
     * 下载转入会员清单.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            调用GDS时传递的orgCode
     * @param response
     *            返回数据
     * @param exception
     *            异常
     */
    void findTransInList(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.results.model.ResultsGETResponse> response,
            Exception exception);

}
