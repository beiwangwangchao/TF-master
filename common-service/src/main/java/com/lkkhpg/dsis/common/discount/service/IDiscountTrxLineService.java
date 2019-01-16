package com.lkkhpg.dsis.common.discount.service;

import com.lkkhpg.dsis.common.discount.dto.DiscountTrxLineDto;
import com.lkkhpg.dsis.platform.annotation.StdWho;
import com.lkkhpg.dsis.platform.core.IRequest;

public interface IDiscountTrxLineService {

    public long insert(IRequest request, @StdWho DiscountTrxLineDto discountTrxLineDto);

}
