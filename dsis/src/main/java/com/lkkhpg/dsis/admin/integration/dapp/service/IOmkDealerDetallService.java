/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.dapp.service;

import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallRequest;
import com.lkkhpg.dsis.admin.integration.dapp.dto.OmkDealerDetallResponse;
import com.lkkhpg.dsis.integration.dapp.exception.DAppException;

/**
 * 获取会员详细信息接口类.
 * 
 * @author zhenyang.he
 *
 */
public interface IOmkDealerDetallService {

    /**
     * 获取会员详细信息.
     * 
     * @param omkDealerDetallRequest
     *            请求的数据集
     * @return 会员详细信息
     * @throws DAppException
     *             异常接口
     */
    OmkDealerDetallResponse getDealerDetall(OmkDealerDetallRequest omkDealerDetallRequest) throws DAppException;
}
