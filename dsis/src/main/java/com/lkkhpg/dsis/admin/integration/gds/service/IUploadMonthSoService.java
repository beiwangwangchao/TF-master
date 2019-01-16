/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 月結銷售資料上傳接口類.
 * 
 * @author zhenyang.he
 *
 */
public interface IUploadMonthSoService extends ProxySelf<IUploadMonthSoService>{

    /**
     * 月結銷售資料上傳的邏輯方法.
     * 
     * @param isgGdsProcedureParam
     *                  數據集合
     * @return 返回響應數據
     */
    int uploadMonthSo(IsgGdsProcedureParam isgGdsProcedureParam);
}
