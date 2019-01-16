/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IDailyProductPriceUploadService;

/**
 * 每日產品價格資料上傳.
 * 
 * @author fengwanjun
 */
@Service
public class DailyProductPriceUploadServiceImpl implements IDailyProductPriceUploadService {
    
    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;
    
    @Override
    public int dailyProductPriceUpload(IsgGdsProcedureParam isgGdsProcedureParam) {
        return isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);
    }

}