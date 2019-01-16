/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgGdsProcedureParam;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgGdsProcedureMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IUploadMonthDealerService;

/**
 * 月结会员资料上传实现类.
 * 
 * @author zhenyang.he
 *
 */
@Service
@Transactional
public class UploadMonthDealerServiceImpl implements IUploadMonthDealerService {

    @Autowired
    private IsgGdsProcedureMapper isgGdsProcedureMapper;

    /**
     * 月结会员资料上传实现方法.
     */
    @Override
    public int uploadMonthDealer(IsgGdsProcedureParam isgGdsProcedureParam) {
        // TODO Auto-generated method stub
        int result = isgGdsProcedureMapper.invokeGdsProcedure(isgGdsProcedureParam);

        return result;
    }

}
