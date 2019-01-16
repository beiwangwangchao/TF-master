package com.lkkhpg.dsis.common.discount.service.impl;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import com.lkkhpg.dsis.common.discount.mapper.DiscountTrxLineMapper;
import com.lkkhpg.dsis.common.discount.service.IDiscountTrxLineService;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DiscountTrxLineServiceImpl implements IDiscountTrxLineService {

    @Autowired
    private DiscountTrxLineMapper discountTrxLineMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long insert(IRequest request,@StdWho DiscountTrxLineDto discountTrxLineDto) {
        return discountTrxLineMapper.baseInsert(discountTrxLineDto);
    }
}
