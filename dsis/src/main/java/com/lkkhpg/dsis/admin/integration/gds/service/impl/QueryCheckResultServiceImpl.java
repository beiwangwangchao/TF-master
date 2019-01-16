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

import com.lkkhpg.dsis.admin.integration.gds.dto.IsgDiffCheck;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgDiffCheckMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IQueryCheckResultService;
import com.lkkhpg.dsis.integration.gds.resource.checkResults.model.CheckResultsGETResponse;

/**
 * 差异结果查询实现类.
 * 
 * @author linyuheng
 */
@Service
@Transactional
public class QueryCheckResultServiceImpl implements IQueryCheckResultService {

    private Logger logger = LoggerFactory.getLogger(QueryCheckResultServiceImpl.class);

    @Autowired
    private IsgDiffCheckMapper isgDiffCheckMapper;

    @Override
    public void updateIntGdsMemberDiff(String adviseNo, List<CheckResultsGETResponse> response, Exception exception) {
        if (exception == null) {
            for (CheckResultsGETResponse checkResultsGETResponse : response) {
                IsgDiffCheck isgDiffCheck = new IsgDiffCheck();
                // intGdsMemberDiff.setCheckDate(checkResultsGETResponse.getCheckDate());
                isgDiffCheck.setCheckOrgCode(checkResultsGETResponse.getCheckOrgCode());
                isgDiffCheck.setCheckEntityType(checkResultsGETResponse.getCheckEntityType());
                isgDiffCheck.setCheckEntityNo(checkResultsGETResponse.getCheckEntityNo());
                isgDiffCheck.setCheckEntityRefPeriod(checkResultsGETResponse.getCheckEntityRefPeriod());
                isgDiffCheck.setCheckPhase(checkResultsGETResponse.getCheckPhase());
                isgDiffCheck.setCheckResultCode(checkResultsGETResponse.getCheckResultCode());
                isgDiffCheck.setCheckResultMemo01(checkResultsGETResponse.getCheckResultMemo01());
                isgDiffCheck.setCheckResultMemo02(checkResultsGETResponse.getCheckResultMemo02());
                isgDiffCheck.setOrgReadFlag(checkResultsGETResponse.getOrgReadFlag());
                // intGdsMemberDiff.setOrgReadTime(checkResultsGETResponse.getOrgReadTime());
                isgDiffCheck.setOrgReadBy(checkResultsGETResponse.getOrgReadBy());
                isgDiffCheck.setOrgAmendFlag(checkResultsGETResponse.getOrgAmendFlag());
                isgDiffCheck.setOrgAmendMemo(checkResultsGETResponse.getOrgAmendMemo());
                // intGdsMemberDiff.setOrgAmendTime(checkResultsGETResponse.getOrgAmendTime());
                isgDiffCheck.setOrgAmendBy(checkResultsGETResponse.getOrgAmendBy());
                // intGdsMemberDiff.setEnabled(checkResultsGETResponse.getEnabled());
                isgDiffCheck.setComments(checkResultsGETResponse.getComments());
                // intGdsMemberDiff.setOrgAutoSyn(checkResultsGETResponse.getOrgAutoSyn());

                isgDiffCheckMapper.insertSelective(isgDiffCheck);
            }
        } else {
            if (logger.isErrorEnabled()) {
                logger.error(exception.getMessage(), exception);
            }
            throw new RuntimeException(new IntegrationException(exception.getMessage(), null));
        }
    }
}
