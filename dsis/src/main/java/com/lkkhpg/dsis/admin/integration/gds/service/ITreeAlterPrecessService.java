/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.gtreeAlters.moveLine.notify.model.NotifyPOSTResponse;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 移线通知.
 * @author frank.li
 */
public interface ITreeAlterPrecessService extends ProxySelf<ITreeAlterPrecessService> {

    /**
     * 移线通知数据获取.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            组织参数
     * @return 返回数据
     */
    NotifyPOSTBody prepareTreeAlterPrecess(String adviseNo, String orgCode);

    /**
     * 处理返回结果.
     * 
     * @param adviseNo
     *            通知号
     * @param orgCode
     *            组织参数
     * @param requestData
     *            请求参数
     * @param response
     *            GDS返回数据
     * @param exception
     *            接口异常
     */
    void processTreeAlterPrecessResponse(String adviseNo, String orgCode, NotifyPOSTBody requestData,
            NotifyPOSTResponse response, Exception exception);
}
