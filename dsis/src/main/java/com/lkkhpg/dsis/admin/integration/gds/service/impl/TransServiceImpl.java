/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgMarketChange;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgMarketChangeMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.ITransService;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTBody;
import com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferOut.notify.model.NotifyPOSTResponse;

/**
 * 上传转入转出会员处理清单接口实现类.
 * 
 * @author mclin
 */
@Service
@Transactional
public class TransServiceImpl implements ITransService {

    private Logger logger = LoggerFactory.getLogger(TransServiceImpl.class);

    @Autowired
    private IsgMarketChangeMapper isgMarketChangeMapper;

    /*
     * ******************** ** 转出 ** ********************
     */
    @Override
    public void processTransOut(String adviseNo, String orgCode, List<NotifyPOSTBody> requestData,
            List<NotifyPOSTResponse> response, Exception exception) {
        try {
            if (exception == null) {
                for (NotifyPOSTResponse re : response) {
                    IsgMarketChange imc = new IsgMarketChange();
                    imc.setAdviseNo(adviseNo);
                    imc.setGdsId(re.getId());
                    imc.setOperationType(IntegrationConstant.OUT_MARKET_CHANGE);
                    // 更新上传状态
                    updateInterfaceUploadFlag(imc);
                }
            } else {
                // 写异常日志
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                IsgMarketChange imc = new IsgMarketChange();
                // 通知号和异常信息插入一条数据到接口表
                imc.setAdviseNo(adviseNo);
                imc.setOperationType(IntegrationConstant.OUT_MARKET_CHANGE);
                imc.setProcessStatus(IntegrationConstant.ERROR_STATUS);
                imc.setProcessMessage(exception.getMessage());
                imc.setUploadFlag(IntegrationConstant.UPLOAD_STATUS);
                isgMarketChangeMapper.insertSelective(imc);
            }
        } catch (Exception e) {
            // 整个过程中出现异常则写错误日志并往外抛异常
            if (logger.isErrorEnabled()) {
                logger.error("processTransOut: ", e);
            }
            // 异常代码和格式待定
            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }

    // 更新接口表上传标记字段（必然提交不回滚）--上传成功后调用
    // @Transactional(noRollbackFor = Exception.class, propagation =
    // Propagation.REQUIRES_NEW)
    void updateInterfaceUploadFlag(IsgMarketChange imc) {
        isgMarketChangeMapper.updateUploadFlag(imc);
    };

    /*
     * ******************** ** 转入 ** ********************
     */
    @Override
    public void processTransIn(String adviseNo, String orgCode,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTBody> requestData,
            List<com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse> response,
            Exception exception) {
        try {
            if (exception == null) {
                for (com.lkkhpg.dsis.integration.gds.resource.marketChanges.transferIn.notify.model.NotifyPOSTResponse re : response) {
                    IsgMarketChange imc = new IsgMarketChange();
                    imc.setAdviseNo(adviseNo);
                    imc.setGdsId(re.getId());
                    imc.setOperationType(IntegrationConstant.IN_MARKET_CHANGE);
                    // 更新上传状态
                    updateInterfaceUploadFlag(imc);
                }
            } else {
                // 写异常日志
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                IsgMarketChange imc = new IsgMarketChange();
                // 通知号和异常信息插入一条数据到接口表
                imc.setAdviseNo(adviseNo);
                imc.setOperationType(IntegrationConstant.IN_MARKET_CHANGE);
                imc.setProcessStatus(IntegrationConstant.ERROR_STATUS);
                imc.setProcessMessage(exception.getMessage());
                imc.setUploadFlag(IntegrationConstant.UPLOAD_STATUS);
                isgMarketChangeMapper.insertSelective(imc);
            }
        } catch (Exception e) {
            // 整个过程中出现异常则写错误日志并往外抛异常
            if (logger.isErrorEnabled()) {
                logger.error("processTransIn: ", e);
            }
            // 异常代码和格式待定
            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }
}
