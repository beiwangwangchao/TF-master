/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadDailySoService;

/**
 * 日结销售资料上传实现类.
 * 
 * @author zhenyang.he
 *
 */
@Service
@Transactional
public class UploadDailySoServiceImpl implements IUploadDailySoService {
    
    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;

    /**
     * 逻辑执行方法.
     */
    @Override
    public int uploadDailySo(IsgGdsProcedureParam isgGdsProcedureParam) {
        // TODO Auto-generated method stub
        int response = isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);       
        return response;
    }
}
