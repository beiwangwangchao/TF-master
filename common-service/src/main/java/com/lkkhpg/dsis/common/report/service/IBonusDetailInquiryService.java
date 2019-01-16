/*
 *
 */
package com.lkkhpg.dsis.common.report.service;

import java.util.List;

import com.lkkhpg.dsis.common.report.dto.GdsSalaryMaster;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

/**
 * BonusDetailInquiry - Service.
 * 
 * @author guanghui.liu
 */
public interface IBonusDetailInquiryService extends ProxySelf<IBonusDetailInquiryService> {

    List<GdsSalaryMaster> queryBasicSalesBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryBasicSalesBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryExtraSalesBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryExtraSalesBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryPerformanceBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryPerformanceBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryLeadershipBonusRoot(IRequest request, GdsSalaryMaster gdsSalaryMaster);

    List<GdsSalaryMaster> queryLeadershipBonusLeaf(IRequest request, GdsSalaryMaster gdsSalaryMaster);
}
