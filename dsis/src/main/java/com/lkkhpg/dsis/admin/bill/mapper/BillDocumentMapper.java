package com.lkkhpg.dsis.admin.bill.mapper;

import com.lkkhpg.dsis.admin.bill.dto.BillDto;

import java.util.List;

public interface BillDocumentMapper {

    /**
     * @param billDto
     * @return
     */
    void insert(BillDto billDto);

    List<BillDto> selectUnit(BillDto bill);

    Long queryMarket(Long marketId);
    /**
     * @param billDto
     * @return
     */
   List<BillDto> selectByProcessDate(String date);

   String selectMaxDate();

}
