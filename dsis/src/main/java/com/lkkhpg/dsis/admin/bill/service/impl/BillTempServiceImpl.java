package com.lkkhpg.dsis.admin.bill.service.impl;

import com.lkkhpg.dsis.admin.bill.dto.BillDto;
import com.lkkhpg.dsis.admin.bill.mapper.BillTempMapper;
import com.lkkhpg.dsis.admin.bill.service.IBillDocument;
import com.lkkhpg.dsis.admin.bill.service.IBillTemp;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BillTempServiceImpl implements IBillTemp {

    @Autowired
    private BillTempMapper billTempMapper;

    @Override
    public int insert(List<BillDto> billDto) {
        return 0;
    }

    @Override
    public int update(List<BillDto> billDto) {
        return 0;
    }

    @Override
    public List<BillDto> queryData(BillDto billDto) {
        return billTempMapper.queryData(billDto);
    }
}
