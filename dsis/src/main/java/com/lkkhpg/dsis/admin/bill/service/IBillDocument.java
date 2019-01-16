package com.lkkhpg.dsis.admin.bill.service;

import com.lkkhpg.dsis.admin.bill.dto.BillDto;
import com.lkkhpg.dsis.platform.core.IRequest;
import com.lkkhpg.dsis.platform.core.ProxySelf;

import java.util.List;

public interface IBillDocument extends ProxySelf<IBillDocument> {

    /**
     * @param billDto
     * @return
     */
    boolean insert(List<BillDto> billDto);


    /**
     * @return
     */
    List<BillDto> queryData(String order_process_data);


    List<BillDto> selectUnit(IRequest request, BillDto billDto, String partner, String privateSubpartner, String publicPartner, int page, int pagesize);


    boolean listeningDownloadAccountList()throws Exception;


    Long queryCompany(IRequest request, Long marketId);

}
