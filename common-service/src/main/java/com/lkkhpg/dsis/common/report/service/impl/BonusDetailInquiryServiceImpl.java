/*
 *
 */
package com.lkkhpg.dsis.common.report.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;
import com.lkkhpg.dsis.common.report.mapper.GdsSalaryMasterMapper;
import com.lkkhpg.dsis.common.report.service.IBonusDetailInquiryService;
import com.lkkhpg.dsis.platform.core.IRequest;

/**
 * BonusDetailInquiry - Service Impl.
 * 
 * @author guanghui.liu
 *
 */
@Service
public class BonusDetailInquiryServiceImpl implements IBonusDetailInquiryService {

    private static Logger logger = LoggerFactory.getLogger(BonusDetailInquiryServiceImpl.class);

    @Autowired
    private GdsSalaryMasterMapper gdsSalaryMasterMapper;

    @Override
    public List<GdsSalaryMaster> queryBasicSalesBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryBasicSalesBonusRoot(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryBasicSalesBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryBasicSalesBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryExtraSalesBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryExtraSalesBonusRoot(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryExtraSalesBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryExtraSalesBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryPerformanceBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryPerformanceBonusRoot(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryPerformanceBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryPerformanceBonusLeaf(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryLeadershipBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryLeadershipBonusRoot(gdsSalaryMaster);
    }

    @Override
    public List<GdsSalaryMaster> queryLeadershipBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster) {
        return gdsSalaryMasterMapper.queryLeadershipBonusLeaf(gdsSalaryMaster);
    }

}
