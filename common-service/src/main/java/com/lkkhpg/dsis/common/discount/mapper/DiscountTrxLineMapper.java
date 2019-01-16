package com.lkkhpg.dsis.common.discount.mapper;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface DiscountTrxLineMapper {

     int baseInsert(DiscountTrxLineDto discountTrxLineDto);

     int queryMemberIfExists(DiscountTrxLineDto discountTrxLineDto);

     List<DiscountTrxLineDto> queryDiscountDetail(@Param("discountTrxHeadId") Long discountTrxHeadId);

     int updateSelectiveByPrimarkey(DiscountTrxLineDto record);
}
