/*
 *
 */
package com.lkkhpg.dsis.admin.integration.gds.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lkkhpg.dsis.admin.integration.gds.constant.IntegrationConstant;
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgDiffCheck;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgDiffCheckMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IDiffCheckService;
import com.lkkhpg.dsis.integration.gds.exception.ESBException;
import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * 差异数据service接口实现类.
 * 
 * @author Clerifen Li
 */
@Service
@Transactional
public class DiffCheckServiceImpl implements IDiffCheckService {

    private final Logger logger = LoggerFactory.getLogger(DiffCheckServiceImpl.class);

    @Autowired
    private IsgDiffCheckMapper isgDiffCheckMapper;

    @Override
    public void insertDiffCheckData(String adviseNo, String orgCode, List<CheckResultsGETResponse> response,
            Exception exception) {
        try {
            insertInterface(adviseNo, orgCode, response, exception);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Transactional(noRollbackFor = Exception.class)
    private void insertInterface(String adviseNo, String orgCode, List<CheckResultsGETResponse> response,
            Exception exception) {
        try {
            Date date = new Date();
            if (exception == null) {
                IsgDiffCheck data = new IsgDiffCheck();
                for (CheckResultsGETResponse current : response) {
                    data.setCheckDate(current.getCheckDate());
                    data.setCheckOrgCode(current.getCheckOrgCode());
                    data.setCheckEntityNo(current.getCheckEntityNo());
                    data.setCheckEntityRefPeriod(current.getCheckEntityRefPeriod());
                    data.setCheckEntityType(current.getCheckEntityType());
                    data.setCheckPhase(current.getCheckPhase());
                    data.setCheckResultCode(current.getCheckResultCode());
                    data.setCheckResultMemo01(current.getCheckResultMemo01());
                    data.setCheckResultMemo02(current.getCheckResultMemo02());
                    data.setOrgReadFlag(current.getOrgReadFlag());
                    data.setOrgReadTime(current.getOrgReadTime());
                    data.setOrgReadBy(current.getOrgReadBy());
                    data.setOrgAmendBy(current.getOrgAmendBy());
                    data.setOrgAmendFlag(current.getOrgAmendFlag());
                    data.setOrgAmendMemo(current.getOrgAmendMemo());
                    data.setOrgAmendTime(current.getOrgAmendTime());
                    if (current.getEnabled() != null) {
                        data.setEnabled(current.getEnabled() ? IntegrationConstant.FLAG_Y : IntegrationConstant.FLAG_N);
                    }
                    data.setComments(current.getComments());
                    if (current.getOrgAutoSyn() != null) {
                        data.setOrgAutoSyn(
                                current.getOrgAutoSyn() ? IntegrationConstant.FLAG_Y : IntegrationConstant.FLAG_N);
                    }
                    data.setAdviseNo(adviseNo);
                    data.setProcessStatus(IntegrationConstant.PROCESS_STATUS_S);
                    data.setProcessDate(date);
                    data.setProcessMessage(null);
                    isgDiffCheckMapper.insertSelective(data);
                }
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                IsgDiffCheck data = new IsgDiffCheck();
                data.setAdviseNo(adviseNo);
                data.setProcessStatus(IntegrationConstant.PROCESS_STATUS_E);
                data.setProcessDate(date);
                data.setProcessMessage(exception.getMessage());
                isgDiffCheckMapper.insertSelective(data);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(IntegrationException.MSG_ERROR_ISG_QUERY_CHECK_RESULT, e);
            }
            if (e instanceof ESBException) {
                throw new RuntimeException(
                        new IntegrationException(IntegrationException.MSG_ERROR_ISG_INVOKE_GDS_INTERFACE, new Object[] {
                                ((ESBException) e).getErrorCode() + " : " + ((ESBException) e).getErrorResponse() }));
            }
            throw new RuntimeException(new IntegrationException(IntegrationException.MSG_ERROR_ISG_QUERY_CHECK_RESULT,
                    new Object[] { e.toString(), ":", e.getMessage() }));
        }
    }

    @Override
    public List<IsgDiffCheck> queryDiffChecks(IRequest iRequest, int page, int pagesize, IsgDiffCheck isgDiffCheck) {
        PageHelper.startPage(page, pagesize);
        List<IsgDiffCheck> diffChecks = isgDiffCheckMapper.queryDiffChecks(isgDiffCheck);
        return diffChecks;
    }

}
