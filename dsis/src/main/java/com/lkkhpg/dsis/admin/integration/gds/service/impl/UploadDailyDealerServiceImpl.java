/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadDailyDealerService;

/**
 * 每日產品基礎資料上傳.
 * 
 * @author fengwanjun
 */
@Service
@Transactional
public class UploadDailyDealerServiceImpl implements IUploadDailyDealerService {
    
    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;
    
    @Override
    public int uploadDailyDealer(IsgGdsProcedureParam isgGdsProcedureParam) {
        return isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);
    }

}