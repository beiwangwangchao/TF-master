/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 月结会员资料上传接口类.
 * 
 * @author zhenyang.he
 *
 */
public interface IUploadMonthDealerService extends ProxySelf<IUploadMonthDealerService>{

    /**
     * 月结会员资料上传方法.
     * 
     * @param isgGdsProcedureParam 
     *              GDS接口存储DTO
     * @return 响应的数据
     */
    int uploadMonthDealer(IsgGdsProcedureParam isgGdsProcedureParam);
}
