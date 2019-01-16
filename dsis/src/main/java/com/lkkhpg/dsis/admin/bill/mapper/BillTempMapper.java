package com.lkkhpg.dsis.admin.bill.mapper;

import com.lkkhpg.dsis.admin.bill.dto.BillDto;

import java.util.List;

public interface BillTempMapper {

    /**
     * @param billDto
     * @return
     */
    int insert(List<BillDto> billDto);


    /**
     * @param billDto
     * @return
     */
    int update(List<BillDto> billDto);


    /**
     * @return
     */
    List<BillDto> queryData(BillDto billDto);

}
