/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadSalaryDetailService;

/** 
* @author songyuanhuang
* 月結支付明細上傳实现类
*/
@Service
@Transactional
public class UploadSalaryDetailServiceImpl implements IUploadSalaryDetailService {
    
    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;

    /**
     * 月结支付明細上传实现方法.
     */
    @Override
    public int uploadSalaryDetail(IsgGdsProcedureParam isgGdsProcedureParam) {
        int result = isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);

        return result;
    }

}
