/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 日结销售资料上传接口类.
 * 
 * @author zhenyang.he
 *
 */
public interface IUploadDailySoService extends ProxySelf<IUploadDailySoService>{

    /**
     * 定时执行逻辑方法.
     * 
     * @param isgGdsProcedureParam 
     *             
     * @return 返回响应数据 
     * 
     */
    
    int uploadDailySo(IsgGdsProcedureParam isgGdsProcedureParam);
}
