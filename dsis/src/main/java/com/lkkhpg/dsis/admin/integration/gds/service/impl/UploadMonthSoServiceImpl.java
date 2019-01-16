/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadMonthSoService;

/**
 * 月結銷售資料上傳實現類.
 * 
 * @author zhenyang.he
 *
 */
@Service
@Transactional
public class UploadMonthSoServiceImpl implements IUploadMonthSoService {

    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;

    /**
     * 月結銷售資料上傳實現方法.
     */
    @Override
    public int uploadMonthSo(IsgGdsProcedureParam isgGdsProcedureParam) {
        // TODO Auto-generated method stub
        int result = isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);
        return result;
    }

}
