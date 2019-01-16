/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import java.util.List;

import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 上传转入会员处理清单接口类(定时被动).
 * 
 * @author mclin
 */
public interface ITransService extends ProxySelf<ITransService> {

    /**
     * 更新转出会员列表.
     * 
     * @param adviseNo
     *            通知号
     * @return List 返回更新成功的会员列表
     */
    /* List<NotifyPOSTBody> updateTransOutList(String adviseNo); */

    /**
     * 处理转出GDS返回结果.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            调用GDS传递的OrgCode
     * @param requestData
     *            发出的数据
     * @param response
     *            接受的数据
     * @param exception
     *            异常
     */
    void processTransOut(String adviseNo, String orgCode, List<NotifyPOSTBody> requestData,
            List<NotifyPOSTResponse> response, Exception exception);

    /**
     * 更新转入会员列表.
     * 
     * @param adviseNo
     *            通知号
     * @return List 返回更新成功的会员列表
     */
    /*
     * List<com.lkkhpg.dsis.integration.gds.resource
     * .marketChanges.transferIn.notify.model.NotifyPOSTBody>
     * updateTransInList(String adviseNo);
     */

    /**
     * 处理转入GDS返回结果.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            调用GDS传递的OrgCode
     * @param requestData
     *            发出的数据
     * @param response
     *            接受的数据
     * @param exception
     *            异常
     */
    void processTransIn(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse> response,
            Exception exception);
}
