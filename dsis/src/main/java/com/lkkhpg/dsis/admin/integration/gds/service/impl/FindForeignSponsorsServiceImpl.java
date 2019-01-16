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
import com.lkkhpg.dsis.admin.integration.gds.dto.IsgOverseasSponsor;
import com.lkkhpg.dsis.admin.integration.gds.exception.IntegrationException;
import com.lkkhpg.dsis.admin.integration.gds.mapper.IsgOverseasSponsorMapper;
import com.lkkhpg.dsis.admin.integration.gds.service.IFindForeignSponsorsService;
import com.lkkhpg.dsis.common.member.dto.MemOverseasSponsor;
import com.lkkhpg.dsis.common.member.mapper.MemOverseasSponsorMapper;
import com.lkkhpg.dsis.integration.gds.resource.dealers.foreignSponsors.model.ForeignSponsorsGETResponse;

/**
 * 小批量下载海外推荐人资料service接口实现类.
 * 
 * @author gulin
 *
 */
@Service
@Transactional
public class FindForeignSponsorsServiceImpl implements IFindForeignSponsorsService {

    private final Logger logger = LoggerFactory.getLogger(FindForeignSponsorsServiceImpl.class);

    @Autowired
    private IsgOverseasSponsorMapper isgOverseasSponsorMapper;
    @Autowired
    private MemOverseasSponsorMapper memOverseasSponsorMapper;

    @Override
    public void insertSponsors(String adviseNo, String orgCode, List<ForeignSponsorsGETResponse> response,
            Exception exception) {
        try {
            insertInterface(adviseNo, orgCode, response, exception);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            throw new RuntimeException(new IntegrationException(e.getMessage(), null)); // 异常代码和格式待定
        }
    }

    @Transactional(noRollbackFor = Exception.class)
    private void insertInterface(String adviseNo, String orgCode, List<ForeignSponsorsGETResponse> response,
            Exception exception) {
        try {
            if (exception == null) {
                IsgOverseasSponsor isgOverseasSponsor = new IsgOverseasSponsor();
                for (ForeignSponsorsGETResponse temp : response) {
                    isgOverseasSponsor.setDealerNo(temp.getDealerNo());
                    isgOverseasSponsor.setDealerName(temp.getDealerName());
                    isgOverseasSponsor.setDealerPostCode(temp.getDealerPostCode());
                    isgOverseasSponsor.setSponsorNo(temp.getSponsorNo());
                    isgOverseasSponsor.setSaleBranchNo(temp.getSaleBranchNo());
                    isgOverseasSponsor.setSaleOrgCode(temp.getSaleOrgCode());
                    isgOverseasSponsor.setTaxCustCode(temp.getTaxCustCode());
                    isgOverseasSponsor.setAdviseNo(adviseNo);
                    isgOverseasSponsor.setProcessStatus(IntegrationConstant.PROCESS_STATUS_S);
                    isgOverseasSponsor.setProcessDate(null);
                    isgOverseasSponsor.setProcessMessage(null);
                    isgOverseasSponsorMapper.insertSelective(isgOverseasSponsor);
                }
                memOverseasSponsorMapper.deleteDataByOrgCode(orgCode);
                if (0 < response.size()) {
                    MemOverseasSponsor memOverseasSponsor = new MemOverseasSponsor();
                    for (ForeignSponsorsGETResponse temp : response) {
                        memOverseasSponsorMapper.deleteByDealerNo(temp.getDealerNo());
                        memOverseasSponsor.setDealerNo(temp.getDealerNo());
                        memOverseasSponsor.setDealerName(temp.getDealerName());
                        memOverseasSponsor.setDealerPostCode(temp.getDealerPostCode());
                        memOverseasSponsor.setSponsorNo(temp.getSponsorNo());
                        memOverseasSponsor.setSaleBranchNo(temp.getSaleBranchNo());
                        memOverseasSponsor.setSaleOrgCode(temp.getSaleOrgCode());
                        memOverseasSponsor.setTaxCustCode(temp.getTaxCustCode());
                        memOverseasSponsor.setAdviseNo(adviseNo);
                        memOverseasSponsorMapper.insertSelective(memOverseasSponsor);
                    }
                }
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                IsgOverseasSponsor isgOverseasSponsor = new IsgOverseasSponsor();
                isgOverseasSponsor.setAdviseNo(adviseNo);
                isgOverseasSponsor.setProcessStatus(IntegrationConstant.PROCESS_STATUS_E);
                isgOverseasSponsor.setProcessDate(null);
                isgOverseasSponsor.setProcessMessage(exception.getMessage());
                isgOverseasSponsorMapper.insertSelective(isgOverseasSponsor);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            throw new RuntimeException(new IntegrationException(e.getMessage(), null));
        }
    }
}
