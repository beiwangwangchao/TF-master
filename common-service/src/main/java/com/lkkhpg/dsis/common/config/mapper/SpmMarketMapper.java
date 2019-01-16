/*
 *
 */
package com.lkkhpg.dsis.common.config.mapper;

import java.util.List;
import java.util.Map;

import com.lkkhpg.dsis.common.member.dto.Member;
import org.apache.ibatis.annotations.Param;

import com.lkkhpg.dsis.common.config.dto.SpmMarket;

/**
 * 市场MAPPER.
 * 
 * @author frank.li
 */
public interface SpmMarketMapper {
    int deleteByPrimaryKey(SpmMarket spmMarket);

    int insert(SpmMarket spmMarket);

    int insertSelective(SpmMarket spmMarket);

    SpmMarket selectByPrimaryKey(Long marketId);

    int updateByPrimaryKeySelective(SpmMarket spmMarket);

    int updateByPrimaryKey(SpmMarket record);
   /*查询所有的市场，不管那家公司*/
    List<SpmMarket> queryByMarket(SpmMarket marketId);
    /*查询该市场的所有公司下得市场*/
    List<SpmMarket> queryByMarket2(SpmMarket marketId);

    List<SpmMarket>selectCompanyId(Long marketId);

    /*仅查询改市场所属公司及子公司的市场*/
    List<SpmMarket>  queryByMarketId(SpmMarket marketId);
    int queryCount(SpmMarket spmMarket);

    SpmMarket selectBySalesOrgId(Long salesOrgId);

    SpmMarket selectByName(String name);

    Map<String, Object> selectCompAndMarketBySalesOrgId(Long salesOrgId);

    Long queryMarketIdByCodeAndCompany(Map<String, Object> map);

    List<SpmMarket> queryMarket(Long marketId);

    SpmMarket selectMarketByCode(String code);

    Integer queryMarketsQuanties();

    /**
     * 查询IGI映射的市场.
     * 
     * @param markeId
     *            IGI市场ID
     * @return 映射到的市场
     */
    Long selectIGIMappingMarket(@Param("markeId") Long markeId);
    
    SpmMarket selectMarketWithoutEnabled(String code);

    List<SpmMarket> queryMarketsByRole(@Param("roleId") Long roleId,@Param("userId") Long userId);


    List<SpmMarket> getEnableEinvoiceMarkets(@Param("userId") Long userId, @Param("roleId") Long roleId);
    // D031 2017-07-10 END

    //根据ID获取市场 2017/07/21 XinJia.Yao BEGIN
    SpmMarket getMarket(Long markeId);
    //2017/07/21 XinJia.Yao END

    //根据会员id获取market
    List<SpmMarket> selectSpmMarketByMemberId(@Param("memberId") Long memberId);

}