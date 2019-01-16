/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;

/**
 * 每日產品價格資料上傳.
 * 
 * @author fengwanjun
 */
public interface IDailyProductPriceUploadService {

	int dailyProductPriceUpload(IsgGdsProcedureParam isgGdsProcedureParam);
	
}