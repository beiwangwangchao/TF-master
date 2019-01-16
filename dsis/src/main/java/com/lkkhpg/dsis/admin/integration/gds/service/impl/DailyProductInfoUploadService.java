/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IDailyProductInfoUploadService;

/**
 * 每日產品基礎資料上傳.
 * 
 * @author fengwanjun
 */
@Service
public class DailyProductInfoUploadService implements IDailyProductInfoUploadService {

    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;
    
    @Override
    public int dailyProductInfoUpload(IsgGdsProcedureParam isgGdsProcedureParam) {
        return isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);
    }

}