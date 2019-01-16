/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgPeriodClose;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgPeriodCloseMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IClosePeriodService;
import com.lkkhpg.dsis.admin.integration.gds.service.IGdsUtilService;
import com.lkkhpg.dsis.common.config.dto.SpmPeriod;
import com.lkkhpg.dsis.common.config.mapper.SpmPeriodMapper;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.periods.close.model.ClosePOSTResponse;
import com.lkkhpg.dsis.integration.gds.service.IGdsService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 关闭奖金期间接口实现.
 * 
 * @author guanghui.liu
 */
@Service
@Transactional
public class ClosePeriodServiceImpl implements IClosePeriodService {

    private final Logger log = LoggerFactory.getLogger(ClosePeriodServiceImpl.class);

    @Autowired
    private IGdsService gdsService;

    @Autowired
    private IsgPeriodCloseMapper isgPeriodCloseMapper;

    @Autowired
    private SpmPeriodMapper spmPeriodMapper;
    
    @Autowired
    private IGdsUtilService gdsUtilService;

    @Override
    public ClosePOSTResponse closePeriod(IRequest request, String orgCode, String period) throws IntegrationException {
        ClosePOSTResponse response = null;
        IsgPeriodClose record = new IsgPeriodClose();
        String gdsOrgCode = gdsUtilService.getGdsOrgCode(request, orgCode);
        try {
            // 准备接口表插入数据
            record.setProcessStatus(IntegrationConstant.PROCESS_STATUS_S);
            record.setProcessMessage(IntegrationConstant.PROCESS_MESSAGE_SUCCESS);
            record.setCreatedBy(request.getAccountId());
            record.setLastUpdatedBy(request.getAccountId());
            // 调用接口易发生异常
            response = gdsService.closePeriod(gdsOrgCode, period);
            // 调用接口成功,取出月份返回值
            record.setPeriod(response.getPeriod());
            // 调用接口成功,说明成功关闭期间,同步状态到pos系统的spm_period表
            SpmPeriod spmPeriod = new SpmPeriod();
            spmPeriod.setClosingStatus(IntegrationConstant.FLAG_Y);
            spmPeriod.setPeriodType(IntegrationConstant.CLOSE_PERIOD_TYPE_BM);
            spmPeriod.setPeriodName(response.getPeriod());
            spmPeriod.setLastUpdatedBy(request.getAccountId());
            // updateSpmPeriod(spmPeriod);
        } catch (Exception e) {
            // 接口调用失败
            if (log.isErrorEnabled()) {
                log.error(IntegrationException.MSG_ERROR_ISG_CLOSE_PERIOD, e);
            }
            record.setProcessStatus(IntegrationConstant.PROCESS_STATUS_E);
            record.setProcessMessage(e.toString() + ":" + e.getMessage());
            record.setPeriod(period);
            
            if (e instanceof ESBException) {
                throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE,
                        new Object[] { e.toString(), ":", e.getMessage() });
            }
            throw new IntegrationException(IntegrationException.MSG_ERROR_ISG_CLOSE_PERIOD,
                    new Object[] { e.toString(), ":", e.getMessage() });
        } finally {
            // 插入接口表
            self().insertInterface(record);
        }
        return response;
    }

    @Override
    @Transactional(noRollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int insertInterface(IsgPeriodClose record) {
        return isgPeriodCloseMapper.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSpmPeriod(SpmPeriod record) {
        return spmPeriodMapper.updateByTypeAndName(record);
    }

}