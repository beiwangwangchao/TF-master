/*
 *
 */
package com.lkkhpg.dsis.common.system.report.mapper;

import java.util.List;

import com.lkkhpg.dsis.common.system.report.dto.GdsMeDealerTree;

/**
 * omk.
 * @author HuangJiaJing
 *
 */
public interface GdsMeDealerTreeMapper {
    int insert(GdsMeDealerTree record);

    int insertSelective(GdsMeDealerTree record);
    
    List<GdsMeDealerTree> queryGdsMealerTree(GdsMeDealerTree tree);
    
    String querySalesOrgCode();
    
    String queryByDealerNo(String dealerNo);
}