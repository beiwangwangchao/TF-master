/*
 * Copyright LKK Health Products Group Limited
 */
package com.lkkhpg.dsis.admin.integration.gds.service;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * 日結會員資料上傳.
 * 
 * @author fengwanjun
 */
public interface IUploadDailyDealerService extends ProxySelf<IUploadDailyDealerService> {

    int uploadDailyDealer(IsgGdsProcedureParam isgGdsProcedureParam);
    
}