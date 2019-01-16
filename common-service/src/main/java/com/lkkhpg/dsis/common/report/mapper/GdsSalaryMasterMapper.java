/*
 *
 */
package com.lkkhpg.dsis.common.report.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;

/**
 * gds报表-bonus_detail_inquiry mapper.
 * 
 * @author guanghui.liu
 *
 */
public interface GdsSalaryMasterMapper {

    List<GdsSalaryMaster> queryBasicSalesBonusRoot(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryBasicSalesBonusLeaf(GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryExtraSalesBonusRoot(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryExtraSalesBonusLeaf(GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryPerformanceBonusRoot(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryPerformanceBonusLeaf(GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryLeadershipBonusRoot(GdsSalaryMaster gdsSalaryMaster);
    
    List<GdsSalaryMaster> queryLeadershipBonusLeaf(GdsSalaryMaster gdsSalaryMaster);

}