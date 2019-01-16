/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;

/**
 * 每日產品基礎資料上傳.
 * 
 * @author fengwanjun
 */
public interface IDailyProductInfoUploadService {

	int dailyProductInfoUpload(IsgGdsProcedureParam isgGdsProcedureParam);
}
