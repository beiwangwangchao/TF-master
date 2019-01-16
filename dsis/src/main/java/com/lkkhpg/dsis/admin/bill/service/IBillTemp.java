package com.lkkhpg.dsis.admin.bill.service;

import com.lkkhpg.dsis.admin.bill.dto.BillDto;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

public interface IBillTemp extends ProxySelf<IBillTemp> {

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
