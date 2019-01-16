/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import java.util.List;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDownlinePerforResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * @author songyuanhuang 获取下线绩效列表接口类.
 */
public interface IOmkDownlinePerforService {
    /**
     * 下线绩效列表.
     * 
     * @param request
     *            请求上下文.
     * @param OmkDownlinePerforRequest
     *            获取下线绩效列表请求DTO
     * @return DApp返回数据
     * @throws DAppException
     *             DApp接口异常
     */

    List<OmkDownlinePerforResponse> getOmkDownlinePerfor(OmkDownlinePerforRequest OmkDownlinePerforRequest)
            throws DAppException;

}
