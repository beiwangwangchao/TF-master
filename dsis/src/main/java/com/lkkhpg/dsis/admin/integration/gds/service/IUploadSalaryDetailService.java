/*
 *
 */

package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;

/** 
* @author songyuanhuang
* 月結支付明細上傳接口类
*/
public interface IUploadSalaryDetailService {
    /**
     * 月結支付明細上傳方法.
     * 
     * @param isgGdsProcedureParam 
     *              GDS接口存储DTO
     * @return 响应的数据
     */
    int uploadSalaryDetail(IsgGdsProcedureParam isgGdsProcedureParam);

}
