package com.lkkhpg.dsis.common.discount.mapper;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Project: pos2
 * Package: com.lkkhpg.dsis.common.discount.mapper
 * User: 11816
 * Date: 2018/1/23
 * Time: 11:22
 */
public interface DiscountTrxQueryMapper {

//    List<DiscountTrxQuery> queryBasic(DiscountTrxQuery discountTrxQuery);

    List<DiscountTrxQuery> queryBasic(@Param("discountTrxQuery") DiscountTrxQuery discountTrxQuery, @Param("list") List<String> list);

    List<DiscountTrxQuery> query( @Param("discountTrxQuery")DiscountTrxQuery discountTrxQuery);

}
